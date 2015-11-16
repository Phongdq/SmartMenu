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
public class SumMoney implements Serializable, IsSerializable {
	long sumMoney;
	String time;

	public SumMoney() {
	}

	public SumMoney(String time, long sumMoney) {
		this.time = time;
		this.sumMoney = sumMoney;
	}

	public long getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(long sumMoney) {
		this.sumMoney = sumMoney;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
