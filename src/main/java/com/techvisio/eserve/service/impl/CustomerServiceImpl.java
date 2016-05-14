package com.techvisio.eserve.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ApproveUnitDtl;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.GenericRequest;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.manager.CustomerManager;
import com.techvisio.eserve.service.ActivityService;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.service.WorkItemService;

@Component
@Transactional
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	CustomerManager customerManager;

	@Autowired
	WorkItemService workItemService; 

	@Autowired
	ActivityService activityService;
	
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
	public Long saveCustomer(Customer customer) {
		Long customerId = customerManager.saveCustomer(customer);
		return customerId;
	}

	@Override
	public Long saveCustomer(GenericRequest<Customer> request, String context) {

		Customer customer=request.getBussinessObject();
		String comment = request.getContextInfo().get("comment");
		Long customerId = customerManager.saveCustomer(customer, context);

		workItemService.createWorkItemForCustomerSave(context, customer,comment);
//		activityService.createActivityForCustomer(customer);
		return customerId;
	}


	@Override
	public void saveUnit(List<Unit> units, Long customerId) {
		customerManager.saveUnit(units, customerId);
	}

	@Override
	public Long saveUnit(GenericRequest<Unit> request, String context) {

		Unit unit=request.getBussinessObject();
		String comment = request.getContextInfo().get("comment");
		Long unitId = customerManager.saveUnit(unit,context);

		workItemService.createWorkItemForUnitSave(context, unitId, comment);

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

	//	@Override
	//	public void updateServiceAgreement(ServiceAgreement agreement, Long unitId) {
	//		customerManager.updateServiceAgreement(agreement, unitId);
	//	    Unit unit = getUnit(agreement.getUnitId());
	//	    createWorkItemWithRenewService(unit);
	//	}

	//	private void createWorkItemWithRenewService(Unit unit) {
	//		
	////	    if(unit.getApprovalStatus() == AppConstants.APPROVED){
	////
	////            workItemService.createWorkItemForUnit(AppConstants.PUBLISH, unit.getUnitId(),);
	////	    }
	//	}

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
		workItemService.closeAgreementApprovalWorkItem(unit.getUnitId());
		workItemService.createWorkItemForServiceRenewal(unit);
		return unitFromDB;
	}

	@Override
	public ApproveUnitDtl getUnitForApproval(Long unitId) {
		ApproveUnitDtl approveUnitDtl = customerManager.getUnitForApproval(unitId);
		return approveUnitDtl;
	}

	@Override
	public Customer getEmailId(String emailId) {
		Customer customer = customerManager.getEmailId(emailId);
		return customer;
	}

	@Override
	public Customer getContactNo(String contactNo)  {
		Customer customer = customerManager.getContactNo(contactNo);
		return customer;
	}

	@Override
	public Unit rejectUnitApproval(GenericRequest<Unit> request) {
		Unit unit = request.getBussinessObject();
		String comment = request.getContextInfo().get("comment");
		Unit unitFromDB = customerManager.rejectUnitApproval(unit);

		workItemService.workItemWorkForRejectApprovalChanges(unitFromDB, comment);
		return unitFromDB;
	}

}
