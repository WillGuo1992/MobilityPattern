package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Config.Config;
import Model.Grid;
import Model.GridMap;
import Model.StayPoint;
import Model.StayRecord;

/*
 * author:youg
 * date:20170206
 * 生成热点区域可视化展示数据（早晚高峰居住工作区）
 * 0raw:原始信令数据，不包含经纬度信息，按时间划分文件
 * 1fixed:添加经纬度信息，按id后两位划分文件，文件内按id和时间排序
 * 2timeSpan:记录每个ID每天最早出现的时间和位置以及最晚出现的时间和位置
 * 3timeLine：以15分钟为单位统计每个ID在每个时间段出现的次数
 * 4goodUser:数据质量好、用于下一步分析的用户ID列表。提取规则：7点前、19点后有记录，7-19点每3个小时有记录的用户数所占比例；用户比例：55%
 * 5goodRecord:4goodUser列表里的用户的完整记录，按id后两位分割到不同文件中
 * 7stayRecord:从5goodRecord中提取出的用户停留点记录
 * 8OD:从7stayRecord中提取出的指定OD范围内的Trips
 */
public class HotArea {
	public static DecimalFormat df = new DecimalFormat("#.000000");
	public static GridMap map = new GridMap();
	
	public static int gridNum;
	//public static int[] gridList;
	//public static Map<Integer,Integer> gridMap = new HashMap<Integer,Integer>();
	
	public static List<StayRecord> stayRecords = new LinkedList<StayRecord>();
	
