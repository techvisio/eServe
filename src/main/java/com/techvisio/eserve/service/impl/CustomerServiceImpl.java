package com.techvisio.eserve.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.GenericRequest;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicCustomer;
import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.beans.WorkOrder;
import com.techvisio.eserve.exception.EntityLockedException;
import com.techvisio.eserve.icc.CustomerServiceICC;
import com.techvisio.eserve.manager.CustomerManager;
import com.techvisio.eserve.service.ActivityService;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.service.EntityLockService;
import com.techvisio.eserve.service.WorkItemService;
import com.techvisio.eserve.service.WorkOrderService;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;
import com.techvisio.eserve.util.ServiceLocator;

@Component
@Transactional
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	CustomerManager customerManager;

	@Autowired
	WorkItemService workItemService; 

	@Autowired
	ActivityService activityService;

	@Autowired
	EntityLockService entityLockService;

	@Autowired
	ServiceLocator servicelocator;

	@Autowired
	WorkOrderService complaintService;
	@Override
	public List<Customer> retrieveAllCustomer() {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		customerServiceICC.preRetrieveAllCustomers();
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		List<Customer> customers = customerManager.getCustomers(clientId);

		customers=customerServiceICC.postRetrieveAllCustomers(customers);

		return customers;
	}

	@Override
	public Customer getCustomerbyId(Long customerId) {
		Customer customer = customerManager.getCustomer(customerId);
		return customer;
	}

	@Override
	public Long saveCustomerDirect(Customer customer) {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		customer=customerServiceICC.preSaveCustomerDirect(customer);
		Long customerId = customerManager.saveCustomer(customer);
		customer=customerServiceICC.postSaveCustomerDirect(customer);
		return customerId;

	}

	@Override
	public Long saveCustomerWizard(GenericRequest<Customer> request, String context) {

		Customer customer=request.getBussinessObject();
		String comment = request.getContextInfo().get("comment");
		Long customerId = customerManager.saveCustomer(customer, context);

		workItemService.createWorkItemForCustomerSave(context, customer,comment);
		//		activityService.createActivityForCustomer(customer);
		return customerId;
	}


	//	@Override
	//	public void saveUnit(List<Unit> units, Long customerId) {
	//		customerManager.saveUnit(units, customerId);
	//	}

	@Override
	public Long saveUnitWizard(GenericRequest<Unit> request, String context) {

		Unit unit=request.getBussinessObject();
		if(unit.getUnitId()!=null){
			String userName = CommonUtil.getCurrentUser().getUserName();
			boolean isEntityLocked=entityLockService.isEntityLocked(unit.getUnitId(), AppConstants.EntityType.UNIT.toString(), userName);
			if(!isEntityLocked){
				throw new EntityLockedException("Current user does not hold lock for this unit");
			}
		}
		String comment = request.getContextInfo().get("comment");
		Long unitId = customerManager.saveUnit(unit,context);

		workItemService.createWorkItemForUnitSave(context, unitId, comment);

		entityLockService.unlockEntity("UNIT", unitId);
		return unitId;
	}


	@Override
	public List<Unit> getAllUnitForCustomerById(Long customerId) {
		List<Unit> units = customerManager.getUnits(customerId);
		return units;
	}

	@Override
	public SearchResultData getCustomerByCriteria(SearchCriteria searchCriteria) {
		SearchResultData customers = customerManager.getCustomerByCriteria(searchCriteria);
		return customers;
	}

	//	@Override
	//	public Map<String, Object> checkCustomerExistOrNot(CustomerComplaint customerComplaint) {
	//		
	//		Map<String, Object> result;
	//		Customer customer = createCustomerfromComplaint(customerComplaint);
	//		
	//		result = customerManager.saveCustomer(customer);
	//
	//		if(!(boolean) result.get("success")){
	//			return result;
	//		}
	//
	//		Customer customerFromDB = customerManager.getCustomer(customer.getCustomerId());
	//		customerComplaint.setCustomerCode(customerFromDB.getCustomerCode());
	//		customerComplaint.setCustomerId(customerFromDB.getCustomerId());
	//		return result;
	//	}
	//	

	@Override
	public Customer createCustomerfromComplaint(WorkOrder workOrder) {
		Customer customer = new Customer();
		customer.setCustomerName(workOrder.getCustomerName());
		customer.setContactNo(workOrder.getContactNo());
		customer.setEmailId(workOrder.getEmailId());
		List<Unit> units = new ArrayList<Unit>();
		units.add(workOrder.getUnit());

		customer.setUnits(units);
		return customer;
	}

	//	@Override
	//	public void updateServiceAgreement(ServiceAgreement agreement, Long unitId) {
	//		customerManager.updateServiceAgreement(agreement, unitId);
	//	    Unit unit = getUnit(agreement.getUnitId());
	//	    createWorkItemWithRenewService(unit);
	//	}

	//	private void createWorkItemWithRenewService(Unit unit) {
	//		
	////	    if(unit.getApprovalStatus() == AppConstants.APPROVED){
	////
	////            workItemService.createWorkItemForUnit(AppConstants.PUBLISH, unit.getUnitId(),);
	////	    }
	//	}

	@Override
	public List<ServiceAgreementHistory> getServiceAgreementHistoryForUnit(Long unitId) {
		List<ServiceAgreementHistory> agreementHistories = customerManager.getServiceAgreementHistoryForUnit(unitId);
		return agreementHistories;
	}

	@Override
	public Unit getUnitById(Long unitId) {
		Unit unit = customerManager.getUnit(unitId);
		return unit;
	}

	@Override
	public Unit updateUnitForApproval(Unit unit) {

		Unit unitFromDB = customerManager.approveUnit(unit);
		workItemService.closeAgreementApprovalWorkItem(unit.getUnitId());
		workItemService.createWorkItemForServiceRenewal(unit);
		complaintService.createPmsWorkItem(unit);
		return unitFromDB;
	}

	@Override
	public UnitBasicCustomer getUnitWithBasicCustomerDetais(Long unitId) {
		UnitBasicCustomer approveUnitDtl = customerManager.getUnitForApproval(unitId);
		return approveUnitDtl;
	}

	@Override
	public Customer getCustomerByEmailId(String emailId) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		Customer customer = customerManager.getEmailId(emailId, clientId);
		return customer;
	}

	@Override
	public Customer getCustomerByContactNo(String contactNo)  {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		Customer customer = customerManager.getContactNo(contactNo,clientId);
		return customer;
	}

	@Override
	public Unit updateUnitForRejection(GenericRequest<Unit> request) {
		Unit unit = request.getBussinessObject();
		String comment = request.getContextInfo().get("comment");
		workItemService.workItemWorkForRejectApprovalChanges(unit, comment);
		Unit unitFromDB = customerManager.rejectUnitApproval(unit);
		return unitFromDB;
	}

	@Override
	public List<EquipmentDetail> getAllEquipmentForUnitById(String type, Long unitId){

		List<EquipmentDetail> equipmentDetails= customerManager.getEquipmentDetail(type, unitId);
		return equipmentDetails;
	}

	@Override
	public void deleteEquipmentDtlInclusion(List<EquipmentDetail> equipmentDetails,
			Long unitId){
		customerManager.deleteEquipmentDtlInclusion(equipmentDetails, unitId);
	}

	@Override
	public Long saveEquipmentforUnit(EquipmentDetail equipmentDetail) {
		Long equipmentdtlId = customerManager.saveEquipment(equipmentDetail);
		return equipmentdtlId;
	}

	@Override
	public EquipmentDetail getEquipmentDetailByEquipmentId(Long equipDtlId) {
		EquipmentDetail equipmentDetails = customerManager.getEquipmentDetailByEquipmentId(equipDtlId);
		return equipmentDetails;
	}

	//	@Override
	//	public Long saveUnit(Unit unit) {
	//		Long unitId = customerManager.saveUnit(unit);
	//		return unitId;
	//	}

	@Override
	public UnitBasicInfo getUnitBasicInfoById(Long unitId) {
		UnitBasicInfo basicInfo = customerManager.getUnitBasicInfo(unitId);
		return basicInfo;
	}
}
