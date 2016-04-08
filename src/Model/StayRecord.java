package Model;

import java.util.LinkedList;
import java.util.List;


//�û�ͣ�����¼
public class StayRecord{
	public String id;
	public String date;
	public List<StayPoint> stayPoints;
	public StayRecord(){
		
	}
	public StayRecord(String id, String date){
		this.id = id;
		this.date = date;
		stayPoints = new LinkedList<StayPoint>();
	}
}