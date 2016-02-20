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

	@Autowired 
	ConfigPreferences configPreferences;

	@Override
	public CustomerComplaint getCustomerComplaint(Long complaintId) {
		CustomerComplaint complaint = complaintDao.getCustomerComplaint(complaintId);
		return complaint;
	}
	@Override
	public Long saveComplaint(CustomerComplaint customerComplaint){

		Date slaDate = getSlaDateByPriority(customerComplaint.getPriority());
		customerComplaint.setSlaDate(slaDate);
		if(customerComplaint.getCustomerId()==null){
			Customer customer = customerService.createCustomerfromComplaint(customerComplaint);
			Long customerId = customerService.saveCustomer(customer);
			Customer customerFromDB = customerService.getCustomer(customerId);

			customerComplaint.setCustomerCode(customerFromDB.getCustomerCode());
			customerComplaint.setCustomerId(customerFromDB.getCustomerId());
		}

		Long complaintId = complaintDao.saveComplaint(customerComplaint);	

		CustomerComplaint complaintFromDB = complaintDao.getCustomerComplaint(complaintId);
		return complaintId;
	}

	private Date getSlaDateByPriority(String priority) {


		if(priority.equalsIgnoreCase(AppConstants.CRITICAL)){
			Date date = configPreferences.getSlaDateForCriticalIssue(CommonUtil.getCurrentClient().getClientId());		
			return date;
		}

		if(priority.equalsIgnoreCase(AppConstants.HIGH)){
			Date date = configPreferences.getSlaDateForHighIssue(CommonUtil.getCurrentClient().getClientId());
			return date;
		}

		if(priority.equalsIgnoreCase(AppConstants.MEDIUM)){
			Date date = configPreferences.getSlaDateForMediumIssue(CommonUtil.getCurrentClient().getClientId());
			return date;
		}

		if(priority.equalsIgnoreCase(AppConstants.LOW)){
			Date date = configPreferences.getSlaDateForLowIssue(CommonUtil.getCurrentClient().getClientId());
			return date;
		}
		return null;
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
