package util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

public class HashMapUtil {
	@Test
	public void test() throws Exception {
		HashMap map = new HashMap();
		List list1 = new ArrayList();
		list1.add("1value");
		List list2 = new ArrayList();
		list2.add("2value");
		map.put("1", list1);
		addToMapByList(map, "1", list2);
		System.out.println(map.get("1"));
		System.out.println(list1);
	}
	
	public static <T> void addToMapByList(Map<String, List<T>> map, String key, List<T> newList) {
		List<T> list = map.get(key);
		if(list == null) {
			list = new ArrayList<T>();
		}
		list.addAll(newList);
		map.put(key, list);
	}
	
	public static <T> void addToMapByTreeSetValue(Map<String, SortedSet<T>> map, String key, T obj) {
		SortedSet<T> set = map.get(key);
		if(set == null) {
			set = new TreeSet<T>();
		}
		set.add(obj);
		map.put(key, set);
	}
	

	public static <T> void addToMapByValue(Map<String, List<T>> map, String key, T obj) {
		List<T> list = map.get(key);
		if(list == null) {
			list = new ArrayList<T>();
		}
		list.add(obj);
		map.put(key, list);
	}

	public static <T> int getMapListSize(HashMap<String, List<T>> map) {
		int sum = 0;
		for(List<T> list : map.values()) {
			sum = sum + list.size();
		}
		return sum;
	}
	
	/**
	 * 对比字符串，大于则替换，替换了返回true
	 * @param map
	 * @param key
	 * @param value
	 * @return 
	 */
	public static boolean strCompareBigReplaceValue(Map<String, String> map,String key, String value) {
		String oldValue = map.get(key);
		if(oldValue == null) {
			map.put(key, value);
			return true;
		}
		if(value.compareTo(oldValue) > 0) {
			map.put(key, value);
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 对比字符串，小于则替换，替换了返回true
	 * @param map
	 * @param key
	 * @param value
	 * @return 
	 */
	public static boolean strCompareLesserReplaceValue(Map<String, String> map,String key, String value) {
		String oldValue = map.get(key);
		if(oldValue == null) {
			map.put(key, value);
			return true;
		}
		if(value.compareTo(oldValue) < 0) {
			map.put(key, value);
			return true;
		}else {
			return false;
		}
	}
}
