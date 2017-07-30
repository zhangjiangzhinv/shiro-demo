package cn.study.shiro.chapter6.service;

import cn.study.shiro.chapter6.entity.Permission;

public interface PermissionService {
	public Permission createPermission(Permission permission);
	public void deletePermission(Long permissionId);
}
