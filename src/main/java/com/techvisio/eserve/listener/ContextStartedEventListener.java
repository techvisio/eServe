package com.techvisio.eserve.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.techvisio.eserve.async.process.AsyncMessageConsumer;

public class ContextStartedEventListener implements
ApplicationListener<ContextRefreshedEvent> {

@Override
public void onApplicationEvent(ContextRefreshedEvent event) {
	//TODO:Add all all the non processed msg to asyn queue queue from Table
	
	//TODO:Start a new thread to process the asyn messages
	Thread asyncMessageProducerThread=new Thread(new Runnable() {
		
		@Override
		public void run() {
			AsyncMessageConsumer consumer=new AsyncMessageConsumer();
			consumer.startConsumer();
		}
	});
	
	asyncMessageProducerThread.start();
}
}
