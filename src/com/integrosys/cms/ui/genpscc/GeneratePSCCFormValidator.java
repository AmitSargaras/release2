package com.integrosys.cms.ui.genpscc;

import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class GeneratePSCCFormValidator {

    static boolean debugValidator = false;

    public static ActionErrors validateInput(GeneratePSCCForm aForm, Locale locale) {

//        if (debugValidator) {
//            System.out.println("PSCC FORM : " + aForm.toString());
//            System.out.println("Issued Index : " + aForm.getParSccIssuedIndex());
//            System.out.println("FORM : " + aForm.toString());
//        }

        ActionErrors errors = new ActionErrors();

        //String actLimit[] = aForm.getActLimit();
        String distAmt[] = aForm.getDistbursementAmt();
        String enforceAmt[] = aForm.getAmtEnforceTodate();
        String appLimit[] = aForm.getAppLimit();

        /*
        String cleanActLimit[] = aForm.getCleanActLimit();
        String notCleanActLimit[] = aForm.getNotCleanActLimit();
        String cleanAppLimit[] = aForm.getCleanAppLimit();
        String notCleanAppLimit[] = aForm.getNotCleanAppLimit();
        */

        String errorCode = "";
        String event = aForm.getEvent();

        DefaultLogger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$", "Inside Validation");

        if (event.equals("submit")) { // only required for submit event
            /*
            if (!(errorCode = Validator.checkString(aForm.getCreditOfficerName(), true, 1, 50))
                    .equals(Validator.ERROR_NONE)) {
                errors.add("creditOfficerName", new ActionMessage("error.string.mandatory", "1", "50"));
            }
            if (!(errorCode = Validator.checkString(aForm.getCreditOfficerSgnNo(), true, 1, 20))
                    .equals(Validator.ERROR_NONE)) {
                errors.add("creditOfficerSgnNo", new ActionMessage("error.string.mandatory", "1", "20"));
            }
            */
        }

        //Some knowledge before continue from this point beyond..
        //SCC Limit Amount to be Activated == Disbursement Amount
        //Limit Maturiry Date == Expiry of Availability Period

        //Validation for the Disbursement Amount - Copy from the code below:
        //TODO : The validation might not be accurated. Revamp this when have additional time.
        if ((distAmt != null && distAmt.length > 0) &&
            (enforceAmt != null && enforceAmt.length > 0)) {
            HashMap checkedIssued = new HashMap();
            try {
                String partIndex = aForm.getParSccIssuedIndex();

                StringTokenizer st = new StringTokenizer(partIndex, ",");
                while (st.hasMoreTokens()) {
                    checkedIssued.put(st.nextToken(), null);
                }
            } catch (Exception ex) {
            }

            try {
                if (!checkedIssued.isEmpty()) {
                for (int i = 0; i < distAmt.length; i++) {
                    //String temp = distAmt[i];
                    String curDistAmt = distAmt[i].trim();
                    //String temp1 = appLimit[i];
                    String curAppLimit = appLimit[i].trim();
                    String curEnforceAmt = enforceAmt[i].trim();

                    if (checkedIssued.containsKey(String.valueOf(i))) {
                        if (curDistAmt.equals("")) continue;

                        if (!(errorCode = Validator.checkAmount(curDistAmt, true, 0.0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
                            errors.add("distAmt" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0.0", appLimit[i]));
                        } else {
                            // checks that the activated amount does not exceeds the approved amount, only if the user input is a valid amount
                            if (CurrencyManager.convertToAmount(locale, aForm.getDistbursementAmtCurrCode()[i], curDistAmt).getAmountAsDouble() >
                                    CurrencyManager.convertToAmount(locale, aForm.getDistbursementAmtCurrCode()[i], curAppLimit).getAmountAsDouble()) {
                                errors.add("distAmt" + i, new ActionMessage("error.amount.not.greaterthan", "Disbursement Amount", "Approved Limit"));
                            }
                        }

                        if (!(errorCode = Validator.checkAmount(curEnforceAmt, true, 0.0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
                            errors.add("enforceAmt" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0.0", appLimit[i]));
                        } else {
                            // checks that the activated amount does not exceeds the approved amount, only if the user input is a valid amount
                            if (CurrencyManager.convertToAmount(locale, aForm.getDistbursementAmtCurrCode()[i], curEnforceAmt).getAmountAsDouble() >
                                    CurrencyManager.convertToAmount(locale, aForm.getDistbursementAmtCurrCode()[i], curAppLimit).getAmountAsDouble()) {
                                errors.add("enforceAmt" + i, new ActionMessage("error.amount.not.greaterthan", "Amount Enforce Todate", "Approved Limit"));
                            }
                        }
                    } else {
                        /*
                        if ((curDistAmt != null && curDistAmt.length() > 0) && (curEnforceAmt != null && curEnforceAmt.length() > 0)) {
                            if ((!curDistAmt.equals("0") && !curEnforceAmt.equals("0"))) {
                                errors.add("parSccIssued" + i, new ActionMessage("error.string.mandatory"));
                                if (!curDistAmt.equals("") && !curEnforceAmt.equals("")) {
                                    if (!(errorCode = Validator.checkAmount(curDistAmt, false, 0.0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
                                        errors.add("distAmt" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0.0", appLimit[i]));
                                    } else {
                                        // checks that the activated amount does not exceeds the approved amount, only if the user input is a valid amount
                                        if (CurrencyManager.convertToAmount(locale, aForm.getDistbursementAmtCurrCode()[i], curDistAmt).getAmountAsDouble() >
                                                CurrencyManager.convertToAmount(locale, aForm.getDistbursementAmtCurrCode()[i], curAppLimit).getAmountAsDouble()) {
                                            errors.add("distAmt" + i, new ActionMessage("error.amount.not.greaterthan", "Disbursement Amount", "Approved Limit"));
                                        }
                                    }
                                    
                                    if (!(errorCode = Validator.checkAmount(curEnforceAmt, true, 0.0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
                                        errors.add("enforceAmt" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0.0", appLimit[i]));
                                    } else {
                                        // checks that the activated amount does not exceeds the approved amount, only if the user input is a valid amount
                                        if (CurrencyManager.convertToAmount(locale, aForm.getDistbursementAmtCurrCode()[i], curEnforceAmt).getAmountAsDouble() >
                                                CurrencyManager.convertToAmount(locale, aForm.getDistbursementAmtCurrCode()[i], curAppLimit).getAmountAsDouble()) {
                                            errors.add("enforceAmt" + i, new ActionMessage("error.amount.not.greaterthan", "Amount Enforce Todate", "Approved Limit"));
                                        }
                                    }
                                }
                            }
                        }
                        */
                    }
                }
            }
            }
            catch (Exception e) {
                DefaultLogger.debug("GeneratePSCCFormValidator", "Exception ");
                e.printStackTrace();
            }
        }

        //errors.add("distAmt0", new ActionMessage("error.amount.not.greaterthan", "Disbursement Amount", "Approved Limit"));
        
        
        //-------------- Original Validation; Commented off by R1.5-CR146 ---------------
        // Activated Amount (SCC Limit Amount to be Activated)
        /*
        if (actLimit != null && actLimit.length > 0) {
            HashMap tempMap = new HashMap();
            try {
                String partIndex = aForm.getParSccIssuedIndex();

                StringTokenizer st = new StringTokenizer(partIndex, ",");
                while (st.hasMoreTokens()) {
                    tempMap.put(st.nextToken(), "ooo");
                }
            } catch (Exception ex) {
            }

            try {
                for (int i = 0; i < actLimit.length; i++) {
                    String temp = actLimit[i];
                    String temp1 = appLimit[i];

                    // Priya - CMSSP-686 :Wrong validation and Incorrect Error Message as "Minimum amount is $0.1"
                    if (tempMap.containsKey(String.valueOf(i))) {
                        if (!(errorCode = Validator.checkAmount(temp, true, 0.0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
                            errors.add("actLimit" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0.0", appLimit[i]));
                        } else {
                            // checks that the activated amount does not exceeds the approved amount, only if the user input is a valid amount
                            if (CurrencyManager.convertToAmount(locale, aForm.getActAmtCurrCode()[i], temp).getAmountAsDouble() >
                                    CurrencyManager.convertToAmount(locale, aForm.getActAmtCurrCode()[i], temp1).getAmountAsDouble()) {
                                errors.add("actLimit" + i, new ActionMessage("error.amount.not.greaterthan", "Activated limit", "Approved limit"));
                            }
                        }
                    } else {
                        if (temp != null && temp.trim().length() > 0) {
                            if ((!temp.trim().equals("0"))) {
                                errors.add("parSccIssued" + i, new ActionMessage("error.string.mandatory"));
                                if (!temp.trim().equals("")) {
                                    if (!(errorCode = Validator.checkAmount(temp, false, 0.0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
                                        errors.add("actLimit" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0.0", appLimit[i]));
                                    } else {
                                        // checks that the activated amount does not exceeds the approved amount, only if the user input is a valid amount
                                        if (CurrencyManager.convertToAmount(locale, aForm.getActAmtCurrCode()[i], temp).getAmountAsDouble() >
                                                CurrencyManager.convertToAmount(locale, aForm.getActAmtCurrCode()[i], temp1).getAmountAsDouble()) {
                                            errors.add("actLimit" + i, new ActionMessage("error.amount.not.greaterthan", "Activated limit", "Approved limit"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                DefaultLogger.debug("GeneratePSCCFormValidator", "Exception ");
                e.printStackTrace();
            }
        }
        */
        //--------------End of comment off by R1 .5 - CR146---------------


        /*
        // ---------------------- Start of R1.5-CR146 -----------------------
        HashMap tempMap = new HashMap();
        String partIndex = aForm.getParSccIssuedIndex();
        StringTokenizer st = new StringTokenizer(partIndex, ",");
        while (st.hasMoreTokens()) {
            tempMap.put(st.nextToken(), "ooo");
        }

        try {
            // Clean Activated Amount
            if ((cleanActLimit != null) && (cleanActLimit.length > 0)) {
                for (int i = 0; i < cleanActLimit.length; i++) {
                    String temp = cleanActLimit[i];
                    String temp1 = cleanAppLimit[i];
                    if (tempMap.containsKey(String.valueOf("clean" + i))) {
                        if (!(errorCode = Validator.checkAmount(temp, true, 0.0,
                                IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
                                .equals(Validator.ERROR_NONE)) {
                            errors.add("cleanActLimit" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
                                    errorCode), "0", UIUtil.trimDecimal(cleanAppLimit[i])));
                        } else {
                            // checks that the activated amount does not exceeds
                            // the approved amount, only if the user input is a
                            // valid amount
                            if (CurrencyManager.convertToAmount(locale, aForm.getCleanActAmtCurrCode()[i], temp)
                                    .getAmountAsDouble() > CurrencyManager.convertToAmount(locale,
                                    aForm.getCleanActAmtCurrCode()[i], temp1).getAmountAsDouble()) {
                                errors.add("cleanActLimit" + i, new ActionMessage("error.amount.not.greaterthan",
                                        "Activated limit", "Approved limit"));
                            }
                        }
                    } else {
                        if ((temp != null) && (temp.trim().length() > 0)) {
                            if ((!temp.trim().equals("0"))) {
                                errors.add("parSccIssuedClean" + i, new ActionMessage("error.string.mandatory"));
                                if (!temp.trim().equals("")) {
                                    if (!(errorCode = Validator.checkAmount(temp, false, 0.0,
                                            IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY,
                                            locale)).equals(Validator.ERROR_NONE)) {
                                        errors.add("cleanActLimit" + i, new ActionMessage(ErrorKeyMapper.map(
                                                ErrorKeyMapper.AMOUNT, errorCode), "0", UIUtil
                                                .trimDecimal(cleanAppLimit[i])));
                                    } else { // checks that the activated amount
                                        // does not exceeds the approved
                                        // amount, only if the user input is
                                        // a valid amount
                                        if (CurrencyManager.convertToAmount(locale, aForm.getCleanActAmtCurrCode()[i],
                                                temp).getAmountAsDouble() > CurrencyManager.convertToAmount(locale,
                                                aForm.getCleanActAmtCurrCode()[i], temp1).getAmountAsDouble()) {
                                            errors.add("cleanActLimit" + i,
                                                    new ActionMessage("error.amount.not.greaterthan",
                                                            "Activated limit", "Approved limit"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Not Clean Activated Amount
            if ((notCleanActLimit != null) && (notCleanActLimit.length > 0)) {
                for (int i = 0; i < notCleanActLimit.length; i++) {
                    String temp = notCleanActLimit[i];
                    String temp1 = notCleanAppLimit[i];
                    if (tempMap.containsKey(String.valueOf("notClean" + i))) {
                        if (!(errorCode = Validator.checkAmount(temp, true, 0.0,
                                IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
                                .equals(Validator.ERROR_NONE)) {
                            errors.add("notCleanActLimit" + i, new ActionMessage(ErrorKeyMapper.map(
                                    ErrorKeyMapper.AMOUNT, errorCode), "0", UIUtil.trimDecimal(notCleanAppLimit[i])));
                        } else { // checks that the activated amount does not
                            // exceeds the approved amount, only if the user
                            // input is a valid amount
                            if (CurrencyManager.convertToAmount(locale, aForm.getNotCleanActAmtCurrCode()[i], temp)
                                    .getAmountAsDouble() > CurrencyManager.convertToAmount(locale,
                                    aForm.getNotCleanActAmtCurrCode()[i], temp1).getAmountAsDouble()) {
                                errors.add("notCleanActLimit" + i, new ActionMessage("error.amount.not.greaterthan",
                                        "Activated limit", "Approved limit"));
                            }
                        }
                    } else {
                        if ((temp != null) && (temp.trim().length() > 0)) {
                            if ((!temp.trim().equals("0"))) {
                                errors.add("parSccIssuedNotClean" + i, new ActionMessage("error.string.mandatory"));
                                if (!temp.trim().equals("")) {
                                    if (!(errorCode = Validator.checkAmount(temp, false, 0.0,
                                            IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY,
                                            locale)).equals(Validator.ERROR_NONE)) {
                                        errors.add("notCleanActLimit" + i, new ActionMessage(ErrorKeyMapper.map(
                                                ErrorKeyMapper.AMOUNT, errorCode), "0", UIUtil
                                                .trimDecimal(notCleanAppLimit[i])));
                                    } else { // checks that the activated amount
                                        // does not exceeds the approved
                                        // amount, only if the user input is
                                        // a valid amount
                                        if (CurrencyManager.convertToAmount(locale,
                                                aForm.getNotCleanActAmtCurrCode()[i], temp).getAmountAsDouble() > CurrencyManager
                                                .convertToAmount(locale, aForm.getNotCleanActAmtCurrCode()[i], temp1)
                                                .getAmountAsDouble()) {
                                            errors.add("notCleanActLimit" + i,
                                                    new ActionMessage("error.amount.not.greaterthan",
                                                            "Activated limit", "Approved limit"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        catch (Exception e) {
            DefaultLogger.debug("GeneratePSCCFormValidator", "Exception ");
        }
        // ---------------------- End of R1.5-CR146 -----------------------
        */

        DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
        return errors;
    }
}
