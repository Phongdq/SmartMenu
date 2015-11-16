/**
 * 
 */
package com.nnm.emenu.shared;

import java.io.Serializable;
import java.sql.Timestamp;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author bizluvsakura
 *
 */
public class OrderDetail implements Serializable, IsSerializable {
	private long id;
	private long order_id;
	private int table;
	private int user_id;
	private int food_id;
	private int amount;
	private int money;
	private String time;

	private int state;
	public static final int STATE_WAIT_CONFIRM = 0;
	public static final int STATE_WAIT_COMPLETE = 1;
	public static final int STATE_COMPLETE = 2;
	public static final int STATE_REJECT = 3;
	public static final int STATE_CANCEL = 4;

	public OrderDetail() {
	}

	public OrderDetail(long id, long order_id, int table, int user_id,
			int food_id, int amount, int money, String time) {
		this.id = id;
		this.order_id = order_id;
		this.table = table;
		this.user_id = user_id;
		this.food_id = food_id;
		this.amount = amount;
		this.money = money;
		this.time = time;
		this.state = STATE_WAIT_CONFIRM;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	public int getTable() {
		return table;
	}

	public void setTable(int table) {
		this.table = table;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getFood_id() {
		return food_id;
	}

	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}
}
