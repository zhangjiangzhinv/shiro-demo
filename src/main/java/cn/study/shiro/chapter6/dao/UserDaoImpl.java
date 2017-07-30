package cn.study.shiro.chapter6.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ExpressionState;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import cn.study.shiro.chapter6.entity.Permission;
import cn.study.shiro.chapter6.entity.Role;
import cn.study.shiro.chapter6.entity.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;	
	
	public User createUser(final  User user) {
        final String sql = "insert into sys_users(username, password, salt, locked) values(?,?,?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[] { "id" });
                psst.setString(1, user.getUsername());
                psst.setString(2, user.getPassword());
                psst.setString(3, user.getSalt());
                psst.setBoolean(4, user.getLocked());
                return psst;
            }
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
	}

	public User findUser(final Long userId) {
		final String sql = "select id,username,password,salt,locked from sys_users where id=?";
		List<User> uList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<User>(User.class),userId);
		if(uList.size() == 0){
			return null;
		}else{
			return uList.get(0);			
		}
	}

	public int updateUser(final User user) {
		String sql = "update sys_users set username=?,password=?,salt=?,locked=? where id=?";
		return jdbcTemplate.update(sql,user.getUsername(),user.getPassword(),user.getSalt(),user.getLocked());
	}

	public void correlationRoles(Long userId, Long... roleIds) {
	    if(roleIds == null || roleIds.length == 0) {
	        return;
	    }
	    String sql = "insert into sys_users_roles(user_id,role_id) values(?,?)";
	    for(Long roleId : roleIds){
	    	if(!exist(userId,roleId))
	    	jdbcTemplate.update(sql,userId,roleId);
	    }	    
	}
	

	public void uncorrelationRoles(Long userId, Long... roleIds) {
	   if(roleIds == null || roleIds.length == 0) {
	        return;
	    }
	    String sql = "delete from sys_users_roles where user_id = ? and role_id = ?";
	    for(Long roleId : roleIds){
	    	if(exist(userId,roleId))
	    	jdbcTemplate.update(sql,userId,roleId);
	    }	
	}

	public int deleteUser(Long userId) {
		String sql = "delete from sys_users where id=?";
		return jdbcTemplate.update(sql,userId);
	}

	public boolean exist(Long userId, Long roleId) {
        String sql = "select count(1) from sys_users_roles where user_id=? and role_id=?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId, roleId) != 0;
	}

	public List<Role> findRoles(String username) {
		String sql = "select id,role,description,available from sys_roles where id in "
				+ " (select t.role_id from sys_users_roles t where t.user_id = "
				+ "	(select u.id from sys_users u where u.username  = ?))";
		return jdbcTemplate.queryForList(sql, Role.class, username);
	}

	public List<Permission> findPermission(String username) {
		String sql = "select pe.id,pe.permission,pe.description,pe.available "
				+ " from sys_users u,sys_users_roles r,sys_roles_permissions p,sys_permissions pe "
				+ " where u.id=r.user_id and r.role_id=p.role_id and p.permission_id and pe.id and u.username=?";
		return jdbcTemplate.queryForList(sql, Permission.class,username);
	}
	
	

}
