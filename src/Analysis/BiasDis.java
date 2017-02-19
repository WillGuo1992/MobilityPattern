package Analysis;

import java.io.BufferedReader;
import java.io.FileReader;

/*
 * author:youg
 * date:20170207
 * 分析三次多项式（cubic）、分段线性（linear）、最近邻（neareat）等插值算法的偏移距离
 * 0raw:原始信令数据，不包含经纬度信息，按时间划分文件
 * 1fixed:添加经纬度信息，按id后两位划分文件，文件内按id和时间排序
 * 2timeSpan:记录每个ID每天最早出现的时间和位置以及最晚出现的时间和位置
 * 3timeLine：以15分钟为单位统计每个ID在每个时间段出现的次数
 * 4goodUser:数据质量好、用于下一步分析的用户ID列表。提取规则：7点前、19点后有记录，7-19点每3个小时有记录的用户数所占比例；用户比例：55%
 * 5goodRecord:4goodUser列表里的用户的完整记录，按id后两位分割到不同文件中
 * 7stayRecord:从5goodRecord中提取出的用户停留点记录
 * 8OD:从7stayRecord中提取出的指定OD范围内的Trips
 */
public class BiasDis {
	/*
	 * 计算两位置之间的距离，根据球面坐标长度公式计算(单位：米)
	 * 注意，这个计算很耗时间,另外,这个计算把经纬度的100万倍还原了!
	 */
	public static double distanceInGlobal(double lon1, double lat1, double lon2, double lat2){
		double x1 = lon1;
		double y1 = lat1;
		double x2 = lon2;
		double y2 = lat2;

		double L = (3.1415926*6370/180)*Math.sqrt((Math.abs((x1)-(x2)))*(Math.abs((x1)-(x2)))*(Math.sin((90-(y1))*(3.1415926/180)))*(Math.sin((90-(y1))*(3.1415926/180)))+(Math.abs((y1)-(y2)))*(Math.abs((y1)-(y2))));
		return L * 1000;
	}
	public static void main(String[] args)throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("K:\\TrajectoryFill\\TrajectoryRepairExpResult.csv"));
		double[] lon_raw = new double[48];
		double[] lat_raw = new double[48];
		double[] lon_cub_10p = new double[48];
		double[] lat_cub_10p = new double[48];
		double[] lon_lin_10p = new double[48];
		double[] lat_lin_10p = new double[48];
		double[] lon_nea_10p = new double[48];
		double[] lat_nea_10p = new double[48];
		double[] lon_cub_20p = new double[48];
		double[] lat_cub_20p = new double[48];
		double[] lon_lin_20p = new double[48];
		double[] lat_lin_20p = new double[48];
		double[] lon_nea_20p = new double[48];
		double[] lat_nea_20p = new double[48];
		double[] lon_cub_50p = new double[48];
		double[] lat_cub_50p = new double[48];
		double[] lon_lin_50p = new double[48];
		double[] lat_lin_50p = new double[48];
		double[] lon_nea_50p = new double[48];
		double[] lat_nea_50p = new double[48];
		String af;
		String[] afs;
		int i=0;
		af=br.readLine();
		double sum_cub_10p=0,sum_lin_10p=0,sum_nea_10p=0;
		double sum_cub_20p=0,sum_lin_20p=0,sum_nea_20p=0;
		double sum_cub_50p=0,sum_lin_50p=0,sum_nea_50p=0;
		while((af=br.readLine())!=null){
			afs = af.split(",");
			lon_raw[i] = Double.parseDouble(afs[0]);
			lat_raw[i] = Double.parseDouble(afs[1]);
			lon_cub_10p[i] = Double.parseDouble(afs[4]);
			lat_cub_10p[i] = Double.parseDouble(afs[5]);
			lon_lin_10p[i] = Double.parseDouble(afs[6]);
			lat_lin_10p[i] = Double.parseDouble(afs[7]);
			lon_nea_10p[i] = Double.parseDouble(afs[8]);
			lat_nea_10p[i] = Double.parseDouble(afs[9]);
			sum_cub_10p+=distanceInGlobal(lon_raw[i],lat_raw[i],lon_cub_10p[i],lat_cub_10p[i]);
			sum_lin_10p+=distanceInGlobal(lon_raw[i],lat_raw[i],lon_lin_10p[i],lat_lin_10p[i]);
			sum_nea_10p+=distanceInGlobal(lon_raw[i],lat_raw[i],lon_nea_10p[i],lat_nea_10p[i]);
			
			lon_cub_20p[i] = Double.parseDouble(afs[12]);
			lat_cub_20p[i] = Double.parseDouble(afs[13]);
			lon_lin_20p[i] = Double.parseDouble(afs[14]);
			lat_lin_20p[i] = Double.parseDouble(afs[15]);
			lon_nea_20p[i] = Double.parseDouble(afs[16]);
			lat_nea_20p[i] = Double.parseDouble(afs[17]);
			sum_cub_20p+=distanceInGlobal(lon_raw[i],lat_raw[i],lon_cub_20p[i],lat_cub_20p[i]);
			sum_lin_20p+=distanceInGlobal(lon_raw[i],lat_raw[i],lon_lin_20p[i],lat_lin_20p[i]);
			sum_nea_20p+=distanceInGlobal(lon_raw[i],lat_raw[i],lon_nea_20p[i],lat_nea_20p[i]);

			lon_cub_50p[i] = Double.parseDouble(afs[20]);
			lat_cub_50p[i] = Double.parseDouble(afs[21]);
			lon_lin_50p[i] = Double.parseDouble(afs[22]);
			lat_lin_50p[i] = Double.parseDouble(afs[23]);
			lon_nea_50p[i] = Double.parseDouble(afs[24]);
			lat_nea_50p[i] = Double.parseDouble(afs[25]);
			sum_cub_50p+=distanceInGlobal(lon_raw[i],lat_raw[i],lon_cub_50p[i],lat_cub_50p[i]);
			sum_lin_50p+=distanceInGlobal(lon_raw[i],lat_raw[i],lon_lin_50p[i],lat_lin_50p[i]);
			sum_nea_50p+=distanceInGlobal(lon_raw[i],lat_raw[i],lon_nea_50p[i],lat_nea_50p[i]);
			i+=1;
			if(i>40)
				break;
		}
		sum_cub_10p/=i;
		sum_lin_10p/=i;
		sum_nea_10p/=i;
		sum_cub_20p/=i;
		sum_lin_20p/=i;
		sum_nea_20p/=i;
		sum_cub_50p/=i;
		sum_lin_50p/=i;
		sum_nea_50p/=i;
		System.out.println("sum_cub_10p,"+sum_cub_10p);
		System.out.println("sum_lin_10p,"+sum_lin_10p);
		System.out.println("sum_nea_10p,"+sum_nea_10p);
		System.out.println("sum_cub_20p,"+sum_cub_20p);
		System.out.println("sum_lin_20p,"+sum_lin_20p);
		System.out.println("sum_nea_20p,"+sum_nea_20p);
		System.out.println("sum_cub_50p,"+sum_cub_50p);
		System.out.println("sum_lin_50p,"+sum_lin_50p);
		System.out.println("sum_nea_50p,"+sum_nea_50p);
		br.close();
		System.out.println("finish");
	}
}
