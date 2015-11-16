/**
 * 
 */
package com.nnm.emenu.shared;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author bizluvsakura
 *
 */
public class OrderData implements Serializable, IsSerializable {
	Order order;
	public ArrayList<OrderDetail> listOrderDetail = new ArrayList<OrderDetail>();
	int state;
	long version;

	public OrderData() {
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}

	public void addNewOrderDetail(OrderDetail orderDetail) {
		listOrderDetail.add(orderDetail);
	}

	public void addNewOrderDetail(int id, int food_id, int amount, int user_id,
			int money, String time) {
		OrderDetail orderDetail = new OrderDetail(id, order.getId(),
				order.getTable(), user_id, food_id, amount, money, time);
		listOrderDetail.add(orderDetail);
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public long getVersion() {
		return version;
	}

	public void removeOrderDetail(long id) {
		for (int i = 0; i < listOrderDetail.size(); i++) {
			if (listOrderDetail.get(i).getId() == id) {
				listOrderDetail.remove(i);
				return;
			}
		}
	}

	public void removerOrderDetailByFoodId(int food_id) {
		for (int i = 0; i < listOrderDetail.size(); i++) {
			if (listOrderDetail.get(i).getFood_id() == food_id) {
				listOrderDetail.remove(i);
				return;
			}
		}
	}

	public OrderDetail getOrderDetail(long id) {
		for (int i = 0; i < listOrderDetail.size(); i++) {
			if (id == listOrderDetail.get(i).getId()) {
				return listOrderDetail.get(i);
			}
		}
		return null;
	}
}
