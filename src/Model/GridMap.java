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
	public int L1Step,L2Step,L3Step;
	public double L1minLon=Double.MAX_VALUE,L1minLat=Double.MAX_VALUE,L1maxLon=Double.MIN_VALUE,L1maxLat=Double.MIN_VALUE;
	public double L2minLon=Double.MAX_VALUE,L2minLat=Double.MAX_VALUE,L2maxLon=Double.MIN_VALUE,L2maxLat=Double.MIN_VALUE;
	public double L3minLon=Double.MAX_VALUE,L3minLat=Double.MAX_VALUE,L3maxLon=Double.MIN_VALUE,L3maxLat=Double.MIN_VALUE;

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
			double minLon = Double.parseDouble(afs[1]);
			double minLat = Double.parseDouble(afs[2]);
			double maxLon = Double.parseDouble(afs[3]);
			double maxLat = Double.parseDouble(afs[4]);
			int step = Integer.parseInt(afs[5]);
			if(id/10000==1){
				L1Step = step;
				L1minLon = Math.min(L1minLon, minLon);
				L1minLat = Math.min(L1minLat, minLat);
				L1maxLon = Math.max(L1maxLon, maxLon);
				L1maxLat = Math.max(L1maxLat, maxLat);
				L1grids.add(new Grid(id,minLon,minLat,maxLon,maxLat,step));
			}else if(id/10000==2){
				L2Step = step;
				L2minLon = Math.min(L2minLon, minLon);
				L2minLat = Math.min(L2minLat, minLat);
				L2maxLon = Math.max(L2maxLon, maxLon);
				L2maxLat = Math.max(L2maxLat, maxLat);
				L2grids.add(new Grid(id,minLon,minLat,maxLon,maxLat,step));
			}else if(id/10000==3){
				L3Step = step;
				L3minLon = Math.min(L3minLon, minLon);
				L3minLat = Math.min(L3minLat, minLat);
				L3maxLon = Math.max(L3maxLon, maxLon);
				L3maxLat = Math.max(L3maxLat, maxLat);
				L3grids.add(new Grid(id,minLon,minLat,maxLon,maxLat,step));
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
	//通过经纬度找到所在网格
	public Grid getGridByPos(double lon,double lat){
		if(lon>=L1minLon && lon<=L1maxLon && lat>=L1minLat && lat<=L1maxLat){
			for(Grid grid:L1grids)
				if(lon>=grid.minLon && lon<=grid.maxLon && lat>=grid.minLat && lat<=grid.maxLat)
					return grid;
		}else if(lon>=L2minLon && lon<=L2maxLon && lat>=L2minLat && lat<=L2maxLat){
			for(Grid grid:L2grids)
				if(lon>=grid.minLon && lon<=grid.maxLon && lat>=grid.minLat && lat<=grid.maxLat)
					return grid;
		}else if(lon>=L3minLon && lon<=L3maxLon && lat>=L3minLat && lat<=L3maxLat){
			for(Grid grid:L3grids)
				if(lon>=grid.minLon && lon<=grid.maxLon && lat>=grid.minLat && lat<=grid.maxLat)
					return grid;
		}
		return null;
	}
}
