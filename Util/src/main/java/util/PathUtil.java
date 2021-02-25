package util;


import java.io.File;

public class PathUtil {
	public static void main(String[] args) {
		System.out.println("getProjectPath():" + getProjectPath());
	}
	
	public static String getClassRootPath(){
		String path = PathUtil.class.getResource("/").getPath();
		return path;
	}
	
	public static String getProjectPath(){
		String projectPath = System.getProperty("user.dir");
		return projectPath;
	}

	public static boolean createDirs(String craeteDirPath){
		File createDirFile = new File(craeteDirPath);
		return createDirFile.mkdirs();
	}
}
