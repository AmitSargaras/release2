package com.integrosys.cms.ui.cci;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.custrelationship.customer.CRCustomerSearchAction;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;

import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 9, 2007
 * Time: 3:56:31 PM
 * To change this template use File | Settings | File Templates.
 */


public class CounterpartySearchFormValidator {

    private static String LOGOBJ = CounterpartySearchFormValidator.class.getName();

    private static int INT_CUSTOMER_NAME_SEARCH_CHAR = 3 ;

    public static ActionErrors validateInput(CounterpartySearchForm aForm, Locale locale) {
        ActionErrors errors = new ActionErrors();
        String errorCode = "";
        final String MAX_NUMBER = "99999999999999999";
        String event = aForm.getEvent();
        String regex = "[0-9]*";

        try {
            if ("customer_list".equals(event) || CRCustomerSearchAction.EVENT_CUST_SEARCH.equals(event)) {
/*
                 if ("1".equals(aForm.getGobutton())) {
                        if (!(errorCode = Validator.checkString(aForm.getLeIDType(), true, 3, 40)).equals(Validator.ERROR_NONE)) {
                            errors.add("leIDType", new ActionMessage("error.string.mandatory"));
                            DefaultLogger.debug(LOGOBJ, "(customer_list) aForm.leIDType() = " + aForm.getLeIDType());
                        }
                     if (!(errorCode = Validator.checkString(aForm.getCustomerName(), true, 1, 40)).equals(Validator.ERROR_NONE) &&
                         !(errorCode = Validator.checkString(aForm.getLegalID(), true, 1, 40)).equals(Validator.ERROR_NONE) &&
                         !(errorCode = Validator.checkString(aForm.getIdNO(), true, 1, 40)).equals(Validator.ERROR_NONE)
                             ) {
                         errors.add("searchError", new ActionMessage("error.string.cci.search"));
                         DefaultLogger.debug(LOGOBJ, " no search enter"  );
                    }else  if (!(errorCode = Validator.checkString(aForm.getCustomerName(), false, EXT_CUSTOMER_NAME_SEARCH_CHAR, 40)).equals(Validator.ERROR_NONE)) {
                        errors.add("cusName", new ActionMessage("error.string.cciCustomername"));
                        DefaultLogger.debug(LOGOBJ, "(customer_list) CustomerName() must be " + EXT_CUSTOMER_NAME_SEARCH_CHAR + " min char " + aForm.getCustomerName());
                    }
                }
*/
                if (aForm.getGobutton().equals("1"))  {
                    if (!(errorCode = Validator.checkString(aForm.getCustomerName(), true, 5, 40)).equals(Validator.ERROR_NONE)) {
                        errors.add("cusName", new ActionMessage("error.string.cciCustomername"));
                        DefaultLogger.debug(LOGOBJ, " aForm.getCustomerName() = " + aForm.getCustomerName());
                    }
                }

                if (aForm.getGobutton().equals("2")) {
                    if (!(errorCode = Validator.checkString(aForm.getLeIDType(), true, 1, 10)) .equals(Validator.ERROR_NONE)) {
                        errors.add("leIDType", new ActionMessage("error.mandatory"));
                        DefaultLogger.debug(LOGOBJ, " aForm.getLeIDType() = " + aForm.getLeIDType());
                    }
                    if (!(errorCode = Validator.checkString(aForm.getLegalID(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
                        errors.add("legalID", new ActionMessage("error.string.legalid"));
                        DefaultLogger.debug(LOGOBJ, " aForm.getLegalID() = " + aForm.getLegalID());
                    }
                }
                if (aForm.getGobutton().equals("3")) {
                    if (!(errorCode = Validator.checkString(aForm.getIdNO(), true, 1, 100)).equals(Validator.ERROR_NONE)) {
                        errors.add("idNO", new ActionMessage("error.string.idno"));
                        DefaultLogger.debug(LOGOBJ, " aForm.getIdNO() = " + aForm.getIdNO());
                    }
                }
            } else if ("list".equals(event)) {
                if ("1".equals(aForm.getGobutton())) {
                    if (!(errorCode = Validator.checkString(aForm.getCustomerName(), true, INT_CUSTOMER_NAME_SEARCH_CHAR, 40)).equals(Validator.ERROR_NONE)) {
                        errors.add("customerName", new ActionMessage("error.string.int_cciCustomername"));
                        DefaultLogger.debug(LOGOBJ, " (List) CustomerName() must be " +  INT_CUSTOMER_NAME_SEARCH_CHAR  +" min char  " + aForm.getCustomerName());
                    }
                } else if ("2".equals(aForm.getGobutton())) {
                    if (!(errorCode = Validator.checkString(aForm.getLeIDType(), true, 1, 10)) .equals(Validator.ERROR_NONE)) {
                        errors.add("leIDType", new ActionMessage("error.mandatory"));
                        DefaultLogger.debug(LOGOBJ, " aForm.getLeIDType() = " + aForm.getLeIDType());
                    }
                    if (!(errorCode = Validator.checkString(aForm.getLegalID(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
                        errors.add("legalID", new ActionMessage("error.string.legalid"));
                        DefaultLogger.debug(LOGOBJ, " aForm.getLegalID() = " + aForm.getLegalID());
                    }
                } else if ("3".equals(aForm.getGobutton())) {
                    if (!(errorCode = Validator.checkString(aForm.getIdNO(), true, 1, 100)).equals(Validator.ERROR_NONE)) {
                        errors.add("idNO", new ActionMessage("error.string.idno"));
                        DefaultLogger.debug(LOGOBJ, " aForm.getIdNO() = " + aForm.getIdNO());
                    }
                } else if ("4".equals(aForm.getGobutton())) {
                    if (!(errorCode = Validator.checkNumber(aForm.getGroupCCINo(), true, 0, Double.parseDouble(MAX_NUMBER))).equals(Validator.ERROR_NONE)){
                        errors.add("groupCCINo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", MAX_NUMBER));
                        DefaultLogger.debug(LOGOBJ, " aForm.getGroupCCINo() = " + aForm.getGroupCCINo());

                    } else if (aForm.getGroupCCINo().indexOf(".") != -1) {
                        errors.add("groupCCINo", new ActionMessage("error.amount.decimalnotallowed"));
                    }  else if (!Validator.checkPattern(aForm.getGroupCCINo(), regex)) {
                		errors.add("groupCCINo", new ActionMessage("error.number.format"));
                	}  
                }
            } else if ("checker_approve_edit_cci".equals(event) || "checker_reject_edit_cci".equals(event) || "submit".equals(event)) {
                errorCode = RemarksValidatorUtil.checkRemarks(aForm.getRemarks(), false);
                if (!Validator.ERROR_NONE.equals(errorCode)) {
                    errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
                }
            }

        } catch (Exception e) {
        }

        DefaultLogger.debug(LOGOBJ, "CustomerSearchFormValidator , No of Errors..." + errors.size());

        return errors;

    }

}
