package com.techvisio.eserve.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.WorkItemSearchCriteria;
import com.techvisio.eserve.beans.WorkOrderAssignment;
import com.techvisio.eserve.beans.WorkOrderEquipment;
import com.techvisio.eserve.beans.WorkOrderResolution;
import com.techvisio.eserve.beans.ComplaintSearchData;
import com.techvisio.eserve.beans.ClientConfig;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.WorkOrder;
import com.techvisio.eserve.beans.EntityLocks;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.PmsWorkOrder;
import com.techvisio.eserve.beans.SearchWorkOrder;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.exception.EntityLockedException;
import com.techvisio.eserve.factory.WorkItemFactory;
import com.techvisio.eserve.manager.WorkOrderManager;
import com.techvisio.eserve.manager.impl.ClientConfiguration;
import com.techvisio.eserve.service.WorkOrderService;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.service.EntityLockService;
import com.techvisio.eserve.service.WorkItemService;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Component
@Transactional
public class WorkOrderServiceImpl implements WorkOrderService{

	@Autowired
	WorkOrderManager workOrderManager;

	@Autowired
	EntityLockService entityLockService;

	@Autowired
	CustomerService customerService;

	@Autowired
	ClientConfiguration configPreferences;

	@Autowired
	WorkItemService workItemService;

	@Override
	public Long saveWorkOrder(WorkOrder workOrder) {

		String userName = CommonUtil.getCurrentUser().getUserName();
		if(workOrder.getWorkOrderId()!=null){
//			boolean isEntityLocked=entityLockService.isEntityLocked(workOrder.getWorkOrderId(), AppConstants.EntityType.COMPLAINT.toString(), userName);
//			if(!isEntityLocked){
//				throw new EntityLockedException("Current user does not hold lock for this complaint");
//			}
			PmsWorkOrder pmsWorkOrder = workOrderManager.getPmsComplaintByWorkOrderId(workOrder.getWorkOrderId());
			if(pmsWorkOrder!=null){
				WorkItem workItem = workItemService.getWorkitemByWorkitemId(pmsWorkOrder.getWorkitemId());
				workItem.setStatus("CLOSE");
				workItemService.saveWorkItem(workItem);
				workOrder.setStatus(AppConstants.ComplaintStatus.UNASSIGNED.name());
			}
		}
		
		if(workOrder.getWorkOrderType()==null){
			workOrder.setWorkOrderType("Complaint");
		}

		if(workOrder.getCustomerId()==null){
			Customer customer = customerService.createCustomerfromComplaint(workOrder);
			Long customerId = customerService.saveCustomerDirect(customer);
			Customer customerFromDB = customerService.getCustomerbyId(customerId);
			workOrder.setCustomerCode(customerFromDB.getCustomerCode());
			workOrder.setCustomerId(customerFromDB.getCustomerId());
		}

		Long workOrderId = workOrderManager.saveWorkOrder(workOrder);		
		return workOrderId;
	}

	@Override
	public WorkOrder getWorkOrder(Long workOrderId) {
		WorkOrder workOrder = workOrderManager.getWorkOrder(workOrderId);
		EntityLocks entityLocks  = entityLockService.getEntity(workOrderId, AppConstants.EntityType.COMPLAINT.toString());
		if(entityLocks!=null){
			workOrder.setEdited(true);
		}
		else{
			workOrder.setEdited(false);
		}
		return workOrder;
	}

	@Override
	public Customer getCustomerBasicInfo(Long customerId) {
		Customer customer =  workOrderManager.getCustomerBasicInfo(customerId);
		return customer;
	}

	@Override
	public Unit getUnitBasicInfo(Long unitId) {
		Unit unit = workOrderManager.getUnitBasicInfo(unitId);
		return unit;
	}

	@Override
	public List<WorkOrder> getWorkOrders(Long workOrderId) {
		List<WorkOrder> workOrders = workOrderManager.getWorkOrders(workOrderId);
		return workOrders;
	}

	@Override
	public void saveWorkOrderResolution(Long workOrderId,
			WorkOrderResolution workOrderResolution) {
		workOrderManager.saveWorkOrderResolution(workOrderId, workOrderResolution);
	}

