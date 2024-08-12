/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IThresholdRating;
import com.integrosys.cms.app.limit.bus.ITradingAgreement;

/**
 * Describe this class. Purpose: for Maker to save particular record into OB
 * Description: command that let the maker to save particular record that being
 * edited to the OB
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date:$ Tag: $Name$
 */

public class ThresholdRatingSaveDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "thresholdRatingVal", "java.lang.Object", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE }, });
	}

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
		String event = (String) map.get("event");
		String trxId = (String) map.get("TrxId");

		ITradingAgreement tradingAgreementVal = (ITradingAgreement) map.get("tradingAgreementVal");
		IThresholdRating[] curThresholdRating = (IThresholdRating[]) map.get("thresholdRatingVal");

//		System.out.println("------------------------------------ 2 curThresholdRating : " + curThresholdRating);
		try {
			if ("prepare_add_threshold_confirm".equals(event) || "prepare_update_threshold_confirm".equals(event)) {
				tradingAgreementVal.setThresholdRatingList(Arrays.asList(curThresholdRating));
			}

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
