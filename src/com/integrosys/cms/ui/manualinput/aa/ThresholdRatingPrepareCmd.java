/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IThresholdRating;
import com.integrosys.cms.app.limit.bus.ITradingAgreement;
import com.integrosys.cms.app.limit.bus.OBThresholdRating;

/**
 * Describe this class. Purpose: for Maker to view the value from OB
 * Description: command that let the maker to view the value from OB
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date:$ Tag: $Name$
 */

public class ThresholdRatingPrepareCmd extends AbstractCommand {

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
				{ "indexChange", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "InitialThresholdRating", "java.lang.Object", FORM_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this,"**************inside command class doExecute ****************");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap rating = new HashMap();

		String preEvent = (String) map.get("preEvent");
		String ind = (String) map.get("indexChange");
		int indexChange = Integer.parseInt(ind) + 1;
		String event = (String) map.get("event");
		String trxId = (String) map.get("TrxId");

		try {

			ITradingAgreement tradingAgreement = (ITradingAgreement) map.get("tradingAgreementVal");
			IThresholdRating[] curThresholdRating = (IThresholdRating[]) map.get("thresholdRatingVal");
			IThresholdRating[] thresholdRating = null;
			int i = 0;
//			System.out.println("**************inside command class tradingAgreement : " + tradingAgreement);

			if (curThresholdRating != null) {
				thresholdRating = curThresholdRating;
			}
			else {
				if (tradingAgreement.getThresholdRatingList() != null) {
					List list = tradingAgreement.getThresholdRatingList();
					if (list != null) {
						thresholdRating = new OBThresholdRating[list.size()];
						Iterator iter = list.iterator();
						while (iter.hasNext()) {
							thresholdRating[i] = (IThresholdRating) iter.next();
							i++;
						}
					}
				}
			}

			resultMap.put("tradingAgreementVal", tradingAgreement);
			resultMap.put("thresholdRatingVal", thresholdRating);
			resultMap.put("InitialThresholdRating", thresholdRating);

			resultMap.put("preEvent", preEvent);
			resultMap.put("indexChange", String.valueOf(indexChange));
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
