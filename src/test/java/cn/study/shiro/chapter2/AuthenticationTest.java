package cn.study.shiro.chapter2;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.LogoutAware;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class AuthenticationTest extends TestCase{
	private void login(String path){
		//获取SecurityManager的工厂
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(path);
		//通过工厂获取SecurityManager实例
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		//将SecurityManager实例绑定到SecurityUtils
		SecurityUtils.setSecurityManager(securityManager);
		//通过SecurityUtils获得Subject实例
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		currentUser.login(token);
	}
	
	private void Logout(Subject subject){
		subject.logout();
	}
	
	@Test
	public void testAllSuccessfulStrategyWithSuccess(){
		login("classpath:shiro/chapter2/shiro_authenticator_all_success.ini");
		Subject subject = SecurityUtils.getSubject();
		PrincipalCollection  principalCollection = subject.getPrincipals();
		Assert.assertEquals(2, principalCollection.asList().size());
		System.out.println(principalCollection.asList().size());
		Logout(subject);
	}
}
