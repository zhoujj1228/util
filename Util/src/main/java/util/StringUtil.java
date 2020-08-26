package util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.stream.events.Characters;

public class StringUtil {
	public static void main(String[] args){
		List<String> list1 = new ArrayList<String>();
		list1.add("A");
		list1.add("B");
		List<String> list2 = new ArrayList<String>();
		list2.add("C");
		list2.add("B");
		System.out.println(changeDBStrToCamelStr("MU_AAA_BB"));
	}
	
	/**
	 * 将list转换为string，中间以标记字符间隔
	 * @param systemSet
	 * @param splitFlag
	 * @return
	 */
	public static String getStringSplitflag(Collection<String> systemSet, String splitFlag) {
		String result = "";
		for (String system : systemSet) {
			result = result + system +  splitFlag;
		}
		return result.substring(0, result.length() - 1);
	}
	
	
	/**
	 * 将list转换为string，中间以标记字符间隔
	 * @param collection
	 * @param splitFlag
	 * @param length 最多保留多少个结果
	 * @return
	 */
	public static String getStringSplitflag(Collection<String> collection, String splitFlag, int length) {
		String result = "";
		int size = 0;
		for (String system : collection) {
			result = result + system +  splitFlag;
			size++;
			if(size > length) {
				break;
			}
		}
		return result.substring(0, result.length() - 1);
	}
	
	
	public static String ifNullToEmptyStr(String str) {
		if(str == null) {
			return "";
		}
		return str;
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
	
	/**
	 * 转换驼峰字符串为数据库字符串
	 * 如myHashMap转为MY_HASH_MAP
	 * @param str
	 * @return
	 */
	public static String changeCamelStrToDBStr(String str) {
		char[] charArray = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < charArray.length; i++) {
			int lastIndex = i - 1;
			if (Character.isUpperCase(charArray[i])) {
				if(lastIndex > -1){
					if (Character.isLowerCase(charArray[lastIndex])) {
						sb.append("_");
					}
				}else{
					//代表是首字母
				}
			}
			sb.append(charArray[i]);
		}
		return sb.toString().toUpperCase();
	}
	
	
	/**
	 * 转换驼峰字符串为数据库字符串
	 * 如MY_HASH_MAP转为myHashMap
	 * @param dbStr
	 * @return
	 */
	public static String changeDBStrToCamelStr(String dbStr) {
		String lowerDbStr = dbStr.toLowerCase();
		char[] charArray = lowerDbStr.toCharArray();
		StringBuffer sb = new StringBuffer();
		boolean nextNeedUpper = false;
		for (int i = 0; i < charArray.length; i++) {
			if(nextNeedUpper && charArray[i] != '_') {
				sb.append(Character.toUpperCase(charArray[i]));
				nextNeedUpper = false;
				continue;
			}
			
			if(charArray[i] == '_') {
				nextNeedUpper = true;
				continue;
			}
			
			sb.append(charArray[i]);
		}
		return sb.toString();
	}
	
	/**
	 * 获得字段10进制长度头,不够前补零
	 * 
	 * @param lenNum  长度位数
	 * @param strLen  字段长度
	 * @return
	 */
	public static String getStrLengthWithZero(int lenNum, int strLen) {

		String retLen = null;
		String lenStr = "" + strLen;
		// 前补0
		while (lenStr.length() < lenNum) {
			lenStr = "0" + lenStr;
		}
		return retLen;
	}
}
