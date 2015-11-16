package com.nnm.emenu.client;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.nnm.emenu.shared.CountFoodOrder;
import com.nnm.emenu.shared.FoodCategory;
import com.nnm.emenu.shared.FoodInfo;
import com.nnm.emenu.shared.Order;
import com.nnm.emenu.shared.OrderData;
import com.nnm.emenu.shared.OrderDetail;
import com.nnm.emenu.shared.SumMoney;
import com.nnm.emenu.shared.UserInfo;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void login(String username, String password,
			AsyncCallback<UserInfo> callback);

	void uploadImage(String data, AsyncCallback<String> callback);

	void downloadImage(String key, AsyncCallback<String> callback);

	void savenewFood(FoodInfo food, AsyncCallback<String> callback);

	void getFood(int key, AsyncCallback<List<FoodInfo>> callback);

	void getUserInfo(String username, AsyncCallback<UserInfo> callback);

	void getFoodCategory(int parent_id,
			AsyncCallback<List<FoodCategory>> callback);

	void order(int table, int userId, int foodId, int amount, int money,
			String time, AsyncCallback<OrderDetail> callback);

	void cancelOrderDetail(int table, int userId, long order_food_Id,
			AsyncCallback<Boolean> callback);

	void getDataVersion(AsyncCallback<Long> callback);

	void refresh(AsyncCallback<ArrayList<OrderData>> callback);

	void changeStateOrder(int table, long id, int state, int user_id,
			AsyncCallback<Integer> callback);

	void payment(long order_id, int user_id, int table, double money_total,
			double money_discount, float vat, double money_payment,
			double money_customer, double money_return, String gendate,
			AsyncCallback<Boolean> callback);

	void logout(int id, AsyncCallback<Void> callback);

	void saveNewFood(int user_id, String title, String code, String des,
			int price, String unit, int category_id, String title_category,
			int state, String gendate, AsyncCallback<FoodInfo> callback);

	void saveNewFoodCategory(int user_id, String title, String description,
			String url, int parent_id, String gendate,
			AsyncCallback<FoodCategory> callback);

	void search(int user_id, String text, AsyncCallback<List<FoodInfo>> callback);

	void getSumMoney(int user_id, int typeFilter, String time_start,
			String time_end, AsyncCallback<List<SumMoney>> callback);

	void getCountFoodOrder(int user_id, int typeFilter, String time_start,
			String time_end, int max,
			AsyncCallback<List<CountFoodOrder>> callback);

	void getTotalRevenueInfo(int user_id, String time_start, String time_end,
			AsyncCallback<Map<String, Long>> callback);

	void getListUser(AsyncCallback<List<UserInfo>> callback);

	void registerUser(String name, String username, String password,
			int roleId, AsyncCallback<Integer> callback);

	void updateUser(int user_id, String name, String username, String password,
			int roleId, AsyncCallback<Integer> callback);

	void deleteUser(int user_id, String username,
			AsyncCallback<Integer> callback);

	void updateFood(int user_id, String code, String title, String des,
			String price, String unit, String category_id, String gendate,
			AsyncCallback<FoodInfo> callback);

	void deleteFood(int user_id, String code, AsyncCallback<Integer> callback);

	void updateFoodCategory(int user_id, int id, String title,
			String description, int parent_id, String gendate,
			AsyncCallback<FoodCategory> callback);

	void deleteFoodCategory(int user_id, int id, AsyncCallback<Integer> callback);

	void getFoodCategoryById(int id, AsyncCallback<FoodCategory> callback);

	void cancelOrder(int table, int user_id, AsyncCallback<Integer> callback);

	void changeTable(long order_id, int table, int user_id,
			AsyncCallback<Integer> callback);

	void changeStateOrderByFoodId(int table, long order_id, int food_id,
			int state, int user_id, AsyncCallback<Integer> callback);

	void changeAmountOrderDetail(int table, long id, int amount, int user_id,
			AsyncCallback<Integer> callback);

	void createOrder(int table, String time, int user_id,
			AsyncCallback<Order> callback);

}
