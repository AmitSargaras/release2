/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ITradingAgreement;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;

/**
 * Describe this class. Purpose: for Maker to return the record to the previous
 * page Description: command that return the transaction value back to the
 * previous page
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class ReturnAgreementDetailCmd extends AbstractCommand {
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE }, });

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
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "thresholdRatingVal", "java.lang.Object", SERVICE_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String preEvent = (String) map.get("preEvent");
		String ind = (String) map.get("indexChange");
		int indexChange = Integer.parseInt(ind) + 1;
		String trxId = (String) map.get("TrxId");

		try {
			ILimitProfileTrxValue limitProfileTrxVal = (ILimitProfileTrxValue) (map.get("limitProfileTrxVal"));
			DefaultLogger.debug(this,"********************** inside ReturnAgreementDetailCmd limitProfileTrxVal : "
					+ limitProfileTrxVal);
			ITradingAgreement tradingAgreementVal = (ITradingAgreement) map.get("tradingAgreementVal");

			result.put("limitProfileTrxVal", limitProfileTrxVal);
			result.put("tradingAgreementVal", tradingAgreementVal);
			result.put("thresholdRatingVal", null);
			result.put("preEvent", preEvent);
			result.put("indexChange", String.valueOf(indexChange));
			result.put("TrxId", trxId);
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
