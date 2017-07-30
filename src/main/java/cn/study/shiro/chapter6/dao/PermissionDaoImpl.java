package cn.study.shiro.chapter6.dao;

import cn.study.shiro.chapter6.entity.Permission;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository("permissionDao")
public class PermissionDaoImpl implements PermissionDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Permission createPermission(final Permission permission) {
		final String sql = "insert into sys_permissions(permission,description,available) values(?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {	
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pStatement = con.prepareStatement(sql,new String[] { "id" });
				pStatement.setString(1, permission.getPermission());
				pStatement.setString(2, permission.getDescription());
				pStatement.setBoolean(3, permission.getAvailable());				
				return pStatement;
			}
		},keyHolder);
		permission.setId(keyHolder.getKey().longValue());
		return permission;
	}

	public void deletePermission(Long permissionId) {
		String sql = "delete from sys_permissions where id = ?";
		jdbcTemplate.update(sql,permissionId);
	}

}
