package com.nnm.emenu.server.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.nnm.emenu.server.dataengine.DataEngine;

/**
 * A Java servlet that handles file upload from client.
 * 
 * @author www.codejava.net
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String UPLOAD_DIRECTORY = "D:/Application Setup/ApacheTomcat8/webapps/emenu/upload";
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	/**
	 * handles file upload via HTTP POST method
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.print("Do Post \n");
		// checks if the request actually contains upload file
		if (!ServletFileUpload.isMultipartContent(request)) {
			System.out.println("REQUEST DOES NOT CONTAIN UPLOAD DATA");
			PrintWriter writer = response.getWriter();
			writer.println("Request does not contain upload data");
			writer.flush();
			return;
		}

		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// constructs the directory path to store upload file
		String uploadPath = getServletContext().getRealPath("")
				+ File.separator + UPLOAD_DIRECTORY;
		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			// parses the request's content to extract file data
			List formItems = upload.parseRequest(request);
			Iterator iter = formItems.iterator();

			// iterates over form's fields
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				// processes only fields that are not form fields
				if (!item.isFormField()) {
					System.out.println("item.getName() : " + item.getName());
					String fileName = new File(item.getName()).getName();
					String filePath = UPLOAD_DIRECTORY + File.separator
							+ fileName;
					System.out.println("File Path : " + filePath);
					File storeFile = new File(filePath);
					// saves the file on disk
					item.write(storeFile);

					String itemFieldName = item.getFieldName();
					if (itemFieldName.startsWith("C_")) {
						String code = itemFieldName.substring(2);
						System.out.println("code : " + code);
						DataEngine.getInstance()
								.updateImageFood(code, fileName);
					} else if (itemFieldName.startsWith("G_")) {
						String gendate = itemFieldName.substring(2);
						System.out.println("gendate : " + gendate);
						DataEngine.getInstance().updateFoodCategoryUrl(gendate,
								fileName);
					}
				}
			}
			request.setAttribute("message",
					"Upload has been done successfully!");
		} catch (Exception ex) {
			request.setAttribute("message",
					"There was an error: " + ex.getMessage());
		}
	}
}
