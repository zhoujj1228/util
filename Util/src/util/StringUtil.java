package util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	public static void main(String[] args){
		List<String> list1 = new ArrayList<String>();
		list1.add("A");
		list1.add("B");
		List<String> list2 = new ArrayList<String>();
		list2.add("C");
		list2.add("B");
		System.out.println(getTwoListSourceNoContainList(list1, list2) + list1.toString());
	}
	/**
	 * 根据开始与结束字符串截取字符串
	 * @param checkString 原字符串
	 * @param start 截取开始字符串
	 * @param end 截取结束字符串
	 * @return 截取的字符串
	 */
	public static String getTwoStringMiddle(String checkString, String start, String end){
		int indexEnd = checkString.length();
		int indexStart = 0;
		if(end != null){
			indexEnd = checkString.indexOf(end);
		}
		if(start != null){
			indexStart = checkString.indexOf(start)+start.length();
		}
		if(indexEnd > -1 && indexStart > -1 && indexEnd >indexStart){
			String s = checkString.substring(indexStart, indexEnd);
			return s;
		}else{
			return null;
		}
		
	}
	
	/**
	 * 根据标志字符串与偏移量截取对应长度的字符串
	 * @param checkString 原字符串
	 * @param flag 标志字符串（开始）
	 * @param offset 偏移量
	 * @param length 截取字符串长度
	 * @return 截取的字符串
	 */
	public static String getStringByFlag(String checkString, String flag, int offset, int length){
		if(checkString == null || flag == null || offset < 0 || length < 0){
			return null;
		}
		int flagIndex = checkString.indexOf(flag);
		if(flagIndex < 0){
			return null;
		}
		int start = flag.length() + flagIndex + offset;
		int end = start + length;
		if(start > checkString.length() || end > checkString.length()){
			return null;
		}
		String result = checkString.substring(start, end);
		return result;
	}
	
	/**
	 * 创建指定数量的空格
	 * @param length 需要空格的数量
	 * @return 生成的指定数量的空格
	 */
	public static String getBlankByLength(int length) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < length; i++){
			sb.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 得到两个list的交集
	 * @param sourceList
	 * @param list
	 * @return
	 */
	public static List<String> getTwoListRetainList(List<String> sourceList, List<String> list){
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		list1.addAll(sourceList);
		list2.addAll(list);
		list1.retainAll(list2);
		return list1;
	}
	
	/**
	 * 得到原list与另一个list的差集
	 * @param sourceList
	 * @param list
	 * @return
	 */
	public static List<String> getTwoListSourceNoContainList(List<String> sourceList, List<String> list){
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		list1.addAll(sourceList);
		list2.addAll(list);
		list1.removeAll(list2);
		return list1;
	}
	
	/**
	 * 得到两个list的无重复并集
	 * @param sourceList
	 * @param list
	 * @return
	 */
	public static List<String> getTwoListDictList(List<String> sourceList, List<String> list){
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		list1.addAll(sourceList);
		list2.addAll(list);
		list2.removeAll(list1);
		list1.addAll(list2);
		return list1;
	}
	
	/**
	 * 得到两个list的差集
	 * @param sourceList
	 * @param list
	 * @return
	 */
	public static List<String> getTwoListNoContainList(List<String> sourceList, List<String> list){
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		List<String> list3 = new ArrayList<String>();
		list1.addAll(sourceList);
		list2.addAll(list);
		list3.addAll(sourceList);
		list1.removeAll(list2);
		list2.removeAll(list3);
		list1.addAll(list2);
		return list1;
	}
	
	
	public static String getIntStringbyDouble(double num){
		String result = (int)num + "";
		return result;
	}
	
}
