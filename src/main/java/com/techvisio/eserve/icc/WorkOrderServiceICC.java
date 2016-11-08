package com.techvisio.eserve.icc;

import java.util.List;

import com.techvisio.eserve.beans.ComplaintSearchData;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.PmsWorkOrder;
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

public interface WorkOrderServiceICC {

	public WorkOrder preSaveWorkOrder(WorkOrder workOrder);
	public WorkOrder postSaveWorkOrder(WorkOrder workOrder);

	public void preGetWorkOrderById();
	public WorkOrder postGetWorkOrderById(WorkOrder workOrder);

	public void preGetCustomerBasicInfo();
	public Customer postGetCustomerBasicInfo(Customer customer); 

	public void preGetUnitBasicInfo();
	public Unit postGetUnitBasicInfo(Unit unit); 

	public WorkOrderResolution preSaveWorkOrderResolution(WorkOrderResolution workOrderResolution);
	public WorkOrderResolution postSaveWorkOrderResolution(WorkOrderResolution workOrderResolution);

	public void preGetWorkOrderResolution();
	public WorkOrderResolution postGetWorkOrderResolution(WorkOrderResolution resolution);

	public WorkOrderAssignment preSaveWorkOrderAssignment(WorkOrderAssignment workOrderAssignment);
	public WorkOrderAssignment postSaveWorkOrderAssignment(WorkOrderAssignment workOrderAssignment);

		public void preGetWorkOrderAssignment();
	public WorkOrderAssignment postGetWorkOrderAssignment( WorkOrderAssignment workOrderAssignment);

	public void preGetCustomerForComplaintByCriteria();
	public List<SearchComplaintCustomer> postGetCustomerForComplaintByCriteria(List<SearchComplaintCustomer> searchComplaintCustomers);

	public void preGetSearchUnitByCustomerId();
	public List<SearchComplaintUnit> postGetSearchUnitByCustomerId(List<SearchComplaintUnit> units);

	public void preGetComplaintSearchByUnitId();
	public List<SearchWorkOrder> postGetComplaintSearchByUnitId(List<SearchWorkOrder> workOrders); 

	public void preGetAllComplaintsForUnit();
	public List<WorkOrder> postGetAllComplaintsForUnit(List<WorkOrder> workOrders);

	public void preGetComplaintDataforDashboard();
	public List<ComplaintSearchData> postGetComplaintDataforDashboard(List<ComplaintSearchData> searchData);

	public void preGetEquipmentDetail();
	public List<EquipmentDetail> postGetEquipmentDetail(List<EquipmentDetail> equipmentDetails);

	public List<EquipmentDetail> preSaveEquipment(List<EquipmentDetail> equipmentDetails);
	public List<EquipmentDetail> postSaveEquipment(List<EquipmentDetail> equipmentDetails);

	public void preGetEquipmentDetailByEquipmentId();
	public EquipmentDetail postGetEquipmentDetailByEquipmentId(EquipmentDetail equipmentDetail);

	public WorkOrderEquipment preSaveWorkOrderEquipments(WorkOrderEquipment orderEquipment);
	public WorkOrderEquipment postSaveWorkOrderEquipments(WorkOrderEquipment orderEquipment);

	public void preGetWorkOrderEquipments();
	public List<WorkOrderEquipment> postGetWorkOrderEquipments(List<WorkOrderEquipment> workOrderEquipments);

	public void preDeleteEquipmentDtlInclusion();
	public void postDeleteEquipmentDtlInclusion(List<EquipmentDetail> equipmentDetails);

}
