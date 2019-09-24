package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class IOUtil {
    
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fs = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\1.txt"));
        String result = toString(fs, "utf-8");
        System.out.println(result);
    }

    public static String toString(InputStream in, String encode) {
        int len = 1024;
        int off = 0;
        byte[] buffer = new byte[len];
        String result = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            while((len = in.read(buffer, off, len)) > -1){
                bos.write(buffer, off, len);
            }
            result = bos.toString(encode);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

}
