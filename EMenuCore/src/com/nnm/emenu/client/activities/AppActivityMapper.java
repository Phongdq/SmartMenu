package com.nnm.emenu.client.activities;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.nnm.emenu.client.activities.foodmanager.FoodManagerActivity;
import com.nnm.emenu.client.activities.foodmanager.FoodManagerPlace;
import com.nnm.emenu.client.activities.home.HomeActivity;
import com.nnm.emenu.client.activities.home.HomePlace;
import com.nnm.emenu.client.activities.login.LoginActivity;
import com.nnm.emenu.client.activities.login.LoginPlace;
import com.nnm.emenu.client.activities.manageruser.ManagerUserActivity;
import com.nnm.emenu.client.activities.manageruser.ManagerUserPlace;
import com.nnm.emenu.client.activities.revenue.ReportedRevenueActivity;
import com.nnm.emenu.client.activities.revenue.ReportedRevenuePlace;

public class AppActivityMapper implements ActivityMapper {
	ClientFactory clientFactory;
	LoginActivity loginActivity;
	FoodManagerActivity foodManagerActivity;
	HomeActivity homeActivity;
	ReportedRevenueActivity reportedRevenueActivity;
	ManagerUserActivity managerUserActivity;

	public AppActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof LoginPlace) {
			if (loginActivity == null) {
				loginActivity = new LoginActivity();
			}
			return loginActivity;
		} else if (place instanceof FoodManagerPlace) {
			if (foodManagerActivity == null) {
				foodManagerActivity = new FoodManagerActivity();
			}
			return new FoodManagerActivity();
		} else if (place instanceof HomePlace) {
			if (homeActivity == null) {
				homeActivity = new HomeActivity();
				System.out.println("call create homeActivity");
			}
			return homeActivity;
		} else if (place instanceof ReportedRevenuePlace) {
			if (reportedRevenueActivity == null) {
				reportedRevenueActivity = new ReportedRevenueActivity();
			}
			return reportedRevenueActivity;
			// return new ReportedRevenueActivity();
		} else if (place instanceof ManagerUserPlace) {
			if (managerUserActivity == null) {
				managerUserActivity = new ManagerUserActivity();
			}
			return new ManagerUserActivity();
		}
		return null;
	}

	public void clearHomeActivity() {
		homeActivity = null;
	}

}
