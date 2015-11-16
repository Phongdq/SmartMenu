package com.nnm.emenu.server.dataengine;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.nnm.emenu.server.dataengine.dao.BillDAO;
import com.nnm.emenu.server.dataengine.dao.FoodCategoryDAO;
import com.nnm.emenu.server.dataengine.dao.FoodInfoDAO;
import com.nnm.emenu.server.dataengine.dao.OrderDAO;
import com.nnm.emenu.server.dataengine.dao.OrderDetailDAO;
import com.nnm.emenu.server.dataengine.dao.UserInfoDAO;
import com.nnm.emenu.shared.Bill;
import com.nnm.emenu.shared.CountFoodOrder;
import com.nnm.emenu.shared.FoodCategory;
import com.nnm.emenu.shared.FoodInfo;
import com.nnm.emenu.shared.Order;
import com.nnm.emenu.shared.OrderDetail;
import com.nnm.emenu.shared.SumMoney;
import com.nnm.emenu.shared.UserInfo;
import com.sun.org.glassfish.external.amx.AMX;

import common.jdbc.JdbcConnectionPool;
import common.jdbc.JdbcParameter;

public class DataEngine implements Serializable, IsSerializable {
	private static DataEngine INSTANCE;
	// config
	private String dbAddr;
	private String dbPort;
	private String dbUser;
	private String dbPass;
	private String dbName;
	private int maxConn;
	private int clearPeriod;

	private UserInfoDAO userInfoDAO;
	private FoodInfoDAO foodInfoDAO;
	private FoodCategoryDAO foodCategoryDAO;
	private OrderDAO orderDAO;
	private OrderDetailDAO orderDetailDAO;
	private BillDAO billDAO;

	/*---------Object Fields-------------*/
	private JdbcConnectionPool pool;
	private JdbcParameter parameter;

