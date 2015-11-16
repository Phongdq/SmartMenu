package com.nnm.emenu.server.utils;

import java.io.BufferedReader;
import java.io.FileReader;

public class Config {
	public static String DB_ADDRESS;
	public static String DB_PORT;
	public static String DB_USERNAME;
	public static String DB_PASSWORD;
	public static String DB_NAME;
	public static String DB_MAX_CONNECTION;
	public static String DB_CLEAR_PERIOD;
	public static String DB_PATH_LOG;
	public static String SERVER_DIRECTORY;

	private static Config INSTANCE;

	public Config() {
		String fileName = "D:/Application Setup/ApacheTomcat8/webapps/emenu/config/config.txt";
		try {
			// Create object of FileReader
			FileReader inputFile = new FileReader(fileName);

			// Instantiate the BufferedReader Class
			BufferedReader bufferReader = new BufferedReader(inputFile);

			// Variable to hold the one line data
			String line;

			// Read file line by line and print on the console
			while ((line = bufferReader.readLine()) != null) {
				if (line.startsWith("dbAdress")) {
					DB_ADDRESS = line.substring(line.indexOf(":") + 1);
				} else if (line.startsWith("dbPort")) {
					DB_PORT = line.substring(line.indexOf(":") + 1);
				} else if (line.startsWith("dbUserName")) {
					DB_USERNAME = line.substring(line.indexOf(":") + 1);
				} else if (line.startsWith("dbPassWord")) {
					DB_PASSWORD = line.substring(line.indexOf(":") + 1);
				} else if (line.startsWith("dbName")) {
					DB_NAME = line.substring(line.indexOf(":") + 1);
				} else if (line.startsWith("dbMaxConnection")) {
					DB_MAX_CONNECTION = line.substring(line.indexOf(":") + 1);
				} else if (line.startsWith("dbClearPeriod")) {
					DB_CLEAR_PERIOD = line.substring(line.indexOf(":") + 1);
				} else if (line.startsWith("dbPathLog")) {
					DB_PATH_LOG = line.substring(line.indexOf(":") + 1);
				}
				System.out.println(line);
			}
			// Close the buffer reader
			bufferReader.close();
		} catch (Exception e) {
			System.out.println("Error while reading file line by line:"
					+ e.getMessage());
		}
	}

	public static void loadConfig() {
		INSTANCE = new Config();
	}

	public Config getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Config();
		}
		return INSTANCE;
	}
}
