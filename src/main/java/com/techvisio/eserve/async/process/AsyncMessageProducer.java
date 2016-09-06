package com.techvisio.eserve.async.process;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class AsyncMessageProducer {

	private static BlockingQueue<AsyncMessage> messageQueue=new LinkedBlockingDeque<AsyncMessage>();
	
	public static void addJob(AsyncMessage msg){
		messageQueue.offer(msg);
	}
	
	public static AsyncMessage getJob(){
		try {
			return messageQueue.take();
		} catch (InterruptedException e) {
		throw new RuntimeException("Thread intrupted while waiting for a Async Job", e);
		}
	}
}
