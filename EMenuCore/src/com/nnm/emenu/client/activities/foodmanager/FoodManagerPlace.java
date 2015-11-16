package com.nnm.emenu.client.activities.foodmanager;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class FoodManagerPlace extends Place {
	String token = "addfoodView";

	public FoodManagerPlace(String token) {
		this.token = token;
	}
	public String getToken() {
		return token;
	}
	
	public void setToken(String token){
		this.token = token;
	}
	
	public static class Tokenizer implements PlaceTokenizer<FoodManagerPlace>{

		@Override
		public FoodManagerPlace getPlace(String token) {
			return new FoodManagerPlace(token);
		}

		@Override
		public String getToken(FoodManagerPlace place) {
			return place.getToken();
		}
		
	}
}
