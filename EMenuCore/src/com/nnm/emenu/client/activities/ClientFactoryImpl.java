package com.nnm.emenu.client.activities;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.nnm.emenu.client.activities.foodmanager.FoodManagerView;
import com.nnm.emenu.client.activities.foodmanager.FoodManagerViewImpl;
import com.nnm.emenu.client.activities.home.HomeView;
import com.nnm.emenu.client.activities.home.HomeViewImpl;
import com.nnm.emenu.client.activities.login.LoginVewImpl;
import com.nnm.emenu.client.activities.login.LoginView;
import com.nnm.emenu.client.activities.manageruser.ManagerUserVew;
import com.nnm.emenu.client.activities.manageruser.ManagerUserViewImpl;
import com.nnm.emenu.client.activities.revenue.ReportedRevenueView;
import com.nnm.emenu.client.activities.revenue.ReportedRevenueViewImpl;

public class ClientFactoryImpl implements ClientFactory {
	SimpleEventBus eventBus;
	PlaceController placeController;

	LoginView loginView;
	FoodManagerView foodManagerView;
	HomeView homeView;
	ReportedRevenueView reportedRevenueView;
	ManagerUserVew managerUserView;

	@SuppressWarnings("deprecation")
	public ClientFactoryImpl() {
		eventBus = GWT.create(SimpleEventBus.class);
		placeController = new PlaceController(eventBus);
	}

	void creatEventBus() {
	}

	@Override
	public SimpleEventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public LoginView getLoginView() {
		if (loginView == null) {
			loginView = new LoginVewImpl();
		}
		return loginView;
	}

	@Override
	public FoodManagerView getFoodManagerView() {
		if (foodManagerView == null) {
			foodManagerView = new FoodManagerViewImpl();
		}
		return foodManagerView;
	}

	@Override
	public HomeView getHomeView() {
		if (homeView == null) {
			homeView = new HomeViewImpl();
			System.out.println("Call get HomeViewIMPL");
		}
		return homeView;
	}

	@Override
	public void clearHomeView() {
		homeView = null;
	}

	@Override
	public ReportedRevenueView getReportedRevenueView() {
		if (reportedRevenueView == null) {
			reportedRevenueView = new ReportedRevenueViewImpl();
		}
		return reportedRevenueView;
		// return new ReportedRevenueViewImpl();
	}

	@Override
	public ManagerUserVew getManagerUserVew() {
		if (managerUserView == null) {
			managerUserView = new ManagerUserViewImpl();
		}
		return managerUserView;
	}

}
