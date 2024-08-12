package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IDevelopmentDoc;
import com.integrosys.cms.app.bridgingloan.bus.IProjectSchedule;
import com.integrosys.cms.app.bridgingloan.bus.OBDevelopmentDoc;
import com.integrosys.cms.app.bridgingloan.bus.OBProjectSchedule;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 3, 2007 Time: 12:15:11 PM To
 * change this template use File | Settings | File Templates.
 */
public class ReadDevelopmentDocCommand extends AbstractCommand {

	public ReadDevelopmentDocCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { "projectScheduleIndex", "java.lang.String", REQUEST_SCOPE },
				{ "progressStage", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "commonRef", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", FORM_SCOPE },
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", REQUEST_SCOPE },
				{ "objStagingProjectSchedule", "com.integrosys.cms.app.bridgingloan.bus.OBProjectSchedule",
						REQUEST_SCOPE },
				{ "objActualProjectSchedule", "com.integrosys.cms.app.bridgingloan.bus.OBProjectSchedule",
						REQUEST_SCOPE },
				{ "objStagingDevelopmentDoc", "com.integrosys.cms.app.bridgingloan.bus.OBDevelopmentDoc", REQUEST_SCOPE },
				{ "objActualDevelopmentDoc", "com.integrosys.cms.app.bridgingloan.bus.OBDevelopmentDoc", REQUEST_SCOPE },
				{ "projectScheduleIndex", "java.lang.String", SERVICE_SCOPE },
				{ "progressStage", "java.lang.String", SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside ReadDevelopmentDocCommand doExecute()");

		try {
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
			DefaultLogger.debug(this, "trxValue=" + trxValue);

			IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();
			DefaultLogger.debug(this, "objBridgingLoan=" + objBridgingLoan);

			IProjectSchedule[] projectScheduleList = trxValue.getStagingBridgingLoan().getProjectScheduleList();
			DefaultLogger.debug(this, "objStagingProjectSchedule=" + projectScheduleList);

			IProjectSchedule[] projectScheduleActualList = null;
			if (trxValue.getBridgingLoan() != null) { // actual will be null if
														// this is new record
				projectScheduleActualList = trxValue.getBridgingLoan().getProjectScheduleList();
			}

			if ((projectScheduleList != null) && (projectScheduleList.length > 0)) {
				String progressStage = (String) map.get("progressStage");
				DefaultLogger.debug(this, "progressStage=" + progressStage);
				int projectScheduleIndex = 0;

				// Check for the projectSchedule index in the list based on
				// progress stage
				for (int i = 0; i < objBridgingLoan.getProjectScheduleList().length; i++) {
					if ((projectScheduleList[i].getDevelopmentDocList() != null)
							&& projectScheduleList[i].getProgressStage().equals(progressStage)) {
						projectScheduleIndex = i;
					}
				}

				IDevelopmentDoc[] developmentDocList = projectScheduleList[projectScheduleIndex]
						.getDevelopmentDocList();
				IDevelopmentDoc[] developmentDocActualList = null;
				if (trxValue.getBridgingLoan() != null) { // actual will be null
															// if this is new
															// record
					if (projectScheduleActualList.length > projectScheduleIndex) {
						developmentDocActualList = projectScheduleActualList[projectScheduleIndex]
								.getDevelopmentDocList();
					}
				}

				String event = (String) map.get("event");
				DefaultLogger.debug(this, "event=" + event);
				if (DevelopmentDocAction.EVENT_VIEW.equals(event)
						|| DevelopmentDocAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)
						|| DevelopmentDocAction.EVENT_MAKER_PREPARE_DELETE.equals(event)) {
					resultMap.put("objBridgingLoan", objBridgingLoan);
				}
				else { // Checker
					OBProjectSchedule objStagingProjectSchedule = null;
					OBProjectSchedule objActualProjectSchedule = null;
					OBDevelopmentDoc objStagingDevelopmentDoc = null;
					OBDevelopmentDoc objActualDevelopmentDoc = null;
					String commonRef = (String) map.get("commonRef");

					DefaultLogger.debug(this, "projectScheduleList=" + projectScheduleList);
					for (int i = 0; i < projectScheduleList.length; i++) {
						objStagingProjectSchedule = (OBProjectSchedule) projectScheduleList[i];
					}
					DefaultLogger.debug(this, "projectScheduleActualList=" + projectScheduleActualList);
					if (projectScheduleActualList != null) {
						for (int i = 0; i < projectScheduleActualList.length; i++) {
							objActualProjectSchedule = (OBProjectSchedule) projectScheduleActualList[i];
						}
					}

					for (int i = 0; i < developmentDocList.length; i++) {
						if (developmentDocList[i].getCommonRef() == Long.parseLong(commonRef)) {
							objStagingDevelopmentDoc = (OBDevelopmentDoc) developmentDocList[i];
						}
					}
					if (developmentDocActualList != null) {
						for (int i = 0; i < developmentDocActualList.length; i++) {
							if (developmentDocActualList[i].getCommonRef() == Long.parseLong(commonRef)) {
								objActualDevelopmentDoc = (OBDevelopmentDoc) developmentDocActualList[i];
							}
						}
					}
					resultMap.put("objStagingProjectSchedule", objStagingProjectSchedule); // staging
																							// data
					resultMap.put("objActualProjectSchedule", objActualProjectSchedule); // actual
																							// data
					resultMap.put("objStagingDevelopmentDoc", objStagingDevelopmentDoc); // staging
																							// data
					resultMap.put("objActualDevelopmentDoc", objActualDevelopmentDoc); // actual
																						// data
				}
				resultMap.put("progressStage", progressStage);
				resultMap.put("projectScheduleIndex", Integer.toString(projectScheduleIndex));
			}
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			e.printStackTrace();
			throw new CommandProcessingException(e.toString());
		}
	}
}