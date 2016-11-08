package com.techvisio.eserve.icc.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvisio.eserve.async.process.AsyncEmailMessage;
import com.techvisio.eserve.async.process.AsyncMessageProducer;
import com.techvisio.eserve.beans.CommunicationJob;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.GenericRequest;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicCustomer;
import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.beans.WorkOrder;
import com.techvisio.eserve.exception.EntityLockedException;
import com.techvisio.eserve.exception.MandatoryFieldMissingException;
import com.techvisio.eserve.service.CommunicationService;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.service.EntityLockService;
import com.techvisio.eserve.service.WorkItemService;
import com.techvisio.eserve.service.WorkOrderService;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.ApplicationContextProvider;
import com.techvisio.eserve.util.CommonUtil;
import com.techvisio.eserve.util.ServiceLocator;

@Service
public class CustomerServiceICCDefaultImpl extends	AbstractCustomerServiceICCImpl {

	@Autowired
	WorkItemService workItemService; 

	@Autowired
	CustomerService customerService;
	@Autowired
	EntityLockService entityLockService;

	@Autowired
	WorkOrderService workOrderService;

	@Autowired
	CommunicationService communicationService;

	@Autowired
	ServiceLocator serviceLocator;

	@Override
	public void preRetrieveAllCustomers() {
		super.preRetrieveAllCustomers();
	}

	@Override
	public List<Customer> postRetrieveAllCustomers(List<Customer> customers) {
		return super.postRetrieveAllCustomers(customers);
	}

	@Override
	public Customer preSaveCustomerDirect(Customer customer) {
		Customer customerPreSave=super.preSaveCustomerDirect(customer);
		String userName = CommonUtil.getCurrentUser().getUserName();
		boolean isEntityLocked=entityLockService.isEntityLocked(customerPreSave.getCustomerId(), AppConstants.EntityType.CUSTOMER.toString(), userName);

		if(!isEntityLocked){
			throw new EntityLockedException("Current user does not hold lock for this customer");
		}

		return customerPreSave;
	}

	@Override
	public Customer postSaveCustomerDirect(Customer customer) {
		Customer customerPostSave=super.postSaveCustomerDirect(customer);
		entityLockService.unlockEntity("CUSTOMER", customer.getCustomerId());
		return customerPostSave;
	}

	@Override
	public void preGetCustomerbyId() {
		super.preGetCustomerbyId();
	}

	@Override
	public Customer postGetCustomerbyId(Customer customer) {
		return super.postGetCustomerbyId(customer);
	}

	@Override
	public GenericRequest<Customer> preSaveCustomerWizard(GenericRequest<Customer> request) {
		return super.preSaveCustomerWizard(request);
	}

	@Override
	public GenericRequest<Customer> postSaveCustomerWizard(GenericRequest<Customer> request, String context) {
		request=super.postSaveCustomerWizard(request, context);
		String comment = request.getContextInfo().get("comment");
		workItemService.createWorkItemForCustomerSave(context, request.getBussinessObject(),comment);
		return request;	
	}

	@Override
	public GenericRequest<Unit> preSaveUnitWizard(GenericRequest<Unit> request){
		request=super.preSaveUnitWizard(request);

		if(request.getBussinessObject().getUnitId()!=null){
			String userName = CommonUtil.getCurrentUser().getUserName();
			boolean isEntityLocked=entityLockService.isEntityLocked(request.getBussinessObject().getUnitId(), AppConstants.EntityType.UNIT.toString(), userName);
			if(!isEntityLocked){
				throw new EntityLockedException("Current user does not hold lock for this unit");
			}
		}
		return request;
	}

	@Override
	public GenericRequest<Unit> postSaveUnitWizard(GenericRequest<Unit> request, String context){
		request=super.postSaveUnitWizard(request, context);
		String comment = request.getContextInfo().get("comment");
		workItemService.createWorkItemForUnitSave(context, request.getBussinessObject(), comment);
		entityLockService.unlockEntity("UNIT", request.getBussinessObject().getUnitId());

		return request;
	}

	@Override
	public void preGetAllUnitForCustomerById(){
		super.preGetAllUnitForCustomerById();
	};

	@Override
	public List<Unit> postGetAllUnitForCustomerById(List<Unit> units){
		return super.postGetAllUnitForCustomerById(units);
	}

	@Override
	public void preGetCustomerByCriteria(){
		super.preGetCustomerByCriteria();
	};

	@Override
	public SearchResultData postGetCustomerByCriteria(SearchResultData searchResultData){
		return super.postGetCustomerByCriteria(searchResultData);
	}

	@Override
	public Customer preCreateCustomerfromComplaint(WorkOrder workOrder){
		return null;
	}

	@Override
	public Customer postCreateCustomerfromComplaint(WorkOrder workOrder, Customer customer){
		return customer;
	}

	@Override
	public void preGetServiceAgreementHistoryForUnit(){
		super.preGetServiceAgreementHistoryForUnit();
	}

	@Override
	public List<ServiceAgreementHistory> postGetServiceAgreementHistoryForUnit(
			List<ServiceAgreementHistory> agreementHistories) {
		return super.postGetServiceAgreementHistoryForUnit(agreementHistories);
	}

	@Override
	public void preGetUnitById() {
		super.preGetUnitById();
	}

	@Override
	public Unit postGetUnitById(Unit unit) {
		return super.postGetUnitById(unit);
	}

	@Override
	public Unit preUpdateUnitForApproval(Unit unit) {
		return super.postGetUnitById(unit);
	}

