package com.integrosys.cms.ui.collateral;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Jan 12, 2009
 * Time: 7:00:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManualValuationValidator {

    private static String LOGOBJ = ManualValuationValidator.class.getName();
    private static String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2).toString();



    public static ActionErrors validateInput(CollateralForm aForm, Locale locale, ActionErrors errors) {
        String errorCode = null;
        DefaultLogger.debug(LOGOBJ, ">>>>>>>>>>>>>############# in ManualValuationValidator");

        boolean isMandatory = false;
        boolean validateCurrency = false;
        boolean validateOmv = false;
        boolean validateValuer = false;

        if ("submit".equals(aForm.getEvent())) {
			isMandatory = true;
            validateCurrency = true;
            validateOmv = true;
            validateValuer = true;
        }

        DefaultLogger.debug(LOGOBJ, ">>>>>>>1. validateCurrency=[" + validateCurrency + "] validateOmv=[" + validateOmv + "]");

        validateCurrency = validateCurrency || (aForm.getAmountCMV().length() > 0);
        validateOmv = validateOmv ||  (aForm.getValCurrency().length() > 0);
        validateValuer = validateValuer ||  (aForm.getValuerInGCMS().length() > 0);

        DefaultLogger.debug(LOGOBJ, ">>>>>>>2. validateCurrency=[" + validateCurrency + "] validateOmv=[" + validateOmv + "]");

        if (aForm.getEvent().equals("update") || aForm.getEvent().equals("submit")) {
			if (aForm.getIsSSC().equals("false")) {
                //Andy Wong, 6 Feb 2009: validate valuation info when valuer provided
                if (aForm.getAmountCMV().length() > 0 || aForm.getValDate().length() > 0 || aForm.getValCurrency().length() > 0 || aForm.getValuerInGCMS().length() > 0) {

                    if (validateOmv&&!errors.get("amountCMV").hasNext()) {
                        DefaultLogger.debug(LOGOBJ, ">>>>>>>>>>>>>>>>>>> validateOmv");
                        if (!(errorCode = Validator.checkAmount(aForm.getAmountCMV(), true, 0,
                                IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
                                .equals(Validator.ERROR_NONE)) {
                            errors.add("amountCMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
                                    "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
                            DefaultLogger.error(LOGOBJ, "aForm.getAmountCMV(): " + aForm.getAmountCMV());
                            DefaultLogger.debug(LOGOBJ, ">>>>>>>>>>>>>>>>>>> error is with amountCMV");
                        }
                    }
                    
                    if(!(errorCode = Validator.checkAmount(aForm.getAmountFSV(), false, 0,
                            IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
                            .equals(Validator.ERROR_NONE)){
                    	errors.add("amountFSV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),"0",
                    			IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_STR));
                    }

                    /*Govind S:Commented for HDFC bank*/
                   
                    /*if (validateCurrency) {
                        DefaultLogger.debug(LOGOBJ, ">>>>>>>>>>>>>>>>>>> validateCurrency");
                        if (!(errorCode = Validator.checkString(aForm.getValCurrency(), true, 1, 3))
                                .equals(Validator.ERROR_NONE)) {
                            errors.add("valCurrency", new ActionMessage(ErrorKeyMapper
                                    .map(ErrorKeyMapper.STRING, errorCode), "0", 3 + ""));
                            DefaultLogger.error(LOGOBJ, "getAmountCMV valCurrency()");
                            DefaultLogger.debug(LOGOBJ, ">>>>>>>>>>>>>>>>>>> error is with valCurrency");
                        }
                    }
                    */
                    /*Govind S:Commented for HDFC bank*/
                    /*
                    //Andy Wong: validate valuer when valuation info input
                    if (validateValuer) {
                        if (!(errorCode = Validator.checkString(aForm.getValuerInGCMS(), true, 1, 100))
                                .equals(Validator.ERROR_NONE)) {
                            errors.add("valuerInGCMS", new ActionMessage(ErrorKeyMapper
                                    .map(ErrorKeyMapper.STRING, errorCode)));
                        }
                    }
                    */
                    if (!(errorCode = Validator.checkString(aForm.getValuationType(), false, 1, 3))
                            .equals(Validator.ERROR_NONE)) {
                        errors.add("valuationType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
                                errorCode), "0", 3 + ""));
                        DefaultLogger.error(LOGOBJ, "getValuationType valuationTypes()");
                        DefaultLogger.debug(LOGOBJ, ">>>>>>>>>>>>>>>>>>> error is with valuationTypes");
                    }
                    /*Govind S:Commented for HDFC bank*/
                    /*
                    if (!(errorCode = Validator.checkDate(aForm.getValDate(), isMandatory, locale))
                            .equals(Validator.ERROR_NONE)) {
                        errors.add("valDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
                                "1", 250 + ""));
                        DefaultLogger.error(LOGOBJ, "getAmountCMV valDate()");
                        DefaultLogger.debug(LOGOBJ, ">>>>>>>>>>>>>>>>>>> error is with valDate");
                    }
                    */
                }
			}

		}

		DefaultLogger.debug(LOGOBJ, "ManualValuationValidator , No of Errors..." + errors.size());
		return errors;

	}
}
