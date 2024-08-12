package com.integrosys.cms.ui.bridgingloan.buildup;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IBuildUp;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: Jun 21, 2007 Time: 4:59:43 PM To
 * change this template use File | Settings | File Templates.
 */
public class PrepareBuildUpCopyCommand extends AbstractCommand {

	public PrepareBuildUpCopyCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "bridgingLoanTrxValue",
				"com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "objBuildUp", "com.integrosys.cms.app.bridgingloan.bus.OBBuildUp", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside PrepareBuildUpCopyCommand doExecute()");

		try {
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
			IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan(); // Staging

			IBuildUp[] objBuildUp = null;
			if (objBridgingLoan != null) {
				objBuildUp = (IBuildUp[]) objBridgingLoan.getBuildUpList();
			}

			resultMap.put("objBuildUp", objBuildUp);
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