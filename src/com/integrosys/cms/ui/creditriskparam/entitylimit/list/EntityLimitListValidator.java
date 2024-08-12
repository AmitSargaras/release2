/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.entitylimit.list;

import java.io.Serializable;
import java.util.Locale;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.custrelationship.CustRelConstants;


/**
 * Entity Limit Validator
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class EntityLimitListValidator implements Serializable {

	private static final long serialVersionUID = 1L;

    private static String LOGOBJ = EntityLimitListValidator.class.getName();

    public static ActionErrors validateInput(EntityLimitListForm form, Locale locale) {

        String event = form.getEvent();
        ActionErrors errors = new ActionErrors();

        String errorCode = "";

        if (EntityLimitListAction.EVENT_REMOVE.equals(event)) {
            
            if (form.getCheckSelects() == null || form.getCheckSelects().length == 0) {
                errors.add("chkDeletes", new ActionMessage("error.chk.del.records"));
                DefaultLogger.debug(LOGOBJ, "Check that there is at least one selected checkbox.");
            }
        }
        
        if (EntityLimitListAction.EVENT_UPDATE.equals(event)) {
        	
        	DefaultLogger.debug(LOGOBJ, "entering EntityLimitListValidator.validateInput EVENT_UPDATE");
        	if (form.getLimitAmounts() != null || form.getLimitAmounts().length > 0) {
        		int len = form.getLimitAmounts().length;
        		
        		for (int i = 0; i < len; i++) {
        			String limitAmountName = "limitAmounts" + i;
        			String limitCurrencysName = "limitCurrencys" + i;
        			String limitLastReviewDatesName = "limitLastReviewDates" + i;
        			
	        		if (form.getLimitCurrencys()[i] != null) {
	        			if (!(errorCode = Validator.checkString(form.getLimitCurrencys()[i], true, 1, 3)).equals(Validator.ERROR_NONE)) {
	                        errors.add(limitCurrencysName, new ActionMessage("error.string.mandatory", "1", "3"));
	                        DefaultLogger.debug(LOGOBJ, " form.getLimitAmount()[" + i + "] = " + form.getLimitCurrencys()[i]);
	                    }
	        		}
        			
	        		if (form.getLimitAmounts()[i] != null) {
                        if (!(errorCode = Validator.checkAmount(form.getLimitAmounts()[i], true, 0.01, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_17, IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
                            errors.add(limitAmountName, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_17_STR));
                            DefaultLogger.debug(LOGOBJ, "aForm.getLimitAmount()[" + i + "] = " + form.getLimitAmounts()[i]);
                        }
                        //Andy Wong, 14 April 2010: revert jira ABCMS-701 validation that causing jira ABCMS-782, unnecessary checking that already handled in checkAmount method
//	                     else {
//	                    	 String amount = form.getLimitAmounts()[i];
//	                    	 if(!(Validator.checkPattern(amount,"[-]*[0-9]+(\\.[0-9]*)?$")))
//	                    		 errors.add(limitAmountName, new ActionMessage("error.amount.format"));
//	                    	 }
	        		}
	        		
	        		if (form.getLimitLastReviewDates()[i] != null) {
	        			if (!(errorCode = Validator.checkDate(form.getLimitLastReviewDates()[i], true, locale)).equals(Validator.ERROR_NONE)) {
	                        errors.add(limitLastReviewDatesName, new ActionMessage("error.date.mandatory"));
	                        DefaultLogger.debug(LOGOBJ, "aForm.getLimitLastReviewDates()[" + i + "] = " + form.getLimitLastReviewDates()[i]);
	                    } else {
	        			
		        			java.util.Date date = DateUtil.convertDate(locale, form.getLimitLastReviewDates()[i]);
		        			if (date.after(new java.util.Date())) {
		                        errors.add(limitLastReviewDatesName, new ActionMessage("error.crp.limit.last.review.date.before.today"));
		                        DefaultLogger.debug(LOGOBJ, "aForm.getLimitLastReviewDates()[" + i + "] = " + form.getLimitLastReviewDates()[i]);	        				
		        			}
	        			}
	        		}
        		}
        		
				        
				        	 
                   
        	}
        		
        }
        		
        if (EntityLimitListAction.EVENT_APPROVE.equals(event) ||
        		EntityLimitListAction.EVENT_REJECT.equals(event) ||
        		EntityLimitListAction.EVENT_CLOSE.equals(event)|| 
        		EntityLimitListAction.EVENT_SUBMIT.equals(event)) {
            errorCode = RemarksValidatorUtil.checkRemarks(form.getRemarks(), false);
            if (!Validator.ERROR_NONE.equals(errorCode)) {
                errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
            }
        }

        DefaultLogger.debug(LOGOBJ, "errors " + errors.size());
        return errors;
    }
}
