/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/marketablesec/MarketableSecValidationHelper.java,v 1.9 2005/07/29 10:06:25 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.marketablesec;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.CollateralValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/07/29 10:06:25 $
 * Tag: $Name:  $
 */
/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class MarketableSecValidationHelper extends CollateralValidator {

	private static String LOGOBJ = MarketableSecValidationHelper.class.getName();

	public static ActionErrors validateInput(MarketableSecForm aForm, Locale locale, ActionErrors errors) {
		String errorCode;
		final double MAX_NUMBER = Double.parseDouble("99");
		String maximumInterestRate = IGlobalConstant.MAXIMUM_ALLOWED_INTEREST_RATE;
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
		boolean mandatorySubmit = false;

		if (!aForm.getEvent().equals("approve") && !aForm.getEvent().equals("reject")) {
			mandatorySubmit = aForm.getEvent().equals("submit");

			if (aForm.getIsSSC().equals("false")) {
				if (!(errorCode = Validator.checkString(aForm.getValCurrency(), false, 1, 3))
						.equals(Validator.ERROR_NONE)) {
					// errors.add("valCurrency", new
					// ActionMessage("error.string.mandatory", "1", "3"));
					// DefaultLogger.debug(LOGOBJ,
					// "============= aForm.valBefMargin - aForm.valCurrency()
					// ==========>"
					// );
				}
			}

			/*
			 * No longer used. Moved to the Port
			 * 
			 * if (!(errorCode =
			 * Validator.checkString(aForm.getStockCounterCode(), true, 1, 50))
			 * .equals(Validator.ERROR_NONE)) { errors.add("stockCounterCode",
			 * new ActionMessage("error.string.mandatory", "1", "50"));
			 * DefaultLogger.debug(LOGOBJ, "=============
			 * aForm.getStockCounterCode() ==========>" ); }
			 */
			/*if (!(errorCode = Validator.checkString(aForm.getChargeType(), mandatorySubmit, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("chargeType", new ActionMessage("error.string.mandatory", "1", "50"));
				DefaultLogger.debug(LOGOBJ, "============= aForm.getChargeType() ==========>");
			}*/

			/*if (!(errorCode = Validator.checkAmount(aForm.getAmtCharge(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("amtCharge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR));
				DefaultLogger.debug(LOGOBJ, "aForm.getAmtCharge() =" + aForm.getAmtCharge());
			}*/

			if (!(errorCode = Validator.checkAmount(aForm.getCappedPrice(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("cappedPrice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR));
				DefaultLogger.debug(LOGOBJ, "aForm.getCappedPrice() =" + aForm.getCappedPrice());
			}

			DefaultLogger.debug(LOGOBJ, "aForm.getSubTypeCode()=========================" + aForm.getSubTypeValue());

			String subTypeValue = aForm.getSubTypeValue() == null ? "" : aForm.getSubTypeValue();
			if ("MarksecBill".equals(subTypeValue) || "MarksecNonListedLocal".equals(subTypeValue) || 
					"MarksecBondLocal".equals(subTypeValue) || "MarksecBondForeign".equals(subTypeValue)) {
				if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), mandatorySubmit, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("collateralMaturityDate", new ActionMessage("error.date.mandatory", "1", "256"));
				}
			}

		}

		/*
		 * This validation already done in collateral validator level if
		 * (!(errorCode = Validator.checkString(aForm.getLe(), true, 1,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("le", new
		 * ActionMessage("error.string.mandatory", "1", "3"));
		 * DefaultLogger.debug(LOGOBJ, " aForm.getLe() =" + aForm.getLe()); }
		 */

		/*
		 * if (!(errorCode = Validator.checkAmount(aForm.getMinimalFSV(), false,
		 * 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
		 * IGlobalConstant.DEFAULT_CURRENCY, locale))
		 * .equals(Validator.ERROR_NONE)) { errors.add("minimalFSV", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
		 * "0", maximumAmt)); DefaultLogger.debug(LOGOBJ, "... minimalFSV..."); }
		 */

		DefaultLogger.debug(LOGOBJ, "No of Errors..." + errors.size());

		return errors;

	}
}
