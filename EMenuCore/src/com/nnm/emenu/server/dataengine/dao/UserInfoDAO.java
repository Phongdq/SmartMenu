package com.nnm.emenu.server.dataengine.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.nnm.emenu.shared.UserInfo;

import common.jdbc.JdbcConnectionPool;
import common.jdbc.core.RowMapper;
import common.jdbc.core.simple.SimpleJdbcDaoSupport;

public class UserInfoDAO extends SimpleJdbcDaoSupport {
	public UserInfoDAO(JdbcConnectionPool pool) {
		super(pool);
	}

	private class UserInfoMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			UserInfo userInfo = new UserInfo();
			userInfo.setId(rs.getInt("id"));
			userInfo.setUsername(rs.getString("username"));
			userInfo.setPassword(rs.getString("password"));
			userInfo.setName(rs.getString("name"));
			userInfo.setRoleId(rs.getInt("roleid"));
			userInfo.setState(rs.getInt("state"));
			return userInfo;
		}
	}

	public List<UserInfo> getAll() {
		String query = "SELECT * FROM user_info";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new UserInfoMapper());
	}

	public void insertNewUser(String name, String username, String password,
			int roleId) {
		String query = "insert into user_info(username,password,name,roleid) values('"
				+ username
				+ "','"
				+ password
				+ "','"
				+ name
				+ "','"
				+ roleId
				+ "') ";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public List<UserInfo> getUser(String username, String password) {
		String query = "select * from user_info where username='" + username
				+ "' and password = '" + password + "'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new UserInfoMapper());
	}

	public List<UserInfo> getUser(String username, String password, int state) {
		String query = "select * from user_info where username='" + username
				+ "' and password = '" + password + "' and state = '" + state
				+ "'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new UserInfoMapper());
	}

	public List<UserInfo> getUser(String username) {
		String query = "select * from user_info where username='" + username
				+ "'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new UserInfoMapper());
	}

	public List<UserInfo> getUser(int id) {
		String query = "select * from user_info where id='" + id + "'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new UserInfoMapper());
	}

	public void updateUserInfo(String name, String username, String password,
			int roleId) {
		String query = "update `user_info` set `name` = '" + name
				+ "',`password` = '" + password + "',`roleid` = '" + roleId
				+ "' where username = '" + username + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void updateUserState(int id, int state) {
		String query = "update `user_info` set `state` = '" + state
				+ "' where id = '" + id + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void deleteUser(String username) {
		String query = "delete from user_info where `username` = '" + username
				+ "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}
}
