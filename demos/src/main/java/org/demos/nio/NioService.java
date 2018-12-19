package org.demos.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioService {

	public static void main(String[] args) throws IOException {
		Selector selector = Selector.open();
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		ssc.register(selector, SelectionKey.OP_ACCEPT);

		ServerSocket serverSocket = ssc.socket();
		InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8888);
		serverSocket.bind(socketAddress);

		while (true) {
			selector.select();
			Set<SelectionKey> set = selector.selectedKeys();
			Iterator<SelectionKey> it = set.iterator();
			while (it.hasNext()) {
				SelectionKey key = it.next();
				if (key.isAcceptable()) {
					ServerSocketChannel ssChannel1 = (ServerSocketChannel) key.channel();

					// 服务器会为每个新连接创建一个 SocketChannel
					SocketChannel sChannel = ssChannel1.accept();
					sChannel.configureBlocking(false);

					// 这个新连接主要用于从客户端读取数据
					sChannel.register(selector, SelectionKey.OP_READ);
				}else if(key.isReadable()) {
                    SocketChannel sChannel = (SocketChannel) key.channel();
                    System.out.println(readDataFromSocketChannel(sChannel));
                    sChannel.close();
                }
				it.remove();
			}
		}
	}
	 private static String readDataFromSocketChannel(SocketChannel sChannel) throws IOException {

	        ByteBuffer buffer = ByteBuffer.allocate(1024);
	        StringBuilder data = new StringBuilder();

	        while (true) {

	            buffer.clear();
	            int n = sChannel.read(buffer);
	            if (n == -1) {
	                break;
	            }
	            buffer.flip();
	            int limit = buffer.limit();
	            char[] dst = new char[limit];
	            for (int i = 0; i < limit; i++) {
	                dst[i] = (char) buffer.get(i);
	            }
	            data.append(dst);
	            buffer.clear();
	        }
	        return data.toString();
	    }
}
