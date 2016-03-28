package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

/*
 * author:youg
 * date:20160229
 * ��ȡ�����õ��û����ڽ�һ������
 * 0raw:ԭʼ�������ݣ���������γ����Ϣ����ʱ�仮���ļ�
 * 1withPos:��Ӿ�γ����Ϣ����id����λ�����ļ����ļ��ڰ�id��ʱ������
 * 2timeSpan:��¼ÿ��IDÿ��������ֵ�ʱ���λ���Լ�������ֵ�ʱ���λ��
 * 3timeLine����15����Ϊ��λͳ��ÿ��ID��ÿ��ʱ��γ��ֵĴ���
 * 4goodUser:���������á�������һ���������û�ID�б���ȡ����7��ǰ��19����м�¼��7-19��ÿ3��Сʱ�м�¼���û�����ռ�������û�������55%
 * 5goodRecord:4goodUser�б�����û���������¼����id����λ�ָ��ͬ�ļ���
 */
public class getGoodRecord {
	public static String workPath = "F:\\BJmobile\\";
	public static String date = "20130228";
	public static String withPosPathName = workPath+date+"\\1withPos\\";
	public static String timeSpanPathName = workPath+date+"\\2timeSpan\\";
	public static String timeLinePathName = workPath+date+"\\3timeLine\\";
	public static String goodUserPathName = workPath+date+"\\4goodUser\\";
	public static String goodRecordPathName = workPath+date+"\\5goodRecord\\";
	
	public static BufferedReader br;
	public static BufferedWriter bw;
	public static Set<String> goodUser = new HashSet<String>();//��ź�����ID����readGoodUserList����
	/*
	 * ������û�ID������goodUser
	 */
	public static void readGoodUserList(String goodUserFileName)throws Exception{
		System.out.println("Now readGoodUserList");
		br = new BufferedReader(new FileReader(goodUserFileName));
		String af;
		while((af=br.readLine())!=null)
			goodUser.add(af);
		br.close();
	}
	/*
	 * ��1withPos����ȡgoodUser��Record����5goodRecord��
	 */
	public static void selectGoodRecord(File[] files)throws Exception{
		String af;
		String subAf;
		for(File file:files){
			System.out.println("Now selecting good record from:"+file.getAbsolutePath());
			br = new BufferedReader(new FileReader(file));
			bw = new BufferedWriter(new FileWriter(goodRecordPathName+file.getName()));
			while((af=br.readLine())!=null){
				subAf = af.substring(0,19);
				if(goodUser.contains(subAf))
					bw.write(af+"\n");
			}
			br.close();
			bw.close();
		}
	}
	public static void main(String[] args)throws Exception{
		String goodUserFileName = goodUserPathName+"goodUser.txt";
		readGoodUserList(goodUserFileName);
		File withPosPath = new File(withPosPathName);
		File[] withPosFiles = withPosPath.listFiles();
		selectGoodRecord(withPosFiles);
		System.out.println("finish");
	}
}
