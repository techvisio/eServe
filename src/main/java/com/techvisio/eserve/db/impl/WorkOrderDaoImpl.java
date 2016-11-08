package com.techvisio.eserve.db.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ComplaintSearchData;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerUnitComplaint;
import com.techvisio.eserve.beans.PmsWorkOrder;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.SearchWorkOrder;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.WorkOrder;
import com.techvisio.eserve.beans.WorkOrderAssignment;
import com.techvisio.eserve.beans.WorkOrderEquipment;
import com.techvisio.eserve.beans.WorkOrderResolution;
import com.techvisio.eserve.beans.complaintSearchCriteria;
import com.techvisio.eserve.db.WorkOrderDao;
import com.techvisio.eserve.factory.UniqueIdentifierGenerator;
import com.techvisio.eserve.manager.CustomerManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;
import com.techvisio.eserve.util.DateUtil;
import com.techvisio.eserve.util.StringUtilities;

@Component
public class WorkOrderDaoImpl extends BaseDao implements WorkOrderDao{

	@Autowired
	UniqueIdentifierGenerator identifierGenerator;

	@Autowired
	CustomerManager customerManager;

	@Override
	public Long saveWorkOrder(WorkOrder workOrder) {
		if(workOrder.getWorkOrderId()==null){

			String workOrderNo = workOrder.getWorkOrderNo();
			if(workOrderNo==null){
				workOrderNo = identifierGenerator.getUniqueIdentifierForComplaint(workOrder);
				workOrder.setWorkOrderNo(workOrderNo);
			}
			getEntityManager().persist(workOrder);
		}

		else{ 
			getEntityManager().merge(workOrder);
		}
		return workOrder.getWorkOrderId();
	}

	@Override
	public List<WorkOrder> getAllComplaintsForUnit(Long unitId) {
		String queryString="FROM WorkOrder wo WHERE wo.workOrderType='Complaint' and  wo.unit.unitId = "+ unitId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkOrder> complaints= (List<WorkOrder>)query.getResultList();
		List<WorkOrder> clonedWorkOrders = new ArrayList<WorkOrder>(complaints);
		return clonedWorkOrders;
	}

	@Override
	public WorkOrder getWorkOrder(Long workOrderId) {
		String queryString="FROM WorkOrder wo WHERE wo.workOrderId = "+workOrderId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkOrder> workOrders= (List<WorkOrder>)query.getResultList();
		if(workOrders != null && workOrders.size()>0){
			WorkOrder clonedWorkOrder=SerializationUtils.clone(workOrders.get(0));
			return clonedWorkOrder;
		}
		return null;
	}

	@Override
	public Customer getCustomerBasicInfo(Long customerId) {
		String queryString="SELECT CUSTOMER_ID, CREATED_BY, CREATED_ON, UPDATED_BY, UPDATED_ON, CONTACT_NO, CUSTOMER_CODE, CUSTOMER_NAME, CUSTOMER_TYPE_ID, EMAIL_ID, Client_Id, ADDRESS_ID FROM TB_CUSTOMER_DETAIL where CUSTOMER_ID = "+customerId ;
		Query query=getEntityManager().createNativeQuery(queryString, Customer.class);
		List<Customer> customers= (List<Customer>)query.getResultList();
		if(customers != null && customers.size()>0){
			Customer clonedCustomer=SerializationUtils.clone(customers.get(0));
			return clonedCustomer;
		}
		return null;
	}

	@Override
	public Unit getUnitBasicInfo(Long unitId) {
		String queryString="SELECT UNIT_ID, CREATED_BY, LAST_APPROVAL_DATE, LAST_APPROVED_BY, CREATED_ON, UPDATED_BY, UPDATED_ON, CUSTOMER_ID, ASSET_NO, MACHINE_SERIAL_NO,MODEL_NO,APPROVAL_STATUS, VERSION_ID, UNIT_CATEGORY_ID,Client_Id, ADDRESS_ID, UNIT_CODE FROM TB_UNIT_DETAIL where UNIT_ID = "+unitId ;
		Query query=getEntityManager().createNativeQuery(queryString, Unit.class);
		List<Unit> units= (List<Unit>)query.getResultList();
		if(units != null && units.size()>0){
			Unit clonedUnit=SerializationUtils.clone(units.get(0));
			return clonedUnit;
		}
		return null;
	}

