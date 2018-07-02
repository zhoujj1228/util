package util;

public class MathUtil {
	private double sin(double duijiao, double xiebian){
		double radians = Math.toRadians(duijiao);
		double duibian = xiebian * Math.sin(radians);
		return duibian;
	}
	
	private double cos(double duijiao, double xiebian){
		double radians = Math.toRadians(duijiao);
		double linbian = xiebian * Math.cos(radians);
		return linbian;
	}
}
