/**
 * Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.creditriskparam.policycap;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCap;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.IPolicyCapProxyManager;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.PolicyCapProxyManagerFactory;
import com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapTrxValue;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.StockExchangeList;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class ReadPolicyCapCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadPolicyCapCommand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "stockExchange", "java.lang.String", REQUEST_SCOPE }, { "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "isRejected", "java.lang.String", REQUEST_SCOPE },
				{ "stockExchangeName", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "policyCapTrxValue", "com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapTrxValue",
						SERVICE_SCOPE }, { "policyCapMap", "java.util.HashMap", FORM_SCOPE },
				{ "stockExchangeName", "java.lang.String", REQUEST_SCOPE },
				{ "isRejected", "java.lang.String", REQUEST_SCOPE },
				{ "boardDescList", "com.integrosys.cms.ui.common.CommonCodeList", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		String event = (String) map.get("event");
		String stockExchange = (String) map.get("stockExchange");
		String stockExchangeName = (String) map.get("stockExchangeName");
		String trxID = (String) map.get("trxID");
		String isRejected = (String) map.get("isRejected");
		boolean isEdit = (isRejected == null);

		IPolicyCapTrxValue trxValue = null;
		try {
			CommonCodeList boardDescList = CommonCodeList.getInstance(null, null, "BOARD_TYPE", false, stockExchange);
			resultMap.put("boardDescList", boardDescList);

			IPolicyCapProxyManager proxy = PolicyCapProxyManagerFactory.getPolicyCapProxyManager();
			DefaultLogger.debug(this, ">>>>>>>>>>>> trxID field = " + trxID);
			if ((trxID == null) || trxID.equals("")) { // Maker Edit
				DefaultLogger.debug(this, ">>>>>>>>>>>> Getting the Transaction object");
				IPolicyCap[] policyCapList = proxy.getPolicyCapByExchange(stockExchange);
				if ((policyCapList != null) && (policyCapList.length > 0)) {
					DefaultLogger.debug(this, ">>>>>>>>>>>>> groupID = " + policyCapList[0].getGroupID());
					trxValue = proxy.getPolicyCapTrxValue(policyCapList[0].getGroupID());
				}
			}
			else { // Maker/Checher from To-Do
				DefaultLogger.debug(this, ">>>>>>>>>>>> In getting by trxID");
				trxValue = proxy.getPolicyCapTrxValueByTrxID(trxID);
				DefaultLogger.debug(this, ">>>>>>>>>>>> trxValue=" + trxValue);
			}

			if (trxValue != null) {
				if (trxValue.getPolicyCap() != null) {
					// stockExchange =
					// trxValue.getPolicyCap()[0].getStockExchange();
					DefaultLogger.debug(this, "stockExchange=" + stockExchange);
				}

				String trxStatus = trxValue.getStatus();
				DefaultLogger.debug(this, ">>>>>> event = " + event);
				DefaultLogger.debug(this, ">>>>>> trxStatus = " + trxStatus);
				if (PolicyCapAction.EVENT_MAKER_EDIT.equals(event)) {
					DefaultLogger.debug(this, ">>>>>>>>>>> isEdit = " + isEdit);
					if (isEdit && !(ICMSConstant.STATE_ACTIVE.equals(trxStatus))) {
						resultMap.put("wip", "wip");
					}
				}

				HashMap policyCapMap = new HashMap();
				policyCapMap.put("policyCapList", (isEdit) ? trxValue.getPolicyCap() : trxValue.getStagingPolicyCap());
				resultMap.put("policyCapMap", policyCapMap);

			}

			DefaultLogger.debug(this, "stockExchangeName before=" + stockExchangeName);
			if (stockExchangeName == null) {
				stockExchangeName = StockExchangeList.getInstance().getStockExchangeName(stockExchange);
				DefaultLogger.debug(this, "stockExchangeName=" + stockExchangeName);
			}

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		resultMap.put("isRejected", isRejected);
		resultMap.put("stockExchangeName", stockExchangeName);
		resultMap.put("policyCapTrxValue", trxValue);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}