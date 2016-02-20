package com.techvisio.eserve.manager.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ApproveUnitDtl;
import com.techvisio.eserve.beans.ComplaintAssignment;
import com.techvisio.eserve.beans.ComplaintResolution;
import com.techvisio.eserve.beans.Config;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.SearchComplaint;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.ServiceAgreement;
import com.techvisio.eserve.beans.ServiceAgreementFinanceHistory;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.ServiceRenewalBean;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.db.CacheDao;
import com.techvisio.eserve.db.CustomerDao;
import com.techvisio.eserve.manager.CustomerManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;
@Component
public class CustomerManagerImpl implements CustomerManager {

	@Autowired
	CustomerDao customerDao;

	@Autowired
	CacheDao cacheDao;

	@Override
	public Customer getCustomer(Long customerId) {
		Customer customer = customerDao.getCustomer(customerId);
		return customer;
	}

	@Override
	public List<Customer> getCustomerByCriteria(SearchCriteria searchCriteria) {
		List<Customer> customers = customerDao.getCustomerByCriteria(searchCriteria);
		return customers;
	}

	@Override
	public Long saveCustomer(Customer customer) {
//		Map<String,Object> result=new HashMap<String, Object>();
//
//		if(customer.getCustomerId()==null){
//
//			boolean isCustomerExists = customerDao.isCustomerExists(customer);
//			if(isCustomerExists){
//				result.put("existingCustomer", null);
//				result.put("success", false);
//				return result;
//			}
//		}

		customer.setClient(CommonUtil.getCurrentClient());		

		Long customerId = customerDao.saveCustomer(customer);	

//		Customer customerFromDB = customerDao.getCustomer(customerId);
//		result.put("customer", customerFromDB);
//		result.put("success", true);
		return customerId;
	}


	@Override
	public void saveUnit(List<Unit> units, Long customerId) {
		customerDao.saveUnit(units, customerId);
	}

	@Override
	public void saveUnit(Unit unit) {
		customerDao.saveUnit(unit);

	}

	@Override
	public List<Unit> getUnits(Long customerId) {
		List<Unit> units = customerDao.getUnits(customerId);
		return units;
	}

	@Override
	public List<Customer> getCustomers() {
		List<Customer> customers = customerDao.getCustomers();
		return customers;
	}

	@Override
	public void renewService(ServiceAgreement agreement){
		customerDao.renewService(agreement);
	}

	@Override
	public List<ServiceAgreementHistory> getServiceAgreementHistoryForUnit(Long unitId) {
		List<ServiceAgreementHistory> agreementHistories = customerDao.getServiceAgreementHistoryForUnit(unitId);
		return agreementHistories;
	}

	@Override
	public Unit getUnit(Long unitId) {
		Unit unit = customerDao.getUnit(unitId);
		return unit;
	}

	@Override
	public Unit approveUnit(Unit unit){

		Unit unitFromDB = null;

		if(unit.getApprovalStatus()!=AppConstants.APPROVED.charAt(0)){

			unit.setApprovalStatus(AppConstants.APPROVED.charAt(0));
			unit.setVersionId(unit.getVersionId()+1);
			customerDao.saveUnit(unit);

			ServiceAgreementHistory history = new ServiceAgreementHistory();
			history.setClient(unit.getClient());
			history.setEndDate(unit.getServiceAgreement().getContractExpireOn());
			history.setServiceType(unit.getServiceAgreement().getServiceCategory());
			history.setStartDateString(unit.getServiceAgreement().getContractStartOnString());
			history.setUnitId(unit.getServiceAgreement().getUnitId());
			customerDao.saveServiceAgreementHistory(history);

			if(unit.getServiceAgreement().getServiceAgreementFinance() != null){
				ServiceAgreementFinanceHistory financeHistory = new ServiceAgreementFinanceHistory();
				financeHistory.setAgreementAmount(unit.getServiceAgreement().getServiceAgreementFinance().getAgreementAmount());
				financeHistory.setClient(unit.getClient());
				financeHistory.setUnitId(unit.getUnitId());
				customerDao.saveServiceAgreementFinanceHistory(financeHistory);
			}
		}

		unitFromDB = customerDao.getUnit(unit.getUnitId());
		return unitFromDB;
	}

	@Override
	public ApproveUnitDtl getUnitForApproval(Long unitId) {
		ApproveUnitDtl approveUnitDtl = customerDao.getUnitForApproval(unitId);
		return approveUnitDtl;
	}

}
