package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import java.util.Locale;

/**
 * Describe this class.
 * Purpose: Validation for security envelope
 * Description: Validate the value that user key in
 *
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 *        Tag: $Name$
 */

public class SecEnvelopeFormValidator {

    public static ActionErrors validateSecEnvelopeForm(ActionForm aForm, Locale locale) {

        String errorCode = null;
        ActionErrors errors = new ActionErrors();

        try {
            SecEnvelopeForm envelopeForm = (SecEnvelopeForm) aForm;

            String event = envelopeForm.getEvent();

            if (event.equals("checker_confirm_approve_create") || event.equals("checker_confirm_reject_create")
                    || event.equals("checker_confirm_approve_edit") || event.equals("checker_confirm_reject_edit")
                    || event.equals("maker_confirm_resubmit_edit")) {

                if (!(errorCode = RemarksValidatorUtil.checkRemarks(envelopeForm.getRemarks(), false)).equals(Validator.ERROR_NONE)) {
                    errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));

                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return errors;
    }

    public static ActionErrors validateSecEnvelopeItemForm(ActionForm aForm, Locale locale) {
        String errorCode = null;
        ActionErrors errors = new ActionErrors();
        DefaultLogger.debug(SecEnvelopeFormValidator.class, "locale from common action >>>>>>>>>>" + locale);
        try {
            SecEnvelopeItemForm itemForm = (SecEnvelopeItemForm) aForm;

            if (AbstractCommonMapper.isEmptyOrNull(itemForm.getSecEnvelopeItemAddr())) {
                errors.add("addressError", new ActionMessage("error.string.mandatory"));
            }

            if (AbstractCommonMapper.isEmptyOrNull(itemForm.getSecEnvelopeItemBarcode())) {
                errors.add("barCodeError", new ActionMessage("error.string.mandatory"));
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return errors;
    }
}
