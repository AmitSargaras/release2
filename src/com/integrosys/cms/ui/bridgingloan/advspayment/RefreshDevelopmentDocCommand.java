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
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 11, 2007 Time: 4:00:54 PM To
 * change this template use File | Settings | File Templates.
 */
public class RefreshDevelopmentDocCommand extends AbstractCommand {

	public RefreshDevelopmentDocCommand() {
		DefaultLogger.debug("\n----------------------------->", "Entering RefreshDevelopmentDocCommand()");
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE },
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", FORM_SCOPE },
				{ "progressStage", "java.lang.String", REQUEST_SCOPE },
				{ "projectScheduleIndex", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "commonRef", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", REQUEST_SCOPE },
				{ "objProjectSchedule", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", REQUEST_SCOPE },
				{ "objDevelopmentDoc", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", REQUEST_SCOPE },
				{ "objActualDevelopmentDoc", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", REQUEST_SCOPE },
				{ "progressStage", "java.lang.String", SERVICE_SCOPE },
				{ "projectScheduleIndex", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside RefreshDevelopmentDocCommand doExecute()");

		try {
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
			DefaultLogger.debug(this, "trxValue=" + trxValue);

			IProjectSchedule[] objProjectSchedule = trxValue.getStagingBridgingLoan().getProjectScheduleList();
			DefaultLogger.debug(this, "objProjectSchedule=" + objProjectSchedule);
			int projectScheduleIndex = 0;

			if ((objProjectSchedule != null) && (objProjectSchedule.length > 0)) {
				projectScheduleIndex = Integer.parseInt((String) map.get("projectScheduleIndex"));
				DefaultLogger.debug(this, "projectScheduleIndex=" + projectScheduleIndex);

				if (projectScheduleIndex != ICMSConstant.INT_INVALID_VALUE) {
					IDevelopmentDoc[] developmentDocList = objProjectSchedule[projectScheduleIndex]
							.getDevelopmentDocList();
					IDevelopmentDoc[] developmentDocActualList = null;
					if (trxValue.getBridgingLoan() != null) { // actual will be
																// null if this
																// is new record
						IProjectSchedule[] projectScheduleActualList = trxValue.getBridgingLoan()
								.getProjectScheduleList();
						DefaultLogger.debug(this, "projectScheduleActualList=" + projectScheduleActualList);

						if (projectScheduleActualList != null) {
							DefaultLogger.debug(this, "projectScheduleActualList.length="
									+ projectScheduleActualList.length);
							if (projectScheduleActualList.length > projectScheduleIndex) {
								developmentDocActualList = projectScheduleActualList[projectScheduleIndex]
										.getDevelopmentDocList();
								DefaultLogger.debug(this, "developmentDocActualList=" + developmentDocActualList);
							}
						}
					}

					String event = (String) map.get("event");
					DefaultLogger.debug(this, "event=" + event);
					if (DevelopmentDocAction.EVENT_VIEW.equals(event)
							|| DevelopmentDocAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)
							|| DevelopmentDocAction.EVENT_MAKER_REFRESH.equals(event)) {
						IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();
						DefaultLogger.debug(this, "objBridgingLoan=" + objBridgingLoan);
						resultMap.put("objBridgingLoan", objBridgingLoan);
					}
					else {
						OBDevelopmentDoc objDevelopmentDoc = null;
						OBDevelopmentDoc objActualDevelopmentDoc = null;
						String commonRef = (String) map.get("commonRef");

						DefaultLogger.debug(this, "developmentDocList=" + developmentDocList);
						for (int i = 0; i < developmentDocList.length; i++) {
							if (developmentDocList[i].getCommonRef() == Long.parseLong(commonRef)) {
								objDevelopmentDoc = (OBDevelopmentDoc) developmentDocList[i];
							}
						}
						if (developmentDocActualList != null) {
							DefaultLogger.debug(this, "developmentDocActualList=" + developmentDocActualList);
							for (int i = 0; i < developmentDocActualList.length; i++) {
								if (developmentDocActualList[i].getCommonRef() == Long.parseLong(commonRef)) {
									objActualDevelopmentDoc = (OBDevelopmentDoc) developmentDocActualList[i];
								}
							}
						}
						resultMap.put("objDevelopmentDoc", objDevelopmentDoc); // staging
																				// data
						resultMap.put("objActualDevelopmentDoc", objActualDevelopmentDoc); // actual
																							// data
					}
				}
			}
			resultMap.put("progressStage", (String) map.get("progressStage"));
			resultMap.put("projectScheduleIndex", Integer.toString(projectScheduleIndex));
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
