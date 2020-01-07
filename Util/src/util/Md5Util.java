package util;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;


public class Md5Util {
	
	@Test
	public void test() {
		System.out.println("test:" + getMd5(new File("D:\\Test\\txt\\1.txt")));
		System.out.println("test:" + getMd5("1111111111111111111111111111111111111".getBytes()));
		Assert.assertEquals("Md5Util test pass", getMd5("123".getBytes()), "202cb962ac59075b964b07152d234b70");
	}
	
	public static String getMd5(File file) {
		String result = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while((len = fis.read(buffer)) > -1) {
				bos.write(buffer, 0, len);
			}
			byte[] secretBytes = bos.toByteArray();
			result = changMd5ByteToString(secretBytes);
			return result;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
	        try {
	        	if(fis != null) {
	        		fis.close();
	        	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static String getMd5(byte[] bytes) {
		// ����һ���ֽ�����
		byte[] secretBytes = null;
		try {
			// ����һ��MD5���ܼ���ժҪ
			MessageDigest md = MessageDigest.getInstance("MD5");
			// ���ַ������м���
			md.update(bytes);
			// ��ü��ܺ������
			secretBytes = md.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("û��md5����㷨��");
		}

		String result = changMd5ByteToString(secretBytes);
		return result;
	}

	private static String changMd5ByteToString(byte[] secretBytes) {
		// �����ܺ������ת��Ϊ16��������
		String md5code = new BigInteger(1, secretBytes).toString(16);// 16��������
		// �����������δ��32λ����Ҫǰ�油0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;

	}
	
	
}
