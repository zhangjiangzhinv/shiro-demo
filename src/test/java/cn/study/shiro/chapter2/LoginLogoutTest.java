package cn.study.shiro.chapter2;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Test;

public class LoginLogoutTest extends TestCase {
	@Test
	public void testHelloWorld(){
		//获取SecurityManager的工厂
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/chapter2/shiro_hard.ini");
		//通过工厂获取SecurityManager实例
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		//将SecurityManager实例绑定到SecurityUtils
		SecurityUtils.setSecurityManager(securityManager);
		//通过SecurityUtils获得Subject实例
		Subject subject = SecurityUtils.getSubject();
		//创建身份凭证Token
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		try{
			//身份验证，登录，该方法会自动委托给SecurityManager实例的login()方法
			//SecurityManager会继续委托给Authenticator进行登录验证
			//Authenticator会将token传递给Realm，从Realm中获取身份验证信息。可配置多个Realm，按照顺序进行验证
			subject.login(token);
		}catch(AuthenticationException e){
			//身份验证失败
			e.printStackTrace();
		}
		//断言身份验证成功，用户已经登录
		Assert.assertEquals(true, subject.isAuthenticated());
		System.out.println("testHelloWorld "+subject.isAuthenticated());
		//用户登出
		subject.logout();
	}
	@Test
	public void testCustomRealm(){
		//获取SecurityManager的工厂
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/chapter2/shiro_realm.ini");
		//通过工厂获取SecurityManager实例
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		//将SecurityManager实例绑定到SecurityUtils
		SecurityUtils.setSecurityManager(securityManager);
		//通过SecurityUtils获得Subject实例
		Subject subject = SecurityUtils.getSubject();
		//创建身份凭证Token
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		try{
			subject.login(token);
		}catch(AuthenticationException e){
			e.printStackTrace();
		}
		//断言身份验证成功，用户已经登录
		Assert.assertEquals(true, subject.isAuthenticated());
		System.out.println("testCustomRealm "+subject.isAuthenticated());
		//用户登出
		subject.logout();
	}
	@Test
	public void testJDBCRealm(){
		//生成SecurityManager工厂
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/chapter2/shiro_jdbc_realm.ini");
		//从SecurityManager工厂实例化SecurityManager对象
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		//将SecurityManager对象和SecurityUtils绑定
		SecurityUtils.setSecurityManager(securityManager);
		//通过SecurityUtils获得Subject实例
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		try{
			//subject的该方法会委托给securityManager处理
			//securityManager会将登录委托给Authenticator进行验证
			//Authenticator会将身份信息传递给Realm处理。进行身份验证成功后，会返回AuthenticationInfo信息，否则会抛出AuthenticationException异常
			subject.login(token);
		}catch(AuthenticationException e){
			e.printStackTrace();
		}
		Assert.assertEquals(true, subject.isAuthenticated());
		System.out.println("testJDBCRealm "+subject.isAuthenticated());
		subject.logout();
	}
	
	@After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
	}
}
