package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPUtil {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// String rsp = "000325ESBH141 00000375178 000015010106030006010106
		// 01010620180412989308000101062018041298930800 0001 0000 7C5DAB368710098EW9105
		// 17726706IB00412018-02-190000ssssssssssss
		// 00000001780000250||0.000000|0|0.000000|0|";
		// createThreadTCPServer(6868, "127.0.0.1", rsp , "", "");
		String rsp = createSocketClient("127.0.0.1", 6666,
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?> <Request> <Head> <TxCode>100501</TxCode><TransSerialNumber>3135910010010501012099090900000001</TransSerialNumber> </Head> <Body> <ListSource>200501</ListSource> <DataType>AccountNumber</DataType> <OrganizationID>0000</OrganizationID> <Data>701000108903574</Data> <AccountName>detectName</AccountName> </Body> </Request>",
				"UTF-8", "UTF-8");
		System.out.println(rsp);
	}

	public static void createTCPServer(int port, String ip, String repTxt, String reqEncode, String rspEncode) {
		// 初始化
		ServerSocket serverSocket = null;
		InputStream input = null;
		OutputStream output = null;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName(ip));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 开始监听
		boolean shutdown = false;
		Socket socket = null;

		try {
			while (!shutdown) {
				System.out.println("开始监听");
				socket = serverSocket.accept();
				System.out.println("监听到了一个请求");
				socket.setKeepAlive(true);

				// 解析接入数据
				input = socket.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[64];
				int len = 0;
				while ((len = input.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
					System.out.println(len);
				}
				byte[] reqBytes = baos.toByteArray();
				String reqStr = new String(reqBytes, reqEncode);
				System.out.println("接收到一笔请求报文：\n" + reqStr);

				// 返回数据
				output = socket.getOutputStream();
				// repTxt = "111";
				System.out.println("返回报文设置为：" + repTxt);
				output.write(repTxt.getBytes(rspEncode));
				output.flush();

				System.out.println("server end");
			}

		} catch (Exception e) {
			e.printStackTrace();
			shutdown = true;
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void createThreadTCPServer(int port, String ip, String repTxt, String reqEncode, String rspEncode) {
		// 初始化
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName(ip));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 开始监听
		boolean shutdown = false;
		Socket socket = null;
		while (!shutdown) {
			try {
				System.out.println("开始监听");
				socket = serverSocket.accept();
				System.out.println("监听到了一个请求");
				Thread t = new Thread(new TCPServcrService(socket));
				t.start();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("server end");
				shutdown = true;
			}

		}
	}

	public static String createSocketClient(String ip, int port, String reqTxt, String reqEncode, String rspEncode)
			throws UnsupportedEncodingException {
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		byte[] rspBytes = null;
		try {
			byte[] reqBytes = reqTxt.getBytes(reqEncode);
			try {
				socket = new Socket(ip, port);
				socket.setSoTimeout(60000);
				socket.setTcpNoDelay(true);
				socket.setTrafficClass(0x04 | 0x10);

				// 将请求数据写出到Socket输出流
				os = new BufferedOutputStream(socket.getOutputStream());
				System.out.println("req:" + new String(reqBytes, reqEncode));
				os.write(reqBytes);
				os.flush();
				//不执行这一步服务方无法收到-1结束标记
				socket.shutdownOutput();

				// 从Socket输入流中读取响应数据
				is = new BufferedInputStream(socket.getInputStream());
				rspBytes = readContent(is);
				System.out.println("rsp:" + new String(rspBytes, rspEncode));

			} catch (Exception e) {
				e.printStackTrace();
				throw new IOException("通讯异常");
			} finally {
				// 关闭连接
				closeConnect(socket, os, is);
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new String(rspBytes, rspEncode);
	}

	private static byte[] readContent(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = is.read(buffer)) > -1) {
			bos.write(buffer, 0, len);
		}
		return bos.toByteArray();
	}

	private static byte[] readContentByLen(InputStream is, int headLength) throws IOException {
		byte[] headData = readLenContent(is, headLength);
		// 获得交易长度
		int length = Integer.parseInt(new String(headData));
		// 按指定长度读取报文
		byte[] reqData = readLenContent(is, length);

		return reqData;
	}

	private static byte[] readLenContent(InputStream is, int length) throws IOException {

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

	public static void closeConnect(Socket socket, OutputStream os, InputStream is) {
		try {
			if (is != null) {
				is.close();
				is = null;
			}

			if (os != null) {
				os.close();
				os = null;
			}

			if (socket != null) {
				socket.close();
				socket = null;
			}
		} catch (Exception e) {
		}
	}

}

class TCPServcrService implements Runnable {
	InputStream input = null;
	OutputStream output = null;
	Socket socket;
	String reqEncode;
	String rspEncode;
	String repTxt;

	TCPServcrService(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		/*try {
			Thread.currentThread().sleep(1000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/

		try {

			socket.setKeepAlive(true);

			// 解析接入数据
			input = socket.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[64];
			int len = 0;
			while ((len = input.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
				System.out.println(len);
			}
			byte[] reqBytes = baos.toByteArray();
			String reqStr = new String(reqBytes, reqEncode);
			System.out.println("接收到一笔请求报文：\n" + reqStr);

			// 返回数据
			output = socket.getOutputStream();
			// repTxt = "111";
			System.out.println("返回报文设置为：" + repTxt);
			output.write(repTxt.getBytes(rspEncode));
			output.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