	@Override
	public void saveWorkOrderResolution(Long workOrderId, WorkOrderResolution workOrderResolution) {
		if(workOrderResolution.getWorkOrderId()==null){
			WorkOrder workOrder = getWorkOrder(workOrderId);	
			workOrderResolution.setWorkOrder(workOrder);
			getEntityManager().persist(workOrderResolution);
		}

		else{ 
			getEntityManager().merge(workOrderResolution);
		}
	}

	@Override
	public WorkOrderResolution getWorkOrderResolution(Long workOrderId) {
		String queryString="FROM WorkOrderResolution cus WHERE cus.workOrderId = "+ workOrderId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkOrderResolution> resolutions= (List<WorkOrderResolution>)query.getResultList();
		if(resolutions != null && resolutions.size()>0){
			return resolutions.get(0);
		}
		return null;
	}


	@Override
	public void saveWorkOrderAssignment(Long workOrderId, WorkOrderAssignment workOrderAssignment) {
		if(workOrderAssignment.getWorkOrderId()==null){
			WorkOrder workOrder = getWorkOrder(workOrderId);
			if(workOrder.getStatus()==null || workOrder.getStatus().equals(AppConstants.ComplaintStatus.UNASSIGNED.name())){
				workOrder.setStatus(AppConstants.ComplaintStatus.ASSIGNED.name());
				saveWorkOrder(workOrder);	
			}

			workOrderAssignment.setWorkOrder(workOrder);

			getEntityManager().persist(workOrderAssignment);
		}

		else{
			getEntityManager().merge(workOrderAssignment);
		}
	}

	@Override
	public WorkOrderAssignment getWorkOrderAssignment(Long workOrderId) {
		String queryString="FROM WorkOrderAssignment cus WHERE cus.workOrderId = "+ workOrderId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkOrderAssignment> assignments= (List<WorkOrderAssignment>)query.getResultList();
		if(assignments != null && assignments.size()>0){
			return assignments.get(0);
		}
		return null;
	}	

	@Override
	public List<SearchComplaintCustomer> getCustomerForComplaintByCriteria(SearchCriteria searchCriteria) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		String queryString="from Customer WHERE client.clientId = coalesce(:clientId, client.clientId) and lower(contactNo) = coalesce(:contactNo, contactNo) and lower(customerCode) = coalesce(:customerCode, customerCode)  and lower(emailId) = coalesce(:emailId, emailId) and  lower(customerName) LIKE :customerName";
		Query query= getEntityManager().createQuery(queryString);

		String customerName = StringUtils.isEmpty(searchCriteria.getCustomerName())?"":searchCriteria.getCustomerName().toLowerCase();
		String emailId = StringUtils.isEmpty(searchCriteria.getEmailId())?null:searchCriteria.getEmailId().toLowerCase();
		String contactNo = StringUtils.isEmpty(searchCriteria.getContactNo())?null:searchCriteria.getContactNo().toLowerCase();
		String customerCode = StringUtils.isEmpty(searchCriteria.getCustomerCode())?null:searchCriteria.getCustomerCode().toLowerCase();
		query.setParameter("customerName", "%"+customerName+"%");
		query.setParameter("contactNo", contactNo);
		query.setParameter("clientId", clientId);
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
	public List<SearchWorkOrder> getComplaintSearchByUnitId(Long unitId){

		List<SearchWorkOrder> searchWorkOrders = new ArrayList<SearchWorkOrder>();

		String queryString="FROM WorkOrder wo WHERE wo.unit.unitId = "+unitId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkOrder> workOrders= (List<WorkOrder>)query.getResultList();

		if(workOrders != null && workOrders.size()>0){

			for(WorkOrder workOrder : workOrders){
				SearchWorkOrder searchWorkOrder = new SearchWorkOrder();
				if(workOrder != null && workOrder.getWorkOrderAssignment()!=null){
					searchWorkOrder.setAssignTo(workOrder.getWorkOrderAssignment().getUser().getUserName());
				}
				searchWorkOrder.setWorkOrderId(workOrder.getWorkOrderId());
				searchWorkOrder.setPriority(workOrder.getPriority());

				//removing time stamp from date
				Date date = workOrder.getSlaDate();
				if(date!=null){
					DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
					String slaDate = outputFormatter.format(date);
					searchWorkOrder.setSlaDate(slaDate);
				}
				searchWorkOrder.setStatus(workOrder.getStatus());
				searchWorkOrder.setWorkOrderNo(workOrder.getWorkOrderNo());   
				searchWorkOrders.add(searchWorkOrder);
			}
		}
		return searchWorkOrders;
	}	

