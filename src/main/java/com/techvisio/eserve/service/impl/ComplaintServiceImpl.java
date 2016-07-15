package com.techvisio.eserve.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ComplaintAssignment;
import com.techvisio.eserve.beans.ComplaintResolution;
import com.techvisio.eserve.beans.ComplaintSearchData;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.EntityLocks;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.SearchComplaint;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.exception.EntityLockedException;
import com.techvisio.eserve.manager.ComplaintManager;
import com.techvisio.eserve.service.ComplaintService;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.service.EntityLockService;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Component
@Transactional
public class ComplaintServiceImpl implements ComplaintService{

	@Autowired
	ComplaintManager complaintManager;

	@Autowired
	EntityLockService entityLockService;

	@Autowired
	CustomerService customerService;

	@Override
	public Long saveComplaint(CustomerComplaint customerComplaint) {

		String userName = CommonUtil.getCurrentUser().getUserName();
		if(customerComplaint.getComplaintId()!=null){
			boolean isEntityLocked=entityLockService.isEntityLocked(customerComplaint.getComplaintId(), AppConstants.entityType.COMPLAINT.toString(), userName);
			if(isEntityLocked){
				throw new EntityLockedException("Current user does not hold lock for this customer");
			}
		}

		Long complaintId = complaintManager.saveComplaint(customerComplaint);		
		return complaintId;
	}

	@Override
	public CustomerComplaint getCustomerComplaint(Long complaintId) {
		CustomerComplaint customerComplaint = complaintManager.getCustomerComplaint(complaintId);
		EntityLocks entityLocks  = entityLockService.getEntity(complaintId, AppConstants.entityType.COMPLAINT.toString());
		if(entityLocks!=null){
			customerComplaint.setEdited(true);
		}
		else{
			customerComplaint.setEdited(false);
		}
		return customerComplaint;
	}

	@Override
	public Customer getCustomerBasicInfo(Long customerId) {
		Customer customer =  complaintManager.getCustomerBasicInfo(customerId);
		return customer;
	}

	@Override
	public Unit getUnitBasicInfo(Long unitId) {
		Unit unit = complaintManager.getUnitBasicInfo(unitId);
		return unit;
	}

	@Override
	public List<CustomerComplaint> getCustomerComplaints(Long customerId) {
		List<CustomerComplaint> complaints = complaintManager.getCustomerComplaints(customerId);
		return complaints;
	}

	@Override
	public void saveComplaintResolution(Long complaintId,
			ComplaintResolution complaintResolution) {
		complaintManager.saveComplaintResolution(complaintId, complaintResolution);
	}

	@Override
	public ComplaintResolution getComplaintResolution(Long complaintId) {
		ComplaintResolution complaintResolution = complaintManager.getComplaintResolution(complaintId);
		return complaintResolution;
	}

	@Override
	public void saveComplaintAssignment(Long complaintId,
			ComplaintAssignment complaintAssignment) {
		complaintManager.saveComplaintAssignment(complaintId, complaintAssignment);

	}

	@Override
	public ComplaintAssignment getComplaintAssignment(Long complaintId) {
		ComplaintAssignment assignment = complaintManager.getComplaintAssignment(complaintId);
		return assignment;
	}

	@Override
	public List<SearchComplaintCustomer> getCustomerForComplaintByCriteria(
			SearchCriteria searchCriteria) {
		List<SearchComplaintCustomer> complaintCustomers = complaintManager.getCustomerForComplaintByCriteria(searchCriteria);
		return complaintCustomers;
	}

	@Override
	public List<SearchComplaintUnit> getSearchUnitByCustomerId(Long customerId) {
		List<SearchComplaintUnit> complaintUnits = complaintManager.getSearchUnitByCustomerId(customerId);
		return complaintUnits;
	}

	@Override
	public List<SearchComplaint> getComplaintSearchByUnitId(Long unitId) {
		List<SearchComplaint> complaints = complaintManager.getComplaintSearchByUnitId(unitId);
		return complaints;
	}

	@Override
	public List<CustomerComplaint> getAllComplaintsForUnit(Long unitId) {
		List<CustomerComplaint> complaints= complaintManager.getAllComplaintsForUnit(unitId);
		return complaints;
	}

	@Override
	public List<ComplaintSearchData> getComplaintDataforDashboard(String type,String code) {
		Long clientId=CommonUtil.getCurrentClient().getClientId();
		List<ComplaintSearchData> complaints= complaintManager.getComplaintDataforDashboard( clientId,type,code);
		return complaints;
	}

	@Override
	public List<EquipmentDetail> getEquipmentDetail(String type, Long unitId){

		List<EquipmentDetail> equipmentDetails= customerService.getEquipmentDetail(type, unitId);
		return equipmentDetails;
	}

	@Override
	public void saveEquipment(EquipmentDetail equipmentDetail) {
		customerService.saveEquipment(equipmentDetail);
	}

	@Override
	public List<EquipmentDetail> getEquipmentDetailByEquipmentId(Long equipDtlId) {
		List<EquipmentDetail> equipmentDetails = customerService.getEquipmentDetailByEquipmentId(equipDtlId);
		return equipmentDetails;
	}

}

