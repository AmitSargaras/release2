package com.integrosys.cms.ui.bridgingloan.fdr;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IFDR;
import com.integrosys.cms.app.bridgingloan.bus.OBFDR;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: Apr 18, 2007 Time: 3:13:19 PM To
 * change this template use File | Settings | File Templates.
 */
public class ReadFDRCommand extends AbstractCommand {

	public ReadFDRCommand() {
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
				{ "objStagingFDR", "com.integrosys.cms.app.bridgingloan.bus.OBFDR", REQUEST_SCOPE },
				{ "objActualFDR", "com.integrosys.cms.app.bridgingloan.bus.OBFDR", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside ReadFDRCommand doExecute()");

		try {
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
			IFDR[] fdrList = trxValue.getStagingBridgingLoan().getFdrList();
			IFDR[] fdrActualList = null;
			if (trxValue.getBridgingLoan() != null) { // actual will be null if
														// this is new record
				fdrActualList = trxValue.getBridgingLoan().getFdrList();
			}

			String event = (String) map.get("event");
			if (FDRAction.EVENT_VIEW.equals(event) || FDRAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)
					|| FDRAction.EVENT_MAKER_PREPARE_DELETE.equals(event)) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();
				resultMap.put("objBridgingLoan", objBridgingLoan);
			}
			else { // Checker
				OBFDR objStagingFDR = null;
				OBFDR objActualFDR = null;
				String commonRef = (String) map.get("commonRef");

				for (int i = 0; i < fdrList.length; i++) {
					if (fdrList[i].getCommonRef() == Long.parseLong(commonRef)) {
						objStagingFDR = (OBFDR) fdrList[i];
					}
				}
				if (fdrActualList != null) {
					for (int i = 0; i < fdrActualList.length; i++) {
						if (fdrActualList[i].getCommonRef() == Long.parseLong(commonRef)) {
							objActualFDR = (OBFDR) fdrActualList[i];
						}
					}
				}
				resultMap.put("objStagingFDR", objStagingFDR); // staging data
				resultMap.put("objActualFDR", objActualFDR); // actual data
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