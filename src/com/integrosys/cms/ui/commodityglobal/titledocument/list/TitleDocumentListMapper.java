/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/titledocument/list/TitleDocumentListMapper.java,v 1.2 2004/06/04 05:11:52 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.titledocument.list;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.commodityglobal.CommodityGlobalConstants;
import com.integrosys.cms.ui.commodityglobal.titledocument.TitleDocumentAction;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:11:52 $ Tag: $Name: $
 */

public class TitleDocumentListMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		TitleDocumentListForm aForm = (TitleDocumentListForm) cForm;

		if (aForm.getEvent().equals(TitleDocumentAction.EVENT_DELETE)) {
			HashMap titledocObj = (HashMap) inputs.get("titleDocumentObj");

			String[] chkDelete = null;
			ArrayList titledoc = new ArrayList();

			if (aForm.getType().equals(CommodityGlobalConstants.NEGOTIABLE_DOCUMENT)) {
				chkDelete = aForm.getChkDeletesNeg();
				titledoc = (ArrayList) titledocObj.get("stageTitleDocNeg");
			}
			else {
				chkDelete = aForm.getChkDeletesNonNeg();
				titledoc = (ArrayList) titledocObj.get("stageTitleDocNonNeg");
			}

			if (chkDelete != null) {
				if (chkDelete.length <= titledoc.size()) {
					int numDelete = 0;
					for (int i = 0; i < chkDelete.length; i++) {
						if (Integer.parseInt(chkDelete[i]) < titledoc.size()) {
							numDelete++;
						}
					}
					if (numDelete != 0) {
						ArrayList newList = new ArrayList();
						int i = 0, j = 0;
						while (i < titledoc.size()) {
							if ((j < chkDelete.length) && (Integer.parseInt(chkDelete[j]) == i)) {
								j++;
							}
							else {
								newList.add(titledoc.get(i));
							}
							i++;
						}
						if (aForm.getType().equals(CommodityGlobalConstants.NEGOTIABLE_DOCUMENT)) {
							titledocObj.put("stageTitleDocNeg", newList);
						}
						else {
							titledocObj.put("stageTitleDocNonNeg", newList);
						}
					}
				}
			}
			return titledocObj;
		}

		return inputs;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		TitleDocumentListForm aForm = (TitleDocumentListForm) cForm;

		aForm.setChkDeletesNeg(new String[0]);
		aForm.setChkDeletesNonNeg(new String[0]);

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "titleDocumentObj", "java.util.HashMap", SERVICE_SCOPE }, });
	}

}
