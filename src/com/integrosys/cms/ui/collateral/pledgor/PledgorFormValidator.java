package com.integrosys.cms.ui.collateral.pledgor;

import com.integrosys.base.techinfra.validation.Validator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.commons.lang.StringUtils;

import java.util.Locale;

public class PledgorFormValidator {

    private final static String SEARCH_BY_CIF = "1";

    private final static String SEARCH_BY_LEGAL_ID = "2";

    private final static String SEARCH_BY_ID_NUMBER = "3";

    public static ActionErrors validateInput(PledgorForm form, Locale locale) {
        ActionErrors errors = new ActionErrors();

        // validation for search
        if (CashPledgorAction.EVENT_SEARCH_PLEDGOR.equals(form.getEvent())) {
            if (SEARCH_BY_CIF.equals(form.getSearchButton())) {
                if (!(Validator.checkString(form.getCustomerName(), true, 3, 40)).equals(Validator.ERROR_NONE)) {
                    errors.add("customerName", new ActionMessage("error.string.customername"));
                }
            } else if (SEARCH_BY_LEGAL_ID.equals(form.getSearchButton())) {
                //Andy Wong, 21 Nov 2008: validate CIF Id for numeric type
                //trim CIF field
                form.setLegalID(StringUtils.trim(form.getLegalID()));
                String errMsg = Validator.checkNumber(form.getLegalID(), true, 1, 20);
                if (!(errMsg.equals(Validator.ERROR_NONE)
                        || errMsg.equals(Validator.ERROR_GREATER_THAN)
                        || errMsg.equals(Validator.ERROR_LESS_THAN))) {
                    errors.add("legalID", new ActionMessage("error.number." + errMsg));
                }
            } else if (SEARCH_BY_ID_NUMBER.equals(form.getSearchButton())) {
                if (!(Validator.checkString(form.getIdNO(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
                    errors.add("idNO", new ActionMessage("error.mandatory"));
                }
                //Andy Wong, 13 Nov 2008: validate mandatory id type and issued country
                if (!(Validator.checkString(form.getIDType(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
                    errors.add("IDType", new ActionMessage("error.mandatory"));
                }
                if (!(Validator.checkString(form.getIssuedCountry(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
                    errors.add("issuedCountry", new ActionMessage("error.mandatory"));
                }
            }
        }

        // validation for save
        else if (CashPledgorAction.EVENT_SAVE_PLEDGOR.equals(form.getEvent())) {
            if (form.getSelected() == null) {
                errors.add("select", new ActionMessage("error.mandatory"));
            }
        }

        return errors;
    }
}
