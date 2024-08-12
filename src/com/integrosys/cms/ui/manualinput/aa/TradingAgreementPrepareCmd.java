/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.IThresholdRating;
import com.integrosys.cms.app.limit.bus.ITradingAgreement;
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

public class TradingAgreementPrepareCmd extends AbstractCommand {

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
				{ "TrxId", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
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
				{ "ratingTable", "java.util.Hashtable", REQUEST_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "InitialTradingAgreement", "java.lang.Object", FORM_SCOPE },
				{ "cpAmountError", "java.lang.String", REQUEST_SCOPE },
				{ "mbbAmountError", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
//		System.out.println("**************inside command class doExecute ****************");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		Hashtable ratingTable = new Hashtable();
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		boolean cpAmountError = false;
		boolean mbbAmountError = false;

		String trxId = (String) map.get("TrxId");
		String preEvent = (String) map.get("preEvent");
		String ind = (String) map.get("indexChange");
		int indexChange = Integer.parseInt(ind) + 1;
		String event = (String) map.get("event");
//		System.out.println("**************inside command class preEvent : " + preEvent + "; indexChange : "+ indexChange);

		try {

			TradingAgreementMapper mapper = new TradingAgreementMapper();
//			System.out.println("**************inside command class event : " + event);
			ILimitProfileTrxValue limitProfileTrxVal = (ILimitProfileTrxValue) map.get("limitProfileTrxVal");
			ITradingAgreement tradingAgreement = (ITradingAgreement) map.get("tradingAgreementVal");
			ITradingAgreement actualTradingAgreement = null;
			IThresholdRating[] thresholdRating = null;
			int i = 0;
//			System.out.println("**************inside command class limitProfileTrxVal : " + limitProfileTrxVal);
//			System.out.println("**************inside command class tradingAgreement : " + tradingAgreement);

			RatingTypeList list = RatingTypeList.getInstance();
			Collection ratingTypeListID = list.getRatingTypeListID();
			Collection ratingTypeListValue = list.getRatingTypeListValue();
			resultMap.put("ratingTypeListID", ratingTypeListID);
			resultMap.put("ratingTypeListValue", ratingTypeListValue);

			if ("maker_add_agreement".equals(event) || "maker_add_editreject".equals(event)) {
				if ((tradingAgreement == null) && (limitProfileTrxVal == null)) {
					tradingAgreement = new OBTradingAgreement();
					tradingAgreement.setBaseCurrency("MYR");

				}
				else if ((tradingAgreement == null) && (limitProfileTrxVal != null)) {

					ILimitProfile lmtProfile = null;
					lmtProfile = limitProfileTrxVal.getStagingLimitProfile();
//					System.out.println("**************inside command class lmtProfile : " + lmtProfile);
					actualTradingAgreement = lmtProfile.getTradingAgreement();
					tradingAgreement = new OBTradingAgreement();
					tradingAgreement = actualTradingAgreement;
//					System.out.println("**************inside command class tradingAgreement : " + tradingAgreement);

					if (tradingAgreement != null) {
						if ((tradingAgreement.getBaseCurrency() == null)
								|| tradingAgreement.getBaseCurrency().equals("")) {
							tradingAgreement.setBaseCurrency("MYR");
						}

						if (tradingAgreement.getCounterPartyExtRating() != null) {
							RatingList ratingCpt = RatingList.getInstance(tradingAgreement.getCounterPartyExtRating()
									.getRatingType());
							Collection ratingCptListID = ratingCpt.getRatingListID();
							Collection ratingCptListValue = ratingCpt.getRatingListValue();
							resultMap.put("ratingCptListID", ratingCptListID);
							resultMap.put("ratingCptListValue", ratingCptListValue);
						}

						if (tradingAgreement.getMbbExtRating() != null) {
							RatingList ratingMbb = RatingList.getInstance(tradingAgreement.getMbbExtRating()
									.getRatingType());
							Collection ratingMbbListID = ratingMbb.getRatingListID();
							Collection ratingMbbListValue = ratingMbb.getRatingListValue();
							resultMap.put("ratingMbbListID", ratingMbbListID);
							resultMap.put("ratingMbbListValue", ratingMbbListValue);
						}
					}
					else {
						tradingAgreement = new OBTradingAgreement();
						tradingAgreement.setBaseCurrency("MYR");
					}

				}
				else if (indexChange > 0) {

					if (tradingAgreement != null) {
						if ((tradingAgreement.getBaseCurrency() == null)
								|| tradingAgreement.getBaseCurrency().equals("")) {
							tradingAgreement.setBaseCurrency("MYR");
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
							RatingList ratingCpt = RatingList.getInstance(ratingType);
							Collection ratingCptListID = ratingCpt.getRatingListID();
							Collection ratingCptListValue = ratingCpt.getRatingListValue();
							resultMap.put("ratingCptListID", ratingCptListID);
							resultMap.put("ratingCptListValue", ratingCptListValue);
//							System.out.println("ratingType : " + ratingType);
//							System.out.println("ratingCptListID : " + ratingCptListID);

							String rating = tradingAgreement.getCounterPartyExtRating().getRating();
							if (!(rating.equals(null) || rating.equals("")) && (thresholdRating != null)) {
								for (int j = 0; j < thresholdRating.length; j++) {
									if (thresholdRating[j].getRatingType().equals(ratingType)
											&& thresholdRating[j].getRating().equals(rating)) {
//										System.out.println("baseCurrency : " + baseCurrency
//												+ " & thresholdRating[j].getThresholdAmount() : "
//												+ thresholdRating[j].getThresholdAmount());
										ForexHelper fr = new ForexHelper();
										Amount cpConvertValue = fr.convert(thresholdRating[j].getThresholdAmount(),
												baseCurrency);
//										System.out.println("convertValue : " + cpConvertValue);
										if (cpConvertValue == null) {
											resultMap.put("cpAmountError", "true");
										}
										else {
											tradingAgreement.setCounterPartyThresholdAmount(cpConvertValue);
										}
									}
								}
							}
						}

						if (tradingAgreement.getMbbExtRating() != null) {
							RatingList ratingMbb = RatingList.getInstance(tradingAgreement.getMbbExtRating()
									.getRatingType());
							Collection ratingMbbListID = ratingMbb.getRatingListID();
							Collection ratingMbbListValue = ratingMbb.getRatingListValue();
							resultMap.put("ratingMbbListID", ratingMbbListID);
							resultMap.put("ratingMbbListValue", ratingMbbListValue);

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
										}
										else {
											tradingAgreement.setMbbThresholdAmount(mbbConvertValue);
										}
									}
								}
							}
						}

					}
					else {
						tradingAgreement = new OBTradingAgreement();
						tradingAgreement.setBaseCurrency("MYR");
					}

				}

			}
			else if ("maker_update_agreement".equals(event) || "maker_update_editreject".equals(event)) {

				if ((tradingAgreement == null) && (limitProfileTrxVal != null)) {
					ILimitProfile lmtProfile = null;
					lmtProfile = limitProfileTrxVal.getStagingLimitProfile();
					actualTradingAgreement = lmtProfile.getTradingAgreement();
					tradingAgreement = new OBTradingAgreement();
					tradingAgreement = actualTradingAgreement;
				}

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
						String ratingType = tradingAgreement.getCounterPartyExtRating().getRatingType();
						RatingList ratingCpt = RatingList.getInstance(ratingType);
						Collection ratingCptListID = ratingCpt.getRatingListID();
						Collection ratingCptListValue = ratingCpt.getRatingListValue();
						resultMap.put("ratingCptListID", ratingCptListID);
						resultMap.put("ratingCptListValue", ratingCptListValue);
//						System.out.println("ratingType : " + ratingType);
//						System.out.println("ratingCptListID : " + ratingCptListID);

						String rating = tradingAgreement.getCounterPartyExtRating().getRating();
						if (!(rating.equals(null) || rating.equals("")) && (thresholdRating != null)) {
							for (int j = 0; j < thresholdRating.length; j++) {
								if (thresholdRating[j].getRatingType().equals(ratingType)
										&& thresholdRating[j].getRating().equals(rating)) {
									ForexHelper fr = new ForexHelper();
									Amount cpConvertValue = fr.convert(thresholdRating[j].getThresholdAmount(),
											baseCurrency);
									if (cpConvertValue == null) {
										resultMap.put("cpAmountError", "true");
									}
									else {
										tradingAgreement.setCounterPartyThresholdAmount(cpConvertValue);
									}
								}
							}
						}
					}

					if (tradingAgreement.getMbbExtRating() != null) {
						RatingList ratingMbb = RatingList.getInstance(tradingAgreement.getMbbExtRating()
								.getRatingType());
						Collection ratingMbbListID = ratingMbb.getRatingListID();
						Collection ratingMbbListValue = ratingMbb.getRatingListValue();
						resultMap.put("ratingMbbListID", ratingMbbListID);
						resultMap.put("ratingMbbListValue", ratingMbbListValue);

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
									}
									else {
										tradingAgreement.setMbbThresholdAmount(mbbConvertValue);
									}
								}
							}
						}
					}

				}

			}
			else if ("maker_view_agreement".equals(event) || "checker_view_agreement".equals(event)) {

				if ((tradingAgreement == null) && (limitProfileTrxVal != null)) {
					ILimitProfile lmtProfile = null;
					lmtProfile = limitProfileTrxVal.getStagingLimitProfile();
					actualTradingAgreement = lmtProfile.getTradingAgreement();
					tradingAgreement = new OBTradingAgreement();
					tradingAgreement = actualTradingAgreement;
				}

			}
			else if ("view".equals(event)) {
				if ((tradingAgreement == null) && (limitProfileTrxVal != null)) {
					ILimitProfile lmtProfile = null;
//					System.out.println("limitProfileTrxVal.getStatus() : " + limitProfileTrxVal.getStatus());
					if (!((limitProfileTrxVal.getStatus().equals(ICMSConstant.STATE_ND)) || (limitProfileTrxVal
							.getStatus().equals(ICMSConstant.STATE_ACTIVE)))) {
						lmtProfile = limitProfileTrxVal.getStagingLimitProfile();
					}
					else {
						lmtProfile = limitProfileTrxVal.getLimitProfile();
					}
//					System.out.println("**********************111 inside TradingAgreementPrepareCmd view lmtProfile : "+ lmtProfile);
					tradingAgreement = lmtProfile.getTradingAgreement();
				}

			}

//			System.out.println("********************** inside TradingAgreementPrepareCmd tradingAgreement : "+ tradingAgreement);
			resultMap.put("limitProfileTrxVal", limitProfileTrxVal);
			resultMap.put("tradingAgreementVal", tradingAgreement);
			resultMap.put("InitialTradingAgreement", tradingAgreement);

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
