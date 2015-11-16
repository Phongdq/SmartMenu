/**
 * 
 */
package com.nnm.emenu.client.ultis;

import com.google.gwt.i18n.client.LocaleInfo;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.apiloader.ApiLoader;
import com.googlecode.gwt.charts.client.apiloader.ApiLoaderOptions;

/**
 * @author bizluvsakura
 *
 */
public class CustomChartLoader extends ChartLoader{
	
	private static final String API_NAME = "visualization";
	private static final String API_VERSION = "1.1";

	private ChartPackage[] packages;
	private String language;
	private String version;
	private ApiLoaderOptions options = ApiLoaderOptions.create();

	/**
	 * Creates a chart loader with the specified packages.
	 * 
	 * @param packages a list of ChartPackage's
	 */
	public CustomChartLoader(ChartPackage... packages) {
		setPackages(packages);
		setLanguage(LocaleInfo.getCurrentLocale().getLocaleName());
		setVersion(API_VERSION);
	}
	
	public ApiLoaderOptions getApiLoaderOption(){
		return options;
	}

	/**
	 * Returns the current language.
	 * 
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Returns the current packages.
	 * 
	 * @return the packages
	 */
	public ChartPackage[] getPackages() {
		return packages;
	}

	/**
	 * Returns the current version.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Loads requested API libraries and calls an handler after finished.
	 * 
	 * @param callback the handler to be called
	 */
	public void loadApi(Runnable callback) {
		String[] packagesArray = new String[packages.length];
		for (int i = 0; i < packages.length; i++) {
			packagesArray[i] = packages[i].getName();
		}
		options.setPackages(packagesArray);
		if (language != null) {
			options.setLanguage(language);
		}
		CustomApiLoader.loadApi(API_NAME, version, callback, options);
	}
	/**
	 * Sets the display language for the charts.
	 * Must be set before {@link #loadApi(Runnable)} is called.
	 * 
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Sets the packages for loading
	 * Must be set before {@link #loadApi(Runnable)} is called.
	 * 
	 * @param packages the packages to load
	 */
	public void setPackages(ChartPackage... packages) {
		this.packages = packages;
	}

	/**
	 * Sets the api version for loading
	 * Must be set before {@link #loadApi(Runnable)} is called.
	 * 
	 * @param version the version to use
	 */
	public void setVersion(String version) {
		this.version = version;
	}

}
