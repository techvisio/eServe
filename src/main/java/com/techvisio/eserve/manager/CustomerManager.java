package com.techvisio.eserve.manager;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.UnitBasicCustomer;
import com.techvisio.eserve.beans.WorkOrderAssignment;
import com.techvisio.eserve.beans.WorkOrderResolution;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.WorkOrder;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.SearchWorkOrder;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.ServiceAgreement;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.ServiceRenewalBean;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.beans.complaintSearchCriteria;
@Component
public interface CustomerManager {

	public List<Customer> getCustomers();
	public Customer getCustomer(Long customerId);
	public Long saveCustomer(Customer customer, String context);
	public void saveUnit(List<Unit> units,  Long customerId);
	public Long saveUnit(Unit unit, String context);
	public List<Unit> getUnits(Long customerId);
	public SearchResultData getCustomerByCriteria(SearchCriteria searchCriteria);
	public List<ServiceAgreementHistory> getServiceAgreementHistoryForUnit(Long unitId);
	public Unit getUnit(Long unitId);
	public Unit approveUnit(Unit unit);
	public UnitBasicCustomer getUnitForApproval(Long unitId);
	public Long saveCustomer(Customer customer);
	public Customer getEmailId(String EmailId);
	public Customer getContactNo(String ContactNo);
	public Unit rejectUnitApproval(Unit unit);
	public List<EquipmentDetail> getEquipmentDetail(String type, Long unitId);
	void deleteEquipmentDtlInclusion(List<EquipmentDetail> equipmentDetails,
			Long unitId);
	public Long saveEquipment(EquipmentDetail equipmentDetail);
	public EquipmentDetail getEquipmentDetailByEquipmentId(Long equipDtlId);
	public Long saveUnit(Unit unit);
	public UnitBasicInfo getUnitBasicInfo(Long unitId);
	public Unit renewSalesAgreement(Unit unit, String context);
}
