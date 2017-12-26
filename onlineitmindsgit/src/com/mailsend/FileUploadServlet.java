package com.mailsend;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "upload";

	// upload settings
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	/**
	 * Upon receiving file upload submission, parses the request to read upload
	 * data and saves the file on disk.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("enetered into servlet");
		response.setContentType("text/html");
		//PrintWriter out = response.getWriter();
		String name = "";
		String to = "";
		String no = "";
		String skills = "";
		String experience = "";
		String message = "";
		String filePath = "";

		if (!ServletFileUpload.isMultipartContent(request)) {
			// if not, we stop here
			PrintWriter writer = response.getWriter();
			writer.println("Error: Form must has enctype=multipart/form-data.");
			writer.flush();
			return;
		}

		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// sets memory threshold - beyond which files are stored in disk
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// sets temporary location to store files
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);

		// sets maximum size of upload file
		upload.setFileSizeMax(MAX_FILE_SIZE);

		// sets maximum size of request (include file + form data)
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// constructs the directory path to store upload file
		// this path is relative to application's directory
		String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			// parses the request's content to extract file data
			@SuppressWarnings("unchecked")
			List<FileItem> formItems = upload.parseRequest(request);

			if (formItems != null && formItems.size() > 0) {
				// iterates over form's fields
				for (FileItem item : formItems) {
					// processes only fields that are not form fields
					if (!item.isFormField()) {
						String fileName = new File(item.getName()).getName();
						filePath = uploadPath + File.separator + fileName;
						File storeFile = new File(filePath);
						System.out.println("file location  " + filePath);
						// saves the file on disk
						item.write(storeFile);
						// request.setAttribute("message", "Upload has been done
						// successfully!");

						// String subject=request.getParameter("subject");
						String msg = request.getParameter("message");
						// Mailer.sendMailAttachment(to, experience, msg, name,
						// no, skills, filePath);
						// out.print("message has been sent successfully");
						//request.getRequestDispatcher("/contact-us.html").forward(request, response);
					}

					else {

						String field = item.getFieldName();
						// String value=item.getString();
						// System.out.println("field="+value);
						if (field.equalsIgnoreCase("name")) {
							name = (String) item.getString();
							System.out.println("UserName is:" + name);
						}

						if (field.equalsIgnoreCase("to")) {
							to = (String) item.getString();
							System.out.println("email id is:" + to);
						}

						if (field.equalsIgnoreCase("no")) {
							no = (String) item.getString();
							System.out.println("phone no is:" + no);
						}

						if (field.equalsIgnoreCase("skills")) {
							skills = (String) item.getString();
							System.out.println("skills is:" + skills);
						}

						if (field.equalsIgnoreCase("experience")) {
							experience = (String) item.getString();
							System.out.println("experience is:" + experience);
						}

						if (field.equalsIgnoreCase("message")) {
							message = (String) item.getString();
							System.out.println("message is:" + message);
						}
					}

				}
			}
			Mailer.sendMailAttachment(to, experience, message, name, no, skills, filePath);
			// out.print("message has been sent successfully");
			request.getRequestDispatcher("/blog.html").forward(request, response);

		} catch (Exception ex) {
			System.err.println(ex);
		}

		// redirects client to message page
		// getServletContext().getRequestDispatcher("/message.jsp").forward(request,
		// response);

	}
}