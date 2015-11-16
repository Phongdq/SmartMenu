package com.nnm.emenu.server.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.nnm.emenu.shared.SumMoney;

public class DateTime {

	public static String FORMAT = "yyyy-MM-dd";

	public static Date getDate(long time) {
		return new Date(time);
	}

	public static String getStringDate(long time, String format) {
		return new SimpleDateFormat(format).format(new Date(time));
	}

	public static boolean isDateValid(String date) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false);
			df.parse(date);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isBefore(long timestart, long timeend, int day) {
		if (timeend - 86400 * 1000 * day > timestart) {
			return true;
		} else {
			return false;
		}
	}

	public static Date getDate(String time) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String changeDateFormat(String time, String current_format,
			String last_format) {
		try {
			Date date = new SimpleDateFormat(current_format).parse(time);
			return new SimpleDateFormat(last_format).format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getCusDate(long time) {
		if (getStringDate(time, "dd-MM").equals(getCurrentDate("dd-MM"))) {
			return getStringDate(time, "HH:mm");
		} else {
			return getStringDate(time, "dd-MM");
		}
	}

	public static void main(String[] args) {
		// test();
		Date date = getDate("2015-05-03");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		System.out.println("week : " + cal.get(Calendar.WEEK_OF_YEAR));
	}

	static void test() {
		List<SumMoney> listSumMoney = new ArrayList<SumMoney>();
		listSumMoney.add(new SumMoney("2", 500000));
		listSumMoney.add(new SumMoney("4", 300000));
		ArrayList<String> listTime = DateTime.getListMonth("2015-01-10",
				"2015-12-06");
		List<SumMoney> result = new ArrayList<SumMoney>();
		int index = 0;
		for (int i = 0; i < listTime.size(); i++) {
			if (!checkContainTime(listSumMoney, listTime.get(i).substring(0, 1))) {
				SumMoney sum = new SumMoney();
				sum.setTime(listTime.get(i));
				sum.setSumMoney(0);
				result.add(sum);
			} else {
				SumMoney sum = new SumMoney();
				sum.setTime(listTime.get(i));
				sum.setSumMoney(listSumMoney.get(index).getSumMoney());
				result.add(sum);
				index++;
			}
		}
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i).getTime() + "::"
					+ result.get(i).getSumMoney());
		}
	}

	static boolean checkContainTime(List<SumMoney> listSumMoney, String time) {
		for (int i = 0; i < listSumMoney.size(); i++) {
			if (listSumMoney.get(i).getTime().equals(time)) {
				return true;
			}
		}
		return false;
	}

	public static ArrayList<String> getListDate(String time_start,
			String time_end) {
		ArrayList<String> result = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(FORMAT);
		cal.setTime(getDate(time_start));
		while (cal.getTime().before(getDateAfter(time_end, 1))) {
			result.add(format.format(cal.getTime()));
			System.out.println("time : " + format.format(cal.getTime()));
			cal.add(Calendar.DATE, 1);
		}
		return result;
	}

	public static ArrayList<String> getListWeek(String time_start,
			String time_end) {
		ArrayList<String> result = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(2);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM");
		cal.setTime(getDate(time_start));
		if (cal.get(Calendar.DAY_OF_WEEK) != 2) {
			result.add(format.format(cal.getTime()) + " - ");
		}
		while (cal.getTime().before(getDateAfter(time_end, 1))) {
			if (cal.get(Calendar.DAY_OF_WEEK) == 2) {
				result.add(format.format(cal.getTime()) + " - ");
			}
			if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
				String week = result.get(result.size() - 1);
				week += format.format(cal.getTime());
				week += "::" + cal.get(Calendar.WEEK_OF_YEAR);
				result.set(result.size() - 1, week);
			}
			cal.add(Calendar.DATE, 1);
		}
		cal.setTime(getDate(time_end));
		if (cal.get(Calendar.DAY_OF_WEEK) != 1) {
			String week = result.get(result.size() - 1);
			week += format.format(cal.getTime());
			week += "::" + cal.get(Calendar.WEEK_OF_YEAR);
			result.set(result.size() - 1, week);
		}
		System.out.println("==========" + result.get(result.size() - 1));
		return result;
	}

	public static ArrayList<String> getListMonth(String time_start,
			String time_end) {
		ArrayList<String> result = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(time_start));
		while (cal.getTime().before(getDateAfter(time_end, 1))) {
			if (!result.contains(cal.get(Calendar.MONTH) + 1 + "/"
					+ cal.get(Calendar.YEAR))) {
				result.add(cal.get(Calendar.MONTH) + 1 + "/"
						+ cal.get(Calendar.YEAR));
			}
			cal.add(Calendar.DATE, 1);
		}
		return result;
	}

	public static String getCurrentDate(String format) {
		return new SimpleDateFormat(format).format(Calendar.getInstance()
				.getTime());
	}

	public static String getDateBefor(String format, int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -day);
		return new SimpleDateFormat(format).format(cal.getTime());
	}

	public static String getStringDateBefor(int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -day);
		return new SimpleDateFormat(FORMAT).format(cal.getTime());
	}

	public static String getStringDateAfter(String format, int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, day);
		return new SimpleDateFormat(format).format(cal.getTime());
	}

	public static Date getDateAfter(String time, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(time));
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

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
