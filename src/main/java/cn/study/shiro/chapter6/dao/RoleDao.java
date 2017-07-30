package cn.study.shiro.chapter6.dao;

import cn.study.shiro.chapter6.entity.Role;

public interface RoleDao {
	public Role createRole(Role role);
	public void deleteRole(Long roleId);
	public void correlationPermission(Long roleId,Long... permissionIds);
	public void uncorrelationPermission(Long roleId,Long... permissionIds);
}
