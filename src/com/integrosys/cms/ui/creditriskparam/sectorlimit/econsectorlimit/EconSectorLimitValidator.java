package com.integrosys.cms.ui.creditriskparam.sectorlimit.econsectorlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import java.util.Locale;

public class EconSectorLimitValidator {
    public static ActionErrors validateInput(EconSectorLimitForm form, Locale locale) {

        ActionErrors errors = new ActionErrors();
        String errorCode = null;
        DefaultLogger.debug("this", "The value of econSectorCode IS:" + form.getEconSectorCode());
        if (!(Validator.checkString(form.getEconSectorCode(), true, 1, 150)).equals(Validator.ERROR_NONE)) {
            errors.add("econSectorCode",  new ActionMessage("error.feeds.no.selection"));
        }
         DefaultLogger.debug("this", "The value of secCode IS:" + form.getSecCode());
         errorCode = Validator.checkString(form.getSecCode(), true, 1, 100);
         if (!(errorCode.equals(Validator.ERROR_NONE))) {
             errors.add("secCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "100"));
         }
/*        if(form.getSecCode() == null || form.getSecCode().equals("")){
        	 errors.add("secCode", new ActionMessage("error.string.mandatory"));
        }*/

        DefaultLogger.debug("this", "The value of limitPercentage IS:" + form.getLimitPercentage());
        errorCode = Validator.checkNumber(form.getLimitPercentage(), false, 0, 100);
         if (!(errorCode.equals(Validator.ERROR_NONE))) {
             errors.add("limitPercentage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100"));
         }

        DefaultLogger.debug("this", "The value of conventionalBankPercentage IS:" + form.getConventionalBankPercentage());
        errorCode = Validator.checkNumber(form.getConventionalBankPercentage(), false, 0, 100);
         if (!(errorCode.equals(Validator.ERROR_NONE))) {
              errors.add("conventionalBankPercentage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100"));
         }

        DefaultLogger.debug("this", "The value of islamicBankPercentage IS:" + form.getIslamicBankPercentage());
        errorCode = Validator.checkNumber(form.getIslamicBankPercentage(), false, 0, 100);
         if (!(errorCode.equals(Validator.ERROR_NONE))) {
              errors.add("islamicBankPercentage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100"));
         }

        DefaultLogger.debug("this", "The value of investmentBankPercentage IS:" + form.getInvestmentBankPercentage());
        errorCode = Validator.checkNumber(form.getInvestmentBankPercentage(), false, 0, 100);
         if (!(errorCode.equals(Validator.ERROR_NONE))) {
              errors.add("investmentBankPercentage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100"));
         }


        return errors;        
    }
}
