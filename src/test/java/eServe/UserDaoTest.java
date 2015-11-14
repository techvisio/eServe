package eServe;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.eserve.beans.SearchCriteria;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.db.UserDao;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config/Application-context.xml" })
public class UserDaoTest {

	@Autowired
	UserDao userDao;	

	@Test
	public void getUserBySearchCriteria(){
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setUserName("sgusain");;
		searchCriteria.setEmailId("ddd");
		User user = userDao.verifyUserNameAndEmialId(searchCriteria);
		System.out.println("Data Is : " + user);
	}
}
