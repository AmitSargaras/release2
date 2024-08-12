package com.integrosys.cms.ui.bridgingloan.buildup;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IBuildUp;
import com.integrosys.cms.app.bridgingloan.bus.OBBuildUp;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: Apr 25, 2007 Time: 4:32:33 PM To
 * change this template use File | Settings | File Templates.
 */
public class ReadBuildUpCommand extends AbstractCommand {

	public ReadBuildUpCommand() {
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
				{ "objStagingBuildUp", "com.integrosys.cms.app.bridgingloan.bus.OBBuildUp", REQUEST_SCOPE },
				{ "objActualBuildUp", "com.integrosys.cms.app.bridgingloan.bus.OBBuildUp", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside ReadBuildUpCommand doExecute()");

		try {
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
			IBuildUp[] buildUpList = (IBuildUp[]) trxValue.getStagingBridgingLoan().getBuildUpList();
			DefaultLogger.debug(this, "buildUpList=" + buildUpList);
			IBuildUp[] buildUpActualList = null;
			if (trxValue.getBridgingLoan() != null) { // actual will be null if
														// this is new record
				buildUpActualList = trxValue.getBridgingLoan().getBuildUpList();
			}

			String event = (String) map.get("event");
			DefaultLogger.debug(this, "event=" + event);
			if (BuildUpAction.EVENT_VIEW.equals(event) || BuildUpAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)
					|| BuildUpAction.EVENT_MAKER_PREPARE_DELETE.equals(event)) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan(); // Staging
				DefaultLogger.debug(this, "objBridgingLoan=" + objBridgingLoan);
				resultMap.put("objBridgingLoan", objBridgingLoan);
			}
			else { // Checker
				OBBuildUp objStagingBuildUp = null;
				OBBuildUp objActualBuildUp = null;
				String commonRef = (String) map.get("commonRef");
				DefaultLogger.debug(this, "commonRef=" + commonRef);

				DefaultLogger.debug(this, "buildUpList=" + buildUpList);
				for (int i = 0; i < buildUpList.length; i++) {
					if (buildUpList[i].getCommonRef() == Long.parseLong(commonRef)) {
						objStagingBuildUp = (OBBuildUp) buildUpList[i];
					}
				}
				if (buildUpActualList != null) {
					DefaultLogger.debug(this, "buildUpActualList=" + buildUpActualList);
					for (int i = 0; i < buildUpActualList.length; i++) {
						if (buildUpActualList[i].getCommonRef() == Long.parseLong(commonRef)) {
							objActualBuildUp = (OBBuildUp) buildUpActualList[i];
						}
					}
				}
				resultMap.put("objStagingBuildUp", objStagingBuildUp); // staging
																		// data
				resultMap.put("objActualBuildUp", objActualBuildUp); // actual
																		// data
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
}