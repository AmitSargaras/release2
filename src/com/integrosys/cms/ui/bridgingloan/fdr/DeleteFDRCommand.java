package com.integrosys.cms.ui.bridgingloan.fdr;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: Apr 20, 2007 Time: 11:35:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class DeleteFDRCommand extends AbstractCommand {

	public DeleteFDRCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE },
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", FORM_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan",
				REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside DeleteFDRCommand doExecute()");

		try {
			IBridgingLoan objBridgingLoan = (IBridgingLoan) map.get("objBridgingLoan");
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
			trxValue.setStagingBridgingLoan(objBridgingLoan);

			resultMap.put("objBridgingLoan", objBridgingLoan);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}
}