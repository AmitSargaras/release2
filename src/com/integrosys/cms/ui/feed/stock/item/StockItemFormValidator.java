package com.integrosys.cms.ui.feed.stock.item;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.feed.FeedConstants;

/**
 * This class implements validation
 */
public class StockItemFormValidator implements java.io.Serializable {

	private static String LOGOBJ = StockItemFormValidator.class.getName();

	public static ActionErrors validateInput(StockItemForm form, Locale locale) {

		// Only for save event.
		
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		String scriptName = form.getScriptName();
		String scriptCode = form.getScriptCode();
		String scriptValue = form.getScriptValue();
		String facevalue = form.getFaceValue();
		
		//Phase 3 CR:comma separated
		scriptValue=UIUtil.removeComma(scriptValue);
		facevalue=UIUtil.removeComma(facevalue);
		
		String stockName = form.getStockExchangeName();
		String stockType = form.getStockType();
		
		if(scriptName == null && scriptName.equals("")){
			errors.add("scriptNameError", new ActionMessage("error.string.mandatory"));
		}else{
			if (!(errorCode = Validator.checkString(scriptName, true, 1, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("scriptNameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
						new Integer(30)));
			}
			else{
				boolean nameFlag = ASSTValidator.isValidAndRoundBrackets(scriptName);
				if( nameFlag == true)
					errors.add("specialCharacterNameScriptNameError", new ActionMessage("error.string.invalidCharacter"));
			}			
		}	
		
		if((stockType!=null && !stockType.equals("")) && stockType.equals("003")){
			if(stockName == null && stockName.equals("")){
				errors.add("stockExchangeNameError", new ActionMessage("error.string.mandatory"));
			}else{
				if (!(errorCode = Validator.checkString(stockName, true, 1, 30)).equals(Validator.ERROR_NONE)) {
					errors.add("stockExchangeNameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
							new Integer(30)));
				}
			}
		}	

		if(scriptCode == null || scriptCode.equals("")){
			errors.add("scriptCodeError", new ActionMessage("error.string.mandatory"));
		}else{
			
			boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(scriptCode);
			if( nameFlag == true){
				errors.add("specialCharacterNameScriptCodeError", new ActionMessage("error.string.invalidCharacter"));
			}
			else if (!(errorCode = Validator.checkString(scriptCode, true, 1, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("scriptCodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						new Integer(1), new Integer(30)));
			}
			else{
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(scriptCode);
				if( codeFlag == true)
					errors.add("spaceError", new ActionMessage("error.string.noSpace","Script Code"));
			}
		}	
		
		if((scriptValue !=null && !scriptValue.trim().equals(""))){
			if (!(errorCode = Validator.checkNumber(scriptValue, true, 0, 9999999.99,3,locale)).equals(Validator.ERROR_NONE)) {
				errors.add("scriptValueError" , new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,errorCode), "0", "9999999.99"));
				DefaultLogger.debug(LOGOBJ, "scriptValueError= "+ scriptValue);
			}
			else if (!"".equals(scriptValue) && checkString(scriptValue)
					&& !Validator.checkPattern(scriptValue, FeedConstants.FOREX_PRICE_FORMAT)) {
				errors.add("scriptValueError", new ActionMessage(FeedConstants.ERROR_INVALID));
				DefaultLogger.debug(LOGOBJ, " scriptValueError= "+ scriptValue);
			}
		}
		
		if((facevalue !=null && !facevalue.trim().equals(""))){
			if (!(errorCode = Validator.checkNumber(facevalue, true, 0, 99999.99,3,locale)).equals(Validator.ERROR_NONE)) {
				errors.add("faceValueError" , new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,errorCode), "0", "99999.99"));
				DefaultLogger.debug(LOGOBJ, "faceValueError= "+ facevalue);
			}
			else if (!"".equals(facevalue) && checkString(facevalue)
					&& !Validator.checkPattern(facevalue, FeedConstants.FOREX_PRICE_FORMAT)) {
				errors.add("faceValueError", new ActionMessage(FeedConstants.ERROR_INVALID));
				DefaultLogger.debug(LOGOBJ, " faceValueError= "+ facevalue);
			}
		}
		
		DefaultLogger.debug(LOGOBJ, "errors" + errors.size());
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
