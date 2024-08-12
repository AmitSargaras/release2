/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.list;

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
public class CustRelationshipListValidator implements Serializable {

	private static final long serialVersionUID = 1L;

    private static String LOGOBJ = CustRelationshipListValidator.class.getName();

    public static ActionErrors validateInput(CustRelationshipListForm form, Locale locale) {

        String event = form.getEvent();
        ActionErrors errors = new ActionErrors();

        String errorCode = "";

        if (CustRelationshipListAction.EVENT_REMOVE.equals(event)) {
            
            if (form.getCheckSelects() == null || form.getCheckSelects().length == 0) {
                errors.add("chkDeletes", new ActionMessage(CustRelConstants.ERROR_CHKBOX_MANDATORY));
                DefaultLogger.debug(LOGOBJ, "Check that there is at least one selected checkbox.");
            }
        }
        
        if (CustRelationshipListAction.EVENT_SUBMIT.equals(event)) {
        	
        	if (form.getEntityRels() != null) {
        		for (int i = 0; i < form.getEntityRels().length; i++) {
        			if (form.getEntityRels()[i] == null || form.getEntityRels()[i].length() == 0) {
    	               errors.add("entityRels." + i, new ActionMessage(CustRelConstants.ERROR_ENTITYREL_MANDATORY));
    	               DefaultLogger.debug(LOGOBJ, "Check that entity relationship is selected.");
        			}
        		}
        	} 
        	
        	/**
        	if (form.getEntityRels() == null || form.getEntityRels().length == 0) {
        		errors.add("custRelError", new ActionMessage("error.no.entries"));
        		DefaultLogger.debug(LOGOBJ, "Check that at least one entry entered to submit.");       		
        	}
        	**/
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
