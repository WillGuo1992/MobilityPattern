package ODMatrix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
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
 * date:20170123
 * 构建OD总流量矩阵
 */
public class ODFlow {
	public static DecimalFormat df = new DecimalFormat("#.000000");
	public static GridMap map = new GridMap();
	
	public static int gridNum;
	public static int[] gridList;
	public static Map<Integer,Integer> gridMap = new HashMap<Integer,Integer>();
	
	public static List<StayRecord> stayRecords = new LinkedList<StayRecord>();
	
	public static int[][] ODflow;
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
	 * 统计OD总流量
	 */
	public static void calODflow(){
		System.out.println("Now caling ODflow");
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
					ODflow[gridMap.get(oGrid.getId())][gridMap.get(dGrid.getId())]+=1;
				}
				lastPoint = point;
			}
		}
	}
	/*
	 * 输出OD总流量
	 */
	public static void exportODflow(String fileName)throws Exception{
		System.out.println("Now exporting ODflow");
		BufferedWriter bw;
		int total=0,moreThan10Total=0;
		/*
		bw = new BufferedWriter(new FileWriter(fileName+"ODflowMatrix.csv"));
		for(int i=0;i<gridNum;i++)
			bw.write(","+gridList[i]);
		bw.write("\n");
		for(int i=0;i<gridNum;i++){
			bw.write(""+gridList[i]);
			for(int j=0;j<gridNum;j++)
				bw.write(","+ODflow[i][j]);
			bw.write("\n");
		}
		bw.close();
		*/
		bw = new BufferedWriter(new FileWriter(fileName+"ODflowList.csv"));
		for(int i=0;i<gridNum;i++)
			for(int j=0;j<gridNum;j++){
				total+=ODflow[i][j];
				double dis = distanceInGlobal((map.getGridById(gridList[i]).minLon+map.getGridById(gridList[i]).maxLon)/2.0,(map.getGridById(gridList[i]).minLat+map.getGridById(gridList[i]).maxLat)/2.0,(map.getGridById(gridList[j]).minLon+map.getGridById(gridList[j]).maxLon)/2.0,(map.getGridById(gridList[j]).minLat+map.getGridById(gridList[j]).maxLat)/2.0);
				if(dis>10000){
					bw.write(gridList[i]+","+gridList[j]+","+ODflow[i][j]+","+df.format(dis)+"\n");
					moreThan10Total+=ODflow[i][j];
				}
			}
		bw.close();
		System.out.println("Total:"+total);
		System.out.println("MoreThan10:"+moreThan10Total);
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
	public static void main(String[] args)throws Exception{
		Config.init();
		//地图初始化及网格顺序号生成过程-------------------
		map.init();
		gridNum = map.getL1Grids().size()+map.getL2Grids().size()+map.getL3Grids().size();
		gridList = new int[gridNum];
		ODflow = new int[gridNum][gridNum];
		int n = 0;
		for(Grid grid:map.getL1Grids())
			gridList[n++]=grid.getId();
		for(Grid grid:map.getL2Grids())
			gridList[n++]=grid.getId();
		for(Grid grid:map.getL3Grids())
			gridList[n++]=grid.getId();
		for(int i=0;i<gridList.length;i++)
			gridMap.put(gridList[i], i);
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
				calODflow();
				//if(k++>=40)
				//	break;
			}//every file
			//if(days++>0)
				//break;
		}//every day
		exportODflow("K:\\ODmatrix\\");
		System.out.println("finish");
	}
}
