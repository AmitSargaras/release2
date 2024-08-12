/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealValidator
 *
 * Created on 3:25:06 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.predeal;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 21, 2007 Time: 3:25:06 PM
 */
public final class PreDealValidator {
	private static final PreDealValidator self = new PreDealValidator();

	private PreDealValidator() {

	}

	public static ActionErrors validateSearchParameters(PreDealForm form, Locale locale) {
		DefaultLogger.debug(self, "Validating for search parameters");

		ActionErrors errors = new ActionErrors();

		String isinCode = form.getIsinCode();
		String counterName = form.getCounterName();
		String ric = form.getRic();

		boolean hasInput = Validator.validateMandatoryField(isinCode)
                || Validator.validateMandatoryField(counterName)
				|| Validator.validateMandatoryField(ric);

		if (!hasInput) {
			DefaultLogger.debug(self, "Search input is empty");
			errors.add("searchError", new ActionMessage("error.search.input.required"));
        }

        DefaultLogger.debug(self, "Validating  validateSearchParameters " + errors.size());

        return errors;
	}

	public static ActionErrors validateNewEarMarking(PreDealForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		ActionMessage error;
		String errorCode;

		DefaultLogger.debug(self, "Validating for new ear marking");

		errorCode = Validator.checkString(form.getCustomerName(), true, 1, 40);

		if (!Validator.ERROR_NONE.equals(errorCode)) {
			error = new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "40");

			errors.add("customerName", error);
		}

		errorCode = Validator.checkString(form.getSourceSystem(), true, 1, 40);

		if (!Validator.ERROR_NONE.equals(errorCode)) {
			error = new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode));

			errors.add("sourceSystem", error);
		}

		errorCode = Validator.checkString(form.getSecurityId(), false, 1, 40);

		if (!Validator.ERROR_NONE.equals(errorCode)) {
			error = new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "40");

			errors.add("securityId", error);
		}

		errorCode = Validator.checkString(form.getAaNumber(), true, 1, 40);

		if (!Validator.ERROR_NONE.equals(errorCode)) {
			error = new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "40");

			errors.add("aaNumber", error);
		}

		errorCode = Validator.checkString(form.getBranchName(), true, 1, 40);

		if (!Validator.ERROR_NONE.equals(errorCode)) {
			error = new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "40");

			errors.add("branchName", error);
		}

		errorCode = Validator.checkString(form.getBranchCode(), true, 1, 40);

		if (!Validator.ERROR_NONE.equals(errorCode)) {
			error = new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "40");

			errors.add("branchCode", error);
		}

		errorCode = Validator.checkString(form.getCifNumber(), false, 1, 40);

		if (!Validator.ERROR_NONE.equals(errorCode)) {
			error = new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "40");

			errors.add("cifNumber", error);
		}

		errorCode = Validator.checkString(form.getAccountNo(), false, 1, 40);

		if (!Validator.ERROR_NONE.equals(errorCode)) {
			error = new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "40");

			errors.add("accountNo", error);
		}

		errorCode = Validator.checkInteger(form.getEarMarkUnits(), true, 1, Integer.MAX_VALUE);

		if (!Validator.ERROR_NONE.equals(errorCode)) {
			error = new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), "1", String
					.valueOf(Integer.MAX_VALUE));

			errors.add("earMarkUnits", error);
		}

		return errors;
	}

	public static ActionErrors validateEarMarkRelease(PreDealForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		ActionMessage error;
		String errorCode;

		DefaultLogger.debug(self, "Validating for release ear mark");

		errorCode = Validator.checkString(form.getReleaseStatus(), true, 1, 40);

		if (!Validator.ERROR_NONE.equals(errorCode)) {
			error = new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode));

			errors.add("releaseStatus", error);
		}

		errorCode = Validator.checkString(form.getInfoIncorrectDetails(), !form.getInfoCorrectInd(), 1, 250); // mandatory
																												// if
																												// information
																												// incorrect
																												// indicator
																												// is
																												// true

		if (!Validator.ERROR_NONE.equals(errorCode)) {
			error = new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "250");

			errors.add("infoIncorrectDetails", error);
		}

		return errors;
	}

}