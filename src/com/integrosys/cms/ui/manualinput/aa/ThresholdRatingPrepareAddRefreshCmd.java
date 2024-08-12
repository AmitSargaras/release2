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
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IThresholdRating;

/**
 * Describe this class. Purpose: for Maker to view the value from OB
 * Description: command that let the maker to view the value from OB
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date:$ Tag: $Name$
 */

public class ThresholdRatingPrepareAddRefreshCmd extends AbstractCommand {

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
				{ "thresholdRatingVal", "java.lang.Object", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE },
				{ "InitialThresholdRating", "com.integrosys.cms.app.limit.bus.OBThresholdRating", FORM_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "ratingTypeListID", "java.util.Collection", REQUEST_SCOPE },
				{ "ratingTypeListValue", "java.util.Collection", REQUEST_SCOPE },
				{ "ratingListID", "java.util.Collection", REQUEST_SCOPE },
				{ "ratingListValue", "java.util.Collection", REQUEST_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE },
				{ "InitialThresholdRating", "java.lang.Object", FORM_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		String preEvent = (String) map.get("preEvent");
		String ind = (String) map.get("indexChange");
		int indexChange = Integer.parseInt(ind) + 1;
		String event = (String) map.get("event");
		String index = (String) map.get("index");
		String trxId = (String) map.get("TrxId");

		try {

			RatingTypeList list = RatingTypeList.getInstance();
			Collection ratingTypeListID = list.getRatingTypeListID();
			Collection ratingTypeListValue = list.getRatingTypeListValue();
			resultMap.put("ratingTypeListID", ratingTypeListID);
			resultMap.put("ratingTypeListValue", ratingTypeListValue);

			IThresholdRating thresholdRating = (IThresholdRating) map.get("InitialThresholdRating");

			if (thresholdRating != null) {
//				System.out.println("---------------------------------B4 thresholdRating.getRatingType(): "+ thresholdRating.getRatingType());
				if (thresholdRating.getRatingType() != null) {
					RatingList rating = RatingList.getInstance(thresholdRating.getRatingType());
					Collection ratingListID = rating.getRatingListID();
					Collection ratingListValue = rating.getRatingListValue();
					resultMap.put("ratingListID", ratingListID);
					resultMap.put("ratingListValue", ratingListValue);
				}

			}

			resultMap.put("InitialThresholdRating", thresholdRating);
//			System.out.println("---------------------------------after thresholdRating: " + thresholdRating);

			resultMap.put("preEvent", preEvent);
			resultMap.put("indexChange", String.valueOf(indexChange));
			resultMap.put("index", index);
			resultMap.put("TrxId", trxId);

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
