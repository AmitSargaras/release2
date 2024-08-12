/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/DocumentMapperHelper.java,v 1.8 2006/10/09 05:24:20 jzhai Exp $
 */
package com.integrosys.cms.ui.collateral.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.OBLimitCharge;
import com.integrosys.cms.app.collateral.bus.type.document.IDocumentCollateral;
import com.integrosys.cms.ui.collateral.CollateralMapper;

/**
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2006/10/09 05:24:20 $ Tag: $Name: $
 */
public class DocumentMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		DocumentForm aForm = (DocumentForm) cForm;
		IDocumentCollateral iObj = (IDocumentCollateral) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICollateralLimitMap[] limitMap = iObj.getCollateralLimits();
		try {
			iObj.setMaxAmount(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm.getMaxAmount()));
			iObj.setMinAmount(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm.getMinAmount()));
			iObj.setReferenceNo(aForm.getNumberLetter());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDocumentDate())) {
				Date stageDate = CollateralMapper.compareDate(locale, iObj.getDocumentDate(), aForm.getDocumentDate());
				iObj.setDocumentDate(stageDate);
			}
			else {
				iObj.setDocumentDate(null);
			}

			iObj.setCollateralLimits(limitMap);
			iObj.setDocumentAmount(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm
					.getAmountLetter()));

			iObj.setIssuer(aForm.getIssuer());

			populateChargeDetails(iObj);
		}
		catch (Exception ex) {
			MapperException me = new MapperException("failed to map document form to col ob class");
			me.initCause(ex);
			throw me;
		}

		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		DocumentForm aForm = (DocumentForm) cForm;
		IDocumentCollateral iObj = (IDocumentCollateral) obj;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			DefaultLogger.debug("DocLoUMapperHelper - mapOBToForm", " MaxAmount is " + iObj.getMaxAmount());
			aForm.setMaxAmount(iObj.getMaxAmount() != null ? CurrencyManager.convertToString(locale, iObj
					.getMaxAmount()) : "");
			aForm.setMinAmount(iObj.getMinAmount() != null ? CurrencyManager.convertToString(locale, iObj
					.getMinAmount()) : "");
			aForm.setAmountLetter(iObj.getDocumentAmount() != null ? CurrencyManager.convertToString(locale, iObj
					.getDocumentAmount()) : "");
			aForm.setNumberLetter(iObj.getReferenceNo());
			aForm.setIssuer(iObj.getIssuer());
		}
		catch (Exception ex) {
			MapperException me = new MapperException("failed to map collateral ob class to form");
			me.initCause(ex);
			throw me;
		}

		return aForm;
	}

	protected static void populateChargeDetails(IDocumentCollateral col) {
		Amount documentAmt = col.getDocumentAmount();
		Date documentDate = col.getDocumentDate();

		ILimitCharge charge = null;
		ILimitCharge limitCharges[] = col.getLimitCharges();
		if (limitCharges != null) {
			if (limitCharges.length > 0) {
				charge = limitCharges[0];
			}
			else {
				limitCharges = new OBLimitCharge[1];
			}
		}
		else {
			limitCharges = new OBLimitCharge[1];
		}

		if (charge == null) {
			charge = new OBLimitCharge();
		}

		charge.setChargeAmount(documentAmt);
		charge.setLegalChargeDate(documentDate);

		limitCharges[0] = charge;
		col.setLimitCharges(limitCharges);
	}
}
