/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/concentrationreport/MakerReadConcReportCmd.java,v 1.4 2003/10/02 05:41:32 pooja Exp $
 */

package com.integrosys.cms.ui.concentrationreport;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.systemparameters.Constants;
import com.integrosys.cms.app.systemparameters.proxy.SystemParametersProxy;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.component.commondata.app.bus.CommonDataManagerException;
import com.integrosys.component.commondata.app.trx.IBusinessParameterGroupTrxValue;

/**
 * @author $Author: pooja $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/10/02 05:41:32 $ Tag: $Name: $
 */
public class MakerReadConcReportCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public MakerReadConcReportCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "SystemParametersTrxValue",
						"com.integrosys.component.commondata.app.trx.OBBusinessParameterGroupTrxValue", SERVICE_SCOPE },
				{ "BusinessParameterGroup", "com.integrosys.component.commondata.app.bus.OBBusinessParameterGroup",
						FORM_SCOPE }, { "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE }

		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String event = (String) map.get("event");
		String TrxId = (String) map.get("TrxId");
		DefaultLogger.debug(this, "Inside doExecute()  event= " + event + ", groupCode="
				+ Constants.CONC_REPORT_PARAMS_GROUP_CODE);
		try {

			SystemParametersProxy proxy = new SystemParametersProxy();

			IBusinessParameterGroupTrxValue systemParametersTrxVal = proxy
					.getBusinessParameterGroupByGroupCode(Constants.CONC_REPORT_PARAMS_GROUP_CODE);

			// if current status is other than ACTIVE & REJECTED, then show
			// workInProgress.
			// i.e. allow edit only if status is either ACTIVE or REJECTED
			if (!(TrxId != null)) {
				if (!((systemParametersTrxVal.getStatus().equals(ICMSConstant.STATE_ACTIVE)) || systemParametersTrxVal
						.getStatus().equals(ICMSConstant.STATE_ND))) {
					resultMap.put("wip", "wip");

				}
				else {
					resultMap.put("SystemParametersTrxValue", systemParametersTrxVal);
					if (CommonDataSingleton.getCodeCategoryLabels("TIME_FACTOR") != null) {
						resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels("TIME_FACTOR"));
					}
					if (CommonDataSingleton.getCodeCategoryValues("TIME_FACTOR") != null) {
						resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryLabels("TIME_FACTOR"));
					}
				}
				if (!event.equals("maker_add_edit_concreport_error")) {
					resultMap.put("BusinessParameterGroup", systemParametersTrxVal.getBusinessParameterGroup());
				}
				if (CommonDataSingleton.getCodeCategoryLabels("TIME_FACTOR") != null) {
					resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels("TIME_FACTOR"));
				}
				if (CommonDataSingleton.getCodeCategoryValues("TIME_FACTOR") != null) {
					resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryLabels("TIME_FACTOR"));
				}
			}
			else {
				if (systemParametersTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
					resultMap.put("SystemParametersTrxValue", systemParametersTrxVal);
					if (CommonDataSingleton.getCodeCategoryLabels("TIME_FACTOR") != null) {
						resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels("TIME_FACTOR"));
					}
					if (CommonDataSingleton.getCodeCategoryValues("TIME_FACTOR") != null) {
						resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryLabels("TIME_FACTOR"));
					}
					if (!event.equals("maker_add_edit_concreport_error")) {
						resultMap.put("BusinessParameterGroup", systemParametersTrxVal
								.getStagingBusinessParameterGroup());
					}
				}
				else {
					resultMap.put("wip", "wip");
				}
			}

		}
		catch (CommonDataManagerException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