	@Override
	public WorkOrderResolution getWorkOrderResolution(Long workOrderId) {
		WorkOrderResolution workOrderResolution = workOrderManager.getWorkOrderResolution(workOrderId);
		return workOrderResolution;
	}

	@Override
	public void saveWorkOrderAssignment(Long workOrderId,
			WorkOrderAssignment workOrderAssignment) {
		workOrderManager.saveWorkOrderAssignment(workOrderId, workOrderAssignment);

	}

	@Override
	public WorkOrderAssignment getWorkOrderAssignment(Long workOrderId) {
		WorkOrderAssignment assignment = workOrderManager.getWorkOrderAssignment(workOrderId);
		return assignment;
	}

	@Override
	public List<SearchComplaintCustomer> getCustomerForComplaintByCriteria(
			SearchCriteria searchCriteria) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		List<SearchComplaintCustomer> complaintCustomers = workOrderManager.getCustomerForComplaintByCriteria(searchCriteria);
		return complaintCustomers;
	}

	@Override
	public List<SearchComplaintUnit> getSearchUnitByCustomerId(Long customerId) {
		List<SearchComplaintUnit> complaintUnits = workOrderManager.getSearchUnitByCustomerId(customerId);
		return complaintUnits;
	}

	@Override
	public List<SearchWorkOrder> getComplaintSearchByUnitId(Long unitId) {
		List<SearchWorkOrder> searchWorkOrders = workOrderManager.getComplaintSearchByUnitId(unitId);
		return searchWorkOrders;
	}

	@Override
	public List<WorkOrder> getAllComplaintsForUnit(Long unitId) {
		List<WorkOrder> complaints= workOrderManager.getAllComplaintsForUnit(unitId);
		return complaints;
	}

	@Override
	public List<ComplaintSearchData> getComplaintDataforDashboard(String type,String code) {
		Long clientId=CommonUtil.getCurrentClient().getClientId();
		List<ComplaintSearchData> complaints= workOrderManager.getComplaintDataforDashboard(type,code);
		return complaints;
	}

	@Override
	public List<EquipmentDetail> getEquipmentDetail(String type, Long unitId){

		List<EquipmentDetail> equipmentDetails= customerService.getAllEquipmentForUnitById(type, unitId);
		return equipmentDetails;
	}

	@Override
	public void saveEquipment(List<EquipmentDetail> equipmentDetails, Long complaintId) {
		for(EquipmentDetail equipmentDetail:equipmentDetails){
			Long equipmentdtlId = customerService.saveEquipmentforUnit(equipmentDetail);
			EquipmentDetail equipmentDetailFromDB = customerService.getEquipmentDetailByEquipmentId(equipmentdtlId);
			saveComplaintEquipment(equipmentDetailFromDB, complaintId);
		}
	}

	private void saveComplaintEquipment(EquipmentDetail equipmentDetail, Long complaintId) {
		WorkOrderEquipment complaintEquipment = new WorkOrderEquipment();
		complaintEquipment.setWorkOrderId(complaintId);
		complaintEquipment.setEquipment(equipmentDetail.getEquipment());
		complaintEquipment.setEquipmentDtlId(equipmentDetail.getEquipmentDtlId());
		complaintEquipment.setInstallationDate(equipmentDetail.getInstallationDate());
		complaintEquipment.setInstallationDate(equipmentDetail.getInstallationDate());
		complaintEquipment.setInvoiceNo(equipmentDetail.getInvoiceNo());
		complaintEquipment.setSerialNo(equipmentDetail.getSerialNo());
		complaintEquipment.setEquipmentType(equipmentDetail.getEquipmentType());
		complaintEquipment.setUnderWarranty(equipmentDetail.isUnderWarranty());
		complaintEquipment.setUnitId(equipmentDetail.getUnitId());
		complaintEquipment.setWarrantyUnder(equipmentDetail.getWarrantyUnder());
		complaintEquipment.setDeleted(false);
		if(equipmentDetail.isDeleted()){
			complaintEquipment.setDeleted(true);
		}

		workOrderManager.saveWorkOrderEquipments(complaintEquipment);
	}

	@Override
	public EquipmentDetail getEquipmentDetailByEquipmentId(Long equipDtlId) {
		EquipmentDetail equipmentDetails = customerService.getEquipmentDetailByEquipmentId(equipDtlId);
		return equipmentDetails;
	}

	@Override
	public void saveWorkOrderEquipments(WorkOrderEquipment orderEquipment) {
		workOrderManager.saveWorkOrderEquipments(orderEquipment);

	}

	//	@Override
	//	public Long saveUnit(Unit unit) {
	//		Long unitId = customerService.saveUnit(unit);
	//		return unitId;
	//	}

	@Override
	public List<WorkOrderEquipment> getWorkOrderEquipments(Long workOrderId) {
		List<WorkOrderEquipment> workOrderEquipments = workOrderManager.getWorkOrderEquipments(workOrderId);
		return workOrderEquipments;
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

		ClientConfig pmsDueDate = configPreferences.getConfigObject(AppConstants.PMS_DUE_DATE_REMINDER);
		int pmsDueDateCount = Integer.parseInt(pmsDueDate.getValue());
		ClientConfig pmsCalculationData = configPreferences.getConfigObject(AppConstants.PMS_CALCULATION_DATA);
		ClientConfig pmsCalculationFreqeuncy = configPreferences.getConfigObject(AppConstants.PMS_CACULATION_FREQUENCY); 
		List<String> stringToStringArray = CommonUtil.stringToStringArray(pmsCalculationData.getValue());
		List<Integer> stringArrayToIntegerArray = CommonUtil.stringArrayToIntegerArray(stringToStringArray);

		//get existing workitem for unit and type
		WorkItemSearchCriteria criteria = workItemService.getWorkitemCriteria(unit.getUnitId(),AppConstants.WorkItemType.PMS.getEntityType(), AppConstants.WorkItemType.PMS.getWorkType());
		List<WorkItem> existingWorkItem=workItemService.getActiveWorkItems(criteria, unit.getServiceAgreement());
		Collections.sort(existingWorkItem, new Comparator<WorkItem>() {

			@Override
			public int compare(WorkItem o1, WorkItem o2) {
				return o1.getDueDate().compareTo(o2.getDueDate());
			}
		});

		//workItemService.deleteWorkItemsByEntityIdAndWorkType(unit.getUnitId(), AppConstants.WorkItemType.PMS.getWorkType());

		int index=0;
		for (Integer pmsFrequencyCount : stringArrayToIntegerArray){
			Date dueDate = CommonUtil.getDueDateByPmsFrequencyCalculator(unit.getServiceAgreement()	.getContractStartOn(), pmsFrequencyCount, pmsCalculationFreqeuncy.getValue(), pmsDueDateCount);

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

	@Override
	public WorkOrder createWorkOrderByPms(Long workitemId, UnitBasicInfo basicInfo){
		PmsWorkOrder pmsWorkOrder = workOrderManager.getPmsWorkOrderByWorkitemId(workitemId);
		WorkOrder workOrderFromDB = null;
		if(pmsWorkOrder == null){

			WorkOrder workOrder = new WorkOrder();
			workOrder.setContactNo(basicInfo.getContactNo());
			workOrder.setCustomerCode(basicInfo.getCustomerCode());
			workOrder.setCustomerId(basicInfo.getCustomerId());
			workOrder.setCustomerName(basicInfo.getCustomerName());
			workOrder.setEmailId(basicInfo.getEmailId());
			workOrder.setWorkOrderType("PMS");
			workOrder.setStatus("DRAFT");
			Unit unit = customerService.getUnitById(basicInfo.getUnitId());
			workOrder.setUnit(unit);
			Long workOrderId = workOrderManager.saveWorkOrder(workOrder);

			pmsWorkOrder = new PmsWorkOrder();
			pmsWorkOrder.setWorkitemId(workitemId);
			pmsWorkOrder.setWorkOrderId(workOrderId);
			workOrderManager.createPmsComplaint(pmsWorkOrder);
			workOrderFromDB = workOrderManager.getWorkOrder(workOrderId);
			return workOrderFromDB;
		}

		workOrderFromDB = workOrderManager.getWorkOrder(pmsWorkOrder.getWorkOrderId());
		return workOrderFromDB;
	}

}

