/**
 * 
 */
package com.nnm.emenu.server.dataengine.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.nnm.emenu.server.dataengine.dao.OrderDAO.CurrentIdMapper;
import com.nnm.emenu.server.utils.DateTime;
import com.nnm.emenu.shared.CountFoodOrder;
import com.nnm.emenu.shared.OrderDetail;

import common.jdbc.JdbcConnectionPool;
import common.jdbc.core.RowMapper;
import common.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * @author bizluvsakura
 *
 */
public class OrderDetailDAO extends SimpleJdbcDaoSupport {
	public OrderDetailDAO(JdbcConnectionPool pool) {
		// TODO Auto-generated constructor stub
		super(pool);
	}

	private class OrderDetailMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			OrderDetail order = new OrderDetail();
			order.setId(rs.getLong("id"));
			order.setOrder_id(rs.getLong("order_id"));
			order.setTable(rs.getInt("table"));
			order.setUser_id(rs.getInt("user_id"));
			order.setFood_id(rs.getInt("food_id"));
			order.setAmount(rs.getInt("amount"));
			order.setMoney(rs.getInt("money"));
			order.setTime(rs.getString("time"));
			order.setState(rs.getInt("state"));
			return order;
		}

	}

	public void insertNewOrderDetail(long order_id, int table, int user_id,
			int food_id, int amount, int money, String time) {
		String query = "insert into order_detail(`order_id`,`table`,`user_id`,`food_id`,`amount`,`money`,`time`) values('"
				+ order_id
				+ "','"
				+ table
				+ "','"
				+ user_id
				+ "','"
				+ food_id
				+ "','" + amount + "','" + money + "','" + time + "')";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public List<OrderDetail> getOrderDetail(long order_id) {
		String query = "select * from order_detail where order_id='" + order_id
				+ "'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new OrderDetailMapper());
	}

	public List<OrderDetail> getOrderDetail(long order_id, String time) {
		String query = "select * from order_detail where order_id='" + order_id
				+ "' and time = '" + time + "'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new OrderDetailMapper());
	}

	public void updateStateOrderDetail(long id, int state) {
		String query = "update `order_detail` set `state` = '" + state
				+ "' where id = '" + id + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void updateAmountOrderDetail(long id, int amount) {
		String query = "update `order_detail` set `amount` = '" + amount
				+ "' where id = '" + id + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void updateStateOrderDetailByFoodId(long order_id, int food_id,
			int state) {
		String query = "update `order_detail` set `state` = '" + state
				+ "' where food_id = '" + food_id + "' and order_id = '"
				+ order_id + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void updateStateOrderDetailByOrder(long order_id, int state) {
		String query = "update `order_detail` set `state` = '" + state
				+ "' where order_id = '" + order_id + "' and `state` < '"
				+ state + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void updateTableOrderDetail(long order_id, int table) {
		String query = "update `order_detail` set `table` = '" + table
				+ "' where order_id = '" + order_id + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void removeOrderDetail(long id, int user_id) {
		String query = "delete from `order_detail` where id = '" + id + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void removeOrderDetail(long order_id) {
		String query = "delete from `order_detail` where id = '" + order_id
				+ "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public long getCurrentId() {
		String query = "select max(id) from `order_detail`";
		System.out.println("QUERY : " + query);
		return (long) getSimpleJdbcTemplate().query(query,
				new CurrentIdMapper()).get(0);
	}

	public List<CountFoodOrder> getCountFoodOrderBest(String time_start,
			String time_end, int max) {
		String query = "select `food_id`,sum(`amount`) from order_detail where time >= '"
				+ time_start
				+ "' and time <= '"
				+ time_end
				+ " 23:59:59' and `state` < '3' group by `food_id` order by sum(`amount`) desc limit "
				+ max;
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new CountFoodOrderMapper());
	}

	public List<CountFoodOrder> getCountFoodOrderLeast(String time_start,
			String time_end, int max) {
		String query = "select `food_id`,sum(`amount`) from order_detail where time >= '"
				+ time_start
				+ "' and time <= '"
				+ time_end
				+ " 23:59:59' group by `food_id` order by sum(`amount`) asc limit "
				+ max;
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new CountFoodOrderMapper());
	}

	class CurrentIdMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			long currentId = rs.getLong("max(id)");
			return currentId;
		}

	}

	class CountFoodOrderMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			CountFoodOrder obj = new CountFoodOrder();
			obj.setFood_id(rs.getInt("food_id"));
			obj.setCount(rs.getInt("sum(`amount`)"));
			return obj;
		}

	}
}
