/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/generalinfo/cashdeposit/CashDepositMapper.java,v 1.8 2005/08/19 08:12:52 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.generalinfo.cashdeposit;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.deal.bus.cash.IDealCashDeposit;
import com.integrosys.cms.app.commodity.deal.bus.cash.OBDealCashDeposit;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/08/19 08:12:52 $ Tag: $Name: $
 */

public class CashDepositMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		CashDepositForm aForm = (CashDepositForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		int index = Integer.parseInt((String) inputs.get("indexID"));
		IDealCashDeposit[] depositArr = trxValue.getStagingCommodityDeal().getCashDeposit();

		OBDealCashDeposit obToChange;
		if (index == -1) {
			obToChange = new OBDealCashDeposit();
		}
		else {
			try {
				obToChange = (OBDealCashDeposit) AccessorUtil.deepClone(depositArr[index]);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		obToChange.setDepositType(aForm.getTypeCashHolding());
		obToChange.setReferenceNo(aForm.getDepositNumber());
		if (isEmptyOrNull(aForm.getDepositAmt())) {
			obToChange.setAmount(null);
		}
		else {
			try {
				obToChange.setAmount(UIUtil.convertToAmount(locale, aForm.getDepositCcy(), aForm.getDepositAmt()));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
		obToChange.setLocationCountryCode(aForm.getCashLocation());
		obToChange.setMaturityDate(compareDate(locale, obToChange.getMaturityDate(), aForm.getDepositMaturityDate()));

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		CashDepositForm aForm = (CashDepositForm) cForm;
		IDealCashDeposit deposit = (IDealCashDeposit) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		aForm.setTypeCashHolding(deposit.getDepositType());
		aForm.setDepositNumber(deposit.getReferenceNo());
		if ((deposit.getAmount() != null) && (deposit.getAmount().getCurrencyCode() != null)
				&& (deposit.getAmount().getAmount() >= 0)) {
			aForm.setDepositCcy(deposit.getAmount().getCurrencyCode());
			try {
				aForm.setDepositAmt(UIUtil.formatNumber(deposit.getAmount().getAmountAsBigDecimal(), 0, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}

		aForm.setCashLocation(deposit.getLocationCountryCode());
		aForm.setDepositMaturityDate(DateUtil.formatDate(locale, deposit.getMaturityDate()));

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, });
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
