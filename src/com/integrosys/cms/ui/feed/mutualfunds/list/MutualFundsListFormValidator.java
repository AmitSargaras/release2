package com.integrosys.cms.ui.feed.mutualfunds.list;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.feed.FeedConstants;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 * This class implements validation
 */
public class MutualFundsListFormValidator implements java.io.Serializable {

	private static String LOGOBJ = MutualFundsListFormValidator.class.getName();

	public static ActionErrors validateInput(MutualFundsListForm form, Locale locale) {

		String event = form.getEvent();
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		if (MutualFundsListAction.EVENT_SAVE.equals(event) || MutualFundsListAction.EVENT_SUBMIT.equals(event)
				|| MutualFundsListAction.EVENT_PAGINATE.equals(event)
				|| MutualFundsListAction.EVENT_REMOVE.equals(event)
				/** The Following event is commented by 
				 *  @author sandiip.shinde as it is not required  
				 *  to validate before adding a new record.
				 * 
				 * */
				/*|| MutualFundsListAction.EVENT_ADD.equals(event)*/
				) {
			// Check that the updated unit prices fall in range.
			String[] updatedUnitPricesArr = form.getUpdatedCurrentNAV();
			String[] schemeTypes = form.getSchemeTypeArr();
			String[] startDateArr = form.getStartDateArr();
			String[] expiryDateArr = form.getExpiryDateArr();
			String[] schemeNames = form.getSchemeNames();
			String[] schemeStatus = form.getSchemeTypeList();
			validateCurrentNAVArr(updatedUnitPricesArr, errors);
			
			
			if(schemeNames != null && !schemeNames.equals("")){
				for(int i = 0; i < schemeNames.length; i++){
					if (schemeNames[i] ==null || schemeNames[i].equals("")){
						errors.add("schemeNamesError"+i, new ActionMessage("error.string.mandatory"));
					} else{
						if(!(errorCode = Validator.checkString(schemeNames[i], true, 1, 30)).equals(Validator.ERROR_NONE)) {
							errors.add("schemeNamesError"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
								new Integer(30)));
						}
						else{
							boolean nameFlag = ASSTValidator.isValidMutualFundName(schemeNames[i]);
							if( nameFlag == true)
								errors.add("schemeNamesError"+i, new ActionMessage("error.string.invalidCharacter"));
						}
					}
				}
			}
			
			if(schemeTypes!=null && !schemeTypes.equals("")){
				for(int i = 0; i < schemeTypes.length; i++){
					
					if(schemeTypes[i] == null && schemeTypes[i].equals("")){
						errors.add("schemeTypeError"+i, new ActionMessage("error.string.mandatory"));
					}
						/* Original Condition*/
//					else if((schemeTypes[i] != null && !schemeTypes[i].equals("")) && schemeTypes[i].equalsIgnoreCase("CLOSE")){

					/* Commented By Sandeep Shinde*/
//					else if((schemeStatus[i] != null && !schemeStatus[i].equals("")) && schemeStatus[i].equalsIgnoreCase("CLOSE")){

//					boolean validate = false;
					if( schemeStatus[i].equalsIgnoreCase("OPEN") ){ 
						if( schemeTypes[i] != null && schemeTypes[i].equalsIgnoreCase("CLOSE") ){											
							if (expiryDateArr[i] ==null || expiryDateArr[i].equals("")){
								errors.add("expiryDateError"+i, new ActionMessage("error.string.mandatory"));
							}else { 
								if (!(errorCode = Validator.checkDate(expiryDateArr[i], false, locale)).equals(Validator.ERROR_NONE)) {
									errors.add("expiryDateError"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
								}else if (DateUtil.convertDate(locale, expiryDateArr[i]).before(new Date())) {
									errors.add("expiryDateError"+i, new ActionMessage(FeedConstants.ERROR_PAST_DATE));
								}
							}
			
							if (startDateArr[i] ==null || startDateArr[i].equals("")){
								errors.add("startDateError"+i, new ActionMessage("error.string.mandatory"));
							}else { 
								if (!(errorCode = Validator.checkDate(startDateArr[i], false, locale)).equals(Validator.ERROR_NONE)) {
									errors.add("startDateError"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
								}else if (DateUtil.convertDate(locale, startDateArr[i]).after(new Date())) {
									errors.add("startDateError"+i, new ActionMessage(FeedConstants.ERROR_FUTURE_DATE));
								}	
							}
						}
					}
					
					else if( schemeStatus[i].equalsIgnoreCase("CLOSE") ){						
						if( schemeTypes[i].equalsIgnoreCase("") ){
							/*validate = true;
						else if( schemeTypes[i].equalsIgnoreCase("CLOSE") )
							validate = true;
						
						if( validate ){*/							
							if (expiryDateArr[i] == null || expiryDateArr[i].equals("")){
								errors.add("expiryDateError"+i, new ActionMessage("error.string.mandatory"));
							}else { 
								if (!(errorCode = Validator.checkDate(expiryDateArr[i], false, locale)).equals(Validator.ERROR_NONE)) {
									errors.add("expiryDateError"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
								}else if (DateUtil.convertDate(locale, expiryDateArr[i]).before(new Date())) {
									errors.add("expiryDateError"+i, new ActionMessage(FeedConstants.ERROR_PAST_DATE));
								}
							}
			
							if (startDateArr[i] == null || startDateArr[i].equals("")){
								errors.add("startDateError"+i, new ActionMessage("error.string.mandatory"));
							}else { 
								if (!(errorCode = Validator.checkDate(startDateArr[i], false, locale)).equals(Validator.ERROR_NONE)) {
									errors.add("startDateError"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
								}else if (DateUtil.convertDate(locale, startDateArr[i]).after(new Date())) {
									errors.add("startDateError"+i, new ActionMessage(FeedConstants.ERROR_FUTURE_DATE));
								}	
							}				
						}
					/*	if(expiryDateArr[i] != null && (expiryDateArr[i].trim()).length()>0){
							if (!(errorCode = Validator.checkDate(expiryDateArr[i], false, locale)).equals(Validator.ERROR_NONE)) {
								errors.add("expiryDateError"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
							}else if (DateUtil.convertDate(locale, expiryDateArr[i]).before(new Date())) {
								errors.add("expiryDateError"+i, new ActionMessage(FeedConstants.ERROR_PAST_DATE));
							}
						}	
						if(startDateArr[i] != null && startDateArr[i].trim().length()>0){
							if (!(errorCode = Validator.checkDate(startDateArr[i], false, locale)).equals(Validator.ERROR_NONE)) {
								errors.add("startDateError"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
							}else if (DateUtil.convertDate(locale, startDateArr[i]).after(new Date())) {
								errors.add("startDateError"+i, new ActionMessage(FeedConstants.ERROR_FUTURE_DATE));	
							}
						}*/	
					}
				}	
			}	
		}

		// Check that there is at least one selected checkbox.
		if (MutualFundsListAction.EVENT_REMOVE.equals(event)) {
			if ((form.getChkDeletes() == null) || (form.getChkDeletes().length == 0)) {
				errors.add("chkDeletes", new ActionMessage(FeedConstants.ERROR_CHKBOX_MANDATORY));
			}
		}

		if (MutualFundsListAction.EVENT_APPROVE.equals(event) || MutualFundsListAction.EVENT_REJECT.equals(event)
				|| MutualFundsListAction.EVENT_CLOSE.equals(event)) {
			errorCode = RemarksValidatorUtil.checkRemarks(form.getRemarks(), false);
			if (!Validator.ERROR_NONE.equals(errorCode)) {
				errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
			}

		}
		DefaultLogger.debug(LOGOBJ, "number of errors = " + errors.size());
		return errors;
	}
	
	public static ActionErrors validateCurrentNAVArr(String[] updatedCurrentNAVArr, ActionErrors errors) {
		String errorCode = "";
		Locale locale = Locale.getDefault();		
		if (updatedCurrentNAVArr != null) {
			for (int i = 0; i < updatedCurrentNAVArr.length; i++) {
				
				updatedCurrentNAVArr[i]=UIUtil.removeComma(updatedCurrentNAVArr[i]);
				
				if (!(errorCode = Validator.checkNumber(updatedCurrentNAVArr[i], true, 0, 99999.99,3,locale)).equals(Validator.ERROR_NONE)) {
					errors.add("currentNAVError"+i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,errorCode), "0", "99999.99"));
					DefaultLogger.debug(LOGOBJ, "currentNAVError= "+ updatedCurrentNAVArr[i]);
				}
				else if (!"".equals(updatedCurrentNAVArr[i]) && checkString(updatedCurrentNAVArr[i])
						&& !Validator.checkPattern(updatedCurrentNAVArr[i], FeedConstants.FOREX_PRICE_FORMAT)) {
					errors.add("currentNAVError"+i, new ActionMessage(FeedConstants.ERROR_INVALID));
					DefaultLogger.debug(LOGOBJ, " currentNAVError= "+ updatedCurrentNAVArr[i]);
				}
			}
		}
		DefaultLogger.debug(LOGOBJ, " validateCurrentNAVArr No of Errors = " + errors.size());
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
