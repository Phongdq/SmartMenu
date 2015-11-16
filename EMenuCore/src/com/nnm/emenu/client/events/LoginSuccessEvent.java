/**
 * 
 */
package com.nnm.emenu.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.nnm.emenu.shared.UserInfo;

/**
 * @author bizluvsakura
 *
 */
public class LoginSuccessEvent extends GwtEvent<LoginSuccessEventHandle> {

	public static Type<LoginSuccessEventHandle> TYPE = new Type<LoginSuccessEventHandle>();

	UserInfo user;

	public LoginSuccessEvent(UserInfo user) {
		// TODO Auto-generated constructor stub
		this.user = user;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoginSuccessEventHandle> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(LoginSuccessEventHandle handler) {
		// TODO Auto-generated method stub
		handler.success(this);
	}

	public UserInfo getUser() {
		return user;
	}

}
