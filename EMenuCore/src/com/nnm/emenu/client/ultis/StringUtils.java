/**
 * 
 */
package com.nnm.emenu.client.ultis;

import java.text.DecimalFormat;

import com.google.gwt.i18n.client.NumberFormat;

/**
 * @author bizluvsakura
 *
 */
public class StringUtils {
	String pattern = "###,###.###";

	public static String getNumberString(long number) {
		String o = NumberFormat.getDecimalFormat().format(number);
		return o;
	}
	
	public static String getNumberString(double number) {
		String o = NumberFormat.getDecimalFormat().format(number);
		return o;
	}
}
