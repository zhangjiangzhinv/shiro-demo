package cn.study.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroUtils {
	public static Subject login(String config,String username,String password){
		//加载SecurityManager工厂
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(config);
		//从工厂获得SecurityManager实例
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		//将SecurityManager实例与SecurityUtils绑定
		SecurityUtils.setSecurityManager(securityManager);
		//从SecurityUtils获得当前Subject
		Subject currentUser =  SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		try{
			currentUser.login(token);
			System.out.println("角色判断成功");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("角色判断失败");
		}
		return currentUser;
	}
	public static void logout(Subject subject){
		subject.logout();
	}
}
