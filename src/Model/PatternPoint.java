package Model;

import java.util.LinkedList;
import java.util.List;


public class PatternPoint{
	private double dis;//路径通行距离，单位：km
	private int time;//路径通行时长，单位：min
	public int[] timeSpan = new int[24];//时间分布
	private double vel;//路径速度，单位：km/h
	public double ratio;//路径使用率
	private int timeType;//时间分布类型0:暂未标识；1：早高峰型；2：晚高峰型；3：均衡型
	private int velType;//速度类型0：暂未标识；1：拥堵；2：缓慢；3：畅通
	private int type;//路径选择偏好类型
	public PatternPoint(){
		
	}
	public void setDis(double dis){
		this.dis = dis;
	}
	public double getDis(){
		return this.dis;
	}
	public void setTime(int time){
		this.time = time;
	}
	public int getTime(){
		return time;
	}
	public void setVel(double vel){
		this.vel = vel;
	}
	public double getVel(){
		return vel;
	}
	public void setTimeType(int timeType){
		this.timeType = timeType;
	}
	public int getTimeType(){
		return timeType;
	}
	public void setVelType(int velType){
		this.velType = velType;
	}
	public int getVelType(){
		return velType;
	}
	public void setType(int type){
		this.type = type;
	}
	public int getType(){
		return type;
	}
}

