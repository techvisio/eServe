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

import com.techvisio.eserve.beans.ComplaintAssignment;
import com.techvisio.eserve.beans.ComplaintResolution;
import com.techvisio.eserve.beans.Config;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.SearchComplaint;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
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
	public Map<String, Object> saveCustomer(Customer customer) {
		Map<String,Object> result=new HashMap<String, Object>();

		if(customer.getCustomerId()==null){

			boolean isCustomerExists = customerDao.isCustomerExists(customer);
			if(isCustomerExists){
				result.put("existingCustomer", null);
				result.put("success", false);
				return result;
			}
		}

		customer.setClient(CommonUtil.getCurrentClient());		

		Long customerId = customerDao.saveCustomer(customer);	

		Customer customerFromDB = customerDao.getCustomer(customerId);
		result.put("customer", customerFromDB);
		result.put("success", true);
		return result;
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
	public CustomerComplaint getCustomerComplaint(Long complaintId) {
		CustomerComplaint complaint = customerDao.getCustomerComplaint(complaintId);
		return complaint;
	}
	@Override
	public void saveComplaint(CustomerComplaint customerComplaint){

		List<Config> defaultValues = cacheDao.getDefalutValues(CommonUtil.getCurrentClient().getClientId());
		for(Config config : defaultValues){

			if(customerComplaint.getPriority().equalsIgnoreCase("C") && config.getProperty().equalsIgnoreCase(AppConstants.DefaultValues.SLA_DAYS_CRITICAL.name())){
				Date date = new Date();
				Calendar c = Calendar.getInstance(); 
				c.setTime(date); 
				c.add(Calendar.DATE, Integer.parseInt(config.getValue()));
				date = c.getTime();
				customerComplaint.setSlaDate(date);
			}

			if(customerComplaint.getPriority().equalsIgnoreCase("H") && config.getProperty().equalsIgnoreCase(AppConstants.DefaultValues.SLA_DAYS_HIGH.name())){
				Date date = new Date();
				Calendar c = Calendar.getInstance(); 
				c.setTime(date); 
				c.add(Calendar.DATE, Integer.parseInt(config.getValue()));
				date = c.getTime();
				customerComplaint.setSlaDate(date);
			}

			if(customerComplaint.getPriority().equalsIgnoreCase("M") && config.getProperty().equalsIgnoreCase(AppConstants.DefaultValues.SLA_DAYS_MEDIUM.name())){
				Date date = new Date();
				Calendar c = Calendar.getInstance(); 
				c.setTime(date); 
				c.add(Calendar.DATE, Integer.parseInt(config.getValue()));
				date = c.getTime();
				customerComplaint.setSlaDate(date);
			}

			if(customerComplaint.getPriority().equalsIgnoreCase("L") && config.getProperty().equalsIgnoreCase(AppConstants.DefaultValues.SLA_DAYS_LOW.name())){
				Date date = new Date();
				Calendar c = Calendar.getInstance(); 
				c.setTime(date); 
				c.add(Calendar.DATE, Integer.parseInt(config.getValue()));
				date = c.getTime();
				customerComplaint.setSlaDate(date);
			}
		}
		if(customerComplaint.getCustomerId()==null){

			Customer customer = new Customer();
			customer.setCustomerName(customerComplaint.getCustomerName());
			customer.setContactNo(customerComplaint.getContactNo());
			customer.setEmailId(customerComplaint.getEmailId());
			List<Unit> units = new ArrayList<Unit>();
			units.add(customerComplaint.getUnit());

			customer.setUnits(units);
			saveCustomer(customer);

			Customer customerFromDB = customerDao.getCustomer(customer.getCustomerId());
			customerComplaint.setCustomerCode(customerFromDB.getCustomerCode());
			customerComplaint.setCustomerId(customerFromDB.getCustomerId());
		}

		if(customerComplaint.getComplaintId() == null){
			customerComplaint.setStatus(AppConstants.complaintStatus.UNASSIGNED.name());
		}
		customerDao.saveComplaint(customerComplaint);
	}

	@Override
	public Customer getCustomerBasicInfo(Long customerId) {
		Customer customer = customerDao.getCustomerBasicInfo(customerId);
		return customer;
	}

	@Override
	public Unit getUnitBasicInfo(Long unitId) {
		Unit unit = customerDao.getUnitBasicInfo(unitId);
		return unit;
	}

	@Override
	public List<CustomerComplaint> getCustomerComplaints(Long customerId) {
		List<CustomerComplaint> complaints = customerDao.getCustomerComplaints(customerId);
		return complaints;
	}

	@Override
	public void saveComplaintResolution(Long complaintId, ComplaintResolution complaintResolution) {
		customerDao.saveComplaintResolution(complaintId, complaintResolution);
	}

	@Override
	public ComplaintResolution getComplaintResolution(Long complaintId) {
		ComplaintResolution complaintResolution = customerDao.getComplaintResolution(complaintId);
		return complaintResolution;
	}

	@Override
	public void saveComplaintAssignment(Long complaintId, ComplaintAssignment complaintAssignment) {
		customerDao.saveComplaintAssignment(complaintId,complaintAssignment);
	}

	@Override
	public ComplaintAssignment getComplaintAssignment(Long complaintId) {
		ComplaintAssignment complaintAssignment = customerDao.getComplaintAssignment(complaintId);
		return complaintAssignment;
	}

	@Override
	public List<SearchComplaintCustomer> getCustomerForComplaintByCriteria(
			SearchCriteria searchCriteria) {
		List<SearchComplaintCustomer> customers = customerDao.getCustomerForComplaintByCriteria(searchCriteria);
		if(searchCriteria.getComplaintCode() != null ){
			customers = customerDao.getCustomerByComplaintCode(searchCriteria);
		}
		
		return customers;
	}

	@Override
	public List<SearchComplaintUnit> getSearchUnitByCustomerId(Long customerId) {
		List<SearchComplaintUnit> complaintUnits = customerDao.getSearchUnitByCustomerId(customerId);
		return complaintUnits;
	}

	@Override
	public List<SearchComplaint> getComplaintByUnitId(Long unitId) {
		List<SearchComplaint> complaints = customerDao.getComplaintByUnitId(unitId);
		return complaints;
	}
}
