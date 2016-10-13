package DataVisual;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

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
	public static void main(String[] args)throws Exception{
		//Config.init();
		File ODRoutesPath = new File("K:\\BJmobilePattern2014\\songjiazhuang_jinrongjie\\bad");
		File[] ODFiles = ODRoutesPath.listFiles();
		for(File file:ODFiles){
			System.out.println(file.getName());
			if(file.getName().matches(".*txt$")){
				System.out.println("ok");
			}
		}
		System.out.println("finish");
	}
}
