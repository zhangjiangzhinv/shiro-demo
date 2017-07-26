package cn.study.shiro.chapter2.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

public class MyRealm1 implements Realm {
	/**
	 * 根据Token获取认证信息
	 */
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		String username = (String)arg0.getPrincipal();
//		String password = (String)arg0.getCredentials();
		//必须这样写才能够得到正确的凭证，上面的写法是不成功的
		String password = new String((char[])arg0.getCredentials());
		if(!"zhang".equals(username)){
			throw new UnknownAccountException();
		}
		if(!"123".equals(password)){
			throw new IncorrectCredentialsException();
		}
		return new SimpleAuthenticationInfo(username,password,getName());
	}

	/**
	 * 返回一个唯一的Realm名字
	 */
	public String getName() {
		return "myRealm";
	}

	/**
	 * 判断此Realm是否支持此Token
	 */
	public boolean supports(AuthenticationToken arg0) {
		//仅支持UsernamePasswordToken
		return arg0 instanceof UsernamePasswordToken;
	}

}
