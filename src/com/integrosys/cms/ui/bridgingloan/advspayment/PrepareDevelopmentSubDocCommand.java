package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 9, 2007 Time: 2:26:18 PM To
 * change this template use File | Settings | File Templates.
 */
public class PrepareDevelopmentSubDocCommand extends AbstractCommand {

	public PrepareDevelopmentSubDocCommand() {
		DefaultLogger.debug("\n----------------------------->", "Entering PrepareDevelopmentSubDocCommand()");
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
				{ "progressStage", "java.lang.String", SERVICE_SCOPE },
				{ "projectScheduleIndex", "java.lang.String", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside PrepareDevelopmentSubDocCommand doExecute()");

		try {
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
			DefaultLogger.debug(this, "trxValue=" + trxValue);

			if (trxValue != null) {
				IBridgingLoan objBridgingLoan = trxValue.getStagingBridgingLoan();
				DefaultLogger.debug(this, "objBridgingLoan=" + objBridgingLoan);

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
}