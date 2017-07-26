package cn.study.shiro.chapter6.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import cn.study.shiro.chapter6.dao.UserDaoImpl;
import cn.study.shiro.chapter6.entity.User;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDaoImpl userDao;
	public UserService createuser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public void changePassword(Long userId, String password) {
		// TODO Auto-generated method stub

	}

	public void correlationRoles(Long userId, Long... roleIds) {
		// TODO Auto-generated method stub

	}

	public Set<String> findRoles(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> findPermissions(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public void testUserDaoCall() {
		userDao.testUserDaoCall();
	}

}
