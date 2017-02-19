package Model;
/*
 * 用于国测局GCJ_02和百度BD_09经纬度转换的类
 */
public class Location {
	public double gcj_lon,bd_lon;
	public double gcj_lat,bd_lat;
	public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	//GCJ_02转换成BD_09
	public void bd_encrypt(){
		double x = gcj_lon, y = gcj_lat;  
	    double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);  
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);  
	    bd_lon = z * Math.cos(theta) + 0.0065;  
	    bd_lat = z * Math.sin(theta) + 0.006;  
	}
	//BD_09转换成GCJ_02
	public void bd_decrypt(){
		double x = bd_lon - 0.0065, y = bd_lat - 0.006;  
	    double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);  
	    double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);  
	    gcj_lon = z * Math.cos(theta);  
	    gcj_lat = z * Math.sin(theta);  
	}
}
