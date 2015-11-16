/**
 * 
 */
package com.nnm.emenu.shared;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author bizluvsakura
 *
 */
public class Bill implements Serializable, IsSerializable {
	private long id;
	private long table;
	private long order_id;
	private int user_id;
	private double money_total;
	private double money_discount;
	private float vat;
	private double money_payment;
	private double money_customer;
	private double money_return;
	private String gendate;

	public Bill() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTable() {
		return table;
	}

	public void setTable(long table) {
		this.table = table;
	}

	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public double getMoney_total() {
		return money_total;
	}

	public void setMoney_total(double money_total) {
		this.money_total = money_total;
	}

	public double getMoney_discount() {
		return money_discount;
	}

	public void setMoney_discount(double money_discount) {
		this.money_discount = money_discount;
	}

	public float getVat() {
		return vat;
	}

	public void setVat(float vat) {
		this.vat = vat;
	}

	public double getMoney_payment() {
		return money_payment;
	}

	public void setMoney_payment(double money_payment) {
		this.money_payment = money_payment;
	}

	public double getMoney_customer() {
		return money_customer;
	}

	public void setMoney_customer(double money_customer) {
		this.money_customer = money_customer;
	}

	public double getMoney_return() {
		return money_return;
	}

	public void setMoney_return(double money_return) {
		this.money_return = money_return;
	}

	public String getGendate() {
		return gendate;
	}

	public void setGendate(String gendate) {
		this.gendate = gendate;
	}
}
