package com.techvisio.eserve.db.impl;

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
import com.techvisio.eserve.beans.Equipment;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.beans.UserPrivilege;
import com.techvisio.eserve.db.CustomerDao;
import com.techvisio.eserve.factory.UniqueIdentifierGenerator;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

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
	public void saveComplaint(CustomerComplaint customerComplaint) {
		if(customerComplaint.getComplaintId()==null){
			getEntityManager().persist(customerComplaint);
		}

		else{ 
			getEntityManager().merge(customerComplaint);
		}
	}

	@Override
	public List<CustomerComplaint> getCustomerComplaints(Long customerId) {
		String queryString="FROM CustomerComplaint cus WHERE cus.customerId = "+ customerId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<CustomerComplaint> complaints= (List<CustomerComplaint>)query.getResultList();
		return complaints;
	}


	@Override
	public CustomerComplaint getCustomerComplaint(Long complaintId) {
		String queryString="FROM CustomerComplaint cus WHERE cus.complaintId = "+complaintId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<CustomerComplaint> complaint= (List<CustomerComplaint>)query.getResultList();
		if(complaint != null && complaint.size()>0){
			return complaint.get(0);
		}
		return null;
	}

	@Override
	public Customer getCustomerBasicInfo(Long customerId) {
		String queryString="SELECT CUSTOMER_ID, CREATED_BY, CREATED_ON, UPDATED_BY, UPDATED_ON, CONTACT_NO, CUSTOMER_CODE, CUSTOMER_NAME, CUSTOMER_TYPE, EMAIL_ID, Client_Id, ADDRESS_ID FROM tb_customer_detail where CUSTOMER_ID = "+customerId ;
		Query query=getEntityManager().createNativeQuery(queryString, Customer.class);
		List<Customer> customers= (List<Customer>)query.getResultList();
		if(customers != null && customers.size()>0){
			return customers.get(0);
		}
		return null;
	}

	@Override
	public Unit getUnitBasicInfo(Long unitId) {
		String queryString="SELECT UNIT_ID, CREATED_BY, CREATED_ON, UPDATED_BY, UPDATED_ON, CUSTOMER_ID, EXTERNAL_ID, HEIGHT, LENGTH, SERVICE_CATEGORY, SERVICE_PARTY, UNIT_CATEGORY, WIDTH, Client_Id, ADDRESS_ID, UNIT_CODE, CONTRACT_START_ON, CONTRACT_EXPIRE_ON FROM tb_unit_detail where UNIT_ID = "+unitId ;
		Query query=getEntityManager().createNativeQuery(queryString, Unit.class);
		List<Unit> units= (List<Unit>)query.getResultList();
		if(units != null && units.size()>0){
			return units.get(0);
		}
		return null;
	}

	@Override
	public void saveComplaintResolution(Long complaintId, ComplaintResolution complaintResolution) {
		if(complaintResolution.getComplaintId()==null){
			CustomerComplaint complaint = getCustomerComplaint(complaintId);	
			complaintResolution.setCustomerComplaint(complaint);
			getEntityManager().persist(complaintResolution);
		}

		else{ 
			getEntityManager().merge(complaintResolution);
		}
	}

	@Override
	public ComplaintResolution getComplaintResolution(Long complaintId) {
		String queryString="FROM ComplaintResolution cus WHERE cus.complaintId = "+ complaintId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<ComplaintResolution> resolutions= (List<ComplaintResolution>)query.getResultList();
		if(resolutions != null && resolutions.size()>0){
			return resolutions.get(0);
		}
		return null;
	}


	@Override
	public void saveComplaintAssignment(Long complaintId, ComplaintAssignment complaintAssignment) {
		if(complaintAssignment.getComplaintId()==null){
			CustomerComplaint complaint = getCustomerComplaint(complaintId);
			if(complaint.getStatus()==null || complaint.getStatus().equals(AppConstants.complaintStatus.UNASSIGNED.name())){
				complaint.setStatus(AppConstants.complaintStatus.ASSIGNED.name());
				saveComplaint(complaint);	
			}

			complaintAssignment.setCustomerComplaint(complaint);
			
			getEntityManager().persist(complaintAssignment);
		}

		else{
			getEntityManager().merge(complaintAssignment);
		}
	}

	@Override
	public ComplaintAssignment getComplaintAssignment(Long complaintId) {
		String queryString="FROM ComplaintAssignment cus WHERE cus.complaintId = "+ complaintId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<ComplaintAssignment> assignments= (List<ComplaintAssignment>)query.getResultList();
		if(assignments != null && assignments.size()>0){
			return assignments.get(0);
		}
		return null;
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
