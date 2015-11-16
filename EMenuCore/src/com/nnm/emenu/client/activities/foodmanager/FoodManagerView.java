package com.nnm.emenu.client.activities.foodmanager;

import com.google.gwt.user.client.ui.Widget;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.utils.ui.CustomSearchTextBox;
import com.nnm.emenu.client.utils.ui.ProductHomeBinder;
import com.nnm.emenu.shared.FoodInfo;

public interface FoodManagerView {
	public Widget asWidget();

	void setSelected(int index);

	void handleButtonOrder(ClickListener listener);

	void handleButtonExpenses(ClickListener listener);

	void handleButtonMaterial(ClickListener listener);

	void handleButtonProduct(ClickListener listener);

	void handleButtonHome(ClickListener listener);

	ProductHomeBinder getProductHome();

	CustomSearchTextBox getSearchBox();
}
