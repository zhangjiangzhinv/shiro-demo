package cn.study.shiro.chapter6.dao;

import java.util.List;

import cn.study.shiro.chapter6.entity.Permission;
import cn.study.shiro.chapter6.entity.Role;
import cn.study.shiro.chapter6.entity.User;

public interface UserDao {
	public User createUser(User user);
	public User findUser(Long userId);
	public int updateUser(User user);
	public int deleteUser(Long userId);
	public void correlationRoles(Long userId,Long...roleIds);
	public void uncorrelationRoles(Long userId,Long...roleIds);
	public List<Role> findRoles(String username);
	public List<Permission> findPermission(String username);
}
