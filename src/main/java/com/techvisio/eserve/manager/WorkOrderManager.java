package com.techvisio.eserve.manager;

import java.util.List;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.WorkItemSearchCriteria;
import com.techvisio.eserve.beans.WorkOrderAssignment;
import com.techvisio.eserve.beans.WorkOrderEquipment;
import com.techvisio.eserve.beans.WorkOrderResolution;
import com.techvisio.eserve.beans.ComplaintSearchData;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.WorkOrder;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.PmsWorkOrder;
import com.techvisio.eserve.beans.SearchWorkOrder;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.Unit;

@Component
public interface WorkOrderManager {

	public Long saveWorkOrder(WorkOrder customerComplaint);
	public WorkOrder getWorkOrder(Long complaintId);
	public Customer getCustomerBasicInfo(Long customerId);
	public Unit getUnitBasicInfo(Long unitId);
	public List<WorkOrder> getWorkOrders(Long customerId);
	public void saveWorkOrderResolution(Long complaintId, WorkOrderResolution complaintResolution);
	public WorkOrderResolution getWorkOrderResolution(Long complaintId);
	public void saveWorkOrderAssignment(Long complaintId, WorkOrderAssignment complaintAssignment);
	public WorkOrderAssignment getWorkOrderAssignment(Long complaintId);
	public List<SearchComplaintCustomer> getCustomerForComplaintByCriteria(
			SearchCriteria searchCriteria);
	public List<SearchComplaintUnit> getSearchUnitByCustomerId(Long customerId);
	public List<SearchWorkOrder> getComplaintSearchByUnitId(Long unitId);
	public List<WorkOrder> getAllComplaintsForUnit(Long unitId);
	public 	List<ComplaintSearchData> getComplaintDataforDashboard(String type, String code);
	public void saveWorkOrderEquipments(WorkOrderEquipment complaintEquipment);
	public List<WorkOrderEquipment> getWorkOrderEquipments(Long complaintId);
	public void createPmsComplaint(PmsWorkOrder pmsComplaint);
	public PmsWorkOrder getPmsWorkOrderByWorkitemId(Long workitemId);
	public PmsWorkOrder getPmsComplaintByWorkOrderId(Long workOrderId);
}
