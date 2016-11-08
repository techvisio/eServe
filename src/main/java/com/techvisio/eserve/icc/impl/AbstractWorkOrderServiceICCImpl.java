package com.techvisio.eserve.icc.impl;

import java.util.List;

import com.techvisio.eserve.beans.ComplaintSearchData;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchWorkOrder;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.beans.WorkItem;
import com.techvisio.eserve.beans.WorkOrder;
import com.techvisio.eserve.beans.WorkOrderAssignment;
import com.techvisio.eserve.beans.WorkOrderEquipment;
import com.techvisio.eserve.beans.WorkOrderResolution;
import com.techvisio.eserve.icc.WorkOrderServiceICC;

public abstract class AbstractWorkOrderServiceICCImpl implements WorkOrderServiceICC{

	public WorkOrder preSaveWorkOrder(WorkOrder workOrder){
		return workOrder;
	};

	public WorkOrder postSaveWorkOrder(WorkOrder workOrder){
		return workOrder;
	}

	public void preGetWorkOrderById(){

	}
	public WorkOrder postGetWorkOrderById(WorkOrder workOrder){
		return workOrder;
	}

	public void preGetCustomerBasicInfo(){

	}
	public Customer postGetCustomerBasicInfo(Customer customer){
		return customer;
	} 

	public void preGetUnitBasicInfo(){

	}
	public Unit postGetUnitBasicInfo(Unit unit){
		return unit;
	} 

	public WorkOrderResolution preSaveWorkOrderResolution(WorkOrderResolution workOrderResolution){
		return workOrderResolution;
	}
	public WorkOrderResolution postSaveWorkOrderResolution(WorkOrderResolution workOrderResolution){
		return workOrderResolution;
	}

	public void preGetWorkOrderResolution(){

	}
	public WorkOrderResolution postGetWorkOrderResolution(WorkOrderResolution resolution){
		return resolution;
	}

	public WorkOrderAssignment preSaveWorkOrderAssignment(WorkOrderAssignment workOrderAssignment){
		return workOrderAssignment;
	}
	public WorkOrderAssignment postSaveWorkOrderAssignment(WorkOrderAssignment workOrderAssignment){
		return workOrderAssignment;
	}

	public void preGetWorkOrderAssignment(){

	}
	public WorkOrderAssignment postGetWorkOrderAssignment( WorkOrderAssignment workOrderAssignment){
		return workOrderAssignment;
	}

	public void preGetCustomerForComplaintByCriteria(){

	}
	public List<SearchComplaintCustomer> postGetCustomerForComplaintByCriteria(List<SearchComplaintCustomer> searchComplaintCustomers){
		return searchComplaintCustomers;
	}

	public void preGetSearchUnitByCustomerId(){

	}
	public List<SearchComplaintUnit> postGetSearchUnitByCustomerId(List<SearchComplaintUnit> units){
		return units;
	}

	public void preGetComplaintSearchByUnitId(){

	}
	public List<SearchWorkOrder> postGetComplaintSearchByUnitId(List<SearchWorkOrder> workOrders){
		return workOrders;
	} 

	public void preGetAllComplaintsForUnit(){

	}
	public List<WorkOrder> postGetAllComplaintsForUnit(List<WorkOrder> workOrders){
		return workOrders;
	}

	public void preGetComplaintDataforDashboard(){

	}
	public List<ComplaintSearchData> postGetComplaintDataforDashboard(List<ComplaintSearchData> searchData){
		return searchData;
	}

	public void preGetEquipmentDetail(){

	}
	public List<EquipmentDetail> postGetEquipmentDetail(List<EquipmentDetail> equipmentDetails){
		return equipmentDetails;
	}

	public List<EquipmentDetail> preSaveEquipment(List<EquipmentDetail> equipmentDetails){
		return equipmentDetails;
	}
	public List<EquipmentDetail> postSaveEquipment(List<EquipmentDetail> equipmentDetails){
		return equipmentDetails;
	}

	public void preGetEquipmentDetailByEquipmentId(){

	}
	public EquipmentDetail postGetEquipmentDetailByEquipmentId(EquipmentDetail equipmentDetail){
		return equipmentDetail;
	}

	public WorkOrderEquipment preSaveWorkOrderEquipments(WorkOrderEquipment orderEquipment){
		return orderEquipment;
	}
	public WorkOrderEquipment postSaveWorkOrderEquipments(WorkOrderEquipment orderEquipment){
		return orderEquipment;
	}

	public void preGetWorkOrderEquipments(){

	}
	public List<WorkOrderEquipment> postGetWorkOrderEquipments(List<WorkOrderEquipment> workOrderEquipments){
		return workOrderEquipments;
	}

	public void preDeleteEquipmentDtlInclusion(){

	}
	public void postDeleteEquipmentDtlInclusion(List<EquipmentDetail> equipmentDetails){

	}

}
