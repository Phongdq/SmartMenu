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
public class CountFoodOrder implements Serializable, IsSerializable {
	private int food_id;
	private int count;

	public CountFoodOrder() {
	}

	public CountFoodOrder(int food_id, int count) {
		this.food_id = food_id;
		this.count = count;
	}

	public int getFood_id() {
		return food_id;
	}

	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
