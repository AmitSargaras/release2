package com.integrosys.cms.ui.feed.mutualfunds.item;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.feed.FeedConstants;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 * This class implements validation
 */
public class MutualFundsItemFormValidator implements java.io.Serializable {
	
	private static String LOGOBJ = MutualFundsItemFormValidator.class.getName();
	
	public static ActionErrors validateInput(MutualFundsItemForm form, Locale locale) {

		// Only for save event.

		ActionErrors errors = new ActionErrors();

		String schemName = form.getSchemeName();
		String schemCode = form.getSchemeCode();
		String schemeType = form.getSchemeType();
		String currentNAV = form.getCurrentNAV();
		
		//Phase 3 CR:comma separated
		currentNAV=UIUtil.removeComma(currentNAV);
		
		String expiryDate = form.getExpiryDate();
		String startDate = form.getStartDate();
		String errorCode = "";

		if (schemName ==null || schemName.equals("")){
			errors.add("schemeNameError", new ActionMessage("error.string.mandatory"));
		} else{
			if(!(errorCode = Validator.checkString(schemName, true, 1, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("schemeNameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
					new Integer(30)));
			}
			else{
				boolean nameFlag = ASSTValidator.isValidMutualFundName(schemName);
				if( nameFlag == true)
					errors.add("schemeNameError", new ActionMessage("error.string.invalidCharacter"));
			}
		}

		if (schemCode ==null || schemCode.equals("")){
			errors.add("schemeCodeError", new ActionMessage("error.string.mandatory"));
		}else { 
			if(!(errorCode = Validator.checkString(schemCode, true, 1, 30)).equals(Validator.ERROR_NONE)){
					errors.add("schemeCodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(
					1), new Integer(30)));
			}
			else{
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(schemCode);
				if( codeFlag == true)
					errors.add("schemeCodeError", new ActionMessage("error.string.invalidCharacter"));
			}
		}

		if (schemeType ==null || schemeType.equals("")){
			errors.add("schemeTypeError", new ActionMessage("error.string.mandatory"));
		}else { 
			if (!(errorCode = Validator.checkString(schemeType, false, 1, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("schemeTypeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					new Integer(1), new Integer(20)));
			}
		}
		
		if(currentNAV == null || currentNAV.equals("")){
			errors.add("currentNAVError", new ActionMessage("error.string.mandatory"));
		}else{
			if (!(errorCode = Validator.checkNumber(currentNAV, true, 0, 99999.99,3,locale)).equals(Validator.ERROR_NONE)) {
				errors.add("currentNAVError" , new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,errorCode), "0", "99999.99"));
				DefaultLogger.debug(LOGOBJ, "currentNAVError= "+ currentNAV);
			}
			else if (!"".equals(currentNAV) && checkString(currentNAV)
					&& !Validator.checkPattern(currentNAV, FeedConstants.FOREX_PRICE_FORMAT)) {
				errors.add("currentNAVError", new ActionMessage(FeedConstants.ERROR_INVALID));
				DefaultLogger.debug(LOGOBJ, " currentNAVError= "
						+ currentNAV);
			}
		}
		if((schemeType != null && !schemeType.equals("")) && schemeType.equalsIgnoreCase("CLOSE")){
			if (expiryDate ==null || expiryDate.equals("")){
				errors.add("expiryDateError", new ActionMessage("error.string.mandatory"));
			}else { 
				if (!(errorCode = Validator.checkDate(expiryDate, false, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("expiryDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
				}else if (DateUtil.convertDate(locale, expiryDate).before(new Date())) {
					errors.add("expiryDateError", new ActionMessage(FeedConstants.ERROR_PAST_DATE));
				}
			}

			if (startDate ==null || startDate.equals("")){
				errors.add("startDateError", new ActionMessage("error.string.mandatory"));
			}else { 
				if (!(errorCode = Validator.checkDate(startDate, false, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("startDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
				}else if (DateUtil.convertDate(locale, startDate).after(new Date())) {
					errors.add("startDateError", new ActionMessage(FeedConstants.ERROR_FUTURE_DATE));
				}	
			}
		}else{
			if(expiryDate != null && !expiryDate.equals("")){
				if (!(errorCode = Validator.checkDate(expiryDate, false, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("expiryDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
				}else if (DateUtil.convertDate(locale, expiryDate).before(new Date())) {
					errors.add("expiryDateError", new ActionMessage(FeedConstants.ERROR_PAST_DATE));
				}
			}	
			if(startDate != null && !startDate.equals("")){
				if (!(errorCode = Validator.checkDate(startDate, false, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("startDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
				}else if (DateUtil.convertDate(locale, startDate).after(new Date())) {
					errors.add("startDateError", new ActionMessage(FeedConstants.ERROR_FUTURE_DATE));
				}
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
