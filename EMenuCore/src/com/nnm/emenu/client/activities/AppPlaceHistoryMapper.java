package com.nnm.emenu.client.activities;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.nnm.emenu.client.activities.foodmanager.FoodManagerPlace;
import com.nnm.emenu.client.activities.home.HomePlace;
import com.nnm.emenu.client.activities.login.LoginPlace;
import com.nnm.emenu.client.activities.manageruser.ManagerUserPlace;
import com.nnm.emenu.client.activities.revenue.ReportedRevenuePlace;

@WithTokenizers({ LoginPlace.Tokenizer.class, FoodManagerPlace.Tokenizer.class,
		HomePlace.Tokenizer.class, ReportedRevenuePlace.Tokenizer.class,
		ManagerUserPlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
