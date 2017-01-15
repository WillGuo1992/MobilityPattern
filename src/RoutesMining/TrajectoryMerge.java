package RoutesMining;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import Config.Config;
import Model.RawPoint;
import Model.StayPoint;
import Model.StayRecord;

/*
 * author:youg
 * date:20161108
 * 把一个用户连续多天的多条轨迹合并成为一条轨迹
 * 0raw:原始信令数据，不包含经纬度信息，按时间划分文件
 * 1fixed:添加经纬度信息，按id后两位划分文件，文件内按id和时间排序
 * 2timeSpan:记录每个ID每天最早出现的时间和位置以及最晚出现的时间和位置
 * 3timeLine：以15分钟为单位统计每个ID在每个时间段出现的次数
 * 4goodUser:数据质量好、用于下一步分析的用户ID列表。提取规则：7点前、19点后有记录，7-19点每3个小时有记录的用户数所占比例；用户比例：55%
 * 5goodRecord:4goodUser列表里的用户的完整记录，按id后两位分割到不同文件中
 * 7stayRecord:从5goodRecord中提取出的用户停留点记录
 * 8OD:从7stayRecord中提取出的指定OD范围内的Trips
 */
public class TrajectoryMerge {
	public static List<StayRecord> stayRecords;
	public static StayRecord mergedRecord;
	public static BufferedReader br;
	public static BufferedWriter bw;
	//加载一个用户多日的trajectory
	public static void loadRecord(File multiDayFile)throws Exception{
		stayRecords = new LinkedList<StayRecord>();
		//String id=null,date=null;
		br = new BufferedReader(new FileReader(multiDayFile));
		String af;
		String[] afs;
		while((af=br.readLine())!=null){
			afs = af.split(",");
			if(afs[5].equals("2")){
				stayRecords.add(new StayRecord(afs[0],afs[1]));
			}
			StayRecord nowUser = stayRecords.get(stayRecords.size()-1);
			double lon=Double.parseDouble(afs[3]);
			double lat=Double.parseDouble(afs[4]);
			String[] times = afs[2].split("-");
			String sTime=times[0];
			String eTime=times[1];
			int event=0;
			int state=Integer.parseInt(afs[5]);
			int type=0;
			StayPoint nowStayPoint = new StayPoint(lon,lat,sTime,eTime,event,state,type);
			nowUser.getStayPoints().add(nowStayPoint);
		}
		br.close();
	}
	//把多日trajectory合并到一个trajectory
	public static void mergeRecord()throws Exception{
		mergedRecord = new StayRecord(stayRecords.get(0).getId(),"00000000");
		int days = stayRecords.size();
		int[] pos = new int[days];
		int[] max = new int[days];
		int totalPassbyPoint = 0;
		for(int i=0;i<days;i++){
			max[i]=stayRecords.get(i).getStayPoints().size();
			totalPassbyPoint+=max[i]-2;
		}
		double oLon = 0.0, oLat=0.0, dLon=0.0, dLat=0.0;
		for(int i=0;i<days;i++){
			oLon+=stayRecords.get(i).getStayPoints().get(pos[i]).getLon();
			oLat+=stayRecords.get(i).getStayPoints().get(pos[i]++).getLat();
			dLon+=stayRecords.get(i).getStayPoints().get(max[i]-1).getLon();
			dLat+=stayRecords.get(i).getStayPoints().get(max[i]-1).getLat();
		}
		oLon/=days;
		oLat/=days;
		dLon/=days;
		dLat/=days;
		mergedRecord.getStayPoints().add(new StayPoint(oLon,oLat,"000000","000000",0,2,0));//创建O点
		//排序移动点
		while(totalPassbyPoint-->0){
			double minDis = -1.0;
			int minPos = -1;
			double lastLon = mergedRecord.getStayPoints().get(mergedRecord.getStayPoints().size()-1).getLon();
			double lastLat = mergedRecord.getStayPoints().get(mergedRecord.getStayPoints().size()-1).getLat();
			for(int i=0;i<days;i++){
				if(pos[i]>=max[i]-1)
					continue;
				double nowLon = stayRecords.get(i).getStayPoints().get(pos[i]).getLon();
				double nowLat = stayRecords.get(i).getStayPoints().get(pos[i]).getLat();
				double dis = distanceInGlobal(nowLon,nowLat,lastLon,lastLat);
				//System.out.println("dis="+dis);
				if(minDis<0 || dis<minDis){
					minDis = dis;
					minPos = i;
				}
			}
			//System.out.println("minDis="+minDis);
			//位置相同的点，只保留一个
			if(minDis>-0.99999999 && minDis<0.000000001){
				pos[minPos]++;
				continue;
			}
			//反向点，去除
			double nLon = stayRecords.get(minPos).getStayPoints().get(pos[minPos]).getLon();
			double nLat = stayRecords.get(minPos).getStayPoints().get(pos[minPos]).getLat();
			double bLon = mergedRecord.getStayPoints().get(mergedRecord.getStayPoints().size()-1).getLon();
			double bLat = mergedRecord.getStayPoints().get(mergedRecord.getStayPoints().size()-1).getLat();
			if(distanceInGlobal(oLon,oLat,nLon,nLat)<distanceInGlobal(oLon,oLat,bLon,bLat)){
				pos[minPos]++;
				continue;
			}
			if(distanceInGlobal(dLon,dLat,nLon,nLat)>distanceInGlobal(dLon,dLat,bLon,bLat)){
				pos[minPos]++;
				continue;
			}
			mergedRecord.getStayPoints().add(new StayPoint(stayRecords.get(minPos).getStayPoints().get(pos[minPos]++)));
		}
		mergedRecord.getStayPoints().add(new StayPoint(dLon,dLat,"000000","000000",0,3,0));//创建D点
	}
	//输出合并后的trajectory
	public static void exportRecord(File mergedFile)throws Exception{
		String outputFileName = Config.getAttr(Config.PatternPath)+File.separator+"2mergedRecord"+File.separator+mergedFile.getName();
		File outputFile = new File(outputFileName);
		bw = new BufferedWriter(new FileWriter(outputFile));
		DecimalFormat df = new DecimalFormat("#.000000");
		int i=1;
		for(StayPoint sp:mergedRecord.getStayPoints()){
			bw.write(mergedRecord.getId()+",00000000,"+(i++)+","+df.format(sp.getLon())+","+df.format(sp.getLat())+","+sp.getState()+"\n");
		}
		bw.close();
	}
	//输出合并后的trajectory到可视化模块的json文件
	public static void exportRecordToDV(File inputFile)throws Exception{
		String outputFileName = "E:\\DataVisual\\dv\\data\\dateid\\20130225"+File.separator+inputFile.getName().split("\\.")[0];
		File outputFile  = new File(outputFileName);
		outputFile.mkdirs();
		outputFileName+=File.separator+"point.json";
		outputFile = new File(outputFileName);
		bw = new BufferedWriter(new FileWriter(outputFile));
		DecimalFormat df = new DecimalFormat("#.000000");
		bw.write("[");
		int i=1;
		for(StayPoint sp:mergedRecord.getStayPoints()){
			if(i!=1)
				bw.write(",\n");
			else
				bw.write("\n");
			bw.write("{\n\"coords\":["+df.format(sp.getLon())+","+df.format(sp.getLat())+"],\n");
			bw.write("\"label\":\""+(i++)+"\",\n");
			bw.write("\"is_special\":"+(sp.getState()==0?"false":"true")+"\n}");
		}
		bw.write("\n]");
		bw.close();
	}
	/*
	 * 计算两位置之间的距离，根据球面坐标长度公式计算(单位：米)
	 * 注意，这个计算很耗时间,另外,这个计算把经纬度的100万倍还原了!
	 */
	public static double distanceInGlobal(double lon1, double lat1, double lon2, double lat2){
		double x1 = lon1;
		double y1 = lat1;
		double x2 = lon2;
		double y2 = lat2;

		double L = (3.1415926*6370/180)*Math.sqrt((Math.abs((x1)-(x2)))*(Math.abs((x1)-(x2)))*(Math.sin((90-(y1))*(3.1415926/180)))*(Math.sin((90-(y1))*(3.1415926/180)))+(Math.abs((y1)-(y2)))*(Math.abs((y1)-(y2))));
		return L * 1000;
	}
	public static double distanceInGlobal(RawPoint a, RawPoint b){
		return distanceInGlobal(a.getLon(), a.getLat(), b.getLon(), b.getLat());
	}
	public static void main(String[] args)throws Exception{
		Config.init();
		File goodUserRecordPath = new File(Config.getAttr(Config.PatternPath)+File.separator + "1goodUserRecord");
		File[] goodUserRecordFiles = goodUserRecordPath.listFiles();
		for(File file:goodUserRecordFiles){
			//System.out.print(",\""+file.getName().split("\\.")[0]+"\"");
			//System.out.println("Now loading: "+file.getAbsolutePath());
			loadRecord(file);
			//System.out.println("Now merging: "+file.getAbsolutePath());
			mergeRecord();
			//System.out.println("Now exporting: "+file.getAbsolutePath());
			exportRecord(file);
			//System.out.println("Now exportingToDV: "+file.getAbsolutePath());
			exportRecordToDV(file);
			//break;
		}
		System.out.println("finish");
	}
}
