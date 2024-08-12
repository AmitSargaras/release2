package com.integrosys.cms.ui.custgrpi.groupcreditgrade;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.custgrpi.CustGrpIdentifierFormValidator;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: May 8, 2007
 * Time: 6:34:37 PM
 * To change this template use File | Settings | File Templates.
 */


public class GroupCreditGradeValidator {


    private static final String ATLEAST_MANDATORY = "error.atleast.mandatory";
    private static String LOGOBJ = CustGrpIdentifierFormValidator.class.getName();
    private static String MAXIMUM_ALLOWED_AMOUNT_15_STR = IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR;
    private static double MAXIMIUM_ALLOWED_AMOUNT_15 = IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15;
    private static String DEFAULT_CURRENCY = IGlobalConstant.DEFAULT_CURRENCY;

    public static ActionErrors validateInput(GroupCreditGradeForm aForm, Locale locale) {
        String errorCode = null;
        ActionErrors errors = new ActionErrors();
        String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
        String event = aForm.getEvent();
        try {
//            if (!("approve".equals(event)) || "reject".equals(event)) {
//                if ("create".equals(event) || "update".equals(event)) {
//                    if (!(errorCode = Validator.checkString(aForm.getTypeCD(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
//                        errors.add("typeCD", new ActionMessage("error.string.mandatory"));
//                        DefaultLogger.debug(LOGOBJ, "aForm.getTypeCD() = " + aForm.getTypeCD());
//                    }
//
//                }
//            }

            //Andy Wong, 2 July 2008: validate rating and rating date
            if (StringUtils.isBlank(aForm.getRatingCD())) {
                errors.add("ratingCD", new ActionMessage("error.string.mandatory"));
                DefaultLogger.debug(LOGOBJ, "aForm.getRatingCD() = " + aForm.getRatingCD());
            }
            if (!(errorCode = Validator.checkDate(aForm.getRatingDt(), true, locale)).equals(Validator.ERROR_NONE)) {
                errors.add("ratingDt", new ActionMessage("error.date.mandatory"));
                DefaultLogger.debug(LOGOBJ, "aForm.getRatingDt() = " + aForm.getRatingDt());
            }

        } catch (Exception e) {
        }

        DefaultLogger.debug(LOGOBJ, " No of Errors..." + errors.size());

        return errors;

    }

}
