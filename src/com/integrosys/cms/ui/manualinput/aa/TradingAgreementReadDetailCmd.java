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
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.IThresholdRating;
import com.integrosys.cms.app.limit.bus.ITradingAgreement;
import com.integrosys.cms.app.limit.bus.OBTradingAgreement;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.ui.common.ForexHelper;

/**
 * Describe this class. Purpose: for Maker to read the value from OB
 * Description: command that let the maker to read the value that want to be
 * edited from OB
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date:$ Tag: $Name$
 */

public class TradingAgreementReadDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE }, };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "InitialTradingAgreement", "java.lang.Object", FORM_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE },
				{ "ratingTypeListID", "java.util.Collection", REQUEST_SCOPE },
				{ "ratingTypeListValue", "java.util.Collection", REQUEST_SCOPE },
				{ "ratingCptListID", "java.util.Collection", REQUEST_SCOPE },
				{ "ratingCptListValue", "java.util.Collection", REQUEST_SCOPE },
				{ "ratingMbbListID", "java.util.Collection", REQUEST_SCOPE },
				{ "ratingMbbListValue", "java.util.Collection", REQUEST_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE }, { "cpAmountError", "java.lang.String", REQUEST_SCOPE },
				{ "mbbAmountError", "java.lang.String", REQUEST_SCOPE }, };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		boolean cpAmountError = false;
		boolean mbbAmountError = false;

		try {
			TradingAgreementMapper mapper = new TradingAgreementMapper();
			String trxId = (String) map.get("TrxId");
			String event = (String) map.get("event");
			String preEvent = (String) map.get("preEvent");
			String indexChange = (String) map.get("indexChange");
//			System.out.println("indexChange : " + indexChange);
			int ind = Integer.parseInt(indexChange);
			ILimitProfileTrxValue limitProfileTrxVal = (ILimitProfileTrxValue) map.get("limitProfileTrxVal");
//			System.out.println("limitProfileTrxVal limitProfileTrxVal : " + limitProfileTrxVal);
			ITradingAgreement tradingAgreement = (ITradingAgreement) map.get("tradingAgreementVal");
			ITradingAgreement actualTradingAgreement = null;
			IThresholdRating[] thresholdRating = null;
			int i = 0;

			RatingTypeList list = RatingTypeList.getInstance();
			Collection ratingTypeListID = list.getRatingTypeListID();
			Collection ratingTypeListValue = list.getRatingTypeListValue();
			result.put("ratingTypeListID", ratingTypeListID);
			result.put("ratingTypeListValue", ratingTypeListValue);

			ILimitProfile lmtProfile = null;
			if ("maker_update_agreement".equals(event) && (ind == 0) && !"maker_edit_aadetail_reject".equals(preEvent)) {
				lmtProfile = limitProfileTrxVal.getLimitProfile();
				actualTradingAgreement = lmtProfile.getTradingAgreement();
				tradingAgreement = new OBTradingAgreement();
				tradingAgreement = actualTradingAgreement;
//				System.out.println("limitProfileTrxVal getLimitProfile lmtProfile : " + lmtProfile);
			}
			else {
				if (tradingAgreement == null) {
					lmtProfile = limitProfileTrxVal.getStagingLimitProfile();
					actualTradingAgreement = lmtProfile.getTradingAgreement();
					tradingAgreement = new OBTradingAgreement();
					tradingAgreement = actualTradingAgreement;
				}
//				System.out.println("limitProfileTrxVal getStagingLimitProfile lmtProfile : " + lmtProfile);
			}

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
				String ratingType = tradingAgreement.getCounterPartyExtRating().getRatingType();
				String rating = tradingAgreement.getCounterPartyExtRating().getRating();
				RatingList ratingCpt = RatingList.getInstance(ratingType);
				Collection ratingCptListID = ratingCpt.getRatingListID();
				Collection ratingCptListValue = ratingCpt.getRatingListValue();
				result.put("ratingCptListID", ratingCptListID);
				result.put("ratingCptListValue", ratingCptListValue);

				if (!(rating.equals(null) || rating.equals("")) && (thresholdRating != null)) {
					for (int j = 0; j < thresholdRating.length; j++) {
						if (thresholdRating[j].getRatingType().equals(ratingType)
								&& thresholdRating[j].getRating().equals(rating)) {
							ForexHelper fr = new ForexHelper();
							Amount cpConvertValue = fr.convert(thresholdRating[j].getThresholdAmount(), baseCurrency);
							if (cpConvertValue == null) {
								result.put("cpAmountError", "true");
							}
							else {
								tradingAgreement.setCounterPartyThresholdAmount(cpConvertValue);
							}
						}
					}
				}
			}

			if (tradingAgreement.getMbbExtRating() != null) {
				String mbbRating = tradingAgreement.getMbbExtRating().getRating();
				String mbbRatingType = tradingAgreement.getMbbExtRating().getRatingType();
				RatingList ratingMbb = RatingList.getInstance(mbbRatingType);
				Collection ratingMbbListID = ratingMbb.getRatingListID();
				Collection ratingMbbListValue = ratingMbb.getRatingListValue();
				result.put("ratingMbbListID", ratingMbbListID);
				result.put("ratingMbbListValue", ratingMbbListValue);

				if (!(mbbRating.equals(null) || mbbRating.equals("")) && (thresholdRating != null)) {
					for (int j = 0; j < thresholdRating.length; j++) {
						if (thresholdRating[j].getRatingType().equals(mbbRatingType)
								&& thresholdRating[j].getRating().equals(mbbRating)) {
							ForexHelper fr = new ForexHelper();
							Amount mbbConvertValue = fr.convert(thresholdRating[j].getThresholdAmount(), baseCurrency);
							if (mbbConvertValue == null) {
								result.put("mbbAmountError", "true");
							}
							else {
								tradingAgreement.setMbbThresholdAmount(mbbConvertValue);
							}
						}
					}
				}
			}

			result.put("tradingAgreementVal", tradingAgreement);
			result.put("InitialTradingAgreement", tradingAgreement);
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
