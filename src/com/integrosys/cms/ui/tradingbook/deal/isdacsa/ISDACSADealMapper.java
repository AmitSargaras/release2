/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.isdacsa;

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
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal;
import com.integrosys.cms.app.tradingbook.bus.OBISDACSADeal;
import com.integrosys.cms.app.tradingbook.bus.OBISDACSADealDetail;
import com.integrosys.cms.app.tradingbook.bus.OBISDACSADealSummary;
import com.integrosys.cms.app.tradingbook.bus.OBISDACSADealVal;
import com.integrosys.cms.app.tradingbook.trx.IISDACSADealValTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Describe this class. Purpose: Map the form to OB or OB to form for ISDA CSA
 * Deal Description: Map the value from database to the screen or from the
 * screen that user key in to database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/09$ Tag: $Name$
 */

public class ISDACSADealMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "ISDACSADealTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBISDACSADealValTrxValue",
						SERVICE_SCOPE },
				{ "ISDACSADealSummaryTrxValue", "com.integrosys.cms.app.tradingbook.bus.OBISDACSADealSummary",
						SERVICE_SCOPE },
				{ "ISDACSADealDetailTrxValue", "com.integrosys.cms.app.tradingbook.bus.OBISDACSADealDetail",
						SERVICE_SCOPE },
				{ "SingleCashMarginTrxValue", "com.integrosys.cms.app.tradingbook.bus.OBCashMargin", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "InitialISDACSADeal", "java.util.Object", FORM_SCOPE },
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
		String targetOffset = (String) map.get("targetOffset");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		ISDACSADealForm aForm = (ISDACSADealForm) cForm;

		if ("view_isda_deal_detail".equals(event)) {

			OBISDACSADealSummary obISDACSADealSummary = new OBISDACSADealSummary();

			return obISDACSADealSummary;

		}
		else if (event.equals("process_isda_dealdetails")) {

			OBISDACSADeal obISDACSADeal = new OBISDACSADeal();

			obISDACSADeal.setCMSDealID(Long.parseLong(aForm.getSingleCMSDealID()));

			return obISDACSADeal;

		}
		else if (event.equals("maker_close_valuation_confirm")) {

			OBISDACSADealVal obISDACSADealVal = new OBISDACSADealVal();
			return obISDACSADealVal;

		}
		else if (event.equals("maker_input_valuation") || ISDACSADealAction.EVENT_VIEW.equals(event)) {

			OBISDACSADealDetail obISDACSADealDetail = new OBISDACSADealDetail();

			obISDACSADealDetail.setLimitProfileID(Long.parseLong(aForm.getLimitProfileID()));
			obISDACSADealDetail.setAgreementID(Long.parseLong(aForm.getAgreementID()));

			return obISDACSADealDetail;

		}
		else if (event.equals("maker_input_valuation_confirm") || "maker_edit_reject_confirm".equals(event)
				|| event.equals("paginate")) {

			IISDACSADealValTrxValue oldTrxValue = (IISDACSADealValTrxValue) map.get("ISDACSADealTrxValue");
			String[] marketValue = aForm.getMarketValue();
			if (marketValue == null) {
				marketValue = new String[0];
			}
			String[] dealID = aForm.getDealID();
			if (dealID == null) {
				dealID = new String[0];
			}
			// System.out.println(marketValue);

			OBISDACSADealVal[] newISDACSADealVal = new OBISDACSADealVal[dealID.length];

			for (int i = 0; i < dealID.length; i++) {

				newISDACSADealVal[i] = new OBISDACSADealVal();
				Amount valAmt = null;
				// System.out.println("marketValue[i] : " + marketValue[i]);
				String curr = oldTrxValue.getCPAgreementDetail().getBaseCurrency();
				if ((marketValue[i] != null) && !marketValue[i].equals("")) {
					valAmt = new Amount(UIUtil.mapStringToBigDecimal(marketValue[i]), new CurrencyCode(curr));
					// }else{
					// valAmt = new Amount(new
					// BigDecimal(ICMSConstant.DOUBLE_INVALID_VALUE), new
					// CurrencyCode(curr) );
				}
				// System.out.println("valAmt : " + valAmt);
				newISDACSADealVal[i].setMarketValue(valAmt);

			}

			List returnList = new ArrayList();
			returnList.add(targetOffset);
			returnList.add(newISDACSADealVal);

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
			ISDACSADealForm aForm = (ISDACSADealForm) cForm;
			if (obj != null) {
				IISDACSADealVal[] sr = (IISDACSADealVal[]) obj;

				if (sr.length != 0) {
					String dealID[] = new String[sr.length];
					String maxValue[] = new String[sr.length];

					for (int i = 0; i < sr.length; i++) {
						if (sr[i].getMarketValue() != null) {
							//System.out.println("sr[i].getMarketValue()="+sr[i]
							// .getMarketValue());
							// if(sr[i].getMarketValue().getAmount() >= 0){
							maxValue[i] = UIUtil.formatAmount(sr[i].getMarketValue(), 4, locale, false);
							// }else{
							// maxValue[i] = null;
							// }
							// System.out.println("maxValue[i]="+maxValue[i]);
						}
						else {
							maxValue[i] = null;
						}
						dealID[i] = String.valueOf(sr[i].getCMSDealID());
					}
					aForm.setMarketValue(maxValue);
					aForm.setDealID(dealID);
				}

				DefaultLogger.debug(this, "Before putting vector result");
			}
			else {
				aForm.setMarketValue(null);
				aForm.setDealID(null);
			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in ISDACSADealMapper is" + e);
		}
		return null;
	}

	public static int adjustOffset(int offset, int length, int maxSize) {

		int adjustedOffset = offset;

		if (offset >= maxSize) {
			DefaultLogger.debug(ISDACSADealMapper.class.getName(), "offset " + offset + " + length " + length
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
			DefaultLogger.debug(ISDACSADealMapper.class.getName(), "adjusted offset = " + adjustedOffset);
		}

		return adjustedOffset;
	}

}
