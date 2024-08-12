package com.integrosys.cms.ui.collateral.document.docdoa;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.deedassignment.IDeedAssignment;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.base.businfra.currency.CurrencyManager;

public class DocDoAMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		DocDoAForm aForm = (DocDoAForm) cForm;
		IDeedAssignment iObj = (IDeedAssignment) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		try {
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAwardedDate())) {
				Date stageDate = CollateralMapper.compareDate(locale, iObj.getAwardedDate(), aForm.getAwardedDate());
				iObj.setAwardedDate(stageDate);
			}
			else {
				iObj.setAwardedDate(null);
			}

			iObj.setDeedAssignmtTypeCode(aForm.getDeedAssignmtTypeCode());
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
			iObj.setContractNumber(aForm.getContractNumber());
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getContractAmt())) {
				iObj.setContractAmt(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm.getContractAmt()));
			} else iObj.setContractAmt(null);
			iObj.setContractName(aForm.getContractName());
			iObj.setContractDate(DateUtil.convertDate(locale, aForm.getContractDate()));
		}
		catch (Exception e) {
			DefaultLogger.debug("DocDoAMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		DocDoAForm aForm = (DocDoAForm) cForm;
		IDeedAssignment iObj = (IDeedAssignment) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		try {
			aForm.setDocumentDate(DateUtil.formatDate(locale, iObj.getDocumentDate()));
			aForm.setAwardedDate(DateUtil.formatDate(locale, iObj.getAwardedDate()));
			aForm.setDeedAssignmtTypeCode(iObj.getDeedAssignmtTypeCode());
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
			
			aForm.setContractNumber(iObj.getContractNumber());
			if (iObj.getContractAmt()!=null) aForm.setContractAmt(CurrencyManager.convertToString(locale, iObj.getContractAmt()));
			aForm.setContractName(iObj.getContractName());
			aForm.setContractDate(DateUtil.formatDate(locale, iObj.getContractDate()));
		}
		catch (Exception e) {
			DefaultLogger.debug("DocDoAMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}
		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((IDeedAssignment) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}
}