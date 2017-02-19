package ODMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import Config.Config;
import Model.StayPoint;
import Model.StayRecord;

public class AreaAnalysis {
	/*
	//O-天通苑
	public static final double OMaxLon = 116.442177;
	public static final double OMinLon = 116.406162;
	public static final double OMaxLat = 40.079664;
	public static final double OMinLat = 40.061106;
	//D-国贸
	public static final double DMaxLon = 116.477652;
	public static final double DMinLon = 116.450161;
	public static final double DMaxLat = 39.913619;
	public static final double DMinLat = 39.903507;
	//R-Routes范围
	public static final double RMaxLon = 116.548925;
	public static final double RMinLon = 116.391873;
	public static final double RMaxLat = 40.113943;
	public static final double RMinLat = 39.893205;
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
	
	//O-回龙观
	public static final double OMaxLon = 116.361741;
	public static final double OMinLon = 116.317710;
	public static final double OMaxLat = 40.091381;
	public static final double OMinLat = 40.070957;
	//D-上地
	public static final double DMaxLon = 116.309427;
	public static final double DMinLon = 116.284836;
	public static final double DMaxLat = 40.057491;
	public static final double DMinLat = 40.034494;
	//R-Routes范围
	public static final double RMaxLon = 200.00;
	public static final double RMinLon = 100;
	public static final double RMaxLat = 50;
	public static final double RMinLat = 30;	
	
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
	public static int totalO=0,totalD=0;
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
			if(point.getState()!=0){
				if(point.getLon()<OMaxLon && point.getLon()>OMinLon && point.getLat()<OMaxLat && point.getLat()>OMinLat)
					if(timeSpan(point.getETime(),"060000")<0 && timeSpan(point.getETime(),"100000")>0)
						totalO+=1;
				if(point.getLon()<DMaxLon && point.getLon()>DMinLon && point.getLat()<DMaxLat && point.getLat()>DMinLat)
					if(timeSpan(point.getSTime(),"060000")<0 && timeSpan(point.getSTime(),"100000")>0)
						totalD+=1;
			}
		}
		br.close();
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
	public static void main(String[] args)throws Exception{
		Config.init();
		
		File[] datePath = new File(Config.getAttr(Config.WorkPath)).listFiles();
		int days = 0;
		for(File file:datePath){
			if(days++>0)
				break;
			File stayRecordPath = new File(file.getAbsolutePath()+"\\7stayRecord");
			System.out.println(stayRecordPath.getAbsolutePath());
			File[] stayRecordFiles = stayRecordPath.listFiles();
			int k=0;
			for(File stayfile:stayRecordFiles){
				k++;
				//if((k&1)==1)
				//	continue;
				
				
				importStayRecord(stayfile);
				//extractOD();
				//exportOD("All20Days_tiantongyuan_guomao170105test.txt");
				
				
				//if(k>=40)
				//	break;
			}
		}//every day
		System.out.println("totalO="+totalO);
		System.out.println("totalD="+totalD);
		System.out.println("finish");	
	}
}