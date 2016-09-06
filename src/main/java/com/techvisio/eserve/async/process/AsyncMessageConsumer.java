package com.techvisio.eserve.async.process;

public class AsyncMessageConsumer {

	public void startConsumer(){
		while(true){
			AsyncMessage msg=AsyncMessageProducer.getJob();
			msg.process();
		}
	}
}
