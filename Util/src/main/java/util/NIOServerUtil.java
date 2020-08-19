package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * ���ĸ�ʽΪ��6λ����ͷ + ��������
 * @author Jay
 *
 */
public class NIOServerUtil {
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException{
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					NIOServerUtil.createNIOServer("127.0.0.1", 6666, 6, 2, "server already get", "UTF-8");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
			}
		});
		t.start();
		
	}
	
	public static void createNIOServer(String ip, int port, int headLength, int bufSize, String defaultRspStr, String encode) throws InterruptedException{
		Selector selector = null;
		ServerSocketChannel serverChannel = null;
		try {
			// ʵ����selector
			selector = Selector.open();
			// ʵ����serverChannel
			serverChannel = ServerSocketChannel.open();
			// ����Ϊ��������ʽ�����Ϊtrue��ô��Ϊ��ͳ��������ʽ
			serverChannel.configureBlocking(false);
			//��ip�Ͷ˿�
			serverChannel.socket().bind(new InetSocketAddress(ip, port));
			//ע��OP_ACCEPT�¼�
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("��ʼ����");
		while(true){
			
			try {
				int i = selector.select();
				if(i == 0){
					continue;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> iter = keys.iterator();

			while(iter.hasNext()){
				SelectionKey key = iter.next();
				iter.remove();

				if(key.isAcceptable()){
					accpetRequest(key, selector);
				}
				if(key.isReadable()){
					SocketChannel sc = (SocketChannel) key.channel();
					int bodyLength = readHead(sc, headLength, encode);
					if(bodyLength > 0) {
						String reqStr = readBody(sc, bodyLength, encode);
						System.out.println(reqStr);
						writeMessage(sc, defaultRspStr, encode, headLength);
					}
				}

			}
				
				
			
		}
	}

	private static String readBody(SocketChannel sc, int bodyLength, String encode) {
		String message = readLengthMessage(sc, bodyLength, encode);
		return message;
	}

	private static int readHead(SocketChannel sc, int headLength, String encode) {
		String message = readLengthMessage(sc, headLength, encode);
		int result = -1;
		if(message != null) {
			try {
				result = Integer.parseInt(message);
			} catch (NumberFormatException e) {
				System.out.println("can not transfer to int:" + message);
				closeSocketChannel(sc);
			}
		}
		return result;
		
	}

	private static void closeSocketChannel(SocketChannel sc) {
		try {
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String readLengthMessage(SocketChannel sc, int length, String encode) {
		String result = null;
		try {
			ByteBuffer headBuffer = ByteBuffer.allocate(length);
			int i = sc.read(headBuffer);
			if(i < 0){
				System.out.println("client is closed");
				sc.close();
			}else{
				headBuffer.flip();
				result = readByteBuffer(headBuffer, encode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private static void accpetRequest(SelectionKey key, Selector selector) {
		System.out.println("�յ���������start");
		ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
		SocketChannel sc = null;
		try {
			sc = ssc.accept();
			if(sc != null){
				sc.configureBlocking(false);
				sc.register(selector, SelectionKey.OP_READ);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("�յ���������end");
		
	}

	private static void writeMessage(SocketChannel sc, String message, String encode, int headLength) {
		/*
		 * //��һ��ʵ��
		 * ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(encode));
		 * 
		 * //�ڶ���ʵ�� 
		 * byte[] bytes = message.getBytes(encode);
		 * buffer = ByteBuffer.allocate(bytes.length); 
		 * buffer.put(bytes); 
		 * buffer.flip();
		 */
		
		try {
			System.out.println("server�յ�д������");
			byte[] bodyBytes = message.getBytes(encode);
			String headLengthStr = getFixLengthStrWithZero(bodyBytes.length, headLength);
			byte[] headBytes = headLengthStr.getBytes(encode);
			ByteBuffer headBuffer = ByteBuffer.wrap(headBytes);
			ByteBuffer bodyBuffer = ByteBuffer.wrap(bodyBytes);
			sc.write(headBuffer);
			sc.write(bodyBuffer);
			System.out.println("server д��ɹ�");
		} catch (Exception e) {
			System.out.println("-----------------writeerror:" + e.getMessage());
			e.printStackTrace();
			try {
				sc.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}


	private static String getFixLengthStrWithZero(int length, int lengthSize) {
		String result = length + "";
		while(result.length() < lengthSize) {
			result = "0" + result;
		}
		return result;
	}

	private static String readByteBuffer(ByteBuffer buffer, String encode) throws UnsupportedEncodingException {
		String result = "";
		ByteArrayOutputStream bio = new ByteArrayOutputStream();
		while(buffer.hasRemaining()){
			byte c = buffer.get();
			bio.write(c);
		}
		result = bio.toString(encode);
		return result;
	}
	
	
}
