package com.nnm.emenu.client.ultis;

import java.sql.Timestamp;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

public class DateTime {

	public static String FORMAT = "yyyy-MM-dd";
	public static String FORMAT_FULL = "yyyy-MM-dd hh:mm:ss";

	public static Date getDate(long time) {
		return new Date(time);
	}

	public static String getStringDate(Date date, String format) {
		return DateTimeFormat.getFormat(format).format(date);
	}

	public static String getStringDate(long time, String format) {
		return DateTimeFormat.getFormat(format).format(new Date(time));
	}

	public static Date getDate(String time, String format) {
		return DateTimeFormat.getFormat(format).parse(time);
	}

	// public static String getStringDate(long time, String format) {
	// return new SimpleDateFormat(format).format(new Date(time));
	// }

	// public static boolean isDateValid(String date) {
	// try {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	// df.setLenient(false);
	// df.parse(date);
	// return true;
	// } catch (Exception e) {
	// return false;
	// }
	// }

	public static boolean isBefore(long timestart, long timeend, int day) {
		if (timeend - 86400 * 1000 * day > timestart) {
			return true;
		} else {
			return false;
		}
	}

	public static Date getDateBefore(int day) {
		return new Date(new Date().getTime() - 86400 * day * 1000);
	}

	public static Date getDateAfter(int day) {
		return new Date(new Date().getTime() + 86400 * day * 1000);
	}

	public static String getDateBefore(String format, int day) {
		long last_date = new Date().getTime() - 86400 * 1000 * day;
		return getStringDate(last_date, format);
	}

	public static String getDateAfter(String format, int day) {
		long last_date = new Date().getTime() + 86400 * 1000 * day;
		return getStringDate(last_date, format);
	}

	public static void main(String[] args) {
	}

	public static String getCusDate(long time) {
		if (getStringDate(time, "dd-MM").equals(getCurrentDate("dd-MM"))) {
			return getStringDate(time, "HH:mm");
		} else {
			return getStringDate(time, "dd-MM");
		}
	}

	public static String getCurrentDate(String format) {
		return DateTimeFormat.getFormat(format).format(new Date());
	}

	public static String getCurrentDate(PredefinedFormat format) {
		return DateTimeFormat.getFormat(format).format(new Date());
	}

	// public static String getCurrentDate(String format) {
	// return new SimpleDateFormat(format).format(Calendar.getInstance()
	// .getTime());
	// }

	// public static String getDateBefor(String format, int day) {
	// Calendar cal = Calendar.getInstance();
	// cal.add(Calendar.DAY_OF_MONTH, -day);
	// return new SimpleDateFormat(format).format(cal.getTime());
	// }
	//
	// public static String getDateBefor(int day) {
	// Calendar cal = Calendar.getInstance();
	// cal.add(Calendar.DAY_OF_MONTH, -day);
	// return new SimpleDateFormat(FORMAT).format(cal.getTime());
	// }

	// public static String getDateAfter(String format, int day) {
	// Calendar cal = Calendar.getInstance();
	// cal.add(Calendar.DAY_OF_MONTH, day);
	// return new SimpleDateFormat(format).format(cal.getTime());
	// }

	public static String getFullTime(Timestamp time) {
		String result = DateTime.getStringDate(time.getTime(),
				"yyyy-MM-dd HH:mm:ss");
		System.out.println("Custom Time : " + result);
		return result;
	}

	public static String getTime(Timestamp time, String format) {
		String result = DateTime.getStringDate(time.getTime(), format);
		return result;
	}
}
