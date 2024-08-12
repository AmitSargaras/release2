/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.limitbooking;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

import java.util.Locale;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.common.UIValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author priya
 *
 */

public class LimitBookingValidator {

    private static final double MAX_NUMBER = Double.parseDouble("99999999999999999999");

    public static ActionErrors validateInput(LimitBookingForm form, Locale locale) {
        ActionErrors errors = new ActionErrors();
        String errorCode = null;
        String event = form.getEvent();
        
        String alphanumericRegex = "[a-zA-Z0-9\\s]*";
        
        String numericRegex = "[a-zA-Z0-9\\s]*";

        if (LimitBookingAction.EVENT_ADD_POL.equals(event)|| LimitBookingAction.EVENT_EDIT_POL.equals(event)){
        	if (!(errorCode = Validator.checkString(form.getProdType(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
                errors.add("prodTypeError", new ActionMessage("error.feeds.no.selection"));
            }
            if (!(errorCode = Validator.checkString(form.getPol(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
                errors.add("polError", new ActionMessage("error.feeds.no.selection"));
            }
            if (!(errorCode = Validator.checkString(form.getPolBkgCurrency(), true, 1, 3)).equals(Validator.ERROR_NONE)) {
                errors.add("polBkgCurrencyError",  new ActionMessage("error.feeds.no.selection"));
            }
            if (!(errorCode = Validator.checkNumber(form.getPolBkgAmount(), true, 0.01, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, 3,locale)).equals(Validator.ERROR_NONE)) {
                errors.add("polBkgAmountError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0.01", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
            }
        }
        else if (LimitBookingAction.EVENT_SEARCH_GROUP.equals(event)){
            if ("1".equals(form.getGobutton())) {
                if (!(errorCode = Validator.checkNumber(form.getSearchGroupNo(), true, 0, MAX_NUMBER)).equals(Validator.ERROR_NONE)) {
                    errors.add("searchGroupNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "5", MAX_NUMBER + ""));

                } else if (!Validator.checkPattern(form.getSearchGroupNo(), numericRegex)) {
            		errors.add("searchGroupNo", new ActionMessage("error.number.format"));
            	}
            } else if ("2".equals(form.getGobutton())) {
                if (!(errorCode = Validator.checkString(form.getSearchGroupName(), true, 5, 150)).equals(Validator.ERROR_NONE)) {
                    errors.add("searchGroupName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "5", "150"));
                }
                
            }
        }
        else if (LimitBookingAction.EVENT_REMOVE_POL_ADD.equals(event) || LimitBookingAction.EVENT_REMOVE_POL_EDIT.equals(event)){
	         if(form.getPolDeletedList() == null || form.getPolDeletedList().length == 0) {
		            errors.add("polDeletedListError", new ActionMessage("error.pol.deleted.list"));
	         }
        }
        else if (LimitBookingAction.EVENT_REMOVE_GROUP_ADD.equals(event) || LimitBookingAction.EVENT_REMOVE_GROUP_EDIT.equals(event)){
	         if(form.getBankGroupDeletedList() == null || form.getBankGroupDeletedList().length == 0) {
		            errors.add("bankGroupDeletedListError", new ActionMessage("error.bank.group.list"));
	         }
        }
        else if (LimitBookingAction.EVENT_RETRIEVE_BGEL_ADD.equals(event) || LimitBookingAction.EVENT_RETRIEVE_BGEL_EDIT.equals(event)){
        	if (!(errorCode = Validator.checkString(form.getBkgIDNo(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
                errors.add("bkgIDNoError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "20"));
            }
        	
        }
        else if (LimitBookingAction.EVENT_SEARCH_BOOKING.equals(event)){
            if ("1".equals(form.getGobuttonBooking())) {
            	
            	boolean isMandatory = false;
            	
            	if (!(errorCode = Validator.checkDate(form.getSearchFromDate(), true, locale)).equals(Validator.ERROR_NONE)) {
                    errors.add("searchFromDateError", new ActionMessage("error.date.mandatory", "1", "256"));
                    isMandatory = true;
                }
            	if (!(errorCode = Validator.checkDate(form.getSearchToDate(), true, locale)).equals(Validator.ERROR_NONE)) {
                    errors.add("searchToDateError", new ActionMessage("error.date.mandatory", "1", "256"));
                    isMandatory = true;
                }
            	
            	if (!isMandatory && !(errorCode = UIValidator.compareDateEarlier(form.getSearchFromDate(), form.getSearchToDate(), locale)).equals(Validator.ERROR_NONE)) {
            		errors.add("searchToDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "From Date", "To Date"));	
            	}
            	
            } else if ("2".equals(form.getGobuttonBooking())) {
                if (!(errorCode = Validator.checkString(form.getSearchTicketNo(), true, 1, 15)).equals(Validator.ERROR_NONE)) {
                    errors.add("searchTicketNoError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
                }
                else if (!Validator.checkPattern(form.getSearchTicketNo(), alphanumericRegex))
                {
            		errors.add("searchTicketNoError", new ActionMessage("error.limit.booking.alphanumeric"));
            	}
            } else if ("3".equals(form.getGobuttonBooking())) {
                if (!(errorCode = Validator.checkString(form.getSearchBookingGroupName(), true, 5, 150)).equals(Validator.ERROR_NONE)) {
                    errors.add("searchBookingGroupNameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "5", "150"));
                }
                
            } else if ("4".equals(form.getGobuttonBooking())) {
                if (!(errorCode = Validator.checkString(form.getSearchCustomerName(), true, 5, 150)).equals(Validator.ERROR_NONE)) {
                    errors.add("searchCustomerNameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "5", "150"));
                }
               
            } else if ("5".equals(form.getGobuttonBooking())) {
                if (!(errorCode = Validator.checkString(form.getSearchIDNo(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
                    errors.add("searchIDNoError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "20"));
                }
                
            }
        }
        else
        {
            if (!(errorCode = Validator.checkString(form.getIsExistingCustomer(), true, 1, 1)).equals(Validator.ERROR_NONE)) {
                errors.add("isExistingCustomerError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "1"));
            }
            if (!(errorCode = Validator.checkString(form.getIsFinancialInstitution(), true, 1, 1)).equals(Validator.ERROR_NONE)) {
                errors.add("isFinancialInstitutionError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "1"));
            }
            
            if (!(errorCode = Validator.checkString(form.getBkgName(), true, 1, 150)).equals(Validator.ERROR_NONE)) {
                errors.add("bkgNameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "150"));
            }
            
            
            if (!(errorCode = Validator.checkString(form.getBkgIDNo(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
                errors.add("bkgIDNoError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "20"));
            }
            
            
            if (!(errorCode = Validator.checkString(form.getBkgBusSector(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
                errors.add("bkgBusSectorError", new ActionMessage("error.feeds.no.selection"));
            }
            if (!(errorCode = Validator.checkString(form.getAaSource(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
                errors.add("aaSourceError", new ActionMessage("error.feeds.no.selection"));
            }
            
            if (!(errorCode = Validator.checkString(form.getAaNo(), false, 1, 40)).equals(Validator.ERROR_NONE)) {
                errors.add("aaNoError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "40"));
            }
            else if (!Validator.checkPattern(form.getAaNo(), alphanumericRegex))
            {
        		errors.add("aaNoError", new ActionMessage("error.limit.booking.alphanumeric"));
        	}
            
            if (!(errorCode = Validator.checkString(form.getBkgCountry(), true, 1, 3)).equals(Validator.ERROR_NONE)) {
                errors.add("bkgCountryError",  new ActionMessage("error.feeds.no.selection"));
            }
            if (!(errorCode = Validator.checkString(form.getBkgBusUnit(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
                errors.add("bkgBusUnitError",  new ActionMessage("error.feeds.no.selection"));
            }
            if (!(errorCode = Validator.checkString(form.getBkgBankEntity(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
                errors.add("bkgBankEntityError", new ActionMessage("error.feeds.no.selection"));
            }
            if (!(errorCode = Validator.checkString(form.getBkgCurrency(), true, 1, 3)).equals(Validator.ERROR_NONE)) {
                errors.add("bkgCurrencyError",  new ActionMessage("error.feeds.no.selection"));
            }
           if (!(errorCode = Validator.checkNumber(form.getBkgAmount(), true, 0.01, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, 3,locale)).equals(Validator.ERROR_NONE)) {
                errors.add("bkgAmountError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0.01", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
            }
        }

        DefaultLogger.debug(" Total Errors", "--------->" + errors.size());

        return errors;
    }
}