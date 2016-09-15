package eServe;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.CommunicationJob;
import com.techvisio.eserve.db.CommunicationDao;
import com.techvisio.eserve.db.CustomerDao;
import com.techvisio.eserve.manager.CommunicationManager;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config/Application-context.xml" })
public class CustomerDaoTest {

	@Autowired
	CustomerDao dao;

	@Autowired
	CommunicationDao dao2;

		@Test
		public void getCustomer(){
	List<CommunicationJob> jobs = dao2.getAllNonProcessedMsg();

	System.out.println("data is:" + jobs);
		}


}