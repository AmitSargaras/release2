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
import com.integrosys.cms.app.limit.bus.OBThresholdRating;

/**
 * Describe this class. Purpose: for Maker to save the value into OB
 * Description: command that let the maker to save the value that being edited
 * to the OB
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date:$ Tag: $Name$
 */

public class ThresholdRatingSaveUpdateDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "thresholdRatingVal", "java.lang.Object", SERVICE_SCOPE },
				{ "InitialThresholdRating", "java.lang.Object", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "index", "java.lang.String", REQUEST_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {

		return (new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "thresholdRatingVal", "java.lang.Object", SERVICE_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "alreadyExist", "java.lang.String", REQUEST_SCOPE },
				{ "InitialThresholdRating", "java.lang.Object", FORM_SCOPE }, });
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
		boolean addedAmount = false;

		try {
			String index = (String) map.get("index");
			ITradingAgreement tradingAgreement = (ITradingAgreement) map.get("tradingAgreementVal");
			IThresholdRating[] curThresholdRating = (IThresholdRating[]) map.get("thresholdRatingVal");
			;

			if ("prepare_add_threshold_update_confirm".equals(event)
					|| "prepare_update_threshold_update_confirm".equals(event)) {
				IThresholdRating thresholdRating = (IThresholdRating) (map.get("InitialThresholdRating"));
				IThresholdRating link = new OBThresholdRating();
				link = thresholdRating;
				IThresholdRating[] refArr = curThresholdRating;
				if (refArr == null) {
					refArr = new OBThresholdRating[1];
					refArr[0] = link;
					curThresholdRating = refArr;
				}
				else {
					int ind1 = Integer.parseInt(index);
					for (int i = 0; i < refArr.length; i++) {
						if ((i < ind1) || (i > ind1)) {
//							System.out.println("i = " + i + " & ind1 = " + ind1);
							if (thresholdRating.getRatingType().equals(refArr[i].getRatingType())
									&& thresholdRating.getRating().equals(refArr[i].getRating())) {
								addedAmount = true;
								break;
							}
						}
					}
					if (addedAmount == true) {
						result.put("alreadyExist", "alreadyExist");
						curThresholdRating = refArr;
					}
					else {
						refArr[ind1] = link;
						curThresholdRating = refArr;
					}
//					System.out.println("curThresholdRating = " + curThresholdRating + " & refArr[ind1] = "+ refArr[ind1]);
				}
			}
			result.put("tradingAgreementVal", tradingAgreement);
			result.put("thresholdRatingVal", curThresholdRating);
			result.put("InitialThresholdRating", curThresholdRating);
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
