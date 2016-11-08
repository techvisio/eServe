package com.techvisio.eserve.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
import com.techvisio.eserve.beans.complaintSearchCriteria;
import com.techvisio.eserve.icc.CustomerServiceICC;
import com.techvisio.eserve.manager.CustomerManager;
import com.techvisio.eserve.service.CustomerService;
import com.techvisio.eserve.util.CommonUtil;
import com.techvisio.eserve.util.ServiceLocator;

@Component
@Transactional
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	CustomerManager customerManager;

	@Autowired
	ServiceLocator servicelocator;

	@Override
	public List<Customer> retrieveAllCustomer() {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		customerServiceICC.preRetrieveAllCustomers();
		List<Customer> customers = customerManager.getCustomers();

		customers=customerServiceICC.postRetrieveAllCustomers(customers);

		return customers;
	}

	@Override
	public Customer getCustomerbyId(Long customerId) {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		customerServiceICC.preGetCustomerbyId();
		Customer customer = customerManager.getCustomer(customerId);
		customer = customerServiceICC.postGetCustomerbyId(customer);
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
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		Customer customer=request.getBussinessObject();
		Long customerId = customerManager.saveCustomer(customer, context);
		request = customerServiceICC.postSaveCustomerWizard(request, context);
		return customerId;
	}


	//	@Override
	//	public void saveUnit(List<Unit> units, Long customerId) {
	//		customerManager.saveUnit(units, customerId);
	//	}

	@Override
	public Long saveUnitWizard(GenericRequest<Unit> request, String context) {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		request = customerServiceICC.preSaveUnitWizard(request);
		Unit unit=request.getBussinessObject();

		Long unitId = customerManager.saveUnit(unit,context);
		request = customerServiceICC.postSaveUnitWizard(request, context);
		return unitId;
	}


	@Override
	public List<Unit> getAllUnitForCustomerById(Long customerId) {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		customerServiceICC.preGetAllUnitForCustomerById();
		List<Unit> units = customerManager.getUnits(customerId);
		units = customerServiceICC.postGetAllUnitForCustomerById(units);
		return units;
	}

	@Override
	public SearchResultData getCustomerByCriteria(SearchCriteria searchCriteria) {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		customerServiceICC.preGetCustomerByCriteria();
		SearchResultData customers = customerManager.getCustomerByCriteria(searchCriteria);
		customers = customerServiceICC.postGetCustomerByCriteria(customers);
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

	//	private void createWorkItemWithRenewService(Unit unit) {
	//	    if(unit.getApprovalStatus() == AppConstants.APPROVED){
	//            workItemService.createWorkItemForUnit(AppConstants.PUBLISH, unit.getUnitId());
	//	    }
	//	}

	@Override
	public List<ServiceAgreementHistory> getServiceAgreementHistoryForUnit(Long unitId) {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		customerServiceICC.preGetServiceAgreementHistoryForUnit();
		List<ServiceAgreementHistory> agreementHistories = customerManager.getServiceAgreementHistoryForUnit(unitId);
		agreementHistories = customerServiceICC.postGetServiceAgreementHistoryForUnit(agreementHistories);
		return agreementHistories;
	}

	@Override
	public Unit getUnitById(Long unitId) {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		customerServiceICC.preGetUnitById();
		Unit unit = customerManager.getUnit(unitId);
		unit = customerServiceICC.postGetUnitById(unit);
		return unit;
	}

	@Override
	public Unit updateUnitForApproval(Unit unit) {
		CustomerServiceICC customerServiceICC = servicelocator.getService(CustomerServiceICC.class);
		unit=customerServiceICC.preUpdateUnitForApproval(unit);
		Unit unitFromDB = customerManager.approveUnit(unit);
		unitFromDB = customerServiceICC.postUpdateUnitForApproval(unitFromDB);
		return unitFromDB;
	}

	@Override
	public UnitBasicCustomer getUnitWithBasicCustomerDetais(Long unitId) {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		customerServiceICC.preGetUnitWithBasicCustomerDetails();
		UnitBasicCustomer approveUnitDtl = customerManager.getUnitForApproval(unitId);
		approveUnitDtl = customerServiceICC.postGetUnitWithBasicCustomerDetails(approveUnitDtl);
		return approveUnitDtl;
	}

	@Override
	public Customer getCustomerByEmailId(String emailId) {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		customerServiceICC.preGetCustomerByEmailId();
		Customer customer = customerManager.getEmailId(emailId);
		customer = customerServiceICC.postGetCustomerByEmailId(customer);
		return customer;
	}

	@Override
	public Customer getCustomerByContactNo(String contactNo)  {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		customerServiceICC.preGetCustomerByContactNo();
		Customer customer = customerManager.getContactNo(contactNo);
		customer = customerServiceICC.postGetCustomerByContactNo(customer);
		return customer;
	}

	@Override
	public Unit updateUnitForRejection(GenericRequest<Unit> request) {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		request = customerServiceICC.preUpdateUnitForRejection(request);
		Unit unit = request.getBussinessObject();
		Unit unitFromDB = customerManager.rejectUnitApproval(unit);

		request = customerServiceICC.postUpdateUnitForRejection(request);
		return unitFromDB;
	}

	@Override
	public List<EquipmentDetail> getAllEquipmentForUnitById(String type, Long unitId){
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		customerServiceICC.preGetAllEquipmentForUnitById();
		List<EquipmentDetail> equipmentDetails= customerManager.getEquipmentDetail(type, unitId);
		equipmentDetails = customerServiceICC.postGetAllEquipmentForUnitById(equipmentDetails);
		return equipmentDetails;
	}

	@Override
	public void deleteEquipmentDtlInclusion(List<EquipmentDetail> equipmentDetails,
			Long unitId){
		customerManager.deleteEquipmentDtlInclusion(equipmentDetails, unitId);
	}

	@Override
	public Long saveEquipmentforUnit(EquipmentDetail equipmentDetail) {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		equipmentDetail=customerServiceICC.preSaveEquipmentforUnit(equipmentDetail);
		Long equipmentdtlId = customerManager.saveEquipment(equipmentDetail);
		equipmentDetail = customerServiceICC.postSaveEquipmentforUnit(equipmentDetail);
		return equipmentdtlId;
	}

	@Override
	public EquipmentDetail getEquipmentDetailByEquipmentId(Long equipDtlId) {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		customerServiceICC.preGetEquipmentDetailByEquipmentId();
		EquipmentDetail equipmentDetail = customerManager.getEquipmentDetailByEquipmentId(equipDtlId);
		equipmentDetail = customerServiceICC.postGetEquipmentDetailByEquipmentId(equipmentDetail);
		return equipmentDetail;
	}

	//	@Override
	//	public Long saveUnit(Unit unit) {
	//		Long unitId = customerManager.saveUnit(unit);
	//		return unitId;
	//	}

	@Override
	public UnitBasicInfo getUnitBasicInfoById(Long unitId) {
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		customerServiceICC.preGetUnitBasicInfoById();
		UnitBasicInfo basicInfo = customerManager.getUnitBasicInfo(unitId);
		basicInfo = customerServiceICC.postGetUnitBasicInfoById(basicInfo);
		return basicInfo;
	}

	@Override
	public Unit renewSalesAgreement(GenericRequest<Unit> request, String context, Long unitId){
		CustomerServiceICC customerServiceICC=servicelocator.getService(CustomerServiceICC.class);
		request = customerServiceICC.preRenewSalesAgreement(request);
		Unit unit=request.getBussinessObject();
		Unit unitFromDB = customerManager.renewSalesAgreement(unit,context);

		request=customerServiceICC.postRenewSalesAgreement(request, context);		
		return unitFromDB;
	}

	@Override
	public void saveUnitForEmailProcess(Unit unit){
		customerManager.saveUnit(unit);
	}
	
}
