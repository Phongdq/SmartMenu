package com.nnm.emenu.client.activities.login;

import com.google.gwt.user.client.ui.Widget;
import com.nnm.emenu.client.listener.ClickListener;

public interface LoginView {
	public Widget asWidget();

	public void hasLoginListener(ClickListener listener);

	public String getUserName();

	public String getPassWord();
}
