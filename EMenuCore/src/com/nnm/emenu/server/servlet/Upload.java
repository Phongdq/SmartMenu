package com.nnm.emenu.server.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

public class Upload extends HttpServlet {
	private static final String UPLOAD_DIRECTORY = "D:\\DA\\Image\\";
	private final String rootPath = System.getProperty("catalina.home");
	private final String parentPath = rootPath + File.separator + "webapps"
			+ File.separator + "masterordering" + File.separator + "image";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("DO POST");
		// process only multipart requests
		if (ServletFileUpload.isMultipartContent(req)) {

			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			try {
				List<FileItem> items = upload.parseRequest(req);
				System.out.println("items size : " + items.size());
				for (FileItem item : items) {
					// process only file upload - discard other form item types
					if (item.isFormField()) {
						continue;
					}

					String fileName = item.getName();
					System.out.println("file Name : " + fileName);
					// get only the file name not whole path
					if (fileName != null) {
						fileName = FilenameUtils.getName(fileName);
					}

					File uploadedFile = new File(parentPath, fileName);
					System.out.println("aaaaaaa");
					// uploadedFile.setWritable(true);
					// uploadedFile.setExecutable(true);
					// uploadedFile.setReadable(true);
					if (uploadedFile.createNewFile()) {
						System.out.println("create new file success");
						item.write(uploadedFile);
						System.out.println("Write file success");
						resp.setStatus(HttpServletResponse.SC_CREATED);
						resp.getWriter().print(
								"The file was created successfully.");
						resp.flushBuffer();
					} else
						throw new IOException(
								"The file already exists in repository.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(
						HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"An error occurred while creating the file : "
								+ e.getMessage());
			}

		} else {
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
					"Request contents type is not supported by the servlet.");
		}
	}
}
