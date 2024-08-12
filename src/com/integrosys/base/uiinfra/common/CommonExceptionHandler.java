package com.integrosys.base.uiinfra.common;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.exception.ExceptionUtil;
import com.integrosys.base.techinfra.exception.WorkflowConcurrentUpdateException;

/**
 * <p>
 * Struts Exception Handler. To make sure there is always a page to be forwarded
 * to for any exception.
 * 
 * <p>
 * Currently for concurrent update exception, will be forwarded to
 * <code>concurrent_error</code>. Others kinds of exception, just follow
 * <code>ui_error</code>.
 * 
 * @author Chong Jun Yong
 * @since 10.09.2008
 */
public class CommonExceptionHandler extends ExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

	public ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance,
			HttpServletRequest request, HttpServletResponse response) throws ServletException {

		ActionForward actionForward = mapping.findForward("ui_error");

		Throwable rootCause = ExceptionUtil.getRootCause(ex);
		if (rootCause != null) {
			if (rootCause instanceof ConcurrentUpdateException
					|| rootCause instanceof WorkflowConcurrentUpdateException) {
				actionForward = mapping.findForward("concurrent_error");
			}
		}

		if (ex instanceof ConcurrentUpdateException) {
			actionForward = mapping.findForward("concurrent_error");
		}
		else {
			logger.warn("[" + ex.getClass() + "] exception encountered after running commands, forward to 'ui_error'",
					ex);
		}

		request.setAttribute(ICommonEventConstant.ACTION_PROPERTIES, ((IntegroActionForward) actionForward)
				.getActionProp());

		return actionForward;
	}
}