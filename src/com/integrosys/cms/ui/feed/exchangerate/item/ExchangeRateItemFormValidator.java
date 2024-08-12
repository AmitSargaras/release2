package com.integrosys.cms.ui.feed.exchangerate.item;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.feed.FeedUIValidator;

/**
 * This class implements validation
 */
public class ExchangeRateItemFormValidator implements java.io.Serializable {

	private static String LOGOBJ = ExchangeRateItemFormValidator.class.getName();

	public static ActionErrors validateInput(ExchangeRateItemForm form, Locale locale) {

		String event = form.getEvent();
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		if (ExchangeRateItemAction.EVENT_SAVE.equals(event)) {
			
			String currencyCode = form.getCurrencyCode();
			String unitPrice = form.getUnitPrice();
			String currencyDescription=form.getCurrencyDescription();

			FeedUIValidator.validateUnitPrices(unitPrice, errors);

			/*
			 * if (!"".equals(unitPrice) && !Validator.checkPattern(unitPrice,
			 * FeedConstants.FOREX_PRICE_REGEX)) { errors.add("unitPrice", new
			 * ActionMessage("error.string.lessthan", "0",
			 * FeedConstants.FOREX_EXCHANGE_MAX_STR));
			 * DefaultLogger.debug(LOGOBJ, "Invalid unitPrice" + unitPrice); }
			 * else if (!(errorCode = Validator.checkNumber(unitPrice, false, 0,
			 * FeedConstants.FOREX_PRICE_MAX)).equals(Validator.ERROR_NONE)) {
			 * errors.add("unitPrice", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
			 * errorCode), "0" , FeedConstants.FOREX_EXCHANGE_MAX_STR));
			 * DefaultLogger.debug(LOGOBJ, "checkNumber unitPrice" + unitPrice);
			 * }
			 */

		/*	if ("".equals(currencyCode)) {
				errors.add("currencyCode", new ActionMessage(FeedConstants.ERROR_NO_SELECTION));
				DefaultLogger.debug(LOGOBJ, "currencyCode" + currencyCode);
			}

		
		else{
			boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getCurrencyCode());
			if( codeFlag == true)
				errors.add("spaceError", new ActionMessage("error.string.noSpace","PartyCode"));
			}
		DefaultLogger.debug(LOGOBJ, "errors" + errors.size());
		if (form.getCurrencyDescription() !=null ) {
			boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getCurrencyDescription());
			if( nameFlag == true)
				errors.add("specialCharacterNameError", new ActionMessage("error.string.invalidCharacter"));
			}*/
			if (!(errorCode = Validator.checkString(form.getCurrencyCode(), true, 0, 4)).equals(Validator.ERROR_NONE)) {
				errors.add("currencyCodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",4 + ""));
			}else if( ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getCurrencyCode())){
				errors.add("currencyCodeError", new ActionMessage("error.string.invalidCharacter"));
			}
		
			if (!(errorCode = Validator.checkString(form.getCurrencyIsoCode(), true, 0, 4)).equals(Validator.ERROR_NONE)) {
				errors.add("currencyISOCodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",4 + ""));
			}else if( ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getCurrencyIsoCode())){
				errors.add("currencyISOCodeError", new ActionMessage("error.string.invalidCharacter"));
			}
			
			if (!(errorCode = Validator.checkString(form.getCurrencyDescription(), true, 0, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("currencyDescriptionError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",50 + ""));
			}else if( ASSTValidator.isValidCurrencyName(form.getCurrencyDescription())){
				errors.add("currencyDescriptionError", new ActionMessage("error.string.invalidCharacter"));
			}	
			
			if (!(errorCode = Validator.checkString(form.getCurrencyIsoCode(), true, 0, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("restrictionTypeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",50 + ""));
			}else if( ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getCurrencyIsoCode())){
				errors.add("restrictionTypeError", new ActionMessage("error.string.invalidCharacter"));
			}
		}

		return errors;
	}
	// end validateInput

}
