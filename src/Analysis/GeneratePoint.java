package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import Model.RawPoint;
/*
 * 用于生成散点图
 * youg
 * 20161015
 */
public class GeneratePoint {
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
	/*
	 * 计算两个时间点之间的时间差，时间格式“HHmmSS”，返回结果单位分钟
	 */
	public static int timeSpan(String a, String b){
		int span = Integer.valueOf(b.substring(0,2))*60+Integer.valueOf(b.substring(2,4));
		span = span - (Integer.valueOf(a.substring(0,2))*60+Integer.valueOf(a.substring(2,4)));
		if(span<0)
			span=-span;
		return span;
	}
	public static void main(String[] args)throws Exception{
		String input = "K:\\BJMoblieCellData2014\\20141105\\1fixed\\00.txt";
		String output = "K:\\pointGraph.csv";
		BufferedReader br= new BufferedReader(new FileReader(input));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		String af = null;
		String lastId = null, lastTime = null;
		double dis,span,lastLon=0.0,lastLat=0.0,lon=0.0,lat=0.0;
		while((af = br.readLine())!=null){
			String[] afs= af.split(",");
			if(!afs[0].equals(lastId)){
				lastId=afs[0];
				lastTime=afs[2];
				lastLon=Double.parseDouble(afs[5]);
				lastLat=Double.parseDouble(afs[6]);
				continue;
			}
			lon = Double.parseDouble(afs[5]);
			lat = Double.parseDouble(afs[6]);
			span=timeSpan(lastTime,afs[2]);
			dis=distanceInGlobal(lastLon,lastLat,lon,lat);
			if(dis<1 || span<1)
				continue;
			lastId=afs[0];
			lastTime=afs[2];
			lastLon=lon;
			lastLat=lat;
			bw.write(String.valueOf(dis)+","+String.valueOf(span)+"\n");
		}
		br.close();
		bw.close();
	}
}
