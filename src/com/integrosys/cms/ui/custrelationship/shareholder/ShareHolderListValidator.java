/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.shareholder;

import java.io.Serializable;
import java.util.Locale;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.custrelationship.CustRelConstants;
import com.integrosys.cms.ui.custrelationship.list.CustRelationshipListAction;

/**
 * @author user
 *
 */
public class ShareHolderListValidator implements Serializable {

	private static final long serialVersionUID = 1L;

    private static String LOGOBJ = ShareHolderListValidator.class.getName();

    public static ActionErrors validateInput(ShareHolderListForm form, Locale locale) {

        String event = form.getEvent();
        ActionErrors errors = new ActionErrors();

        String errorCode = "";

        if (ShareHolderListAction.EVENT_REMOVE.equals(event)) {
            
            if (form.getCheckSelects() == null || form.getCheckSelects().length == 0) {
                errors.add("chkDeletes", new ActionMessage(CustRelConstants.ERROR_CHKBOX_MANDATORY));
                DefaultLogger.debug(LOGOBJ, "Check that there is at least one selected checkbox.");
            }
        }
        	
        if (ShareHolderListAction.EVENT_SUBMIT.equals(event) || ShareHolderListAction.EVENT_PAGINATE.equals(event)) {
        	
        	String[] percentages = form.getPercentages();
        	
            if (form.getPercentages() != null) {
            	for (int i = 0; i < percentages.length; i++) {
            		if (percentages[i] == null || percentages[i].length() == 0) {
     	               errors.add("percentages." + i, new ActionMessage(CustRelConstants.ERROR_PERCENTAGE_MANDATORY));
    	               DefaultLogger.debug(LOGOBJ, "Check that percentages is inserted.");
            		} else if (!Validator.checkDoubleDigits(percentages[i], 10, 2, false)) {
      	               errors.add("percentages." + i, new ActionMessage(CustRelConstants.ERROR_PRECENTAGE_INVALID));
    	               DefaultLogger.debug(LOGOBJ, "Check that percentages is in valid format.");
            		} else if (Validator.ERROR_NONE != Validator.checkNumber(percentages[i], true, 0.005, 100.1)) {
       	               errors.add("percentages." + i, new ActionMessage(CustRelConstants.ERROR_PRECENTAGE_INVALID));
    	               DefaultLogger.debug(LOGOBJ, "Check that percentages is more than 0.005 and less than 100");            			
            		}
            	}
            }
            
            /*
        	if (form.getPercentages()  == null || form.getPercentages().length == 0) {
        		errors.add("custShareHolderError", new ActionMessage("error.no.entries"));
        		DefaultLogger.debug(LOGOBJ, "Check that at least one entry entered to submit.");       		
        	}
        	*/
        }
        
        if (CustRelationshipListAction.EVENT_APPROVE.equals(event) ||
        		CustRelationshipListAction.EVENT_REJECT.equals(event) ||
        		CustRelationshipListAction.EVENT_CLOSE.equals(event) ||
				CustRelationshipListAction.EVENT_SUBMIT.equals(event) ) {
            errorCode = RemarksValidatorUtil.checkRemarks(form.getRemarks(), false);
            if (!Validator.ERROR_NONE.equals(errorCode)) {
                errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
            }
        }
	
        DefaultLogger.debug(LOGOBJ, "errors " + errors.size());
        return errors;
    }
}
