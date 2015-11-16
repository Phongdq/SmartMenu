/**
 * 
 */
package com.nnm.emenu.client.events;

import java.util.ArrayList;

import com.google.gwt.event.shared.GwtEvent;
import com.nnm.emenu.shared.OrderData;

/**
 * @author bizluvsakura
 *
 */
public class OrderDataReceiveEvent extends
		GwtEvent<OrderDataReceiveEventHandle> {

	public static Type<OrderDataReceiveEventHandle> TYPE = new Type<OrderDataReceiveEventHandle>();

	private ArrayList<OrderData> listOrder = new ArrayList<OrderData>();

	public OrderDataReceiveEvent(ArrayList<OrderData> listOrder) {
		// TODO Auto-generated constructor stub
		this.listOrder = listOrder;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<OrderDataReceiveEventHandle> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(OrderDataReceiveEventHandle handler) {
		// TODO Auto-generated method stub
		handler.receive(this);
	}

	public ArrayList<OrderData> getOrders() {
		return listOrder;
	}

}
