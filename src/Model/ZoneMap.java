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
	public int num;
	public double maxLon;
	public double minLon;
	public double maxLat;
	public double minLat;
	public ZoneMap(){
		zoneList = new ArrayList<Zone>();
		maxLon = Double.MIN_VALUE;
		minLon = Double.MAX_VALUE;
		maxLat = Double.MIN_VALUE;
		minLat = Double.MAX_VALUE;
		num=0;
	}
	//初始化，载入交通小区数据
	public void init()throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(".\\input\\Zone\\ZoneMap.txt"));
		System.out.println("Start init...");
		String af;
		String[] afs;
		while((af=br.readLine())!=null){
			afs = af.split(",");
			Zone newZone = new Zone(Integer.parseInt(afs[0]));
			newZone.num = Integer.parseInt(afs[4]);
			newZone.area = Double.parseDouble(afs[1]);
			newZone.length = Double.parseDouble(afs[2]);
			newZone.name = afs[3];
			for(int i=0;i<newZone.num*2;i+=2){
				double newLon = Double.parseDouble(afs[5+i]);
				double newLat = Double.parseDouble(afs[6+i]);
				newZone.lons.add(newLon);
				newZone.lats.add(newLat);
				if(newLon>newZone.maxLon)
					newZone.maxLon = newLon;
				if(newLon<newZone.minLon)
					newZone.minLon = newLon;
				if(newLat>newZone.maxLat)
					newZone.maxLat = newLat;
				if(newLat<newZone.minLat)
					newZone.minLat = newLat;
				if(newLon>this.maxLon)
					this.maxLon = newLon;
				if(newLon<this.minLon)
					this.minLon = newLon;
				if(newLat>this.maxLat)
					this.maxLat = newLat;
				if(newLat<this.minLat)
					this.minLat = newLat;
			}
			this.zoneList.add(newZone);
			num+=1;
		}
		br.close();
		System.out.println("Finish init...");
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
		if(lon>this.maxLon || lon<this.minLon || lat>this.maxLat || lat<this.minLat)
			return null;
		for(Zone zone:zoneList)
			if(zone.Contains(lon, lat))
				return zone;
		return null;
	}
}
