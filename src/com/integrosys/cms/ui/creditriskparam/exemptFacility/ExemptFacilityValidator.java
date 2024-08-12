/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.exemptFacility;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

import java.util.Locale;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $
 *        Tag: $Name:  $
 */
public class ExemptFacilityValidator {

    public static ActionErrors validateInput(ExemptFacilityForm form, Locale locale) {
        ActionErrors errors = new ActionErrors();
        String errorCode = null;
        String event = form.getEvent();

        if (ExemptFacilityAction.EVENT_CHECKER_APPROVE.equals(event) ||
            ExemptFacilityAction.EVENT_SUBMIT.equals(event) ||
            ExemptFacilityAction.EVENT_CHECKER_REJECT.equals(event) ||
            ExemptFacilityAction.EVENT_MAKER_CLOSE_CONFIRM.equals(event)) {
                errorCode = RemarksValidatorUtil.checkRemarks(form.getRemarks(), false);
                if (!Validator.ERROR_NONE.equals(errorCode)) {
                    errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
                }
      }else if("remove".equals(event)){
//	        System.out.println("form.getCheckSelects(): "+form.getCheckSelects());
		          if(form.getCheckSelects() == null || form.getCheckSelects().length == 0)
		          {
			                errors.add("deleteItems", new ActionMessage("error.chk.del.records"));
		          }
           }
        else
        {
            if (!(errorCode = Validator.checkString(form.getFacilityStatusExempted(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
                errors.add("facilityStatusExemptedError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "40"));
            }
            if (form.getFacilityStatusExempted() != null){
                if (ICMSConstant.EXEMPT_FACILITY_STATUS_CONDITIONAL.equals(form.getFacilityStatusExempted())){
                    if (!(errorCode = Validator.checkNumber(form.getFacilityStatusConditionalPerc(), true, 1, 100, 3, locale)).equals(Validator.ERROR_NONE)) {
	                  if( errorCode.equals("greaterthan") || errorCode.equals("lessthan") ) {
							errors.add("facilityStatusConditionalPercError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, "heightlessthan"), "0", "100"));

	                  } else if ( errorCode.equals("decimalexceeded") ) {
	                    errors.add("facilityStatusConditionalPercError", new ActionMessage("error.number.moredecimalexceeded","","","2"));

	                  }else if ( !errorCode.equals("mandatory") ){
	                    errors.add("facilityStatusConditionalPercError", new ActionMessage("error.number." + errorCode));
	                  }
                    }
                }
                else
                {
                    if (form.getFacilityStatusConditionalPerc() != null && !form.getFacilityStatusConditionalPerc().trim().equals(""))
                        errors.add("facilityStatusConditionalPercError", new ActionMessage("label.crp.exemptfac.conditional.perc.not.required"));
                }
            }
            if (!(errorCode = Validator.checkString(form.getFacilityCode(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
                errors.add("facilityCodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "40"));
            }
        }
        

        DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
//        System.out.println(" Total Errors--------->" + errors);

        return errors;
    }
}