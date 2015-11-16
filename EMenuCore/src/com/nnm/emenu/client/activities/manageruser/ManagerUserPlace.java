package com.nnm.emenu.client.activities.manageruser;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ManagerUserPlace extends Place {
	String token = "";

	public ManagerUserPlace(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static class Tokenizer implements PlaceTokenizer<ManagerUserPlace> {

		@Override
		public ManagerUserPlace getPlace(String token) {
			return new ManagerUserPlace(token);
		}

		@Override
		public String getToken(ManagerUserPlace place) {
			return place.getToken();
		}
	}
}
