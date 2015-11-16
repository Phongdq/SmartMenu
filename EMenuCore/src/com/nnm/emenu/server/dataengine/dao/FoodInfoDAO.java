package com.nnm.emenu.server.dataengine.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.nnm.emenu.shared.FoodInfo;
import common.jdbc.JdbcConnectionPool;
import common.jdbc.core.RowMapper;
import common.jdbc.core.simple.SimpleJdbcDaoSupport;

public class FoodInfoDAO extends SimpleJdbcDaoSupport {
	public FoodInfoDAO(JdbcConnectionPool pool) {
		super(pool);
	}

	private class FoodInfoMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			FoodInfo foodInfo = new FoodInfo();
			foodInfo.setId(rs.getInt("id"));
			foodInfo.setTitle(rs.getString("title"));
			foodInfo.setCode(rs.getString("code"));
			foodInfo.setDescription(rs.getString("description"));
			foodInfo.setPrice(rs.getInt("price"));
			foodInfo.setUnit(rs.getString("unit"));
			foodInfo.setCategory_id(rs.getInt("category_id"));
			foodInfo.setCategory_title(rs.getString("category_title"));
			foodInfo.setImage(rs.getString("image"));
			foodInfo.setState(rs.getInt("state"));
			foodInfo.setGendate(rs.getString("gendate"));
			return foodInfo;
		}
	}

	public void inserFood(String title, String code, String des, int price,
			String unit, int category_id, String category_title, String image,
			int state, String gendate) {
		String query = "insert into `food_info`(`title`,`code`,`description`,`price`,`unit`,`category_id`,`category_title`,`image`,`state`,`gendate`) values('"
				+ title
				+ "','"
				+ code
				+ "','"
				+ des
				+ "','"
				+ price
				+ "','"
				+ unit
				+ "','"
				+ category_id
				+ "','"
				+ category_title
				+ "','"
				+ image + "','" + state + "','" + gendate + "')";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public List<FoodInfo> getFoods() {
		String query = "select * from food_info";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new FoodInfoMapper());
	}

	public List<FoodInfo> getFoods(int category_id) {
		String query = "select * from food_info where category_id = '"
				+ category_id + "'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new FoodInfoMapper());
	}

	public List<FoodInfo> getFoods(String code) {
		String query = "select * from food_info where code = '" + code + "'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new FoodInfoMapper());
	}

	public List<FoodInfo> searchFood(String text) {
		String query = "select * from `food_info` where title like '%" + text
				+ "%' or title like '%" + text.replaceAll(" ", "") + "%'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new FoodInfoMapper());
	}

	public void updateImageFood(String code, String image) {
		String query = "update `food_info` set `image` = '" + image
				+ "' where code = '" + code + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void updateFoodInfo(String code, String title, String description,
			String price, String unit, String category_id, String gendate) {
		String query = "update `food_info` set `title` = '" + title
				+ "', `description` = '" + description + "', `price` = '"
				+ price + "', `unit` = '" + unit + "', `category_id` = '"
				+ category_id + "', `gendate` = '" + gendate
				+ "' where code = '" + code + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void updateCategoryFoodInfo(int category_id, int parent_id,
			String parent_title) {
		String query = "update `food_info` set `category_id` = '" + parent_id
				+ "',`category_title` = '" + parent_title
				+ "' where `category_id` = '" + category_id + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void deleteFoodInfo(String code) {
		String query = "delete from food_info where `code` = '" + code + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public int getCurrentId() {
		String query = "select max(id) from `food_info`";
		System.out.println("QUERY : " + query);
		return (int) getSimpleJdbcTemplate()
				.query(query, new CurrentIdMapper()).get(0);
	}

	class CurrentIdMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			int currentId = rs.getInt("max(id)");
			return currentId;
		}

	}
}
