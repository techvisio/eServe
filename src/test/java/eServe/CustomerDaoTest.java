package eServe;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.CustomerReport;
import com.techvisio.eserve.db.CustomerDao;
import com.techvisio.eserve.db.DashBordDao;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config/Application-context.xml" })
public class CustomerDaoTest {

	@Autowired
	CustomerDao dao;


	//	@Test
	//	public void getCustomer(){
	//
	//		Customer customer = dao.getCustomerBasicInfo(1L);
	//		System.out.println("Data is :"+customer);
	//	}

}