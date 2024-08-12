/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/titledocument/item/TitleDocumentItemMapper.java,v 1.2 2004/06/04 05:11:52 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.titledocument.item;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.commodity.main.bus.titledocument.OBTitleDocument;
import com.integrosys.cms.ui.commodityglobal.CommodityGlobalConstants;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:11:52 $ Tag: $Name: $
 */

public class TitleDocumentItemMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		TitleDocumentItemForm aForm = (TitleDocumentItemForm) cForm;

		int index = Integer.parseInt((String) inputs.get("indexID"));
		OBTitleDocument obToChange = null;

		if (index == -1) {
			obToChange = new OBTitleDocument();
			if (aForm.getType().equals(CommodityGlobalConstants.NEGOTIABLE_DOCUMENT)) {
				obToChange.setType(ITitleDocument.NEGOTIABLE);
			}
			else {
				obToChange.setType(ITitleDocument.NON_NEGOTIABLE);
			}
		}
		else {
			HashMap titleDocObj = (HashMap) inputs.get("titleDocumentObj");
			ArrayList titledocList = new ArrayList();
			if (aForm.getType().equals(CommodityGlobalConstants.NEGOTIABLE_DOCUMENT)) {
				titledocList = (ArrayList) titleDocObj.get("stageTitleDocNeg");
			}
			else {
				titledocList = (ArrayList) titleDocObj.get("stageTitleDocNonNeg");
			}
			DefaultLogger.debug(this, "index is: " + index);
			try {
				obToChange = (OBTitleDocument) AccessorUtil.deepClone((OBTitleDocument) titledocList.get(index));
			}
			catch (Exception e) {
				DefaultLogger.debug(this, e.getMessage());
			}
		}
		obToChange.setName(aForm.getDocumentDesc());
		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		TitleDocumentItemForm aForm = (TitleDocumentItemForm) cForm;
		ITitleDocument titleDocObj = (ITitleDocument) obj;

		aForm.setDocumentDesc(titleDocObj.getName());

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "titleDocumentObj", "java.util.HashMap", SERVICE_SCOPE }, });
	}
}
