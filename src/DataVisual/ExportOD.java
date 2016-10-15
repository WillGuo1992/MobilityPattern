package DataVisual;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import Config.Config;
import DBSCANforTrip.Cluster;
import DBSCANforTrip.ClusterAnalysis;
import DBSCANforTrip.DataPoint;
import Model.StayRecord;

/*
 * author:youg
 * date:20161013
 * 生成可视化OD部分的所需json文件。
 * 输入：聚类结果文件夹
 * 输出：json文件
 * 0raw:原始信令数据，不包含经纬度信息，按时间划分文件
 * 1fixed:添加经纬度信息，按id后两位划分文件，文件内按id和时间排序
 * 2timeSpan:记录每个ID每天最早出现的时间和位置以及最晚出现的时间和位置
 * 3timeLine：以15分钟为单位统计每个ID在每个时间段出现的次数
 * 4goodUser:数据质量好、用于下一步分析的用户ID列表。提取规则：7点前、19点后有记录，7-19点每3个小时有记录的用户数所占比例；用户比例：55%
 * 5goodRecord:4goodUser列表里的用户的完整记录，按id后两位分割到不同文件中
 * 7stayRecord:从5goodRecord中提取出的用户停留点记录
 */
public class ExportOD {
	public static double midOLon=0.0,midOLat=0.0,midDLat=0.0,midDLon=0.0;
	public static int ONum=0,DNum=0;
	public static BufferedReader br;
	public static BufferedWriter bw;
	public static int label=0;
	public static boolean isFirstFile=true,isFirstUser=false,isFirstLine=false;
	public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	public static String LocToBaidu(String lon,String lat){
		double x = Double.valueOf(lon);
		double y = Double.valueOf(lat);
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);  
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);  
	    double bd_lon = z * Math.cos(theta) + 0.0065;  
	    double bd_lat = z * Math.sin(theta) + 0.006;
	    String ans = String.valueOf(bd_lon)+","+String.valueOf(bd_lat);
	    return ans;
	}
	public static String LocToBaidu(double lon,double lat){
		double x = lon;
		double y = lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);  
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);  
	    double bd_lon = z * Math.cos(theta) + 0.0065;  
	    double bd_lat = z * Math.sin(theta) + 0.006;
	    String ans = String.valueOf(bd_lon)+","+String.valueOf(bd_lat);
	    return ans;
	}
	public static void BeforeExport(File output)throws Exception{
		bw = new BufferedWriter(new FileWriter(output,true));
		bw.write("{\n");
		bw.write("\"routes\":[\n");
		bw.close();
	}
	public static void Export(File input,File output)throws Exception{
		br = new BufferedReader(new FileReader(input));
		bw = new BufferedWriter(new FileWriter(output,true));
		if(isFirstFile){
			bw.write("\n[\n");
			isFirstFile=false;
		}else
			bw.write(",\n[\n");
		String af;
		String[] afs;
		isFirstUser=true;
		while((af=br.readLine())!=null){
			afs = af.split(",");
			if(afs.length!=6)
				continue;
			if(afs[5].equals("2")){
				midOLon+=Double.valueOf(afs[3]);
				midOLat+=Double.valueOf(afs[4]);
				ONum+=1;
				if(isFirstUser){
					bw.write("\n[\n");
					isFirstUser=false;
				}else
					bw.write(",\n[\n");
				isFirstLine=true;
			}else if(afs[5].equals("0")){
				if(isFirstLine){
					bw.write("{\"coords\":["+LocToBaidu(afs[3],afs[4])+"],\"is_special\":false,\"label\":\""+String.valueOf(label++)+"\"}");
					isFirstLine=false;
				}else
					bw.write(",\n{\"coords\":["+LocToBaidu(afs[3],afs[4])+"],\"is_special\":false,\"label\":\""+String.valueOf(label++)+"\"}");
			}else if(afs[5].equals("3")){
				midDLon+=Double.valueOf(afs[3]);
				midDLat+=Double.valueOf(afs[4]);
				DNum+=1;
				bw.write("]");
			}
		}
		bw.write("\n]");
		br.close();
		bw.close();
	}
	public static void AfterExport(File output)throws Exception{
		bw = new BufferedWriter(new FileWriter(output,true));
		bw.write("\n],\n\"start\":{\n\"coords\":[");
		System.out.println(midOLon);
		System.out.println(ONum);
		midOLon=midOLon/ONum;
		midOLat=midOLat/ONum;
		System.out.println(midOLon);
		bw.write(LocToBaidu(midOLon,midOLat));
		bw.write("],\n\"label\":\"\",\n\"is_special\":true\n},\n");
		bw.write("\"end\":{\n\"coords\":[");
		midDLon=midDLon/DNum;
		midDLat=midDLat/DNum;
		bw.write(LocToBaidu(midDLon,midDLat));
		bw.write("],\n\"label\":\"\",\n\"is"
				+ "_special\":true\n}\n}");
		bw.close();
	}
	public static void main(String[] args)throws Exception{
		//Config.init();
		File ODRoutesPath = new File("K:\\odCluster\\yayuncun_jinrongjie\\good");
		File[] ODFiles = ODRoutesPath.listFiles();
		File outputFile = new File("E:\\DataVisual\\dv\\data\\od\\yayuncun_jinrongjie.json");
		BeforeExport(outputFile);
		for(File file:ODFiles){
			if(file.getName().matches(".*txt$")){
				Export(file,outputFile);
			}
		}
		AfterExport(outputFile);
		System.out.println("finish");
	}
}
