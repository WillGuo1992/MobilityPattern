package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import Config.Config;
import Model.Location;
import Model.Zone;
import Model.ZoneMap;

/*
 * author:youg
 * date:20170206
 * 生成热点区域可视化展示数据（热点出发、到达区域）(以交通小区为单位)
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
	public static DecimalFormat df = new DecimalFormat("#0.000000");
	/*public static void main(String[] args)throws Exception{
		//BufferedReader br = new BufferedReader(new FileReader("K:\\HotArea\\TransZone461.txt"));
		//br.close();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("K:\\HotArea\\TransZone461.txt"),"GBK"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("E:\\DataVisual\\dv\\data\\date\\20130225\\matrix.json"));
		String af;
		String[] afs;
		bw.write("[\n");
		int num=1;
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
			String name = afs[3].substring(1,afs[3].length()-1);
			bw.write("\t\t\"title\": \""+(num++)+"-"+name+"\",\n");
			bw.write("\t\t\"color\": \"#ffefc6\"\n");
			bw.write("\t},\n");
		}
		bw.write("]");
		bw.close();
		br.close();
	}
	*/
	public static void main(String[] args)throws Exception{
		//目录初始化，交通小区初始化
		Config.init();
		ZoneMap map = new ZoneMap();
		map.init();
		int[][] OrigHot = new int[map.num][24];
		int[][] DestHot = new int[map.num][24];
		BufferedReader br;
		String af;
		String[] afs;
		br = new BufferedReader(new FileReader("K:\\BJMoblieCellData2014\\20141103\\7stayRecord\\00.txt"));
		while((af=br.readLine())!=null){
			afs = af.split(",");
			if(!afs[5].equals("1"))
				continue;
			Double lon = Double.parseDouble(afs[3]);
			Double lat = Double.parseDouble(afs[4]);
			Zone z = map.getZoneByPos(lon, lat);
			if(z==null){
				///System.out.println("...");
				continue;
			}
			int id = z.id-1;
			int dt = Integer.parseInt(afs[2].substring(0, 2));
			int ot = Integer.parseInt(afs[2].substring(7, 9));
			if(ot<24)
				OrigHot[id][ot]+=1;
			if(dt<24)
				DestHot[id][dt]+=1;
		}
		br.close();
		br = new BufferedReader(new FileReader("K:\\BJMoblieCellData2014\\20141103\\7stayRecord\\01.txt"));
		while((af=br.readLine())!=null){
			afs = af.split(",");
			if(!afs[5].equals("1"))
				continue;
			Double lon = Double.parseDouble(afs[3]);
			Double lat = Double.parseDouble(afs[4]);
			Zone z = map.getZoneByPos(lon, lat);
			if(z==null){
				///System.out.println("...");
				continue;
			}
			int id = z.id-1;
			int dt = Integer.parseInt(afs[2].substring(0, 2));
			int ot = Integer.parseInt(afs[2].substring(7, 9));
			if(ot<24)
				OrigHot[id][ot]+=1;
			if(dt<24)
				DestHot[id][dt]+=1;
		}
		br.close();
		br = new BufferedReader(new FileReader("K:\\BJMoblieCellData2014\\20141103\\7stayRecord\\02.txt"));
		while((af=br.readLine())!=null){
			afs = af.split(",");
			if(!afs[5].equals("1"))
				continue;
			Double lon = Double.parseDouble(afs[3]);
			Double lat = Double.parseDouble(afs[4]);
			Zone z = map.getZoneByPos(lon, lat);
			if(z==null){
				///System.out.println("...");
				continue;
			}
			int id = z.id-1;
			int dt = Integer.parseInt(afs[2].substring(0, 2));
			int ot = Integer.parseInt(afs[2].substring(7, 9));
			if(ot<24)
				OrigHot[id][ot]+=1;
			if(dt<24)
				DestHot[id][dt]+=1;
		}
		br.close();
		br = new BufferedReader(new FileReader("K:\\BJMoblieCellData2014\\20141103\\7stayRecord\\03.txt"));
		while((af=br.readLine())!=null){
			afs = af.split(",");
			if(!afs[5].equals("1"))
				continue;
			Double lon = Double.parseDouble(afs[3]);
			Double lat = Double.parseDouble(afs[4]);
			Zone z = map.getZoneByPos(lon, lat);
			if(z==null){
				///System.out.println("...");
				continue;
			}
			int id = z.id-1;
			int dt = Integer.parseInt(afs[2].substring(0, 2));
			int ot = Integer.parseInt(afs[2].substring(7, 9));
			if(ot<24)
				OrigHot[id][ot]+=1;
			if(dt<24)
				DestHot[id][dt]+=1;
		}
		br.close();
		br = new BufferedReader(new FileReader("K:\\BJMoblieCellData2014\\20141103\\7stayRecord\\04.txt"));
		while((af=br.readLine())!=null){
			afs = af.split(",");
			if(!afs[5].equals("1"))
				continue;
			Double lon = Double.parseDouble(afs[3]);
			Double lat = Double.parseDouble(afs[4]);
			Zone z = map.getZoneByPos(lon, lat);
			if(z==null){
				///System.out.println("...");
				continue;
			}
			int id = z.id-1;
			int dt = Integer.parseInt(afs[2].substring(0, 2));
			int ot = Integer.parseInt(afs[2].substring(7, 9));
			if(ot<24)
				OrigHot[id][ot]+=1;
			if(dt<24)
				DestHot[id][dt]+=1;
		}
		br.close();
		BufferedWriter bw;
		bw = new BufferedWriter(new FileWriter("K:\\OriginHot.csv"));
		for(int i=0;i<OrigHot.length;i++){
			bw.write(""+(i+1));
			for(int j=0;j<OrigHot[0].length;j++)
				bw.write(","+df.format(OrigHot[i][j]*100.0/map.getZoneById(i+1).area));
			bw.write("\n");
		}
		bw.close();
		bw = new BufferedWriter(new FileWriter("K:\\DestinationHot.csv"));
		for(int i=0;i<DestHot.length;i++){
			bw.write(""+(i+1));
			for(int j=0;j<DestHot[0].length;j++)
				bw.write(","+df.format(DestHot[i][j]*100.0/map.getZoneById(i+1).area));
			bw.write("\n");
		}
		bw.close();
		System.out.println("finish");
	}
}
