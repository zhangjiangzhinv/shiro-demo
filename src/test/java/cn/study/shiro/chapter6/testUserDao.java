package cn.study.shiro.chapter6;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.study.shiro.chapter6.service.UserServiceImpl;
import junit.framework.TestCase;

public class testUserDao extends TestCase {

	@Test
	public void testUserDaoCall(){
		String currentPath = testUserDao.class.getResource("/").getPath();
		String WEBINFPath = currentPath.substring(0,currentPath.indexOf("test-classes"));
		String configPath = WEBINFPath+"study/WEB-INF/applicationContext.xml";
		System.out.println(currentPath+"/n"+WEBINFPath+"/n"+configPath);
		ApplicationContext context = new ClassPathXmlApplicationContext(configPath);
		UserServiceImpl userServiceImpl  = (UserServiceImpl)context.getBean("userService");
		userServiceImpl.testUserDaoCall();
	}

}
