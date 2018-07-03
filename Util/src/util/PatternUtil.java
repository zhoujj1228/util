package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {
	public static void main(String[] args) {
		String noPatternString = getNoPatternString("$");
		System.out.println(noPatternString);
	}
	
	/**
	 * ƥ���Ӧ�ַ�����û�ж�Ӧ���ȵ����֣����򷵻ض�Ӧ���ȵ������ַ��������򷵻�null
	 * @param count	����
	 * @param s	ƥ����ַ���
	 * @return	��Ӧ���ȵ��ַ���
	 */
	public static String getNumberString(int count, String s){
		String model = "[0-9]{"+count+"}";
		Pattern p = Pattern.compile(model);
		Matcher m = p.matcher(s);
		if(m.find()){
			String result = m.group();
			return result;
		}
		return null;
	}
	public static List<String> getStringByPattern(String patternString, String s){
		Pattern p = Pattern.compile(patternString);
		Matcher m = p.matcher(s);
		List<String> list = new ArrayList<String>();
		while(m.find()){
			String result = m.group();
			list.add(result);
		}
		return list;
	}
	
	/**
	 * 
	 * @param sourceStr
	 * @param pattern
	 * @param patternNum ƥ��ʽ��ռλ���ĸ���
	 * @return
	 */
	public static List<List<String>> getPatternList(String sourceStr, String pattern, int patternNum){
		List<List<String>> result = new ArrayList<List<String>>();
		Pattern p = Pattern.compile(pattern);// ƥ���ģʽ
		Matcher matcher = p.matcher(sourceStr);
		while (matcher.find()) {
			List<String> temp = new ArrayList<String>();
			for(int i = 0; i < patternNum + 1; i++){
				String s = matcher.group(i);
				temp.add(s);
			}
			result.add(temp);
		}
		return result;
	}
	
	public static String getNoPatternString(String patternStr){
		String result = Matcher.quoteReplacement(patternStr);
		return result;
	}
}