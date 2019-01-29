package util;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		/*JsonUtil td = new JsonUtil();
		td.setStr("111");
		JSONObject json = JSONObject.fromObject(td);
		String jsonStr = json.toString();
		System.out.println(jsonStr);
		String jsonNewStr = "{\"str\":\"111\",\"str1\":\"112\"}";
		JSONObject jsonNew = JSONObject.fromString(jsonNewStr);
		System.out.println(jsonNew.get("str1"));*/
		
		String jsonNewStr = "{\"str\":\"111\",\"str1\":\"112\"}";
		getDomainByJsonStr(jsonNewStr, JsonUtil.class);
		/*double sourceValue = 1.11101111111111;
		float value = (float) sourceValue ;
		System.out.println(value);*/
	}
	
	public static JSONObject getJSONObjectByStr(String jsonStr){
		JSONObject jsonObj = JSONObject.fromString(jsonStr);
		return jsonObj;
	}
	
	public static String getStrByObject(Object obj){
		JSONObject json = JSONObject.fromObject(obj);
		String jsonStr = json.toString();
		return jsonStr;
	}
	
	/**
	 * Î´Íê³É
	 * @param jsonStr
	 * @param cl
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static void getDomainByJsonStr(String jsonStr, Class<?> cl) throws IllegalArgumentException, IllegalAccessException, InstantiationException{
		JSONObject jsonObj = JSONObject.fromString(jsonStr);
		Object obj = cl.newInstance();
		Field[] fields = cl.getDeclaredFields();
		for(Field field : fields){
			Class<?> type = field.getType();
			//System.out.println("getDomainByJsonStr field name : " + field.getName());
			String name = field.getName();
			if(type.equals(String.class)){
				//System.out.println("getDomainByJsonStr type.equals is String");
				String value = jsonObj.getString(name);
				field.set(obj, value);
			}else if(type.equals(Integer.class)){
				int value = jsonObj.getInt(name);
				field.set(obj, value);
			}else if(type.equals(Boolean.class)){
				boolean value = jsonObj.getBoolean(name);
				field.set(obj, value);
			}else if(type.equals(Double.class)){
				double value = jsonObj.getDouble(name);
				field.set(obj, value);
			}else if(type.equals(Float.class)){
				double sourceValue = jsonObj.getDouble(name);
				float value = (float) sourceValue;
				field.set(obj, value);
			}else if(type.equals(String[].class)){
				JSONArray sourceValue = jsonObj.getJSONArray(name);
				String[] temp = new String[sourceValue.length()];
				for (int i = 0; i < sourceValue.length(); i++) {
					String value = sourceValue.getString(i);
					temp[i] = value;
				}
				field.set(obj, temp);
			}else if(type.equals(ArrayList.class)){
				
			}else{
				/*jsonObj.get
				getDomainByJsonStr*/
			}
		}
		
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

