/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.commodity.common.AmountConversionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.IExternalRating;
import com.integrosys.cms.app.limit.bus.IThresholdRating;
import com.integrosys.cms.app.limit.bus.ITradingAgreement;
import com.integrosys.cms.app.limit.bus.OBExternalRating;
import com.integrosys.cms.app.limit.bus.OBTradingAgreement;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.ui.common.ForexHelper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Describe this class. Purpose: Map the form to OB or OB to form for Trading
 * Agreement Description: Map the value from database to the screen or from the
 * screen that user key in to OB
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class TradingAgreementMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "lmtProfileTrxValue", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "InitialTradingAgreement", "java.lang.Object", FORM_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });

	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */

	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		String cpAmountError = "false";
		String mbbAmountError = "false";

		try {
			TradingAgreementForm aForm = (TradingAgreementForm) cForm;
			ILimitProfileTrxValue limitProfileTrxVal = (ILimitProfileTrxValue) map.get("limitProfileTrxVal");
			ITradingAgreement existTradingAgreement = (ITradingAgreement) map.get("tradingAgreementVal");
			IThresholdRating[] thresholdRating = null;
			int i = 0;

			ITradingAgreement newTradingAgreement = new OBTradingAgreement();
			if (existTradingAgreement != null) {
				newTradingAgreement.setThresholdRatingList(existTradingAgreement.getThresholdRatingList());
			}

			newTradingAgreement.setAgreementType(aForm.getAgreementType());
			newTradingAgreement.setCounterPartyBranch(aForm.getCounterPartyBranch());

			Amount valAmt = new Amount();
			if (!(aForm.getMinTransfer().equals(null) || aForm.getMinTransfer().equals(""))) {
				valAmt = new Amount(UIUtil.mapStringToBigDecimal(aForm.getMinTransfer()), new CurrencyCode(aForm
						.getMinTransCurCode()));
				newTradingAgreement.setMinTransferAmount(valAmt);
			}
			else {
				valAmt = new Amount(ICMSConstant.DOUBLE_INVALID_VALUE, aForm.getMinTransCurCode());
				newTradingAgreement.setMinTransferAmount(valAmt);
			}

			IExternalRating counterPartyRating = null;
			if (newTradingAgreement.getCounterPartyExtRating() != null) {
				counterPartyRating = newTradingAgreement.getCounterPartyExtRating();
			}
			else {
				counterPartyRating = new OBExternalRating();
			}
			if (!(aForm.getCountRatingType().equals(null) || aForm.getCountRatingType().equals(""))
					|| !(aForm.getCounterpartyRating().equals(null) || aForm.getCounterpartyRating().equals(""))) {
				counterPartyRating.setRatingType(aForm.getCountRatingType());
				counterPartyRating.setRating(aForm.getCounterpartyRating());
				newTradingAgreement.setCounterPartyExtRating(counterPartyRating);
			}
			else {
				newTradingAgreement.setCounterPartyExtRating(null);
			}

			IExternalRating maybankRating = null;
			if (newTradingAgreement.getMbbExtRating() != null) {
				maybankRating = newTradingAgreement.getMbbExtRating();
			}
			else {
				maybankRating = new OBExternalRating();
			}
			if (!(aForm.getMaybankRatingType().equals(null) || aForm.getMaybankRatingType().equals(""))
					|| !(aForm.getMaybankRating().equals(null) || aForm.getMaybankRating().equals(""))) {
				maybankRating.setRatingType(aForm.getMaybankRatingType());
				maybankRating.setRating(aForm.getMaybankRating());
				newTradingAgreement.setMbbExtRating(maybankRating);
			}
			else {
				newTradingAgreement.setMbbExtRating(null);
			}

			newTradingAgreement.setAgreeIntRateType(aForm.getAgreeIntRateType());
			newTradingAgreement.setBaseCurrency(aForm.getBaseCurrency());

			newTradingAgreement.setAgentBankName(aForm.getAgentBankName());
			newTradingAgreement.setAgentBankAddress(aForm.getAgentBankAddress());
			newTradingAgreement.setBankClearanceID(aForm.getBankClearanceId());
			newTradingAgreement.setBankAccountID(aForm.getBankAccountId());
			newTradingAgreement.setClearingDesc(aForm.getClearingDesc());
			newTradingAgreement.setNotificationTime(aForm.getNotificationTime());
			newTradingAgreement.setValuationTime(aForm.getValuationTime());

			List list = newTradingAgreement.getThresholdRatingList();
			if (list != null) {
				thresholdRating = new IThresholdRating[list.size()];
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					thresholdRating[i] = (IThresholdRating) iter.next();
					i++;
				}
			}

			CurrencyCode baseCurrency = new CurrencyCode(aForm.getBaseCurrency());
			if (newTradingAgreement.getCounterPartyExtRating() != null) {
				String countRatingType = newTradingAgreement.getCounterPartyExtRating().getRatingType();
				String countRating = newTradingAgreement.getCounterPartyExtRating().getRating();

				if (!(countRating.equals(null) || countRating.equals("") || countRatingType.equals(null) || countRatingType
						.equals(""))
						&& (thresholdRating != null)) {
					for (int j = 0; j < thresholdRating.length; j++) {
						if (thresholdRating[j].getRatingType().equals(countRatingType)
								&& thresholdRating[j].getRating().equals(countRating)) {
							ForexHelper fr = new ForexHelper();
							Amount cpConvertValue = fr.convert(thresholdRating[j].getThresholdAmount(), baseCurrency);

							if (cpConvertValue == null) {
								cpAmountError = "true";
							}
							else {
								newTradingAgreement.setCounterPartyThresholdAmount(cpConvertValue);
								break;
							}
						}
						else {
							newTradingAgreement.setCounterPartyThresholdAmount(null);
						}
					}
				}
				else {
					newTradingAgreement.setCounterPartyThresholdAmount(null);
				}
			}
			else {
				newTradingAgreement.setCounterPartyThresholdAmount(null);
			}

			if (newTradingAgreement.getMbbExtRating() != null) {
				String mbbRatingType = newTradingAgreement.getMbbExtRating().getRatingType();
				String mbbRating = newTradingAgreement.getMbbExtRating().getRating();
				if (!(mbbRating.equals(null) || mbbRating.equals("") || mbbRatingType.equals(null) || mbbRatingType
						.equals(""))
						&& (thresholdRating != null)) {
					for (int k = 0; k < thresholdRating.length; k++) {
						if (thresholdRating[k].getRatingType().equals(mbbRatingType)
								&& thresholdRating[k].getRating().equals(mbbRating)) {
							ForexHelper fr = new ForexHelper();
							Amount mbbConvertValue = fr.convert(thresholdRating[k].getThresholdAmount(), baseCurrency);
							if (mbbConvertValue == null) {
								mbbAmountError = "true";
							}
							else {
								newTradingAgreement.setMbbThresholdAmount(mbbConvertValue);
								break;
							}
						}
						else {
							newTradingAgreement.setMbbThresholdAmount(null);
						}
					}
				}
				else {
					newTradingAgreement.setMbbThresholdAmount(null);
				}
			}
			else {
				newTradingAgreement.setMbbThresholdAmount(null);
			}

