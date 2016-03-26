package com.techvisio.eserve.db.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.AgreementDuration;
import com.techvisio.eserve.beans.ApproveUnitDtl;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.EquipmentHistory;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.ServiceAgreement;
import com.techvisio.eserve.beans.ServiceAgreementFinanceHistory;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitHistory;
import com.techvisio.eserve.db.CacheDao;
import com.techvisio.eserve.db.CustomerDao;
import com.techvisio.eserve.exception.NoEntityFoundException;
import com.techvisio.eserve.factory.UniqueIdentifierGenerator;
import com.techvisio.eserve.util.AppConstants;

@Component
public class CustomerDaoImpl extends BaseDao implements CustomerDao{

	@Autowired
	UniqueIdentifierGenerator identifierGenerator;

	@Autowired
	CacheDao cacheDao;

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
			populateUniqueIdentifierInCustomerAndUnit(customer, customerCode);

			getEntityManager().persist(customer);
			getEntityManager().flush();
			List<Unit> units= customer.getUnits();
			for(Unit unit : units){

				unit = getUnit(unit.getUnitId());
				if(unit.getServiceAgreement().getUnitId()==null){
					unit.getServiceAgreement().setUnitId(unit.getUnitId());
				}
				unit.getServiceAgreement().setVersionId(unit.getVersionId());
				saveUnit(unit);			
			}
		}

		else{
			for(Unit unit : customer.getUnits()){
				insertServiceExpirationDateInServiceAgreement(unit);
			}
			getEntityManager().merge(customer);
		}
		return customer.getCustomerId();
	}

	private void populateUniqueIdentifierInCustomerAndUnit(Customer customer,
			String customerCode) {
		if(customerCode==null){
			customerCode=identifierGenerator.getUniqueIdentifierForCustomer(customer);
			customer.setCustomerCode(customerCode);
			for(Unit unit : customer.getUnits()){
				if(unit.getUnitCode()==null){
					unit.setUnitCode(identifierGenerator.getUniqueIdentifierForUnit(unit));
				}

				insertServiceExpirationDateInServiceAgreement(unit);
			}
		}
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
	public Long saveUnit(Unit unit) {

		String unitCode = unit.getUnitCode();
		if(unit.getUnitId() == null){

			if(unitCode==null){
				unitCode=identifierGenerator.getUniqueIdentifierForUnit(unit);
				unit.setUnitCode(unitCode);
			}
			insertServiceExpirationDateInServiceAgreement(unit);
			getEntityManager().persist(unit);
		}
		else{
			if(unitCode==null){
				unitCode=identifierGenerator.getUniqueIdentifierForUnit(unit);
				unit.setUnitCode(unitCode);
			}
			insertServiceExpirationDateInServiceAgreement(unit);
			deleteEquipmentDtlExclusion(unit.getEquipmentDetails(), unit.getUnitId());
			getEntityManager().merge(unit);
		}
		getEntityManager().flush();
		return unit.getUnitId();
	}

	private void insertServiceExpirationDateInServiceAgreement(Unit unit) {
		if(unit.getServiceAgreement().getContractStartOnString()!= null && unit.getServiceAgreement().getAgreementDuration() != null && unit.getServiceAgreement().getContractExpireOn()==null){
			Long durationId = unit.getServiceAgreement().getAgreementDuration().getAgreementDurationId();
			Date contractExpireDate = getDurationValue(unit.getServiceAgreement().getContractStartOnString(), durationId);
			unit.getServiceAgreement().setContractExpireOn(contractExpireDate);
		}
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
			if(equipmentDetails!=null){
				for (EquipmentDetail equipmentDetail : equipmentDetails) {
					if(equipmentDetail.getEquipmentDtlId() != null){
						equipmentDtlId.add(equipmentDetail.getEquipmentDtlId());
					}
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

	@Override
	public Unit getUnit(Long unitId) {
		String queryString="FROM Unit u WHERE u.unitId = "+unitId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Unit> units= (List<Unit>)query.getResultList();
		if(units != null && units.size()>0){
			return units.get(0);
		}
		return null;
	}

	public void saveServiceAgreement(ServiceAgreement agreement) {

		if(agreement.getServiceAgreementId() == null){
			getEntityManager().persist(agreement);
		}
		else{
			getEntityManager().merge(agreement);
		}
		getEntityManager().flush();
	}

	@Override
	public void saveServiceAgreementHistory(ServiceAgreementHistory history) {
		if(history.getAgreementHistoryId()==null){
			getEntityManager().persist(history);
		}
	}	

	@Override
	public void saveServiceAgreementFinanceHistory(ServiceAgreementFinanceHistory financeHistory) {
		if(financeHistory.getSrvcAgrmntFinancHstoryId()==null){
			getEntityManager().persist(financeHistory);
		}
	}	

	@Override
	public void saveUnitHistory(UnitHistory unitHistory) {
			getEntityManager().persist(unitHistory);
	}
	
	@Override
	public void saveEquipmentHistory(EquipmentHistory equipmentHistory) {
			getEntityManager().persist(equipmentHistory);
	}

	@Override
	public void updateServiceAgreement(ServiceAgreement agreement, Long unitId){

		Date contractExpireDate = getDurationValue(agreement.getContractStartOnString(), agreement.getAgreementDuration().getAgreementDurationId());
		agreement.setContractExpireOn(contractExpireDate);
		if(agreement.getServiceAgreementFinance() != null){
			agreement.getServiceAgreementFinance().setUnitId(unitId);
		}
		agreement.getServiceAgreementFinance().setVersionId(agreement.getVersionId());
		Unit unit = getUnit(unitId);
		
		saveUnit(unit);
		saveServiceAgreement(agreement);

	}

	private Date getDurationValue(String contractStartDate, Long durationId) {
		String startDateString = contractStartDate;
		Date startDate = null;
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTime().withZoneUTC();
		if(!StringUtils.isEmpty(startDateString)){
			startDate = parser2.parseDateTime(startDateString).toDate();
		}

		Calendar c = Calendar.getInstance(); 
		c.setTime(startDate);
		AgreementDuration agreementDuration = cacheDao.getAgreementDuration(durationId); 
		c.add(Calendar.MONTH, agreementDuration.getDuration());

		Date date = c.getTime();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
		Date contractExpireDate = c.getTime();
		return contractExpireDate;
	} 

	@Override
	public List<ServiceAgreementHistory> getServiceAgreementHistoryForUnit(Long unitId) {
		String queryString="FROM ServiceAgreementHistory sah WHERE sah.UnitId = "+ unitId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<ServiceAgreementHistory> agreementHistories= (List<ServiceAgreementHistory>)query.getResultList();
		return agreementHistories;
	}

	@Override
	public ApproveUnitDtl getUnitForApproval(Long unitId){


		Unit unit = getUnit(unitId);

		if(unit==null){
			throw new NoEntityFoundException("No Unit found with id : "+unitId);
		}
		Customer customer = getCustomer(unit.getCustomerId());

		ApproveUnitDtl unitDtl = new ApproveUnitDtl();

		unitDtl.setUnit(unit);
		unitDtl.setCustomerCode(customer.getCustomerCode());
		unitDtl.setCustomerName(customer.getCustomerName());
		unitDtl.setContactNo(customer.getContactNo());
		unitDtl.setEmailId(customer.getEmailId());

		return unitDtl;

	}

	@Override
	public List<Customer> getEmailId(String EmailId) {
		String queryString="FROM Customer cus WHERE cus.emailId = "+" '" + EmailId +" ' ";
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Customer> customers= (List<Customer>)query.getResultList();
		return customers;
	}
	
	@Override
	public List<Customer> getContactNo(String ContactNo) {
		String queryString="FROM Customer cus WHERE cus.contactNo = "+" '" + ContactNo +" ' ";
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Customer> customers= (List<Customer>)query.getResultList();
		return customers;
	}

}