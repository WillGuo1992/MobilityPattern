package Model;

public class Grid {
	private int id;
	private double maxLon;
	private double maxLat;
	private double minLon;
	private double minLat;
	public Grid(){
		
	}
	public Grid(int id,double maxLon,double maxLat,double minLon,double minLat){
		this.id = id;
		this.maxLon = maxLon;
		this.maxLat = maxLat;
		this.minLon = minLon;
		this.minLat = minLat;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}
	public void setMaxLon(double maxLon){
		this.maxLon= maxLon;
	}
	public double getMaxLon(){
		return this.maxLon;
	}
	public void setMaxLat(double maxLat){
		this.maxLat = maxLat;
	}
	public double getMaxLat(){
		return maxLat;
	}
	public void setMinLon(double minLon){
		this.minLon = minLon;
	}
	public double getMinLon(){
		return minLon;
	}
	public void setMinLat(double minLat){
		this.minLat = minLat;
	}
	public double getMinLat(){
		return minLat;
	}
}
