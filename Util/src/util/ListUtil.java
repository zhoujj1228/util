package util;

import java.util.Iterator;
import java.util.List;

public class ListUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String getStringByListAndSplitFlag(List<String> list, String flag){
		if(list == null || list.size() == 0){
			return null;
		}
		String result = list.get(0);
		for (int i = 1; i < list.size(); i++) {
			result = result + flag + list.get(i);
		}
		return result;
	}
}
