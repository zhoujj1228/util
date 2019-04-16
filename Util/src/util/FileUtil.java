package util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;


public class FileUtil {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args){
		File file = new File("E:\\备份\\");
		TreeMap<String, TreeMap> fileSubPathMap = getFileSubPath(file, false);
		String off = "--";
		itorFilePathMap(fileSubPathMap, off);
	}
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void itorFilePathMap(TreeMap<?, TreeMap> fileSubPathMap, String off) {
		Set<String> keySet = (Set<String>) fileSubPathMap.keySet();
		for(String name : keySet){
			System.out.println(off + name);
			TreeMap subFiles = fileSubPathMap.get(name);
			if(subFiles != null){
				itorFilePathMap(subFiles, off + "--");
			}
		}
	}

	/**
	 * 获取目录列表
	 * @param supFile
	 * @param isNeedFile
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static TreeMap<String, TreeMap> getFileSubPath(File supFile, boolean isNeedFile){
		TreeMap<String, TreeMap> result = new TreeMap<String, TreeMap>();
		File[] listFiles = supFile.listFiles();
		for(File file : listFiles){
			if(!isNeedFile && !file.isDirectory()){
				continue;
			}
			if(file.listFiles() == null || file.listFiles().length == 0){
				//result.put(i++ + "." + file.getName(), null);
				result.put(file.getName(), null);
			}else{
				TreeMap<String, TreeMap> temp = getFileSubPath(file, isNeedFile);
				//result.put(i++ + "." + file.getName(), temp);
				result.put(file.getName(), temp);
			}
		}
		return result;
	}
	
	
	public static void writeByFileAppendWithEncodeByList(List<String> list ,File file ,String encode){
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file,true);
			for(String s : list) {
				s = s + "\t";
				fos.write(s.getBytes(encode));
			}
			fos.write("\n".getBytes(encode));
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static boolean deleteAllSubFile(File file){
		File[] listFiles = file.listFiles();
		for(int i = 0; i < listFiles.length; i++){
			if(listFiles[i].isFile()){
				listFiles[i].delete();
			}else{
				deleteAllSubFile(listFiles[i]);
				listFiles[i].delete();
			}
		}
		return true;
	}
	
	public static boolean deleteAllFromDir(File tempFileDir){
		File[] listFiles = tempFileDir.listFiles();
		for(int i = 0; i < listFiles.length; i++){
			if(listFiles[i].isFile()){
				listFiles[i].delete();
			}else{
				deleteAllFromDir(listFiles[i]);
			}
		}
		tempFileDir.delete();
		return true;
	}
	
	/**
	 * 读取文件内容
	 * @param file
	 * @return 文件内容（String）
	 */
	public static boolean copyFile(File fromFile, File toFile){
		FileInputStream input = null;
		FileOutputStream output = null;
		try {
			input = new FileInputStream(fromFile);
			output = new FileOutputStream(toFile);
			int in = input.read();
			while(in != -1){
				output.write(in);
				in = input.read();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		} finally{
			if(input != null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(output != null){
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	public static String readByFile(File file){
		String result = null;
		FileInputStream is =null;
		BufferedReader bd =null;
		try {
			is = new FileInputStream(file);
			bd = new BufferedReader(new InputStreamReader(is));
			StringBuffer sb = new StringBuffer();
			String s = bd.readLine();
			while(s != null){
				sb.append(s);
				s = bd.readLine();
			}
			result = sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(bd != null){
					bd.close();
				}
				if(is != null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 根据路径读取文件内容
	 * @param path
	 * @return
	 */
	public static String readByPath(String path){
		File file = new File(path);
		return readByFile(file);
	}
	
	public static boolean writeByFileWithNoEncoding(String data ,File file){
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			fw.write(data);
			fw.flush();
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
		} finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	public static boolean writeByFileWithEncoding(String data ,File file ,String encoding){
		OutputStreamWriter fw = null;
		try {
			fw = new OutputStreamWriter(new FileOutputStream(file), encoding);
			fw.write(data);
			fw.flush();
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		} finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	/**
	 * 读取文件内容并将有标志的所在行包装为List返回
	 * @param file 文件
	 * @param checkFlag 检查字段
	 * @return 有标志的所有行list
	 */
	public static List<String> checkFileFlag(File file, String checkFlag){

		List<String> result = null;
		FileInputStream is =null;
		BufferedReader bd =null;
		try {
			is = new FileInputStream(file);
			bd = new BufferedReader(new InputStreamReader(is));
			String s = bd.readLine();
			result = new ArrayList<String>();
			while(s != null){
				if(s.indexOf(checkFlag) > 0){
					result.add(s);
				}
				s = bd.readLine();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(bd != null){
					bd.close();
				}
				if(is != null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	
	}
	/**
	 * 获得某个目录下所有文件
	 * @param file 文件
	 * @return 所有文件的集合list<FILE>
	 */
	public static List<File> getAllFileByFile(File file){
		List<File> list = new ArrayList<File>();
		if(file.isDirectory()){
			String[] fileList = file.list();
			String parentPath = file.getPath();
			for(int i = 0; i < fileList.length; i++){
				
				list.addAll(getAllFileByFile(new File(parentPath+"/"+fileList[i])));
			}
		}else if(file.isFile()){
			list.add(file);
		}
		return list;
	}
	
	/**
	 * 读取文件内容
	 * @param file 文件
	 * @return 文件内容（List）
	 * 
	 */
	public static List<String> readByFileToList(File file, String charSet){
		List<String> result = null;
		FileInputStream is =null;
		BufferedReader bd =null;
		try {
			is = new FileInputStream(file);
			if(charSet == null){
				bd = new BufferedReader(new InputStreamReader(is));
			}else{
				bd = new BufferedReader(new InputStreamReader(is, charSet));
			}
			
			List<String> list = new ArrayList<String>();
			String s = bd.readLine();
			while(s != null){
				list.add(s);
				s = bd.readLine();
			}
			result = list;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(bd != null){
					bd.close();
				}
				if(is != null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	
	
	
	/**
	 * 
	 * @param is	输入流
	 * @param length	读取长度
	 * @return	读取字节数组
	 * @throws IOException
	 */
	public static byte[] readLenContent(InputStream is, int length)
			throws IOException {
		
		int count = 0;
		int offset = 0;

		byte[] retData = new byte[length];

		while ((count = is.read(retData, offset, length - offset)) != -1) {
			
			offset += count;
			if (offset == length)
				break;
		}

		return retData;
	}
	
	
	
	
	
	public static String readByFileWithEncoding(File file,String encoding){
		String result = null;
		FileInputStream is =null;
		BufferedReader bd =null;
		try {
			is = new FileInputStream(file);
			bd = new BufferedReader(new InputStreamReader(is,encoding));
			StringBuffer sb = new StringBuffer();
			String s = bd.readLine();
			while(s != null){
				sb.append(s);
				s = bd.readLine();
			}
			result = sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(bd != null){
					bd.close();
				}
				if(is != null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static List<String> compareTwoFileSimple(File createfile, File sourcefile){
		List<String> list1 = readByFileToList(createfile, null);
		List<String> list2 = readByFileToList(sourcefile, null);
		String fileName = createfile.getName();
		System.out.println("对比文件"+fileName);
		List<String> differentList = new ArrayList<String>();
		for(String s : list2){
			if(!list1.contains(s)){
				System.out.println("different原来文件多"+s);
				differentList.add(s);
			}
		}
		for(String s : list1){
			if(!list2.contains(s)){
				System.out.println("different生成文件多"+s);
				differentList.add(s);
			}
		}
		if(differentList.size() == 0){
			System.out.println("文件相同");
		}
		return null;
	}
	

	public static void writeFile(File file,String s){
		FileWriter fw1 = null;
		try {
			fw1 = new FileWriter(file);
			fw1.write(s);
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally{
			if(fw1 != null){
				try {
					fw1.close();
				} catch (IOException e) {
					 
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void writeFileAppendNoEncode(File file,String s){
		FileWriter fw2 = null;
		try {
			fw2 = new FileWriter(file,true);
			fw2.write(s);
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally{
			if(fw2 != null){
				try {
					fw2.close();
				} catch (IOException e) {
					 
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void writeFileAppendWithEncode(File file, String s, String encode){
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file,true);
			fos.write(s.getBytes(encode));
			fos.flush();
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally{
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					 
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * @param lists
	 * @return 交换了行列的List
	 */
	public static List<List<String>> tranHangAndLieList(List<List<String>> lists) {
		List<List<String>> results = new ArrayList<List<String>>();
		for(int i = 0; i < lists.size(); i++){
			List<String> list = lists.get(i);
			//System.out.println();
			for(int j = 0; j < list.size(); j++){
				//System.out.print(list.get(j));
				if(results.size() > j){
					results.get(j).add(list.get(j));
				}else{
					List<String> temp = new ArrayList<String>();
					temp.add(list.get(j));
					results.add(temp);
				}
			}
		}
		//System.out.println("--------------");
		return results;
	}
	
	
	
	public static File createFile(String path){
		File file = new File(path);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	public static File createFileDeleteSource(String path){
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return file;
	}
	
	
	
	public static String readByFileWithEncodingWithLineBreak(File file,String encoding){
		String result = null;
		FileInputStream is =null;
		BufferedReader bd =null;
		try {
			is = new FileInputStream(file);
			bd = new BufferedReader(new InputStreamReader(is,encoding));
			StringBuffer sb = new StringBuffer();
			String s = bd.readLine();
			while(s != null){
				sb.append(s + "\n");
				s = bd.readLine();
			}
			result = sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(bd != null){
					bd.close();
				}
				if(is != null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static void createDIRbyPath(String rootPath, String dir){
		File file = new File(rootPath + dir);
		file.mkdirs();
	}
	
	public static void createDIRbyPath(String path){
        File file = new File(path);
        file.mkdirs();
    }
	
	public static byte[] readFileToByteByInt(String filePath) {
		InputStream fis;
		ByteArrayOutputStream bos;
		byte[] bytes = null;
		try {
			fis = new FileInputStream(filePath);
			bos = new ByteArrayOutputStream();
			int i = 0;
			while((i = fis.read()) != -1){
				bos.write(i);
			}
			bytes = bos.toByteArray();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return bytes;
	}
	
	public static byte[] readFileToByteByByte(String filePath) {
		InputStream fis;
		ByteArrayOutputStream bos;
		byte[] bytes = null;
		try {
			fis = new FileInputStream(filePath);
			bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int i;
			while((i = fis.read(buffer)) != -1){
				bos.write(buffer, 0, i);
			}
			bytes = bos.toByteArray();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return bytes;
	}
	
	
	
	
	
	
	
	public static byte[] readBytesByFile(File file){
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int len = 1024;
			byte[] buffer = new byte[1024];
			int readlen = 0;
			while((readlen = fis.read(buffer, 0, len)) > 0){
				bos.write(buffer, 0, readlen);
			}
			byte[] bytes = bos.toByteArray();
			fis.close();
			bos.close();
			return bytes;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}



