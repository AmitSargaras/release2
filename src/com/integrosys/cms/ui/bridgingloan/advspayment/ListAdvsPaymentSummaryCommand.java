package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IDisbursement;
import com.integrosys.cms.app.bridgingloan.bus.ISettlement;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: Apr 30, 2007 Time: 6:38:35 PM To
 * change this template use File | Settings | File Templates.
 */
public class ListAdvsPaymentSummaryCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListAdvsPaymentSummaryCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE },
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", SERVICE_SCOPE },
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", REQUEST_SCOPE },
				{ "disbursementSummary", "com.integrosys.cms.ui.bridgingloan.advspayment.DisbursementAction",
						REQUEST_SCOPE },
				{ "settlementSummary", "com.integrosys.cms.ui.bridgingloan.advspayment.SettlementAction", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, ">>>>>>>> Inside ListAdvsPaymentSummaryCommand doExecute()");

		try {
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");

			IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();
			IDisbursement[] disbursementSummary = (IDisbursement[]) objBridgingLoan.getDisbursementList();
			ISettlement[] settlementSummary = (ISettlement[]) objBridgingLoan.getSettlementList();

			resultMap.put("objBridgingLoan", objBridgingLoan);
			resultMap.put("disbursementSummary", disbursementSummary);
			resultMap.put("settlementSummary", settlementSummary);
		}
		catch (Exception e) {
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}