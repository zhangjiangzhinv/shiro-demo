package cn.study.shiro.chapter6.service;

import java.security.acl.Permission;

public interface PermissionService {
	public Permission createPermission(Permission permission);
	public void deletePermission(Long permissionId);
}
