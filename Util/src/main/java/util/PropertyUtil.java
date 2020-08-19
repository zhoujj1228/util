package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.poi.hssf.record.ExtSSTRecord;

public class PropertyUtil {
	public static Properties prop;
	public static Properties initPropertyWithEncoding(File propertyFile, String encoding){
		prop = new Properties();
		FileInputStream fs;
		BufferedReader bd =null;
		try {
			fs = new FileInputStream(propertyFile);
			bd = new BufferedReader(new InputStreamReader(fs,encoding));
			prop.load(bd);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return prop;
	}
	public static Properties initPropertyNoEncoding(File propertyFile){
		prop = new Properties();
		FileInputStream fs;
		BufferedReader bd =null;
		try {
			fs = new FileInputStream(propertyFile);
			bd = new BufferedReader(new InputStreamReader(fs));
			prop.load(bd);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return prop;
	}
	/**
	 * ���ݶ�Ӧ�ַ������õ���Ӧ��ֵ
	 * @param s
	 * @return
	 */
	public static String getProperty(String s){
		if(prop == null){
			try {
				throw new Exception("���ȳ�ʼ��property");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return prop.getProperty(s);
	}
	/**
	 * ���ݶ�Ӧ�ַ��������ö�Ӧ��ֵ
	 * @param s
	 * @return
	 */
	public static void setProperty(String s, String value){
		if(prop == null){
			try {
				throw new Exception("���ȳ�ʼ��property");
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		prop.setProperty(s, value);
	}
}
