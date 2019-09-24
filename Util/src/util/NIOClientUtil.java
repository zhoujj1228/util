package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 报文格式为：6位长度头 + 报文正文
 * @author Jay
 *
 */
public class NIOClientUtil implements Runnable {
	
	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 6666;
		int headLength = 6;
		String encode = "UTF-8";
		String defaultRspStr = "NIOClientUtil defaultRspStr defaultRspStr defaultRspStr";
		new NIOClientUtil(host, port, headLength, encode, defaultRspStr).run();
	}
	

    private SocketChannel socketChannel;
    private int port;
    private Selector selector;
    private String host;
    private boolean stop;
	private int headLength;
	private String encode;
	private String defaultRspStr;

    public NIOClientUtil(String host, int port, int headLength, String encode, String defaultRspStr) {

        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        this.headLength = headLength;
        this.encode = encode;
        this.defaultRspStr = defaultRspStr;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(host, port));
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void run() {
		
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    handleInput(key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel sc = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                if (sc.finishConnect()) {
                    //在通道上注册感兴趣事件为读
                    sc.register(selector, SelectionKey.OP_READ);
                    doWrite(sc, defaultRspStr, encode, headLength);//发送命令
                } else {
                    //连接失败，退出
                    System.exit(1);
                }
            }
            if (key.isReadable()) {//读取消息
            	int bodyLength = readHead(sc, headLength, encode);
            	if(bodyLength < 0) {
            		System.out.println("Server is closed");
            	}else {
            		String readBody = readBody(sc, bodyLength, encode);
            		System.out.println("readBody:" + readBody);
            	}
            	sc.close();
            	this.stop = true;
            }

        }
    }

    private void doConnect() throws IOException {
        //如果直接连接成功，则注册到多路复用器上，发送请求信息，读应答
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel, defaultRspStr, encode, headLength);
        } else {
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel socketChannel, String defaultRspStr, String encode, int lengthSize) throws IOException {
    	byte[] bodyBytes = defaultRspStr.getBytes(encode);
    	byte[] headBytes = getFixLengthStrWithZero(bodyBytes.length, lengthSize).getBytes(encode);
    	ByteBuffer headBuffer = ByteBuffer.wrap(headBytes);
    	ByteBuffer bodyBuffer = ByteBuffer.wrap(bodyBytes);
    	
        socketChannel.write(headBuffer);
        socketChannel.write(bodyBuffer);
        System.out.println("客户端发送命令成功");
    }
    
    private static String getFixLengthStrWithZero(int length, int lengthSize) {
		String result = length + "";
		while(result.length() < lengthSize) {
			result = "0" + result;
		}
		return result;
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
				System.out.println("read fail");
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
	
	private static String readBody(SocketChannel sc, int bodyLength, String encode) {
		String message = readLengthMessage(sc, bodyLength, encode);
		return message;
	}
}
