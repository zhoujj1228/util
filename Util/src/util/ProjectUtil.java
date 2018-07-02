package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.junit.Test;

public class ProjectUtil {

	public static void main(String[] args) {
		
	}
	@Test
	public static String getProjectRootPath(){
		String path = System.getProperty("user.dir");
		return path;
	}

}
