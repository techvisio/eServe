package com.techvisio.eserve.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ApproveUnitDtl;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.ServiceAgreement;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.factory.WorkItemFactory;
import com.techvisio.eserve.manager.CustomerManager;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.service.WorkItemService;
import com.techvisio.eserve.util.AppConstants;

@Component
@Transactional
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	CustomerManager customerManager;

	@Autowired
	WorkItemService workItemService; 

	@Override
	public List<Customer> getCustomers() {
		List<Customer> customers = customerManager.getCustomers();
		return customers;
	}

	@Override
	public Customer getCustomer(Long customerId) {
		Customer customer = customerManager.getCustomer(customerId); 
		return customer;
	}

	@Override
	public Long saveCustomer(Customer customer, String context) {

		Long customerId = customerManager.saveCustomer(customer, context);
		WorkItem workItem = new WorkItem();

		if(context.equalsIgnoreCase(AppConstants.DRAFT)){
			WorkItemFactory factory = new WorkItemFactory();

			workItem = factory.getWorkItem(context);

			workItem.setEntityType("CUSTOMER");
			workItem.setEntityId(customerId);

		}

		if(context.equalsIgnoreCase(AppConstants.PUBLISH)){
			WorkItemFactory factory = new WorkItemFactory();
			workItem = factory.getWorkItem(context);

			workItem.setEntityType("CUSTOMER");
			workItem.setEntityId(customerId);
		}
		workItemService.saveWorkItem(workItem);
		return customerId;
	}

	@Override
	public void saveUnit(List<Unit> units, Long customerId) {
		customerManager.saveUnit(units, customerId);
	}

	@Override
	public Long saveUnit(Unit unit, String context) {
		Long unitId = customerManager.saveUnit(unit);

		WorkItem workItem = new WorkItem();

		if(context.equalsIgnoreCase(AppConstants.DRAFT)){
			WorkItemFactory factory = new WorkItemFactory();

			workItem = factory.getWorkItem(context);
			workItem.setEntityType("UNIT");
			workItem.setEntityId(unitId);
		}

		if(context.equalsIgnoreCase(AppConstants.PUBLISH)){
			WorkItemFactory factory = new WorkItemFactory();

			workItem = factory.getWorkItem(context);
			workItem.setEntityType("UNIT");
			workItem.setEntityId(unitId);
		}
		workItemService.saveWorkItem(workItem);		
		return unitId;
	}

	@Override
	public List<Unit> getUnits(Long customerId) {
		List<Unit> units = customerManager.getUnits(customerId);
		return units;
	}

	@Override
	public List<Customer> getCustomerByCriteria(SearchCriteria searchCriteria) {
		List<Customer> customers = customerManager.getCustomerByCriteria(searchCriteria);
		return customers;
	}

	//	@Override
	//	public Map<String, Object> checkCustomerExistOrNot(CustomerComplaint customerComplaint) {
	//		
	//		Map<String, Object> result;
	//		Customer customer = createCustomerfromComplaint(customerComplaint);
	//		
	//		result = customerManager.saveCustomer(customer);
	//
	//		if(!(boolean) result.get("success")){
	//			return result;
	//		}
	//
	//		Customer customerFromDB = customerManager.getCustomer(customer.getCustomerId());
	//		customerComplaint.setCustomerCode(customerFromDB.getCustomerCode());
	//		customerComplaint.setCustomerId(customerFromDB.getCustomerId());
	//		return result;
	//	}
	//	

	@Override
	public Customer createCustomerfromComplaint(CustomerComplaint customerComplaint) {
		Customer customer = new Customer();
		customer.setCustomerName(customerComplaint.getCustomerName());
		customer.setContactNo(customerComplaint.getContactNo());
		customer.setEmailId(customerComplaint.getEmailId());
		List<Unit> units = new ArrayList<Unit>();
		units.add(customerComplaint.getUnit());

		customer.setUnits(units);
		return customer;
	}

	@Override
	public void renewService(ServiceAgreement agreement) {
		customerManager.renewService(agreement);
	}

	@Override
	public List<ServiceAgreementHistory> getServiceAgreementHistoryForUnit(Long unitId) {
		List<ServiceAgreementHistory> agreementHistories = customerManager.getServiceAgreementHistoryForUnit(unitId);
		return agreementHistories;
	}

	@Override
	public Unit getUnit(Long unitId) {
		Unit unit = customerManager.getUnit(unitId);
		return unit;
	}

	@Override
	public Unit approveUnit(Unit unit) {

		Unit unitFromDB = customerManager.approveUnit(unit);
		return unitFromDB;
	}

	@Override
	public ApproveUnitDtl getUnitForApproval(Long unitId) {
		ApproveUnitDtl approveUnitDtl = customerManager.getUnitForApproval(unitId);
		return approveUnitDtl;
	}

	@Override
	public Long saveCustomer(Customer customer) {
		Long customerId = customerManager.saveCustomer(customer);
		return customerId;
	}
}
