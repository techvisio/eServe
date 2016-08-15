package com.techvisio.eserve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techvisio.eserve.beans.WorkOrderAssignment;
import com.techvisio.eserve.beans.WorkOrderEquipment;
import com.techvisio.eserve.beans.WorkOrderResolution;
import com.techvisio.eserve.beans.ComplaintSearchData;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.WorkOrder;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.Response;
import com.techvisio.eserve.beans.SearchWorkOrder;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.service.WorkOrderService;

@RestController
@RequestMapping("service/complaint")
public class WorkOrderController {

	@Autowired
	WorkOrderService workOrderService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Response> saveWorkOrder(@RequestBody WorkOrder workOrder) {
		Long workOrderId = workOrderService.saveWorkOrder(workOrder);
		WorkOrder workOrderFromDB = workOrderService.getWorkOrder(workOrderId);
		Response response=new Response();
		response.setResponseBody(workOrderFromDB);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Response> updateWorkOrder(@RequestBody WorkOrder workOrder) {
		Response response=new Response();
		workOrderService.saveWorkOrder(workOrder);
		WorkOrder workOrderFromDB = workOrderService.getWorkOrder(workOrder.getWorkOrderId());
		response.setResponseBody(workOrderFromDB);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}	

	@RequestMapping(value="customercomplaint/{customerId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getCustomerForComplaint(@PathVariable Long customerId) {  
		Response response=new Response();
		Customer customer = workOrderService.getCustomerBasicInfo(customerId);
		response.setResponseBody(customer);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="unitcomplaint/{unitId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getUnitForComplaint(@PathVariable Long unitId) {  
		Response response=new Response();
		Unit unit = workOrderService.getUnitBasicInfo(unitId);
		response.setResponseBody(unit);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="complaints/{customerId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getAllCustomerComplaints(@PathVariable Long customerId) {  
		Response response=new Response();
		List<WorkOrder> complaints = workOrderService.getWorkOrders(customerId);
		response.setResponseBody(complaints);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value="/{workOrderId}",method = RequestMethod.GET)
	public ResponseEntity<Response> getWorkOrder(@PathVariable Long workOrderId) {  
		Response response=new Response();
		WorkOrder workOrder = workOrderService.getWorkOrder(workOrderId);
		response.setResponseBody(workOrder);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/complaintresolution/{workOrderId}", method = RequestMethod.POST)
	public ResponseEntity<Response> saveWorkOrderResolution(@PathVariable Long workOrderId, @RequestBody WorkOrderResolution workOrderResolution) {
		Response response=new Response();
		workOrderService.saveWorkOrderResolution(workOrderId, workOrderResolution);
		WorkOrder workOrderFromDB = workOrderService.getWorkOrder(workOrderId);
		response.setResponseBody(workOrderFromDB);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/complaintassignment/{workOrderId}", method = RequestMethod.POST)
	public ResponseEntity<Response> saveWorkOrderAssignment(@PathVariable Long workOrderId, @RequestBody WorkOrderAssignment workOrderAssignment) {
		Response response=new Response();
		workOrderService.saveWorkOrderAssignment(workOrderId, workOrderAssignment);
		WorkOrder workOrderFromDB = workOrderService.getWorkOrder(workOrderId);
		response.setResponseBody(workOrderFromDB);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/searchcomplaintcustomer/", method = RequestMethod.POST)
	public ResponseEntity<Response> getCustomerComplaintByCriteria(@RequestBody SearchCriteria searchCriteria) {
		Response response=new Response();
		List<SearchComplaintCustomer> complaintByCriteria = workOrderService.getCustomerForComplaintByCriteria(searchCriteria);
		response.setResponseBody(complaintByCriteria);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/searchcomplaintunit/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getSearchUnitByCustomerId(@PathVariable Long customerId) {
		Response response=new Response();
		List<SearchComplaintUnit> searchComplaintUnits = workOrderService.getSearchUnitByCustomerId(customerId);
		response.setResponseBody(searchComplaintUnits);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/searchcomplaint/{unitId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getComplaintSearchByUnitId(@PathVariable Long unitId) {
		Response response=new Response();
		List<SearchWorkOrder> searchComplaints = workOrderService.getComplaintSearchByUnitId(unitId);
		response.setResponseBody(searchComplaints);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/complaint/{unitId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getAllComplaintsForUnit(@PathVariable Long unitId) {
		Response response=new Response();
		List<WorkOrder> complaints = workOrderService.getAllComplaintsForUnit(unitId);
		response.setResponseBody(complaints);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/dashboard/complaintdata/{type}/{code}", method = RequestMethod.GET)
	public ResponseEntity<Response> getComplaintDataforDashboard(@PathVariable String type,@PathVariable String code) {
		Response response=new Response();
		List<ComplaintSearchData> complaints = workOrderService.getComplaintDataforDashboard(type,code);
		response.setResponseBody(complaints);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/equipment/{type}/{unitId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getEquipmentDetail(@PathVariable String type,@PathVariable Long unitId) {
		Response response=new Response();
		List<EquipmentDetail> equipmentDetails = workOrderService.getEquipmentDetail(type, unitId);
		response.setResponseBody(equipmentDetails);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@RequestMapping(value ="/equipment/{complaintId}",method = RequestMethod.POST)
	public ResponseEntity<Response> saveEquipment(@RequestBody List<EquipmentDetail> equipmentDetail, @PathVariable Long complaintId) {
		workOrderService.saveEquipment(equipmentDetail,complaintId);
		List<WorkOrderEquipment> equipmentDetails = workOrderService.getWorkOrderEquipments(complaintId);
		Response response=new Response();
		response.setResponseBody(equipmentDetails);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value ="/complaintequipment/{workOrderId}", method = RequestMethod.GET)
	public ResponseEntity<Response> getWorkOrderEquipments(@PathVariable Long workOrderId) {
		Response response=new Response();
		List<WorkOrderEquipment> workOrderEquipments = workOrderService.getWorkOrderEquipments(workOrderId);
		response.setResponseBody(workOrderEquipments);

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value ="/deleteequipment/{unitId}/{complaintId}",method = RequestMethod.POST)
	public ResponseEntity<Response> deleteEquipments(@RequestBody List<EquipmentDetail> equipmentDetails, @PathVariable Long unitId, @PathVariable Long complaintId) {
		workOrderService.deleteEquipmentDtlInclusion(equipmentDetails, unitId, complaintId);
		List<WorkOrderEquipment> complaintEquipments = workOrderService.getWorkOrderEquipments(complaintId);
		Response response=new Response();
		response.setResponseBody(complaintEquipments);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value ="/pmscomplaint/{workitemId}",method = RequestMethod.POST)
	public ResponseEntity<Response> createWorkOrderByPms(@RequestBody UnitBasicInfo unitBasicInfo, @PathVariable Long workitemId) {
		WorkOrder customerComplaint = workOrderService.createWorkOrderByPms(workitemId, unitBasicInfo);
		Response response=new Response();
		response.setResponseBody(customerComplaint);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
