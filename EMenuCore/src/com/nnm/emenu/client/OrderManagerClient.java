/**
 * 
 */
package com.nnm.emenu.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.nnm.emenu.shared.OrderData;
import com.nnm.emenu.shared.OrderDetail;

/**
 * @author bizluvsakura
 *
 */
public class OrderManagerClient {
	static OrderManagerClient INSTANCE;
	Map<Integer, OrderData> orders;

	public OrderManagerClient() {
		orders = new HashMap<Integer, OrderData>();
	}

	public void addNewOrder(OrderData order) {
		orders.put(order.getOrder().getTable(), order);
	}

	public void removerOrder(int table) {
		this.orders.remove(table);
	}

	public OrderData getOrder(int table) {
		return this.orders.get(table);
	}

	public void changeOrder(int table, OrderData order) {
		if (this.orders.containsKey(table)) {
			this.orders.remove(table);
		}
		this.orders.put(table, order);
	}

	public void changeOrdertable(int last_table, int new_table) {
		OrderData order = getOrder(last_table);
		order.getOrder().setTable(new_table);
		for (int i = 0; i < order.listOrderDetail.size(); i++) {
			order.listOrderDetail.get(i).setTable(new_table);
		}
		orders.remove(last_table);
		orders.put(new_table, order);
	}

	public static OrderManagerClient getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new OrderManagerClient();
		}
		return INSTANCE;
	}

	private OrderDetail getOrderDetail(int table, long id) {
		for (int i = 0; i < this.orders.get(table).listOrderDetail.size(); i++) {
			if (this.orders.get(table).listOrderDetail.get(i).getId() == id) {
				return this.orders.get(table).listOrderDetail.get(i);
			}
		}
		return null;
	}

	public void changeStateOrderDetail(int table, long id, int state) {
		System.out.println("id : " + getOrderDetail(table, id).getId());
		System.out.println("table : " + getOrderDetail(table, id).getTable());
		getOrderDetail(table, id).setState(state);
	}
	
	public void changeAmountOrderDetail(int table,long id,int amount){
		getOrderDetail(table, id).setAmount(amount);
	}

	public void changeStateOrderDetailByFoodId(int table, int food_id, int state) {
		for (int i = 0; i < orders.get(table).listOrderDetail.size(); i++) {
			if (orders.get(table).listOrderDetail.get(i).getFood_id() == food_id) {
				orders.get(table).listOrderDetail.get(i).setState(state);
			}
		}
	}

	public void getListOrder() {
		EMenuCore.greetingService
				.refresh(new AsyncCallback<ArrayList<OrderData>>() {

					@Override
					public void onSuccess(ArrayList<OrderData> result) {
						// TODO Auto-generated method stub
						System.out
								.println("get list order success \nList order size : "
										+ result.size());
						for (int i = 0; i < result.size(); i++) {
							orders.put(result.get(i).getOrder().getTable(),
									result.get(i));
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});
	}
}
