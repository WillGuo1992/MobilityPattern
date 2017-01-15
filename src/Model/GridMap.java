package Model;

import java.util.ArrayList;
import java.util.List;

public class GridMap {
	private List<Grid> grids;
	public GridMap(){
		grids = new ArrayList<Grid>();
	}
	public List<Grid> getGrids(){
		return grids;
	}
}
