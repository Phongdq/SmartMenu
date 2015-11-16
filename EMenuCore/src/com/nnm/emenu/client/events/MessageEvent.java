package com.nnm.emenu.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class MessageEvent extends GwtEvent<MessageEventHandle>{

	public static Type<MessageEventHandle> TYPE = new Type<MessageEventHandle>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MessageEventHandle> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(MessageEventHandle handler) {
		// TODO Auto-generated method stub
		handler.dosomething();
	}

}
