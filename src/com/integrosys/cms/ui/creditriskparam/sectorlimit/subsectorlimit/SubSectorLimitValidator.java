package com.integrosys.cms.ui.creditriskparam.sectorlimit.subsectorlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import java.util.Locale;
public class SubSectorLimitValidator {
    public static ActionErrors validateInput(SubSectorLimitForm form, Locale locale) {

        ActionErrors errors = new ActionErrors();
        String event = form.getEvent();
        String errorCode = null;

        DefaultLogger.debug("this", "The value of secCode IS:" + form.getSubSectorCode());
        errorCode = Validator.checkString(form.getSubSectorCode(), true,1, 150);
         if (!(errorCode.equals(Validator.ERROR_NONE))) {
             errors.add("subSectorCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "150"));
         }

        DefaultLogger.debug("this", "The value of secCode IS:" + form.getSecCode());
        errorCode = Validator.checkString(form.getSecCode(), true, 1, 10);
         if (!(errorCode.equals(Validator.ERROR_NONE))) {
             errors.add("secCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "10"));
         }
        
        if (SubSectorLimitAction.EVENT_DELETE_ECO_ITEM.equals(event)) {       
            if (form.getDeleteEcoItems() == null || form.getDeleteEcoItems().length == 0) {
                errors.add("chkDeletes", new ActionMessage("error.chk.del.records"));
            }
        }

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
