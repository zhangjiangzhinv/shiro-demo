package cn.study.shiro.chapter2;

import org.apache.shiro.subject.Subject;
import org.junit.Test;

import cn.study.shiro.ShiroUtils;
import junit.framework.TestCase;

public class AutorizationTest extends TestCase {
	@Test
	public void testHasRole(){
		String config = "classpath:shiro/chapter2/shiro_role.ini";
		String username = "zhang";
		String password = "123";
		Subject currentUser = ShiroUtils.login(config, username, password);		
		ShiroUtils.logout(currentUser);
	}
}
