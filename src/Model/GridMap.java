package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import Config.Config;

public class GridMap {
	private List<Grid> L1grids;
	private List<Grid> L2grids;
	private List<Grid> L3grids;
	private double L1minLon,L1minLat,L1maxLon,L1maxLat;
	private double L2minLon,L2minLat,L2maxLon,L2maxLat;
	private double L3minLon,L3minLat,L3maxLon,L3maxLat;

	public GridMap(){
		L1grids = new ArrayList<Grid>();
		L2grids = new ArrayList<Grid>();
		L3grids = new ArrayList<Grid>();
	}
	//初始化，载入网格数据
	public void init()throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(".\\input\\Zone\\GridMap.csv"));
		String af;
		String[] afs;
		while((af=br.readLine())!=null){
			afs=af.split(",");
			int id = Integer.parseInt(afs[0]);
			if(id/10000==1){
				L1grids.add(new Grid(id,Double.parseDouble(afs[1]),Double.parseDouble(afs[2]),Double.parseDouble(afs[3]),Double.parseDouble(afs[4]),Integer.parseInt(afs[5])));
			}else if(id/10000==2){
				L2grids.add(new Grid(id,Double.parseDouble(afs[1]),Double.parseDouble(afs[2]),Double.parseDouble(afs[3]),Double.parseDouble(afs[4]),Integer.parseInt(afs[5])));
			}else if(id/10000==3){
				L3grids.add(new Grid(id,Double.parseDouble(afs[1]),Double.parseDouble(afs[2]),Double.parseDouble(afs[3]),Double.parseDouble(afs[4]),Integer.parseInt(afs[5])));
			}
		}
		br.close();
	}
	public List<Grid> getL1Grids(){
		return L1grids;
	}
	public List<Grid> getL2Grids(){
		return L2grids;
	}
	public List<Grid> getL3Grids(){
		return L3grids;
	}
	//通过ID找到网格
	public Grid getGridById(int gridId){
		if(gridId/10000==1){
			for(Grid grid:L1grids)
				if(grid.getId()==gridId)
					return grid;
		}else if(gridId/10000==2){
			for(Grid grid:L2grids)
				if(grid.getId()==gridId)
					return grid;
		}else if(gridId/1000==3){
			for(Grid grid:L3grids)
				if(grid.getId()==gridId)
					return grid;
		}
		return null;
	}
	//通过经纬度找到网格
	public Grid getGridByPos(double lon,double lat){
		
		return null;
	}
}
