package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * author:youg
 * date:20160229
 * �����û�ʱ����timeSpan��ʱ�串�����timeLine����ȡ�������û����浽goodUser��
 * 0raw:ԭʼ�������ݣ���������γ����Ϣ����ʱ�仮���ļ�
 * 1withPos:��Ӿ�γ����Ϣ����id����λ�����ļ����ļ��ڰ�id��ʱ������
 * 2timeSpan:��¼ÿ��IDÿ��������ֵ�ʱ���λ���Լ�������ֵ�ʱ���λ��
 * 3timeLine����15����Ϊ��λͳ��ÿ��ID��ÿ��ʱ��γ��ֵĴ���
 * 4goodUser:���������á�������һ���������û�ID�б���ȡ����7��ǰ��19����м�¼��7-19��ÿ3��Сʱ�м�¼���û�����ռ�������û�������55%
 */
public class QualityStat {
	public static String workPath = "F:\\BJmobile\\";
	public static String date = "20130228";
	public static String inputPathName = workPath+date+"\\1withPos\\";
	public static String timeSpanPathName = workPath+date+"\\2timeSpan\\";
	public static String timeLinePathName = workPath+date+"\\3timeLine\\";
	public static String goodUserPathName = workPath+date+"\\4goodUser\\";
	
	public static BufferedReader br;
	public static BufferedWriter bw;
	public static int total = 0,useful=0;
	/*
	 * ͳ���û��������û�ƽ����¼����
	 */
	public static void userNumber(File[] files)throws Exception{
		int userCount=0,recordCount=0;
		for(File file:files){
			System.out.println("Now counting userNumber with "+file.getAbsolutePath());
			br = new BufferedReader(new FileReader(file));
			String af,last=null;
			while((af=br.readLine())!=null){
				recordCount+=1;
				af=af.substring(0,19);
				if(!af.equals(last))
					userCount+=1;
				last=af;
			}
			br.close();
		}
		System.out.println("User Number: "+String.valueOf(userCount));
		System.out.println("ƽ���û���¼��: "+String.valueOf(recordCount*1.0/userCount));
	}
	/*
	 * ͳ��ƽ����������
	 */
	public static void statTime(File[] files)throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd,HHmmss");
		Date lastTime= new Date(0);
		Date thisTime= new Date(0);
		String thisUser,lastUser=null;
		double timeCount=0;
		int recordCount=0;
		for(File file:files){
			System.out.println("Now stating time with "+file.getAbsolutePath());
			br = new BufferedReader(new FileReader(file));
			String af;
			while((af=br.readLine())!=null){
				thisUser=af.substring(0,19);
				if(!thisUser.equals(lastUser)){
					lastTime = format.parse(af.substring(20,35));
					lastUser = thisUser;
					continue;
				}
				thisTime = format.parse(af.substring(20,35));
				timeCount+= (thisTime.getTime()-lastTime.getTime())/1000.0/60.0;//����
				recordCount+=1;
				lastTime = new Date(thisTime.getTime());
				lastUser = thisUser;
			}
			br.close();
		}
		System.out.println("ƽ����������: "+String.valueOf(timeCount/recordCount));
	}
	/*
	 * ͳ���û�ƽ��ʱ���ȡ�ʱ���ȷֲ���ʱ���ȳ���12Сʱ���û�ռ���û�����
	 */
	public static void statDelay(File[] files)throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd,HHmmss");
		Map<Integer,Integer> delay = new HashMap<Integer,Integer>();
		Date firstTime= new Date(0);
		Date lastTime= new Date(0);
		String thisUser,lastUser=null;
		delay.put(0, 0);
		delay.put(1, 0);
		delay.put(2, 0);
		delay.put(3, 0);
		delay.put(4, 0);
		delay.put(5, 0);
		delay.put(6, 0);
		for(File file:files){
			System.out.println("Now stating delay with "+file.getAbsolutePath());
			br = new BufferedReader(new FileReader(file));
			String af;
			while((af=br.readLine())!=null){
				thisUser = af.substring(0,19);
				if(!thisUser.equals(lastUser)){
					if(lastUser!=null){
						int t = (int)((lastTime.getTime()-firstTime.getTime())/1000.0/60.0/60.0);
						t=t/4;
						if(t>6)
							t=6;
						if(t<0)
							t=0;
						delay.put(t, delay.get(t)+1);
					}
					firstTime = format.parse(af.substring(20,35));
					lastTime = format.parse(af.substring(20,35));
					lastUser = thisUser;
					continue;
				}
				lastTime = format.parse(af.substring(20,35));
				lastUser = thisUser;
			}
			br.close();
		}
		for(Integer i:delay.keySet()){
			System.out.println(String.valueOf(i)+":"+String.valueOf(delay.get(i)));
		}
	}
	/*
	 *ͳ��ÿ���û���ʱ���ȣ����浽\\2timeSpan·���� 
	 */
	public static void createTimeSpan(File[] files)throws Exception{
		String firstTime=null,lastTime=null;
		String firstLon=null,lastLon=null,firstLat=null,lastLat=null;
		String thisUser=null,lastUser=null;
		for(File file:files){
			System.out.println("Now creating time span with "+file.getAbsolutePath());
			String name = file.getName();
			String outputFileName = timeSpanPathName+name;
			bw = new BufferedWriter(new FileWriter(outputFileName));
			br = new BufferedReader(new FileReader(file));
			String af;
			String[] afList;
			while((af=br.readLine())!=null){
				afList = af.split(",");
				thisUser = af.substring(0,19);
				if(!thisUser.equals(lastUser)){
					if(lastUser!=null){
						//�����ļ�
						bw.write(lastUser+","+firstTime+","+lastTime+","+firstLon+","+firstLat+","+lastLon+","+lastLat+"\n");
					}
					firstTime = String.format("%.1f", (Double.valueOf(afList[2].substring(0,2))+Double.valueOf(afList[2].substring(2,4))/60.0));
					firstLon = afList[5];
					firstLat = afList[6];
					lastTime = firstTime;
					lastLon = firstLon;
					lastLat = firstLat;
					lastUser = thisUser;
					continue;
				}
				lastTime = String.format("%.1f", (Double.valueOf(afList[2].substring(0,2))+Double.valueOf(afList[2].substring(2,4))/60.0));
				lastLon = afList[5];
				lastLat = afList[6];
				lastUser = thisUser;
			}
			br.close();
			bw.close();
		}
	}
	/*
	 * ͳ��ÿ���û���ʱ���Ḳ�ǣ����浽\\3timeLine·����
	 */
	public static void createTimeLine(File[] files)throws Exception{
		int[] timeLine= new int[96];
		String thisUser,lastUser=null;
		for(File file:files){
			System.out.println("Now creating time line with "+file.getAbsolutePath());
			String name = file.getName();
			String outputFileName = timeLinePathName+name;
			br = new BufferedReader(new FileReader(file));
			bw = new BufferedWriter(new FileWriter(outputFileName));
			String af;
			String[] afList;
			int timeStamp;
			while((af=br.readLine())!=null){
				afList = af.split(",");
				thisUser = afList[0];
				if(!thisUser.equals(lastUser)){
					if(lastUser!=null){
						bw.write(lastUser);
						for(int i=0;i<timeLine.length;i++){
							bw.write(","+String.valueOf(timeLine[i]));
							timeLine[i]=0;
						}
						bw.write("\n");
					}
					timeStamp=Integer.valueOf(afList[2].substring(0,2))*60+Integer.valueOf(afList[2].substring(2,4));
					timeLine[timeStamp/15]+=1;
					lastUser=thisUser;
					continue;
				}
				timeStamp=Integer.valueOf(afList[2].substring(0,2))*60+Integer.valueOf(afList[2].substring(2,4));
				timeLine[timeStamp/15]+=1;
			}
			br.close();
			bw.close();
		}
	}
	/*
	 * ͳ��7��ǰ��7-19�㡢19��֮���м�¼���û�����ռ����
	 */
	public static void statTimeLine_1(File[] files)throws Exception{
		int totalUser=0;
		int rightUser=0;
		for(File file:files){
			System.out.println("Now stating time line 1 with "+file.getAbsolutePath());
			br = new BufferedReader(new FileReader(file));
			String af;
			String[] afList;
			while((af=br.readLine())!=null){
				totalUser+=1;
				afList = af.split(",");
				boolean ok1=false,ok2=false,ok3=false;
				for(int i=1;i<=28;i++)
					if(!afList[i].equals("0")){
						ok1=true;
						break;
					}
				for(int i=29;i<=76;i++)
					if(!afList[i].equals("0")){
						ok2=true;
						break;
					}
				for(int i=77;i<=96;i++)
					if(!afList[i].equals("0")){
						ok3=true;
						break;
					}
				if(ok1 && ok2 && ok3)
					rightUser+=1;
			}
			br.close();
		}
		System.out.println("good User Ratio: "+String.format("%.6f",rightUser*1.0/totalUser));
	}
	/*
	 * ͳ��7��ǰ��19����м�¼��7-19��ÿ��Сʱ�м�¼���û�����ռ����
	 */
	public static void statTimeLine_2(File[] files)throws Exception{
		int totalUser=0;
		int rightUser=0;
		for(File file:files){
			System.out.println("Now stating time line 2 with "+file.getAbsolutePath());
			br = new BufferedReader(new FileReader(file));
			String af;
			String[] afList;
			while((af=br.readLine())!=null){
				totalUser+=1;
				afList = af.split(",");
				boolean ok1=false,ok2=true,ok3=false;
				for(int i=1;i<=28;i++)
					if(!afList[i].equals("0")){
						ok1=true;
						break;
					}
				for(int i=29;i<=76;i=i+4)
					if(afList[i].equals("0") && afList[i+1].equals("0") && afList[i+2].equals("0") && afList[i+3].equals("0")){
						ok2=false;
						break;
					}
				for(int i=77;i<=96;i++)
					if(!afList[i].equals("0")){
						ok3=true;
						break;
					}
				if(ok1 && ok2 && ok3){
					rightUser+=1;
					System.out.println(afList[0]);
				}
			}
			br.close();
		}
		System.out.println("good User Ratio: "+String.format("%.6f",rightUser*1.0/totalUser));
	}
	/*
	 * ͳ��7��ǰ��19����м�¼��7-19��ÿ3��Сʱ�м�¼���û�����ռ����
	 */
	public static void statTimeLine_3(File[] files)throws Exception{
		int totalUser=0;
		int rightUser=0;
		String goodUserFileName = goodUserPathName+"goodUser.txt";
		bw = new BufferedWriter(new FileWriter(goodUserFileName));
		for(File file:files){
			System.out.println("Now stating time line 3 with "+file.getAbsolutePath());
			br = new BufferedReader(new FileReader(file));
			String af;
			String[] afList;
			while((af=br.readLine())!=null){
				totalUser+=1;
				afList = af.split(",");
				boolean ok1=false,ok2=true,ok3=false;
				for(int i=1;i<=28;i++)
					if(!afList[i].equals("0")){
						ok1=true;
						break;
					}
				for(int i=29;i<=76;i=i+12)
					if(afList[i].equals("0") && afList[i+1].equals("0") && afList[i+2].equals("0") && afList[i+3].equals("0")
							&& afList[i+4].equals("0") && afList[i+5].equals("0") && afList[i+6].equals("0") && afList[i+7].equals("0")
							&& afList[i+8].equals("0") && afList[i+9].equals("0") && afList[i+10].equals("0") && afList[i+11].equals("0")){
						ok2=false;
						break;
					}
				for(int i=77;i<=96;i++)
					if(!afList[i].equals("0")){
						ok3=true;
						break;
					}
				if(ok1 && ok2 && ok3){
					rightUser+=1;
					//System.out.println(afList[0]);
					bw.write(afList[0]+"\n");
				}
			}
			br.close();
		}
		bw.close();
		System.out.println("good User Ratio: "+String.format("%.6f",rightUser*1.0/totalUser));
	}
	public static void main(String[] args)throws Exception{
		/*
		 * ****************************************************
		 */
		File inputPath = new File(inputPathName);
		File[] inputFiles = inputPath.listFiles();
		//userNumber(inputFiles);
		//statTime(inputFiles);
		//statDelay(inputFiles);
		createTimeSpan(inputFiles);
		/*
		 * ****************************************************
		 */
		createTimeLine(inputFiles);
		File timeLinePath = new File(timeLinePathName);
		File[] timeLineFiles = timeLinePath.listFiles();
		//statTimeLine_1(timeLineFiles);
		//statTimeLine_2(timeLineFiles);
		statTimeLine_3(timeLineFiles);
		/*
		 * ****************************************************
		 */
		System.out.println("finish");
	}
}
