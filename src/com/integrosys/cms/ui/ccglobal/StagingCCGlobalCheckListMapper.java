/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.ccglobal;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.chktemplate.trx.OBDocumentItemTrxValue;
import com.integrosys.cms.ui.docglobal.StagingDocumentationGlobalCheckListMapper;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/05 12:11:06 $ Tag: $Name: $
 */

public class StagingCCGlobalCheckListMapper extends StagingDocumentationGlobalCheckListMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		CCGlobalForm aForm = (CCGlobalForm) cForm;
		IDocumentItem temp = (IDocumentItem) super.mapFormToOB(cForm, map);

		if (aForm.getIsForBorrower().equals("isBorrower")) {
			temp.setIsForBorrower(true);
		}
		else {
			temp.setIsForBorrower(false);
		}
		if (aForm.getIsForPledgor().equals("isPledgor")) {
			temp.setIsForPledgor(true);
		}
		else {
			temp.setIsForPledgor(false);
		}

		return temp;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		CCGlobalForm aForm = (CCGlobalForm) super.mapOBToForm(cForm, obj, map);
		String stringIsForBorrower = "";
		String stringIsForPledgor = "";
		if (obj != null) {
			IDocumentItem tempOb = (IDocumentItem) obj;
			if (tempOb.getIsForBorrower()) {
				stringIsForBorrower = "isBorrower";
			}
			else {
				stringIsForBorrower = "";
			}
			aForm.setIsForBorrower(stringIsForBorrower);
			if (tempOb.getIsForPledgor()) {
				stringIsForPledgor = "isPledgor";
			}
			else {
				stringIsForPledgor = "";
			}
			aForm.setIsForPledgor(stringIsForPledgor);

		}
		return aForm;

	}
}
