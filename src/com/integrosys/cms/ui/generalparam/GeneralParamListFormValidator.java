package com.integrosys.cms.ui.generalparam;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.feed.FeedConstants;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 * This class implements validation
 */
public class GeneralParamListFormValidator implements java.io.Serializable {

	private static String LOGOBJ = GeneralParamListFormValidator.class.getName();

	public static ActionErrors validateInput(GeneralParamListForm form, Locale locale) {

		String event = form.getEvent();
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		if (GeneralParamListAction.EVENT_SAVE.equals(event) || GeneralParamListAction.EVENT_SUBMIT.equals(event)
				|| GeneralParamListAction.EVENT_PAGINATE.equals(event) || GeneralParamListAction.EVENT_ADD.equals(event)
				|| GeneralParamListAction.EVENT_REMOVE.equals(event)) {
			// Check that the updated unit prices fall in range.
			String[] updatedUnitPricesArr = form.getUpdatedParamValue();
			validateParamValueArr(form, errors,locale);

		}

		// Check that there is at least one selected checkbox.
		if (GeneralParamListAction.EVENT_REMOVE.equals(event)) {
			if ((form.getChkDeletes() == null) || (form.getChkDeletes().length == 0)) {
				errors.add("chkDeletes", new ActionMessage(FeedConstants.ERROR_CHKBOX_MANDATORY));
			}
		}

		if (GeneralParamListAction.EVENT_APPROVE.equals(event) || GeneralParamListAction.EVENT_REJECT.equals(event)
				|| GeneralParamListAction.EVENT_CLOSE.equals(event)) {
			errorCode = RemarksValidatorUtil.checkRemarks(form.getRemarks(), false);
			if (!Validator.ERROR_NONE.equals(errorCode)) {
				errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
			}

		}
		DefaultLogger.debug(LOGOBJ, "number of errors = " + errors.size());
		return errors;
	}
	
