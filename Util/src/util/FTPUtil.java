package util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;
import sun.net.ftp.FtpClient.TransferType;

public class FTPUtil {

	public static FtpClient connectServer(String ip, int port, String user, String password, String path) throws Exception{
		FtpClient ftpClient = FtpClient.create();
		SocketAddress addr = new InetSocketAddress(ip, port);
		ftpClient.connect(addr);
		ftpClient.login(user, password.toCharArray());
		System.out.println("login success");
		//设置传输模式
		ftpClient.setType(TransferType.BINARY);
		if(path.length() != 0){
			ftpClient.changeDirectory(path);
			System.out.println("当前目录为："+ftpClient.getWorkingDirectory());
		}
		return ftpClient;
	}
	
	/**
	 * 
	 * @param ftpClient
	 * @param loaclFile  本地文件
	 * @param remoteFile 目标文件
	 */
	public static void upload(FtpClient ftpClient, String loaclFile, String remoteFile){
		OutputStream os = null;
		FileInputStream is = null;
		try {
			os = ftpClient.putFileStream(remoteFile);
			File file_in = new File(loaclFile);
			is = new FileInputStream(file_in);
			byte[] bytes = new byte[1024];
			int c;
			while((c = is.read(bytes)) != -1){
				os.write(bytes,0,c);
			}
			System.out.println("upload success");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FtpProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(is != null){
					is.close();
				}
				if(os != null){
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void closeFTP(FtpClient ftpClient){
		try {
			ftpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void delete(FtpClient ftpClient, String ftpPath) {
		try {
			ftpClient.deleteFile(ftpPath);
		} catch (FtpProtocolException | IOException e) {
			System.out.println("删除失败:" + ftpPath);
		}
	}
	
	public static void download(FtpClient ftpClient, String loaclPath, String ftpPath) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = ftpClient.getFileStream(ftpPath);
			os = new FileOutputStream(loaclPath);
			byte[] bytes = new byte[1024];
			int i;
			while((i = is.read(bytes)) != -1){
				os.write(bytes, 0, i);
			}
			System.out.println("下载成功:" + ftpPath);
		} catch (FtpProtocolException | IOException e) {
			System.out.println("下载失败:" + ftpPath);
		}
	}
}
