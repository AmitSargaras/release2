package com.integrosys.cms.app.customer.bus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

import com.integrosys.base.techinfra.exception.OFAException;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

public class TraceDisabledException extends OFAException{

	
//	public TraceDisabledException() throws ServletException,Exception {
//		ActionMapping mapping = new ActionMapping();
//		execute(mapping);
//	}
	public TraceDisabledException(String msg){
	}
	public TraceDisabledException(ActionMapping actionMapping,HttpServletRequest request, HttpServletResponse response) throws ServletException{
		super();
		execute(actionMapping,request,response);
	}
	
	
	public ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance,
			HttpServletRequest request, HttpServletResponse response) throws ServletException {
		org.apache.struts.action.ActionMapping am=(org.apache.struts.action.ActionMapping
				)request.getAttribute("org.apache.struts.action.mapping.instance");
		ActionForward actionForward = mapping.findForward("ui_error");
		return actionForward;
	}
	
	
	public ActionForward execute(ActionMapping actionMapping,HttpServletRequest request, HttpServletResponse response) throws ServletException {
		
		String fullWelcomeURL = "http://" + request.getServerName() + ":" + "7001" + request.getContextPath() + "/system/system_error.jsp";
		String normalPort = PropertyManager.getValue("system.normal.port");
		System.out.println("http://" + request.getServerName() + ":" + normalPort + request.getContextPath()
		+"/system/error_log_popup_detail.jsp");
		System.out.println("fullWelcomeURL==="+fullWelcomeURL);
		ActionForward actionForward = null;
		return new ActionForward(fullWelcomeURL, false);
	}
	
	
//	public ActionForward execute(ActionMapping mapping) throws ServletException {
//
//		ActionMapping mapping1 = new ActionMapping();
//		mapping1.setForward("ui_error");
//		ActionForward actionForward = new ActionForward();
//		actionForward = mapping1.findForward("ui_error");
//		System.out.println("actionForward == "+actionForward);
//		return actionForward;
//	}
}