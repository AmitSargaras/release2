package com.integrosys.cms.ui.creditriskparam.sectorlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import java.util.Locale;

/**
 * Author: Syukri
 * Author: KC Chin
 * Date: Jun 12, 2008
 */
public class SectorLimitValidator {

    public static ActionErrors validateInput(SectorLimitForm form, Locale locale) {
        String event = form.getEvent();
        ActionErrors errors = new ActionErrors();

        String errorCode = null;

       // DefaultLogger.debug("********** Main Validation event=" + event, event);
        
        DefaultLogger.debug("this","**********Main Validation event="+event);
       
        if (SectorLimitAction.EVENT_ADD_ITEM.equals(event) ||
        		SectorLimitAction.EVENT_EDIT_ITEM.equals(event) ||
        		SectorLimitAction.EVENT_DELETE_ITEM.equals(event) ||
        		SectorLimitAction.EVENT_SUBMIT.equals(event)) {

            DefaultLogger.debug("this", "The value of secCode IS:" + form.getSectorCode());
            errorCode = Validator.checkString(form.getSectorCode(), true,1, 150);
             if (!(errorCode.equals(Validator.ERROR_NONE))) {
                 errors.add("sectorCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "150"));
             }
            
            DefaultLogger.debug("this", "The value of secCode IS:" + form.getSecCode());
            errorCode = Validator.checkString(form.getSecCode(), true, 1, 10);
             if (!(errorCode.equals(Validator.ERROR_NONE))) {
                 errors.add("secCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "10"));
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
        }
        
        if (SectorLimitAction.EVENT_DELETE_ITEM.equals(event)) {       
            if (form.getDeleteItems() == null || form.getDeleteItems().length == 0) {
                errors.add("chkDeletes", new ActionMessage("error.chk.del.records"));
            }
        }

        if (SectorLimitAction.EVENT_APPROVE.equals(event) ||
        		SectorLimitAction.EVENT_REJECT.equals(event) ||
        		SectorLimitAction.EVENT_CLOSE.equals(event) ||
        		SectorLimitAction.EVENT_SUBMIT.equals(event) ||
        		SectorLimitAction.EVENT_DELETE.equals(event)) {
            errorCode = RemarksValidatorUtil.checkRemarks(form.getRemarks(), false);
            if (!Validator.ERROR_NONE.equals(errorCode)) {
                errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
            }
        }
        
        return errors;
    }
}
