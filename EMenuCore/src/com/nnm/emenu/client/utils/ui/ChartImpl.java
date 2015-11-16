/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.apiloader.ApiLoaderOptions;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;
import com.nnm.emenu.client.ultis.CustomChartLoader;

/**
 * @author bizluvsakura
 *
 */
public class ChartImpl extends DockLayoutPanel {

	private ColumnChart chart;
	public ArrayList<String> represent;
	public ArrayList<String> hUnit;
	public Map<String, ArrayList<Long>> values;

	public int chart_column_width = 100;
	String title_chart = "";

	public ChartImpl(Unit unit, String title) {
		super(unit);
		this.title_chart = title;
		init();
	}

	public void setTitleChart(String title) {
		this.title_chart = title;
	}

	private void init() {
		represent = new ArrayList<String>();
		hUnit = new ArrayList<String>();
		values = new HashMap<String, ArrayList<Long>>();
	}

	public void initialize() {
		CustomChartLoader customChartLoader = new CustomChartLoader(
				ChartPackage.CORECHART);
		customChartLoader.getApiLoaderOption().setBaseDomain("/js");
		customChartLoader.loadApi(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				chart = new ColumnChart();
				add(chart);
				draw();
			}
		});
		// ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		// chartLoader.loadApi(new Runnable() {
		//
		// @Override
		// public void run() {
		// // Create and attach the chart
		// chart = new ColumnChart();
		// add(chart);
		// draw();
		// }
		// });
	}

	private void draw() {
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Year");
		for (int i = 0; i < represent.size(); i++) {
			dataTable.addColumn(ColumnType.NUMBER, represent.get(i));
		}
		dataTable.addRows(hUnit.size());
		for (int i = 0; i < hUnit.size(); i++) {
			dataTable.setValue(i, 0, String.valueOf(hUnit.get(i)));
		}
		for (int col = 0; col < represent.size(); col++) {
			ArrayList<Long> value = values.get(represent.get(col));
			for (int row = 0; row < value.size(); row++) {
				dataTable.setValue(row, col + 1, value.get(row));
			}
		}

		// Set options
		ColumnChartOptions options = ColumnChartOptions.create();
		// options.setFontName("Arial");
		options.setTitle(title_chart);

		// Draw the chart
		chart.draw(dataTable, options);
	}
}
