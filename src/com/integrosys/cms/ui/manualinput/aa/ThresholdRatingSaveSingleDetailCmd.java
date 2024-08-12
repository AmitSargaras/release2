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
 * Describe this class. Purpose: for Maker to save particular record into OB
 * Description: command that let the maker to save particular record that being
 * edited to the OB
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date:$ Tag: $Name$
 */

public class ThresholdRatingSaveSingleDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "thresholdRatingVal", "java.lang.Object", SERVICE_SCOPE },
				{ "InitialThresholdRating", "java.lang.Object", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "preEvent", "java.lang.String", REQUEST_SCOPE },
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
			IThresholdRating[] curThresholdRating = null;
			int j = 0;
			ITradingAgreement tradingAgreementVal = (ITradingAgreement) map.get("tradingAgreementVal");
			curThresholdRating = (IThresholdRating[]) map.get("thresholdRatingVal");
//			System.out.println("inside ThresholdRatingSaveSingleDetailCmd curThresholdRating = " + curThresholdRating);

			if ("prepare_add_threshold_new_confirm".equals(event)
					|| "prepare_update_threshold_new_confirm".equals(event)) {
				OBThresholdRating thresholdRating = (OBThresholdRating) map.get("InitialThresholdRating");
//				System.out.println("inside ThresholdRatingSaveSingleDetailCmd curThresholdRating = "+ curThresholdRating);
				IThresholdRating[] refArr = curThresholdRating;
				if (refArr == null) {
					refArr = new OBThresholdRating[1];
					refArr[0] = thresholdRating;
					curThresholdRating = refArr;
				}
				else {
					IThresholdRating[] newArr = null;
					for (int i = 0; i < refArr.length; i++) {
//						System.out.println("thresholdRating.getRatingType() : " + thresholdRating.getRatingType()+ "& refArr[i].getRatingType() : " + refArr[i].getRatingType());
//						System.out.println("thresholdRating.getRating() : " + thresholdRating.getRating()+ "& refArr[i].getRating() : " + refArr[i].getRating());
						if (thresholdRating.getRatingType().equals(refArr[i].getRatingType())
								&& thresholdRating.getRating().equals(refArr[i].getRating())) {
//							System.out.println("inside if thresholdRating.getRating() : " + thresholdRating.getRating()+ "& refArr[i].getRating() : " + refArr[i].getRating());
							addedAmount = true;
							break;
						}
					}
					if (addedAmount == true) {
						result.put("alreadyExist", "alreadyExist");
						newArr = new IThresholdRating[refArr.length];
						newArr = refArr;
					}
					else {
						newArr = new IThresholdRating[refArr.length + 1];
						for (int i = 0; i < refArr.length; i++) {
							newArr[i] = refArr[i];
						}
						newArr[newArr.length - 1] = thresholdRating;
					}
					curThresholdRating = newArr;
				}
			}

			result.put("tradingAgreementVal", tradingAgreementVal);
			result.put("thresholdRatingVal", curThresholdRating);
			result.put("InitialThresholdRating", curThresholdRating);
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
