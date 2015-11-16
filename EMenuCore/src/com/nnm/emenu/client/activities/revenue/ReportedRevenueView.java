package com.nnm.emenu.client.activities.revenue;

import com.google.gwt.user.client.ui.Widget;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.utils.ui.CustomImageButton;

public interface ReportedRevenueView {
	public Widget asWidget();

	void setTabSelected(int index);

	void handleButtonCategory(ClickListener listener);

	void handleButtonDate(ClickListener listener);

	void handleButtonProduct(ClickListener listener);
	
	CustomImageButton getButtonHome();

	void setFilterSelected(int index);

	void handleButtonFilterYear(ClickListener listener);

	void handleButtonFilterMonth(ClickListener listener);

	void handleButtonFilterWeek(ClickListener listener);

	void handleButtonFilterDay(ClickListener listener);

	void handleButtonView(ClickListener listener);

	void requestRevenue();
}
