package cn.study.shiro.chapter6.dao;

import cn.study.shiro.chapter6.entity.User;

public interface UserDao {
	public void testUserDaoCall();
	public User createUser(User user);
	public int updateUser(User user);
	public int deleteUser(Long userId);
	public void correlationRoles(Long userId,Long...roleIds);
	public boolean exist(Long userId,Long roleId);
}
