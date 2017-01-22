package Model;

import java.util.ArrayList;
import java.util.List;

public class GridMap {
	private List<Grid> L1grids;
	private List<Grid> L2grids;
	private List<Grid> L3grids;

	private List<Grid> L4grids;
	private List<Grid> L5grids;
	public GridMap(){
		L1grids = new ArrayList<Grid>();
		L2grids = new ArrayList<Grid>();
		L3grids = new ArrayList<Grid>();
		L4grids = new ArrayList<Grid>();
		L5grids = new ArrayList<Grid>();
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
	public List<Grid> getL4Grids(){
		return L4grids;
	}
	public List<Grid> getL5Grids(){
		return L5grids;
	}
}
