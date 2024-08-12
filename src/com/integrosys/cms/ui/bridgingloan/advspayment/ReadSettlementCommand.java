package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.ISettlement;
import com.integrosys.cms.app.bridgingloan.bus.OBSettlement;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 3, 2007 Time: 11:14:16 AM To
 * change this template use File | Settings | File Templates.
 */
public class ReadSettlementCommand extends AbstractCommand {

	public ReadSettlementCommand() {
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
				{ "objStagingSettlement", "com.integrosys.cms.app.bridgingloan.bus.OBSettlement", REQUEST_SCOPE },
				{ "objActualSettlement", "com.integrosys.cms.app.bridgingloan.bus.OBSettlement", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside ReadSettlementCommand doExecute()");

		try {
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
			ISettlement[] settlementList = trxValue.getStagingBridgingLoan().getSettlementList();
			ISettlement[] settlementActualList = null;
			if (trxValue.getBridgingLoan() != null) { // actual will be null if
														// this is new record
				settlementActualList = trxValue.getBridgingLoan().getSettlementList();
			}

			String event = (String) map.get("event");
			if (DisbursementAction.EVENT_VIEW.equals(event)
					|| DisbursementAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)
					|| DisbursementAction.EVENT_MAKER_PREPARE_DELETE.equals(event)) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();
				resultMap.put("objBridgingLoan", objBridgingLoan);
			}
			else {
				OBSettlement objStagingSettlement = null;
				OBSettlement objActualSettlement = null;
				String commonRef = (String) map.get("commonRef");

				for (int i = 0; i < settlementList.length; i++) {
					if (settlementList[i].getCommonRef() == Long.parseLong(commonRef)) {
						objStagingSettlement = (OBSettlement) settlementList[i];
					}
				}
				if (settlementActualList != null) {
					DefaultLogger.debug(this, "settlementActualList=" + settlementActualList);
					for (int i = 0; i < settlementActualList.length; i++) {
						if (settlementActualList[i].getCommonRef() == Long.parseLong(commonRef)) {
							objActualSettlement = (OBSettlement) settlementActualList[i];
						}
					}
				}
				resultMap.put("objStagingSettlement", objStagingSettlement); // staging
																				// data
				resultMap.put("objActualSettlement", objActualSettlement); // actual
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