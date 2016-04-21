package eServe;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.beans.CustomerReportAttribute;
import com.techvisio.eserve.beans.GraphData;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.db.ComplaintDao;
import com.techvisio.eserve.db.CustomerDao;
import com.techvisio.eserve.db.DashBordDao;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config/Application-context.xml" })
public class CustomerDaoTest {

	@Autowired
	CustomerDao dao;
	
	@Autowired
	ComplaintDao comp;

	@Autowired
	DashBordDao bordDao;


	//	@Test
	//	public void getCustomer(){
	//
	//		Customer customer = dao.getCustomerBasicInfo(1L);
	//		System.out.println("Data is :"+customer);
	//	}

	@Test
	public void getUnit(){
		CustomerReportAttribute customerReportAttribute = new CustomerReportAttribute();
		customerReportAttribute.setApprovalStatus("P");
		List<CustomerReport> customerReports = dao.getCustomerReportByCriteria(customerReportAttribute);
		System.out.println("Data is :"+customerReports);
	}

	@Test
	public void getCustomers(){
		Unit customers = comp.getUnitBasicInfo(1L);
		System.out.println("Data is :"+customers);
	}
}
