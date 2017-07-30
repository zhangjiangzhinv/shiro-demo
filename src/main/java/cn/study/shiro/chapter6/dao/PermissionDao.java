package cn.study.shiro.chapter6.dao;

import cn.study.shiro.chapter6.entity.Permission;

public interface PermissionDao {
	public Permission createPermission(Permission permission);
	public void deletePermission(Long permissionId);
}
