/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.bankentitybranch;

import java.util.Date;
import java.util.Locale;
import java.math.BigDecimal;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.common.UIValidator;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.creditriskparam.countrylimit.CountryLimitForm;
import com.integrosys.cms.ui.creditriskparam.countrylimit.CountryLimitItemForm;
import com.integrosys.cms.ui.creditriskparam.countrylimit.CountryRatingForm;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 6, 2008
 * Time: 4:53:34 PM
 * Desc: Bank entity branch param item validator class
 */
public class BankEntityBranchParamValidator {

    public static ActionErrors validateItemInput(ActionForm aForm, Locale locale) {
	      
        BankEntityBranchParamItemForm itemForm = (BankEntityBranchParamItemForm) aForm;
        ActionErrors errors = new ActionErrors();

        if(itemForm.getEntityTypeCode().equals("0"))
            errors.add("entityTypeCode", new ActionMessage("error.string.mandatory"));

        if(itemForm.getBranchCode().equals("0"))
            errors.add("branchCode", new ActionMessage("error.string.mandatory"));

        return errors;
    }

    public static ActionErrors validateRemarks(ActionForm aForm, Locale locale) {
	    
	      
        BankEntityBranchParamForm form = (BankEntityBranchParamForm) aForm;
        ActionErrors errors = new ActionErrors();
        String event = form.getEvent();
        if(StringUtils.isNotEmpty(form.getRemarks()))
        {
            String errorCode = RemarksValidatorUtil.checkRemarks(form.getRemarks(), false);
            if (!errorCode.equals(Validator.ERROR_NONE))
                errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
        }
        
        if("maker_delete_item".equals(event)){
		          if(form.getDeleteItems() == null || form.getDeleteItems().length == 0)
		          {
			                errors.add("delItem", new ActionMessage("error.chk.del.records"));
		          }
        }
        return errors;

    }
}