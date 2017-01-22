package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;

import Config.Config;

public class DistanceBetweenAdjacentPassbyPoint {
	public static BufferedReader br;
	public static BufferedWriter bw;
	public static DecimalFormat df = new DecimalFormat("#0.000000");
	public static double maxLon,maxLat,minLon,minLat;
	public static int idLength;
	/*
	 * 计算相邻移动点间的距离
	 */
	public static void statDistance(File goodRecordPath)throws Exception{
		File[] files = goodRecordPath.listFiles();
		int[] disDis = new int[102];
		for(File file:files){
			System.out.println("Now stating distance "+file.getAbsolutePath());
			br = new BufferedReader(new FileReader(file));
			String af,lastAf=null;
			int lastState=-1,state;
			double lon1=0.0,lon2=0.0,lat1=0.0,lat2=0.0;
			while((af=br.readLine())!=null){
				String[] afs = af.split(",");
				state=Integer.parseInt(afs[5]);
				lon1 = Double.valueOf(afs[3]);
				lat1 = Double.valueOf(afs[4]);
				if(state==0 && lastState==0 && afs[0].equals(lastAf)){
					//计算距离
					int dis = (int)distanceInGlobal(lon1,lat1,lon2,lat2);
					if(dis>=0){
						dis=dis/1000;
						if(dis<101)
							disDis[dis]+=1;
						else
							disDis[101]+=1;
					}
				}
				lon2 = lon1;
				lat2 = lat1;
				lastState=state;
				lastAf = afs[0];
			}
			br.close();
			break;
		}
		for(int i=0;i<disDis.length;i++){
			System.out.println(i+":"+disDis[i]);
		}
	}
	/*
	 * 统计相邻移动点之间的时间间隔
	 */
	public static void statTimeInterval(File stayRecordPath)throws Exception{
		File[] files = stayRecordPath.listFiles();
		int[] disDis = new int[102];
		for(File file:files){
			System.out.println("Now stating distance "+file.getAbsolutePath());
			br = new BufferedReader(new FileReader(file));
			String af,lastID=null;
			//double lon1=0.0,lon2=0.0,lat1=0.0,lat2=0.0;
			int lastTime=Integer.MAX_VALUE,time;
			int lastState=-1,state;
			while((af=br.readLine())!=null){
				String[] afs = af.split(",");
				state=Integer.parseInt(afs[5]);
				time=Integer.parseInt(afs[2].substring(7,9))*3600+Integer.parseInt(afs[2].substring(9,11))*60+Integer.parseInt(afs[2].substring(11,13));
				if(state==0 && lastState==0 && afs[0].equals(lastID)){
					//计算时间间隔
					int dis = (time-lastTime)/60;//(int)distanceInGlobal(lon1,lat1,lon2,lat2);
					if(dis>=0){
						dis=dis;
						if(dis<101)
							disDis[dis]+=1;
						else
							disDis[101]+=1;
					}
				}
				lastTime=time;
				lastState=state;
				lastID = afs[0];
			}
			br.close();
			break;
		}
		for(int i=0;i<disDis.length;i++){
			System.out.println(i+":"+disDis[i]);
		}
	}
	/*
	 * 计算两位置之间的距离，根据球面坐标长度公式计算(单位：米)
	 * 注意，这个计算很耗时间,另外,这个计算把经纬度的100万倍还原了!
	 */
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
		maxLon = Double.valueOf(Config.getAttr(Config.CityMaxLon));
		minLon = Double.valueOf(Config.getAttr(Config.CityMinLon));
		maxLat = Double.valueOf(Config.getAttr(Config.CityMaxLat));
		minLat = Double.valueOf(Config.getAttr(Config.CityMinLat));
		idLength = Integer.valueOf(Config.getAttr(Config.IdLength));
		//statDistance(new File(Config.getAttr(Config.StayRecordPath)));
		//statTimeInterval(new File(Config.getAttr(Config.StayRecordPath)));
		//System.out.println(distanceInGlobal(116.40,40.98,116.49,40.98));
	}
}
