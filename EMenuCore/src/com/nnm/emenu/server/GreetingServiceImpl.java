package com.nnm.emenu.server;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.Data;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.nnm.emenu.client.GreetingService;
import com.nnm.emenu.client.ultis.ExtParamKeys;
import com.nnm.emenu.server.dataengine.DataEngine;
import com.nnm.emenu.server.order.OrderManagerServer;
import com.nnm.emenu.server.utils.Config;
import com.nnm.emenu.server.utils.DateTime;
import com.nnm.emenu.shared.CountFoodOrder;
import com.nnm.emenu.shared.FoodCategory;
import com.nnm.emenu.shared.FoodInfo;
import com.nnm.emenu.shared.Order;
import com.nnm.emenu.shared.OrderData;
import com.nnm.emenu.shared.OrderDetail;
import com.nnm.emenu.shared.SumMoney;
import com.nnm.emenu.shared.UserInfo;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	OrderManagerServer orderManagerServer;

	// private ConcurrentMap<String, CometSession> users_map = new
	// ConcurrentHashMap<String, CometSession>();

	// static HashSet<String> names = new HashSet<String>();
	// static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

	public GreetingServiceImpl() {
		Config.loadConfig();
		// DataEngine.getInstance().start("localhost", "3306", "root",
		// "anhminh89192", "emenudb", 10, 10, "");
		DataEngine.getInstance().start(Config.DB_ADDRESS, Config.DB_PORT,
				Config.DB_USERNAME, Config.DB_PASSWORD, Config.DB_NAME,
				Integer.valueOf(Config.DB_MAX_CONNECTION),
				Integer.valueOf(Config.DB_CLEAR_PERIOD), Config.DB_PATH_LOG);

		orderManagerServer = new OrderManagerServer();

		List<UserInfo> listUser = DataEngine.getInstance().getListUser();
		for (int i = 0; i < listUser.size(); i++) {
			DataEngine.getInstance().updateUserState(listUser.get(i).getId(),
					UserInfo.STATE_OFFLINE);
		}
	}

	@Override
	public List<UserInfo> getListUser() {
		// TODO Auto-generated method stub
		return DataEngine.getInstance().getListUser();
	}

	@Override
	public UserInfo login(String username, String password) {
		List<UserInfo> users = DataEngine.getInstance().getUser(username,
				password);
		if (users.size() == 0) {
			return null;
		} else {
			if (checkOnline(users.get(0).getId())) {
				UserInfo user = new UserInfo();
				user.setId(-1);
				return user;
			} else {
				DataEngine.getInstance().updateUserState(users.get(0).getId(),
						UserInfo.STATE_ONLINE);
				users.get(0).setState(UserInfo.STATE_ONLINE);
				return users.get(0);
			}
		}
	}

	@Override
	public void logout(int id) {
		// TODO Auto-generated method stub
		DataEngine.getInstance().updateUserState(id, UserInfo.STATE_OFFLINE);
	}

	@Override
	public String uploadImage(String data) {
		return null;
	}

	@Override
	public String downloadImage(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String savenewFood(FoodInfo food) {
		try {
			DataEngine.getInstance().insertNewFood(food);
		} catch (Exception e) {
			e.printStackTrace();
			return ExtParamKeys.FAIL;
		}
		return ExtParamKeys.SUCCESS;
	}

	@Override
	public List<FoodInfo> getFood(int key) {
		if (key == -1) {
			return DataEngine.getInstance().getFoods();
		} else {
			return DataEngine.getInstance().getFoods(key);
		}
	}

	@Override
	public UserInfo getUserInfo(String username) {
		return null;
	}

	@Override
	public List<FoodCategory> getFoodCategory(int parent_id) {
		if (parent_id == -1) {
			return DataEngine.getInstance().getFoodCategory();
		} else {
			return DataEngine.getInstance().getFoodCategory(parent_id);
		}
	}

	@Override
	public Order createOrder(int table, String time, int user_id) {
		if (checkOnline(user_id)) {
			DataEngine.getInstance().insertNewOrder(table, 0, time);
			Order order = DataEngine.getInstance().getOrder(table, time).get(0);
			OrderData orderData = new OrderData();
			orderData.setOrder(order);
			orderManagerServer.addNewOrder(orderData);
			orderManagerServer.setVersion(orderManagerServer.getVersion() + 1);
			return order;
		} else {
			return null;
		}
	}

	@Override
	public OrderDetail order(int table, int userId, int foodId, int amount,
			int money, String time) {
		if (checkOnline(userId)) {
			OrderData orderData = orderManagerServer.getOrder(table);
			if (orderData == null) {
				orderData = new OrderData();
				DataEngine.getInstance().insertNewOrder(table, 0, time);
				Order order = DataEngine.getInstance().getOrder(table, time)
						.get(0);
				orderData.setOrder(order);
				System.out.println("order id : " + order.getId());
				orderManagerServer.addNewOrder(orderData);
			}
			int totalMoney = orderData.getOrder().getMoney() + money;
			orderData.getOrder().setMoney(totalMoney);
			DataEngine.getInstance().insertNewOrderDetail(
					orderData.getOrder().getId(), table, userId, foodId,
					amount, money, time);
			DataEngine.getInstance().updateOrderMoney(
					orderData.getOrder().getId(), totalMoney);
			OrderDetail orderDetail = DataEngine.getInstance()
					.getOrderDetail(orderData.getOrder().getId(), time).get(0);
			System.out.println("orderDetail id : " + orderDetail.getId());
			orderData.addNewOrderDetail(orderDetail);
			orderData.setVersion(orderData.getVersion() + 1);
			orderManagerServer.setVersion(orderManagerServer.getVersion() + 1);
			return orderDetail;
		} else {
			return null;
		}
	}

	@Override
	public int cancelOrder(int table, int user_id) {
		try {
			if (checkOnline(user_id)) {
				OrderData orderData = orderManagerServer.getOrder(table);
				Order order = orderData.getOrder();
				DataEngine.getInstance().updateOrderState(order.getId(),
						Order.STATE_CANCEL);
				DataEngine.getInstance().updateStateOrderDetailByOrder(
						order.getId(), OrderDetail.STATE_CANCEL);
				orderManagerServer.removerOrder(table);
				orderManagerServer
						.setVersion(orderManagerServer.getVersion() + 1);
				return 1;
			} else {
				return 2;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public boolean cancelOrderDetail(int table, int userId, long order_food_Id) {
		if (checkOnline(userId)) {
			DataEngine.getInstance().updateStateOrderDetail(order_food_Id,
					OrderDetail.STATE_CANCEL);
			OrderData orderData = orderManagerServer.getOrder(table);
			if (orderData != null) {
				orderData.getOrderDetail(order_food_Id).setState(
						OrderDetail.STATE_CANCEL);
				orderData.setVersion(orderData.getVersion() + 1);
				System.out.println("change version");
				orderManagerServer
						.setVersion(orderManagerServer.getVersion() + 1);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int changeStateOrder(int table, long id, int state, int user_id) {
		if (checkOnline(user_id)) {
			DataEngine.getInstance().updateStateOrderDetail(id, state);
			orderManagerServer.changeStateOrderDetail(table, id, state);
			orderManagerServer.setVersion(orderManagerServer.getVersion() + 1);
			orderManagerServer.getOrder(table).setVersion(
					orderManagerServer.getOrder(table).getVersion() + 1);
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int changeAmountOrderDetail(int table, long id, int amount,
			int user_id) {
		if (checkOnline(user_id)) {
			if (orderManagerServer.getOrder(table).getOrderDetail(id)
					.getState() == OrderDetail.STATE_WAIT_CONFIRM) {
				DataEngine.getInstance().updateAmountOrderDetail(id, amount);
				orderManagerServer.changeAmountOrderDetail(table, id, amount);
				orderManagerServer
						.setVersion(orderManagerServer.getVersion() + 1);
				orderManagerServer.getOrder(table).setVersion(
						orderManagerServer.getOrder(table).getVersion() + 1);
				return 1;
			} else {
				return 2;
			}
		} else {
			return 0;
		}
	}

	@Override
	public int changeStateOrderByFoodId(int table, long order_id, int food_id,
			int state, int user_id) {
		if (checkOnline(user_id)) {
			DataEngine.getInstance().updateStateOrderDetailByFoodId(order_id,
					food_id, state);
			orderManagerServer.changeStateOrderDetailByFoodId(table, food_id,
					state);
			orderManagerServer.setVersion(orderManagerServer.getVersion() + 1);
			orderManagerServer.getOrder(table).setVersion(
					orderManagerServer.getOrder(table).getVersion() + 1);
			return 1;
		}
		return 0;
	}

	@Override
	public int changeTable(long order_id, int table, int user_id) {
		if (checkOnline(user_id)) {
			DataEngine.getInstance().updateOrderTable(order_id, table);
			DataEngine.getInstance().updateTableOrderDetail(order_id, table);
			orderManagerServer.changeOrdertable(order_id, table);
			orderManagerServer.setVersion(orderManagerServer.getVersion() + 1);
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public long getDataVersion() {
		return orderManagerServer.getVersion();
	}

	@Override
	public ArrayList<OrderData> refresh() {
		ArrayList<OrderData> orders = new ArrayList<OrderData>(
				orderManagerServer.orders.values());
		return orders;
	}

	@Override
	public boolean payment(long order_id, int user_id, int table,
			double money_total, double money_discount, float vat,
			double money_payment, double money_customer, double money_return,
			String gendate) {
		if (checkOnline(user_id)) {
			DataEngine.getInstance().insertNewBill(order_id, user_id, table,
					money_total, money_discount, vat, money_payment,
					money_customer, money_return, gendate);
			DataEngine.getInstance().updateOrderState(order_id,
					Order.STATE_PAYMENT);
			orderManagerServer.removerOrder(table);
			orderManagerServer.setVersion(orderManagerServer.getVersion() + 1);
			return true;
		} else {
			return false;
		}
	}

	boolean checkOnline(int user_id) {
		if (DataEngine.getInstance().getUser(user_id).size() == 0) {
			return false;
		}
		if (DataEngine.getInstance().getUser(user_id).get(0).getState() == UserInfo.STATE_ONLINE) {
			return true;
		}
		return false;
	}

	@Override
	public FoodInfo saveNewFood(int user_id, String title, String code,
			String des, int price, String unit, int category_id,
			String category_title, int state, String gendate) {
		if (checkOnline(user_id)) {
			List<FoodInfo> listFood = DataEngine.getInstance().getFoods(code);
			if (listFood.size() > 0) {
				FoodInfo fooderr = new FoodInfo();
				fooderr.setTitle("Mã món ăn đã tồn tại!!!");
				fooderr.setId(-1);
				return fooderr;
			}
			DataEngine.getInstance().insertNewFood(title, code, des, price,
					unit, category_id, category_title, "", state, gendate);
			try {
				FoodInfo foodInfo = DataEngine.getInstance().getFoods(code)
						.get(0);
				return foodInfo;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public FoodInfo updateFood(int user_id, String code, String title,
			String des, String price, String unit, String category_id,
			String gendate) {
		if (checkOnline(user_id)) {
			DataEngine.getInstance().updateFoodInfo(code, title, des, price,
					unit, category_id, gendate);
			FoodInfo foodInfo = DataEngine.getInstance().getFoods(code).get(0);
			return foodInfo;
		} else {
			return null;
		}
	}

	@Override
	public int deleteFood(int user_id, String code) {
		if (checkOnline(user_id)) {
			try {
				DataEngine.getInstance().deleteFoodInfo(code);
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			return -1;
		}
	}

	@Override
	public List<FoodInfo> search(int user_id, String text) {
		if (checkOnline(user_id)) {
			return DataEngine.getInstance().searchFood(text);
		} else {
			return null;
		}
	}

	// //////////

	@Override
	public FoodCategory getFoodCategoryById(int id) {
		// TODO Auto-generated method stub
		return DataEngine.getInstance().getFoodCategoryById(id).get(0);
	}

	@Override
	public FoodCategory saveNewFoodCategory(int user_id, String title,
			String description, String url, int parent_id, String gendate) {
		if (checkOnline(user_id)) {
			DataEngine.getInstance().insertNewFoodCategory(title, description,
					url, parent_id, gendate);
			try {
				FoodCategory foodCategory = DataEngine.getInstance()
						.getFoodCategory(gendate).get(0);
				return foodCategory;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public FoodCategory updateFoodCategory(int user_id, int id, String title,
			String description, int parent_id, String gendate) {
		if (checkOnline(user_id)) {
			DataEngine.getInstance().updateFoodCategory(id, title, description,
					parent_id, gendate);
			FoodCategory foodCategory = DataEngine.getInstance()
					.getFoodCategoryById(id).get(0);
			return foodCategory;
		} else {
			return null;
		}
	}

	@Override
	public int deleteFoodCategory(int user_id, int id) {
		if (checkOnline(user_id)) {
			try {
				FoodCategory foodCategory = DataEngine.getInstance()
						.getFoodCategoryById(id).get(0);
				if (foodCategory.getParent_id() == 0) {
					DataEngine.getInstance().updateCategoryFoodInFo(id,
							foodCategory.getParent_id(), "NHÓM THỰC ĐƠN");
				} else {
					FoodCategory parentFoodCategory = DataEngine.getInstance()
							.getFoodCategoryById(foodCategory.getParent_id())
							.get(0);
					DataEngine.getInstance().updateCategoryFoodInFo(id,
							foodCategory.getParent_id(),
							parentFoodCategory.getTitle());
				}
				DataEngine.getInstance().deleteFoodCategory(id);
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			return -1;
		}

	}

	@Override
	public List<SumMoney> getSumMoney(int user_id, int typeFilter,
			String time_start, String time_end) {
		if (checkOnline(user_id)) {
			List<SumMoney> listSumMoney = DataEngine.getInstance().getSumMoney(
					typeFilter, time_start, time_end + " 23:59:59");
			System.out.println("listSumMoney size : " + listSumMoney.size());
			List<SumMoney> result = new ArrayList<SumMoney>();
			if (typeFilter == 0) {
				ArrayList<String> listTime = DateTime.getListDate(time_start,
						time_end);
				int index = 0;
				for (int i = 0; i < listTime.size(); i++) {
					if (listSumMoney.size() > 0) {
						if (!checkContainTime(listSumMoney, listTime.get(i))) {
							SumMoney sum = new SumMoney();
							sum.setTime(DateTime.changeDateFormat(
									listTime.get(i), "yyyy-MM-dd", "dd/MM/yy"));
							sum.setSumMoney(0);
							result.add(sum);
						} else {
							listSumMoney.get(index).setTime(
									DateTime.changeDateFormat(
											listSumMoney.get(index).getTime(),
											"yyyy-MM-dd", "dd/MM/yy"));
							result.add(listSumMoney.get(index++));
						}
					} else {
						SumMoney sum = new SumMoney();
						sum.setTime(DateTime.changeDateFormat(listTime.get(i),
								"yyyy-MM-dd", "dd/MM/yy"));
						sum.setSumMoney(0);
						result.add(sum);
					}
				}
			} else if (typeFilter == 1) {
				ArrayList<String> listTime = DateTime.getListWeek(time_start,
						time_end);
				int index = 0;
				for (int i = 0; i < listTime.size(); i++) {
					if (listSumMoney.size() > 0) {
						if (!checkContainTime(listSumMoney, listTime.get(i)
								.substring(listTime.get(i).indexOf("::") + 2))) {
							SumMoney sum = new SumMoney();
							sum.setTime(listTime.get(i).substring(0,
									listTime.get(i).indexOf("::")));
							sum.setSumMoney(0);
							result.add(sum);

						} else {
							SumMoney sum = new SumMoney();
							sum.setTime(listTime.get(i).substring(0,
									listTime.get(i).indexOf("::")));
							sum.setSumMoney(listSumMoney.get(index)
									.getSumMoney());
							result.add(sum);
							index++;
						}
					} else {
						SumMoney sum = new SumMoney();
						sum.setTime(listTime.get(i).substring(0,
								listTime.get(i).indexOf("::")));
						sum.setSumMoney(0);
						result.add(sum);
					}
				}
			} else if (typeFilter == 2) {
				ArrayList<String> listTime = DateTime.getListMonth(time_start,
						time_end);
				int index = 0;
				for (int i = 0; i < listTime.size(); i++) {
					if (listSumMoney.size() > 0) {
						if (!checkContainTime(listSumMoney, listTime.get(i)
								.substring(0, 1))) {
							SumMoney sum = new SumMoney();
							sum.setTime(listTime.get(i));
							sum.setSumMoney(0);
							result.add(sum);
						} else {
							SumMoney sum = new SumMoney();
							sum.setTime(listTime.get(i));
							sum.setSumMoney(listSumMoney.get(index)
									.getSumMoney());
							result.add(sum);
							index++;
						}
					} else {
						SumMoney sum = new SumMoney();
						sum.setTime(listTime.get(i));
						sum.setSumMoney(0);
						result.add(sum);
					}
				}
			} else if (typeFilter == 3) {
				result = listSumMoney;
			} else if (typeFilter == 4 || typeFilter == 5) {
				result = listSumMoney;
			}
			return result;
		} else {
			return null;
		}
	}

	boolean checkContainTime(List<SumMoney> listSumMoney, String time) {
		for (int i = 0; i < listSumMoney.size(); i++) {
			if (listSumMoney.get(i).getTime().equals(time)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<CountFoodOrder> getCountFoodOrder(int user_id, int typeFilter,
			String time_start, String time_end, int max) {
		if (checkOnline(user_id)) {
			List<CountFoodOrder> result;
			if (typeFilter == 1) {
				result = DataEngine.getInstance().getCountFoodOrderBest(
						time_start, time_end, max);
				System.out.println("RESULT SOZE : " + result.size());
				return result;
			} else if (typeFilter == 0) {
				result = DataEngine.getInstance().getCountFoodOrderLeast(
						time_start, time_end, max);
				System.out.println("RESULT SOZE : " + result.size());
				return result;
			}
		}
		return null;
	}

	@Override
	public Map<String, Long> getTotalRevenueInfo(int user_id,
			String time_start, String time_end) {
		if (checkOnline(user_id)) {
			Map<String, Long> result = (Map<String, Long>) DataEngine
					.getInstance().getTotalMoneyRevenue(time_start, time_end);
			return result;
		} else {
			return null;
		}
	}

	@Override
	public int registerUser(String name, String username, String password,
			int roleId) {
		List<UserInfo> user = DataEngine.getInstance().getUser(username);
		if (user.size() > 0) {
			return 0;
		} else {
			DataEngine.getInstance().insetNewUser(name, username, password,
					roleId);
			return 1;
		}
	}

	@Override
	public int updateUser(int user_id, String name, String username,
			String password, int roleId) {
		if (checkOnline(user_id)) {
			DataEngine.getInstance().updateUserInfo(name, username, password,
					roleId);
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public int deleteUser(int user_id, String username) {
		if (checkOnline(user_id)) {
			List<UserInfo> user = DataEngine.getInstance().getUser(username);
			if (user.size() > 0) {
				DataEngine.getInstance().deleteUser(username);
				return 1;
			} else {
				return 0;
			}
		} else {
			return -1;
		}
	}
}
