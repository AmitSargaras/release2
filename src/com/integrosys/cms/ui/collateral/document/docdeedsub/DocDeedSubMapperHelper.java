/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/docdervisd/DocDervISDMapperHelper.java,v 1.11 2003/10/14 11:16:57 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.document.docdeedsub;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.deedsub.IDeedSub;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2003/10/14 11:16:57 $ Tag: $Name: $
 */

public class DocDeedSubMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		DocDeedSubForm aForm = (DocDeedSubForm) cForm;
		IDeedSub iObj = (IDeedSub) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			iObj.setDocumentDesc(aForm.getDescription());
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}
		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		DocDeedSubForm aForm = (DocDeedSubForm) cForm;
		IDeedSub iObj = (IDeedSub) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			aForm.setDescription(iObj.getDocumentDesc());
			aForm.setDocumentDate(DateUtil.formatDate(locale, iObj.getDocumentDate()));
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}
		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((IDeedSub) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
