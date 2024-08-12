package com.integrosys.cms.ui.eventmonitor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.batch.factory.BatchJobFactory;

public class EventMonitorServlet extends HttpServlet {

	private static final long serialVersionUID = 7152989253063804477L;

	private BatchJobFactory batchJobFactory;

	private WebApplicationContext webApplicationContext;

	public void setBatchJobFactory(BatchJobFactory batchJobFactory) {
		this.batchJobFactory = batchJobFactory;
	}

	public BatchJobFactory getBatchJobFactory() {
		return batchJobFactory;
	}

	public void init() throws ServletException {
		webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		webApplicationContext.getAutowireCapableBeanFactory().autowireBeanProperties(this,
				AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
	}

	public void service(ServletRequest req, ServletResponse res) throws IOException {
		DefaultLogger.debug(this, "begin service...");
		try {
			String[] args = (String[]) new ObjectInputStream(req.getInputStream()).readObject();
			getBatchJobFactory().run(args);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		PrintWriter out = res.getWriter();
	//	out.println("Sucessfully!");
		out.flush();
		out.close();
	}
}