	public static Map<Grid,Integer> zgfO = new HashMap<Grid,Integer>();
	public static Map<Grid,Integer> zgfD = new HashMap<Grid,Integer>();
	public static Map<Grid,Integer> wgfO = new HashMap<Grid,Integer>();
	public static Map<Grid,Integer> wgfD = new HashMap<Grid,Integer>();
	/*
	 * 载入出行链（只载入停留点，不载入移动点）
	 */
	public static void importStayRecord(File stayRecordFile)throws Exception{
		System.out.println("Now importing StayRecord: "+stayRecordFile.getAbsolutePath());
		stayRecords.clear();
		BufferedReader br = new BufferedReader(new FileReader(stayRecordFile));
		String lastId=null,thisId=null;
		String af;
		String[] afs;
		int len=0;
		while((af=br.readLine())!=null){
			afs = af.split(",");
			//判断该条记录是否是停留点，如果不是pass
			if(Integer.parseInt(afs[5])!=1)
				continue;
			thisId = afs[0];
			if(!thisId.equals(lastId)){
				StayRecord user = new StayRecord(thisId,afs[1]);
				len+=1;
				stayRecords.add(user);
			}
			String[] times = afs[2].split("-");
			StayRecord user = stayRecords.get(len-1);
			StayPoint point = new StayPoint();
			point.setLon(Double.valueOf(afs[3]));
			point.setLat(Double.valueOf(afs[4]));
			point.setState(Integer.valueOf(afs[5]));
			point.setType(Integer.valueOf(afs[6]));
			point.setSTime(times[0]);
			point.setETime(times[1]);
			user.getStayPoints().add(point);
			/*
			Grid g = map.getGridByPos(point.getLon(), point.getLat());
			if(g!=null && g.getId()==20147)
				System.out.println(user.getId());
			*/
			lastId = thisId;
		}
		br.close();
	}
	/*
	 * 统计早高峰O
	 */
	public static void calZWGFOD(){
		System.out.println("Now caling ZWGFOD");
		for(StayRecord user:stayRecords){
			StayPoint lastPoint=null;
			for(StayPoint point:user.getStayPoints()){
				if(lastPoint!=null){
					Grid oGrid = map.getGridByPos(lastPoint.getLon(), lastPoint.getLat());
					if(oGrid==null)
						continue;
					Grid dGrid = map.getGridByPos(point.getLon(), point.getLat());
					if(dGrid==null)
						continue;
					if(distanceInGlobal(lastPoint.getLon(),lastPoint.getLat(),point.getLon(),point.getLat())<3000)
						continue;
					//zgfO+1
					if(timeSpan("050000",lastPoint.getETime())>0 && timeSpan(lastPoint.getETime(),"080000")>0)
						zgfO.put(oGrid, zgfO.get(oGrid)+1);
					//zgfD+1
					if(timeSpan("060000",point.getSTime())>0 && timeSpan(point.getSTime(),"100000")>0)
						zgfD.put(dGrid, zgfD.get(dGrid)+1);
					//wgfO+1
					if(timeSpan("160000",lastPoint.getETime())>0 && timeSpan(lastPoint.getETime(),"200000")>0)
						wgfO.put(oGrid, wgfO.get(oGrid)+1);
					//wgfD+1
					if(timeSpan("160000",point.getSTime())>0 && timeSpan(point.getSTime(),"200000")>0)
						wgfD.put(dGrid, wgfD.get(dGrid)+1);
				}
				lastPoint = point;
			}
		}
	}
	/*
	 * 计算两个时间点之间的时间差，时间格式“HHmmSS”，返回结果单位分钟
	 */
	public static int timeSpan(String a, String b){
		int span = Integer.valueOf(b.substring(0,2))*60+Integer.valueOf(b.substring(2,4));
		span = span - (Integer.valueOf(a.substring(0,2))*60+Integer.valueOf(a.substring(2,4)));
		//if(span<0)
		//	span=-span;
		return span;
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
	/*
	 * 输出早晚高峰OD
	 */
	public static void exportZWGFOD(String fileName)throws Exception{
		System.out.println("Now exporting ODflow");
		BufferedWriter bw;
		int total=0,moreThan10Total=0;
		int[] zo = new int[zgfO.size()];
		int[] zd = new int[zgfD.size()];
		int[] wo = new int[wgfO.size()];
		int[] wd = new int[wgfD.size()];
		bw = new BufferedWriter(new FileWriter(fileName+"matrix.json"));
		int i=0;
		for(Grid g:zgfO.keySet())
			zo[i++]=zgfO.get(g)/g.step;
		Arrays.sort(zo);
		int num2p = zo[i-1-i/50];
		int num5p = zo[i-1-i/20];
		int num10p = zo[i-1-i/10];
		int num20p = zo[i-1-i/5];
		bw.write("[\n");
		for(Grid g:zgfO.keySet()){
			if(zgfO.get(g)/g.step>=num2p)
				bw.write("\t["+df.format(g.minLon)+","+df.format(g.minLat)+","+df.format(g.maxLon)+","+df.format(g.maxLat)+",\"#d67318\"],\n");
			else if(zgfO.get(g)/g.step>=num5p)
				bw.write("\t["+df.format(g.minLon)+","+df.format(g.minLat)+","+df.format(g.maxLon)+","+df.format(g.maxLat)+",\"#ff9c42\"],\n");
			else if(zgfO.get(g)/g.step>=num10p)
				bw.write("\t["+df.format(g.minLon)+","+df.format(g.minLat)+","+df.format(g.maxLon)+","+df.format(g.maxLat)+",\"#ffb573\"],\n");
			else if(zgfO.get(g)/g.step>=num20p)
				bw.write("\t["+df.format(g.minLon)+","+df.format(g.minLat)+","+df.format(g.maxLon)+","+df.format(g.maxLat)+",\"#ffcea5\"],\n");
			else
				bw.write("\t["+df.format(g.minLon)+","+df.format(g.minLat)+","+df.format(g.maxLon)+","+df.format(g.maxLat)+",\"#ffefc6\"],\n");
		}
		bw.write("]");
		bw.close();
		System.out.println("Total:"+total);
		System.out.println("MoreThan10:"+moreThan10Total);
	}
	public static void main(String[] args)throws Exception{
		Config.init();
		//map初始化及-------------------
		map.init();
		gridNum = map.getL1Grids().size()+map.getL2Grids().size()+map.getL3Grids().size();
		for(Grid g:map.getL1Grids()){
			zgfO.put(g, 0);
			zgfD.put(g, 0);
			wgfO.put(g, 0);
			wgfD.put(g, 0);
		}
		for(Grid g:map.getL2Grids()){
			zgfO.put(g, 0);
			zgfD.put(g, 0);
			wgfO.put(g, 0);
			wgfD.put(g, 0);
		}
		for(Grid g:map.getL3Grids()){
			zgfO.put(g, 0);
			zgfD.put(g, 0);
			wgfO.put(g, 0);
			wgfD.put(g, 0);
		}
		//--------------------------------------
		File[] datePath = new File(Config.getAttr(Config.WorkPath)).listFiles();
		int days = 0;
		for(File file:datePath){
			File stayRecordPath = new File(file.getAbsolutePath()+"\\7stayRecord");
			System.out.println(stayRecordPath.getAbsolutePath());
			File[] stayRecordFiles = stayRecordPath.listFiles();
			int k=0;
			for(File stayfile:stayRecordFiles){
				importStayRecord(stayfile);
				calZWGFOD();
				//if(k++>=40)
				//	break;
			}//every file
			if(++days>0)
				break;
		}//every day
		exportZWGFOD("E:\\DataVisual\\dv\\data\\date\\20130225\\");
		System.out.println("finish");
	}

}
