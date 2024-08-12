package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IProjectSchedule;
import com.integrosys.cms.app.bridgingloan.bus.OBProjectSchedule;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 3, 2007 Time: 12:09:39 PM To
 * change this template use File | Settings | File Templates.
 */
public class PrepareDevelopmentDocCommand extends AbstractCommand {

	public PrepareDevelopmentDocCommand() {
		DefaultLogger.debug("\n----------------------------->", "Entering PrepareDevelopmentDocCommand()");
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { "progressStage", "java.lang.String", REQUEST_SCOPE },
				{ "projectScheduleIndex", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE },
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", REQUEST_SCOPE },
				{ "progressStageLabels", "java.util.List", REQUEST_SCOPE },
				{ "progressStageValues", "java.util.List", REQUEST_SCOPE },
				{ "progressStage", "java.lang.String", SERVICE_SCOPE },
				{ "projectScheduleIndex", "java.lang.String", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside PrepareDevelopmentDocCommand doExecute()");

		try {
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
			DefaultLogger.debug(this, "trxValue=" + trxValue);

			if (trxValue != null) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();
				DefaultLogger.debug(this, "objBridgingLoan=" + objBridgingLoan);

				IProjectSchedule[] projectScheduleList = objBridgingLoan.getProjectScheduleList();
				DefaultLogger.debug(this, "projectScheduleList=" + projectScheduleList);
				List progressStageValues = new ArrayList();
				List progressStageLabels = new ArrayList();

				if ((projectScheduleList != null) && (projectScheduleList.length > 0)) {
					for (int i = 0; i < projectScheduleList.length; i++) {
						OBProjectSchedule objProjectSchedule = (OBProjectSchedule) projectScheduleList[i];
						progressStageValues.add(objProjectSchedule.getProgressStage());
						progressStageLabels.add(objProjectSchedule.getProgressStage());
					}
				}

				if ((trxValue != null) && (trxValue.getStagingBridgingLoan() != null)
						&& (trxValue.getStagingBridgingLoan().getProjectScheduleList() != null)) {

					HashMap projectScheduleMap = new HashMap();
					for (int i = 0; i < projectScheduleList.length; i++) {
						if (!projectScheduleList[i].getIsDeletedInd()) { // not
																			// include
																			// deleted
							projectScheduleMap.put(projectScheduleList[i].getProgressStage(), projectScheduleList[i]
									.getProgressStage());
						}
					}
					HashMap hm = addToList(progressStageValues, progressStageLabels, projectScheduleMap);
					progressStageValues = (List) hm.get("progressStageValues");
					progressStageLabels = (List) hm.get("progressStageLabels");
				}
				resultMap.put("progressStageLabels", progressStageValues);
				resultMap.put("progressStageValues", progressStageLabels);
				resultMap.put("objBridgingLoan", objBridgingLoan);
				resultMap.put("progressStage", (String) map.get("progressStage"));
				resultMap.put("projectScheduleIndex", (String) map.get("projectScheduleIndex"));
			}
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}

	public HashMap addToList(List values, List labels, HashMap hm) {
		HashMap returnHm = new HashMap();
		List progressStageValues = new ArrayList();
		List progressStageLabels = new ArrayList();

		try {
			for (int i = 0; i < values.size(); i++) {
				progressStageValues.add(i, hm.get(values.get(i)));
				progressStageLabels.add(i, hm.get(labels.get(i)));
			}
			returnHm.put("progressStageValues", progressStageValues);
			returnHm.put("progressStageLabels", progressStageLabels);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return returnHm;
	}
}