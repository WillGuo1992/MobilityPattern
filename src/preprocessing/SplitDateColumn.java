package preprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/*
 * author:youg
 * date:20160311
 * ��1withPos������ʱ���ֶλ���Ϊ�����ڣ�ʱ�䡱�ֶΣ���4splitDateColumn�ݴ沢����1withPos�ļ�
 * 0raw:ԭʼ�������ݣ���������γ����Ϣ����ʱ�仮���ļ�
 * 1withPos:��Ӿ�γ����Ϣ����id����λ�����ļ����ļ��ڰ�id��ʱ������
 * 2timeSpan:��¼ÿ��IDÿ��������ֵ�ʱ���λ���Լ�������ֵ�ʱ���λ��
 * 3timeLine����15����Ϊ��λͳ��ÿ��ID��ÿ��ʱ��γ��ֵĴ���
 * 1splitDateColumn���ݴ��޸��ֶε�1withPos�ļ�
 */
public class SplitDateColumn {
	public static String basePath = "F:\\basemap\\BJbase_cellidcn.txt";
	public static String workPath = "F:\\BJmobile\\";
	public static String date = "20130226";
	public static String rawPathName = workPath+date+"\\0raw\\";
	public static String withPosPathName = workPath+date+"\\1withPos\\";
	public static String splitDateColumnPathName = workPath+date+"\\1splitDateColumn\\";
	public static BufferedWriter bw;
	public static BufferedReader br;
	public static int total = 0,useful=0;
	/*
	 * ��1withPos�ļ������ļ������ں�ʱ���ֶηָ
	 */
	public static void splitDateColumn(File withPosFile)throws Exception{
		System.out.println("Now dealing with "+withPosFile.getAbsolutePath());
		br = new BufferedReader(new FileReader(withPosFile));
		String splitDateColumnFileName = splitDateColumnPathName+withPosFile.getName();
		bw = new BufferedWriter(new FileWriter(splitDateColumnFileName));
		String af,date,time;
		String[] afList;
		while((af=br.readLine())!=null){
			afList = af.split(",");
			date = afList[1].substring(0,8);
			time = afList[1].substring(8);
			afList[1] = date+","+time;
			for(int i=0;i<afList.length-1;i++){
				bw.write(afList[i]+",");
			}
			bw.write(afList[afList.length-1]+"\n");
		}
		br.close();
		bw.close();
	}
	public static void main(String[] args)throws Exception{
		File withPosPath = new File(withPosPathName);
		File[] files = withPosPath.listFiles();
		for(File file:files){
			splitDateColumn(file);
		}
		System.out.println("finish");
		
	}
}
