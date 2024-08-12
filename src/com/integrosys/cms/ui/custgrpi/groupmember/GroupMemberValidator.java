package com.integrosys.cms.ui.custgrpi.groupmember;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;

import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: May 8, 2007
 * Time: 6:34:37 PM
 * To change this template use File | Settings | File Templates.
 */


public class GroupMemberValidator {

    private static final String ATLEAST_MANDATORY = "error.atleast.mandatory";
    private static String MAXIMUM_ALLOWED_AMOUNT_15_STR = IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR;
    private static double MAXIMIUM_ALLOWED_AMOUNT_15 = IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15;
    private static String DEFAULT_CURRENCY = IGlobalConstant.DEFAULT_CURRENCY;
    private static String LOGOBJ = GroupMemberValidator.class.getName();
    private static int MIN_CHAR_SEARCH = 5;

    private static int EXT_CUSTOMER_NAME_SEARCH_CHAR = 5;
    private static int INT_CUSTOMER_NAME_SEARCH_CHAR = 3;
    private static final double MAX_NUMBER = Double.parseDouble("999999999999999");

    public static ActionErrors validateInput(GroupMemberForm aForm, Locale locale) {
        ActionErrors errors = new ActionErrors();
        String errorCode = "";
        String event = aForm.getEvent();

        try {

            if ("first_search".equals(event)) {
/*
                if ("1".equals(aForm.getGobutton())) {
                    if (!(errorCode = Validator.checkString(aForm.getSourceType(), true, 3, 40)).equals(Validator.ERROR_NONE)) {
                        errors.add("sourceType", new ActionMessage("error.string.mandatory"));
                        DefaultLogger.debug(LOGOBJ, "(ext_customer_list) aForm.getSourceType() = " + aForm.getSourceType());
                    }
                    if (!(errorCode = Validator.checkString(aForm.getCustomerName(), true, 1, 40)).equals(Validator.ERROR_NONE) &&
                            !(errorCode = Validator.checkString(aForm.getLegalID(), true, 1, 40)).equals(Validator.ERROR_NONE) &&
                            !(errorCode = Validator.checkString(aForm.getIdNO(), true, 1, 40)).equals(Validator.ERROR_NONE)
                            ) {
                        errors.add("searchError", new ActionMessage("error.string.cci.search"));
                        DefaultLogger.debug(LOGOBJ, " no search enter");
                        DefaultLogger.debug(LOGOBJ, "(ext_customer_list) Check data " +
                                "CustomerName=" + aForm.getCustomerName() + ", " +
                                "LegalID =" + aForm.getLegalID() + ", " +
                                "IdNO()= " + aForm.getIdNO());
                    } else
                    if (!(errorCode = Validator.checkString(aForm.getCustomerName(), false, MIN_CHAR_SEARCH, 40)).equals(Validator.ERROR_NONE)) {
                        errors.add("cusName", new ActionMessage("error.string.cciCustomername"));
                        DefaultLogger.debug(LOGOBJ, "(ext_customer_list) CustomerName() must be 5 min char " + aForm.getCustomerName());
                    }
                }
*/
                if (aForm.getGobutton().equals("1"))  {
                    if (!(errorCode = Validator.checkString(aForm.getCustomerName(), true, 5, 40)).equals(Validator.ERROR_NONE)) {
                        errors.add("cusName", new ActionMessage("error.string.cciCustomername"));
//                        DefaultLogger.debug(LOGOBJ, " aForm.getCustomerName() = " + aForm.getCustomerName());
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
            } else if (GroupMemberAction.EVENT_SEARCH_GROUP.equals(event)) {
                if ("4".equals(aForm.getGobutton())) {
                    if (!(errorCode = Validator.checkNumber(aForm.getSearchGroupID(), true, 0, MAX_NUMBER)).equals(Validator.ERROR_NONE)) {
                        errors.add("searchGroupID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", MAX_NUMBER + ""));
                        DefaultLogger.debug(LOGOBJ, " aForm.searchGroupID() = " + aForm.getGrpID());

                    }
                } else if ("5".equals(aForm.getGobutton())) {
                    if (!(errorCode = Validator.checkString(aForm.getSearchGroupName(), true, 0, 40)).equals(Validator.ERROR_NONE)) {
                        errors.add("searchGroupName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", MAX_NUMBER + ""));
                        DefaultLogger.debug(LOGOBJ, " aForm.getSearchGroupName() = " + aForm.getGroupName());

                    }
                }

            }


        } catch (Exception e) {
        }

        DefaultLogger.debug(LOGOBJ, " No of Errors..." + errors.size());

        return errors;

    }


}
