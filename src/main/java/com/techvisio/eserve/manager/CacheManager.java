package com.techvisio.eserve.manager;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.AgreementDuration;
import com.techvisio.eserve.beans.Department;
import com.techvisio.eserve.beans.Designation;
import com.techvisio.eserve.beans.InvoiceTaxes;
import com.techvisio.eserve.beans.Issue;
import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.QuestionMaster;
import com.techvisio.eserve.beans.Resolution;
import com.techvisio.eserve.beans.State;

@Component
public interface CacheManager {


	void refreshCacheList(String entity);
	public <T> List<T> getEntityList(String entity);


	public State getStateId(Long id);
	public QuestionMaster getQuestionByQuestion(String question);
	public Department getDepartmentById(Long departmentId);
	public Designation getDesignationByQuestion(Long designationId);
	List<Privilege> getPrivileges(Long clientId);
	Resolution getResolutionMasters(Long resolutionId);
	Issue getIssue(Long issueId);
	public Map<Long, Map<String, Object>> getConfigMap();
	List<InvoiceTaxes> getInvoiceTaxes(Long clientId);
}
