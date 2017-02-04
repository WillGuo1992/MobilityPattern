package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//出行模式点记录
public class PatternRecord{
	private Grid origin,destination;
	private int routeNum;//路径选择偏好数量
	private List<PatternPoint> routes_pref;
	public PatternRecord(){
		
	}
	public PatternRecord(Grid origin,Grid destination){
		this.origin = origin;
		this.destination = destination;
		routes_pref = new ArrayList<PatternPoint>();
	}
	public void setOrigin(Grid origin){
		this.origin = origin;
	}
	public Grid getOrigin(){
		return origin;
	}
	public void setDestination(Grid destination){
		this.destination = destination;
	}
	public Grid getDestination(){
		return destination;
	}
	public void setRouteNum(int routeNum){
		this.routeNum = routeNum;
	}
	public int getRouteNum(){
		return routeNum;
	}
	public List<PatternPoint> getRoutes_pref(){
		return routes_pref;
	}
}