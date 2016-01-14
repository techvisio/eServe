package com.techvisio.eserve.db.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ComplaintAssignment;
import com.techvisio.eserve.beans.ComplaintResolution;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.SearchComplaint;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.db.CustomerDao;
import com.techvisio.eserve.factory.UniqueIdentifierGenerator;
import com.techvisio.eserve.util.AppConstants;

@Component
public class CustomerDaoImpl extends BaseDao implements CustomerDao{

	@Autowired
	UniqueIdentifierGenerator identifierGenerator;

	@Override
	public Customer getCustomer(Long customerId) {
		String queryString="FROM Customer cus WHERE cus.customerId = "+customerId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Customer> customers= (List<Customer>)query.getResultList();
		if(customers != null && customers.size()>0){
			return customers.get(0);
		}
		return null;
	}

	@Override
	public List<Customer> getCustomers() {
		String queryString="FROM Customer";
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Customer> customers= (List<Customer>)query.getResultList();
		return customers;
	}

	@Override
	public List<Customer> getCustomerByCriteria(SearchCriteria searchCriteria) {

		String queryString="from Customer WHERE client.clientId = coalesce(:clientId, client.clientId) and lower(contactNo) = coalesce(:contactNo, contactNo) and lower(customerCode) = coalesce(:customerCode, customerCode)  and lower(emailId) = coalesce(:emailId, emailId) and  lower(customerName) LIKE :customerName";
		Query query= getEntityManager().createQuery(queryString);

		String customerName = StringUtils.isEmpty(searchCriteria.getCustomerName())?"":searchCriteria.getCustomerName().toLowerCase();
		String emailId = StringUtils.isEmpty(searchCriteria.getEmailId())?null:searchCriteria.getEmailId().toLowerCase();
		String contactNo = StringUtils.isEmpty(searchCriteria.getContactNo())?null:searchCriteria.getContactNo().toLowerCase();
		String customerCode = StringUtils.isEmpty(searchCriteria.getCustomerCode())?null:searchCriteria.getCustomerCode().toLowerCase();
		query.setParameter("customerName", "%"+customerName+"%");
		query.setParameter("contactNo", contactNo);
		query.setParameter("clientId", searchCriteria.getClientId());
		query.setParameter("customerCode", customerCode);
		query.setParameter("emailId", emailId);

		@SuppressWarnings("unchecked")
		List<Customer> result= (List<Customer>)query.getResultList();
		return result;

	}

	@Override
	public Long saveCustomer(Customer customer) {
		if(customer.getCustomerId()==null){

			String customerCode = customer.getCustomerCode();
			if(customerCode==null){
				customerCode=identifierGenerator.getUniqueIdentifierForCustomer(customer);
				customer.setCustomerCode(customerCode);
				for(Unit unit : customer.getUnits()){
					if(unit.getUnitCode()==null){
						unit.setUnitCode(identifierGenerator.getUniqueIdentifierForUnit(unit));
					}
				}
			}

			getEntityManager().persist(customer);
		}

		else{
			getEntityManager().merge(customer);
		}
		return customer.getCustomerId();
	}

	@Override
	public void saveUnit(List<Unit> units, Long customerId) {

		if(units!=null && units.size()>0){
			for(Unit unit : units){
				unit.setCustomerId(customerId);
				saveUnit(unit);
			}
		}
	}

	@Override
	public void saveUnit(Unit unit) {

		String unitCode = unit.getUnitCode();
		if(unit.getUnitId() == null){

			if(unitCode==null){
				unitCode=identifierGenerator.getUniqueIdentifierForUnit(unit);
				unit.setUnitCode(unitCode);
			}
			getEntityManager().persist(unit);
		}
		else{
			if(unitCode==null){
				unitCode=identifierGenerator.getUniqueIdentifierForUnit(unit);
				unit.setUnitCode(unitCode);
			}
			deleteEquipmentDtlExclusion(unit.getEquipmentDetails(), unit.getUnitId());
			getEntityManager().merge(unit);
		}
		getEntityManager().flush();
	}	

	@Override
	public List<Unit> getUnits(Long customerId) {
		String queryString="FROM Unit u WHERE u.customerId = "+customerId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Unit> units= (List<Unit>)query.getResultList();
		return units;
	}

	public void deleteEquipmentDtlExclusion(
			List<EquipmentDetail> equipmentDetails, Long unitId) {

		List<Long> equipmentDtlId = new ArrayList<Long>();
		if (equipmentDtlId != null) {
			for (EquipmentDetail equipmentDetail : equipmentDetails) {
				if(equipmentDetail.getEquipmentDtlId() != null){
					equipmentDtlId.add(equipmentDetail.getEquipmentDtlId());
				}
			}

			if (equipmentDtlId.size() == 0) {
				equipmentDtlId.add(-1L);
			}
		}

		String deleteQuery = "Delete from tb_equipment_detail where UNIT_ID =:UNIT_ID and EQUIPMENT_DTL_ID not in :EQUIPMENT_DTL_ID";

		Query query=(Query) getEntityManager().createNativeQuery(deleteQuery).setParameter("UNIT_ID", unitId).setParameter("EQUIPMENT_DTL_ID", equipmentDtlId);
		query.executeUpdate();	

	}

	@Override
	public boolean isCustomerExists(Customer customer) {

		String queryString="from Customer WHERE lower(emailId) = coalesce(:emailId, emailId) or lower(contactNo) = coalesce(:contactNo, contactNo)";
		Query query= getEntityManager().createQuery(queryString);

		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setContactNo(customer.getContactNo());
		searchCriteria.setEmailId(customer.getEmailId());
		String emailId = StringUtils.isEmpty(searchCriteria.getEmailId())?null:searchCriteria.getEmailId().toLowerCase();
		String contactNo = StringUtils.isEmpty(searchCriteria.getContactNo())?null:searchCriteria.getContactNo().toLowerCase();

		query.setParameter("emailId", emailId);
		query.setParameter("contactNo", contactNo);
		@SuppressWarnings("unchecked")
		List<Customer> result= (List<Customer>)query.getResultList();
		if(result != null && result.size()>0){
			Customer existingCustomer = result.get(0);
			if(existingCustomer != null){
				return true;
			}
		}
		return false;
	}

}