//			System.out.println("---------------------------------end of newTradingAgreement: " + newTradingAgreement);
			return newTradingAgreement;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException();
		}

	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		String index = (String) map.get("index");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {

			TradingAgreementForm aForm = (TradingAgreementForm) cForm;
			if (obj != null) {
				ITradingAgreement sr = (ITradingAgreement) obj;

				aForm.setAgreementType(sr.getAgreementType());
				if (sr.getCounterPartyBranch() != null) {
					aForm.setCounterPartyBranch(sr.getCounterPartyBranch());
				}
				else {
					aForm.setCounterPartyBranch(null);
				}

				if (sr.getMinTransferAmount() != null) {
					Amount valAmt = sr.getMinTransferAmount();
					aForm.setMinTransCurCode(valAmt.getCurrencyCode());
					if (valAmt.getAmount() >= 0) {
						String minTransferAmt = UIUtil.formatAmount(valAmt, 4, locale, false);
						aForm.setMinTransfer(minTransferAmt);
					}
					else {
						aForm.setMinTransfer(null);
					}
				}
				else {
					aForm.setMinTransCurCode(null);
					aForm.setMinTransfer(null);
				}

				if (sr.getCounterPartyExtRating() != null) {
					IExternalRating counterPartyRating = sr.getCounterPartyExtRating();
					if (!(counterPartyRating.getRatingType().equals(null) || counterPartyRating.getRatingType().equals(
							""))) {
						aForm.setCountRatingType(counterPartyRating.getRatingType());
					}
					if (!(counterPartyRating.getRating().equals(null) || counterPartyRating.getRating().equals(""))) {
						aForm.setCounterpartyRating(counterPartyRating.getRating());
					}
				}
				else {
					aForm.setCountRatingType(null);
					aForm.setCounterpartyRating(null);
				}

				if (sr.getMbbExtRating() != null) {
					IExternalRating maybankRat = sr.getMbbExtRating();
					if (!(maybankRat.getRatingType().equals(null) || maybankRat.getRatingType().equals(""))) {
						aForm.setMaybankRatingType(maybankRat.getRatingType());
					}
					if (!(maybankRat.getRating().equals(null) || maybankRat.getRating().equals(""))) {
						aForm.setMaybankRating(maybankRat.getRating());
					}
				}
				else {
					aForm.setMaybankRatingType(null);
					aForm.setMaybankRating(null);
				}

				aForm.setAgreeIntRateType(sr.getAgreeIntRateType());
				aForm.setBaseCurrency(sr.getBaseCurrency());
				if (sr.getCounterPartyThresholdAmount() != null) {
					// String curCode =
					// sr.getCounterPartyThresholdAmount().getCurrencyCode();
					// String amt =
					// String.valueOf(sr.getCounterPartyThresholdAmount
					// ().getAmount());
					String thresholdAmt = UIUtil.formatAmount(sr.getCounterPartyThresholdAmount(), 4, locale, true);
					aForm.setCounterPartyThresholdAmt(thresholdAmt);
				}
				else {
					aForm.setCounterPartyThresholdAmt(null);
				}

				if (sr.getMbbThresholdAmount() != null) {
					// String curCode =
					// sr.getMbbThresholdAmount().getCurrencyCode();
					// String amt =
					// String.valueOf(sr.getMbbThresholdAmount().getAmount());
					// String thresholdAmt = curCode + " " + amt;
					String thresholdAmt = UIUtil.formatAmount(sr.getMbbThresholdAmount(), 4, locale, true);
					aForm.setMbbThresholdAmt(thresholdAmt);
				}
				else {
					aForm.setMbbThresholdAmt(null);
				}

				aForm.setAgentBankName(sr.getAgentBankName());
				aForm.setAgentBankAddress(sr.getAgentBankAddress());
				aForm.setBankClearanceId(sr.getBankClearanceID());
				aForm.setBankAccountId(sr.getBankAccountID());
				aForm.setClearingDesc(sr.getClearingDesc());
				aForm.setNotificationTime(sr.getNotificationTime());
				aForm.setValuationTime(sr.getValuationTime());

			}
			else {

				aForm.setAgreementType(null);
				aForm.setCounterPartyBranch(null);
				aForm.setMinTransCurCode(null);
				aForm.setMinTransfer(null);
				aForm.setCountRatingType(null);
				aForm.setCounterpartyRating(null);
				aForm.setMaybankRatingType(null);
				aForm.setMaybankRating(null);
				aForm.setAgreeIntRateType(null);
				aForm.setBaseCurrency(null);
				aForm.setCounterPartyThresholdAmt(null);
				aForm.setAgentBankName(null);
				aForm.setAgentBankAddress(null);
				aForm.setBankClearanceId(null);
				aForm.setBankAccountId(null);
				aForm.setClearingDesc(null);
				aForm.setNotificationTime(null);
				aForm.setValuationTime(null);

			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in TradingAgreementMapper is" + e);
		}
		return null;
	}

	public Amount getThresholdAmountBaseCurrency(String currencyCode, Amount thresholdAmt)
			throws AmountConversionException {

		try {
			Amount convertedAmt = null;
			if (thresholdAmt != null) {
				convertedAmt = AmountConversion.getConversionAmount(thresholdAmt, currencyCode);
			}
			return convertedAmt;
		}
		catch (Exception e) {
			throw new AmountConversionException(e.getMessage());
		}
	}
}
