package cn.study.shiro.chapter6.service;

import java.util.Set;

import cn.study.shiro.chapter6.entity.User;
public interface UserService {
	public UserService createuser(User user);
	public void changePassword(Long userId,String password);
	public void correlationRoles(Long userId,Long...roleIds);
	public Set<String> findRoles(String username);
	public Set<String> findPermissions(String username);
	public void testUserDaoCall();
}
