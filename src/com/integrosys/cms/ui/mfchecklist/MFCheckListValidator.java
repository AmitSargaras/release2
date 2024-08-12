package com.integrosys.cms.ui.mfchecklist;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class MFCheckListValidator {

	public static ActionErrors validateInput(ActionForm aForm, Locale locale) {

		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		MFCheckListForm form = (MFCheckListForm) aForm;

		if (AbstractCommonMapper.isEmptyOrNull(form.getMFTemplateID())) {
			errors.add("MFTemplateID", new ActionMessage("error.string.mandatory"));
		}
		String[] valuerAssignFactorList = form.getValuerAssignFactorList();
		if ((valuerAssignFactorList != null) && (valuerAssignFactorList.length > 0)) {
			for (int j = 0; j < valuerAssignFactorList.length; j++) {
				String value = valuerAssignFactorList[j];
				// System.out.println("validate items........value="+value);

				if (AbstractCommonMapper.isEmptyOrNull(value)) {
					errors.add("valuerAssignFactor" + j, new ActionMessage("error.string.mandatory"));
				}
				else {

					if (!(errorCode = Validator.checkNumber(value, true, 1, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_7_2,
							3, locale)).equals(Validator.ERROR_NONE)) {
						if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
							errors.add("valuerAssignFactor" + j, new ActionMessage(ErrorKeyMapper.map(
									ErrorKeyMapper.NUMBER, "heightlessthan"), "1",
									IGlobalConstant.MAXIMUM_ALLOWED_VALUE_7_2_STR));

						}
						else if (errorCode.equals("decimalexceeded")) {
							errors.add("valuerAssignFactor" + j, new ActionMessage("error.number.moredecimalexceeded",
									"", "", "2"));

						}
						else if (!errorCode.equals("mandatory")) {
							errors.add("valuerAssignFactor" + j, new ActionMessage("error.number." + errorCode));
						}
					}

				}
			}// end for

		}

		return errors;

	}

}
