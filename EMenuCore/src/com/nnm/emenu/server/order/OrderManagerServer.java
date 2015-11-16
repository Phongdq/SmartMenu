/**
 * 
 */
package com.nnm.emenu.server.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nnm.emenu.server.dataengine.DataEngine;
import com.nnm.emenu.shared.Order;
import com.nnm.emenu.shared.OrderData;
import com.nnm.emenu.shared.OrderDetail;

/**
 * @author bizluvsakura
 *
 */
public class OrderManagerServer {
	private long version = -1;
	public Map<Integer, OrderData> orders;

	public OrderManagerServer() {
		orders = new HashMap<Integer, OrderData>();
		createListOrder();
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public void addNewOrder(OrderData order) {
		this.orders.put(order.getOrder().getTable(), order);
	}

	public void removerOrder(int table) {
		this.orders.remove(table);
	}

	public OrderData getOrder(int table) {
		return this.orders.get(table);
	}

	public void changeOrdertable(long order_id, int table) {
		ArrayList<OrderData> orders = new ArrayList<OrderData>(
				this.orders.values());
		for (int i = 0; i < orders.size(); i++) {
			if (order_id == orders.get(i).getOrder().getId()) {
				OrderData order = orders.get(i);
				this.orders.remove(order.getOrder().getTable());
				order.getOrder().setTable(table);
				for (int j = 0; j < order.listOrderDetail.size(); j++) {
					order.listOrderDetail.get(j).setTable(table);
				}
				this.orders.put(table, order);
				return;
			}
		}

	}

	public OrderDetail getOrderDetail(int table, long id) {
		for (int i = 0; i < this.orders.get(table).listOrderDetail.size(); i++) {
			if (this.orders.get(table).listOrderDetail.get(i).getId() == id) {
				return this.orders.get(table).listOrderDetail.get(i);
			}
		}
		return null;
	}

	public void changeStateOrderDetailByFoodId(int table, int food_id,
			int state) {
		for (int i = 0; i < this.orders.get(table).listOrderDetail.size(); i++) {
			if (this.orders.get(table).listOrderDetail.get(i).getFood_id() == food_id) {
				this.orders.get(table).listOrderDetail.get(i).setState(state);
			}
		}
	}

	public void changeStateOrderDetail(int table, long id, int state) {
		System.out.println("id : " + getOrderDetail(table, id).getId());
		System.out.println("table : " + getOrderDetail(table, id).getTable());
		getOrderDetail(table, id).setState(state);
	}
	
	public void changeAmountOrderDetail(int table,long id,int amount){
		getOrderDetail(table, id).setAmount(amount);
	}

	private void createListOrder() {
		List<Order> listOrder = DataEngine.getInstance().getLastOrderToday();
		for (int i = 0; i < listOrder.size(); i++) {
			OrderData orderData = new OrderData();
			orderData.setOrder(listOrder.get(i));
			orderData.listOrderDetail = (ArrayList<OrderDetail>) DataEngine
					.getInstance().getOrderDetail(listOrder.get(i).getId());
			orders.put(listOrder.get(i).getTable(), orderData);
			System.out.println("order id : " + listOrder.get(i).getId());
			System.out.println("orderDetail size : "
					+ orderData.listOrderDetail.size());
		}
	}
}
