package com.techvisio.eserve.icc.impl;

import java.util.List;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.GenericRequest;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicCustomer;
import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.beans.WorkOrder;
import com.techvisio.eserve.icc.CustomerServiceICC;

public class AbstractCustomerServiceICCImpl implements CustomerServiceICC{

	@Override
	public void preRetrieveAllCustomers() {

	}
	@Override
	public List<Customer> postRetrieveAllCustomers(List<Customer> customers) {
		return customers;
	}
	
	@Override
	public Customer preSaveCustomerDirect(Customer customer) {
		return customer;
		
	}
	
	@Override
	public Customer postSaveCustomerDirect(Customer customer) {
		return customer;
		
	}
	@Override
	public void preGetCustomerbyId() {
		
	}
	@Override
	public Customer postGetCustomerbyId(Customer customer) {
		return customer;
	}
	
	@Override
	public GenericRequest<Customer> preSaveCustomerWizard(GenericRequest<Customer> request) {
		return request;
		
	}
	
	@Override
	public GenericRequest<Customer> postSaveCustomerWizard(GenericRequest<Customer> request, String context) {

		return request;	
	}
	
	@Override
	public GenericRequest<Unit> preSaveUnitWizard(GenericRequest<Unit> request){
		return request;
	}
	
	@Override
	public GenericRequest<Unit> postSaveUnitWizard(GenericRequest<Unit> request, String context){
		return request;
		}
	
	@Override
	public void preGetAllUnitForCustomerById(){};
	
	
	@Override
	public List<Unit> postGetAllUnitForCustomerById(List<Unit> units){
		return units;
	}
	
	@Override
	public void preGetCustomerByCriteria(){
		
	};
	
	@Override
	public SearchResultData postGetCustomerByCriteria(SearchResultData searchResultData){
		return searchResultData;
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
		
	}
	@Override
	public List<ServiceAgreementHistory> postGetServiceAgreementHistoryForUnit(
			List<ServiceAgreementHistory> agreementHistories) {
		return agreementHistories;
	}
	@Override
	public void preGetUnitById() {
		
	}
	@Override
	public Unit postGetUnitById(Unit unit) {
		return unit;
	}
	@Override
	public Unit preUpdateUnitForApproval(Unit unit) {
		return unit;
	}
	@Override
	public Unit postUpdateUnitForApproval(Unit unit) {
		return unit;
	}
	@Override
	public void preGetUnitWithBasicCustomerDetails() {
		
	}
	@Override
	public UnitBasicCustomer postGetUnitWithBasicCustomerDetails(
			UnitBasicCustomer unitBasicCustomer) {
		return unitBasicCustomer;
	}
	@Override
	public void preGetCustomerByEmailId() {
		
	}
	@Override
	public Customer postGetCustomerByEmailId(Customer customer) {
		return customer;
	}
	@Override
	public void preGetCustomerByContactNo() {
		
	}
	@Override
	public Customer postGetCustomerByContactNo(Customer customer) {
		return customer;
	}
	@Override
	public GenericRequest<Unit> preUpdateUnitForRejection(
			GenericRequest<Unit> request) {
		return request;
	}
	@Override
	public GenericRequest<Unit> postUpdateUnitForRejection(
			GenericRequest<Unit> request) {
		return request;
	}
	@Override
	public void preGetAllEquipmentForUnitById() {
		
	}
	@Override
	public List<EquipmentDetail> postGetAllEquipmentForUnitById(
			List<EquipmentDetail> equipmentDetails) {
		return equipmentDetails;
	}
	@Override
	public void preDeleteEquipmentDtlInclusion() {
		
	}
	@Override
	public void postDeleteEquipmentDtlInclusion(
			List<EquipmentDetail> equipmentDetails, Long unitId) {
	}
	@Override
	public EquipmentDetail preSaveEquipmentforUnit(EquipmentDetail equipmentDetail) {
		return equipmentDetail;
	}
	@Override
	public EquipmentDetail postSaveEquipmentforUnit(
			EquipmentDetail equipmentDetail) {
		return equipmentDetail;
	}
	@Override
	public void preGetEquipmentDetailByEquipmentId() {
		
	}
	@Override
	public EquipmentDetail postGetEquipmentDetailByEquipmentId(
			EquipmentDetail equipmentDetail) {
		return equipmentDetail;
	}
	@Override
	public void preGetUnitBasicInfoById() {
		
	}
	@Override
	public UnitBasicInfo postGetUnitBasicInfoById(UnitBasicInfo unitBasicInfo) {
		return unitBasicInfo;
	}
	@Override
	public GenericRequest<Unit> preRenewSalesAgreement(GenericRequest<Unit> request) {
		return request;
	}
	@Override
	public GenericRequest<Unit> postRenewSalesAgreement(GenericRequest<Unit> request,
			String context) {
		return request;
	}
	
}
