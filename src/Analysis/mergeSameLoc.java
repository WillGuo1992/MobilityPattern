package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/*
 * author:youg
 * date:20160312
 * ��sample\\selectWithPos����ʽͬ1withPose���ļ����е��ļ�������������ĳ��λ�õĵ�ϲ��������ֹʱ�䣬���浽6mergeSameLoc�ļ�����
 * 0raw:ԭʼ�������ݣ���������γ����Ϣ����ʱ�仮���ļ�
 * 1withPos:��Ӿ�γ����Ϣ����id����λ�����ļ����ļ��ڰ�id��ʱ������
 * 2timeSpan:��¼ÿ��IDÿ��������ֵ�ʱ���λ���Լ�������ֵ�ʱ���λ��
 * 3timeLine����15����Ϊ��λͳ��ÿ��ID��ÿ��ʱ��γ��ֵĴ���
 * 1splitDateColumn���ݴ��޸��ֶε�1withPos�ļ�
 * 6mergeSample���ϲ�����������ĳ��λ�õĵ㣬ʱ���ֶα�ʾ��ֹʱ��
 */
public class mergeSameLoc {
	public static String basePath = "F:\\basemap\\BJbase_cellidcn.txt";
	public static String workPath = "F:\\BJmobile\\";
	public static String date = "20130228";
	public static String rawPathName = workPath+date+"\\0raw\\";
	public static String withPosPathName = workPath+date+"\\1withPos\\";
	public static String goodRecordPathName = workPath+date+"\\5goodRecord\\";
	public static String mergeSameLocPathName = workPath+date+"\\6mergeSameLoc\\";
	
	public static BufferedReader br;
	public static BufferedWriter bw;
	
	public static void merge(File inputfile)throws Exception{
		System.out.println("Now merging with "+inputfile.getAbsolutePath());
		String outputfileName = mergeSameLocPathName+inputfile.getName();
		br = new BufferedReader(new FileReader(inputfile));
		bw = new BufferedWriter(new FileWriter(outputfileName));
		String thisLabel,lastLabel=null;
		String startTime=null,endTime=null;
		String thisAf,lastAf=null;
		String[] afList;
		while((thisAf=br.readLine())!=null){
			afList = thisAf.split(",");
			thisLabel = afList[0]+","+afList[3]+","+afList[4];
			if(!thisLabel.equals(lastLabel)){
				if(lastLabel!=null)
					bw.write(lastAf.substring(0,29)+startTime+"-"+endTime+lastAf.substring(35)+"\n");
				startTime = afList[2];
			}
			endTime = afList[2];
			lastLabel = thisLabel;
			lastAf = thisAf;
		}
		bw.write(lastAf.substring(0,29)+startTime+"-"+endTime+lastAf.substring(35)+"\n");
		br.close();
		bw.close();
	}
	public static void main(String[] args)throws Exception{
		File goodRecordPath = new File(goodRecordPathName);
		File[] files = goodRecordPath.listFiles();
		for(File file:files){
			merge(file);
		}
		System.out.println("finish");
	}
}
