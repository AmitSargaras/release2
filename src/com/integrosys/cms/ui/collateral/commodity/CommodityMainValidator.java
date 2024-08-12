/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/CommodityMainValidator.java,v 1.7 2005/10/06 02:55:20 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/10/06 02:55:20 $ Tag: $Name: $
 */

public class CommodityMainValidator {
	public static ActionErrors validateInput(CommodityMainForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getPreConditions(), false, 1000, 50))
				.equals(Validator.ERROR_NONE)) {
			errors.add("preConditions", RemarksValidatorUtil.getErrorMessage(errorCode, 1000, 50));
		}

		if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getRemarks(), false)).equals(Validator.ERROR_NONE)) {
			errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
		}

		return errors;
	}
}
