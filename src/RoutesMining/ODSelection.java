package RoutesMining;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import Config.Config;
import Model.RawPoint;
import Model.RawRecord;
import Model.StayPoint;
import Model.StayRecord;
import Model.Zone;
import Model.ZoneMap;

/*
 * author:youg
 * date:20160614
 * 从StayRecord中提取指定OD范围内的Trajs
 * 0raw:原始信令数据，不包含经纬度信息，按时间划分文件
 * 1fixed:添加经纬度信息，按id后两位划分文件，文件内按id和时间排序
 * 2timeSpan:记录每个ID每天最早出现的时间和位置以及最晚出现的时间和位置
 * 3timeLine：以15分钟为单位统计每个ID在每个时间段出现的次数
 * 4goodUser:数据质量好、用于下一步分析的用户ID列表。提取规则：7点前、19点后有记录，7-19点每3个小时有记录的用户数所占比例；用户比例：55%
 * 5goodRecord:4goodUser列表里的用户的完整记录，按id后两位分割到不同文件中
 * 7stayRecord:从5goodRecord中提取出的用户停留点记录
 * 8OD:从7stayRecord中提取出的指定OD范围内的Trips
 */

public class ODSelection {
	//O-交通小区
	public static Zone oZone;
	//D-交通小区
	public static Zone dZone;
	//O-交通小区列表
	public static List<Zone> oZoneList;
	//D-交通小区列表
	public static List<Zone> dZoneList;
	//轨迹范围
	public static double RMaxLon = Double.MIN_VALUE;
	public static double RMinLon = Double.MAX_VALUE;
	public static double RMaxLat = Double.MIN_VALUE;
	public static double RMinLat = Double.MAX_VALUE;
	/*
	//O-天通苑
	public static double OMaxLon = 116.442177;
	public static double OMinLon = 116.406162;
	public static double OMaxLat = 40.079664;
	public static double OMinLat = 40.061106;
	//D-国贸
	public static double DMaxLon = 116.477652;
	public static double DMinLon = 116.450161;
	public static double DMaxLat = 39.913619;
	public static double DMinLat = 39.903507;
	//R-Routes范围
	public static double RMaxLon = 116.548925;
	public static double RMinLon = 116.391873;
	public static double RMaxLat = 40.113943;
	public static double RMinLat = 39.893205;
	*/
	/*
	//O-亚运村
	public static final double OMaxLon = 116.416936;
	public static final double OMinLon = 116.400242;
	public static final double OMaxLat = 40.009834;
	public static final double OMinLat = 39.989681;
	//D-中关村
	public static final double DMaxLon = 116.318009;
	public static final double DMinLon = 116.303547;
	public static final double DMaxLat = 39.985287;
	public static final double DMinLat = 39.975521;
	//R-Routes范围
	public static final double RMaxLon = 116.416936;
	public static final double RMinLon = 116.303547;
	public static final double RMaxLat = 40.009834;
	public static final double RMinLat = 39.975521;	
	*/
	/*
	//O-天通苑
	public static final double OMaxLon = 116.442177;
	public static final double OMinLon = 116.406162;
	public static final double OMaxLat = 40.079664;
	public static final double OMinLat = 40.061106;
	//D-中关村
	public static final double DMaxLon = 116.318009;
	public static final double DMinLon = 116.303547;
	public static final double DMaxLat = 39.985287;
	public static final double DMinLat = 39.975521;
	//R-Routes范围
	public static final double RMaxLon = 116.462177;
	public static final double RMinLon = 116.303547;
	public static final double RMaxLat = 40.079664;
	public static final double RMinLat = 39.975521;	
	*/
	/*
	//O-中关村
	public static final double OMaxLon = 116.318009;
	public static final double OMinLon = 116.303547;
	public static final double OMaxLat = 39.985287;
	public static final double OMinLat = 39.975521;
	//D-国贸
	public static final double DMaxLon = 116.477652;
	public static final double DMinLon = 116.450161;
	public static final double DMaxLat = 39.913619;
	public static final double DMinLat = 39.903507;
	//R-Routes范围
	public static final double RMaxLon = 116.477652;
	public static final double RMinLon = 116.303547;
	public static final double RMaxLat = 39.985287;
	public static final double RMinLat = 39.903507;	
	*/
	/*
	//O-宋家庄
	public static final double OMaxLon = 116.450054;
	public static final double OMinLon = 116.421987;
	public static final double OMaxLat = 39.858744;
	public static final double OMinLat = 39.845862;
	//D-金融街
	public static final double DMaxLon = 116.372984;
	public static final double DMinLon = 116.353458;
	public static final double DMaxLat = 39.918422;
	public static final double DMinLat = 39.905946;
	//R-Routes范围
	public static final double RMaxLon = 116.450054;
	public static final double RMinLon = 116.353458;
	public static final double RMaxLat = 39.918422;
	public static final double RMinLat = 39.845862;	
	*/
	/*
	//O-通州
	public static final double OMaxLon = 116.691626;
	public static final double OMinLon = 116.644964;
	public static final double OMaxLat = 39.909760;
	public static final double OMinLat = 39.885705;
	//D-中关村
	public static final double DMaxLon = 116.318009;
	public static final double DMinLon = 116.303547;
	public static final double DMaxLat = 39.985287;
	public static final double DMinLat = 39.975521;
	//R-Routes范围
	public static final double RMaxLon = 116.691626;
	public static final double RMinLon = 116.303547;
	public static final double RMaxLat = 40.005287;
	public static final double RMinLat = 39.885705;	
	*/
	/*
	//O-亚运村
	public static final double OMaxLon = 116.416936;
	public static final double OMinLon = 116.400242;
	public static final double OMaxLat = 40.009834;
	public static final double OMinLat = 39.989681;
	//D-金融街
	public static final double DMaxLon = 116.372984;
	public static final double DMinLon = 116.353458;
	public static final double DMaxLat = 39.918422;
	public static final double DMinLat = 39.905946;
	//R-Routes范围
	public static final double RMaxLon = 116.416936;
	public static final double RMinLon = 116.353458;
	public static final double RMaxLat = 40.009834;
	public static final double RMinLat = 39.905946;	
	*/
	/*
	//O-
	public static final double OMaxLon = ;
	public static final double OMinLon = ;
	public static final double OMaxLat = ;
	public static final double OMinLat = ;
	//D-
	public static final double DMaxLon = ;
	public static final double DMinLon = ;
	public static final double DMaxLat = ;
	public static final double DMinLat = ;
	//R-Routes范围
	public static final double RMaxLon = ;
	public static final double RMinLon = ;
	public static final double RMaxLat = ;
	public static final double RMinLat = ;	
	*/
	public static List<StayRecord> stayRecords = new LinkedList<StayRecord>();
	public static List<StayRecord> ODRecords = new LinkedList<StayRecord>();
	
