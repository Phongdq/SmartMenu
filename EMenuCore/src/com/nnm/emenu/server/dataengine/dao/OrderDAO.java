/**
 * 
 */
package com.nnm.emenu.server.dataengine.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.nnm.emenu.server.utils.DateTime;
import com.nnm.emenu.shared.Order;

import common.jdbc.JdbcConnectionPool;
import common.jdbc.core.RowMapper;
import common.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * @author bizluvsakura
 *
 */
public class OrderDAO extends SimpleJdbcDaoSupport {
	public OrderDAO(JdbcConnectionPool pool) {
		super(pool);
	}

	private class OrderMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			Order order = new Order();
			order.setId(rs.getLong("id"));
			order.setTable(rs.getInt("table"));
			order.setMoney(rs.getInt("money"));
			order.setTime(rs.getString("time"));
			return order;
		}
	}

	public void inserNewOrder(int table, int money, String time) {
		String query = "insert into `order`(`table`,`money`,`time`) values('"
				+ table + "','" + money + "','" + time + "')";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public List<Order> getOrder(int id) {
		String query = "select * from order where id='" + id + "'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new OrderMapper());
	}

	public List<Order> getOrder(int table, String time) {
		String query = "select * from `order` where `table` = '" + table
				+ "' and time='" + time + "'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new OrderMapper());
	}

	public List<Order> getOrderByTable(int table) {
		String query = "select * from `order` where `table`='" + table + "'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new OrderMapper());
	}

	public List<Order> getLastOrderByTable(int table) {
		String query = "select * from `order` where `table`='" + table
				+ "' and time > '" + DateTime.getCurrentDate(DateTime.FORMAT)
				+ " 00:00:00' and time < '"
				+ DateTime.getCurrentDate(DateTime.FORMAT) + " 23:59:59'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new OrderMapper());
	}

	public List<Order> getLastOrderToday() {
		String query = "select * from `order` where time > '"
				+ DateTime.getCurrentDate(DateTime.FORMAT)
				+ " 00:00:00' and time < '"
				+ DateTime.getCurrentDate(DateTime.FORMAT)
				+ " 23:59:59' and state = '0'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new OrderMapper());
	}

	public void updateOrderMoney(long id, int money) {
		String query = "update `order` set `money` = '" + money
				+ "' where id = '" + id + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void updateOrderState(long id, int state) {
		String query = "update `order` set `state` = '" + state
				+ "' where id = '" + id + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void updateOrderTable(long id, int table) {
		String query = "update `order` set `table` = '" + table
				+ "' where id = '" + id + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void deleteOrder(long id) {
		String query = "delete from `order` where id = '" + id + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public long getCurrentId() {
		String query = "select max(id) from `order`";
		System.out.println("QUERY : " + query);
		return (long) getSimpleJdbcTemplate().query(query,
				new CurrentIdMapper()).get(0);
	}

	class CurrentIdMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			long currentId = rs.getLong("max(id)");
			return currentId;
		}

	}
}
