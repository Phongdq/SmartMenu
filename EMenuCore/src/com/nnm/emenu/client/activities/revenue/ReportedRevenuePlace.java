package com.nnm.emenu.client.activities.revenue;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ReportedRevenuePlace extends Place {
	String token = "";

	public ReportedRevenuePlace(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static class Tokenizer implements PlaceTokenizer<ReportedRevenuePlace> {

		@Override
		public ReportedRevenuePlace getPlace(String token) {
			return new ReportedRevenuePlace(token);
		}

		@Override
		public String getToken(ReportedRevenuePlace place) {
			return place.getToken();
		}
	}
}
