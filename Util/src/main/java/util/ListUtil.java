package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ListUtil extends CollectionUtil{

	public static void main(String[] args) {
		List<String> list1 = new ArrayList<>();
		list1.add("1");
		list1.add("2");
		list1.add("3");
		List<String> list2 = new ArrayList<>();
		list2.add("3");
		list2.add("5");
		list2.add("2");
		List<String> mergeLists = intersectLists(list1, list2);
		System.out.println(mergeLists);

	}
	

	/**
	 * 逐行打印list
	 * @param lastTipList
	 */
	public static void displayStrList(List<String> lastTipList) {
		for (String str : lastTipList) {
			System.out.println(str);
		}
	}
	
	
	public static String getStringByListAndSplitFlag(List<String> list, String flag){
		if(list == null || list.size() == 0){
			return null;
		}
		StringBuilder result = new StringBuilder(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			//result = result + flag + list.get(i);
			result.append(flag + list.get(i));
		}
		return result.toString();
	}
	
	/**
	 * 取并集（不重复）
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List mergeLists(List list1, List list2) {
		Set allSet = new HashSet<>();
		allSet.addAll(list1);
		allSet.addAll(list2);
		List list = new ArrayList<>(allSet);
		return list;
	}
	
	/**
	 * 取交集
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List intersectLists(List list1, List list2) {
		List result = new ArrayList<>();
		result.addAll(list1);
		result.retainAll(list2);
		return result;
    }
	
	/**
	 * 截取list
	 * @param start
	 * @param end
	 * @param list
	 * @return 
	 */
	public static List getSubList(int start, int end, List list) {
		List result = new ArrayList();
		if(start < 0) {
			start = 0;
		}
		if(end < 0) {
			end = list.size() - 1;
		}
		
		for (int i = 0; i < list.size(); i++) {
			if(i >= start && i <= end) {
				result.add(list.get(i));
			}
		}
		return result;
	}
}
