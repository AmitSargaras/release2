/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IThresholdRating;
import com.integrosys.cms.app.limit.bus.OBTradingAgreement;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.ui.common.ForexHelper;

/**
 * Describe this class. Purpose: for Maker to view the value from OB
 * Description: command that let the maker to view the value from OB
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date:$ Tag: $Name$
 */

public class TradingAgreementPrepareRefreshCmd extends AbstractCommand {

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
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE },
				{ "InitialTradingAgreement", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", FORM_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE }, { "cpAmountError", "java.lang.String", REQUEST_SCOPE },
				{ "mbbAmountError", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "ratingTypeListID", "java.util.Collection", REQUEST_SCOPE },
				{ "ratingTypeListValue", "java.util.Collection", REQUEST_SCOPE },
				{ "ratingCptListID", "java.util.Collection", REQUEST_SCOPE },
				{ "ratingCptListValue", "java.util.Collection", REQUEST_SCOPE },
				{ "ratingMbbListID", "java.util.Collection", REQUEST_SCOPE },
				{ "ratingMbbListValue", "java.util.Collection", REQUEST_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE },
				{ "InitialTradingAgreement", "java.lang.Object", FORM_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE }, { "cpAmountError", "java.lang.String", REQUEST_SCOPE },
				{ "mbbAmountError", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap ratingTable = new HashMap();

		String preEvent = (String) map.get("preEvent");
		String ind = (String) map.get("indexChange");
		int indexChange = Integer.parseInt(ind) + 1;
		String event = (String) map.get("event");
		String trxId = (String) map.get("TrxId");
		String cpAmountError = (String) map.get("cpAmountError");
		String mbbAmountError = (String) map.get("mbbAmountError");

		try {

			ILimitProfileTrxValue limitProfileTrxVal = (ILimitProfileTrxValue) map.get("limitProfileTrxVal");
//			System.out.println("---------------------------------after limitProfileTrxVal: " + limitProfileTrxVal);
			OBTradingAgreement tradingAgreement = null;
			tradingAgreement = (OBTradingAgreement) map.get("InitialTradingAgreement");
			IThresholdRating[] thresholdRating = null;
			int i = 0;

			RatingTypeList list = RatingTypeList.getInstance();
			Collection ratingTypeListID = list.getRatingTypeListID();
			Collection ratingTypeListValue = list.getRatingTypeListValue();
			resultMap.put("ratingTypeListID", ratingTypeListID);
			resultMap.put("ratingTypeListValue", ratingTypeListValue);

			if (tradingAgreement != null) {
				List list1 = tradingAgreement.getThresholdRatingList();
				if (list1 != null) {
					thresholdRating = new IThresholdRating[list1.size()];
					Iterator iter = list1.iterator();
					while (iter.hasNext()) {
						thresholdRating[i] = (IThresholdRating) iter.next();
						i++;
					}
				}

				CurrencyCode baseCurrency = new CurrencyCode(tradingAgreement.getBaseCurrency());
				if (tradingAgreement.getCounterPartyExtRating() != null) {
//					System.out.println("tradingAgreement.getCounterPartyExtRating().getRatingType() : "+ tradingAgreement.getCounterPartyExtRating().getRatingType());
					RatingList ratingCpt = RatingList.getInstance(tradingAgreement.getCounterPartyExtRating()
							.getRatingType());
					Collection ratingCptListID = ratingCpt.getRatingListID();
					Collection ratingCptListValue = ratingCpt.getRatingListValue();
					resultMap.put("ratingCptListID", ratingCptListID);
					resultMap.put("ratingCptListValue", ratingCptListValue);
//					System.out.println("ratingCptListID : " + ratingCptListID + " & ratingCptListValue : "+ ratingCptListValue);

					String ratingType = tradingAgreement.getCounterPartyExtRating().getRatingType();
					String rating = tradingAgreement.getCounterPartyExtRating().getRating();
					if (!(rating.equals(null) || rating.equals("")) && (thresholdRating != null)) {
						for (int j = 0; j < thresholdRating.length; j++) {
							if (thresholdRating[j].getRatingType().equals(ratingType)
									&& thresholdRating[j].getRating().equals(rating)) {
//								System.out.println("baseCurrency : " + baseCurrency
//										+ " & thresholdRating[j].getThresholdAmount() : "
//										+ thresholdRating[j].getThresholdAmount());
								ForexHelper fr = new ForexHelper();
								Amount cpConvertValue = fr.convert(thresholdRating[j].getThresholdAmount(),
										baseCurrency);
//								System.out.println("convertValue : " + cpConvertValue);
								if (cpConvertValue == null) {
									resultMap.put("cpAmountError", "true");
									// }else{
									// tradingAgreement.
									// setCounterPartyThresholdAmount
									// (cpConvertValue);
								}
							}
						}
					}
				}

				if (tradingAgreement.getMbbExtRating() != null) {
//					System.out.println("tradingAgreement.getMbbExtRating().getRatingType() : "+ tradingAgreement.getMbbExtRating().getRatingType());
					RatingList ratingMbb = RatingList.getInstance(tradingAgreement.getMbbExtRating().getRatingType());
					Collection ratingMbbListID = ratingMbb.getRatingListID();
					Collection ratingMbbListValue = ratingMbb.getRatingListValue();
					resultMap.put("ratingMbbListID", ratingMbbListID);
					resultMap.put("ratingMbbListValue", ratingMbbListValue);
//					System.out.println("ratingMbbListID : " + ratingMbbListID + " & ratingMbbListValue : "+ ratingMbbListValue);

					String mbbRating = tradingAgreement.getMbbExtRating().getRating();
					String mbbRatingType = tradingAgreement.getMbbExtRating().getRatingType();
					if (!(mbbRating.equals(null) || mbbRating.equals("")) && (thresholdRating != null)) {
						for (int j = 0; j < thresholdRating.length; j++) {
							if (thresholdRating[j].getRatingType().equals(mbbRatingType)
									&& thresholdRating[j].getRating().equals(mbbRating)) {
								ForexHelper fr = new ForexHelper();
								Amount mbbConvertValue = fr.convert(thresholdRating[j].getThresholdAmount(),
										baseCurrency);
								if (mbbConvertValue == null) {
									resultMap.put("mbbAmountError", "true");
									// }else{
									// tradingAgreement.setMbbThresholdAmount(
									// mbbConvertValue);
								}
							}
						}
					}

				}

			}

			resultMap.put("tradingAgreementVal", tradingAgreement);
			resultMap.put("InitialTradingAgreement", tradingAgreement);

			resultMap.put("preEvent", preEvent);
			resultMap.put("indexChange", String.valueOf(indexChange));

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
