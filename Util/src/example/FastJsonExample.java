package example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import util.FileUtil;

public class FastJsonExample {

	public static void main(String[] args) {
		/*
		fastJson使用方法类似Jsonlib使用方法，如:
		JSONObject json = JSONObject.forString(jsonStr); //jsonlib
		JSONObject json = JSONObject.parseObject(jsonStr); //fastJson
		*/
		String jsonStr = "{name:\"zhou\",domain:{age:\"24\",no:\"12345\"},"
				+ "arr:[{datakey:\"dataValue\"},[\"n21\",\"n22\"],\"n2\"]}";
		JSONObject json = JSONObject.parseObject(jsonStr);
		JSONObject domain = json.getJSONObject("domain");
		String no = domain.getString("no");
		System.out.println(no);
		JSONArray arr = json.getJSONArray("arr");
		JSONObject arr0 = arr.getJSONObject(0);
		System.out.println(arr0.get("datakey"));
	}

}
