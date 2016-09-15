package com.techvisio.eserve.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.techvisio.eserve.async.process.AsyncEmailMessage;
import com.techvisio.eserve.async.process.AsyncMessageConsumer;
import com.techvisio.eserve.async.process.AsyncMessageProducer;
import com.techvisio.eserve.beans.CommunicationJob;
import com.techvisio.eserve.service.CommunicationService;
import com.techvisio.eserve.util.ServiceLocator;

public class ContextStartedEventListener implements
ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	ServiceLocator serviceLocator;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//TODO:Add all all the non processed msg to asyn queue queue from Table
		CommunicationService communicationService = serviceLocator.getService(CommunicationService.class);
		List<CommunicationJob> communicationJobs = communicationService.getAllNonProcessedMsg();
		for(CommunicationJob job : communicationJobs){
			AsyncEmailMessage emailMsg=serviceLocator.getService(AsyncEmailMessage.class);
			emailMsg.setCommunicationJob(job);		
			AsyncMessageProducer.addJob(emailMsg);
		}

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
