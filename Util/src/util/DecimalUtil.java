package util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DecimalUtil {
	public static void main(String[] args) {
		double d = 11002000000.000123;
		DecimalFormat df = new DecimalFormat("###.##");
		DecimalFormat df1 = new DecimalFormat("000.00");
		System.out.println(df.format(d));
		System.out.println(df1.format(d));
	}
	/**
	 * 根据传入的模式进行格式化数字以字符串输出
	 * 常用
	 * # 一个数字，不包括0
	 * 0 一个数字
	 * . 小数分隔符
	 * 
	 * 000.000    保留三位小数，保留小数后的0 
	 * 000    不保留小数
	 * ###.###   保留三位小数, 不保留小数后的0 
	 * @param number 
	 * @param pattern
	 * @return
	 */
	public static String getNumberStr(double number, String pattern){
		DecimalFormat df = new DecimalFormat(pattern);
		String format = df.format(number);
		return format;
	}
}
