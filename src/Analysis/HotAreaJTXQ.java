package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;

import Model.Location;

/*
 * author:youg
 * date:20170206
 * 生成热点区域可视化展示数据（早晚高峰居住工作区）(以交通小区为单位)
 * 0raw:原始信令数据，不包含经纬度信息，按时间划分文件
 * 1fixed:添加经纬度信息，按id后两位划分文件，文件内按id和时间排序
 * 2timeSpan:记录每个ID每天最早出现的时间和位置以及最晚出现的时间和位置
 * 3timeLine：以15分钟为单位统计每个ID在每个时间段出现的次数
 * 4goodUser:数据质量好、用于下一步分析的用户ID列表。提取规则：7点前、19点后有记录，7-19点每3个小时有记录的用户数所占比例；用户比例：55%
 * 5goodRecord:4goodUser列表里的用户的完整记录，按id后两位分割到不同文件中
 * 7stayRecord:从5goodRecord中提取出的用户停留点记录
 * 8OD:从7stayRecord中提取出的指定OD范围内的Trips
 */
public class HotAreaJTXQ {
	public static DecimalFormat df = new DecimalFormat("#.000000");
	public static void main(String[] args)throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("K:\\HotArea\\TransZone461.txt"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("E:\\DataVisual\\dv\\data\\date\\20130225\\matrix.json"));
		String af;
		String[] afs;
		bw.write("[\n");
		int num=0;
		while((af=br.readLine())!=null){
			afs = af.split(",");
			bw.write("\t{\n");
			bw.write("\t\t\"points\":[\n");
			bw.write("\t\t\t");
			Location p = new Location();
			p.gcj_lon = Double.parseDouble(afs[5]);
			p.gcj_lat = Double.parseDouble(afs[6]);
			p.bd_encrypt();
			bw.write("["+df.format(p.bd_lon)+","+df.format(p.bd_lat)+"]");
			for(int i=7;i<afs.length-2;i+=2){
				p.gcj_lon = Double.parseDouble(afs[i]);
				p.gcj_lat = Double.parseDouble(afs[i+1]);
				p.bd_encrypt();
				bw.write(",["+df.format(p.bd_lon)+","+df.format(p.bd_lat)+"]");
			}
			bw.write("\n\t\t],\n");
			bw.write("\t\t\"title\": \""+(num++)+"\",\n");
			bw.write("\t\t\"color\": \"#ffefc6\"\n");
			bw.write("\t},\n");
		}
		bw.write("]");
		bw.close();
		br.close();
	}
}
