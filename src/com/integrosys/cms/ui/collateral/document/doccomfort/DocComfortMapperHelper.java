/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/doclou/DocLoUMapperHelper.java,v 1.10 2005/09/30 10:03:33 vishal Exp $
 */
package com.integrosys.cms.ui.collateral.document.doccomfort;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.comfort.IComfort;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author $Author: vishal $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2005/09/30 10:03:33 $ Tag: $Name: $
 */
public class DocComfortMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		DocComfortForm aForm = (DocComfortForm) cForm;
		IComfort iObj = (IComfort) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		iObj.setDocumentDesc(aForm.getDescription());

		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		DocComfortForm aForm = (DocComfortForm) cForm;
		IComfort iObj = (IComfort) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		aForm.setDocumentDate(DateUtil.formatDate(locale, iObj.getDocumentDate()));
		aForm.setDescription(iObj.getDocumentDesc());

		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((IComfort) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}
}