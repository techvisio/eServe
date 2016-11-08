package eServe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.CommunicationJob;
import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerUnitComplaint;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.beans.WorkOrder;
import com.techvisio.eserve.beans.complaintSearchCriteria;
import com.techvisio.eserve.db.CommunicationDao;
import com.techvisio.eserve.db.CustomerDao;
import com.techvisio.eserve.db.WorkOrderDao;
import com.techvisio.eserve.manager.CommunicationManager;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config/Application-context.xml" })
public class CustomerDaoTest {

	@Autowired
	CustomerDao dao;

	@Autowired
	CommunicationDao dao2;

	@Autowired
	WorkOrderDao dao3;

	@Test
	public void getCustomer(){
		
		Map<Customer, List<Map<Unit, List<WorkOrder>>>> complaints = dao3.getComplaint();
		System.out.println("data is:" + complaints);
	}


}