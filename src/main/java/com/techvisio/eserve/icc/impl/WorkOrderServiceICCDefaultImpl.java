package com.techvisio.eserve.icc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvisio.eserve.beans.ComplaintSearchData;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.PmsWorkOrder;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchWorkOrder;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.beans.WorkOrder;
import com.techvisio.eserve.beans.WorkOrderAssignment;
import com.techvisio.eserve.beans.WorkOrderEquipment;
import com.techvisio.eserve.beans.WorkOrderResolution;
import com.techvisio.eserve.exception.EntityLockedException;
import com.techvisio.eserve.manager.WorkOrderManager;
import com.techvisio.eserve.manager.impl.ClientConfiguration;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.service.EntityLockService;
import com.techvisio.eserve.service.WorkItemService;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Service
public class WorkOrderServiceICCDefaultImpl extends AbstractWorkOrderServiceICCImpl{

	@Autowired
	WorkOrderManager workOrderManager;

	@Autowired
	EntityLockService entityLockService;

	@Autowired
	CustomerService customerService;

	@Autowired
	WorkItemService workItemService;

	@Override
	public WorkOrder preSaveWorkOrder(WorkOrder workOrder) {
		WorkOrder workOrderPreSave=super.preSaveWorkOrder(workOrder);
		String userName = CommonUtil.getCurrentUser().getUserName();
		if(workOrder.getWorkOrderType()!=null && workOrder.getWorkOrderType().equalsIgnoreCase("Complaint")){
			if(workOrder.getWorkOrderId()!=null){
				boolean isEntityLocked=entityLockService.isEntityLocked(workOrder.getWorkOrderId(), AppConstants.EntityType.COMPLAINT.toString(), userName);
				if(!isEntityLocked){
					throw new EntityLockedException("Current user does not hold lock for this complaint");
				}
			}
		}
		PmsWorkOrder pmsWorkOrder = workOrderManager.getPmsComplaintByWorkOrderId(workOrder.getWorkOrderId());
		if(pmsWorkOrder!=null){
			WorkItem workItem = workItemService.getWorkitemByWorkitemId(pmsWorkOrder.getWorkitemId());
			workItem.setStatus("CLOSE");
			workItemService.saveWorkItem(workItem);
			workOrder.setStatus(AppConstants.ComplaintStatus.UNASSIGNED.name());
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
		return workOrderPreSave;
	}

	@Override
	public WorkOrder postSaveWorkOrder(WorkOrder workOrder) {
		WorkOrder workOrderPostSave=super.postSaveWorkOrder(workOrder);
		entityLockService.unlockEntity("COMPLAINT", workOrder.getWorkOrderId());
		return workOrderPostSave;
	}

	@Override
	public void preGetWorkOrderById(){
		super.preGetWorkOrderById();
	}

	@Override
	public WorkOrder postGetWorkOrderById(WorkOrder workOrder){
		return super.postGetWorkOrderById(workOrder);
	}

	@Override
	public void preGetCustomerBasicInfo() {
		super.preGetCustomerBasicInfo();
	}

	@Override
	public Customer postGetCustomerBasicInfo(Customer customer) {
		return super.postGetCustomerBasicInfo(customer);
	}

	@Override
	public void preGetUnitBasicInfo() {
		super.preGetUnitBasicInfo();
	}

	@Override
	public Unit postGetUnitBasicInfo(Unit unit) {
		return super.postGetUnitBasicInfo(unit);
	}

	@Override
	public WorkOrderResolution preSaveWorkOrderResolution(WorkOrderResolution workOrderResolution) {
		WorkOrderResolution workOrderResolutionPreSave = super.preSaveWorkOrderResolution(workOrderResolution);
		return workOrderResolutionPreSave;
	}

	@Override
	public WorkOrderResolution postSaveWorkOrderResolution(WorkOrderResolution workOrderResolution) {
		WorkOrderResolution workOrderResolutionPostSave = super.postSaveWorkOrderResolution(workOrderResolution);
		return workOrderResolutionPostSave;
	}

	@Override
	public void preGetWorkOrderResolution() {
		super.preGetWorkOrderResolution();
	}

	@Override
	public WorkOrderResolution postGetWorkOrderResolution(WorkOrderResolution resolution) {
		return super.postGetWorkOrderResolution(resolution);
	}

	@Override
	public WorkOrderAssignment preSaveWorkOrderAssignment(WorkOrderAssignment workOrderAssignment) {
		WorkOrderAssignment assignment = super.preSaveWorkOrderAssignment(workOrderAssignment);
		return assignment;
	}

	@Override
	public WorkOrderAssignment postSaveWorkOrderAssignment(WorkOrderAssignment workOrderAssignment) {
		WorkOrderAssignment assignment = super.postSaveWorkOrderAssignment(workOrderAssignment);
		return assignment;
	}

	@Override
	public void preGetWorkOrderAssignment() {
		super.preGetWorkOrderAssignment();
	}

	@Override
	public WorkOrderAssignment postGetWorkOrderAssignment( WorkOrderAssignment workOrderAssignment) {
		return super.postGetWorkOrderAssignment(workOrderAssignment);
	}

	@Override
	public void preGetCustomerForComplaintByCriteria(){
		super.preGetCustomerForComplaintByCriteria();
	}

	@Override
	public List<SearchComplaintCustomer> postGetCustomerForComplaintByCriteria(List<SearchComplaintCustomer> searchComplaintCustomers){
		return super.postGetCustomerForComplaintByCriteria(searchComplaintCustomers);
	}

	@Override
	public void preGetSearchUnitByCustomerId() {
		super.preGetSearchUnitByCustomerId();
	}

	@Override
	public List<SearchComplaintUnit> postGetSearchUnitByCustomerId(List<SearchComplaintUnit> units) {
		return super.postGetSearchUnitByCustomerId(units);
	}

	@Override
	public void preGetComplaintSearchByUnitId() {
		super.preGetComplaintSearchByUnitId();
	}

	@Override
	public List<SearchWorkOrder> postGetComplaintSearchByUnitId(List<SearchWorkOrder> workOrders) {
		return super.postGetComplaintSearchByUnitId(workOrders);
	}

	@Override
	public void preGetAllComplaintsForUnit() {
		super.preGetAllComplaintsForUnit();
	}

	@Override
	public List<WorkOrder> postGetAllComplaintsForUnit(List<WorkOrder> workOrders) {
		return super.postGetAllComplaintsForUnit(workOrders);
	}

	@Override
	public void preGetComplaintDataforDashboard() {
		super.preGetComplaintDataforDashboard();
	}
	@Override
	public List<ComplaintSearchData> postGetComplaintDataforDashboard(List<ComplaintSearchData> searchData) {
		return super.postGetComplaintDataforDashboard(searchData);
	}

	@Override
	public void preGetEquipmentDetail(){
		super.preGetEquipmentDetail();
	}
	@Override
	public List<EquipmentDetail> postGetEquipmentDetail(List<EquipmentDetail> equipmentDetails){
		return super.postGetEquipmentDetail(equipmentDetails);
	}

	@Override
	public List<EquipmentDetail> preSaveEquipment(List<EquipmentDetail> equipmentDetails) {
		List<EquipmentDetail> equipmentPreSave = super.preSaveEquipment(equipmentDetails);
		return equipmentPreSave;
	}

	@Override
	public List<EquipmentDetail> postSaveEquipment(List<EquipmentDetail> equipmentDetails) {
		List<EquipmentDetail> equipmentPostSave = super.postSaveEquipment(equipmentDetails);
		return equipmentPostSave;
	}

	@Override
	public void preGetEquipmentDetailByEquipmentId() {
		super.preGetEquipmentDetailByEquipmentId();
	}

	@Override
	public EquipmentDetail postGetEquipmentDetailByEquipmentId(EquipmentDetail equipmentDetail) {
		return super.postGetEquipmentDetailByEquipmentId(equipmentDetail);
	}

	@Override
	public WorkOrderEquipment preSaveWorkOrderEquipments(WorkOrderEquipment orderEquipment) {
		WorkOrderEquipment orderEquipmentPreSave = super.preSaveWorkOrderEquipments(orderEquipment);
		return orderEquipmentPreSave;
	}

	@Override
	public WorkOrderEquipment postSaveWorkOrderEquipments(WorkOrderEquipment orderEquipment) {
		WorkOrderEquipment orderEquipmentPreSave = super.postSaveWorkOrderEquipments(orderEquipment);
		return orderEquipmentPreSave;
	}

	@Override
	public void preGetWorkOrderEquipments() {
		super.preGetWorkOrderEquipments();

	}
	@Override
	public List<WorkOrderEquipment> postGetWorkOrderEquipments(List<WorkOrderEquipment> workOrderEquipments) {
		return super.postGetWorkOrderEquipments(workOrderEquipments);

	}

	@Override
	public void preDeleteEquipmentDtlInclusion() {
		super.preDeleteEquipmentDtlInclusion();
	}

	@Override
	public void postDeleteEquipmentDtlInclusion(List<EquipmentDetail> equipmentDetails) {
	}

}


