/**
 * 
 */
package com.nnm.emenu.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.nnm.emenu.client.utils.ui.Loading;
import com.nnm.emenu.shared.FoodInfo;

/**
 * @author bizluvsakura
 *
 */
public class ClientManager {
	private static ClientManager INSTANCE;

	private int number_table = 15;

	// public ArrayList<OrderDataClient> order;
	// public ArrayList<FoodInfo> listFood;

	// public HashMap<Integer, OrderData> order;
	public HashMap<Integer, FoodInfo> food;

	public static ClientManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ClientManager();
		}
		return INSTANCE;
	}

	public ClientManager() {
		// TODO Auto-generated constructor stub
		// order = new ArrayList<OrderDataClient>();
		// listFood = new ArrayList<FoodInfo>();
		// order = new HashMap<Integer, OrderData>();
		food = new HashMap<Integer, FoodInfo>();
	}

	// public OrderData getOrder(int table) {
	// // for (int i = 0; i < order.size(); i++) {
	// // if (order.get(i).orderData.getTable() == table) {
	// // System.out.println("Size ton tai: "
	// // + order.get(i).orderData.getListFoodId().size());
	// // return order.get(i);
	// // }
	// // }
	// // OrderDataClient orderDataClient = new OrderDataClient(new
	// // OrderData());
	// // orderDataClient.orderData.setTable(table);
	// // System.out.println("tabel : " + table + "Size chua ton tai: "
	// // + orderDataClient.orderData.getListFoodId().size());
	// // order.add(orderDataClient);
	//
	// if (order.get(table) == null) {
	// OrderData orderDataClient = new OrderData();
	// orderDataClient.setTable(table);
	// order.put(table, orderDataClient);
	// }
	// return order.get(table);
	// }

	public FoodInfo getFood(int id) {
		// for (int i = 0; i < listFood.size(); i++) {
		// if (listFood.get(i).getId() == id) {
		// return listFood.get(i);
		// }
		// }
		// return null;
		return food.get(id);
	}

	public ArrayList<FoodInfo> getFoodByCategoty(int category_id) {
		ArrayList<FoodInfo> listFoodResult = new ArrayList<FoodInfo>();
		ArrayList<FoodInfo> listFood = new ArrayList<FoodInfo>(food.values());
		System.out.println("categoty_id : " + category_id);
		System.out.println("listFood size : " + listFood.size());
		for (int i = 0; i < listFood.size(); i++) {
			if (listFood.get(i).getCategory_id() == category_id) {
				listFoodResult.add(listFood.get(i));
			}
		}
		System.out.println("listFood result : " + listFoodResult.size());
		return listFoodResult;
	}

	public void creatListFood(List<FoodInfo> listFood) {
		food.clear();
		for (int i = 0; i < listFood.size(); i++) {
			food.put(listFood.get(i).getId(), listFood.get(i));
		}
	}

	public int getNumber_table() {
		return number_table;
	}

	public void setNumber_table(int number_table) {
		this.number_table = number_table;
	}

	public void getListFood() {
		EMenuCore.greetingService.getFood(-1,
				new AsyncCallback<List<FoodInfo>>() {

					@Override
					public void onSuccess(List<FoodInfo> result) {
						// TODO Auto-generated method stub
						ClientManager.getInstance().creatListFood(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("Failure : " + caught.getMessage());
					}
				});
	}

	public void updateListFood() {
		EMenuCore.greetingService.getFood(-1,
				new AsyncCallback<List<FoodInfo>>() {

					@Override
					public void onSuccess(List<FoodInfo> result) {
						// TODO Auto-generated method stub
						ClientManager.getInstance().creatListFood(result);
						Loading.getInstance().hide();
						Window.alert("Cập nhật dữ liệu thành công");
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("Failure : " + caught.getMessage());
					}
				});
	}

	// public void changeOrderData(int table, OrderData order) {
	// this.order.remove(table);
	// this.order.put(table, order);
	// }
}
