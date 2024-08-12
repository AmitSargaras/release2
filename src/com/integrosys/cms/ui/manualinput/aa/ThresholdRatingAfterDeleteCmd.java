/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IThresholdRating;
import com.integrosys.cms.app.limit.bus.ITradingAgreement;

/**
 * Describe this class. Purpose: for Maker to set a list of Threshold Rating
 * value without removed record into OB Description: command that let the maker
 * to set a list of Threshold Rating value without removed record into OB
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date:$ Tag: $Name$
 */

public class ThresholdRatingAfterDeleteCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "InitialThresholdRating", "java.lang.Object", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "thresholdRatingVal", "java.lang.Object", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {

		return (new String[][] {
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "thresholdRatingVal", "java.lang.Object", SERVICE_SCOPE },
				{ "InitialThresholdRating", "java.lang.Object", FORM_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			String event = (String) (map.get("event"));
			ITradingAgreement tradingAgreement = (ITradingAgreement) map.get("tradingAgreementVal");
			IThresholdRating[] thresholdRating = (IThresholdRating[]) (map.get("InitialThresholdRating"));

			result.put("thresholdRatingVal", thresholdRating);
			result.put("InitialThresholdRating", thresholdRating);
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
