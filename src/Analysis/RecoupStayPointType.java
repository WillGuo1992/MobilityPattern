package Analysis;

import java.io.File;

import Config.Config;
import ProcessPerday.getStayRecord;
import RoutesMining.ODSelection;
/*
 * author:youg
 * date:20170123
 * 临时构建的类，用于处理之前没有计算停留点属性的用户出行链数据
 */
public class RecoupStayPointType {
	public static void main(String[] args)throws Exception{
		Config.init();
		File[] datePath = new File(Config.getAttr(Config.WorkPath)).listFiles();
		int days = 0;
		for(File file:datePath){
			//if(days++>0)
				//break;
			File stayRecordPath = new File(file.getAbsolutePath()+"\\7stayRecord");
			System.out.println(stayRecordPath.getAbsolutePath());
			File[] stayRecordFiles = stayRecordPath.listFiles();

			for(File stayfile:stayRecordFiles){
				ODSelection.importStayRecord(stayfile);
				getStayRecord.stayRecords = ODSelection.stayRecords;
				getStayRecord.calStayPointType(getStayRecord.stayRecords);
				//String stayRecordFileName = stayRecordPath.getAbsolutePath()+"s\\"+stayfile.getName();
				//System.out.println(stayRecordFileName);
				//getStayRecord.exportStayRecord(stayRecordFileName);
			}
		}//every day
	}
}
