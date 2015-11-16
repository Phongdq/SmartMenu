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
public class Order implements Serializable, IsSerializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private int table;
	private int money;
	// private long time;
	private int state;
	private String time;

	//
	public static final int STATE_ORDERING = 0;
	public static final int STATE_PAYMENT = 1;
	public static final int STATE_CANCEL = 2;

	public Order() {
		// TODO Auto-generated constructor stub
		this.state = STATE_ORDERING;
	}

	public Order(long id, int table, int money, String time) {
		this.id = id;
		this.table = table;
		this.money = money;
		this.time = time;
		this.state = STATE_ORDERING;
	}

	public Order(int table, int money, String time) {
		this.table = table;
		this.money = money;
		this.time = time;
		this.state = STATE_ORDERING;
	}

	public Order(int table, String time) {
		this.table = table;
		this.time = time;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTable() {
		return table;
	}

	public void setTable(int table) {
		this.table = table;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
