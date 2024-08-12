/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/list/SubLimitListValidator.java,v 1.1 2005/10/14 06:31:46 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit.list;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-15 Tag :
 *        com.integrosys.cms.ui.collateral.commodity.sublimit.AddSubLimitValidator
 *        .java
 */
public class SubLimitListValidator {
	public static ActionErrors validateInput(SubLimitListForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		if (aForm.getLimitIDArray() != null) {
			List limitIDList = Arrays.asList(aForm.getLimitIDArray());
			String[] cashReqQtyArray = aForm.getCashReqQty();
			String[] cashReqQtyChk = aForm.getCashReqQtyChk();
			int index = -1;
			for (int i = 0; (cashReqQtyChk != null) && (i < cashReqQtyChk.length); i++) {
				index = limitIDList.indexOf(cashReqQtyChk[i]);
				if (!(errorCode = Validator.checkInteger(cashReqQtyArray[index], true, 0, 100))
						.equals(Validator.ERROR_NONE)) {
					// System.out.println("Error found for : cashReqQty"+
					// cashReqQtyChk[i]);
					errors.add("cashReqQty" + cashReqQtyChk[i], new ActionMessage(ErrorKeyMapper.map(
							ErrorKeyMapper.NUMBER, errorCode), "0", "100"));
				}
			}
		}
		return errors;
	}
}
