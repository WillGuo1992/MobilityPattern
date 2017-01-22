package Analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

import Model.Grid;
import Model.GridMap;

public class MapCreate {
	//第一级网格范围
	public static int L1MinLon = 11621;
	public static int L1MaxLon = 11654;
	public static int L1MinLat = 3978;
	public static int L1MaxLat = 4002;
	public static int L1Step = 1;
	//第二级网格范围
	public static int L2MinLon = 11609;
	public static int L2MaxLon = 11672;
	public static int L2MinLat = 3969;
	public static int L2MaxLat = 4023;
	public static int L2Step = 3;
	//第三级网格范围
	public static int L3MinLon = 11537;
	public static int L3MaxLon = 11745;
	public static int L3MinLat = 3942;
	public static int L3MaxLat = 4111;
	public static int L3Step = 9;
	
	public static GridMap map;
	public static BufferedWriter bw;
	public static Set<Integer> UseLess = new HashSet<Integer>();
	public static int[] a={
	//一级
	10001,10002,10003,10004,10005,10006,10007,10008,10009,10025,10026,10027,10028,10029,10030,10031,10032,
	10049,10050,10051,10052,10053,10054,10055,10073,10074,10075,10076,10077,10078,10097,10098,10099,10100,10101,10121,10122,10123,10124,10145,
	10553,10557,10601,10625,10626,10649,10650,10651,
	10673,10674,10675,10676,
	10697,10698,10699,10700,
	10721,10722,10723,10724,10725,
	10745,10746,10747,10748,10749,
	10769,10770,10771,10772,10773,10774,
	10018,10019,10020,10021,10022,10023,10024,
	10046,10047,10048,
	10071,10072,
	10096,10120,10144,10168,10720,10743,10744,
	10766,10767,10768,
	10788,10789,10790,10791,10792,
	//二级
	20001,
	20004,20005,20006,20007,20008,20009,
	20025,20026,
	20012,20013,20014,20015,20016,20017,20018,
	20032,20033,20034,20035,20036,
	20053,20054,20071,20072,20081,20082,20091,20092,
	20102,20112,20121,20122,20131,20132,20141,20142,20151,20152,20161,20162,20171,20172,20181,20182,
	20199,20200,20217,20218,20235,20236,
	20250,20251,20252,20253,20254,20257,
	20267,20268,20269,20270,20271,20272,
	20284,20285,20286,20287,20288,20289,20290,
	20123,20133,20143,20153,20163,20173,20174,20183,20184,20201,20202,20219,20220,
	20237,20238,20239,20240,
	20255,20256,20267,20258,
	20273,20274,20275,20276,20277,
	//三级
	30001,30002,30003,30004,30005,30006,
	30008,30009,30010,30011,30012,30013,30014,30015,30016,30017,30018,30019,30020,30021,30024,
	30027,30028,30029,30030,30031,30032,30033,30034,30035,30036,30037,30038,30039,30040,
	30047,30048,30049,30050,30051,30052,30053,30054,30055,30056,30057,30058,30059,
	30066,30067,30068,30069,30070,30071,30072,30073,30074,30075,30076,30077,30086,30087,
	30090,30091,30092,30093,30094,30095,30096,30105,
	30110,30111,30112,30113,30114,30115,30116,
	30129,30130,30131,30132,30133,30134,30135,
	30147,30148,30149,30150,30151,30152,30153,30154,
	30161,30162,30163,30164,30165,30166,
	30175,30176,30177,30178,30188,30189,30190,30191,
	30203,30204,30217,30230,30243,30261,30262,30244,
	30245,30231,30232,30218,30219,30205,30263,30264,
	30279,30280,30281,30282,30283,30284,
	30297,30298,30299,30300,30301,30302,
	30316,30317,30318,30319,
	30320,30321,30335,30336,30337,30338,
	30339,30340,30341,30342,30353,30354,30355,30356,30357,
	30358,30359,30360,30361,30362,30363,30364,30365,
	30368,30369,30370,30372,30373,30374,30375,30376,
	30377,30378,30379,30380,30381,30382,30383,30384,
	30386,30387,30388,30389,30391,30392,30393,30394,30395,
	30396,30397,30398,30399,30400,30401,30402,30403,30404,30405,30406,30407,30408,
	30410,30411,30412,30413,30414
	};
	/*
	 * 生成GridMap
	 */
	public static void CreateMap(){
		map = new GridMap();
		int L1id=10000;
		for(int i=L1MinLon;i<L1MaxLon;i+=L1Step)
			for(int j=L1MinLat;j<L1MaxLat;j+=L1Step){
				Grid grid = new Grid(++L1id,i/100.0,j/100.0,(i+L1Step)/100.0,(j+L1Step)/100.0);
				map.getL1Grids().add(grid);
			}
		int L2id=20000;
		for(int i=L2MinLon;i<L2MaxLon;i+=L2Step)
			for(int j=L2MinLat;j<L2MaxLat;j+=L2Step){
				//判断是否在L1区间内，如果在，则不生成L2网格
				if(i>=L1MinLon && i<L1MaxLon && j>=L1MinLat && j<L1MaxLat)
					continue;
				Grid grid = new Grid(++L2id,i/100.0,j/100.0,(i+L2Step)/100.0,(j+L2Step)/100.0);
				map.getL2Grids().add(grid);
			}
		int L3id=30000;
		for(int i=L3MinLon;i<L3MaxLon;i+=L3Step)
			for(int j=L3MinLat;j<L3MaxLat;j+=L3Step){
				//判断是否在L2区间内，如果在，则不生成L3网格
				if(i>=L2MinLon && i<L2MaxLon && j>=L2MinLat && j<L2MaxLat)
					continue;
				Grid grid = new Grid(++L3id,i/100.0,j/100.0,(i+L3Step)/100.0,(j+L3Step)/100.0);
				map.getL3Grids().add(grid);
			}
		/*
		int L4id=40000;
		for(int i=L1MinLon;i<L1MaxLon;i+=L2Step)
			for(int j=L1MinLat;j<L1MaxLat;j+=L2Step){
				//判断是否在L1区间内，如果在，则不生成L2网格
				//if(i>=L1MinLon && i<L1MaxLon && j>=L1MinLat && j<L1MaxLat)
				//	continue;
				Grid grid = new Grid(++L2id,i/100.0,j/100.0,(i+L2Step)/100.0,(j+L2Step)/100.0);
				map.getL2Grids().add(grid);
			}
		int L5id=50000;
		for(int i=L2MinLon;i<L2MaxLon;i+=L3Step)
			for(int j=L2MinLat;j<L2MaxLat;j+=L3Step){
				//判断是否在L2区间内，如果在，则不生成L3网格
				//if(i>=L2MinLon && i<L2MaxLon && j>=L2MinLat && j<L2MaxLat)
				//	continue;
				Grid grid = new Grid(++L3id,i/100.0,j/100.0,(i+L3Step)/100.0,(j+L3Step)/100.0);
				map.getL3Grids().add(grid);
			}
		*/
		System.out.println(L2id+L1id-30000);
		System.out.println("finish create map");
	}
	/*
	 * 导出到CSV格式文件，供程序使用
	 * 格式：
	 * id,minLon,minLat,maxLon,maxLat;
	 */
	public static void exportCSV(File csvFile)throws Exception{
		bw = new BufferedWriter(new FileWriter(csvFile));
		for(Grid grid:map.getL1Grids())
			bw.write(grid.getId()+","+grid.getMinLon()+","+grid.getMinLat()+","+grid.getMaxLon()+","+grid.getMaxLat()+"\n");
		for(Grid grid:map.getL2Grids())
			bw.write(grid.getId()+","+grid.getMinLon()+","+grid.getMinLat()+","+grid.getMaxLon()+","+grid.getMaxLat()+"\n");
		bw.close();
		System.out.println("finish export CSV");
	}
	/*
	 * 导出到json文件供地图显示
	 * 格式：
	 */
	public static void exportJSON(File jsonFile)throws Exception{
		bw = new BufferedWriter(new FileWriter(jsonFile));
		bw.write("var tazData={\n\t\"type\":\"FeatureCollection\",\n\t\"features\":[\n");
		for(Grid grid:map.getL3Grids()){
			//if(UseLess.contains(grid.getId()))
			//	continue;
			bw.write("\t{\n\t\t\"type\":\"Feature\",\n\t\t\"properties\":{\n");
			bw.write("\t\t\t\"id\":"+grid.getId()+",\n\t\t\t\"name\":\""+grid.getId()+"\",\n\t\t\t\"theme\": \"Home\",\n\t\t\t\"color\": \"#FFFFFF\"\n\t\t},\n");
			bw.write("\t\t\"geometry\": {\n\t\t\t\"type\": \"Polygon\",\n\t\t\t\"coordinates\": [[\n");
			bw.write("\t\t\t["+grid.getMinLon()+","+grid.getMinLat()+"],\n");
			bw.write("\t\t\t["+grid.getMinLon()+","+grid.getMaxLat()+"],\n");
			bw.write("\t\t\t["+grid.getMaxLon()+","+grid.getMaxLat()+"],\n");
			bw.write("\t\t\t["+grid.getMaxLon()+","+grid.getMinLat()+"],\n");
			bw.write("\t\t\t["+grid.getMinLon()+","+grid.getMinLat()+"]\n");
			bw.write("\t\t\t]]\n\t\t}\n\t},\n");
		}
		for(Grid grid:map.getL2Grids()){
			//if(UseLess.contains(grid.getId()))
			//	continue;
			bw.write("\t{\n\t\t\"type\":\"Feature\",\n\t\t\"properties\":{\n");
			bw.write("\t\t\t\"id\":"+grid.getId()+",\n\t\t\t\"name\":\""+grid.getId()+"\",\n\t\t\t\"theme\": \"Home\",\n\t\t\t\"color\": \"#FFFFFF\"\n\t\t},\n");
			bw.write("\t\t\"geometry\": {\n\t\t\t\"type\": \"Polygon\",\n\t\t\t\"coordinates\": [[\n");
			bw.write("\t\t\t["+grid.getMinLon()+","+grid.getMinLat()+"],\n");
			bw.write("\t\t\t["+grid.getMinLon()+","+grid.getMaxLat()+"],\n");
			bw.write("\t\t\t["+grid.getMaxLon()+","+grid.getMaxLat()+"],\n");
			bw.write("\t\t\t["+grid.getMaxLon()+","+grid.getMinLat()+"],\n");
			bw.write("\t\t\t["+grid.getMinLon()+","+grid.getMinLat()+"]\n");
			bw.write("\t\t\t]]\n\t\t}\n\t},\n");
		}
		for(Grid grid:map.getL1Grids()){
			//if(UseLess.contains(grid.getId()))
			//	continue;
			bw.write("\t{\n\t\t\"type\":\"Feature\",\n\t\t\"properties\":{\n");
			bw.write("\t\t\t\"id\":"+grid.getId()+",\n\t\t\t\"name\":\""+grid.getId()+"\",\n\t\t\t\"theme\": \"Home\",\n\t\t\t\"color\": \"#FFFFFF\"\n\t\t},\n");
			bw.write("\t\t\"geometry\": {\n\t\t\t\"type\": \"Polygon\",\n\t\t\t\"coordinates\": [[\n");
			bw.write("\t\t\t["+grid.getMinLon()+","+grid.getMinLat()+"],\n");
			bw.write("\t\t\t["+grid.getMinLon()+","+grid.getMaxLat()+"],\n");
			bw.write("\t\t\t["+grid.getMaxLon()+","+grid.getMaxLat()+"],\n");
			bw.write("\t\t\t["+grid.getMaxLon()+","+grid.getMinLat()+"],\n");
			bw.write("\t\t\t["+grid.getMinLon()+","+grid.getMinLat()+"]\n");
			bw.write("\t\t\t]]\n\t\t}\n\t},\n");
		}
		bw.write("\t]\n};");
		bw.close();
		System.out.println("finish export JSON");
	}
	public static void main(String[] args)throws Exception{
		CreateMap();
		for(int i=0;i<a.length;i++)
			UseLess.add(a[i]);
		exportCSV(new File("E:\\Java\\TravelPattern\\MobilityPattern\\input\\Zone\\GridMap.csv"));
		//exportJSON(new File("E:\\DataVisual\\POIforSEEv160130\\json\\Afterchangetranszone.json"));
	}
}
