package com.techvisio.eserve.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CompositeKeyEquipmentHistory;
import com.techvisio.eserve.beans.CompositeKeyUnitHistory;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.EquipmentHistory;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.ServiceAgreementFinanceHistory;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicCustomer;
import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.beans.UnitHistory;
import com.techvisio.eserve.db.CacheDao;
import com.techvisio.eserve.db.CustomerDao;
import com.techvisio.eserve.exception.DuplicateEntityException;
import com.techvisio.eserve.exception.MandatoryFieldMissingException;
import com.techvisio.eserve.manager.CustomerManager;
import com.techvisio.eserve.manager.InvoiceManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;
@Component
public class CustomerManagerImpl implements CustomerManager {

	@Autowired
	CustomerDao customerDao;

	@Autowired
	CacheDao cacheDao;

	@Autowired
	InvoiceManager invoiceManager;

	@Override
	public Customer getCustomer(Long customerId) {
		Customer customer = customerDao.getCustomer(customerId);
		return customer;
	}

	@Override
	public SearchResultData getCustomerByCriteria(SearchCriteria searchCriteria) {
		SearchResultData customers = customerDao.getCustomerByCriteria(searchCriteria);
		return customers;
	}


	@Override
	public Long saveCustomer(Customer customer) {
		Customer customerByEmailId = customerDao.getEmailId(customer.getEmailId());
		if(customerByEmailId!=null && !customerByEmailId.getCustomerId().equals(customer.getCustomerId()) ){
			throw new DuplicateEntityException("This Email Id is Already Exists, Choose Different EmailId");
		}
		Customer customerByContactNo = customerDao.getContactNo(customer.getContactNo());
		if(customerByContactNo!=null && !customerByContactNo.getCustomerId().equals( customer.getCustomerId())){
			throw new DuplicateEntityException("This Contact No is Already Exists, Choose Different Contact No");
		}
		Long customerId = customerDao.saveCustomer(customer);
		return customerId;
	}

	@Override
	public Long saveCustomer(Customer customer, String context) {

		Customer customerByEmailId = customerDao.getEmailId(customer.getEmailId());
		if(customerByEmailId!=null){
			throw new DuplicateEntityException("This Email Id is Already Exists, Choose Different EmailId");
		}
		Customer customerByContactNo = customerDao.getContactNo(customer.getContactNo());
		if(customerByContactNo!=null){
			throw new DuplicateEntityException("This Contact No is Already Exists, Choose Different Contact No");
		}

		List<Unit> units = customer.getUnits();

		for(Unit unit : units){

			if(context.equalsIgnoreCase(AppConstants.CUSTOMER_DRAFT)){
				unit.setApprovalStatus(AppConstants.DRAFTSTATUS);
			}

			if(context.equalsIgnoreCase(AppConstants.PUBLISH)){
				unit.setApprovalStatus(AppConstants.PENDING);
			}
		}

		customer.setClient(CommonUtil.getCurrentClient());		


		Long customerId = customerDao.saveCustomer(customer);	

		//		Customer customerFromDB = customerDao.getCustomer(customerId);
		//		result.put("customer", customerFromDB);
		//		result.put("success", true);
		return customerId;
	}


	@Override
	public void saveUnit(List<Unit> units, Long customerId) {
		customerDao.saveUnit(units, customerId);
	}

	@Override
	public Long saveUnit(Unit unit, String context) {

		if(context.equalsIgnoreCase(AppConstants.CUSTOMER_DRAFT)){
			unit.setApprovalStatus(AppConstants.DRAFTSTATUS);
		}

		if(context.equalsIgnoreCase(AppConstants.PUBLISH)){
			unit.setApprovalStatus(AppConstants.PENDING);
		}

		Long unitId = customerDao.saveUnit(unit);
		return unitId;
	}

	@Override
	public Long saveUnit(Unit unit) {

		Long unitId = customerDao.saveUnit(unit);
		return unitId;
	}


