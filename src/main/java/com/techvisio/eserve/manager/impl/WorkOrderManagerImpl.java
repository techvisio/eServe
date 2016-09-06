package com.techvisio.eserve.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.techvisio.eserve.db.CacheDao;
import com.techvisio.eserve.db.WorkOrderDao;
import com.techvisio.eserve.manager.WorkOrderManager;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Component
public class WorkOrderManagerImpl implements WorkOrderManager{

	@Autowired
	WorkOrderDao workOrderDao;

	@Autowired
	CacheDao cacheDao;

	@Autowired
	CustomerService customerService;

	@Autowired 
	ClientConfiguration configPreferences;

	@Override
	public WorkOrder getWorkOrder(Long workOrderId) {
		WorkOrder workOrder = workOrderDao.getWorkOrder(workOrderId);
		return workOrder;
	}
	@Override
	public Long saveWorkOrder(WorkOrder workOrder){

		Date slaDate = getSlaDateByPriority(workOrder.getPriority());
		workOrder.setSlaDate(slaDate);
		Long workOrderId = workOrderDao.saveWorkOrder(workOrder);	
		return workOrderId;
	}

	private Date getSlaDateByPriority(String priority) {


		if(priority != null && priority.equalsIgnoreCase(AppConstants.CRITICAL)){
			Date date = configPreferences.getSlaDateForCriticalIssue(CommonUtil.getCurrentClient().getClientId());		
			return date;
		}

		if(priority != null && priority.equalsIgnoreCase(AppConstants.HIGH)){
			Date date = configPreferences.getSlaDateForHighIssue(CommonUtil.getCurrentClient().getClientId());
			return date;
		}

		if(priority != null && priority.equalsIgnoreCase(AppConstants.MEDIUM)){
			Date date = configPreferences.getSlaDateForMediumIssue(CommonUtil.getCurrentClient().getClientId());
			return date;
		}

		if(priority != null && priority.equalsIgnoreCase(AppConstants.LOW)){
			Date date = configPreferences.getSlaDateForLowIssue(CommonUtil.getCurrentClient().getClientId());
			return date;
		}
		return null;
	}

	@Override
	public Customer getCustomerBasicInfo(Long customerId) {
		Customer customer = workOrderDao.getCustomerBasicInfo(customerId);
		return customer;
	}

	@Override
	public Unit getUnitBasicInfo(Long unitId) {
		Unit unit = workOrderDao.getUnitBasicInfo(unitId);
		return unit;
	}

	@Override
	public List<WorkOrder> getWorkOrders(Long workOrderId) {
		List<WorkOrder> workOrders = workOrderDao.getWorkOrders(workOrderId);
		return workOrders;
	}

	@Override
	public void saveWorkOrderResolution(Long workOrderId, WorkOrderResolution workOrderResolution) {
		workOrderDao.saveWorkOrderResolution(workOrderId, workOrderResolution);
	}

	@Override
	public WorkOrderResolution getWorkOrderResolution(Long workOrderId) {
		WorkOrderResolution workOrderResolution = workOrderDao.getWorkOrderResolution(workOrderId);
		return workOrderResolution;
	}

	@Override
	public void saveWorkOrderAssignment(Long workOrderId, WorkOrderAssignment workOrderAssignment) {
		workOrderDao.saveWorkOrderAssignment(workOrderId,workOrderAssignment);
	}

	@Override
	public WorkOrderAssignment getWorkOrderAssignment(Long workOrderId) {
		WorkOrderAssignment workOrderAssignment = workOrderDao.getWorkOrderAssignment(workOrderId);
		return workOrderAssignment;
	}

	@Override
	public List<SearchComplaintCustomer> getCustomerForComplaintByCriteria(
			SearchCriteria searchCriteria) {
		List<SearchComplaintCustomer> customers = workOrderDao.getCustomerForComplaintByCriteria(searchCriteria);
		if(searchCriteria.getWorkOrderNo() != null ){
			customers = workOrderDao.getCustomerByWorkOrderNo(searchCriteria);
		}

		return customers;
	}

	@Override
	public List<SearchComplaintUnit> getSearchUnitByCustomerId(Long customerId) {
		List<SearchComplaintUnit> complaintUnits = workOrderDao.getSearchUnitByCustomerId(customerId);
		return complaintUnits;
	}

	@Override
	public List<SearchWorkOrder> getComplaintSearchByUnitId(Long unitId) {
		List<SearchWorkOrder> searchWorkOrders = workOrderDao.getComplaintSearchByUnitId(unitId);
		return searchWorkOrders;
	}

	@Override
	public List<WorkOrder> getAllComplaintsForUnit(Long unitId) {
		List<WorkOrder> workOrders= workOrderDao.getAllComplaintsForUnit(unitId);
		return workOrders;
	}

	@Override
	public List<ComplaintSearchData> getComplaintDataforDashboard(String type,String code) {
		List<ComplaintSearchData> complaints=new ArrayList<ComplaintSearchData>();
		if(type.equalsIgnoreCase("SLA")){
			complaints=workOrderDao.getComplaintBySLA(code);

		}
		if(type.equalsIgnoreCase("ASSIGNMENT")){
			complaints=workOrderDao.getComplaintByASSIGNMENT(code);
		}

		if(type.equalsIgnoreCase("PRIORITY"))	{
			complaints=workOrderDao.getComplaintByPRIORITY(code);
		}
		return complaints;
	}
	@Override
	public void saveWorkOrderEquipments(WorkOrderEquipment workOrderEquipment) {
		workOrderDao.saveWorkOrderEquipments(workOrderEquipment);

	}
	@Override
	public List<WorkOrderEquipment> getWorkOrderEquipments(Long workOrderId) {
		List<WorkOrderEquipment> workOrderEquipments = workOrderDao.getWorkOrderEquipments(workOrderId);
		return workOrderEquipments;
	}
	@Override
	public void createPmsComplaint(PmsWorkOrder pmsComplaint) {
		workOrderDao.createPmsComplaint(pmsComplaint);
	}
	@Override
	public PmsWorkOrder getPmsWorkOrderByWorkitemId(Long workitemId) {
		PmsWorkOrder pmsComplaint = workOrderDao.getPmsComplaintByWorkitemId(workitemId);
		return pmsComplaint;
	}
	@Override
	public PmsWorkOrder getPmsComplaintByWorkOrderId(Long workOrderId) {
		PmsWorkOrder pmsComplaint = workOrderDao.getPmsComplaintByWorkOrderId(workOrderId);
		return pmsComplaint;
	}
}
