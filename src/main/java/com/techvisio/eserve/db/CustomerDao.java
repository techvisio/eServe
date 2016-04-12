package com.techvisio.eserve.db;

import java.util.List;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ApproveUnitDtl;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.EquipmentHistory;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.ServiceAgreementFinanceHistory;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitHistory;

@Component
public interface CustomerDao {

	public Customer getCustomer(Long customerId);
	public Long saveCustomer(Customer customer);
	public void saveUnit(List<Unit> units,  Long customerId);
	public Long saveUnit(Unit unit);
	public List<Unit> getUnits(Long customerId);
	public List<Customer> getCustomers();
	public List<Customer> getCustomerByCriteria(SearchCriteria searchCriteria);
	public boolean isCustomerExists(Customer customer);
	public List<ServiceAgreementHistory> getServiceAgreementHistoryForUnit(Long unitId);
//	public void updateServiceAgreement(ServiceAgreement agreement, Long unitId);
	public Unit getUnit(Long unitId);
	public ApproveUnitDtl getUnitForApproval(Long unitId);
	public void saveServiceAgreementHistory(ServiceAgreementHistory history);
	public void saveServiceAgreementFinanceHistory(
			ServiceAgreementFinanceHistory financeHistory);
	public Customer getEmailId(String EmailId);
	public Customer getContactNo(String ContactNo);
	public void saveUnitHistory(UnitHistory unitHistory);
	public void saveEquipmentHistory(EquipmentHistory equipmentHistory);

}
