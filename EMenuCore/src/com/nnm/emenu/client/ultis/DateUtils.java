/**
 * 
 */
package com.nnm.emenu.client.ultis;

import java.util.Date;

/**
 * @author bizluvsakura
 *
 */
public class DateUtils {
	static DateUtils INSTANCE;

	Date date = new Date();

	public DateUtils() {
	}

	public long getDateLong() {
		return date.getTime();
	}

	public static DateUtils getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DateUtils();
		}
		return INSTANCE;
	}
	
	public String getDateBefore(int day){
		String date = "";
		
		return date;
	}
}
