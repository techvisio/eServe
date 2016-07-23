package eServe;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.UnitBasicInfo;
import com.techvisio.eserve.db.CustomerDao;
import com.techvisio.eserve.manager.CacheManager;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config/Application-context.xml" })
public class CustomerDaoTest {

	@Autowired
	CustomerDao dao;

	@Autowired
	CacheManager cacheManager;

	//	@Test
	//	public void getCustomer(){
	//
	//		Customer customer = dao.getCustomerBasicInfo(1L);
	//		System.out.println("Data is :"+customer);
	//	}


	@Test
	public void getCustomer(){

		
	UnitBasicInfo basicInfo = dao.getUnitBasicInfo(1L);
System.out.println("data is : " + basicInfo);
	}
}