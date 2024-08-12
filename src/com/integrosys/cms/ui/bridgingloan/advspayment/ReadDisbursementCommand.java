package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IDisbursement;
import com.integrosys.cms.app.bridgingloan.bus.OBDisbursement;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 3, 2007 Time: 10:42:43 AM To
 * change this template use File | Settings | File Templates.
 */
public class ReadDisbursementCommand extends AbstractCommand {

	public ReadDisbursementCommand() {
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
				{ "objStagingDisbursement", "com.integrosys.cms.app.bridgingloan.bus.OBDisbursement", REQUEST_SCOPE },
				{ "objActualDisbursement", "com.integrosys.cms.app.bridgingloan.bus.OBDisbursement", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside ReadDisbursementCommand doExecute()");

		try {
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
			DefaultLogger.debug(this, "trxValue=" + trxValue);

			IDisbursement[] disbursementList = trxValue.getStagingBridgingLoan().getDisbursementList();
			DefaultLogger.debug(this, "disbursementList=" + disbursementList);
			IDisbursement[] disbursementActualList = null;
			if (trxValue.getBridgingLoan() != null) { // actual will be null if
														// this is new record
				disbursementActualList = trxValue.getBridgingLoan().getDisbursementList();
			}

			String event = (String) map.get("event");
			DefaultLogger.debug(this, "event=" + event);
			if (DisbursementAction.EVENT_VIEW.equals(event)
					|| DisbursementAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)
					|| DisbursementAction.EVENT_MAKER_PREPARE_DELETE.equals(event)) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();
				DefaultLogger.debug(this, "objBridgingLoan=" + objBridgingLoan);
				resultMap.put("objBridgingLoan", objBridgingLoan);
			}
			else {
				OBDisbursement objStagingDisbursement = null;
				OBDisbursement objActualDisbursement = null;
				String commonRef = (String) map.get("commonRef");

				DefaultLogger.debug(this, "disbursementList=" + disbursementList);
				for (int i = 0; i < disbursementList.length; i++) {
					if (disbursementList[i].getCommonRef() == Long.parseLong(commonRef)) {
						objStagingDisbursement = (OBDisbursement) disbursementList[i];
					}
				}
				if (disbursementActualList != null) {
					DefaultLogger.debug(this, "disbursementActualList=" + disbursementActualList);
					for (int i = 0; i < disbursementActualList.length; i++) {
						if (disbursementActualList[i].getCommonRef() == Long.parseLong(commonRef)) {
							objActualDisbursement = (OBDisbursement) disbursementActualList[i];
						}
					}
				}
				resultMap.put("objStagingDisbursement", objStagingDisbursement); // staging
																					// data
				resultMap.put("objActualDisbursement", objActualDisbursement); // actual
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