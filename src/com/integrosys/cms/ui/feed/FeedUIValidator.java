package com.integrosys.cms.ui.feed;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: May 24, 2007 Time: 10:59:41 AM
 * To change this template use File | Settings | File Templates.
 */

public class FeedUIValidator implements java.io.Serializable {

	private static String LOGOBJ = FeedUIValidator.class.getName();

	public static ActionErrors validateUnitPricesArr(String[] updatedUnitPricesArr, ActionErrors errors) {
		String errorCode = "";
		if (updatedUnitPricesArr != null) {
			for (int i = 0; i < updatedUnitPricesArr.length; i++) {
				String updatedUnitPricesArrStr = updatedUnitPricesArr[i];
				updatedUnitPricesArrStr=UIUtil.removeComma(updatedUnitPricesArrStr);
				if (!(errorCode = Validator
						.checkNumber(updatedUnitPricesArrStr, true, 0, FeedConstants.FOREX_PRICE_MAX))
						.equals(Validator.ERROR_NONE)) {
					errors.add("updatedUnitPrices." + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
							errorCode), "0", FeedConstants.FOREX_EXCHANGE_MAX_STR));
					DefaultLogger.debug(LOGOBJ, "checkNumber updatedUnitPricesArr[" + i + "]= "
							+ updatedUnitPricesArr[i]);
				}
				else if (!"".equals(updatedUnitPricesArrStr) && checkString(updatedUnitPricesArrStr)
						&& !Validator.checkPattern(updatedUnitPricesArrStr, FeedConstants.FOREX_PRICE_FORMAT)) {
					errors.add("updatedUnitPrices." + i, new ActionMessage(FeedConstants.ERROR_INVALID));
					DefaultLogger.debug(LOGOBJ, " checkPattern updatedUnitPricesArr[" + i + "]= "
							+ updatedUnitPricesArr[i]);
				}
			}
		}
		DefaultLogger.debug(LOGOBJ, " validateUnitPricesArr No of Errors = " + errors.size());
		return errors;
	}
	
	
	public static ActionErrors validateCurrencyDiscription(String[] currencyDescriptionArr, ActionErrors errors) {
		String errorCode = "";
		if (currencyDescriptionArr != null) {
			for (int i = 0; i < currencyDescriptionArr.length; i++) {
				if (!(errorCode = Validator.checkString(currencyDescriptionArr[i], true, 0, 50)).equals(Validator.ERROR_NONE)) {
					errors.add("currencyDescriptionError"+ i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							50 + ""));
				}else if( ASSTValidator.isValidCurrencyName(currencyDescriptionArr[i])){
					errors.add("currencyDescriptionError"+ i, new ActionMessage("error.string.invalidCharacter"));
				}
			}
		}
		DefaultLogger.debug(LOGOBJ, " validateUnitPricesArr No of Errors = " + errors.size());
		return errors;
	}


	public static ActionErrors validateGoldUnitPricesArr(String[] updatedUnitPricesArr, ActionErrors errors) {
		String errorCode = "";
		if (updatedUnitPricesArr != null) {
			for (int i = 0; i < updatedUnitPricesArr.length; i++) {
				if (!(errorCode = Validator
						.checkNumber(updatedUnitPricesArr[i], true, 0, FeedConstants.GOLD_PRICE_MAX))
						.equals(Validator.ERROR_NONE)) {
					errors.add("updatedUnitPrices." + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
							errorCode), "0", FeedConstants.GOLD_EXCHANGE_MAX_STR));
					DefaultLogger.debug(LOGOBJ, "checkNumber updatedUnitPricesArr[" + i + "]= "
							+ updatedUnitPricesArr[i]);
				}
				else if (!"".equals(updatedUnitPricesArr[i]) && checkString(updatedUnitPricesArr[i])
						&& !Validator.checkPattern(updatedUnitPricesArr[i], FeedConstants.FOREX_PRICE_FORMAT)) {
					errors.add("updatedUnitPrices." + i, new ActionMessage(FeedConstants.ERROR_INVALID));
					DefaultLogger.debug(LOGOBJ, " checkPattern updatedUnitPricesArr[" + i + "]= "
							+ updatedUnitPricesArr[i]);
				}
			}
		}
		DefaultLogger.debug(LOGOBJ, " validateUnitPricesArr No of Errors = " + errors.size());
		return errors;
	}

	public static ActionErrors validateUnitPrices(String updatedUnitPrices, ActionErrors errors) {
		String errorCode = "";
		
		updatedUnitPrices=UIUtil.removeComma(updatedUnitPrices); //Phase 3 CR:comma separated
		
		if (!AbstractCommonMapper.isEmptyOrNull(updatedUnitPrices)) {
			if (!(errorCode = Validator.checkNumber(updatedUnitPrices, true, 0, FeedConstants.FOREX_PRICE_MAX))
					.equals(Validator.ERROR_NONE)) {
				errors.add("unitPrice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						FeedConstants.FOREX_EXCHANGE_MAX_STR));
				DefaultLogger.debug(LOGOBJ, "checkNumber unitPrice =" + updatedUnitPrices);
			}
			else if (!"".equals(updatedUnitPrices) && checkString(updatedUnitPrices)
					&& !Validator.checkPattern(updatedUnitPrices, FeedConstants.FOREX_PRICE_FORMAT)) {
				errors.add("unitPrice", new ActionMessage(FeedConstants.ERROR_INVALID));
				DefaultLogger.debug(LOGOBJ, " checkPattern unitPrice = " + updatedUnitPrices);
			}
		}else{
			errors.add("unitPrice", new ActionMessage("error.string.mandatory"));
		}

		return errors;
	}
	
	public static ActionErrors validateGoldUnitPrices(String updatedUnitPrices, ActionErrors errors) {
		String errorCode = "";
		if (!AbstractCommonMapper.isEmptyOrNull(updatedUnitPrices)) {
			if (!(errorCode = Validator.checkNumber(updatedUnitPrices, false, 0, FeedConstants.GOLD_PRICE_MAX))
					.equals(Validator.ERROR_NONE)) {
				errors.add("unitPrice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						FeedConstants.GOLD_EXCHANGE_MAX_STR));
				DefaultLogger.debug(LOGOBJ, "checkNumber unitPrice =" + updatedUnitPrices);
			}
			else if (!"".equals(updatedUnitPrices) && checkString(updatedUnitPrices)
					&& !Validator.checkPattern(updatedUnitPrices, FeedConstants.FOREX_PRICE_FORMAT)) {
				errors.add("unitPrice", new ActionMessage(FeedConstants.ERROR_INVALID));
				DefaultLogger.debug(LOGOBJ, " checkPattern unitPrice = " + updatedUnitPrices);
			}
		}

		return errors;
	}

	public static ActionErrors validateFundSize(String fundSize, ActionErrors errors, Locale locale) {
		String errorCode = "";
		String maximumFundSize = FeedConstants.FUND_SIZE_MAX_STR;
		double MAX_NUMBER = FeedConstants.FUND_SIZE_MAX;
		if (!AbstractCommonMapper.isEmptyOrNull(fundSize)) {
			if (fundSize.indexOf('.')>=0) {
				errors.add("fundSize",new ActionMessage("error.number.decimalexceeded"));
			}
			else if (!(errorCode = Validator.checkNumber(fundSize, false, 0, MAX_NUMBER,0,locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("fundSize", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						maximumFundSize));
			}
            else if (fundSize.indexOf(',')>=0) {
                errors.add("fundSize", new ActionMessage("error.number.format"));
            }
			DefaultLogger.debug(LOGOBJ, "checkNumber FundSize = " + fundSize);		
		}
		return errors;
	}

	private static boolean checkString(String str) {
		boolean check = false;
		if (str != null) {
			check = str.indexOf(".") > 0;
		}
		return check;

	}

}
