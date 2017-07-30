package cn.study.shiro.chapter6.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.study.shiro.chapter6.dao.UserDaoImpl;
import cn.study.shiro.chapter6.entity.Permission;
import cn.study.shiro.chapter6.entity.Role;
import cn.study.shiro.chapter6.entity.User;
import cn.study.shiro.helper.PasswordHelper;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDaoImpl userDao;
	@Autowired 
	PasswordHelper passwordHelper;
	
	public User createuser(User user) {
		//密码加密处理
		passwordHelper.encryptPassword(user);
		return userDao.createUser(user);
	}

	public void changePassword(Long userId, String password) {
		User user = userDao.findUser(userId);
		if(user != null){
			// 设置新密码
			user.setPassword(password);
			// 新密码加密处理
			passwordHelper.encryptPassword(user);
			// 更新用户信息
			userDao.updateUser(user);
		}
	}

	public void correlationRoles(Long userId, Long... roleIds) {
		userDao.correlationRoles(userId, roleIds);
	}
	
	public void uncorrelationRoles(Long userId, Long... roleIds) {
		userDao.uncorrelationRoles(userId, roleIds);
	}

	public Set<String> findRoles(String username) {
		List<Role> rList = userDao.findRoles(username);
		Set<String> strings = new HashSet<String>();
		for(int i = 0; i < rList.size(); i++){
			strings.add(rList.get(i).getRole());
		}
		return strings;
	}

	public Set<String> findPermissions(String username) {
		List<Permission> pList = userDao.findPermission(username);
		Set<String> strings = new HashSet<String>();
		for(int i = 0; i < pList.size(); i++){
			strings.add(pList.get(i).getPermission());
		}
		return strings;
	}
	
	public User findUser(String username){
		return null;
	}

}
