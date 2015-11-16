package com.nnm.emenu.server.dataengine.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.nnm.emenu.shared.FoodCategory;

import common.jdbc.JdbcConnectionPool;
import common.jdbc.core.RowMapper;
import common.jdbc.core.simple.SimpleJdbcDaoSupport;

public class FoodCategoryDAO extends SimpleJdbcDaoSupport {
	public FoodCategoryDAO(JdbcConnectionPool pool) {
		super(pool);
	}

	private class FoodCategoryMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			FoodCategory foodCategory = new FoodCategory();
			foodCategory.setId(rs.getInt("id"));
			foodCategory.setTitle(rs.getString("title"));
			foodCategory.setDescription(rs.getString("description"));
			foodCategory.setUrl(rs.getString("url"));
			foodCategory.setParent_id(rs.getInt("parent_id"));
			foodCategory.setGendate(rs.getString("gendate"));
			return foodCategory;
		}
	}

	public void inserFoodCategory(int id, String title, String url,
			int parent_id) {
		String query = "insert into `food_category`(`id`,`title`,`description`,`url`,`parent_id`) values('"
				+ id
				+ "','"
				+ title
				+ "','"
				+ title
				+ "','"
				+ url
				+ "','"
				+ parent_id + "')";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void inserFoodCategory(int id, String title, String description,
			String url, int parent_id) {
		String query = "insert into `food_category`(`id`,`title`,`description`,`url`,`parent_id`) values('"
				+ id
				+ "','"
				+ title
				+ "','"
				+ description
				+ "','"
				+ url
				+ "','" + parent_id + "')";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void inserFoodCategory(String title, String description, String url,
			int parent_id, String gendate) {
		String query = "insert into `food_category`(`title`,`description`,`url`,`parent_id`,`gendate`) values('"
				+ title
				+ "','"
				+ description
				+ "','"
				+ url
				+ "','"
				+ parent_id
				+ "','" + gendate + "')";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public List<FoodCategory> getAll() {
		String query = "Select * from food_category";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new FoodCategoryMapper());
	}

	public List<FoodCategory> getFoodCategory(int parent_id) {
		String query = "Select * from food_category where parent_id = '"
				+ parent_id + "'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new FoodCategoryMapper());
	}

	public List<FoodCategory> getFoodCategoryById(int id) {
		String query = "Select * from `food_category` where `id` = '" + id
				+ "'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new FoodCategoryMapper());
	}

	public List<FoodCategory> getFoodCategory(String gendate) {
		String query = "Select * from food_category where gendate = '"
				+ gendate + "'";
		System.out.println("QUERY : " + query);
		return getSimpleJdbcTemplate().query(query, new FoodCategoryMapper());
	}

	public void updateFoodCategoryUrl(String gendate, String url) {
		String query = "update `food_category` set `url` = '" + url
				+ "' where gendate = '" + gendate + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void updateFoodCategory(int id, String title, String description,
			int parent_id, String gendate) {
		String query = "update `food_category` set `title` = '" + title
				+ "',`description` = '" + description + "',`parent_id` = '"
				+ parent_id + "',`gendate` = '" + gendate + "' where `id` = '"
				+ id + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}

	public void deleteFoodCategory(int id) {
		String query = "delete from `food_category` where `id` = '" + id + "'";
		System.out.println("QUERY : " + query);
		getSimpleJdbcTemplate().update(query);
	}
}
