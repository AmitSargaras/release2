/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/report/ConcReportNewGenerateCmd.java,v 1.7 2003/09/25 03:37:11 phtan Exp $
 */

package com.integrosys.cms.ui.report;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.StringUtil;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.batch.reports.OBReport;
import com.integrosys.cms.batch.reports.OBReportRequest;
import com.integrosys.cms.batch.reports.ReportConstants;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Generates a new concentration report.
 * 
 * @author $Author: phtan $
 * @version $Revision: 1.7 $
 * @since $Date: 2003/09/25 03:37:11 $ Tag: $Name: $
 */
public class ConcReportNewGenerateCmd extends ReportCommandAccessor implements ICommand, ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { ConcReportNewForm.MAPPER, "java.util.List", FORM_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {};
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List list = (List) map.get(ConcReportNewForm.MAPPER);

			DefaultLogger.debug(this, "Mapper Return :" + list);
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);

			Iterator i = list.iterator();
			while (i.hasNext()) {

				String str = (String) i.next();

				// Extract String token
				StringTokenizer st = new StringTokenizer(str, ",");

				// Get Report Master ID
				String MISReportID = st.nextToken();

				// Get Scope Parameter
				String country = st.nextToken();
				String params = "orig_country," + (country);

				OBReport obReport = getReportRequestManager().getReportDetailsByReportID(MISReportID);

				DefaultLogger.debug(this, "MISReportID:" + MISReportID + ", Report ID :" + obReport.getReportId());

				String titleName = this.generateCountryReportTitle(obReport.getTitle(), country);

				OBReportRequest req = new OBReportRequest();
				req.setStatus(ReportConstants.REPORT_STATUS_REQUESTED);
				req.setReportName(titleName);
				req.setRequestTime(new Date());
				req.setUserID(user.getUserID());
				req.setReportID(obReport.getReportId());
				req.setParameters(params);

				DefaultLogger.debug(this, params);
				getReportRequestManager().createReportRequest(req);
			}

		}
		catch (Throwable e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

	/**
	 * 
	 * @param title
	 * @param country
	 * @return
	 */

	private String generateCountryReportTitle(String title, String country) {
		DefaultLogger.debug(this, "Entering generateCountryReportTitle... ");
		String TaskTitle = "";
		if (country != null) {
			CountryList countries = CountryList.getInstance();
			String repl = countries.getCountryName(country);
			TaskTitle = StringUtil.replaceStringOnce(title, ReportConstants.REPLACE_SYMBOL, repl);
		}
		else {
			TaskTitle = title;
		}
		return TaskTitle;
	}

}
