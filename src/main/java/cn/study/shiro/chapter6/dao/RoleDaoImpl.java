package cn.study.shiro.chapter6.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import cn.study.shiro.chapter6.entity.Role;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public Role createRole(final Role role) {
		final String sql = "insert into sys_roles(role,description,available) values(?,?,?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {		
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pStatement = con.prepareStatement(sql,new String[]{"id"});
				pStatement.setString(1, role.getRole());
				pStatement.setString(2, role.getDescription());
				pStatement.setBoolean(3, role.getAvailable());
				return pStatement;
			}
		}, keyHolder);
		role.setId(keyHolder.getKey().longValue());
		return role;
	}

	public void deleteRole(Long roleId) {
		String sql = "delete from sys_roles where id = ?";
		jdbcTemplate.update(sql,roleId);
	}

	public void correlationPermission(Long roleId, Long... permissionIds) {
		if(permissionIds == null || permissionIds.length == 0) return;
		String sql = "insert into sys_roles_permissions(role_id,permission_id) values(?,?)";
		for(Long permissionId : permissionIds){
			if(!exist(roleId,permissionId)){
				jdbcTemplate.update(sql, roleId,permissionId);		
			}
		}
	}

	public void uncorrelationPermission(Long roleId, Long... permissionIds) {
		if(permissionIds == null || permissionIds.length == 0) return;
		String sql = "delete from sys_roles_permissions where role_id = ? and permission_id = ?";
		for(Long permissionId : permissionIds){
			if(exist(roleId,permissionId)){
				jdbcTemplate.update(sql, roleId,permissionId);		
			}		}
	}
	
	public boolean exist(Long roleId, Long permissionId){
		String sql = "count(*) from sys_roles_permissions where role_id = ? and permission_id = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class,roleId, permissionId) != 0;		
	}

}
