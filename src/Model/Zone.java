package Model;

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
		maxLon = Double.MAX_VALUE;
		minLon = Double.MIN_VALUE;
		maxLat = Double.MAX_VALUE;
		minLat = Double.MIN_VALUE;
		num=0;
	}
	public Zone(int id){
		this.id = id;
		lons = new ArrayList<Double>();
		lats = new ArrayList<Double>();
		maxLon = Double.MAX_VALUE;
		minLon = Double.MIN_VALUE;
		maxLat = Double.MAX_VALUE;
		minLat = Double.MIN_VALUE;
		num=0;
	}
}
