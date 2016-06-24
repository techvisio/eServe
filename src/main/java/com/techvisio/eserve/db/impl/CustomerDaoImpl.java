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
import com.techvisio.eserve.beans.SearchResultData;
import com.techvisio.eserve.beans.ServiceAgreement;
import com.techvisio.eserve.beans.ServiceAgreementFinanceHistory;
import com.techvisio.eserve.beans.ServiceAgreementHistory;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitHistory;
import com.techvisio.eserve.db.CacheDao;
import com.techvisio.eserve.db.CustomerDao;
import com.techvisio.eserve.exception.NoEntityFoundException;
import com.techvisio.eserve.factory.UniqueIdentifierGenerator;
import com.techvisio.eserve.util.CommonUtil;

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
	public SearchResultData getCustomerByCriteria(SearchCriteria searchCriteria) {

		SearchResultData<Customer> searchResultData= new SearchResultData<Customer>();
		String ascOrDsc = searchCriteria.getIsAscending()?"ASC":"DESC";
		
		String sortBy=null;
		try {
			sortBy = CommonUtil.getFieldValue(Customer.class, searchCriteria.getSortBy());
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String queryString="select CUSTOMER_ID,CREATED_BY,CREATED_ON,UPDATED_BY,UPDATED_ON,CONTACT_NO,CUSTOMER_CODE,CUSTOMER_NAME,EMAIL_ID,Client_Id,ADDRESS_ID,CUSTOMER_TYPE_ID from tb_customer_detail WHERE client_Id = coalesce(:client_Id, client_Id) and lower(contact_No) = coalesce(:contact_No, contact_No) and lower(customer_Code) = coalesce(:customer_Code, customer_Code)  and lower(email_Id) = coalesce(:email_Id, email_Id) and  lower(customer_Name) LIKE :customer_Name ORDER BY  "+sortBy +" "+ascOrDsc+" limit :START_INDEX,:PAGE_SIZE";
		Query query= getEntityManager().createNativeQuery(queryString, Customer.class);
		
		String queryString1="SELECT count(*),'totalCount' FROM (select * from tb_customer_detail WHERE client_Id = coalesce(:client_Id, client_Id) and lower(contact_No) = coalesce(:contact_No, contact_No) and lower(customer_Code) = coalesce(:customer_Code, customer_Code)  and lower(email_Id) = coalesce(:email_Id, email_Id) and  lower(customer_Name) LIKE :customer_Name)a";
		Query query1= getEntityManager().createNativeQuery(queryString1);
		
		String customerName = StringUtils.isEmpty(searchCriteria.getCustomerName())?"":searchCriteria.getCustomerName().toLowerCase();
		String emailId = StringUtils.isEmpty(searchCriteria.getEmailId())?null:searchCriteria.getEmailId().toLowerCase();
		String contactNo = StringUtils.isEmpty(searchCriteria.getContactNo())?null:searchCriteria.getContactNo().toLowerCase();
		String customerCode = StringUtils.isEmpty(searchCriteria.getCustomerCode())?null:searchCriteria.getCustomerCode().toLowerCase();
		
		int pageSize,pageNo;
		if(searchCriteria.getPageSize()==0)
		{
			pageSize = 3;
		}
		else
		{
			pageSize = searchCriteria.getPageSize();
		}
		if(searchCriteria.getPageNo() == 0)
		{
			pageNo = 1;
		}
		else
		{
			pageNo = searchCriteria.getPageNo();
		}
		
		int startIndex = (pageSize * pageNo) - pageSize;
		
		
		query.setParameter("customer_Name", "%"+customerName+"%");
		query.setParameter("contact_No", contactNo);
		query.setParameter("client_Id", searchCriteria.getClientId());
		query.setParameter("customer_Code", customerCode);
		query.setParameter("email_Id", emailId);
		query.setParameter("PAGE_SIZE", pageSize);
		query.setParameter("START_INDEX", startIndex);
		
		query1.setParameter("customer_Name", "%"+customerName+"%");
		query1.setParameter("contact_No", contactNo);
		query1.setParameter("client_Id", searchCriteria.getClientId());
		query1.setParameter("customer_Code", customerCode);
		query1.setParameter("email_Id", emailId);

		@SuppressWarnings("unchecked")
		List<Object[]> counts = query1.getResultList();
		for (Object[] count : counts) {
			Long count1 = (long) ((Number) count[0]).intValue();
			searchResultData.setTotalCount(count1);
		}
		
		@SuppressWarnings("unchecked")
		List<Customer> result= (List<Customer>)query.getResultList();
		searchResultData.setObjectData(result);
		return searchResultData;
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
				unit.getServiceAgreement().setVersionId(unit.getVersionId());
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
			getEntityManager().flush();
			if(unit.getServiceAgreement().getUnitId()==null){
				unit.getServiceAgreement().setUnitId(unit.getUnitId());
				unit.getServiceAgreement().setVersionId(unit.getVersionId());
				saveServiceAgreement(unit.getServiceAgreement());
			}
		}
		else{
			if(unitCode==null){
				unitCode=identifierGenerator.getUniqueIdentifierForUnit(unit);
				unit.setUnitCode(unitCode);
			}
			unit.getServiceAgreement().setVersionId(unit.getVersionId());
			updateServiceAgreement(unit.getServiceAgreement(), unit.getUnitId());
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

	private void updateServiceAgreement(ServiceAgreement agreement, Long unitId){

		if(agreement !=null && agreement.getAgreementDuration() != null){
			Date contractExpireDate = getDurationValue(agreement.getContractStartOnString(), agreement.getAgreementDuration().getAgreementDurationId());
			agreement.setContractExpireOn(contractExpireDate);
			if(agreement.getServiceAgreementFinance() != null){
				agreement.getServiceAgreementFinance().setUnitId(unitId);
				agreement.getServiceAgreementFinance().setVersionId(agreement.getVersionId());
			}
		}

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
	public Customer getEmailId(String EmailId) {
		String queryString="FROM Customer cus WHERE cus.emailId = "+" '" + EmailId +" ' ";
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Customer> customers= (List<Customer>)query.getResultList();

		if(customers != null && customers.size()>0){
			return customers.get(0);
		}
		return null;
	}

	@Override
	public Customer getContactNo(String ContactNo) {
		String queryString="FROM Customer cus WHERE cus.contactNo = "+" '" + ContactNo +" ' ";
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Customer> customers= (List<Customer>)query.getResultList();
		if(customers != null && customers.size()>0){
			return customers.get(0);
		}
		return null;
	}

}
