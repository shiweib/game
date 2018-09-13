package com.gg.chat;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 聊天
 * @author Administrator
 *
 */
public class ChatService {

	public static BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
	
	public static boolean shutDown;
	
	/**
	 * 发送聊天信息
	 * @param messageCache
	 * @param message
	 */
	public static void sendMessage(MessageCache messageCache,String message){
		messageCache.offer(message);
	}
	
	public static void offer(BlockingQueue<String> queue, String message){
		boolean succ = queue.offer(message);
		if(!succ){
			queue.poll();
			queue.offer(message);
		}
	}
	
	public static final MessageCache messageCache = new MessageCache();
	
	public static void main(String[] args) {
		
		ChatService chatService = new ChatService();
		new Thread(messageCache).start();
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 100; i++) {
			ChatTask chatTask = chatService. new ChatTask();
			fixedThreadPool.execute(chatTask);
		}
	}
	
	public class ChatTask implements Runnable{

		@Override
		public void run() {
			final MessageCache messageCache = ChatService.messageCache;
			String name = Thread.currentThread().getName();
			ChatService.sendMessage(messageCache, name + ": message");
		} 
	}
}
