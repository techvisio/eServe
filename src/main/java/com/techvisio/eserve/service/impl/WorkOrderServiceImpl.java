package com.techvisio.eserve.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ClientConfig;
import com.techvisio.eserve.beans.ComplaintSearchData;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.PmsWorkOrder;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SearchWorkOrder;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.beans.WorkItemSearchCriteria;
import com.techvisio.eserve.beans.WorkOrder;
import com.techvisio.eserve.beans.WorkOrderAssignment;
import com.techvisio.eserve.beans.WorkOrderEquipment;
import com.techvisio.eserve.beans.WorkOrderResolution;
import com.techvisio.eserve.factory.WorkItemFactory;
import com.techvisio.eserve.icc.WorkOrderServiceICC;
import com.techvisio.eserve.manager.WorkOrderManager;
import com.techvisio.eserve.manager.impl.ClientConfiguration;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.service.EntityLockService;
import com.techvisio.eserve.service.WorkItemService;
import com.techvisio.eserve.service.WorkOrderService;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;
import com.techvisio.eserve.util.ServiceLocator;

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

	@Autowired
	ServiceLocator servicelocator;

	@Override
	public Long saveWorkOrder(WorkOrder workOrder) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrder=workOrderServiceICC.preSaveWorkOrder(workOrder);
		Long workOrderId = workOrderManager.saveWorkOrder(workOrder);
		workOrder=workOrderServiceICC.postSaveWorkOrder(workOrder);
		return workOrderId;
	}

	@Override
	public WorkOrder getWorkOrder(Long workOrderId) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrderServiceICC.preGetWorkOrderById();
		WorkOrder workOrder = workOrderManager.getWorkOrder(workOrderId);
		workOrder = workOrderServiceICC.postGetWorkOrderById(workOrder); 
		return workOrder;
	}

	@Override
	public Customer getCustomerBasicInfo(Long customerId) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrderServiceICC.preGetCustomerBasicInfo();
		Customer customer =  workOrderManager.getCustomerBasicInfo(customerId);
		customer = workOrderServiceICC.postGetCustomerBasicInfo(customer);
		return customer;
	}

	@Override
	public Unit getUnitBasicInfo(Long unitId) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrderServiceICC.preGetUnitBasicInfo();
		Unit unit = workOrderManager.getUnitBasicInfo(unitId);
		unit = workOrderServiceICC.postGetUnitBasicInfo(unit);
		return unit;
	}

	@Override
	public void saveWorkOrderResolution(Long workOrderId,
			WorkOrderResolution workOrderResolution) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrderResolution=workOrderServiceICC.preSaveWorkOrderResolution(workOrderResolution);
		workOrderManager.saveWorkOrderResolution(workOrderId, workOrderResolution);
		workOrderResolution = workOrderServiceICC.postSaveWorkOrderResolution(workOrderResolution);
	}

	@Override
	public WorkOrderResolution getWorkOrderResolution(Long workOrderId) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrderServiceICC.preGetWorkOrderResolution();
		WorkOrderResolution workOrderResolution = workOrderManager.getWorkOrderResolution(workOrderId);
		workOrderResolution = workOrderServiceICC.postGetWorkOrderResolution(workOrderResolution);
		return workOrderResolution;
	}

	@Override
	public void saveWorkOrderAssignment(Long workOrderId,
			WorkOrderAssignment workOrderAssignment) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrderAssignment=workOrderServiceICC.preSaveWorkOrderAssignment(workOrderAssignment);
		workOrderManager.saveWorkOrderAssignment(workOrderId, workOrderAssignment);
		workOrderAssignment = workOrderServiceICC.postSaveWorkOrderAssignment(workOrderAssignment);
	}

	@Override
	public WorkOrderAssignment getWorkOrderAssignment(Long workOrderId) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrderServiceICC.preGetWorkOrderAssignment();
		WorkOrderAssignment assignment = workOrderManager.getWorkOrderAssignment(workOrderId);
		assignment = workOrderServiceICC.postGetWorkOrderAssignment(assignment);
		return assignment;
	}

	@Override
	public List<SearchComplaintCustomer> getCustomerForComplaintByCriteria(
			SearchCriteria searchCriteria) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrderServiceICC.preGetCustomerForComplaintByCriteria();
		List<SearchComplaintCustomer> complaintCustomers = workOrderManager.getCustomerForComplaintByCriteria(searchCriteria);
		complaintCustomers = workOrderServiceICC.postGetCustomerForComplaintByCriteria(complaintCustomers);
		return complaintCustomers;
	}

	@Override
	public List<SearchComplaintUnit> getSearchUnitByCustomerId(Long customerId) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrderServiceICC.preGetSearchUnitByCustomerId();
		List<SearchComplaintUnit> complaintUnits = workOrderManager.getSearchUnitByCustomerId(customerId);
		complaintUnits = workOrderServiceICC.postGetSearchUnitByCustomerId(complaintUnits);
		return complaintUnits;
	}

	@Override
	public List<SearchWorkOrder> getComplaintSearchByUnitId(Long unitId) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrderServiceICC.preGetComplaintSearchByUnitId();
		List<SearchWorkOrder> searchWorkOrders = workOrderManager.getComplaintSearchByUnitId(unitId);
		searchWorkOrders = workOrderServiceICC.postGetComplaintSearchByUnitId(searchWorkOrders);
		return searchWorkOrders;
	}

	@Override
	public List<WorkOrder> getAllComplaintsForUnit(Long unitId) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrderServiceICC.preGetAllComplaintsForUnit();
		List<WorkOrder> complaints= workOrderManager.getAllComplaintsForUnit(unitId);
		complaints=workOrderServiceICC.postGetAllComplaintsForUnit(complaints);
		return complaints;
	}

	@Override
	public List<ComplaintSearchData> getComplaintDataforDashboard(String type,String code) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrderServiceICC.preGetComplaintDataforDashboard();
		List<ComplaintSearchData> complaints= workOrderManager.getComplaintDataforDashboard(type,code);
		complaints = workOrderServiceICC.postGetComplaintDataforDashboard(complaints);
		return complaints;
	}

	@Override
	public List<EquipmentDetail> getEquipmentDetail(String type, Long unitId){
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrderServiceICC.preGetEquipmentDetail();
		List<EquipmentDetail> equipmentDetails= customerService.getAllEquipmentForUnitById(type, unitId);
		equipmentDetails = workOrderServiceICC.postGetEquipmentDetail(equipmentDetails);
		return equipmentDetails;
	}

	@Override
	public void saveEquipment(List<EquipmentDetail> equipmentDetails, Long complaintId) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		equipmentDetails=workOrderServiceICC.preSaveEquipment(equipmentDetails);
		for(EquipmentDetail equipmentDetail:equipmentDetails){
			Long equipmentdtlId = customerService.saveEquipmentforUnit(equipmentDetail);
			EquipmentDetail equipmentDetailFromDB = customerService.getEquipmentDetailByEquipmentId(equipmentdtlId);
			saveComplaintEquipment(equipmentDetailFromDB, complaintId);
		}
		equipmentDetails=workOrderServiceICC.postSaveEquipment(equipmentDetails);
	}

	private void saveComplaintEquipment(EquipmentDetail equipmentDetail, Long complaintId) {
		WorkOrderEquipment complaintEquipment = new WorkOrderEquipment();
		complaintEquipment.setWorkOrderId(complaintId);
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
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrderServiceICC.preGetEquipmentDetailByEquipmentId();
		EquipmentDetail equipmentDetails = customerService.getEquipmentDetailByEquipmentId(equipDtlId);
		equipmentDetails = workOrderServiceICC.postGetEquipmentDetailByEquipmentId(equipmentDetails);
		return equipmentDetails;
	}

	@Override
	public void saveWorkOrderEquipments(WorkOrderEquipment orderEquipment) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		orderEquipment=workOrderServiceICC.postSaveWorkOrderEquipments(orderEquipment);
		workOrderManager.saveWorkOrderEquipments(orderEquipment);
		orderEquipment = workOrderServiceICC.postSaveWorkOrderEquipments(orderEquipment);
	}

	@Override
	public List<WorkOrderEquipment> getWorkOrderEquipments(Long workOrderId) {
		WorkOrderServiceICC workOrderServiceICC=servicelocator.getService(WorkOrderServiceICC.class);
		workOrderServiceICC.preGetWorkOrderEquipments();
		List<WorkOrderEquipment> workOrderEquipments = workOrderManager.getWorkOrderEquipments(workOrderId);
		workOrderEquipments = workOrderServiceICC.postGetWorkOrderEquipments(workOrderEquipments);
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

