package com.nnm.emenu.client.activities;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.nnm.emenu.client.activities.foodmanager.FoodManagerView;
import com.nnm.emenu.client.activities.home.HomeView;
import com.nnm.emenu.client.activities.login.LoginView;
import com.nnm.emenu.client.activities.manageruser.ManagerUserVew;
import com.nnm.emenu.client.activities.revenue.ReportedRevenueView;

public interface ClientFactory {
	SimpleEventBus getEventBus();

	PlaceController getPlaceController();

	LoginView getLoginView();

	FoodManagerView getFoodManagerView();

	HomeView getHomeView();
	
	void clearHomeView();
	
	ReportedRevenueView getReportedRevenueView();
	
	ManagerUserVew getManagerUserVew();
	
}