	//载入出行链（停留/移动点）
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
			lastId = thisId;
		}
		br.close();
	}
	public static boolean inZoneList(List<Zone> zones,double lon,double lat){
		for(Zone zone:zones){
			//if(lon<zone.maxLon && lon>zone.minLon && lat<zone.maxLat && lat>zone.minLat)
			if(zone.Contains(lon, lat))
				return true;
		}
		return false;
	}
	//提取指定OD范围内的trips
	public static void extractOD(){
		System.out.println("Now extracting OD");
		ODRecords.clear();
		for(StayRecord user:stayRecords){
			StayRecord person = new StayRecord(user.getId(),user.getDate());
			ODRecords.add(person);
			int label=-1;
			for(int i=0;i<user.getStayPoints().size();i++){
				double lon = user.getStayPoints().get(i).getLon();
				double lat = user.getStayPoints().get(i).getLat();
				//如果不是停留点，判断是否在R范围内，如果不在，label=-1，不管在不在，continue
				if(user.getStayPoints().get(i).getState()==0){
					if(lon<RMinLon || lon>RMaxLon || lat<RMinLat || lat>RMaxLat)
						label=-1;
					continue;
				}
				//如果起点在O范围内
				//if(lon<oZone.maxLon && lon>oZone.minLon && lat<oZone.maxLat && lat>oZone.minLat){
				if(inZoneList(oZoneList,lon,lat)){	
					label=i;
					continue;
				}
				//如果终点在D范围内
				//if(label!=-1 && lon<dZone.maxLon && lon>dZone.minLon && lat<dZone.maxLat && lat>dZone.minLat){
				if(label!=-1 && inZoneList(dZoneList,lon,lat) && i-label>30){
						for(int j=label;j<=i;j++){
						StayPoint point = new StayPoint(user.getStayPoints().get(j));
						if(j==label)
							point.setState(2);
						if(j==i)
							point.setState(3);
						person.getStayPoints().add(point);
					}
				}
				label=-1;
			}
		}
	}
	//导出OD数据
	public static void exportOD(String ODTrajFileName)throws Exception{
		System.out.println("Now exporting ODRecord: "+ODTrajFileName);
		BufferedWriter bw = new BufferedWriter(new FileWriter(ODTrajFileName,true));
		DecimalFormat df = new DecimalFormat("#.000000");
		for(StayRecord user:ODRecords){
			for(StayPoint point:user.getStayPoints()){
				bw.write(user.getId()+","+user.getDate()+","+point.getSTime()+"-"+point.getETime()+","+df.format(point.getLon())+","+df.format(point.getLat())+","+point.getState()+"\n");
			}
		}
		bw.close();
	}
	/*
	 * 统计每个ID出现的天数
	 */
	public static void statUserDay(String ODTrajFileName)throws Exception{
		System.out.println("Now stating ODRecord: "+ODTrajFileName);
		BufferedReader br = new BufferedReader(new FileReader(ODTrajFileName));
		Map<String,Integer> user = new HashMap<String,Integer>();
		String af;
		String[] afs;
		while((af=br.readLine())!=null){
			afs=af.split(",");
			if(!afs[5].equals("2"))
				continue;
			if(user.containsKey(afs[0]))
				user.put(afs[0], user.get(afs[0])+1);
			else
				user.put(afs[0], 1);
		}
		int max=0;
		for(String name:user.keySet())
			if(user.get(name)>max)
				max=user.get(name);
		int[] list = new int[max+1];
		for(String name:user.keySet())
			list[user.get(name)]+=1;
		for(int i=0;i<list.length;i++)
			System.out.println(i+":"+list[i]);
		br.close();
	}
	/*
	 * 统计每天新增新ID数量
	 */
	public static void statAccNumDay(String ODTrajFileName)throws Exception{
		System.out.println("Now stating ODRecord: "+ODTrajFileName);
		BufferedReader br = new BufferedReader(new FileReader(ODTrajFileName));
		Set<String> user = new HashSet<String>();
		Set<String> oldUser = new HashSet<String>();
		Set<String> temp = new HashSet<String>();
		String af;
		String[] afs;
		String lastDay=null;
		while((af=br.readLine())!=null){
			afs=af.split(",");
			if(lastDay==null || !afs[1].equals(lastDay)){
				temp.clear();
				temp.addAll(user);
				temp.removeAll(oldUser);
				System.out.println(user.size()+","+temp.size());
				lastDay=afs[1];
				oldUser.addAll(user);
				user.clear();
			}
			if(!afs[5].equals("2"))
				continue;
			if(!user.contains(afs[0]))
				user.add(afs[0]);
		}
		temp.clear();
		temp.addAll(user);
		temp.removeAll(oldUser);
		System.out.println(user.size()+","+temp.size());
		br.close();
	}
	public static void main(String[] args)throws Exception{
		//目录初始化，交通小区初始化
		Config.init();
		ZoneMap map = new ZoneMap();
		map.init();
		Scanner cin = new Scanner(System.in);
		System.out.println("Input Origin zone list, seperated by \",\":");
		String oZones = cin.next();
		System.out.println("input Destination zone list, seperated by \",\":");
		String dZones = cin.next();
		String[] oZoness = oZones.split(",");
		oZoneList = new ArrayList<Zone>();
		for(String oStr:oZoness){
			Zone oZ = map.getZoneById(Integer.parseInt(oStr));
			if(oZ!=null){
				if(oZ.maxLon>RMaxLon)
					RMaxLon=oZ.maxLon;
				if(oZ.minLon<RMinLon)
					RMinLon=oZ.minLon;
				if(oZ.maxLat>RMaxLat)
					RMaxLat=oZ.maxLat;
				if(oZ.minLat<RMinLat)
					RMinLat=oZ.minLat;
				oZoneList.add(oZ);
			}
		}
		String[] dZoness = dZones.split(",");
		dZoneList = new ArrayList<Zone>();
		for(String dStr:dZoness){
			Zone dZ = map.getZoneById(Integer.parseInt(dStr));
			if(dZ!=null){
				if(dZ.maxLon>RMaxLon)
					RMaxLon=dZ.maxLon;
				if(dZ.minLon<RMinLon)
					RMinLon=dZ.minLon;
				if(dZ.maxLat>RMaxLat)
					RMaxLat=dZ.maxLat;
				if(dZ.minLat<RMinLat)
					RMinLat=dZ.minLat;
				dZoneList.add(dZ);
			}
		}
		RMaxLon+=0.01;
		RMinLon-=0.01;
		RMaxLat+=0.01;
		RMinLat-=0.01;
		if(oZoneList==null || oZoneList.size()<1 || dZoneList==null || dZoneList.size()<1){
			System.out.println("input zone id error...\nexit");
			return;
		}
		
		String ODTrajName = Config.getAttr(Config.ODTrajPath)+File.separator + String.valueOf(oZoneList.get(0).id)+"_"+String.valueOf(dZoneList.get(0).id)+".txt";
		//System.out.println(ODFileName);
		BufferedWriter bw = new BufferedWriter(new FileWriter(ODTrajName));
		bw.close();
		File[] datePath = new File(Config.getAttr(Config.WorkPath)).listFiles();
		int days = 0;
		
		for(File file:datePath){
			//if(++days<10)
			//	continue;
			File stayRecordPath = new File(file.getAbsolutePath()+"\\7stayRecord");
			System.out.println(stayRecordPath.getAbsolutePath());
			File[] stayRecordFiles = stayRecordPath.listFiles();
			int k=0;
			for(File stayfile:stayRecordFiles){
				k++;
				//if((k&1)==1)
				//	continue;
				importStayRecord(stayfile);
				extractOD();
				exportOD(ODTrajName);
				//if(k>=40)
				//	break;
			}
		}//every day
		
		//statUserDay("All20Days_tiantongyuan_guomao170105test.txt");
		//statAccNumDay("All20Days_tiantongyuan_guomao170105test.txt");
		
		System.out.println("finish:"+ODTrajName);
	}
}
