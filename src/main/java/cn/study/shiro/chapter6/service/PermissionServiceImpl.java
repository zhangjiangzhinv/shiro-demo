package cn.study.shiro.chapter6.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.study.shiro.chapter6.dao.PermissionDao;
import cn.study.shiro.chapter6.entity.Permission;
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private PermissionDao permissionDao;
	
	public Permission createPermission(Permission permission) {
		return permissionDao.createPermission(permission);
	}

	public void deletePermission(Long permissionId) {
		permissionDao.deletePermission(permissionId);
	}
	
}
