package com.techvisio.eserve.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.ComplaintAssignment;
import com.techvisio.eserve.beans.ComplaintEquipment;
import com.techvisio.eserve.beans.ComplaintResolution;
import com.techvisio.eserve.beans.ComplaintSearchData;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerComplaint;
import com.techvisio.eserve.beans.EquipmentDetail;
import com.techvisio.eserve.beans.SearchComplaint;
import com.techvisio.eserve.beans.SearchComplaintCustomer;
import com.techvisio.eserve.beans.SearchComplaintUnit;
import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.Unit;

@Component
@Transactional
public interface ComplaintService {

	public Long saveComplaint(CustomerComplaint customerComplaint);
	public CustomerComplaint getCustomerComplaint(Long complaintId);
	public Customer getCustomerBasicInfo(Long customerId);
	public Unit getUnitBasicInfo(Long unitId);
	public List<CustomerComplaint> getCustomerComplaints(Long customerId);
	public void saveComplaintResolution(Long complaintId, ComplaintResolution complaintResolution);
	public ComplaintResolution getComplaintResolution(Long complaintId);
	public void saveComplaintAssignment(Long complaintId, ComplaintAssignment complaintAssignment);
	public ComplaintAssignment getComplaintAssignment(Long complaintId);
	public List<SearchComplaintCustomer> getCustomerForComplaintByCriteria(
			SearchCriteria searchCriteria);
	public List<SearchComplaintUnit> getSearchUnitByCustomerId(Long customerId);
	public List<SearchComplaint> getComplaintSearchByUnitId(Long unitId);
	public List<CustomerComplaint> getAllComplaintsForUnit(Long unitId);
	public List<ComplaintSearchData> getComplaintDataforDashboard(String type,
			String code);
	public List<EquipmentDetail> getEquipmentDetail(String type, Long unitId);
	public void saveEquipment(List<EquipmentDetail> equipmentDetail, Long complaintId);
	public EquipmentDetail getEquipmentDetailByEquipmentId(Long equipDtlId);
	public void saveComplaintEquipments(ComplaintEquipment complaintEquipment);
	public Long saveUnit(Unit unit);
	public List<ComplaintEquipment> getComplaintEquipments(Long complaintId);
	public void deleteEquipmentDtlInclusion(List<EquipmentDetail> equipmentDetails,
			Long unitId, Long complaintId);
	public void createPmsWorkItem(Unit unit);
}
