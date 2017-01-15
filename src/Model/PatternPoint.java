package Model;

import java.util.LinkedList;
import java.util.List;


public class PatternPoint{
	private double dis;//路径通行距离
	private int time;//路径通行时间
	private double vel;//路径速度
	private double ratio;//路径使用率
	private int type;//路径类型0:暂未标识；1：早高峰型；2：晚高峰型；3：均衡型
	public PatternPoint(){
		
	}
	public PatternPoint(double dis,int time,double vel,double ratio,int type){
		this.dis = dis;
		this.time = time;
		this.vel = vel;
		this.ratio = ratio;
		this.type = type;
	}
	public void setDis(double dis){
		this.dis = dis;
	}
	public double getDis(){
		return dis;
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
	public void setRatio(double ratio){
		this.ratio = ratio;
	}
	public double getRatio(){
		return ratio;
	}
	public void setType(int type){
		this.type = type;
	}
	public int getType(){
		return type;
	}
}

