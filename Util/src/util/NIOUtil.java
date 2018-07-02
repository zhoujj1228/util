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
import java.util.LinkedList;
import java.util.Set;

public class NIOUtil {
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException{
		final LinkedList<String> readList = new LinkedList<String>();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					NIOUtil.createNIOServer("11.8.123.208", 6666, 15, readList, "server already get", "UTF-8");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
			}
		});
		t.start();
		while(true){
			
			if(readList.size() > 0){
				String msg = readList.poll();
				System.out.println("readList get msg : " + msg);
			}else{
				System.out.println("read sleep");
				Thread.currentThread().sleep(1000);
			}
		}
		
	}
	
	public static void createNIOServer(String ip, int port, int bufSize, LinkedList<String> readList, String defaultRspStr, String encode) throws InterruptedException{
		Selector selector = null;
		ServerSocketChannel serverChannel = null;
		try {
			// 实例化selector
			selector = Selector.open();
			// 实例化serverChannel
			serverChannel = ServerSocketChannel.open();
			// 设置为非阻塞方式，如果为true那么就为传统的阻塞方式
			serverChannel.configureBlocking(false);
			//绑定ip和端口
			serverChannel.socket().bind(new InetSocketAddress(ip, port));
			//注册OP_ACCEPT事件
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("开始监听");
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
					System.out.println("收到连接请求start");
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
					System.out.println("收到连接请求end");
					
				}
				if(key.isReadable()){
					System.out.println("server收到读取请求");
					SocketChannel sc = null;
					sc = (SocketChannel) key.channel();
					ByteBuffer b = ByteBuffer.allocate(bufSize);
					
					int i;
					try {
						i = sc.read(b);
						b.flip();
						System.out.println(i);
						if(i == -1){
							System.out.println("read fail");
						}else{
							String result = "";
							ByteArrayOutputStream bio = new ByteArrayOutputStream();
							while(b.hasRemaining()){
								byte c = b.get();
								bio.write(c);
							}
							result = bio.toString(encode);
							System.out.println("read success:"+result);
							readList.add(result);
						}
					} catch (IOException e) {
						e.printStackTrace();
						try {
							sc.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					
					key.interestOps(SelectionKey.OP_WRITE);
					
				}
				if(key.isWritable()){
					String message = defaultRspStr;
					ByteBuffer block = null;
					try {
						block = ByteBuffer.wrap(message.getBytes(encode));
						System.out.println("server收到写入请求");
						SocketChannel sc = null;
						sc = (SocketChannel) key.channel();
						try {
							sc.write(block);
							System.out.println("server 写入成功");
						} catch (IOException e) {
								e.printStackTrace();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
						
						

					key.interestOps(SelectionKey.OP_READ);
				}

			}
				
				
			
		}
	}
}
