/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.gmra;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADealDetail;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADealVal;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Describe this class. Purpose: Map the form to OB or OB to form for GMRA Deal
 * Valuation Description: Map the value from database to the screen or from the
 * screen that user key in to database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class GMRADealValMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "GMRADealValTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBGMRADealValTrxValue", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "InitialGMRADealVal", "java.util.Object", FORM_SCOPE },
				{ "GMRADeal", "com.integrosys.cms.app.tradingbook.bus.OBGMRADeal", SERVICE_SCOPE },
				{ "targetOffset", "java.lang.String", REQUEST_SCOPE }, });

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
		String targetOffset = (String) map.get("targetOffset");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		GMRADealValForm aForm = (GMRADealValForm) cForm;

		if ("maker_update_valuation".equals(event) || GMRADealAction.EVENT_VIEW.equals(event)) {

			OBGMRADealDetail obGMRADealDetail = new OBGMRADealDetail();

			obGMRADealDetail.setLimitProfileID(Long.parseLong(aForm.getLimitProfileID()));
			obGMRADealDetail.setAgreementID(Long.parseLong(aForm.getAgreementID()));

			return obGMRADealDetail;

		}
		else if (event.equals("maker_close_valuation_confirm")) {

			OBGMRADealVal obGMRADealVal = new OBGMRADealVal();
			return obGMRADealVal;

		}
		else if (event.equals("maker_update_valuation_confirm") || "maker_edit_reject_confirm".equals(event)
				|| "paginate".equals(event)) {

			String[] marketPriceCurCode = aForm.getMarketPriceCurCode();
			if (marketPriceCurCode == null) {
				marketPriceCurCode = new String[0];
			}
			String[] marketPrice = aForm.getMarketPrice();
			if (marketPrice == null) {
				marketPrice = new String[0];
			}
			String[] dealID = aForm.getDealID();
			if (dealID == null) {
				dealID = new String[0];
			}
			System.out.println(marketPrice);

			OBGMRADealVal[] newGMRADealVal = new OBGMRADealVal[dealID.length];

			for (int i = 0; i < dealID.length; i++) {

				newGMRADealVal[i] = new OBGMRADealVal();
				Amount valAmt = newGMRADealVal[i].getMarketValue();
				System.out.println("marketPrice[i] : " + marketPrice[i]);
				if ((marketPrice[i] != null) && !marketPrice[i].equals("")) {
					valAmt = new Amount(UIUtil.mapStringToBigDecimal(marketPrice[i]), new CurrencyCode(
							marketPriceCurCode[i]));
				}
				else {
					valAmt = new Amount(new BigDecimal(ICMSConstant.DOUBLE_INVALID_VALUE), new CurrencyCode(
							marketPriceCurCode[i]));
				}
				newGMRADealVal[i].setMarketValue(valAmt);

			}

			List returnList = new ArrayList();
			returnList.add(targetOffset);
			returnList.add(newGMRADealVal);

			return returnList;

		}
		return null;

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
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			GMRADealValForm aForm = (GMRADealValForm) cForm;
			DefaultLogger.debug(this, "obj : " + obj);
			if (obj != null) {
				IGMRADealVal[] sr = (IGMRADealVal[]) obj;

				if (sr.length != 0) {
					String dealID[] = new String[sr.length];
					String maxValue[] = new String[sr.length];
					String maxValueCurCode[] = new String[sr.length];

					for (int i = 0; i < sr.length; i++) {
						if (sr[i].getMarketValue() != null) {
							System.out.println("sr[i].getMarketValue()=" + sr[i].getMarketValue());
							if (sr[i].getMarketValue().getAmount() >= 0) {
								maxValue[i] = UIUtil.formatAmount(sr[i].getMarketValue(), 4, locale, false);
							}
							else {
								maxValue[i] = null;
							}
							System.out.println("maxValue[i]=" + maxValue[i]);
							if ((sr[i].getMarketValue().getCurrencyCode() != null)
									&& !sr[i].getMarketValue().getCurrencyCode().equals("")) {
								maxValueCurCode[i] = sr[i].getMarketValue().getCurrencyCode();
							}
							else {
								maxValueCurCode[i] = null;
							}
							System.out.println("maxValueCurCode[i]=" + maxValueCurCode[i]);
						}
						else {
							maxValue[i] = null;
							maxValueCurCode[i] = null;
						}
						dealID[i] = String.valueOf(sr[i].getCMSDealID());
						System.out.println("maxValueCurCode[i]=" + maxValueCurCode[i]);
						System.out.println("dealID[i]=" + dealID[i]);
					}
					aForm.setMarketPrice(maxValue);
					aForm.setMarketPriceCurCode(maxValueCurCode);
					aForm.setDealID(dealID);
				}

				DefaultLogger.debug(this, "Before putting vector result");
			}
			else {
				aForm.setMarketPrice(null);
				aForm.setMarketPriceCurCode(null);
				aForm.setDealID(null);
			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in SRPGlobalMapper is" + e);
		}
		return null;
	}

	public static int adjustOffset(int offset, int length, int maxSize) {

		int adjustedOffset = offset;

		if (offset >= maxSize) {
			DefaultLogger.debug(GMRADealValMapper.class.getName(), "offset " + offset + " + length " + length
					+ " >= maxSize " + maxSize);
			if (maxSize == 0) {
				// Do not reduce offset further.
				adjustedOffset = 0;
			}
			else if (offset == maxSize) {
				// Reduce.
				adjustedOffset = offset - length;
			}
			else {
				// Rely on "/" = Integer division.
				// Go to the start of the last strip.
				adjustedOffset = maxSize / length * length;
			}
			DefaultLogger.debug(GMRADealValMapper.class.getName(), "adjusted offset = " + adjustedOffset);
		}

		return adjustedOffset;
	}

}
