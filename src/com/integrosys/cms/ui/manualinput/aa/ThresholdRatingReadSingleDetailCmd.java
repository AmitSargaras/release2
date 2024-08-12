/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IThresholdRating;
import com.integrosys.cms.app.limit.bus.ITradingAgreement;

/**
 * Describe this class. Purpose: for Maker to read particular record from OB
 * Description: command that let the maker to read particular record that want
 * to be edited from OB
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date:$ Tag: $Name$
 */

public class ThresholdRatingReadSingleDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "thresholdRatingVal", "java.lang.Object", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE }, { "index", "java.lang.String", REQUEST_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE }, };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "thresholdRatingVal", "java.lang.Object", SERVICE_SCOPE },
				{ "InitialThresholdRating", "java.lang.Object", FORM_SCOPE },
				{ "ratingListID", "java.util.Collection", REQUEST_SCOPE },
				{ "ratingListValue", "java.util.Collection", REQUEST_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE }, { "index", "java.lang.String", REQUEST_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE }, };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		try {
			String trxId = (String) map.get("TrxId");
			String event = (String) map.get("event");
			String index = (String) map.get("index");
			int ind = Integer.parseInt(index);
			ITradingAgreement tradingAgreement = (ITradingAgreement) map.get("tradingAgreementVal");
			DefaultLogger.debug(this,"********************** inside ThresholdRatingReadSingleDetailCmd tradingAgreement : "
					+ tradingAgreement);
			IThresholdRating[] thresholdRating = (IThresholdRating[]) map.get("thresholdRatingVal");
			IThresholdRating singleThresholdRating = null;
			int i = 0;

			System.out
					.println("********************** inside ThresholdRatingReadSingleDetailCmd tradingAgreement.getThresholdRatingList() : "
							+ tradingAgreement.getThresholdRatingList());
			/*
			 * List list = tradingAgreement.getThresholdRatingList(); if ( list
			 * != null ) { thresholdRating = new IThresholdRating[list.size()];
			 * Iterator iter = list.iterator(); while (iter.hasNext()) {
			 * thresholdRating[i] = (IThresholdRating)iter.next(); i++; } }
			 */

			singleThresholdRating = thresholdRating[ind];

			if (singleThresholdRating != null) {
				DefaultLogger.debug(this,"---------------------------------B4 singleThresholdRating.getRatingType(): "
						+ singleThresholdRating.getRatingType());
				if (singleThresholdRating.getRatingType() != null) {
					RatingList rating = RatingList.getInstance(singleThresholdRating.getRatingType());
					Collection ratingListID = rating.getRatingListID();
					Collection ratingListValue = rating.getRatingListValue();
					result.put("ratingListID", ratingListID);
					result.put("ratingListValue", ratingListValue);
				}

			}

			result.put("InitialThresholdRating", singleThresholdRating);
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
