package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/*
 * 交通小区类
 */
public class ZoneMap {
	public List<Zone> zoneList;
	public double maxLon;
	public double minLon;
	public double maxLat;
	public double minLat;
	public ZoneMap(){
		zoneList = new ArrayList<Zone>();
	}
	//初始化，载入交通小区数据
	public void init()throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(".\\input\\Zone\\ZoneMap.txt"));
		String af;
		String[] afs;
		while((af=br.readLine())!=null){
			
		}
		br.close();
	}
	public List<Zone> getZoneList(){
		return zoneList;
	}
	//通过ID找到网格
	public Zone getZoneById(int zoneId){
			for(Zone zone:zoneList)
				if(zone.id==zoneId)
					return zone;
		return null;
	}
	//通过经纬度找到所在网格
	public Zone getZoneByPos(double lon,double lat){
		for(Zone zone:zoneList)
			if(lon>=zone.lons.get(0) && lon<=zone.lons.get(1) && lat>=zone.lats.get(0) && lat<=zone.lats.get(1))
				return zone;
		return null;
	}
}
