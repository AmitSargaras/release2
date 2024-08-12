/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/docgencredit/DocGenCreditMapperHelper.java,v 1.10 2003/10/14 11:16:57 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.document.docgencredit;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.creditagreement.ICreditAgreement;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2003/10/14 11:16:57 $ Tag: $Name: $
 */
public class DocGenCreditMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		DocGenCreditForm aForm = (DocGenCreditForm) cForm;
		ICreditAgreement iObj = (ICreditAgreement) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		iObj.setDocumentDesc(aForm.getDescription());

		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		DocGenCreditForm aForm = (DocGenCreditForm) cForm;
		ICreditAgreement iObj = (ICreditAgreement) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		aForm.setDocumentDate(DateUtil.formatDate(locale, iObj.getDocumentDate()));
		aForm.setDescription(iObj.getDocumentDesc());

		return cForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((ICreditAgreement) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}
}