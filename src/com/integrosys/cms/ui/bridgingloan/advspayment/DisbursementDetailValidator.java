package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 30, 2007 Time: 6:55:35 PM To
 * change this template use File | Settings | File Templates.
 */
public class DisbursementDetailValidator {
	public static ActionErrors validateInput(
			com.integrosys.cms.ui.bridgingloan.advspayment.DisbursementDetailForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errMsg = null;

		try {
			DefaultLogger.debug("in DisbursementDetailValidator", "form.getEvent()=" + form.getEvent());

			boolean isSubpurpose = false;
			if (form.getIsSubpurpose().equals("true")) {
				isSubpurpose = true;
			}
			String disbursementDate = form.getDisbursementDate();
			String subpurpose = form.getSubpurpose();
			String invoiceNumber = form.getInvoiceNumber();
			String invoiceCurrency = form.getInvoiceCurrency();
			String invoiceAmount = form.getInvoiceAmount();
			String disburseCurrency = form.getDisburseCurrency();
			String disburseAmount = form.getDisburseAmount();
			String disbursementMode = form.getDisbursementMode();
			String payee = form.getPayee();
			String referenceNumber = form.getReferenceNumber();
			String glReferenceNumber = form.getGlReferenceNumber();
			String remarks = form.getRemarks();

			if (!(errMsg = Validator.checkDate(disbursementDate, true, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("disbursementDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkString(subpurpose, isSubpurpose, 0, 10)).equals(Validator.ERROR_NONE)) {
				errors.add("subpurpose",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "10"));
			}
			if (!(errMsg = Validator.checkString(invoiceNumber, false, 0, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("invoiceNumber", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
						"30"));
			}
			if (!(errMsg = Validator.checkString(invoiceCurrency, false, 0, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("invoiceCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
						"3"));
			}
			if (!(errMsg = Validator.checkAmount(invoiceAmount, false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15,
					IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("invoiceAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errMsg = Validator.checkString(disburseCurrency, true, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("disburseCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
						"1", "3"));
			}
			if (!(errMsg = Validator.checkAmount(disburseAmount, true, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15,
					IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("disburseAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errMsg = Validator.checkString(disbursementMode, true, 0, 10)).equals(Validator.ERROR_NONE)) {
				errors.add("disbursementMode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
						"0", "10"));
			}
			if (!(errMsg = Validator.checkString(payee, true, 0, 150)).equals(Validator.ERROR_NONE)) {
				errors.add("payee", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "150"));
			}
			if (!(errMsg = Validator.checkString(referenceNumber, true, 0, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("referenceNumber", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
						"30"));
			}
			if (!(errMsg = Validator.checkString(glReferenceNumber, true, 0, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("glReferenceNumber", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
						"0", "30"));
			}
			if (!(errMsg = Validator.checkString(remarks, false, 0, 500)).equals(Validator.ERROR_NONE)) {
				errors.add("remarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "500"));
			}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}
}