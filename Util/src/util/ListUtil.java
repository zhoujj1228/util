package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ListUtil {

	public static void main(String[] args) {
		List<String> list1 = new ArrayList<>();
		list1.add("1");
		list1.add("2");
		list1.add("3");
		List<String> list2 = new ArrayList<>();
		list2.add("3");
		list2.add("5");
		list2.add("2");
		List<String> mergeLists = mergeLists(list1, list2);
		System.out.println(mergeLists);

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
	
	public static List mergeLists(List list1, List list2) {
		Set allSet = new HashSet<>();
		allSet.addAll(list1);
		allSet.addAll(list2);
		List list = new ArrayList<>(allSet);
		return list;
	}
}
