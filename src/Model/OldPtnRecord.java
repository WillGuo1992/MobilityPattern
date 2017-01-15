package Model;

import java.util.LinkedList;
import java.util.List;
//老版本定义的模式记录
@Deprecated
public class OldPtnRecord {
	private String id;
	private List<OldPtnPoint> normalPoints;
	private List<OldPtnPoint> dynamicPoints;
	public OldPtnRecord(){
		
	}
	public OldPtnRecord(String id){
		this.id = id;
		normalPoints = new LinkedList<OldPtnPoint>();
		dynamicPoints = new LinkedList<OldPtnPoint>();
	}
	public void setId(String id){
		this.id = id;
	}
	public String getId(){
		return this.id;
	}
	public List<OldPtnPoint> getNormalPoints(){
		return this.normalPoints;
	}
	public List<OldPtnPoint> getDynamicPoints(){
		return this.dynamicPoints;
	}
	@Override
	public String toString(){
		String ans=id+"\n";
		ans+="normalPoints:\n";
		for(OldPtnPoint np:normalPoints){
			ans+=np.toString()+"\n";
		}
		ans+="dynamicPoints:\n";
		for(OldPtnPoint dp:dynamicPoints){
			ans+=dp.toString()+"\n";
		}
		return ans;
	}
}
