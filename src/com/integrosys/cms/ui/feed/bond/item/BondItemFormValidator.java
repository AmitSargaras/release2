package com.integrosys.cms.ui.feed.bond.item;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.feed.FeedConstants;

/**
 * This class implements validation
 */
public class BondItemFormValidator implements java.io.Serializable {
	
	private static String LOGOBJ = BondItemFormValidator.class.getName();

	public static ActionErrors validateInput(BondItemForm form, Locale locale) {

		// Only for save event.

		ActionErrors errors = new ActionErrors();

		// Assume currency code valid.

		// ticker is no longer mandatory since reuters is not able to provide
		// the info
		// String ticker = form.getTicker();
		String scriptCode = form.getBondCode();
		String name = form.getName();
		String isinCode = form.getIsinCode();
		String issueDate = form.getIssueDate();
		String maturityDate = form.getMaturityDate();
		String couponRate = form.getCouponRate();
		String rating = form.getRating();
		String currentUnitPrice = form.getUnitPrice();
		String errorCode = "";
		
		
		if(scriptCode == null || scriptCode.equals("")){
			errors.add("bondCode",new ActionMessage("error.string.mandatory"));
		}
		else if(!(errorCode = Validator.checkString(scriptCode, true, 1, 30)).equals(Validator.ERROR_NONE)){
			errors.add("bondCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
					new Integer(30)));
		}
		else{
			boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(scriptCode);
			if( nameFlag == true)
				errors.add("specialCharacterNameBondCodeError", new ActionMessage("error.string.invalidCharacter"));
		}
		
		if(name == null || name.equals("")){
			errors.add("name",new ActionMessage("error.string.mandatory"));
		}else{
			if (!(errorCode = Validator.checkString(name, true, 1, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("name", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
						new Integer(30)));
			}
			else{
				boolean nameFlag = ASSTValidator.isValidPercentRoundBrackets(name);
				if( nameFlag == true)
					errors.add("specialCharacterBondNameError", new ActionMessage("error.string.invalidCharacter"));
			}
		}	
		
		
		if(isinCode == null || isinCode.equals("")){
			errors.add("isinCode",new ActionMessage("error.string.mandatory"));
		}else{	
			boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(isinCode);
			
			if (!(errorCode = Validator.checkString(isinCode, true, 1, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("isinCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
						new Integer(30)));
			}
			else if( nameFlag == true){
					errors.add("specialCharacterNameIsInCodeError", new ActionMessage("error.string.invalidCharacter"));
			}		
		}
		
		if(rating == null || rating.equals("")){
			errors.add("rating",new ActionMessage("error.string.mandatory"));
		}else{
			if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkString(rating, true, 1, 30))){
				errors.add("rating", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
						new Integer(30)));
			}
		}	
		
		if(maturityDate == null || maturityDate.equals("")){
			errors.add("maturityDate",new ActionMessage("error.string.mandatory"));
		}else{
			if (!(errorCode = Validator.checkDate(maturityDate, false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("maturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}else if ((maturityDate != null) && !maturityDate.equals("")) {
				if (DateUtil.convertDate(locale, maturityDate).before(new Date())) {
					errors.add("maturityDate", new ActionMessage(FeedConstants.ERROR_PAST_DATE));
				}
			}
		}	
		
		
		if(issueDate == null || issueDate.equals("")){
			errors.add("issueDate",new ActionMessage("error.string.mandatory"));
		}else{
			if (!(errorCode = Validator.checkDate(issueDate, false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("issueDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}else if (DateUtil.convertDate(locale, issueDate).after(new Date())) {
				errors.add("issueDate", new ActionMessage(FeedConstants.ERROR_FUTURE_DATE));
			}
		}	
		
		if(couponRate == null || couponRate.equals("")){
			errors.add("couponRate",new ActionMessage("error.string.mandatory"));
		}else{
			
			if (!(errorCode = Validator.checkNumber(couponRate,true, 0, 999.99,3,locale)).equals(Validator.ERROR_NONE)) {
					errors.add("couponRate",new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,errorCode), "0", "999.99"));
				}
			else if (!"".equals(couponRate) && checkString(couponRate) && !Validator.checkPattern(couponRate, FeedConstants.FOREX_PRICE_FORMAT)) {
				errors.add("couponRate", new ActionMessage(FeedConstants.ERROR_INVALID));
				DefaultLogger.debug(LOGOBJ, " couponRate= "+ couponRate);
			}
		}
		
		if(currentUnitPrice == null || currentUnitPrice.equals("")){
			errors.add("unitPrice",new ActionMessage("error.string.mandatory"));
		}else {
			if (!(errorCode = Validator.checkNumber(currentUnitPrice, true, 0, 99999.99,3,locale)).equals(Validator.ERROR_NONE)) {
				errors.add("unitPrice" , new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,errorCode), "0", "99999.99"));
				DefaultLogger.debug(LOGOBJ, "unitPrice= "+ currentUnitPrice);
			}
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
	// end validateInput

}
