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
	public int n;
	public List<Double> lons;
	public List<Double> lats;
	public Zone(){
		lons = new ArrayList<Double>();
		lats = new ArrayList<Double>();
	}
	public Zone(int id){
		this.id = id;
		lons = new ArrayList<Double>();
		lats = new ArrayList<Double>();
	}
}
