package cn.study.shiro.chapter6.service;

import javax.management.relation.Role;
import javax.security.auth.login.LoginContext;

public interface RoleService {
	public Role createRole(Role role);
	public void deleteRole(Long roleId);
	public void correlationPermission(Long roleId,Long permissionId);
	public void uncorrelationPermission(Long roleId,Long permissionId);
}
