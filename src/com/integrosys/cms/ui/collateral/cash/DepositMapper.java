package com.integrosys.cms.ui.collateral.cash;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 25, 2003 Time: 4:56:59 PM
 * To change this template use Options | File Templates.
 */

public class DepositMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		DepositForm aForm = (DepositForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICashCollateral iCash = (ICashCollateral) (((ICollateralTrxValue) inputs.get("serviceColObj"))
				.getStagingCollateral());
		ICashDeposit[] obDeposit = iCash.getDepositInfo();

		DefaultLogger.debug(this, "indexID  is:" + inputs.get("indexID") + ":");

		int index = Integer.parseInt((String) inputs.get("indexID"));
		ICashDeposit obToChange = null;

		if (index == -1) {
			obToChange = new OBCashDeposit();
		}
		else {
			obToChange = obDeposit[index];
		}

		boolean isChanged = false;
		try {

			obToChange.setDepositReceiptNo(aForm.getDepositReceiptNo());
			// obToChange.setDepositRefNo(aForm.getDepositRefNo());

			obToChange.setDepositMaturityDate(UIUtil.mapFormString_OBDate(locale, obToChange.getDepositMaturityDate(),
					aForm.getDepMatDate()));
			obToChange.setIssueDate(UIUtil
					.mapFormString_OBDate(locale, obToChange.getIssueDate(), aForm.getIssueDate()));

			obToChange.setDepositCcyCode(aForm.getDepCurr());
			DefaultLogger.debug(this, "deposit currency code: " + obToChange.getDepositCcyCode());
			if (isEmptyOrNull(aForm.getDepAmt())) {
				if (obToChange.getDepositAmount() != null) {
					isChanged = true;
				}
				obToChange.setDepositAmount(null);
			}
			else {
				Amount amt = CurrencyManager.convertToAmount(locale, obToChange.getDepositCcyCode(), aForm.getDepAmt());
				if (obToChange.getDepositAmount() == null) {
					isChanged = true;
				}
				else if (obToChange.getDepositAmount().getAmount() != amt.getAmount()) {
					isChanged = true;
				}
				obToChange.setDepositAmount(amt);
			}
			
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		HashMap returnMap = new HashMap();
		returnMap.put("deposit", obToChange);
		returnMap.put("isChanged", String.valueOf(isChanged));
		return returnMap;

	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		ICashDeposit iDeposit = (ICashDeposit) obj;

		DepositForm aForm = (DepositForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			aForm.setDepositReceiptNo(iDeposit.getDepositReceiptNo());
			// aForm.setDepositRefNo(iDeposit.getDepositRefNo());
			aForm.setDepAmt(UIUtil.mapOBAmount_FormString(locale, iDeposit.getDepositAmount()));
			aForm.setDepMatDate(UIUtil.mapOBDate_FormString(locale, iDeposit.getDepositMaturityDate()));
			aForm.setIssueDate(UIUtil.mapOBDate_FormString(locale, iDeposit.getIssueDate()));
			aForm.setDepCurr(iDeposit.getDepositCcyCode());
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "serviceColObj", "java.lang.Object", SERVICE_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
	}

	/*
	 * private ICashDeposit getItem(ICashDeposit temp[],long itemRef){
	 * ICashDeposit item = null; if(temp == null){ return item; } for(int
	 * i=0;i<temp.length;i++){ if(temp[i].getRefID()==itemRef){ item = temp[i];
	 * }else{ continue; } } return item; }
	 */
}
