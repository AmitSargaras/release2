/**
 * 
 */
package com.integrosys.cms.ui.creditriskparam.exemptedinst.list;

import java.io.Serializable;
import java.util.Locale;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.custrelationship.CustRelConstants;

/**
 * @author user
 *
 */
public class ExemptedInstListValidator implements Serializable {

	private static final long serialVersionUID = 1L;

    private static String LOGOBJ = ExemptedInstListValidator.class.getName();

    public static ActionErrors validateInput(ExemptedInstListForm form, Locale locale) {

        String event = form.getEvent();
        ActionErrors errors = new ActionErrors();

        String errorCode = "";

        if (ExemptedInstListAction.EVENT_REMOVE.equals(event)) {
            
            if (form.getCheckSelects() == null || form.getCheckSelects().length == 0) {
                errors.add("chkDeletes", new ActionMessage("error.chk.del.records"));
                DefaultLogger.debug(LOGOBJ, "Check that there is at least one selected checkbox.");
            }
        }
        		
        if (ExemptedInstListAction.EVENT_APPROVE.equals(event) ||
        		ExemptedInstListAction.EVENT_REJECT.equals(event) ||
        		ExemptedInstListAction.EVENT_CLOSE.equals(event)||
        		ExemptedInstListAction.EVENT_SUBMIT.equals(event)) {
            errorCode = RemarksValidatorUtil.checkRemarks(form.getRemarks(), false);
            if (!Validator.ERROR_NONE.equals(errorCode)) {
                errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
            }
        }

        DefaultLogger.debug(LOGOBJ, "errors " + errors.size());
        return errors;
    }
}
