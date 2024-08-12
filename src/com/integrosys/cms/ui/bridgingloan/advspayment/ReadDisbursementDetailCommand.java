package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IDisbursement;
import com.integrosys.cms.app.bridgingloan.bus.IDisbursementDetail;
import com.integrosys.cms.app.bridgingloan.bus.OBDisbursementDetail;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: Jun 11, 2007 Time: 12:47:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReadDisbursementDetailCommand extends AbstractCommand {

	public ReadDisbursementDetailCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "disbursementIndex", "java.lang.String", REQUEST_SCOPE },
				{ "commonRef", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", FORM_SCOPE },
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", REQUEST_SCOPE },
				{ "objStagingDisbursementDetail", "com.integrosys.cms.app.bridgingloan.bus.OBDisbursementDetail",
						REQUEST_SCOPE },
				{ "objActualDisbursementDetail", "com.integrosys.cms.app.bridgingloan.bus.OBDisbursementDetail",
						REQUEST_SCOPE }, { "disbursementIndex", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside ReadDisbursementDetailCommand doExecute()");

		try {
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
			DefaultLogger.debug(this, "trxValue=" + trxValue);

			int disbursementIndex = Integer.parseInt((String) map.get("disbursementIndex"));
			IDisbursement[] disbursementList = trxValue.getStagingBridgingLoan().getDisbursementList();
			DefaultLogger.debug(this, "disbursementList=" + disbursementList);

			IDisbursementDetail[] disbursementDetailList = disbursementList[disbursementIndex]
					.getDisbursementDetailList();
			DefaultLogger.debug(this, "disbursementDetailList=" + disbursementDetailList);

			IDisbursement[] disbursementActualList = null;
			IDisbursementDetail[] disbursementDetailActualList = null;
			if (trxValue.getBridgingLoan() != null) { // actual will be null if
														// this is new record
				disbursementActualList = trxValue.getBridgingLoan().getDisbursementList();
				if (disbursementActualList.length > disbursementIndex) {
					disbursementDetailActualList = disbursementActualList[disbursementIndex]
							.getDisbursementDetailList();
				}
			}

			String event = (String) map.get("event");
			DefaultLogger.debug(this, "event=" + event);
			if (DisbursementDetailAction.EVENT_VIEW.equals(event)
					|| DisbursementDetailAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)
					|| DisbursementDetailAction.EVENT_MAKER_PREPARE_DELETE.equals(event)) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();
				DefaultLogger.debug(this, "objBridgingLoan=" + objBridgingLoan);
				resultMap.put("objBridgingLoan", objBridgingLoan);
			}
			else {
				OBDisbursementDetail objStagingDisbursementDetail = null;
				OBDisbursementDetail objActualDisbursementDetail = null;
				String commonRef = (String) map.get("commonRef");

				DefaultLogger.debug(this, "disbursementList=" + disbursementList);
				for (int i = 0; i < disbursementDetailList.length; i++) {
					if (disbursementDetailList[i].getCommonRef() == Long.parseLong(commonRef)) {
						objStagingDisbursementDetail = (OBDisbursementDetail) disbursementDetailList[i];
					}
				}
				if (disbursementActualList != null) {
					DefaultLogger.debug(this, "disbursementActualList=" + disbursementActualList);
					for (int i = 0; i < disbursementActualList.length; i++) {
						if (disbursementActualList[i].getCommonRef() == Long.parseLong(commonRef)) {
							objActualDisbursementDetail = (OBDisbursementDetail) disbursementDetailActualList[i];
						}
					}
				}
				resultMap.put("objStagingDisbursementDetail", objStagingDisbursementDetail); // staging
																								// data
				resultMap.put("objActualDisbursementDetail", objActualDisbursementDetail); // actual
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