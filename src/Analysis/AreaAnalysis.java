package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

/*
 * author:youg
 * date:20160229
 * ����ָ��ʱ��γ�����ָ�������ڵ��û�
 * 0raw:ԭʼ�������ݣ���������γ����Ϣ����ʱ�仮���ļ�
 * 1withPos:��Ӿ�γ����Ϣ����id����λ�����ļ����ļ��ڰ�id��ʱ������
 * 2timeSpan:��¼ÿ��IDÿ��������ֵ�ʱ���λ���Լ�������ֵ�ʱ���λ��
 * 3timeLine����15����Ϊ��λͳ��ÿ��ID��ÿ��ʱ��γ��ֵĴ���
 * 4goodUser:���������á�������һ���������û�ID�б���ȡ����7��ǰ��19����м�¼��7-19��ÿ3��Сʱ�м�¼���û�����ռ�������û�������55%
 * 5goodRecord:4goodUser�б�����û���������¼����id����λ�ָ��ͬ�ļ���
 * 6mergeSameLoc:�ϲ�����������ĳ��λ�õĵ㣬ʱ���ֶα�ʾ��ֹʱ��
 */
public class AreaAnalysis {
	public static double latMin = 40.061106;
	public static double latMax = 40.079664;
	public static double lonMin = 116.406162;
	public static double lonMax = 116.442177;
	public static String basePath = "F:\\basemap\\BJbase_cellidcn.txt";
	public static String workPath = "F:\\BJmobile\\";
	public static String date = "20130226";
	public static String inputPathName = workPath+date+"\\1withPos\\";
	public static String timeSpanPathName = workPath+date+"\\2timeSpan\\";
	public static String timeLinePathName = workPath+date+"\\3timeLine\\";
	public static String goodUserPathName = workPath+date+"\\4goodUser\\";
	public static String goodRecordPathName = workPath+date+"\\5goodRecord\\";
	public static String mergeSameLocPathName = workPath+date+"\\6mergeSameLoc\\";
	
	public static BufferedReader br;
	public static BufferedWriter bw;
	public static int total = 0,useful=0;
	public static Set<String> baseSet = new HashSet<String>();
	
	/*
	 * ���ؾ�γ�ȷ�Χ�ڵĻ�վ��
	 */
	public static void loadBase(String basePath)throws Exception{
		br = new BufferedReader(new FileReader(basePath));
		String af;
		String[] afList;
		double lon,lat;
		while((af=br.readLine())!=null){
			afList = af.split(",");
			lon = Double.valueOf(afList[3]);
			if(lon<lonMin || lon>lonMax)
				continue;
			lat = Double.valueOf(afList[4]);
			if(lat<latMin || lat>latMax)
				continue;
			baseSet.add(afList[0]);
		}
		br.close();
	}
	/*
	 * ɸѡ�賿�����ڸ������ҳ���ͣ��ʱ��γ���4Сʱ��ID
	 */
	public static void selectAreaUser(File mergeSameLocFile)throws Exception{
		br = new BufferedReader(new FileReader(mergeSameLocFile));
		String af;
		String[] afList;
		double startTime,endTime;
		int lac,cell;
		String base;
		while((af=br.readLine())!=null){
			afList = af.split(",");
			lac = Integer.valueOf(afList[3]);
			cell = Integer.valueOf(afList[4]);
			lac = lac*100000+cell;
			base = String.valueOf(lac);
			if(!baseSet.contains(base))
				continue;
			startTime = Integer.valueOf(afList[2].substring(0,2))*1.0+Integer.valueOf(afList[2].substring(2,4))/60.0;
			endTime = Integer.valueOf(afList[2].substring(7,9))*1.0+Integer.valueOf(afList[2].substring(9,11))/60.0;
			if(endTime-startTime<4.0)
				continue;
			if(!(endTime<8.0 || startTime>18.0))
				continue;
			useful+=1;
			System.out.println(afList[0]);
		}
		br.close();
	}
	
	public static void main(String[] args)throws Exception{
		//���������ڻ�վ
		loadBase(basePath);
		File mergeSameLocPath = new File(mergeSameLocPathName);
		File[] mergeSameLocFiles = mergeSameLocPath.listFiles();
		total=0;
		useful=0;
		int i=0;
		for(File file:mergeSameLocFiles){
			selectAreaUser(file);
			if(i++==1)
				break;
		}
		System.out.println("finish");
		System.out.println("total:"+String.valueOf(total));
		System.out.println("useful:"+String.valueOf(useful));
	}
}
