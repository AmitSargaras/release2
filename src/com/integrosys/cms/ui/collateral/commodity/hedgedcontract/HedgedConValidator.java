/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/hedgedcontract/HedgedConValidator.java,v 1.9 2004/07/22 12:35:49 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.hedgedcontract;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainConstant;
import com.integrosys.cms.ui.common.UIValidator;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2004/07/22 12:35:49 $ Tag: $Name: $
 */

public class HedgedConValidator {
	public static ActionErrors validateInput(HedgedConForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (!(errorCode = Validator.checkString(aForm.getSecurityID(), true, 0, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("securityID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					20 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getGlobalTreasuryRef(), true, 0, 50))
				.equals(Validator.ERROR_NONE)) {
			errors.add("globalTreasuryRef", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 50 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getDealDate(), true, locale)).equals(Validator.ERROR_NONE)) {
			errors
					.add("dealDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
							256 + ""));
		}
		else if ((aForm.getDealDate() != null) && (aForm.getDealDate().length() > 0)) {
			Date dealDate = DateUtil.convertDate(locale, aForm.getDealDate());
			if (dealDate.after(DateUtil.getDate())) {
				errors.add("dealDate", new ActionMessage("error.collateral.commodity.not.futuredate", "Deal date"));
			}
		}

		if (!(errorCode = Validator.checkString(aForm.getCounterParty(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("counterParty", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		errors = UIValidator.checkAmount(errors, "dealAmtCcy", "dealAmt", aForm.getDealAmtCcy(), aForm.getDealAmt(),
				false, 0, CommodityMainConstant.MAX_AMOUNT, 0, locale, CommodityMainConstant.MAX_AMOUNT_STR);
		/*
		 * if (!(errorCode = Validator.checkNumber(aForm.getDealAmt(), false, 0,
		 * CommodityMainConstant.MAX_AMOUNT, 0,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("dealAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", CommodityMainConstant.MAX_AMOUNT_STR)); } else if
		 * (aForm.getDealAmt() != null && aForm.getDealAmt().length() > 0) { if
		 * (!(errorCode = Validator.checkString(aForm.getDealAmtCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("dealAmtCcy", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 3 + "")); } }
		 */
		if (!(errorCode = Validator.checkString(aForm.getHedgedAgreeRef(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("hedgedAgreeRef", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					30 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getHedgedAgreeDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("hedgedAgreeDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else if ((aForm.getHedgedAgreeDate() != null) && (aForm.getHedgedAgreeDate().length() > 0)) {
			Date hedgeAgreeDate = DateUtil.convertDate(locale, aForm.getHedgedAgreeDate());
			if (hedgeAgreeDate.after(DateUtil.getDate())) {
				errors.add("hedgedAgreeDate", new ActionMessage("error.collateral.commodity.not.futuredate",
						"Date of Hedging Agreement"));
			}
		}

		if (!(errorCode = Validator.checkInteger(aForm.getMargin(), true, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors
					.add("margin", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
							100 + ""));
		}

		return errors;
	}
}
