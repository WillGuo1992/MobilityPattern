package Model;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

/*
 * 交通小区类
 */
public class Zone {
	public int id;
	public String name;
	public double length;
	public double area;
	public double maxLon;
	public double minLon;
	public double maxLat;
	public double minLat;
	public int num;
	public List<Double> lons;
	public List<Double> lats;
	public Zone(){
		lons = new ArrayList<Double>();
		lats = new ArrayList<Double>();
		maxLon = Double.MIN_VALUE;
		minLon = Double.MAX_VALUE;
		maxLat = Double.MIN_VALUE;
		minLat = Double.MAX_VALUE;
		num=0;
	}
	public Zone(int id){
		this.id = id;
		lons = new ArrayList<Double>();
		lats = new ArrayList<Double>();
		maxLon = Double.MIN_VALUE;
		minLon = Double.MAX_VALUE;
		maxLat = Double.MIN_VALUE;
		minLat = Double.MAX_VALUE;
		num=0;
	}
	//判断点是否在区域内
	public boolean Contains(double lon,double lat){
		if(lon>maxLon || lon < minLon || lat>maxLat || lat<minLat){
			return false;
		}
		if(lons!=null && lons.size()>0){
			int[] xpoints = new int[lons.size()];
			int[] ypoints = new int[lats.size()];
			for(int i=0;i<xpoints.length;i++){
				xpoints[i]=(int)(lons.get(i)*10000000);
				ypoints[i]=(int)(lats.get(i)*10000000);
			}
			Polygon polygon = new Polygon(xpoints,ypoints,xpoints.length);
			return polygon.contains(lon*10000000,lat*10000000);
		}
		return false;
	}
}
