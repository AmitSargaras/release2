/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/contract/ContractValidator.java,v 1.10 2004/11/27 04:06:32 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.contract;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainConstant;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.UIValidator;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2004/11/27 04:06:32 $ Tag: $Name: $
 */

public class ContractValidator {
	public static ActionErrors validateInput(ContractForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (!(errorCode = Validator.checkString(aForm.getSecurityID(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("securityID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					30 + ""));
		}

		if (!(errorCode = Validator.checkString(aForm.getProductType(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("productType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					30 + ""));
		}

		if (!(errorCode = Validator.checkString(aForm.getProductSubType(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("productSubType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					30 + ""));
		}

		if (!(errorCode = Validator.checkNumber(aForm.getMinShippingFreq(), false, 0, 100))
				.equals(Validator.ERROR_NONE)) {
			errors.add("minShippingFreq", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					"100"));
		}

		if (!(errorCode = Validator.checkString(aForm.getMainContractNo(), true, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("mainContractNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					"50"));
		}
		if (!(errorCode = Validator.checkDate(aForm.getContractMaturityDate(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("contractMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
					"0", 256 + ""));
		}
		else if ((aForm.getContractMaturityDate() != null) && (aForm.getContractMaturityDate().length() > 0)) {
			Date contractMatDate = DateUtil.convertDate(locale, aForm.getContractMaturityDate());
			if (contractMatDate.before(DateUtil.getDate())) {
				errors.add("contractMaturityDate", new ActionMessage("error.collateral.commodity.futuredate",
						"Maturity date of contract"));
			}
		}

		if (!(errorCode = Validator.checkDate(aForm.getLastShipmentDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("lastShipmentDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		errors = UIValidator.checkAmount(errors, "mainContractPriceCcy", "mainContractPrice", aForm
				.getMainContractPriceCcy(), aForm.getMainContractPrice(), true, 0,
				CommodityMainConstant.MAX_PRICE_DIFF, 7, locale, CommodityMainConstant.MAX_PRICE_DIFF_STR);

		/*
		 * if (!(errorCode =
		 * Validator.checkString(aForm.getMainContractAmtCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("mainContractAmtCcy",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
		 * errorCode), "0", 3 + "")); }
		 * 
		 * if (!(errorCode = Validator.checkNumber(aForm.getMainContractAmt(),
		 * true, 0, CommodityMainConstant.MAX_AMOUNT, 0,
		 * locale)).equals(Validator.ERROR_NONE)) {
		 * errors.add("mainContractAmt", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", CommodityMainConstant.MAX_AMOUNT_STR)); }
		 */

		if (!(errorCode = Validator.checkNumber(aForm.getContractQty(), true, 0, CommodityMainConstant.MAX_QTY, 5,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add("contractQty", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					CommodityMainConstant.MAX_QTY_STR));
		}

		if (!(errorCode = Validator.checkString(aForm.getContractUOM(), true, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("contractUOM", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					40 + ""));
		}

		if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getContractRemarks(), false))
				.equals(Validator.ERROR_NONE)) {
			errors.add("contractRemarks", RemarksValidatorUtil.getErrorMessage(errorCode));
		}

		if (!(errorCode = Validator.checkNumber(aForm.getQuantityDiff(), false, 0, 100, 0, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("quantityDiff", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					"100"));
		}
		else {
			if ((aForm.getQuantityDiff() != null) && (aForm.getQuantityDiff().length() > 0)) {
				if (!(errorCode = Validator.checkString(aForm.getDiffQuantitysign(), true, 0, 5))
						.equals(Validator.ERROR_NONE)) {
					errors.add("diffQuantitySign", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
							errorCode), "0", 5 + ""));
				}
			}
		}

		return errors;
	}
}
