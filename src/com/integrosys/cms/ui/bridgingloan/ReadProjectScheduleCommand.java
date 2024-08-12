package com.integrosys.cms.ui.bridgingloan;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IProjectSchedule;
import com.integrosys.cms.app.bridgingloan.bus.OBProjectSchedule;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: Apr 25, 2007 Time: 1:07:30 AM To
 * change this template use File | Settings | File Templates.
 */
public class ReadProjectScheduleCommand extends AbstractCommand {

	public ReadProjectScheduleCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "commonRef", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", FORM_SCOPE },
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", REQUEST_SCOPE },
				{ "objStagingProjectSchedule", "com.integrosys.cms.app.bridgingloan.bus.OBProjectSchedule",
						REQUEST_SCOPE },
				{ "objActualProjectSchedule", "com.integrosys.cms.app.bridgingloan.bus.OBProjectSchedule",
						REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside ReadProjectScheduleCommand doExecute()");

		try {
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
			DefaultLogger.debug(this, "trxValue=" + trxValue);

			IProjectSchedule[] projectScheduleList = trxValue.getStagingBridgingLoan().getProjectScheduleList();
			IProjectSchedule[] projectScheduleActualList = null;
			if (trxValue.getBridgingLoan() != null) { // actual will be null if
														// this is new record
				projectScheduleActualList = trxValue.getBridgingLoan().getProjectScheduleList();
			}

			String event = (String) map.get("event");
			DefaultLogger.debug(this, "event=" + event);
			if (ProjectScheduleAction.EVENT_VIEW.equals(event)
					|| ProjectScheduleAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)
					|| ProjectScheduleAction.EVENT_MAKER_PREPARE_DELETE.equals(event)) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();
				resultMap.put("objBridgingLoan", objBridgingLoan);
			}
			else {
				OBProjectSchedule objStagingProjectSchedule = null;
				OBProjectSchedule objActualProjectSchedule = null;
				String commonRef = (String) map.get("commonRef");

				DefaultLogger.debug(this, "projectScheduleList=" + projectScheduleList);
				for (int i = 0; i < projectScheduleList.length; i++) {
					if (projectScheduleList[i].getCommonRef() == Long.parseLong(commonRef)) {
						objStagingProjectSchedule = (OBProjectSchedule) projectScheduleList[i];
					}
				}
				if (projectScheduleActualList != null) {
					DefaultLogger.debug(this, "projectScheduleActualList=" + projectScheduleActualList);
					for (int i = 0; i < projectScheduleActualList.length; i++) {
						if (projectScheduleActualList[i].getCommonRef() == Long.parseLong(commonRef)) {
							objActualProjectSchedule = (OBProjectSchedule) projectScheduleActualList[i];
						}
					}
				}
				resultMap.put("objStagingProjectSchedule", objStagingProjectSchedule); // staging
																						// data
				resultMap.put("objActualProjectSchedule", objActualProjectSchedule); // actual
																						// data
			}
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}
}