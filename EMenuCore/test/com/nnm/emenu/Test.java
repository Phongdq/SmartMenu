package com.nnm.emenu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {
	public static void main(String[] args) {
		FileOutputStream fop = null;
		File file;
		String content = "This is the text content";

		try {
			File filedown = new File(
					"D:/Application Setup/ApacheTomcat8/webapps/emenu/upload/demo.png");
			file = new File("C:/Users/HP450/EMenu/demo2.png");
			fop = new FileOutputStream(file);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = new byte[(int) filedown.length()];
			FileInputStream fin = new FileInputStream(filedown);
			fin.read(contentInBytes);
			fin.close();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
