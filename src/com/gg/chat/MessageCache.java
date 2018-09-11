package com.gg.chat;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 消息缓存区
 * @author Administrator
 *
 */
public class MessageCache implements Runnable{

	private int maxCapacity = 100000;
	//消息缓冲区
	private BlockingQueue<String> queue = new LinkedBlockingQueue<String>(maxCapacity);
	
	@Override
	public void run() {
		while (!ChatService.shutDown) {
			try {
				System.out.println(queue);
				ChatService.offer(ChatService.messageQueue, queue.take());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean offer(String data) {
		if (queue.size() >= maxCapacity - 1) {
			if (queue.size() >= maxCapacity - 1) {
				System.err.println("消息队列已满");
				queue.clear();
			}
		}
		// 不成功则丢弃消息，不等待
		boolean isSuccess = queue.offer(data);
		if (!isSuccess) {
		}
		return isSuccess;
	}
}
