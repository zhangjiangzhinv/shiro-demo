package cn.study.shiro.chapter6.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ExpressionState;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

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


	public int deleteUser(Long userId) {
		String sql = "delete from sys_users where id=?";
		return jdbcTemplate.update(sql,userId);
	}


	public boolean exist(Long userId, Long roleId) {
        String sql = "select count(1) from sys_users_roles where user_id=? and role_id=?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId, roleId) != 0;
	}

	public void testUserDaoCall() {
		System.out.println("testUserDaoCall");
	}

}
