/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/approvedcommodity/ApprovedCommValidator.java,v 1.2 2004/06/04 05:22:08 hltan Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.approvedcommodity;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:22:08 $ Tag: $Name: $
 */

public class ApprovedCommValidator {
	public static ActionErrors validateInput(ApprovedCommForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (!(errorCode = Validator.checkString(aForm.getSecurityID(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("securityID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					30 + ""));
		}

		if (!(errorCode = Validator.checkString(aForm.getProductType(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("productType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					30 + ""));
		}

		if (!(errorCode = Validator.checkString(aForm.getProductSubType(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("productSubType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					30 + ""));
		}

		return errors;
	}
}
