/**
 * 
 */
package com.nnm.emenu.client.activities.manageruser;

import java.util.List;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.utils.ui.AddUserInfo;
import com.nnm.emenu.shared.UserInfo;

public interface ManagerUserVew {
	public Widget asWidget();

	public void createListUser(List<UserInfo> result);

	public FlexPanel getScrollContent();

	void handleButtonAddListener(ClickListener listener);

	PopupPanel getPopUp();

	AddUserInfo getAddUserInfoBinder();

	void handleButtonHomeListener(ClickListener listener);
}
