package com.techvisio.eserve.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.util.CommonUtil;
import com.techvisio.eserve.util.ContextProvider;

@Service
public class UniqueIdentifierFactory implements UniqueIdentifierGenerator {

		
			@Autowired
			CacheManager cacheManager ;
			

			@Override
			public String getUniqueIdentifierForCustomer(Customer customer) {
				
				String customerCode = CommonUtil.getCurrentClient().getClientCode()+"/"+"CUSTOMER";
				
				SequenceFactory sf=ContextProvider.getContext().getBean(SequenceFactory.class);
				
				Long Id = sf.getSequence(customerCode);
				
				if(Id==0){
					throw new RuntimeException("Enable to generate registration no for code : "+ customerCode);
				}
				
				String uniqueCustomerCode=CommonUtil.getCurrentClient().getClientCode()+"/"+"CUSTOMER"+"/"+String.format("%05d", Id);
				
				return uniqueCustomerCode;
			}
			
			@Override
			public String getUniqueIdentifierForUnit(Unit unit) {
				
				String unitCode = CommonUtil.getCurrentClient().getClientCode()+"/"+"UNIT";
				
				SequenceFactory sf=ContextProvider.getContext().getBean(SequenceFactory.class);
				
				Long Id = sf.getSequence(unitCode);
				
				if(Id==0){
					throw new RuntimeException("Enable to generate unique UnitCode for code : "+ unitCode);
				}
				
				String uniqueUnitCode=CommonUtil.getCurrentClient().getClientCode()+"/"+"UNIT"+"/"+String.format("%05d", Id);
				
				return uniqueUnitCode;
			}
	}