	public static ActionErrors validateParamValueArr(GeneralParamListForm form, ActionErrors errors,Locale locale) {
		String[] paramNameArr=form.getParamNames();
		String[] updatedParamValueArr=form.getUpdatedParamValue();
		String errorCode = "";
		if (paramNameArr != null) {
			String paramName="";
			for (int i = 0; i < paramNameArr.length; i++) {
				paramName=paramNameArr[i];
				if("LSS Unit Head Email ID".equals(paramName)){
					String emailIds=updatedParamValueArr[i];
					if(emailIds!=null && !"".equals(emailIds)){
						String[] emailIdArray = emailIds.split(",");
						for (int j = 0; j < emailIdArray.length; j++) {
							String emailId = emailIdArray[j];
							if(emailId!=null && !"".equals(emailId)){
								if (ASSTValidator.isValidEmail(emailId)) {
									//errors.add("lssUnitHeadError", new ActionMessage("error.email.format.string"));
									errors.add("paramError"+i, new ActionMessage("error.email.format.string"));
									break;
								}
							}
						}
					}
				}else if("Max Inactive Interval (In Minutes)".equals(paramName)
						||"Daily Basel Retention Period (Days)".equals(paramName)
						||"Lad Generate Indicator In Months".equals(paramName)
						||"Max Login Attempts.".equals(paramName)
						){
					String paramValue=updatedParamValueArr[i];
					if(!Validator.validateInteger(paramValue, true, 1, 999999999)) {
						//errors.add("maxInactiveIntervalError", new ActionMessage("error.number.format"));
						errors.add("paramError"+i, new ActionMessage("error.number.format"));
					}
				}else if("Base Interest Rate".equals(paramName)){
					boolean base=false;
					String paramValue=updatedParamValueArr[i];
					try{
					if(Double.parseDouble(updatedParamValueArr[i])>=100||Double.parseDouble(updatedParamValueArr[i])<1)
						errors.add("paramError"+i, new ActionMessage("error.number.greaterthan", "0",
						"99.99"));
				if(Validator.checkNumber(updatedParamValueArr[i],true, 0.0, 99.99, 3, locale).equalsIgnoreCase("decimalexceeded"))
					errors.add("paramError"+i, new ActionMessage("error.number.decimalexceeded"));
					}catch(Exception e){
						errors.add("paramError"+i, new ActionMessage("error.number.format"));
						base=true;
					}
					if((updatedParamValueArr[i].charAt(updatedParamValueArr[i].length()-1)=='d'||updatedParamValueArr[i].charAt(updatedParamValueArr[i].length()-1)=='D'||updatedParamValueArr[i].charAt(updatedParamValueArr[i].length()-1)=='f'||updatedParamValueArr[i].charAt(updatedParamValueArr[i].length()-1)=='F')&&updatedParamValueArr[i].length()-1>0&&!base)
						errors.add("paramError"+i, new ActionMessage("error.number.format"));
				}else if("Application Date DD-MMMM-YYYY".equals(paramName)){
					String paramValue=updatedParamValueArr[i];
//					if(!Validator.validateInteger(paramValue, true, 1, 999999999)) {
//						//errors.add("maxInactiveIntervalError", new ActionMessage("error.number.format"));
//						errors.add("paramError"+i, new ActionMessage("error.number.format"));
//					}
					//Need to add date validation
				}else if("BPLR Interest Rate".equals(paramName)){
					boolean bplr=false;
					String paramValue=updatedParamValueArr[i];
					try{
						if(Double.parseDouble(updatedParamValueArr[i])>=100||Double.parseDouble(updatedParamValueArr[i])<1)
							errors.add("paramError"+i, new ActionMessage("error.number.greaterthan", "0",
							"99.99"));
					if(Validator.checkNumber(updatedParamValueArr[i],true, 0.0, 99.99, 3, locale).equalsIgnoreCase("decimalexceeded"))
						errors.add("paramError"+i, new ActionMessage("error.number.decimalexceeded"));
				}catch(Exception e){
					errors.add("paramError"+i, new ActionMessage("error.number.format"));
					bplr=true;
				}
				if((updatedParamValueArr[i].charAt(updatedParamValueArr[i].length()-1)=='d'||updatedParamValueArr[i].charAt(updatedParamValueArr[i].length()-1)=='D'||updatedParamValueArr[i].charAt(updatedParamValueArr[i].length()-1)=='f'||updatedParamValueArr[i].charAt(updatedParamValueArr[i].length()-1)=='F')&&updatedParamValueArr[i].length()-1>0&&!bplr)
					errors.add("paramError"+i, new ActionMessage("error.number.format"));
				}else if("LEI Date Validation Period".equals(paramName)) {
					String paramValue=updatedParamValueArr[i];
					if(!Validator.validateInteger(paramValue, true, 1, 999999999)) {
						errors.add("paramError"+i, new ActionMessage("error.number.must.morethan"));
					}
				}else{
					boolean nameFlag = ASSTValidator.isValidDash(updatedParamValueArr[i]);
					if( nameFlag == true)
						//errors.add("otherBankNameError", new ActionMessage("error.string.invalidCharacter"));
						errors.add("paramError"+i, new ActionMessage("error.string.invalidCharacter"));
				}
		
			}
		}
		
		
		/*if (updatedParamValueArr != null) {
			for (int i = 0; i < updatedParamValueArr.length; i++) {
				boolean nameFlag = ASSTValidator.isValidDash(updatedParamValueArr[i]);
				if( nameFlag == true)
					errors.add("otherBankNameError", new ActionMessage("error.string.invalidCharacter"));
				
				if (!(errorCode = Validator
						.checkNumber(updatedParamValueArr[i], true, 0, FeedConstants.FOREX_PRICE_MAX))
						.equals(Validator.ERROR_NONE)) {
					errors.add("paramValueError" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
							errorCode), "0", FeedConstants.FOREX_EXCHANGE_MAX_STR));
					DefaultLogger.debug(LOGOBJ, "checkNumber updatedParamValueArr[" + i + "]= "
							+ updatedParamValueArr[i]);
				}
				else if (!"".equals(updatedParamValueArr[i]) && checkString(updatedParamValueArr[i])
						&& !Validator.checkPattern(updatedParamValueArr[i], FeedConstants.FOREX_PRICE_FORMAT)) {
					errors.add("paramValueError" + i, new ActionMessage(FeedConstants.ERROR_INVALID));
					DefaultLogger.debug(LOGOBJ, " checkPattern updatedParamValueArr[" + i + "]= "
							+ updatedParamValueArr[i]);
				}
			}
		}*/
		DefaultLogger.debug(LOGOBJ, " validateParamValueArr No of Errors = " + errors.size());
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
