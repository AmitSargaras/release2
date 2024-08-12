/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/docdervisd/DocDervISDMapperHelper.java,v 1.11 2003/10/14 11:16:57 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.document.docdervisd;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.fxisda.IFXISDA;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralMapper;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2003/10/14 11:16:57 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class DocDervISDMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		DocDervISDForm aForm = (DocDervISDForm) cForm;
		IFXISDA iObj = (IFXISDA) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug("DocDervISDMapperHelper - mapFormToOB", "Locale is: " + locale);
		Date stageDate;
		try {
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDocumentDate())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getISDADate(), aForm.getDocumentDate());
				iObj.setISDADate(stageDate);
			}
			else {
				iObj.setISDADate(null);
			}

			iObj.setISDAProductDesc(aForm.getDescProducISDA());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateIFREMAAgmt())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getIFEMADate(), aForm.getDateIFREMAAgmt());
				iObj.setIFEMADate(stageDate);
			}
			else {
				iObj.setIFEMADate(null);
			}

			iObj.setIFEMAProductDesc(aForm.getDescProdIFEMA());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateICOMDocument())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getICOMDate(), aForm.getDateICOMDocument());
				iObj.setICOMDate(stageDate);
			}
			else {
				iObj.setICOMDate(null);
			}

			iObj.setICOMProductDesc(aForm.getDescProdICOM());
			iObj.setDocumentDesc(aForm.getDescription());

		}
		catch (Exception e) {
			DefaultLogger.debug("DocDervISDMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}
		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		DocDervISDForm aForm = (DocDervISDForm) cForm;
		IFXISDA iObj = (IFXISDA) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug("DocDervISDMapperHelper - mapOBToForm", "Locale is: " + locale);
		try {
			aForm.setDocumentDate(DateUtil.formatDate(locale, iObj.getISDADate()));
			aForm.setDescProducISDA(iObj.getISDAProductDesc());
			aForm.setDateIFREMAAgmt(DateUtil.formatDate(locale, iObj.getIFEMADate()));
			aForm.setDescProdIFEMA(iObj.getIFEMAProductDesc());
			aForm.setDateICOMDocument(DateUtil.formatDate(locale, iObj.getICOMDate()));
			aForm.setDescProdICOM(iObj.getICOMProductDesc());
			aForm.setDescription(iObj.getDocumentDesc());
		}
		catch (Exception e) {
			DefaultLogger.debug("DocDervISDMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}
		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((IFXISDA) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
