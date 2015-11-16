package com.nnm.emenu.client.ultis;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

public class Constants {
	public static int WIDTH = Window.getClientWidth();
	public static int HEIGHT = Window.getClientHeight();
	public static String WIDTH_APP = WIDTH + "px";
	public static String HEIGHT_APP = HEIGHT + "px";

	public static final String CHAT_PLACE = "chat_view";
	public static final String HOME_PLACE = "home_view";
	public static final String LOGIN_PLACE = "login_view";
	public static final String ORDER_PLACE = "order_view";
	public static final String ADDFOOD_PLACE = "addfood_view";
	public static final String MENU_PLACE = "menu_view";
	public static final String BASIC_PLACE = "basic_view";
	public static final String REPORTED_RENEVUE_PLACE = "reported_revenue_view";
	public static final String MANAGER_USER_PLACE = "manager_user_place";

	// public static final String ROOT = GWT.getHostPageBaseURL();
	public static String ROOT = "http://192.168.1.89:8080/emenu/";

	public static void CalculatorSize(boolean isCalculator) {
		if (isCalculator) {
			float perWidth = Window.getClientWidth() / WIDTH;
			float perHeight = Window.getClientHeight() / HEIGHT;
			if (perWidth > perHeight) {
				WIDTH = WIDTH * Window.getClientHeight() / HEIGHT;
				HEIGHT = Window.getClientHeight();
			} else {
				HEIGHT = HEIGHT * Window.getClientWidth() / WIDTH;
				WIDTH = Window.getClientWidth();
			}
		} else {
			WIDTH = Window.getClientWidth();
			HEIGHT = Window.getClientHeight();
		}
		WIDTH_APP = WIDTH + "px";
		HEIGHT_APP = HEIGHT + "px";
		System.out.println("WIDTH : " + WIDTH + " : " + "Height : " + HEIGHT);
	}

	public static void setBuildMobile(boolean b) {
		if (b) {
//			ROOT = "localhost:8080/emenu/";
//			ROOT = "http://192.168.10.20:8080/emenu/";
//			ROOT = "http://192.168.10.100:8080/emenu/";
//			 ROOT = "http://192.168.10.60:8080/emenu/";
//			ROOT = "http://192.168.1.20:8080/emenu/";
//			ROOT = "http://172.20.10.2:8080/emenu/";
			ROOT = "http://172.20.10.11:8080/emenu/";
		} else {
			ROOT = GWT.getHostPageBaseURL();
		}
	}
}
