package com.techvisio.eserve.manager.impl;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.CommunicationBusinessObj;
import com.techvisio.eserve.beans.CommunicationJob;
import com.techvisio.eserve.beans.ClientConfig;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.EmailFormat;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.UnitBasicCustomer;
import com.techvisio.eserve.db.CommunicationDao;
import com.techvisio.eserve.manager.CommunicationManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;
@Component
public class CommunicationManagerImpl implements CommunicationManager{

	@Autowired
	CommunicationDao communicationDao;

	@Autowired
	ClientConfiguration configPreferences;

	@Override
	public CommunicationBusinessObj getBusinessObjectByCommunicationJob(CommunicationJob communicationJobs){

		CommunicationBusinessObj communicationBusinessObj = new CommunicationBusinessObj();
		if(communicationJobs.getEntityType().equalsIgnoreCase("UNIT") && communicationJobs.getEventType().equalsIgnoreCase("APPROVAL")){
			UnitBasicCustomer businessObject= getBusinessObject(communicationJobs.getEntityId());
			communicationBusinessObj.setBusinessObject(businessObject);
			List<String> recipients = new ArrayList<String>();
			recipients.add(businessObject.getEmailId());
			communicationBusinessObj.setRecipients(recipients);
		}
		return communicationBusinessObj;
	}

	private UnitBasicCustomer getBusinessObject(Long unitId){

		Unit unit = communicationDao.getUnit(unitId);
		Customer customer = communicationDao.getCustomer(unit.getCustomerId());

		UnitBasicCustomer unitDtl = new UnitBasicCustomer();

		unitDtl.setUnit(unit);
		unitDtl.setCustomerCode(customer.getCustomerCode());
		unitDtl.setCustomerName(customer.getCustomerName());
		unitDtl.setContactNo(customer.getContactNo());
		unitDtl.setEmailId(customer.getEmailId());

		return unitDtl;
	}

	@Override
	public List<String> getWhiteListedRecipietents(List<String> recipitents){
		List<String> filteredRecipietents=new ArrayList<String>(recipitents);
		ClientConfig environment = configPreferences.getConfigObject(AppConstants.ENVIRONMENT);
		if(!(AppConstants.processEnvironment.LIVE.name().equals(environment.getValue()))){

			ClientConfig whiteListedEmail  = configPreferences.getConfigObject(AppConstants.WHITE_LISTED_EMAIL_ID);
			List<String> whiteListedEmailIds = CommonUtil.stringToStringArray(whiteListedEmail.getValue());

			Iterator<String> it = filteredRecipietents.iterator();
			while (it.hasNext() ){
				String recepietent = it.next();
				if (!(whiteListedEmailIds.contains(recepietent))){
					it.remove();
				}
			}
		}

		return filteredRecipietents;
	}

	@Override
	public void updateCommunicationJobStatus(CommunicationJob communicationJob){
		communicationJob.setStatus(AppConstants.PROCESSED);
		communicationDao.saveCommunicationJos(communicationJob);
	}

	@Override
	public EmailFormat getParsedCommunicationTemplate(EmailFormat emailFormat, CommunicationBusinessObj businessObj) throws ParseException{
		RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();
		StringReader reader = new StringReader(emailFormat.getFormat());
		SimpleNode node = runtimeServices.parse(reader, "Template");
		Template template = new Template();
		template.setRuntimeServices(runtimeServices);
		template.setData(node);
		template.initDocument();
		Map params=new HashMap();
		params.put("bo", businessObj.getBusinessObject());
		VelocityContext context=new VelocityContext(params);
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		emailFormat.setParsedTemplate(writer.toString());
		return emailFormat;
	}

 //	public static void main(String[] args) {
//		CommunicationManagerImpl manager=new CommunicationManagerImpl();
//		try {
//			manager.getParsedCommunicationTemplate();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Override
	public Long saveCommunicationJos(CommunicationJob communicationJob) {
		Long jobId = communicationDao.saveCommunicationJos(communicationJob);
		return jobId;
	}

	@Override
	public CommunicationJob getCommunicationJob(Long jobId) {
		CommunicationJob communicationJobs = communicationDao.getCommunicationJob(jobId);
		return communicationJobs;
	}

	@Override
	public EmailFormat getEmailTemplateByCommunicationJob(CommunicationJob communicationJob) {
		EmailFormat emailFormat = communicationDao.getEmailTemplateByCommunicationJob(communicationJob);
		return emailFormat;
	}

	@Override
	public List<CommunicationJob> getAllNonProcessedMsg() {
		List<CommunicationJob> communicationJobs = communicationDao.getAllNonProcessedMsg();
		return communicationJobs;
	}
}