	@Override
	public List<Unit> getUnits(Long customerId) {
		List<Unit> units = customerDao.getUnits(customerId);
		return units;
	}

	@Override
	public List<Customer> getCustomers() {
		List<Customer> customers = customerDao.getCustomers();
		return customers;
	}

	@Override
	public List<ServiceAgreementHistory> getServiceAgreementHistoryForUnit(Long unitId) {
		List<ServiceAgreementHistory> agreementHistories = customerDao.getServiceAgreementHistoryForUnit(unitId);
		return agreementHistories;
	}

	@Override
	public Unit getUnit(Long unitId) {
		Unit unit = customerDao.getUnit(unitId);
		return unit;
	}

	@Override
	public Unit approveUnit(Unit unit){

		Unit unitFromDB = customerDao.getUnit(unit.getUnitId());
		Double agreementAmount=null;
		
		if(unit.getServiceAgreement().getServiceAgreementFinance()!=null){
			 agreementAmount = unit.getServiceAgreement().getServiceAgreementFinance().getAgreementAmount();
		}
		if(unit.getApprovalStatus()==AppConstants.PENDING && agreementAmount!=null){

			unit.setApprovalStatus(AppConstants.APPROVED);

			invoiceManager.saveInvoiceAndInvoiceAgreement(unit.getServiceAgreement().getServiceAgreementId(), agreementAmount);

			saveServiceAgreementHistory(unit);
			saveServiceAgreementFinanceHistory(unit);
			saveUnitHistory(unit);
			unit.setLastApprovalDate(new Date());
			unit.setLastApprovedBy(CommonUtil.getCurrentUser().getUserName());
			unit.setVersionId(unit.getVersionId()+1);
			customerDao.saveUnit(unit);
		}
		else{
			throw new MandatoryFieldMissingException("Agreement amount is missing for this unit");
		}
		unitFromDB = customerDao.getUnit(unit.getUnitId());
		return unitFromDB;
	}


	private List<EquipmentHistory> saveEquipmentHistory(Unit unit) {
		List<EquipmentHistory> equipmentHistories = new ArrayList<EquipmentHistory>();

		for(EquipmentDetail equipmentDetail : unit.getEquipmentDetails()){

			EquipmentHistory equipmentHistory = new EquipmentHistory();
			CompositeKeyEquipmentHistory keyEquipmentHistory = new CompositeKeyEquipmentHistory();
			keyEquipmentHistory.setEquipmentDtlId(equipmentDetail.getEquipmentDtlId());
			keyEquipmentHistory.setUnitId(equipmentDetail.getUnitId());
			keyEquipmentHistory.setVersion(unit.getVersionId());
			equipmentHistory.setCompositeKeyEquipmentHistory(keyEquipmentHistory);
			equipmentHistory.setEquipment(equipmentDetail.getEquipment());
			equipmentHistory.setInvoiceNo(equipmentDetail.getInvoiceNo());
			equipmentHistory.setSerialNo(equipmentDetail.getSerialNo());
			equipmentHistory.setType(equipmentDetail.getType());
			equipmentHistory.setWarrantyUnder(equipmentDetail.getWarrantyUnder());
			equipmentHistory.setUnderWarranty(equipmentDetail.isUnderWarranty());
			customerDao.saveEquipmentHistory(equipmentHistory);

			equipmentHistories.add(equipmentHistory);
		}

		return equipmentHistories;
	}

	private void saveUnitHistory(Unit unit) {
		UnitHistory unitHistory = new UnitHistory();
		CompositeKeyUnitHistory compositeKey = new CompositeKeyUnitHistory();
		compositeKey.setUnitId(unit.getUnitId());
		compositeKey.setVersion(unit.getVersionId());
		unitHistory.setAddress(unit.getAddress());
		unitHistory.setCompositeKey(compositeKey);
		unitHistory.setApprovalStatus(unit.getApprovalStatus());
		unitHistory.setAssetNo(unit.getAssetNo());
		unitHistory.setCustomerId(unit.getCustomerId());
		unitHistory.setMachineSerialNo(unit.getMachineSerialNo());
		unitHistory.setModelNo(unit.getModelNo());
		unitHistory.setUnitCategory(unit.getUnitCategory());
		unitHistory.setUnitCode(unit.getUnitCode());

		List<EquipmentHistory> equipmentHistories=saveEquipmentHistory(unit);

		customerDao.saveUnitHistory(unitHistory);
	}

