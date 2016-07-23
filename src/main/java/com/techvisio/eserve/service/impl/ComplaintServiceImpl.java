package com.techvisio.eserve.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ComplaintAssignment;
import com.techvisio.eserve.beans.ComplaintEquipment;
import com.techvisio.eserve.beans.ComplaintResolution;
import com.techvisio.eserve.beans.ComplaintSearchData;
import com.techvisio.eserve.beans.Config;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.EntityLocks;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.SearchComplaint;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.exception.EntityLockedException;
import com.techvisio.eserve.factory.WorkItemFactory;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.manager.ComplaintManager;
import com.techvisio.eserve.manager.impl.ClientConfiguration;
import com.techvisio.eserve.service.ComplaintService;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.service.EntityLockService;
import com.techvisio.eserve.service.WorkItemService;
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

	@Autowired
	ClientConfiguration configPreferences;

	@Autowired
	WorkItemService workItemService;


	@Override
	public Long saveComplaint(CustomerComplaint customerComplaint) {

		String userName = CommonUtil.getCurrentUser().getUserName();
		if(customerComplaint.getComplaintId()!=null){
			boolean isEntityLocked=entityLockService.isEntityLocked(customerComplaint.getComplaintId(), AppConstants.EntityType.COMPLAINT.toString(), userName);
			if(isEntityLocked){
				throw new EntityLockedException("Current user does not hold lock for this complaint");
			}
		}

		Long complaintId = complaintManager.saveComplaint(customerComplaint);		
		return complaintId;
	}

	@Override
	public CustomerComplaint getCustomerComplaint(Long complaintId) {
		CustomerComplaint customerComplaint = complaintManager.getCustomerComplaint(complaintId);
		EntityLocks entityLocks  = entityLockService.getEntity(complaintId, AppConstants.EntityType.COMPLAINT.toString());
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
	public void saveEquipment(List<EquipmentDetail> equipmentDetails, Long complaintId) {
		for(EquipmentDetail equipmentDetail:equipmentDetails){
			Long equipmentdtlId = customerService.saveEquipment(equipmentDetail);
			EquipmentDetail equipmentDetailFromDB = customerService.getEquipmentDetailByEquipmentId(equipmentdtlId);
			saveComplaintEquipment(equipmentDetailFromDB, complaintId);
		}
	}

	private void saveComplaintEquipment(EquipmentDetail equipmentDetail, Long complaintId) {
		ComplaintEquipment complaintEquipment = new ComplaintEquipment();
		complaintEquipment.setComplaintId(complaintId);
		complaintEquipment.setEquipment(equipmentDetail.getEquipment());
		complaintEquipment.setEquipmentDtlId(equipmentDetail.getEquipmentDtlId());
		complaintEquipment.setInstallationDate(equipmentDetail.getInstallationDate());
		complaintEquipment.setInstallationDateString(equipmentDetail.getInstallationDateString());
		complaintEquipment.setInvoiceNo(equipmentDetail.getInvoiceNo());
		complaintEquipment.setSerialNo(equipmentDetail.getSerialNo());
		complaintEquipment.setType(equipmentDetail.getType());
		complaintEquipment.setUnderWarranty(equipmentDetail.isUnderWarranty());
		complaintEquipment.setUnitId(equipmentDetail.getUnitId());
		complaintEquipment.setWarrantyUnder(equipmentDetail.getWarrantyUnder());
		complaintEquipment.setDeleted(false);
		if(equipmentDetail.isDeleted()){
			complaintEquipment.setDeleted(true);
		}

		complaintManager.saveComplaintEquipments(complaintEquipment);
	}

	@Override
	public EquipmentDetail getEquipmentDetailByEquipmentId(Long equipDtlId) {
		EquipmentDetail equipmentDetails = customerService.getEquipmentDetailByEquipmentId(equipDtlId);
		return equipmentDetails;
	}

	@Override
	public void saveComplaintEquipments(ComplaintEquipment complaintEquipment) {
		complaintManager.saveComplaintEquipments(complaintEquipment);

	}

	@Override
	public Long saveUnit(Unit unit) {
		Long unitId = customerService.saveUnit(unit);
		return unitId;
	}

	@Override
	public List<ComplaintEquipment> getComplaintEquipments(Long complaintId) {
		List<ComplaintEquipment> complaintEquipments = complaintManager.getComplaintEquipments(complaintId);
		return complaintEquipments;
	}

	@Override
	public void deleteEquipmentDtlInclusion(
			List<EquipmentDetail> equipmentDetails, Long unitId, Long complaintId) {

		for(EquipmentDetail equipmentDetail:equipmentDetails){
			if(equipmentDetail.isDeleted()){
				saveComplaintEquipment(equipmentDetail, complaintId);
			}
		}
		customerService.deleteEquipmentDtlInclusion(equipmentDetails, unitId);

	}

	@Override
	public void createPmsWorkItem(Unit unit){

		Config pmsCalculationData = configPreferences.getConfigObject(AppConstants.PMS_CALCULATION_DATA);
		Config pmsCalculationFreqeuncy = configPreferences.getConfigObject(AppConstants.PMS_CACULATION_FREQUENCY); 
		List<String> stringToStringArray = CommonUtil.stringToStringArray(pmsCalculationData.getValue());
		List<Integer> stringArrayToIntegerArray = CommonUtil.stringArrayToIntegerArray(stringToStringArray);

		//get existing workitem for unit and type
		List<WorkItem> existingWorkItem=workItemService.getWorkItemsByEntityIdAndEntityTypeAndWorkType(unit.getUnitId(),AppConstants.WorkItemType.PMS.getEntityType(), AppConstants.WorkItemType.PMS.getWorkType());
		Collections.sort(existingWorkItem, new Comparator<WorkItem>() {

			@Override
			public int compare(WorkItem o1, WorkItem o2) {
				return o1.getDueDate().compareTo(o2.getDueDate());
			}
		});

		workItemService.deleteWorkItemsByEntityIdAndWorkType(unit.getUnitId(), AppConstants.WorkItemType.PMS.getWorkType());

		int index=0;
		for (Integer pmsFrequencyCount : stringArrayToIntegerArray){
			Date dueDate = CommonUtil.getDueDateByPmsFrequencyCalculator(unit.getServiceAgreement()	.getContractStartOn(), pmsFrequencyCount, pmsCalculationFreqeuncy.getValue());

			WorkItem workItem=null;
			if(existingWorkItem !=null && existingWorkItem.size()>index){
				workItem=existingWorkItem.get(index++);
			}

			if (workItem == null) {
				WorkItemFactory factory = new WorkItemFactory();
				workItem  = factory.getWorkItem(AppConstants.PMS);
			}
			workItem.setDueDate(dueDate);
			workItem.setEntityId(unit.getUnitId());
			workItem.setEntityCode(unit.getUnitCode());
			workItemService.saveWorkItem(workItem);
		}
	}
}

