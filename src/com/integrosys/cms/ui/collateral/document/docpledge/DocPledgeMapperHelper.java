/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.collateral.document.docpledge;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.pledge.IPledge;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralMapper;

/**
 * @author $Author: jerlin $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/03/16 $ Tag: $Name: $
 */

public class DocPledgeMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		DocPledgeForm aForm = (DocPledgeForm) cForm;
		IPledge iObj = (IPledge) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAwardedDate())) {
			Date stageDate = CollateralMapper.compareDate(locale, iObj.getAwardedDate(), aForm.getAwardedDate());
			iObj.setAwardedDate(stageDate);
		}
		else {
			iObj.setAwardedDate(null);
		}

		iObj.setProjectName(aForm.getProjectName());
		if (aForm.getIsLetterInstruct().equals("Y")) {
			iObj.setIsLetterInstruct(true);
		}
		else {
			iObj.setIsLetterInstruct(false);
		}

		if (aForm.getIsLetterUndertake().equals("Y")) {
			iObj.setIsLetterUndertake(true);
		}
		else {
			iObj.setIsLetterUndertake(false);
		}

		iObj.setBlanketAssignment(aForm.getBlanketAssignment());
		iObj.setDocumentDesc(aForm.getDescription());

		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		DocPledgeForm aForm = (DocPledgeForm) cForm;
		IPledge iObj = (IPledge) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		aForm.setDocumentDate(DateUtil.formatDate(locale, iObj.getDocumentDate()));
		aForm.setAwardedDate(DateUtil.formatDate(locale, iObj.getAwardedDate()));
		aForm.setProjectName(iObj.getProjectName());

		if (iObj.getIsLetterInstruct() == true) {
			aForm.setIsLetterInstruct("Y");
		}
		else {
			aForm.setIsLetterInstruct("N");
		}

		if (iObj.getIsLetterUndertake() == true) {
			aForm.setIsLetterUndertake("Y");
		}
		else {
			aForm.setIsLetterUndertake("N");
		}

		aForm.setBlanketAssignment(iObj.getBlanketAssignment());
		aForm.setDescription(iObj.getDocumentDesc());

		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((IPledge) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}
}