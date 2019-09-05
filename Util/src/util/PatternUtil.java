package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {
	public static void main(String[] args) {
		String numberString = getAllNumberString("data56");
		System.out.println(numberString);
	}
	
	/**
	 * 匹配对应字符串有没有对应长度的数字，有则返回对应长度的数字字符串，无则返回null
	 * @param count	长度
	 * @param s	匹配的字符串
	 * @return	对应长度的字符串
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
	
	/**
	 * 匹配对应字符串有没有数字，有则返回第一个连续的数字字符串，无则返回null
	 * @param s	匹配的字符串
	 * @return	数字字符串
	 */
	public static String getAllNumberString(String s){
		String model = "[0-9]+";
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
	 * @param patternNum 匹配式中占位符的个数
	 * @return
	 */
	public static List<List<String>> getPatternList(String sourceStr, String pattern, int patternNum){
		List<List<String>> result = new ArrayList<List<String>>();
		Pattern p = Pattern.compile(pattern);// 匹配的模式
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
	
	
	/**
	 * 
	 * @param sourceStr 被匹配的字符串
	 * @param pattern 匹配式
	 * @return 所有匹配到的字符串，包括所有使用()包含的匹配子项
	 * 返回的结果第一个是匹配的项,第二个才是子项开始
	 */
	public static List<List<String>> getPatternList(String sourceStr, String pattern){
		List<List<String>> result = new ArrayList<List<String>>();
		Pattern p = Pattern.compile(pattern);// 匹配的模式
		Matcher matcher = p.matcher(sourceStr);
		while (matcher.find()) {
			List<String> temp = new ArrayList<String>();
			for(int i = 0; i < matcher.groupCount() + 1; i++){
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
