package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class FastJsonUtil {
	public static JSONObject getJSONObjectByStr(String jsonStr){
		JSONObject jsonObj = JSON.parseObject(jsonStr);
		return jsonObj;
	}
	
	public static String getStrByObject(Object obj){
		String jsonStr = JSON.toJSONString(obj);
		return jsonStr;
	}
}
