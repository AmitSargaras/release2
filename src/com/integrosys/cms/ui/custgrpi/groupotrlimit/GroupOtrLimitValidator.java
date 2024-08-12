package com.integrosys.cms.ui.custgrpi.groupotrlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.custgrpi.CustGrpIdentifierFormValidator;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;

import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: July 1, 2008
 * Time: 6:34:37 PM
 * To change this template use File | Settings | File Templates.
 */


public class GroupOtrLimitValidator {

    private static String LOGOBJ = CustGrpIdentifierFormValidator.class.getName();
    private static String MAXIMUM_ALLOWED_AMOUNT_15_STR = IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR;
    private static double MAXIMIUM_ALLOWED_AMOUNT_15 = IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15;
    private static String DEFAULT_CURRENCY = IGlobalConstant.DEFAULT_CURRENCY;

    public static ActionErrors validateInput(GroupOtrLimitForm aForm, Locale locale) {
        ActionErrors errors = new ActionErrors();
        String errorCode = "";
        String event = aForm.getEvent();

        try {
            if (!("approve".equals(event)) || "reject".equals(event)) {
                if ("create".equals(event) || "update".equals(event)) {
                    if (AbstractCommonMapper.isEmptyOrNull(aForm.getOtrLimitTypeCD())) {
                        errors.add("otrLimitTypeCD", new ActionMessage("error.string.mandatory"));
                        DefaultLogger.debug(" ERROR occured , otrLimitTypeCD is  mandatory", "");
                    }

                    if (AbstractCommonMapper.isEmptyOrNull(aForm.getDesc())) {
                        errors.add("desc", new ActionMessage("error.string.mandatory"));
                        DefaultLogger.debug(" ERROR occured , desc is  mandatory", "");
                    }

                    if (!AbstractCommonMapper.isEmptyOrNull(aForm.getLimitAmt())) {
                        if (AbstractCommonMapper.isEmptyOrNull(aForm.getCurrencyCD())) {
                            errors.add("currencyCD", new ActionMessage("error.string.mandatory"));
                            DefaultLogger.debug(" ERROR occured , currency is  mandatory", "");
                        } else {
                            if (!(errorCode = Validator.checkAmount(aForm.getLimitAmt(), false, 0,
                                    MAXIMIUM_ALLOWED_AMOUNT_15, DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
                                errors.add("limitAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1", MAXIMUM_ALLOWED_AMOUNT_15_STR));
                                DefaultLogger.debug(LOGOBJ, "aForm.limitAmt() =" + aForm.getLimitAmt());
                            }
                        }
                    }else
                    {
                        errors.add("limitAmt", new ActionMessage("error.string.mandatory"));
                        DefaultLogger.debug(LOGOBJ, "aForm.getLimitAmt() = " + aForm.getLimitAmt());
                    }

                    if (!(errorCode = Validator.checkDate(aForm.getLastReviewedDt(), true, locale)).equals(Validator.ERROR_NONE)) {
                        errors.add("lastReviewedDt", new ActionMessage("error.date.mandatory"));
                        DefaultLogger.debug(LOGOBJ, "aForm.getLastReviewedDt() = " + aForm.getLastReviewedDt());
                    }
                    
                    if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getRemarks(), false, 150, 3)).equals(Validator.ERROR_NONE)) {
    	                errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode, 150, 3));
    	                DefaultLogger.debug(" ERROR occured , remarks length has exceeded", "");
    	            }
                }

            }

        } catch (Exception e) {
        }

        DefaultLogger.debug(LOGOBJ, " No of Errors..." + errors.size());

        return errors;

    }


}
