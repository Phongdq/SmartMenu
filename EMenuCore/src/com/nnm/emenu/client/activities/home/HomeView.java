/**
 * 
 */
package com.nnm.emenu.client.activities.home;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.panel.Panel;
import com.nnm.emenu.client.utils.ui.ChangeTableBinder;
import com.nnm.emenu.client.utils.ui.CustomImageButton;
import com.nnm.emenu.client.utils.ui.CustomSearchTextBox;
import com.nnm.emenu.client.utils.ui.ItemMenu;
import com.nnm.emenu.client.utils.ui.MenuContentBinder;
import com.nnm.emenu.client.utils.ui.OrderContentBinder;
import com.nnm.emenu.client.utils.ui.OrderDetailBinder;
import com.nnm.emenu.client.utils.ui.PaymentContentBinder;
import com.nnm.emenu.client.utils.ui.TableObject;

/**
 * @author bizluvsakura
 *
 */
public interface HomeView {
	public Widget asWidget();

	CustomImageButton getButtonMenu();

	CustomImageButton getButtonOrder();

	CustomImageButton getButtonEMenu();

	void showMenuContent();

	void handlerMenuLeft();

	CustomImageButton getButtonMenuHeader();

	Panel getMenuContent();

	Panel getMenuContentDetail();

	MenuContentBinder getMenuContentBinder();

	void setStateHome();

	void setStateEMenu();

	void setStateEMenuDetail();

	int getStateContent();

	boolean isStateHome();

	boolean isStateEMenu();

	boolean isStateEMenuDetail();

	void backtoHome();

	void backToEMenu();

	OrderContentBinder getOrderContent();

	void changeStateTable(int table, int state);

	TableObject getTable(int table);

	void setUserTitle(String userName);

	PaymentContentBinder getPaymentContent();

	PopupPanel getPopup();

	ItemMenu getItemMenuUser();

	ItemMenu getItemMenuLogout();

	ItemMenu getItemMenuManager();

	ItemMenu getItemMenuUpdate();

	CustomSearchTextBox getSearchBox();

	ItemMenu getItemMenuReport();

	ItemMenu getItemManagerUser();

	void setMenuLeftPermission();

	ItemMenu getItemMenuInfo();

	void showChangeTable(int table);

	void showPayment();

	ChangeTableBinder getChangeTableBinder();

	void showOrderDetail(int table, int food_id, String title, int price);

	OrderDetailBinder getOrderDetailBinder();
}