	@Override
	public Unit postUpdateUnitForApproval(Unit unit) {
		unit = super.postUpdateUnitForApproval(unit);
		workItemService.closeAgreementApprovalWorkItem(unit.getUnitId());
		workItemService.createWorkItemForServiceRenewal(unit);
		workOrderService.createPmsWorkItem(unit);
		if(!unit.getServiceAgreement().isAgreementComSend()){
			AsyncEmailMessage emailMsg=serviceLocator.getService(AsyncEmailMessage.class);
			//Creating communication Job
			CommunicationJob communicationJob = saveAndGetCommunicationJob(unit.getUnitId());
			//Set comm job obj
			emailMsg.setCommunicationJob(communicationJob);
			AsyncMessageProducer.addJob(emailMsg);
			customerService.saveUnitForEmailProcess(unit);
		}
		return unit;
	}

	private CommunicationJob saveAndGetCommunicationJob(Long unitId) {
		CommunicationJob communicationJob = new CommunicationJob();
		communicationJob.setEntityId(unitId);
		communicationJob.setEntityType(AppConstants.EntityType.UNIT.name());
		communicationJob.setEventType(AppConstants.EventType.APPROVAL.name());
		communicationJob.setCommunicationType("EMAIL");
		communicationService.saveCommunicationJos(communicationJob);
		return communicationJob;
	}

	@Override
	public void preGetUnitWithBasicCustomerDetails() {
		super.preGetUnitWithBasicCustomerDetails();
	}

	@Override
	public UnitBasicCustomer postGetUnitWithBasicCustomerDetails(
			UnitBasicCustomer unitBasicCustomer) {
		return super.postGetUnitWithBasicCustomerDetails(unitBasicCustomer);
	}

	@Override
	public void preGetCustomerByEmailId() {
		super.preGetCustomerByEmailId();
	}

	@Override
	public Customer postGetCustomerByEmailId(Customer customer) {
		return super.postGetCustomerByEmailId(customer);
	}

	@Override
	public void preGetCustomerByContactNo() {
		super.preGetCustomerByContactNo();
	}

	@Override
	public Customer postGetCustomerByContactNo(Customer customer) {
		return super.postGetCustomerByContactNo(customer);
	}

	@Override
	public GenericRequest<Unit> preUpdateUnitForRejection(
			GenericRequest<Unit> request) {
		return super.preUpdateUnitForRejection(request);
	}

	@Override
	public GenericRequest<Unit> postUpdateUnitForRejection(
			GenericRequest<Unit> request) {
		request = super.postUpdateUnitForRejection(request);
		String comment = request.getContextInfo().get("comment");
		workItemService.workItemWorkForRejectApprovalChanges(request.getBussinessObject(), comment);
		return request;
	}

	@Override
	public void preGetAllEquipmentForUnitById() {
		super.preGetAllEquipmentForUnitById();
	}

	@Override
	public List<EquipmentDetail> postGetAllEquipmentForUnitById(
			List<EquipmentDetail> equipmentDetails) {
		return super.postGetAllEquipmentForUnitById(equipmentDetails);
	}

	@Override
	public void preDeleteEquipmentDtlInclusion() {
		super.preDeleteEquipmentDtlInclusion();
	}

	@Override
	public void postDeleteEquipmentDtlInclusion(
			List<EquipmentDetail> equipmentDetails, Long unitId) {
	}

	@Override
	public EquipmentDetail preSaveEquipmentforUnit(EquipmentDetail equipmentDetail) {
		return super.preSaveEquipmentforUnit(equipmentDetail);
	}

	@Override
	public EquipmentDetail postSaveEquipmentforUnit(
			EquipmentDetail equipmentDetail) {
		return super.postSaveEquipmentforUnit(equipmentDetail);
	}

	@Override
	public void preGetEquipmentDetailByEquipmentId() {
		super.preGetEquipmentDetailByEquipmentId();
	}

	@Override
	public EquipmentDetail postGetEquipmentDetailByEquipmentId(
			EquipmentDetail equipmentDetail) {
		return super.postGetEquipmentDetailByEquipmentId(equipmentDetail);
	}

	@Override
	public void preGetUnitBasicInfoById() {
		super.preGetUnitBasicInfoById();
	}

	@Override
	public UnitBasicInfo postGetUnitBasicInfoById(UnitBasicInfo unitBasicInfo) {
		return super.postGetUnitBasicInfoById(unitBasicInfo);
	}	

	@Override
	public GenericRequest<Unit> preRenewSalesAgreement(GenericRequest<Unit> request) {
		 request=super.preRenewSalesAgreement(request);
		
		Date contractStartDate = request.getBussinessObject().getServiceAgreement().getContractStartOn();
		Date contractExpireDate = request.getBussinessObject().getServiceAgreement().getContractExpireOn(); 

		if(contractStartDate.before(contractExpireDate) || contractExpireDate.equals(contractStartDate)){
			throw new MandatoryFieldMissingException("New sales agreement need to be started after last agreement expiration.  so please choose contract start date which will come after previous service expiration date");
		}
		return request;
	}

	@Override
	public GenericRequest<Unit> postRenewSalesAgreement(GenericRequest<Unit> request,
			String context) {
		request = super.postRenewSalesAgreement(request, context);
		String comment = request.getContextInfo().get("comment");
		workItemService.createWorkItemForUnitSave(context,request.getBussinessObject(), comment);
		workItemService.updateWorkItemStatus(request.getBussinessObject().getUnitId(),"CLOSE", "SALES RENEWAL AGREEMENT","UNIT");
		return request;
	}

	@Override
	public void preGetUnitByCriteria() {
		super.preGetUnitByCriteria();
	}
	@Override
	public SearchResultData postGetUnitByCriteria(
			SearchResultData searchResultData) {
		return super.postGetUnitByCriteria(searchResultData);
	}
	
}