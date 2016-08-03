package com.techvisio.eserve.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.UnitBasicCustomer;
import com.techvisio.eserve.beans.ComplaintAssignment;
import com.techvisio.eserve.beans.ComplaintResolution;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.GenericRequest;
import com.techvisio.eserve.beans.SearchComplaint;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.ServiceAgreement;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.ServiceRenewalBean;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicInfo;

@Component
@Transactional
public interface CustomerService {

	public List<Customer> retrieveAllCustomer();
	public Customer getCustomerbyId(Long customerId);
	public Long saveCustomerWizard(GenericRequest<Customer> request, String context);
	public Long saveCustomerDirect(Customer customer);
//	public void saveUnit(List<Unit> units,  Long customerId);
	public Long saveUnitWizard(GenericRequest<Unit> request, String context);
	public List<Unit> getAllUnitForCustomerById(Long customerId);
	public SearchResultData<Customer> getCustomerByCriteria(SearchCriteria searchCriteria);
//	public Map<String, Object> checkCustomerExistOrNot(
//			CustomerComplaint customerComplaint);
//	public void updateServiceAgreement(ServiceAgreement agreement,Long unitId);
	public List<ServiceAgreementHistory> getServiceAgreementHistoryForUnit(Long unitId);
	public Unit getUnitById(Long unitId);
	public Unit updateUnitForApproval(Unit unit);
	public Unit updateUnitForRejection(GenericRequest<Unit> request);
	public UnitBasicCustomer getUnitWithBasicCustomerDetais(Long unitId);
	//TODO:Use save customer instead by creating customer from calling method
	public Customer createCustomerfromComplaint(CustomerComplaint customerComplaint);
	public Customer getCustomerByEmailId(String emailId);
	public Customer getCustomerByContactNo(String contactNo);
	public List<EquipmentDetail> getAllEquipmentForUnitById(String type, Long unitId);
	//TODO: pass entire list of equipement and handle deletion and insertion in customer Service only(remove  this)
	public void deleteEquipmentDtlInclusion(List<EquipmentDetail> equipmentDetails,Long unitId);
	//TODO: pass entire list of equipement and handle deletion and insertion in customer Service only(update to accept list and unitid)
	public Long saveEquipmentforUnit(EquipmentDetail equipmentDetail);
	public EquipmentDetail getEquipmentDetailByEquipmentId(Long equipDtlId);
//	public Long saveUnit(Unit unit);
	public UnitBasicInfo getUnitBasicInfoById(Long unitId);
}
