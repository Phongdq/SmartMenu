package com.nnm.emenu.client;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.nnm.emenu.shared.CountFoodOrder;
import com.nnm.emenu.shared.FoodCategory;
import com.nnm.emenu.shared.FoodInfo;
import com.nnm.emenu.shared.Order;
import com.nnm.emenu.shared.OrderData;
import com.nnm.emenu.shared.OrderDetail;
import com.nnm.emenu.shared.SumMoney;
import com.nnm.emenu.shared.UserInfo;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {

	// user
	int registerUser(String name, String username, String password, int roleId);

	int updateUser(int user_id, String name, String username, String password,
			int roleId);

	int deleteUser(int user_id, String username);

	List<UserInfo> getListUser();

	UserInfo login(String username, String password);

	void logout(int id);

	UserInfo getUserInfo(String username);

	String uploadImage(String data);

	String downloadImage(String key);

	// food
	String savenewFood(FoodInfo food);

	FoodInfo saveNewFood(int user_id, String title, String code, String des,
			int price, String unit, int category_id, String title_category,
			int state, String gendate);

	FoodInfo updateFood(int user_id, String code, String title, String des,
			String price, String unit, String category_id, String gendate);

	int deleteFood(int user_id, String code);

	List<FoodInfo> getFood(int key);

	// food category
	FoodCategory getFoodCategoryById(int id);

	FoodCategory saveNewFoodCategory(int user_id, String title,
			String description, String url, int parent_id, String gendate);

	List<FoodCategory> getFoodCategory(int parent_id);

	FoodCategory updateFoodCategory(int user_id, int id, String title,
			String description, int parent_id, String gendate);

	int deleteFoodCategory(int user_id, int id);

	
	// order
	Order createOrder(int table,String time,int user_id);
	
	OrderDetail order(int table, int userId, int foodId, int amount, int money,
			String time);

	int changeStateOrder(int table, long id, int state,int user_id);
	
	int changeAmountOrderDetail(int table,long id,int amount,int user_id);
	
	int changeStateOrderByFoodId(int table,long order_id,int food_id,int state,int user_id);
	
	int changeTable(long order_id,int table,int user_id);

	int cancelOrder(int table, int user_id);

	boolean cancelOrderDetail(int table, int userId, long order_food_Id);

	ArrayList<OrderData> refresh();

	long getDataVersion();

	// payment
	boolean payment(long order_id, int user_id, int table, double money_total,
			double money_discount, float vat, double money_payment,
			double money_customer, double money_return, String gendate);

	// search
	List<FoodInfo> search(int user_id, String text);

	// get sumMoney
	List<SumMoney> getSumMoney(int user_id, int typeFilter, String time_start,
			String time_end);

	// get countfoodorder
	List<CountFoodOrder> getCountFoodOrder(int user_id, int typeFilter,
			String time_start, String time_end, int max);

	// get InfoTotalRevenue
	Map<String, Long> getTotalRevenueInfo(int user_id, String time_start,
			String time_end);

}
