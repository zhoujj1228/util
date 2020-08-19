package util;

import java.util.List;

public class RandomUtil {

	public static String randomGet(List<String> memberList) {
		String random = "";
		int index = (int) (Math.random() * memberList.size());
		random = memberList.get(index);
		return random;
	}
}
