package com.integrosys.cms.ui.creditriskparam.unittrust;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.feed.FeedConstants;

/**
 * CreditRiskParamUnitTrustFormValidator Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class CreditRiskParamUnitTrustFormValidator implements java.io.Serializable {

	public static ActionErrors validateInput(CreditRiskParamUnitTrustForm form, Locale locale) {

		String event = form.getEvent();
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		if (CreditRiskParamUnitTrustAction.EVENT_SUBMIT.equals(event)
				|| CreditRiskParamUnitTrustAction.EVENT_PAGINATE.equals(event)) {
			// Check that the updated Moa & MaxCap fall in range.
			String[] updatedMoaArr = form.getUpdatedMoa();
			if (updatedMoaArr != null) {
				for (int i = 0; i < updatedMoaArr.length; i++) {
					if (!"".equals(updatedMoaArr[i])
							&& !Validator.checkPattern(updatedMoaArr[i], FeedConstants.PRICE_REGEX)) {
						errors.add("updatedMoa." + i, new ActionMessage(FeedConstants.ERROR_INVALID));
					}
					else if (!(errorCode = Validator.checkNumber(updatedMoaArr[i], true, 0, 100))
							.equals(Validator.ERROR_NONE)) {
						errors.add("updatedMoa." + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
								errorCode), new Integer(0), new Integer(100)));
					}
				}
			}

			String[] updatedMaxCapArr = form.getUpdatedMaxCap();
			if (updatedMaxCapArr != null) {
				for (int i = 0; i < updatedMaxCapArr.length; i++) {
					if (!"".equals(updatedMaxCapArr[i])
							&& !Validator.checkPattern(updatedMaxCapArr[i], FeedConstants.PRICE_REGEX)) {
						errors.add("updatedMaxCap." + i, new ActionMessage(FeedConstants.ERROR_INVALID));
					}
					else if (!(errorCode = Validator.checkNumber(updatedMaxCapArr[i], true, 0, 100))
							.equals(Validator.ERROR_NONE)) {
						errors.add("updatedMaxCap." + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
								errorCode), new Integer(0), new Integer(100)));
					}
				}
			}
		}

		if (CreditRiskParamUnitTrustAction.EVENT_APPROVE.equals(event)
				|| CreditRiskParamUnitTrustAction.EVENT_REJECT.equals(event)
				|| CreditRiskParamUnitTrustAction.EVENT_CLOSE.equals(event)) {
			errorCode = RemarksValidatorUtil.checkRemarks(form.getRemarks(), false);
			if (!Validator.ERROR_NONE.equals(errorCode)) {
				errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
			}
		}

		return errors;
	}
}
