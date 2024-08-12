/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityuom/list/CommodityUOMListValidator.java,v 1.3 2006/11/10 10:44:32 nkumar Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityuom.list;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Description
 * 
 * @author $Author: nkumar $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/11/10 10:44:32 $ Tag: $Name: $
 */

public class CommodityUOMListValidator {
	public static ActionErrors validateInput(CommodityUOMListForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (aForm.getEvent().equals(CommodityUOMListAction.EVENT_PREPARE)
				|| aForm.getEvent().equals(CommodityUOMListAction.EVENT_READ)) {
			if (!(errorCode = Validator.checkString(aForm.getCategory(), true, 0, 250)).equals(Validator.ERROR_NONE)) {
				errors.add("category", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						250 + ""));
			}
			if (!(errorCode = Validator.checkString(aForm.getCommodityType(), true, 0, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("commodityType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 250 + ""));
			}
		}
		if (!(errorCode = Validator.checkString(aForm.getRemarks(), false, 0, 250)).equals(Validator.ERROR_NONE)) {
			errors.add("remarks",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 250 + ""));
		}
		return errors;
	}
}