	private void saveServiceAgreementHistory(Unit unit) {
		ServiceAgreementHistory history = new ServiceAgreementHistory();
		history.setClient(unit.getClient());
		history.setVersionId(unit.getVersionId());
		history.setEndDate(unit.getServiceAgreement().getContractExpireOn());
		history.setServiceType(unit.getServiceAgreement().getServiceCategory());
		history.setStartDateString(unit.getServiceAgreement().getContractStartOnString());
		history.setUnitId(unit.getServiceAgreement().getUnitId());
		customerDao.saveServiceAgreementHistory(history);
	}

	private void saveServiceAgreementFinanceHistory(Unit unit) {
		if(unit.getServiceAgreement().getServiceAgreementFinance() != null){
			ServiceAgreementFinanceHistory financeHistory = new ServiceAgreementFinanceHistory();
			financeHistory.setVersionId(unit.getVersionId());
			financeHistory.setAgreementAmount(unit.getServiceAgreement().getServiceAgreementFinance().getAgreementAmount());
			financeHistory.setClient(unit.getClient());
			financeHistory.setUnitId(unit.getUnitId());
			customerDao.saveServiceAgreementFinanceHistory(financeHistory);
		}
	}

	@Override
	public UnitBasicCustomer getUnitForApproval(Long unitId) {
		UnitBasicCustomer approveUnitDtl = customerDao.getUnitForApproval(unitId);
		return approveUnitDtl;
	}

	@Override
	public Customer getEmailId(String EmailId) {
		Customer customer = customerDao.getEmailId(EmailId);
		return customer;
	}

	@Override
	public Customer getContactNo(String ContactNo) {
		Customer customer = customerDao.getContactNo(ContactNo);
		return customer;
	}

	@Override
	public Unit rejectUnitApproval(Unit unit){

		Unit unitFromDB = null;

		if(unit.getApprovalStatus()==AppConstants.PENDING){

			unit.setApprovalStatus(AppConstants.REJECTED);
			customerDao.saveUnit(unit);

		}
		unitFromDB = customerDao.getUnit(unit.getUnitId());
		return unitFromDB;
	}

	@Override
	public List<EquipmentDetail> getEquipmentDetail(String type, Long unitId){

		List<EquipmentDetail> equipmentDetails= customerDao.getEquipmentDetail(type, unitId);
		return equipmentDetails;
	}

	@Override
	public void deleteEquipmentDtlInclusion(List<EquipmentDetail> equipmentDetails,
			Long unitId){
		customerDao.deleteEquipmentDtlInclusion(equipmentDetails, unitId);

	}

	@Override
	public Long saveEquipment(EquipmentDetail equipmentDetail) {
		Long equipmentdtlId = customerDao.saveEquipment(equipmentDetail);
		return equipmentdtlId;
	}

	@Override
	public EquipmentDetail getEquipmentDetailByEquipmentId(Long equipDtlId) {
		EquipmentDetail equipmentDetails = customerDao.getEquipmentDetailByEquipmentId(equipDtlId);
		return equipmentDetails;
	}

	@Override
	public UnitBasicInfo getUnitBasicInfo(Long unitId) {
		UnitBasicInfo basicInfo = customerDao.getUnitBasicInfo(unitId);
		return basicInfo;
	}

	@Override
	public Unit renewSalesAgreement(Unit unit, String context){
		saveUnit(unit, context);
		Unit unitFromDB = customerDao.getUnit(unit.getUnitId());
		return unitFromDB;
	}
}
