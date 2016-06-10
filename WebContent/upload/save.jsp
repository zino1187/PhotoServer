<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.FileUploadException"%>
<%@page import="java.io.InputStream"%>
<%@page import="org.apache.commons.io.FilenameUtils"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String dir = application.getRealPath("/copy");
	System.out.println(dir);

	try {
		List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

		for (FileItem item : items) {
			if (item.isFormField()) {
				String fieldName = item.getFieldName();
				String fieldValue = item.getString();

				if (fieldName.equals("title")) {
					System.out.println("넘겨받은 제목은" + fieldValue);
				}
			} else {
				//바이너리 파일인 경우..
				// Process form file field (input type="file").
				String fieldName = item.getFieldName();
				String fileName = FilenameUtils.getName(item.getName());
				InputStream is = item.getInputStream();

				FileOutputStream fos = new FileOutputStream(dir + File.separator + "copy.jpg");

				int data = -1;

				while (true) {
					data = is.read();
					if (data == -1)
						break;
					fos.write(data);
					fos.flush();
				}
				fos.close();
				System.out.println(dir + "에 저장완료");
			}
		}
	} catch (FileUploadException e) {
		throw new ServletException("Cannot parse multipart request.", e);
	}
%>
