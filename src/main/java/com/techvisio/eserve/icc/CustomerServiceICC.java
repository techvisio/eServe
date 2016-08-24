package com.techvisio.eserve.icc;

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

public interface CustomerServiceICC {

	public void preRetrieveAllCustomers();
	public List<Customer> postRetrieveAllCustomers(List<Customer> customers);
	
	public Customer preSaveCustomerDirect(Customer customer);
	public Customer postSaveCustomerDirect(Customer customer);
	
	public void preGetCustomerbyId();
	public Customer postGetCustomerbyId(Customer customer);
	
	public GenericRequest<Customer> preSaveCustomerWizard(GenericRequest<Customer> request);
	public GenericRequest<Customer> postSaveCustomerWizard(GenericRequest<Customer> request, String context);
	
	public GenericRequest<Unit> preSaveUnitWizard(GenericRequest<Unit> request);
	public GenericRequest<Unit> postSaveUnitWizard(GenericRequest<Unit> request, String context);
	
	public void preGetAllUnitForCustomerById();
	public List<Unit> postGetAllUnitForCustomerById(List<Unit> units);
	
	public void preGetCustomerByCriteria();
	public SearchResultData postGetCustomerByCriteria(SearchResultData searchResultData);
	
	public Customer preCreateCustomerfromComplaint(WorkOrder workOrder);
	public Customer postCreateCustomerfromComplaint(WorkOrder workOrder, Customer customer);
	
	public void preGetServiceAgreementHistoryForUnit();
	public List<ServiceAgreementHistory> postGetServiceAgreementHistoryForUnit(List<ServiceAgreementHistory> agreementHistories);
	
	public void preGetUnitById();
	public Unit postGetUnitById(Unit unit);
	
	public Unit preUpdateUnitForApproval(Unit unit);
	public Unit postUpdateUnitForApproval(Unit unit);
	
	public void preGetUnitWithBasicCustomerDetails();
	public UnitBasicCustomer postGetUnitWithBasicCustomerDetails(UnitBasicCustomer unitBasicCustomer);
	
	public void preGetCustomerByEmailId();
	public Customer postGetCustomerByEmailId(Customer customer);
	
	public void preGetCustomerByContactNo();
	public Customer postGetCustomerByContactNo(Customer customer);
	
	public GenericRequest<Unit> preUpdateUnitForRejection(GenericRequest<Unit> request);
	public GenericRequest<Unit> postUpdateUnitForRejection(GenericRequest<Unit> request);
	
	public void preGetAllEquipmentForUnitById();
	public List<EquipmentDetail> postGetAllEquipmentForUnitById(List<EquipmentDetail> equipmentDetails);
	
	public void preDeleteEquipmentDtlInclusion();
	public void postDeleteEquipmentDtlInclusion(List<EquipmentDetail> equipmentDetails,Long unitId);
	
	public EquipmentDetail preSaveEquipmentforUnit(EquipmentDetail equipmentDetail);
	public EquipmentDetail postSaveEquipmentforUnit(EquipmentDetail equipmentDetail);

	public void preGetEquipmentDetailByEquipmentId();
	public EquipmentDetail postGetEquipmentDetailByEquipmentId(EquipmentDetail equipmentDetail);

	public void preGetUnitBasicInfoById();
	public UnitBasicInfo postGetUnitBasicInfoById(UnitBasicInfo unitBasicInfo);

	public GenericRequest<Unit> preRenewSalesAgreement(GenericRequest<Unit> request);
	public GenericRequest<Unit> postRenewSalesAgreement(GenericRequest<Unit> request, String context);
}
