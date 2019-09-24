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
	 * ���ݴ����ģʽ���и�ʽ���������ַ������
	 * ����
	 * # һ�����֣�������0
	 * 0 һ������
	 * . С���ָ���
	 * 
	 * 000.000    ������λС��������С�����0 
	 * 000    ������С��
	 * ###.###   ������λС��, ������С�����0 
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
