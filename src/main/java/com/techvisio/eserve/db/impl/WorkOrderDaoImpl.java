package com.techvisio.eserve.db.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.WorkOrderAssignment;
import com.techvisio.eserve.beans.WorkOrderEquipment;
import com.techvisio.eserve.beans.WorkOrderResolution;
import com.techvisio.eserve.beans.ComplaintSearchData;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.WorkOrder;
import com.techvisio.eserve.beans.PmsWorkOrder;
import com.techvisio.eserve.beans.SearchWorkOrder;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.db.WorkOrderDao;
import com.techvisio.eserve.factory.UniqueIdentifierGenerator;
import com.techvisio.eserve.manager.CustomerManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

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
	public List<WorkOrder> getWorkOrders(Long workOrderId) {
		String queryString="FROM WorkOrder wo WHERE wo.workOrderId = "+ workOrderId;
		Query query= getEntityManager().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<WorkOrder> workOrders= (List<WorkOrder>)query.getResultList();
		List<WorkOrder> clonedWorkOrders = new ArrayList<WorkOrder>(workOrders);
		return clonedWorkOrders;
	}

	@Override
	public List<WorkOrder> getAllComplaintsForUnit(Long unitId) {
		String queryString="FROM WorkOrder wo WHERE wo.unit.unitId = "+ unitId;
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
				+" left join TB_CUSTOMER_COMPLAINT CC on CC.UNIT_ID = UD.UNIT_ID"
				+" left join TB_COMPLAINT_ASSIGNMENT CA on CA.WORK_ORDER_ID = CC.WORK_ORDER_ID "
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
				+" left join TB_CUSTOMER_COMPLAINT CC on CC.UNIT_ID = UD.UNIT_ID"
				+" left join TB_COMPLAINT_ASSIGNMENT CA on CA.WORK_ORDER_ID = CC.WORK_ORDER_ID"
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
				+" left join TB_CUSTOMER_COMPLAINT CC on CC.UNIT_ID = UD.UNIT_ID"
				+" left join TB_COMPLAINT_ASSIGNMENT CA on CA.WORK_ORDER_ID = CC.WORK_ORDER_ID"
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
}
