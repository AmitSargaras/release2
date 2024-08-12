/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/docdervnet/DocDervNetMapperHelper.java,v 1.9 2003/10/14 11:16:57 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.document.docdervnet;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.fxnetting.IFXNetting;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2003/10/14 11:16:57 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class DocDervNetMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		DocDervNetForm aForm = (DocDervNetForm) cForm;
		IFXNetting iObj = (IFXNetting) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		iObj.setDocumentDesc(aForm.getDescription());

		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		DocDervNetForm aForm = (DocDervNetForm) cForm;
		IFXNetting iObj = (IFXNetting) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		aForm.setDocumentDate(DateUtil.formatDate(locale, iObj.getDocumentDate()));
		aForm.setDescription(iObj.getDocumentDesc());

		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((IFXNetting) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
