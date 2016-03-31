package Operator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class statTimeDelay {
	/*
	 * author:youg
	 * date:20160329
	 * ͳ�����������¼�����ֲ�
	 * 0raw:ԭʼ�������ݣ���������γ����Ϣ����ʱ�仮���ļ�
	 * 1withPos:��Ӿ�γ����Ϣ����id����λ�����ļ����ļ��ڰ�id��ʱ������
	 * 2timeSpan:��¼ÿ��IDÿ��������ֵ�ʱ���λ���Լ�������ֵ�ʱ���λ��
	 * 3timeLine����15����Ϊ��λͳ��ÿ��ID��ÿ��ʱ��γ��ֵĴ���
	 * 4goodUser:���������á�������һ���������û�ID�б���ȡ����7��ǰ��19����м�¼��7-19��ÿ3��Сʱ�м�¼���û�����ռ�������û�������55%
	 * 5goodRecord:4goodUser�б�����û���������¼����id����λ�ָ��ͬ�ļ���
	 * 7stayRecord:��5goodRecord����ȡ�����û�ͣ�����¼
	 */
	public static String workPath = "F:\\BJmobile\\";
	public static String date = "20130226";
	public static String withPosPathName = workPath+date+"\\1withPos\\";
	public static String timeSpanPathName = workPath+date+"\\2timeSpan\\";
	public static String timeLinePathName = workPath+date+"\\3timeLine\\";
	public static String goodUserPathName = workPath+date+"\\4goodUser\\";
	public static String goodRecordPathName = workPath+date+"\\5goodRecord\\";
	public static String stayRecordPathName = workPath+date+"\\7stayRecord\\";
	public static int[] stat = new int[21];
	
	public static double distanceInGlobal(double lon1, double lat1, double lon2, double lat2){
		double x1 = lon1;
		double y1 = lat1;
		double x2 = lon2;
		double y2 = lat2;

		double L = (3.1415926*6370/180)*Math.sqrt((Math.abs((x1)-(x2)))*(Math.abs((x1)-(x2)))*(Math.sin((90-(y1))*(3.1415926/180)))*(Math.sin((90-(y1))*(3.1415926/180)))+(Math.abs((y1)-(y2)))*(Math.abs((y1)-(y2))));
		return L * 1000;
	}
	public static void calDis(File goodRecordFile)throws Exception{
		System.out.println("Now calulating Dis:"+goodRecordFile.getAbsolutePath());
		BufferedReader br = new BufferedReader(new FileReader(goodRecordFile));
		String af;
		String[] afs;
		String lastId=null,thisId;
		double lastLon=0.0,lastLat=0.0;
		double thisLon=0.0,thisLat=0.0;
		while((af=br.readLine())!=null){
			afs = af.split(",");
			thisId = afs[0];
			if(thisId.equals(lastId)){
				thisLon=Double.valueOf(afs[5]);
				thisLat=Double.valueOf(afs[6]);
				int dis = (int)distanceInGlobal(thisLon,thisLat,lastLon,lastLat);
				if(dis==0)
					continue;
				if(dis<2000)
					stat[dis/100]+=1;
				else
					stat[20]+=1;
			}
			lastId=thisId;
			lastLon=thisLon;
			lastLat=thisLat;
		}
		br.close();
	}
	public static void main(String[] args)throws Exception{
		File goodRecordPath = new File(goodRecordPathName);
		File[] goodRecordFiles = goodRecordPath.listFiles();
		int j=0;
		for(File file:goodRecordFiles){
			calDis(file);
			if(++j>5)
				break;
		}
		System.out.println("finish");
		for(int i=0;i<stat.length;i++)
			System.out.println(i+":"+stat[i]);
	}
}
