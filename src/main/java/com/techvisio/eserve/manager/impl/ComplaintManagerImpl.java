package com.techvisio.eserve.manager.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

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
import com.techvisio.eserve.db.ComplaintDao;
import com.techvisio.eserve.manager.ComplaintManager;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Component
public class ComplaintManagerImpl implements ComplaintManager{

	@Autowired
	ComplaintDao complaintDao;

	@Autowired
	CacheDao cacheDao;

	@Autowired
	CustomerService customerService;

	@Override
	public CustomerComplaint getCustomerComplaint(Long complaintId) {
		CustomerComplaint complaint = complaintDao.getCustomerComplaint(complaintId);
		return complaint;
	}
	@Override
	public Map<String, Object> saveComplaint(CustomerComplaint customerComplaint){

		getSlaDateByPriority(customerComplaint);
		Map<String, Object> result = new HashMap<String, Object>();

		if(customerComplaint.getCustomerId()==null){
			result = customerService.checkCustomerExistOrNot(customerComplaint);
		
			if(!(boolean) result.get("success")){
				return result;
			}
		}

		complaintDao.saveComplaint(customerComplaint);
		
		Long customerId = complaintDao.saveComplaint(customerComplaint);	

		CustomerComplaint complaintFromDB = complaintDao.getCustomerComplaint(customerId);
		result.put("complaint", complaintFromDB);
		result.put("success", true);
		return result;
	}
	
	private void getSlaDateByPriority(CustomerComplaint customerComplaint) {

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
	}

	@Override
	public Customer getCustomerBasicInfo(Long customerId) {
		Customer customer = complaintDao.getCustomerBasicInfo(customerId);
		return customer;
	}

	@Override
	public Unit getUnitBasicInfo(Long unitId) {
		Unit unit = complaintDao.getUnitBasicInfo(unitId);
		return unit;
	}

	@Override
	public List<CustomerComplaint> getCustomerComplaints(Long customerId) {
		List<CustomerComplaint> complaints = complaintDao.getCustomerComplaints(customerId);
		return complaints;
	}

	@Override
	public void saveComplaintResolution(Long complaintId, ComplaintResolution complaintResolution) {
		complaintDao.saveComplaintResolution(complaintId, complaintResolution);
	}

	@Override
	public ComplaintResolution getComplaintResolution(Long complaintId) {
		ComplaintResolution complaintResolution = complaintDao.getComplaintResolution(complaintId);
		return complaintResolution;
	}

	@Override
	public void saveComplaintAssignment(Long complaintId, ComplaintAssignment complaintAssignment) {
		complaintDao.saveComplaintAssignment(complaintId,complaintAssignment);
	}

	@Override
	public ComplaintAssignment getComplaintAssignment(Long complaintId) {
		ComplaintAssignment complaintAssignment = complaintDao.getComplaintAssignment(complaintId);
		return complaintAssignment;
	}

	@Override
	public List<SearchComplaintCustomer> getCustomerForComplaintByCriteria(
			SearchCriteria searchCriteria) {
		List<SearchComplaintCustomer> customers = complaintDao.getCustomerForComplaintByCriteria(searchCriteria);
		if(searchCriteria.getComplaintCode() != null ){
			customers = complaintDao.getCustomerByComplaintCode(searchCriteria);
		}

		return customers;
	}

	@Override
	public List<SearchComplaintUnit> getSearchUnitByCustomerId(Long customerId) {
		List<SearchComplaintUnit> complaintUnits = complaintDao.getSearchUnitByCustomerId(customerId);
		return complaintUnits;
	}

	@Override
	public List<SearchComplaint> getComplaintSearchByUnitId(Long unitId) {
		List<SearchComplaint> complaints = complaintDao.getComplaintSearchByUnitId(unitId);
		return complaints;
	}
	
	@Override
	public List<CustomerComplaint> getAllComplaintsForUnit(Long unitId) {
		List<CustomerComplaint> complaints= complaintDao.getAllComplaintsForUnit(unitId);
		return complaints;
	}
}
