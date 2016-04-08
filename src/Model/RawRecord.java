package Model;

import java.util.LinkedList;
import java.util.List;


//�û�ԭʼ��¼
public class RawRecord{
	public String id;
	public String date;
	public List<RawPoint> rawPoints;
	public RawRecord(){
		
	}
	public RawRecord(String id, String date){
		this.id = id;
		this.date = date;
		rawPoints = new LinkedList<RawPoint>();
	}
}
