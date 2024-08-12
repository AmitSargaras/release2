package com.integrosys.cms.ui.collateral.document.docloi;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.letterindemnity.ILetterIndemnity;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

public class DocLoIMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		DocLoIForm aForm = (DocLoIForm) cForm;
		ILetterIndemnity iObj = (ILetterIndemnity) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		try {
			iObj.setDocumentDesc(aForm.getDescription());
		}
		catch (Exception e) {
			DefaultLogger.debug("DocLoIMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		DocLoIForm aForm = (DocLoIForm) cForm;
		ILetterIndemnity iObj = (ILetterIndemnity) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		try {
			aForm.setDocumentDate(DateUtil.formatDate(locale, iObj.getDocumentDate()));
			aForm.setDescription(iObj.getDocumentDesc());
		}
		catch (Exception e) {
			DefaultLogger.debug("DocLoIMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}
		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((ILetterIndemnity) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}
}