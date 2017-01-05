package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;

import Config.Config;

public class DistanceBetweenAdjacentRecords {
	public static BufferedReader br;
	public static BufferedWriter bw;
	public static DecimalFormat df = new DecimalFormat("#0.000000");
	public static double maxLon,maxLat,minLon,minLat;
	public static int idLength;
	
	public static void statDistance(File goodRecordPath)throws Exception{
		File[] files = goodRecordPath.listFiles();
		int[] disDis = new int[32];
		for(File file:files){
			System.out.println("Now stating distance "+file.getAbsolutePath());
			br = new BufferedReader(new FileReader(file));
			String af,lastAf=null;
			double lon1=0.0,lon2=0.0,lat1=0.0,lat2=0.0;
			while((af=br.readLine())!=null){
				String[] afs = af.split(",");
				lon1 = Double.valueOf(afs[5]);
				lat1 = Double.valueOf(afs[6]);
				if(afs[0].equals(lastAf)){
					//计算距离
					int dis = (int)distanceInGlobal(lon1,lat1,lon2,lat2);
					if(dis==0)
						continue;
					dis=dis/100;
					if(dis<31)
						disDis[dis]+=1;
					else
						disDis[31]+=1;
				}
				lon2 = lon1;
				lat2 = lat1;
				lastAf = afs[0];
			}
			br.close();
		}
		for(int i=0;i<disDis.length;i++){
			System.out.println(i+":"+disDis[i]);
		}
	}
	/*
	 * 统计基站抖动的距离分布
	 * 
	 */
	public static void statJitter(File goodRecordPath)throws Exception{
		File[] goodRecordFiles = goodRecordPath.listFiles();
		int[] disDis = new int[32];
		int j=0;
		for(File goodRecordFile:goodRecordFiles){
			System.out.println("Now statJittering "+goodRecordFile.getAbsolutePath());
			br = new BufferedReader(new FileReader(goodRecordFile));
			int len=0;
			String af;
			String[] afList;
			while((af=br.readLine())!=null)
				len+=1;
			br.close();
			if(len==0)
				return;
			afList = new String[len];
			br = new BufferedReader(new FileReader(goodRecordFile));
			int i=0;
			while((af=br.readLine())!=null)
				afList[i++]=af;
			br.close();
			//System.out.println("start repair");
			for(i=1;i<len-1;i++){
				String[] now = afList[i].split(",");
				String[] before = afList[i-1].split(",");
				String[] after = afList[i+1].split(",");
				if(!now[0].equals(before[0]) || !now[0].equals(after[0]))
					continue;
				double bLon = Double.parseDouble(before[5]),bLat = Double.parseDouble(before[6]);
				double nLon = Double.parseDouble(now[5]),nLat = Double.parseDouble(now[6]);
				double aLon = Double.parseDouble(after[5]),aLat = Double.parseDouble(after[6]);
				int dis1 = (int)distanceInGlobal(bLon,bLat,nLon,nLat);
				int dis2 = (int)distanceInGlobal(nLon,nLat,aLon,aLat);
				int dis3 = (int)distanceInGlobal(aLon,aLat,bLon,bLat);
				// 150km/h 约等于 41m/s
				if(dis3<100){
					if(dis1>0){
						dis1/=100;
						if(dis1<31)
							disDis[dis1]+=1;
						else
							disDis[31]+=1;
					}
					if(dis2>0){
						dis2/=100;
						if(dis2<31)
							disDis[dis2]+=1;
						else
							disDis[31]+=1;
					}
				}
			}
			if(++j>5)
				break;
		}
		//System.out.println("finish repair");
		for(int i=0;i<disDis.length;i++){
			System.out.println(i+":"+disDis[i]);
		}
	}
	/*
	 * 计算两个时间字符串的时间差,time2-time1,单位为秒
	 */
	public static int timeInterval(String time1, String time2){
		int t1=Integer.parseInt(time1.substring(0,2))*3600+Integer.parseInt(time1.substring(2,4))*60+Integer.parseInt(time1.substring(4,6));
		int t2=Integer.parseInt(time2.substring(0,2))*3600+Integer.parseInt(time2.substring(2,4))*60+Integer.parseInt(time2.substring(4,6));
		return t2-t1;
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
		//statDistance(new File(Config.getAttr(Config.GoodRecordPath)));
		statJitter(new File(Config.getAttr(Config.GoodRecordPath)));
		
	}
}
