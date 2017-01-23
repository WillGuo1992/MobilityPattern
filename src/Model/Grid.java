package Model;

public class Grid {
	private int id;
	public double maxLon;
	public double maxLat;
	public double minLon;
	public double minLat;
	public int step;//网格步长，1:0.01经纬度；3： 0.03经纬度；9： 0.09经纬度
	public Grid(){
		
	}
	public Grid(int id,double minLon,double minLat,double maxLon,double maxLat,int step){
		this.id = id;
		this.maxLon = maxLon;
		this.maxLat = maxLat;
		this.minLon = minLon;
		this.minLat = minLat;
		this.step = step;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}
	/*
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
	public void setStep(int step){
		this.step = step;
	}
	public int getStep(){
		return step;
	}
	*/
}