	@Override
	public List<SearchComplaintCustomer> getCustomerByWorkOrderNo(SearchCriteria searchCriteria) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
		String queryString="from CustomerComplaint WHERE client.clientId = coalesce(:clientId, client.clientId) and lower(workOrderNo) = coalesce(:workOrderNo, workOrderNo)";
		Query query= getEntityManager().createQuery(queryString);

		String workOrderNo = StringUtils.isEmpty(searchCriteria.getWorkOrderNo())?null:searchCriteria.getWorkOrderNo().toLowerCase();
		query.setParameter("workOrderNo", workOrderNo);
		query.setParameter("clientId", clientId);
		@SuppressWarnings("unchecked")
		WorkOrder workOrder = new WorkOrder();
		List<WorkOrder> result= (List<WorkOrder>)query.getResultList();
		List<SearchComplaintCustomer> complaintCustomers =  new ArrayList<SearchComplaintCustomer>();
		if(result != null && result.size()>0){
			workOrder = result.get(0);
		}

		Customer customer = customerManager.getCustomer(workOrder.getCustomerId());
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

				List<SearchWorkOrder> searchWorkOrders = new ArrayList<SearchWorkOrder>();
				List<WorkOrder> workOrderByUnitId = getAllComplaintsForUnit(unit.getUnitId());
				for(WorkOrder workOrderUnit : workOrderByUnitId){
					SearchWorkOrder complaint = new SearchWorkOrder();
					if(workOrderUnit != null && workOrderUnit.getWorkOrderAssignment()!=null){
						complaint.setAssignTo(workOrderUnit.getWorkOrderAssignment().getUser().getUserName());
					}
					complaint.setWorkOrderId(workOrderUnit.getWorkOrderId());
					complaint.setPriority(workOrderUnit.getPriority());
					complaint.setSlaDate(workOrderUnit.getSlaDateString());
					complaint.setStatus(workOrderUnit.getStatus());

					searchWorkOrders.add(complaint);
				}
				complaintUnit.setComplaints(searchWorkOrders); 
				complaintUnits.add(complaintUnit);

			}
			complaintCustomer.setUnits(complaintUnits);				
			complaintCustomers.add(complaintCustomer);
		}
		return complaintCustomers;
	}


	@Override
	public List<ComplaintSearchData> getComplaintBySLA(String code) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
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

		String  queryString="SELECT CD.CUSTOMER_ID,CD.CUSTOMER_CODE,UD.UNIT_ID,UD.UNIT_CODE,CC.WORK_ORDER_ID, CC.WORK_ORDER_NO, TIM.VALUE ,CC.STATUS,Case when cc.PRIORITY = 'C' then 'CRITICAL' when cc.PRIORITY = 'H' then 'HIGH' when cc.PRIORITY = 'M' then 'MEDIUM' else 'LOW' End PRIORITY,TU.USER_NAME"
				+" From TB_CUSTOMER_DETAIL CD left join TB_UNIT_DETAIL UD on UD.CUSTOMER_ID = CD.CUSTOMER_ID"
				+" left join TB_WORK_ORDER CC on CC.UNIT_ID = UD.UNIT_ID"
				+" left join TB_WORK_ORDER_ASSIGNMENT CA on CA.WORK_ORDER_ID = CC.WORK_ORDER_ID "
				+ " left join TB_ISSUE_MASTER TIM ON TIM.ISSUE_ID = CC.ISSUE_ID"
				+ " left join TB_USER TU ON TU.USER_ID = CA.USER_ID where cc.Work_order_type='Complaint' and cc.client_Id="+ clientId;

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
	public List<ComplaintSearchData> getComplaintByASSIGNMENT(String code) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
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

		String  queryString="SELECT CD.CUSTOMER_ID,CD.CUSTOMER_CODE,UD.UNIT_ID,UD.UNIT_CODE,CC.WORK_ORDER_ID, CC.WORK_ORDER_NO, TIM.VALUE ,CC.STATUS,Case when cc.PRIORITY = 'C' then 'CRITICAL' when cc.PRIORITY = 'H' then 'HIGH' when cc.PRIORITY = 'M' then 'MEDIUM' else 'LOW' End PRIORITY,TU.USER_NAME"
				+" From TB_CUSTOMER_DETAIL CD left join TB_UNIT_DETAIL UD on UD.CUSTOMER_ID = CD.CUSTOMER_ID"
				+" left join TB_WORK_ORDER CC on CC.UNIT_ID = UD.UNIT_ID"
				+" left join TB_WORK_ORDER_ASSIGNMENT CA on CA.WORK_ORDER_ID = CC.WORK_ORDER_ID"
				+ " left join TB_ISSUE_MASTER TIM ON TIM.ISSUE_ID = CC.ISSUE_ID"
				+ " left join TB_USER TU ON TU.USER_ID = CA.USER_ID where cc.Work_order_type='Complaint' and cc.client_Id="+ clientId;

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
	public List<ComplaintSearchData> getComplaintByPRIORITY(String code) {
		Long clientId = CommonUtil.getCurrentClient().getClientId();
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

		String  queryString="SELECT CD.CUSTOMER_ID,CD.CUSTOMER_CODE,UD.UNIT_ID,UD.UNIT_CODE,CC.WORK_ORDER_ID, CC.WORK_ORDER_NO, TIM.VALUE ,CC.STATUS,Case when cc.PRIORITY = 'C' then 'CRITICAL' when cc.PRIORITY = 'H' then 'HIGH' when cc.PRIORITY = 'M' then 'MEDIUM' else 'LOW' End PRIORITY,TU.USER_NAME"
				+" From TB_CUSTOMER_DETAIL CD left join TB_UNIT_DETAIL UD on UD.CUSTOMER_ID = CD.CUSTOMER_ID"
				+" left join TB_WORK_ORDER CC on CC.UNIT_ID = UD.UNIT_ID"
				+" left join TB_WORK_ORDER_ASSIGNMENT CA on CA.WORK_ORDER_ID = CC.WORK_ORDER_ID"
				+ " left join TB_ISSUE_MASTER TIM ON TIM.ISSUE_ID = CC.ISSUE_ID"
				+ " left join TB_USER TU ON TU.USER_ID = CA.USER_ID where cc.Work_order_type='Complaint' and cc.client_Id="+ clientId;

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
	public void saveWorkOrderEquipments(WorkOrderEquipment WorkOrderEquipment){

		if(WorkOrderEquipment.getWorkOrderEquipmentId()==null){
			getEntityManager().persist(WorkOrderEquipment);
		}
	}

	@Override
	public List<WorkOrderEquipment> getWorkOrderEquipments(Long workOrderId){

		String queryString="FROM WorkOrderEquipment woe where woe.workOrderId = " + workOrderId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkOrderEquipment> result= (List<WorkOrderEquipment>)query.getResultList();
		return result;
	}

	@Override
	public void createPmsComplaint(PmsWorkOrder pmsComplaint){
		if(pmsComplaint.getPmsWorkOrderId()==null){
			getEntityManager().persist(pmsComplaint);
		}
	}

	@Override
	public PmsWorkOrder getPmsComplaintByWorkitemId(Long workitemId){
		String queryString="FROM PmsWorkOrder pwo where pwo.workitemId = " + workitemId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<PmsWorkOrder> result= (List<PmsWorkOrder>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}

	@Override
	public PmsWorkOrder getPmsComplaintByWorkOrderId(Long workOrderId){
		String queryString="FROM PmsWorkOrder pwo where pwo.workOrderId = " + workOrderId;
		Query query=getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<PmsWorkOrder> result= (List<PmsWorkOrder>)query.getResultList();
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}

	@Override
	public List<CustomerUnitComplaint> getCustomerUnitComplaint(complaintSearchCriteria searchCriteria){
		String queryString = filterBasedQueryToGetComplaint(searchCriteria);
		Query query= getEntityManager().createNativeQuery(queryString);

		String customerName = StringUtilities.getDefaultOrBlankValueOfString(searchCriteria.getCustomerName());
		String emailId = StringUtilities.getDefaultOrBlankValueOfString(searchCriteria.getEmailId());
		String contactNo = StringUtilities.getDefaultOrBlankValueOfString(searchCriteria.getContactNo());
		String customerCode = StringUtilities.getDefaultOrBlankValueOfString(searchCriteria.getCustomerCode());
		String unitCode = StringUtilities.getDefaultOrBlankValueOfString(searchCriteria.getUnitCode());;
		String approvalStatus = StringUtilities.getDefaultOrBlankValueOfString(searchCriteria.getApprovalStatus());
		String assetNo = StringUtilities.getDefaultOrBlankValueOfString(searchCriteria.getAssetNo());
		String machineSerialNo = StringUtilities.getDefaultOrBlankValueOfString(searchCriteria.getMachineSerialNo());
		String modelNo = StringUtilities.getDefaultOrBlankValueOfString(searchCriteria.getModelNo());
		String serviceCategory = StringUtilities.getDefaultOrBlankValueOfString(searchCriteria.getServiceCategory());
		String workOrderNo = StringUtilities.getDefaultOrBlankValueOfString(searchCriteria.getWorkOrderNo());
		String priority = StringUtilities.getDefaultOrBlankValueOfString(searchCriteria.getPriority());
		String workOrderStatus = StringUtilities.getDefaultOrBlankValueOfString(searchCriteria.getWorkOrderStaus());;

		query.setParameter("CUSTOMER_NAME", customerName);
		query.setParameter("CONTACT_NO", contactNo);
		query.setParameter("CUSTOMER_CODE", customerCode);
		query.setParameter("EMAIL_ID", emailId);
		query.setParameter("UNIT_CODE", unitCode);
		query.setParameter("AGREEMENT_DURATION_ID", searchCriteria.getAgreementDurationId());
		query.setParameter("APPROVAL_STATUS", approvalStatus);
		query.setParameter("ASSET_NO", assetNo);
		query.setParameter("MACHINE_SERIAL_NO", machineSerialNo);
		query.setParameter("MODEL_NO", modelNo);
		query.setParameter("SERVICE_CATEGORY", serviceCategory);
		query.setParameter("APPROVED_BY", searchCriteria.getApprovedBy());
		query.setParameter("SERVICE_PROVIDER_ID", searchCriteria.getServiceProviderId());
		query.setParameter("UNIT_CATEGORY_ID", searchCriteria.getUnitCategoryId());
		query.setParameter("WORK_ORDER_NO", workOrderNo);
		query.setParameter("WORK_ORDER_STATUS", workOrderStatus);
		query.setParameter("PRIORITY", priority);

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		List<CustomerUnitComplaint> unitComplaints = putDataInCustomerUnitComplaintFromResultSet(results);

		return unitComplaints;
	} 

	private List<CustomerUnitComplaint> putDataInCustomerUnitComplaintFromResultSet( List<Object[]> results) {
		List<CustomerUnitComplaint> unitComplaints = new ArrayList<CustomerUnitComplaint>();
		for (Object[] result : results) {
			CustomerUnitComplaint unitComplaint = new CustomerUnitComplaint();

			Long customerId = (long) ((Number) result[0]).intValue();
			unitComplaint.setCustomerId(customerId);
			unitComplaint.setContactNo((String) result[1]);
			unitComplaint.setCustomerCode((String) result[2]);
			unitComplaint.setCustomerName((String) result[3]);
			unitComplaint.setEmailId((String) result[4]);
			unitComplaint.setCustomerType((String) result[5]);
			Long unitId = (long) ((Number) result[6]).intValue();
			unitComplaint.setUnitId(unitId);
			unitComplaint.setApprovalStatus((char) result[7]);
			unitComplaint.setAssetNo((String) result[8]);
			Long unitCustId = (long) ((Number) result[9]).intValue();
			unitComplaint.setUnitCustomerId(unitCustId);
			if(result[10]!=null){
				Date date =  (Date) result[10];
				String lastApprovedDate = DateUtil.convertDateToString(date);
				unitComplaint.setLastApprovedDate(lastApprovedDate);
			}

			unitComplaint.setLastApprovedBy((String) result[11]);
			unitComplaint.setMachineSerialNo((String) result[12]);
			unitComplaint.setModelNo((String) result[13]);
			unitComplaint.setUnitCode((String) result[14]);
			unitComplaint.setUnitCategory((String) result[15]);
			if(result[16]!=null){
				Long approvedBy = (long) ((Number) result[16]).intValue();
				unitComplaint.setApprovedBy(approvedBy);
			}
			Date contractExpiredOn =  (Date) result[17];
			String contractExpiredOnString = DateUtil.convertDateToString(contractExpiredOn);
			unitComplaint.setContractExpireOnString(contractExpiredOnString);
			Date contractStartOn =  (Date) result[18];
			String contractStartOnString = DateUtil.convertDateToString(contractStartOn);
			unitComplaint.setLastApprovedDate(contractStartOnString);
			unitComplaint.setServiceCategory((String) result[19]);
			Long srvcAgrmntUnitId = (long) ((Number) result[20]).intValue();
			unitComplaint.setSrvcAgrmntUnitId(srvcAgrmntUnitId);
			unitComplaint.setAgreementDuration((String) result[21]);
			unitComplaint.setServiceProvider((String) result[22]);
			Long workOrderId = (long) ((Number) result[23]).intValue();
			unitComplaint.setWorkOrderId(workOrderId);
			unitComplaint.setAlternateMobileNo((String) result[24]);
			unitComplaint.setWoContactNo((String) result[25]);
			unitComplaint.setContactPerson((String) result[26]);
			unitComplaint.setWoCustomerCode((String) result[27]);
			Long woCustId = (long) ((Number) result[28]).intValue();
			unitComplaint.setWoCustomerId(woCustId);
			unitComplaint.setWoCustomerName((String) result[29]);
			unitComplaint.setWoDescription((String) result[30]);
			unitComplaint.setWoEmailId((String) result[31]);
			unitComplaint.setWoLandLineNo((String) result[32]);
			unitComplaint.setPriority((String) result[33]);
			Date slaDate =  (Date) result[34];
			String slaDateString = DateUtil.convertDateToString(slaDate);
			unitComplaint.setSlaDateString(slaDateString);
			unitComplaint.setStatus((String) result[35]);
			unitComplaint.setWorkOrderNo((String) result[36]);
			unitComplaint.setWorkOrderType((String) result[37]);
			unitComplaint.setWoIssue((String) result[38]);
			Long woUnitId = (long) ((Number) result[39]).intValue();
			unitComplaint.setWoUnitId(woUnitId);

			unitComplaints.add(unitComplaint);
		}

		return unitComplaints;
	}

	private String filterBasedQueryToGetComplaint(complaintSearchCriteria searchCriteria) {
		String queryString="select CD.CUSTOMER_ID,CD.CONTACT_NO,CD.CUSTOMER_CODE, CD.CUSTOMER_NAME,CD.EMAIL_ID,"
				+ "CM.CUSTOMER_TYPE,UD.UNIT_ID,UD.APPROVAL_STATUS, UD.ASSET_NO,UD.CUSTOMER_ID as UNIT_CUST_ID, UD.LAST_APPROVAL_DATE,"
				+ "UD.LAST_APPROVED_BY, UD.MACHINE_SERIAL_NO, UD.MODEL_NO,UD.UNIT_CODE,UM.UNIT_TYPE,"
				+ "SA.APPROVED_BY, SA.CONTRACT_EXPIRE_ON, SA.CONTRACT_START_ON, SA.SERVICE_CATEGORY, SA.UNIT_ID AS SA_UNIT_ID,"
				+ "AD.DISPLAY_VALUE as AGREEMENT_DURATION, SPM.SERVICE_PROVIDER,WO.WORK_ORDER_ID,WO.ALTERNATE_MOBILE_NO,"
				+ "WO.CONTACT_NO AS WO_CONTACT_NO,WO.CONTACT_PERSON, WO.CUSTOMER_CODE AS WO_CUST_CODE, WO.CUSTOMER_ID AS WO_CUST_ID, WO.CUSTOMER_NAME AS WO_CUST_NAME, WO.DESCRIPTION,"
				+ "WO.EMAIL_ID AS WO_EMAIL_ID,WO.LANDLINE_NO,WO.PRIORITY, WO.SLA_DATE, WO.STATUS, WO.WORK_ORDER_NO,WO.WORK_ORDER_TYPE,"
				+ "ISM.VALUE as ISSUE, WO.UNIT_ID AS WO_UNIT_ID  FROM tb_customer_detail CD join tb_customer_type_master CM on CD.CUSTOMER_TYPE_ID = CM.CUSTOMER_TYPE_ID"
				+ " join tb_unit_detail UD on CD.CUSTOMER_ID = UD.CUSTOMER_ID join tb_unit_category_master UM on UD.UNIT_CATEGORY_ID = UM.UNIT_CATEGORY_ID "
				+ "join tb_service_agreement SA on UD.UNIT_ID = SA.UNIT_ID join tb_agreement_duration AD on AD.AGREEMENT_DURATION_ID = SA.AGREEMENT_DURATION_ID"
				+ " left join tb_work_order WO on UD.UNIT_ID = WO.UNIT_ID Join tb_issue_master ISM on WO.ISSUE_ID = ISM.ISSUE_ID "
				+ "join tb_service_provider_master SPM on SA.SERVICE_PROVIDER_ID = SPM.SERVICE_PROVIDER_ID "
				+ "where CD.CUSTOMER_CODE = COALESCE(:CUSTOMER_CODE, CD.CUSTOMER_CODE) AND CD.CUSTOMER_NAME = COALESCE(:CUSTOMER_NAME, CD.CUSTOMER_NAME) AND CD.EMAIL_ID = COALESCE(:EMAIL_ID, CD.EMAIL_ID) "
				+ "AND CD.CONTACT_NO = COALESCE(:CONTACT_NO, CD.CONTACT_NO) AND SA.AGREEMENT_DURATION_ID = COALESCE(:AGREEMENT_DURATION_ID, SA.AGREEMENT_DURATION_ID) "
				+ "AND UD.UNIT_CODE = COALESCE(:UNIT_CODE, UD.UNIT_CODE) AND UD.APPROVAL_STATUS = COALESCE(:APPROVAL_STATUS, UD.APPROVAL_STATUS) "
				+ "AND UD.ASSET_NO = COALESCE(:ASSET_NO, UD.ASSET_NO) AND UD.MACHINE_SERIAL_NO = COALESCE(:MACHINE_SERIAL_NO, UD.MACHINE_SERIAL_NO) "
				+ "AND UD.MODEL_NO = COALESCE(:MODEL_NO, UD.MODEL_NO) AND SA.SERVICE_CATEGORY = COALESCE( :SERVICE_CATEGORY, SA.SERVICE_CATEGORY) "
				+ "AND coalesce(SA.APPROVED_BY, '-1') = coalesce(:APPROVED_BY, coalesce(SA.APPROVED_BY, '-1')) AND SA.SERVICE_PROVIDER_ID = COALESCE(:SERVICE_PROVIDER_ID, SA.SERVICE_PROVIDER_ID) "
				+ "AND UD.UNIT_CATEGORY_ID = COALESCE( :UNIT_CATEGORY_ID, UD.UNIT_CATEGORY_ID) AND WO.WORK_ORDER_NO = COALESCE(:WORK_ORDER_NO, WO.WORK_ORDER_NO) "
				+ "AND WO.WORK_ORDER_TYPE = 'COMPLAINT' AND WO.STATUS = COALESCE(:WORK_ORDER_STATUS, WO.STATUS) AND COALESCE(WO.PRIORITY,'-1') = COALESCE(:PRIORITY,COALESCE(WO.PRIORITY, '-1'))";
		return queryString;
	}

	@Override
	public Map<Customer, List<Map<Unit, List<WorkOrder>>>> getComplaint(){
		complaintSearchCriteria searchCriteria = new complaintSearchCriteria();
		List<CustomerUnitComplaint> unitComplaints = getCustomerUnitComplaint(searchCriteria);
		Map<Customer, List<Map<Unit, List<WorkOrder>>>> customerUnitMap =  new HashMap<Customer, List<Map<Unit,List<WorkOrder>>>>();
		List<Map<Unit, List<WorkOrder>>> UnitMapList = new ArrayList<Map<Unit,List<WorkOrder>>>();
		Map<Unit, List<WorkOrder>> unitComplaintMap = new HashMap<Unit, List<WorkOrder>>();
		List<WorkOrder> complaints= new ArrayList<WorkOrder>(); 

		for(CustomerUnitComplaint unitComplaint : unitComplaints){

			Customer customer = new Customer();
			customer.setCustomerId(unitComplaint.getCustomerId());
			customer.setCustomerCode(unitComplaint.getCustomerCode());
			customer.setCustomerName(unitComplaint.getCustomerName());
			customer.setContactNo(unitComplaint.getContactNo());
			customer.setEmailId(unitComplaint.getEmailId());

			Unit unit = new Unit();
			unit.setApprovalStatus(unitComplaint.getApprovalStatus());
			unit.setAssetNo(unitComplaint.getAssetNo());
			unit.setCustomerId(unitComplaint.getUnitCustomerId());
			unit.setLastApprovedBy(unitComplaint.getLastApprovedBy());
			unit.setMachineSerialNo(unitComplaint.getMachineSerialNo());
			unit.setModelNo(unitComplaint.getModelNo());
			unit.getServiceAgreement().getAgreementDuration().setDisplayValue(unitComplaint.getAgreementDuration());
			unit.getServiceAgreement().setContractExpireOnString(unitComplaint.getContractExpireOnString());
			unit.getServiceAgreement().setContractStartOnString(unitComplaint.getContractStartOnString());
			unit.getServiceAgreement().setServiceCategory(unitComplaint.getServiceCategory());
			unit.getServiceAgreement().getServiceProvider().setServiceProvider(unitComplaint.getServiceProvider());
			unit.getServiceAgreement().setUnitId(unitComplaint.getSrvcAgrmntUnitId());
			unit.getServiceAgreement().setApprovedBy(unitComplaint.getApprovedBy());

			WorkOrder complaint = new WorkOrder();
			complaint.setAlternateMobileNo(unitComplaint.getAlternateMobileNo());
			complaint.setContactNo(unitComplaint.getWoContactNo());
			complaint.setContactPerson(unitComplaint.getContactPerson());
			complaint.setCustomerCode(unitComplaint.getWoCustomerCode());
			complaint.setCustomerId(unitComplaint.getWoCustomerId());
			complaint.setCustomerName(unitComplaint.getWoCustomerName());
			complaint.setDescription(unitComplaint.getWoDescription());
			complaint.setEmailId(unitComplaint.getWoEmailId());
			complaint.getIssue().setValue(unitComplaint.getWoIssue());
			complaint.setLandlineNo(unitComplaint.getWoLandLineNo());
			complaint.setPriority(unitComplaint.getPriority());
			complaint.setSlaDateString(unitComplaint.getSlaDateString());
			complaint.setStatus(unitComplaint.getStatus());
			complaint.getUnit().setUnitId(unitComplaint.getWoUnitId());
			complaint.setWorkOrderId(unitComplaint.getWorkOrderId());
			complaint.setWorkOrderNo(unitComplaint.getWorkOrderNo());
			complaint.setWorkOrderType(unitComplaint.getWorkOrderType());

			complaints.add(complaint);

			unitComplaintMap.put(unit, complaints);
			UnitMapList.add(unitComplaintMap);
			customerUnitMap.put(customer, UnitMapList);
		}

		return customerUnitMap;
	}	

}