	public static DataEngine getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DataEngine();
		}
		return INSTANCE;
	}

	public DataEngine() {

	}

	public void start(String dbAddr, String dbPort, String dbUser,
			String dbPass, String dbName, int maxConn, int clearPeriod,
			String pathLog) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.dbAddr = dbAddr;
			this.dbPort = dbPort;
			this.dbUser = dbUser;
			this.dbPass = dbPass;
			this.dbName = dbName;
			this.maxConn = maxConn;
			this.clearPeriod = clearPeriod;

			this.parameter = new JdbcParameter();
			this.parameter.setUrl("jdbc:mysql://" + dbAddr + ":" + dbPort + "/"
					+ dbName);
			System.out.println("URL : " + this.parameter.getUrl());
			this.parameter.setUsername(dbUser);
			this.parameter.setPassword(dbPass);
			this.parameter.setMaxConn(maxConn);
			this.parameter.setClearPeriod(clearPeriod);

			this.pool = new JdbcConnectionPool(parameter);
			// -------Khoi tao DAO-------------------------
			initDao();
			// -------Khoi tao bo ghi file log--------------
			// logger = new Logger(pathLog);
			System.out.println("Connected to Database...");
			System.out.println("Database Host : " + dbAddr);
			System.out.println("Database Port : " + dbPort);
			System.out.println("Database User : " + dbUser);
			System.out.println("Database Name  : " + dbName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void initDao() {
		this.userInfoDAO = new UserInfoDAO(pool);
		this.foodInfoDAO = new FoodInfoDAO(pool);
		this.foodCategoryDAO = new FoodCategoryDAO(pool);
		this.orderDAO = new OrderDAO(pool);
		this.orderDetailDAO = new OrderDetailDAO(pool);
		this.billDAO = new BillDAO(pool);
	}

	// ------------------------------UserInfo------------------------------//

	public List<UserInfo> getListUser() {
		return userInfoDAO.getAll();
	}

	public List<UserInfo> getUser(String username, String password) {
		return userInfoDAO.getUser(username, password);
	}

	public List<UserInfo> getUser(String username, String password, int state) {
		return userInfoDAO.getUser(username, password, state);
	}

	public List<UserInfo> getUser(String username) {
		return userInfoDAO.getUser(username);
	}

	public List<UserInfo> getUser(int id) {
		return userInfoDAO.getUser(id);
	}

	public void insetNewUser(String name, String username, String password,
			int roleId) {
		userInfoDAO.insertNewUser(name, username, password, roleId);
	}

	public void updateUserInfo(String name, String username, String password,
			int roleId) {
		userInfoDAO.updateUserInfo(name, username, password, roleId);
	}

	public void updateUserState(int id, int state) {
		userInfoDAO.updateUserState(id, state);
	}

	public void deleteUser(String username) {
		userInfoDAO.deleteUser(username);
	}

	// ---------------------------food----------------------------------------//
	public List<FoodInfo> getFoods() {
		return foodInfoDAO.getFoods();
	}

	public List<FoodInfo> getFoods(int category_id) {
		return foodInfoDAO.getFoods(category_id);
	}

	public List<FoodInfo> getFoods(String code) {
		return foodInfoDAO.getFoods(code);
	}

	public List<FoodInfo> searchFood(String text) {
		return foodInfoDAO.searchFood(text);
	}

	public void insertNewFood(FoodInfo food) {
		foodInfoDAO.inserFood(food.getTitle(), food.getCode(),
				food.getDescription(), food.getPrice(), food.getUnit(),
				food.getCategory_id(), food.getCategory_title(),
				food.getImage(), food.getState(), food.getGendate());
	}

	public void insertNewFood(String title, String code, String des, int price,
			String unit, int category_id, String category_title, String image,
			int state, String gendate) {
		foodInfoDAO.inserFood(title, code, des, price, unit, category_id,
				category_title, image, state, gendate);
	}

	public int getCurrentFoodInfoId() {
		return foodInfoDAO.getCurrentId();
	}

	public void updateImageFood(String code, String image) {
		foodInfoDAO.updateImageFood(code, image);
	}

	public void updateFoodInfo(String code, String title, String description,
			String price, String unit, String category_id, String gendate) {
		foodInfoDAO.updateFoodInfo(code, title, description, price, unit,
				category_id, gendate);
	}

	public void updateCategoryFoodInFo(int category_id, int parent_id,
			String parent_title) {
		foodInfoDAO
				.updateCategoryFoodInfo(category_id, parent_id, parent_title);

	}

	public void deleteFoodInfo(String code) {
		foodInfoDAO.deleteFoodInfo(code);
	}

	// ----------------------------food category-----------------------------//
	public List<FoodCategory> getFoodCategory() {
		return foodCategoryDAO.getAll();
	}

	public List<FoodCategory> getFoodCategory(int parent_id) {
		return foodCategoryDAO.getFoodCategory(parent_id);
	}

	public List<FoodCategory> getFoodCategoryById(int id) {
		return foodCategoryDAO.getFoodCategoryById(id);
	}

	public List<FoodCategory> getFoodCategory(String gendate) {
		return foodCategoryDAO.getFoodCategory(gendate);
	}

	public void insertNewFoodCategory(String title, String description,
			String url, int parent_id, String gendate) {
		foodCategoryDAO.inserFoodCategory(title, description, url, parent_id,
				gendate);
	}

	public void updateFoodCategoryUrl(String gendate, String url) {
		foodCategoryDAO.updateFoodCategoryUrl(gendate, url);
	}

	public void updateFoodCategory(int id, String title, String description,
			int parent_id, String gendate) {
		foodCategoryDAO.updateFoodCategory(id, title, description, parent_id,
				gendate);
	}

	public void deleteFoodCategory(int id) {
		foodCategoryDAO.deleteFoodCategory(id);
	}

	// -----------------------------order-------------------------------------//
	public void insertNewOrder(int table, int money, String time) {
		orderDAO.inserNewOrder(table, money, time);
	}

	public List<Order> getOrder(int id) {
		return orderDAO.getOrder(id);
	}

	public List<Order> getOrder(int table, String time) {
		return orderDAO.getOrder(table, time);
	}

	public List<Order> getLastOrderByTable(int table) {
		return orderDAO.getLastOrderByTable(table);
	}

	public List<Order> getLastOrderToday() {
		return orderDAO.getLastOrderToday();
	}

	public void updateOrderMoney(long id, int money) {
		orderDAO.updateOrderMoney(id, money);
	}

	public void updateOrderState(long id, int state) {
		orderDAO.updateOrderState(id, state);
	}

	public void updateOrderTable(long id, int table) {
		orderDAO.updateOrderTable(id, table);
	}

	public void deleteOrder(long id) {
		orderDAO.deleteOrder(id);
	}

	public long getCurrentOrderId() {
		return orderDAO.getCurrentId();
	}

	// -----------------------------order detail-----------------------------//
	public void insertNewOrderDetail(long order_id, int table, int user_id,
			int food_id, int amount, int money, String time) {
		orderDetailDAO.insertNewOrderDetail(order_id, table, user_id, food_id,
				amount, money, time);
	}

	public List<OrderDetail> getOrderDetail(long id) {
		return orderDetailDAO.getOrderDetail(id);
	}

	public List<OrderDetail> getOrderDetail(long order_id, String time) {
		return orderDetailDAO.getOrderDetail(order_id, time);
	}

	public void updateStateOrderDetail(long id, int state) {
		orderDetailDAO.updateStateOrderDetail(id, state);
	}
	
	public void updateAmountOrderDetail(long id, int amount) {
		orderDetailDAO.updateAmountOrderDetail(id, amount);
	}

	public void updateStateOrderDetailByFoodId(long order_id,int food_id, int state) {
		orderDetailDAO.updateStateOrderDetailByFoodId(order_id,food_id, state);
	}

	public void updateStateOrderDetailByOrder(long order_id, int state) {
		orderDetailDAO.updateStateOrderDetailByOrder(order_id, state);
	}

	public void updateTableOrderDetail(long order_id, int table) {
		orderDetailDAO.updateTableOrderDetail(order_id, table);
	}

	public long getCurrentOrderDetailId() {
		return orderDetailDAO.getCurrentId();
	}

	public void removeOrderDetail(long id, int user_id) {
		orderDetailDAO.removeOrderDetail(id, user_id);
	}

	public void removeOrderDetail(long order_id) {
		orderDetailDAO.removeOrderDetail(order_id);
	}

	public List<CountFoodOrder> getCountFoodOrderBest(String time_start,
			String time_end, int max) {
		return orderDetailDAO.getCountFoodOrderBest(time_start, time_end, max);
	}

	public List<CountFoodOrder> getCountFoodOrderLeast(String time_start,
			String time_end, int max) {
		return orderDetailDAO.getCountFoodOrderLeast(time_start, time_end, max);
	}

	// --------------------------------bill-------------------------------------//
	public void insertNewBill(long order_id, int user_id, int table,
			double money_total, double money_discount, float vat,
			double money_payment, double money_customer, double money_return,
			String gendate) {
		this.billDAO.insertNewBill(order_id, user_id, table, money_total,
				money_discount, vat, money_payment, money_customer,
				money_return, gendate);
	}

	public Object getTotalMoneyRevenue(String time_start, String time_end) {
		return billDAO.getTotalMoney(time_start, time_end);
	}

	public List<SumMoney> getSumMoney(int typeFilter, String time_start,
			String time_end) {
		return billDAO.getBill(typeFilter, time_start, time_end);
	}

}
