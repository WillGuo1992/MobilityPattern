package Analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;

/*
 * author��youg
 * date:15.12.16
 * ��������ͼ��Ҫ��json����
 * ��ԭʼ����������0.01��γ��Ϊ��λ��ͳ���ܶȣ�����json�����Ա�������ʽ���浽data.js�ļ���
 * data.js�ļ���ʽ��
 * var data = "[{"lng":116.425000,"lat":39.925000,"count":400},...]";
 */
class Point{
	public double lng;
	public double lat;
	public int count;
	public Point(){
		
	};
	public Point(double lng,double lat,int count){
		this.lng = lng;
		this.lat = lat;
		this.count = count;
	}
}
public class heatMap {
	public static String inputPath;
	public static String outputFile;
	public static BufferedReader inputFile;
	public static FileWriter jsonFile;
	public static Map<String,Integer> pointCount = new HashMap<String,Integer>();
	public static int maxValue;
	public static void main(String[] args) throws IOException{
		//��������·�����ļ��л��ļ��Կ�
		inputPath = "F:\\��ͨ�ֻ�����\\20151027\\01";
		//��������ļ�
		outputFile = "F:\\DataVisualization\\heatmap\\data.js";

		String af;
		File filePath = new File(inputPath);
		//��������
		if(filePath.isDirectory()){
			File[] fileList = filePath.listFiles();
			for(File file:fileList){
				System.out.println(file.getName());
				inputFile = new BufferedReader(new FileReader(file));
				af = inputFile.readLine();
				while((af = inputFile.readLine())!=null){
					String[] afList = af.trim().split(",");
					if(afList.length<4 || afList[2].length()<1 || afList[3].length()<1)
						continue;
					double longitude = Double.valueOf(afList[2]).doubleValue();
					double latitude = Double.valueOf(afList[3]).doubleValue();
					longitude = ((int)(longitude*1000000)/10000+0.5)/100.0;
					latitude = ((int)(latitude*1000000)/10000+0.5)/100.0;
					String key = String.valueOf(longitude)+","+String.valueOf(latitude);
					if(pointCount.containsKey(key)){
						pointCount.put(key, pointCount.get(key)+1);
					}else{
						pointCount.put(key,1);
					}
				}
				inputFile.close();
			}
		}else{
			System.out.println(filePath.getName());
			inputFile = new BufferedReader(new FileReader(filePath));
			af = inputFile.readLine();
			while((af = inputFile.readLine())!=null){
				String[] afList = af.trim().split(",");
				if(afList.length<4 || afList[2].length()<1 || afList[3].length()<1)
					continue;
				double longitude = Double.valueOf(afList[2]).doubleValue();
				double latitude = Double.valueOf(afList[3]).doubleValue();
				longitude = ((int)(longitude*1000000)/10000+0.5)/100.0;
				latitude = ((int)(latitude*1000000)/10000+0.5)/100.0;
				String key = String.valueOf(longitude)+","+String.valueOf(latitude);
				if(pointCount.containsKey(key)){
					pointCount.put(key, pointCount.get(key)+1);
				}else{
					pointCount.put(key,1);
				}
			}
			inputFile.close();
		}
		//����point����
		//ת����json��ʽ�������Ŀ���ļ�
		maxValue = 0;
		String jsonString="var data = '[";
		for(String key:pointCount.keySet()){
			String[] keyList = key.trim().split(",");
			Point point = new Point();
			point.lng = Double.valueOf(keyList[0]).doubleValue();
			point.lat = Double.valueOf(keyList[1]).doubleValue();
			point.count = pointCount.get(key);
			if(point.count>maxValue){
				maxValue = point.count;
			}
			jsonString += JSON.toJSONString(point)+",";
		}
		jsonString=jsonString.substring(0, jsonString.length()-1)+"]'";
		jsonFile = new FileWriter(outputFile);
		jsonFile.write(jsonString);
		jsonFile.close();
		System.out.println("maxValue="+maxValue);
		System.out.println("Finished!");
	}
}
