package eServe;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.db.CustomerDao;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config/Application-context.xml" })
public class CustomerDaoTest {

	@Autowired
	CustomerDao dao;	

	@Test
	public void getCustomer(){

		Customer customer = dao.getCustomerBasicInfo(1L);
		System.out.println("Data is :"+customer);
	}
	
	@Test
	public void getUnit(){

		Unit unit = dao.getUnitBasicInfo(1L);
		System.out.println("Data is :"+unit);
	}

}
