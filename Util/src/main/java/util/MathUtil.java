package util;

public class MathUtil {
	public static void main(String[] args) {
		double pow = MathUtil.pow(1.2, 2);
		System.out.println(pow);
	}
	
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
	
	public static double pow(double num, double time){
		return Math.pow(num, time);
	}
}
