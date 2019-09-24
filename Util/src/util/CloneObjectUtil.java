package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CloneObjectUtil {
	public static byte[] deepCloneObjectToByteArray(Object obj) throws IOException{
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(obj);
		
		return bos.toByteArray();
	}
	
	public static Object deepCloneByteArrayToObject(byte[] bytes) throws IOException, ClassNotFoundException{
		
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois.readObject();
	}
}
