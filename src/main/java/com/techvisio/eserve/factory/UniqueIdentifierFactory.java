package com.techvisio.eserve.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.WorkOrder;
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

		String customerCode = CommonUtil.getCurrentClient().getClientCode()+"/"+"CST";

		SequenceFactory sf=ContextProvider.getContext().getBean(SequenceFactory.class);

		Long Id = sf.getSequence(customerCode);

		if(Id==0){
			throw new RuntimeException("Enable to generate customer Code for code : "+ customerCode);
		}

		String uniqueCustomerCode=CommonUtil.getCurrentClient().getClientCode()+"/"+"CST"+String.format("%05d", Id);

		return uniqueCustomerCode;
	}

	@Override
	public String getUniqueIdentifierForUnit(Unit unit) {

		String unitCode = CommonUtil.getCurrentClient().getClientCode()+"/"+"U";

		SequenceFactory sf=ContextProvider.getContext().getBean(SequenceFactory.class);

		Long Id = sf.getSequence(unitCode);

		if(Id==0){
			throw new RuntimeException("Enable to generate unique UnitCode for code : "+ unitCode);
		}

		String uniqueUnitCode=CommonUtil.getCurrentClient().getClientCode()+"/"+"U"+String.format("%06d", Id);

		return uniqueUnitCode;
	}
	
	@Override
	public String getUniqueIdentifierForComplaint(WorkOrder customerComplaint) {

		String complaintCode = CommonUtil.getCurrentClient().getClientCode()+"/"+"C";

		SequenceFactory sf=ContextProvider.getContext().getBean(SequenceFactory.class);

		Long Id = sf.getSequence(complaintCode);

		if(Id==0){
			throw new RuntimeException("Enable to generate complaint code for code : "+ complaintCode);
		}

		String uniqueComplaintCode=CommonUtil.getCurrentClient().getClientCode()+"/"+"C"+String.format("%07d", Id);

		return uniqueComplaintCode;
	}

}
