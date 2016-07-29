package com.techvisio.eserve.db.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ComplaintAssignment;
import com.techvisio.eserve.beans.ComplaintEquipment;
import com.techvisio.eserve.beans.ComplaintResolution;
import com.techvisio.eserve.beans.ComplaintSearchData;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.PmsComplaint;
import com.techvisio.eserve.beans.SearchComplaint;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.db.ComplaintDao;
import com.techvisio.eserve.factory.UniqueIdentifierGenerator;
import com.techvisio.eserve.manager.CustomerManager;
import com.techvisio.eserve.util.AppConstants;

@Component
public class ComplaintDaoImpl extends BaseDao implements ComplaintDao{

	@Autowired
	UniqueIdentifierGenerator identifierGenerator;

	@Autowired
	CustomerManager customerManager;

	@Override
	public Long saveComplaint(CustomerComplaint customerComplaint) {
		if(customerComplaint.getComplaintId()==null){

			String complaintCode = customerComplaint.getComplaintCode();
			if(complaintCode==null){
				complaintCode = identifierGenerator.getUniqueIdentifierForComplaint(customerComplaint);
				customerComplaint.setComplaintCode(complaintCode);
			}
			getEntityManager().persist(customerComplaint);
		}

		else{ 
			getEntityManager().merge(customerComplaint);
		}
		return customerComplaint.getComplaintId();
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
	public List<CustomerComplaint> getAllComplaintsForUnit(Long unitId) {
		String queryString="FROM CustomerComplaint cus WHERE cus.unit.unitId = "+ unitId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<CustomerComplaint> complaints= (List<CustomerComplaint>)query.getResultList();
		return complaints;
	}
	public List<CustomerComplaint> getCustomerComplaintByUnitId(Long unitId) {
		String queryString="FROM CustomerComplaint cus WHERE cus.unit.unitId = "+ unitId;
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
		String queryString="SELECT CUSTOMER_ID, CREATED_BY, CREATED_ON, UPDATED_BY, UPDATED_ON, CONTACT_NO, CUSTOMER_CODE, CUSTOMER_NAME, CUSTOMER_TYPE_ID, EMAIL_ID, Client_Id, ADDRESS_ID FROM tb_customer_detail where CUSTOMER_ID = "+customerId ;
		Query query=getEntityManager().createNativeQuery(queryString, Customer.class);
		List<Customer> customers= (List<Customer>)query.getResultList();
		if(customers != null && customers.size()>0){
			return customers.get(0);
		}
		return null;
	}

	@Override
	public Unit getUnitBasicInfo(Long unitId) {
		String queryString="SELECT UNIT_ID, CREATED_BY, LAST_APPROVAL_DATE, LAST_APPROVED_BY, CREATED_ON, UPDATED_BY, UPDATED_ON, CUSTOMER_ID, ASSET_NO, MACHINE_SERIAL_NO,MODEL_NO,APPROVAL_STATUS, VERSION_ID, UNIT_CATEGORY_ID,Client_Id, ADDRESS_ID, UNIT_CODE FROM tb_unit_detail where UNIT_ID = "+unitId ;
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
			if(complaint.getStatus()==null || complaint.getStatus().equals(AppConstants.ComplaintStatus.UNASSIGNED.name())){
				complaint.setStatus(AppConstants.ComplaintStatus.ASSIGNED.name());
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
	public List<SearchComplaintCustomer> getCustomerForComplaintByCriteria(SearchCriteria searchCriteria) {

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
		List<SearchComplaintCustomer> complaintCustomers =  new ArrayList<SearchComplaintCustomer>();
		if(result != null && result.size()>0){

			for(Customer customer : result ){

				SearchComplaintCustomer complaintCustomer = new SearchComplaintCustomer();
				complaintCustomer.setCustomerId(customer.getCustomerId());
				complaintCustomer.setContactNo(customer.getContactNo());
				complaintCustomer.setCustomerCode(customer.getCustomerCode());
				complaintCustomer.setCustomerName(customer.getCustomerName());
				if(customer.getCustomerType() != null){
					complaintCustomer.setCustomerType(customer.getCustomerType().getCustomerType());
				}
				complaintCustomer.setEmailId(customer.getEmailId());

				complaintCustomers.add(complaintCustomer);
			}
		}
		return complaintCustomers;
	}

	@Override
	public List<SearchComplaintUnit> getSearchUnitByCustomerId(Long customerId){

		List<SearchComplaintUnit> complaintUnits = new ArrayList<SearchComplaintUnit>();
		String queryString="FROM Unit u WHERE u.customerId = "+customerId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Unit> units= (List<Unit>)query.getResultList();

		if(units != null && units.size()>0){
			for(Unit unit : units ){

				SearchComplaintUnit complaintUnit = new SearchComplaintUnit();
				complaintUnit.setExternalId(unit.getAssetNo());
				complaintUnit.setServiceCategory(unit.getServiceAgreement().getServiceCategory());
				complaintUnit.setServiceParty(unit.getServiceAgreement().getServiceProvider().getServiceProvider());
				complaintUnit.setUnitCategory(unit.getUnitCategory().getUnitType());
				complaintUnit.setUnitCode(unit.getUnitCode());
				complaintUnit.setUnitId(unit.getUnitId());

				complaintUnits.add(complaintUnit);
			}
		}
		return complaintUnits;
	}

	@Override
	public List<SearchComplaint> getComplaintSearchByUnitId(Long unitId){

		List<SearchComplaint> searchComplaints = new ArrayList<SearchComplaint>();

		String queryString="FROM CustomerComplaint c WHERE c.unit.unitId = "+unitId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<CustomerComplaint> complaints= (List<CustomerComplaint>)query.getResultList();

		if(complaints != null && complaints.size()>0){

			for(CustomerComplaint customerComplaint : complaints){
				SearchComplaint complaint = new SearchComplaint();
				if(customerComplaint != null && customerComplaint.getComplaintAssignment()!=null){
					complaint.setAssignTo(customerComplaint.getComplaintAssignment().getUser().getUserName());
				}
				complaint.setComplaintId(customerComplaint.getComplaintId());
				complaint.setPriority(customerComplaint.getPriority());

				//removing time stamp from date
				Date date = customerComplaint.getSlaDate();

				DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
				String slaDate = outputFormatter.format(date);
				complaint.setSlaDate(slaDate);

				complaint.setStatus(customerComplaint.getStatus());
				complaint.setComplaintCode(customerComplaint.getComplaintCode());   
				searchComplaints.add(complaint);
			}
		}
		return searchComplaints;
	}	

	@Override
	public List<SearchComplaintCustomer> getCustomerByComplaintCode(SearchCriteria searchCriteria) {

		String queryString="from CustomerComplaint WHERE lower(complaintCode) = coalesce(:complaintCode, complaintCode)";
		Query query= getEntityManager().createQuery(queryString);

		String complaintCode = StringUtils.isEmpty(searchCriteria.getComplaintCode())?null:searchCriteria.getComplaintCode().toLowerCase();
		query.setParameter("complaintCode", complaintCode);

		@SuppressWarnings("unchecked")
		CustomerComplaint customerComplaint = new CustomerComplaint();
		List<CustomerComplaint> result= (List<CustomerComplaint>)query.getResultList();
		List<SearchComplaintCustomer> complaintCustomers =  new ArrayList<SearchComplaintCustomer>();
		List<SearchComplaint> searchComplaints =  new ArrayList<SearchComplaint>();
		if(result != null && result.size()>0){
			customerComplaint = result.get(0);
		}

		Customer customer = customerManager.getCustomer(customerComplaint.getCustomerId());
		if(customer!=null){
			SearchComplaintCustomer complaintCustomer = new SearchComplaintCustomer();
			complaintCustomer.setCustomerId(customer.getCustomerId());
			complaintCustomer.setContactNo(customer.getContactNo());
			complaintCustomer.setCustomerCode(customer.getCustomerCode());
			complaintCustomer.setCustomerName(customer.getCustomerName());
			if(customer.getCustomerType() != null){
				complaintCustomer.setCustomerType(customer.getCustomerType().getCustomerType());
			}
			complaintCustomer.setEmailId(customer.getEmailId());
			List<SearchComplaintUnit> complaintUnits = new ArrayList<SearchComplaintUnit>();

			for(Unit unit : customer.getUnits()){

				SearchComplaintUnit complaintUnit = new SearchComplaintUnit();
				complaintUnit.setExternalId(unit.getAssetNo());
				complaintUnit.setServiceCategory(unit.getServiceAgreement().getServiceCategory());
				complaintUnit.setServiceParty(unit.getServiceAgreement().getServiceProvider().getServiceProvider());
				complaintUnit.setUnitCategory(unit.getUnitCategory().getUnitType());
				complaintUnit.setUnitCode(unit.getUnitCode());
				complaintUnit.setUnitId(unit.getUnitId());

				List<SearchComplaint> complaints = new ArrayList<SearchComplaint>();
				List<CustomerComplaint> customerComplaints = getCustomerComplaintByUnitId(unit.getUnitId());
				for(CustomerComplaint customerComplaint2 : customerComplaints){
					SearchComplaint complaint = new SearchComplaint();
					if(customerComplaint != null && customerComplaint.getComplaintAssignment()!=null){
						complaint.setAssignTo(customerComplaint.getComplaintAssignment().getUser().getUserName());
					}
					complaint.setComplaintId(customerComplaint.getComplaintId());
					complaint.setPriority(customerComplaint.getPriority());
					complaint.setSlaDate(customerComplaint.getSlaDateString());
					complaint.setStatus(customerComplaint.getStatus());

					complaints.add(complaint);
				}
				complaintUnit.setComplaints(complaints); 
				complaintUnits.add(complaintUnit);

			}
			complaintCustomer.setUnits(complaintUnits);				
			complaintCustomers.add(complaintCustomer);
		}
		return complaintCustomers;
	}


	@Override
	public List<ComplaintSearchData> getComplaintBySLA(Long clientId,String code) {

		List<ComplaintSearchData> complaints = new ArrayList<ComplaintSearchData>();		
		String queryString=getComplaintQueryBySLACode(clientId, code);
		Query query=getEntityManager().createNativeQuery(queryString);
		List<Object[]> results=query.getResultList();
		for(Object[] result :results){
			ComplaintSearchData complaintsearchdata=new ComplaintSearchData();			
			Long customerid = (long) ((Number) result[0]).intValue();
			complaintsearchdata.setCustomerId(customerid);
			complaintsearchdata.setCustomerCode((String) result[1]);
			Long unitId = (long) ((Number) result[2]).intValue();
			complaintsearchdata.setUnitId(unitId);
			complaintsearchdata.setUnitCode((String) result[3]);
			Long complaintid = (long) ((Number) result[4]).intValue();
			complaintsearchdata.setComplaintId(complaintid);
			complaintsearchdata.setComplaintCode((String) result[5]);
			complaintsearchdata.setIssue((String) result[6]);
			complaintsearchdata.setStatus((String) result[7]);
			complaintsearchdata.setPriority((String) result[8]);
			if(result[9] != null){
				complaintsearchdata.setAssignTo((String) result[9]);
			}
			complaints.add(complaintsearchdata);	
		}
		return complaints;	
	}



	private String getComplaintQueryBySLACode(Long clientId,String code) {

		String  queryString="SELECT CD.CUSTOMER_ID,CD.CUSTOMER_CODE,UD.UNIT_ID,UD.UNIT_CODE,CC.COMPLAINT_ID, CC.COMPLAINT_CODE, TIM.VALUE ,CC.STATUS,Case when cc.PRIORITY = 'C' then 'CRITICAL' when cc.PRIORITY = 'H' then 'HIGH' when cc.PRIORITY = 'M' then 'MEDIUM' else 'LOW' End PRIORITY,TU.USER_NAME"
				+" From tb_customer_detail CD left join tb_unit_detail UD on UD.CUSTOMER_ID = CD.CUSTOMER_ID"
				+" left join tb_customer_complaint CC on CC.UNIT_ID = UD.UNIT_ID"
				+" left join tb_complaint_assignment CA on CA.COMPLAINT_ID = CC.COMPLAINT_ID "
				+ " left join tb_issue_master TIM ON TIM.ISSUE_ID = CC.ISSUE_ID"
				+ " left join tb_user TU ON TU.USER_ID = CA.USER_ID where cc.client_Id="+ clientId;

		if(code.equalsIgnoreCase("TODAY")){
			queryString=queryString+" AND Date(CC.SLA_DATE) = CURDATE()";
		}
		else if(code.equalsIgnoreCase("DUE")){
			queryString=queryString+" AND Date(CC.SLA_DATE) > CURDATE()";
		}
		else {
			queryString=queryString+" AND Date(CC.SLA_DATE) < CURDATE()";
		}
		return queryString;

	}


	@Override
	public List<ComplaintSearchData> getComplaintByASSIGNMENT(Long clientId,String code) {

		List<ComplaintSearchData> complaints = new ArrayList<ComplaintSearchData>();		
		String queryString=getComplaintQueryByASSIGNMENTCode(clientId, code);
		Query query=getEntityManager().createNativeQuery(queryString);
		List<Object[]> results=query.getResultList();
		for(Object[] result :results){
			ComplaintSearchData complaintsearchdata=new ComplaintSearchData();			
			Long customerid = (long) ((Number) result[0]).intValue();
			complaintsearchdata.setCustomerId(customerid);
			complaintsearchdata.setCustomerCode((String) result[1]);
			Long unitId = (long) ((Number) result[2]).intValue();
			complaintsearchdata.setUnitId(unitId);
			complaintsearchdata.setUnitCode((String) result[3]);
			Long complaintid = (long) ((Number) result[4]).intValue();
			complaintsearchdata.setComplaintId(complaintid);
			complaintsearchdata.setComplaintCode((String) result[5]);
			complaintsearchdata.setIssue((String) result[6]);
			complaintsearchdata.setStatus((String) result[7]);
			complaintsearchdata.setPriority((String) result[8]);
			if(result[9] != null){
				complaintsearchdata.setAssignTo((String) result[9]);
			}
			complaints.add(complaintsearchdata);

		}

		return complaints;	

	}
	private String getComplaintQueryByASSIGNMENTCode(Long clientId,String code) {

		String  queryString="SELECT CD.CUSTOMER_ID,CD.CUSTOMER_CODE,UD.UNIT_ID,UD.UNIT_CODE,CC.COMPLAINT_ID, CC.COMPLAINT_CODE, TIM.VALUE ,CC.STATUS,Case when cc.PRIORITY = 'C' then 'CRITICAL' when cc.PRIORITY = 'H' then 'HIGH' when cc.PRIORITY = 'M' then 'MEDIUM' else 'LOW' End PRIORITY,TU.USER_NAME"
				+" From tb_customer_detail CD left join tb_unit_detail UD on UD.CUSTOMER_ID = CD.CUSTOMER_ID"
				+" left join tb_customer_complaint CC on CC.UNIT_ID = UD.UNIT_ID"
				+" left join tb_complaint_assignment CA on CA.COMPLAINT_ID = CC.COMPLAINT_ID"
				+ " left join tb_issue_master TIM ON TIM.ISSUE_ID = CC.ISSUE_ID"
				+ " left join tb_user TU ON TU.USER_ID = CA.USER_ID where cc.client_Id="+ clientId;

		if(code.equalsIgnoreCase("ASSIGNED")){
			queryString=queryString+" AND CC.STATUS = 'ASSIGNED'";
		}
		else
		{
			queryString= queryString+" AND CC.STATUS='UNASSIGNED'";
		}
		return queryString;
	}
	@Override
	public List<ComplaintSearchData> getComplaintByPRIORITY(Long clientId,String code) {

		List<ComplaintSearchData> complaints = new ArrayList<ComplaintSearchData>();		
		String queryString=getComplaintQueryByPRIORITYcode(clientId, code);
		Query query=getEntityManager().createNativeQuery(queryString);
		List<Object[]> results=query.getResultList();
		for(Object[] result :results){
			ComplaintSearchData complaintsearchdata=new ComplaintSearchData();			
			Long customerid = (long) ((Number) result[0]).intValue();
			complaintsearchdata.setCustomerId(customerid);
			complaintsearchdata.setCustomerCode((String) result[1]);
			Long unitId = (long) ((Number) result[2]).intValue();
			complaintsearchdata.setUnitId(unitId);
			complaintsearchdata.setUnitCode((String) result[3]);
			Long complaintid = (long) ((Number) result[4]).intValue();
			complaintsearchdata.setComplaintId(complaintid);
			complaintsearchdata.setComplaintCode((String) result[5]);
			complaintsearchdata.setIssue((String) result[6]);
			complaintsearchdata.setStatus((String) result[7]);
			complaintsearchdata.setPriority((String) result[8]);
			if(result[9] != null){
				complaintsearchdata.setAssignTo((String) result[9]);
			}
			complaints.add(complaintsearchdata);

		}

		return complaints;	
	}
	private String getComplaintQueryByPRIORITYcode(Long clientId,String code) {

		String  queryString="SELECT CD.CUSTOMER_ID,CD.CUSTOMER_CODE,UD.UNIT_ID,UD.UNIT_CODE,CC.COMPLAINT_ID, CC.COMPLAINT_CODE, TIM.VALUE ,CC.STATUS,Case when cc.PRIORITY = 'C' then 'CRITICAL' when cc.PRIORITY = 'H' then 'HIGH' when cc.PRIORITY = 'M' then 'MEDIUM' else 'LOW' End PRIORITY,TU.USER_NAME"
				+" From tb_customer_detail CD left join tb_unit_detail UD on UD.CUSTOMER_ID = CD.CUSTOMER_ID"
				+" left join tb_customer_complaint CC on CC.UNIT_ID = UD.UNIT_ID"
				+" left join tb_complaint_assignment CA on CA.COMPLAINT_ID = CC.COMPLAINT_ID"
				+ " left join tb_issue_master TIM ON TIM.ISSUE_ID = CC.ISSUE_ID"
				+ " left join tb_user TU ON TU.USER_ID = CA.USER_ID where cc.client_Id="+ clientId;

		if(code.equalsIgnoreCase("CRITICAL")){
			queryString=queryString+" AND CC.PRIORITY = 'C'";
		}
		else if(code.equalsIgnoreCase("HIGH")){
			queryString=queryString+" AND CC.PRIORITY = 'H'";
		}
		else if( code.equalsIgnoreCase("MEDIUM")){
			queryString=queryString+" AND CC.PRIORITY = 'M'";
		}
		else{

			queryString=queryString+"  AND CC.PRIORITY = 'L'";
		}
		return queryString;
	}

	@Override
	public void saveComplaintEquipments(ComplaintEquipment complaintEquipment){

		if(complaintEquipment.getComplaintEquipmentId()==null){
			getEntityManager().persist(complaintEquipment);
		}
	}

	@Override
	public List<ComplaintEquipment> getComplaintEquipments(Long complaintId){

		String queryString="FROM ComplaintEquipment ce where ce.complaintId = " + complaintId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<ComplaintEquipment> result= (List<ComplaintEquipment>)query.getResultList();
		return result;
	}

	@Override
	public void createPmsComplaint(PmsComplaint pmsComplaint){
		if(pmsComplaint.getPmsComplaintId()==null){
			getEntityManager().persist(pmsComplaint);
		}
	}

	@Override
	public PmsComplaint getPmsComplaint(Long workitemId){
		String queryString="FROM PmsComplaint pc where pc.workitemId = " + workitemId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<PmsComplaint> result= (List<PmsComplaint>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}
}
