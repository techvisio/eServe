package com.techvisio.eserve.db;

import java.util.List;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.AgreementDuration;
import com.techvisio.eserve.beans.Config;
import com.techvisio.eserve.beans.CustomerType;
import com.techvisio.eserve.beans.Department;
import com.techvisio.eserve.beans.Designation;
import com.techvisio.eserve.beans.InvoiceTaxes;
import com.techvisio.eserve.beans.Issue;
import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.QuestionMaster;
import com.techvisio.eserve.beans.Resolution;
import com.techvisio.eserve.beans.ServiceProvider;
import com.techvisio.eserve.beans.State;
import com.techvisio.eserve.beans.UnitCategory;

@Component
public interface CacheDao {

	public List<State> getState();
	public List<QuestionMaster> getQuestions();
	public List<Department> getDepartments();
	public List<Designation> getDesignations();
	List<State> getState(Long clientId);
	List<QuestionMaster> getQuestions(Long clientId);
	List<Department> getDepartments(Long clientId);
	List<Designation> getDesignations(Long clientId);
	List<Privilege> getPrivileges(Long clientId);
	List<Config> getDefalutValues(Long clientId);
	List<Config> getDefaultValues();
	List<Resolution> getResolution();
	List<Issue> getIssues();
	List<AgreementDuration> getAgreementDuration();
	List<UnitCategory> getUnitCategories();
	List<CustomerType> getCustomerTypes();
	List<ServiceProvider> getServiceProviders();
	AgreementDuration getAgreementDuration(Long durationId);
	Config getConfig(String property);
	List<InvoiceTaxes> getInvoiceTaxes(Long clientId);
}
