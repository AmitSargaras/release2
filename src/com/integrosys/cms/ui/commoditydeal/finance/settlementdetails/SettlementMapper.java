/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/settlementdetails/SettlementMapper.java,v 1.19 2004/12/06 06:00:40 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.settlementdetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.finance.ISettlement;
import com.integrosys.cms.app.commodity.deal.bus.finance.OBSettlement;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2004/12/06 06:00:40 $ Tag: $Name: $
 */

public class SettlementMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		SettlementForm aForm = (SettlementForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		ICommodityDeal dealObj = trxValue.getCommodityDeal();
		int index = Integer.parseInt((String) inputs.get("indexID"));
		ISettlement[] settlementArr = trxValue.getStagingCommodityDeal().getSettlements();

		OBSettlement obToChange;
		if (index == -1) {
			obToChange = new OBSettlement();
		}
		else {
			try {
				obToChange = (OBSettlement) AccessorUtil.deepClone(settlementArr[index]);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		obToChange.setPaymentDate(compareDate(locale, obToChange.getPaymentDate(), aForm.getPaymentDate()));
		if (isEmptyOrNull(aForm.getPaymentAmount())) {
			obToChange.setPaymentAmt(null);
		}
		else {
			try {
				// use currency code from staging when deal is newly created
				String origFaceValueCurrencyCode = (dealObj != null) ? dealObj.getOrigFaceValue().getCurrencyCode()
						: trxValue.getStagingCommodityDeal().getOrigFaceValue().getCurrencyCode();

				obToChange.setPaymentAmt(UIUtil.convertToAmount(locale, origFaceValueCurrencyCode, aForm
						.getPaymentAmount()));

			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		SettlementForm aForm = (SettlementForm) cForm;

		HashMap settlementMap = (HashMap) obj;
		ISettlement settlement = (ISettlement) settlementMap.get("obj");
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		String from_event = (String) inputs.get("from_event");
		ICommodityDeal dealObj;
		if (from_event.equals(EVENT_READ)) {
			dealObj = trxValue.getCommodityDeal();
		}
		else {
			dealObj = trxValue.getStagingCommodityDeal();
		}
		/*
		 * if (dealObj.getOrigFaceValue() != null) {
		 * aForm.setOrigianlFaceValueCcy
		 * (dealObj.getOrigFaceValue().getCurrencyCode()); if
		 * (dealObj.getOrigFaceValue().getAmount() >= 0) { try {
		 * aForm.setOriginalOutstanding
		 * (UIUtil.formatNumber(dealObj.getOrigFaceValue
		 * ().getAmountAsBigDecimal(), 2, locale)); } catch (Exception e) {
		 * throw new MapperException(e.getMessage()); } } }
		 */

		if (dealObj.getDealAmt() != null) {
			aForm.setOrigianlFaceValueCcy(dealObj.getDealAmt().getCurrencyCode());
			if (dealObj.getDealAmt().getAmount() >= 0) {
				try {
					aForm.setOriginalOutstanding(UIUtil.formatNumber(dealObj.getDealAmt().getAmountAsBigDecimal(), 2,
							locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
		}

		Amount balanceAmt = dealObj.getBalanceDealAmt();
		if ((balanceAmt != null) && (balanceAmt.getAmount() >= 0)) {
			try {
				aForm.setBalanceOutstanding(UIUtil.formatNumber(balanceAmt.getAmountAsBigDecimal(), 2, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}

		aForm.setPaymentDate(DateUtil.formatDate(locale, settlement.getPaymentDate()));
		if ((settlement.getPaymentAmt() != null) && (settlement.getPaymentAmt().getAmount() >= 0)) {
			try {
				aForm.setPaymentAmount(UIUtil.formatNumber(settlement.getPaymentAmt().getAmountAsBigDecimal(), 2,
						locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, { "from_event", "java.lang.String", SERVICE_SCOPE }, });
	}

	private static Date compareDate(Locale locale, Date dateOrigin, String dateStage) {
		Date returnDate = DateUtil.convertDate(locale, dateStage);

		if (dateOrigin != null) {
			String originalDate = DateUtil.formatDate(locale, dateOrigin);
			if (originalDate.equals(dateStage)) {
				returnDate = dateOrigin;
			}
		}

		return returnDate;
	}
}
