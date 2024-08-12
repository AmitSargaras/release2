package com.integrosys.cms.ui.bridgingloan.buildup;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IBuildUp;
import com.integrosys.cms.app.bridgingloan.bus.ISalesProceeds;
import com.integrosys.cms.app.bridgingloan.bus.OBSalesProceeds;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: Apr 30, 2007 Time: 11:45:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReadSalesProceedsCommand extends AbstractCommand {

	public ReadSalesProceedsCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "buildUpIndex", "java.lang.String", REQUEST_SCOPE },
				{ "commonRef", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", FORM_SCOPE },
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", REQUEST_SCOPE },
				{ "objStagingSalesProceeds", "com.integrosys.cms.app.bridgingloan.bus.OBSalesProceeds", REQUEST_SCOPE },
				{ "objActualSalesProceeds", "com.integrosys.cms.app.bridgingloan.bus.OBSalesProceeds", REQUEST_SCOPE },
				{ "buildUpIndex", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside ReadSalesProceedsCommand doExecute()");

		try {
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
			DefaultLogger.debug(this, "trxValue=" + trxValue);

			int buildUpIndex = Integer.parseInt((String) map.get("buildUpIndex"));
			DefaultLogger.debug(this, "buildUpIndex=" + buildUpIndex);
			IBuildUp[] buildUpList = trxValue.getStagingBridgingLoan().getBuildUpList();
			ISalesProceeds[] salesProceedsList = buildUpList[buildUpIndex].getSalesProceedsList();

			IBuildUp[] buildUpActualList = null;
			ISalesProceeds[] salesProceedsActualList = null;

			if (trxValue.getBridgingLoan() != null) { // actual will be null if
														// this is new record
				buildUpActualList = trxValue.getBridgingLoan().getBuildUpList();
				if (buildUpActualList.length > buildUpIndex) {
					salesProceedsActualList = buildUpActualList[buildUpIndex].getSalesProceedsList();
				}
			}

			String event = (String) map.get("event");
			DefaultLogger.debug(this, "event=" + event);
			if (BuildUpAction.EVENT_VIEW.equals(event) || BuildUpAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)
					|| BuildUpAction.EVENT_MAKER_PREPARE_DELETE.equals(event)) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();
				DefaultLogger.debug(this, "objBridgingLoan=" + objBridgingLoan);
				resultMap.put("objBridgingLoan", objBridgingLoan);
			}
			else { // Checker
				OBSalesProceeds objStagingSalesProceeds = null;
				OBSalesProceeds objActualSalesProceeds = null;
				String commonRef = (String) map.get("commonRef");

				DefaultLogger.debug(this, "buildUpList=" + buildUpList);
				for (int i = 0; i < salesProceedsList.length; i++) {
					if (salesProceedsList[i].getCommonRef() == Long.parseLong(commonRef)) {
						objStagingSalesProceeds = (OBSalesProceeds) salesProceedsList[i];
					}
				}
				if (salesProceedsActualList != null) {
					DefaultLogger.debug(this, "salesProceedsActualList=" + salesProceedsActualList);
					for (int i = 0; i < salesProceedsActualList.length; i++) {
						if (salesProceedsActualList[i].getCommonRef() == Long.parseLong(commonRef)) {
							objActualSalesProceeds = (OBSalesProceeds) salesProceedsActualList[i];
						}
					}
				}
				resultMap.put("objStagingSalesProceeds", objStagingSalesProceeds); // staging
																					// data
				resultMap.put("objActualSalesProceeds", objActualSalesProceeds); // actual
																					// data
			}
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