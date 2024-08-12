package com.integrosys.cms.ui.creditriskparam.productprogramlimit;

import java.util.Locale;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;


/**
 * Author: Priya
 * Date: Oct 9, 2009
 */
public class ProductProgramLimitValidator {

    public static ActionErrors validateInput(ProductProgramLimitForm form, Locale locale) {
        String event = form.getEvent();
        ActionErrors errors = new ActionErrors();

        String errorCode = null;
 
        if (ProductProgramLimitAction.EVENT_ADD_ITEM.equals(event) ||
        		ProductProgramLimitAction.EVENT_EDIT_ITEM.equals(event) ||
        		ProductProgramLimitAction.EVENT_DELETE_ITEM.equals(event) ||
        		ProductProgramLimitAction.EVENT_SUBMIT.equals(event)) {
        	
        	if(form.getProdProgramDesc() == null || form.getProdProgramDesc().equals("")){
            	 errors.add("prodProgDesc", new ActionMessage("error.string.mandatory"));
            }
            
            if(form.getProdProgramRefCode() == null || form.getProdProgramRefCode().equals("")){
            	 errors.add("prodProgRefCode", new ActionMessage("error.string.mandatory"));
            }
            
            if (form.getLimitAmt() != null && !form.getLimitAmt().equals("")) {
   			 if (!(errorCode = Validator.checkAmount(form.getLimitAmt(), true, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2, IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE))
                {
                    errors.add("prodProgLimitAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
                }
                else {
               	 String amount = form.getLimitAmt();
               	 if(!(Validator.checkPattern(amount,"[-]*[0-9]+(\\.[0-9]*)?$"))) 
               		 errors.add("prodProgLimitAmt", new ActionMessage("error.amount.format"));
               	 }
            }
      
        }
        
        if (ProductProgramLimitAction.EVENT_DELETE_ITEM.equals(event)) {       
            if (form.getDeleteItems() == null || form.getDeleteItems().length == 0) {
                errors.add("chkDeletes", new ActionMessage("error.chk.del.records"));
            }
        }

        if (ProductProgramLimitAction.EVENT_APPROVE.equals(event) ||
        		ProductProgramLimitAction.EVENT_REJECT.equals(event) ||
        		ProductProgramLimitAction.EVENT_CLOSE.equals(event) ||
        		ProductProgramLimitAction.EVENT_SUBMIT.equals(event) ||
        		ProductProgramLimitAction.EVENT_DELETE.equals(event)) {
            errorCode = RemarksValidatorUtil.checkRemarks(form.getRemarks(), false);
            if (!Validator.ERROR_NONE.equals(errorCode)) {
                errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
            }
        }
        
        return errors;
    }
}
