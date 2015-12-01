package com.techvisio.eserve.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.util.CommonUtil;
import com.techvisio.eserve.util.ContextProvider;

@Service
public class UniqueIdentifierFactory implements UniqueIdentifierGenerator {

		
			@Autowired
			CacheManager cacheManager ;
			

			@Override
			public String getUniqueIdentifierForCustomer(Customer customer) {
				
				String registrationCode = CommonUtil.getCurrentClient().getClientCode();
				
				SequenceFactory sf=ContextProvider.getContext().getBean(SequenceFactory.class);
				
				Long Id = sf.getSequence(registrationCode);
				
				if(Id==0){
					throw new RuntimeException("Enable to generate registration no for code : "+ registrationCode);
				}
				
				String registrationNo=CommonUtil.getCurrentClient().getClientCode()+"/"+Id;
				
				return registrationNo;
			}
	}
