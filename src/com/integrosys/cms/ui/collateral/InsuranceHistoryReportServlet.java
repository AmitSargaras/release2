package com.integrosys.cms.ui.collateral;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

public class InsuranceHistoryReportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String docUrl = PropertyManager.getValue("insurance.history.report.path");
		String docFileName = request.getParameter("docFileName");
		
		if(StringUtils.isEmpty(docUrl) || StringUtils.isEmpty(docFileName)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid path["+docUrl+"] or file["+docFileName+"]");
			return;
		}
		
		File file = new File(docUrl);
		if(file.exists()) {
			retrieveFile(file, docUrl, docFileName, response);
		}
	}
	
	private void retrieveFile(File file, String docUrl, String docFileName, HttpServletResponse response) throws IOException {
		URL url = null;
		try {
			url = file.toURI().toURL();
		}catch(IOException e) {
			DefaultLogger.error(this, "Error converting to url from path: "+docUrl, e);
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Error translating file path, "+e.getMessage());
		}
		
		InputStream inputStream = null;		
		try {
			URLConnection conn = url.openConnection();
			conn.connect();
			
			response.setContentType(conn.getContentType());
			response.setHeader("Content-Type", conn.getContentType());
			response.setHeader("Content-Disposition", "attachment;filename="+docFileName);
			ServletOutputStream outputStream = response.getOutputStream();
			inputStream =  url.openStream();
			IOUtils.copy(inputStream, outputStream);
			
		} catch (IOException e) {
			DefaultLogger.error(this, "Error opening connection url: "+url, e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error downloading file: "+e.getMessage());
		}finally {
			inputStream.close();
		}
	}
}
