/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/cash/cashhkdusd/CashHKDUSDMapperHelper.java,v 1.8 2006/04/10 07:08:33 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.cash.cashhkdusd;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.hkdusd.IHKDUSD;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2006/04/10 07:08:33 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class CashHKDUSDMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		IHKDUSD iCol = (IHKDUSD) obj;
		CashHKDUSDForm aForm = (CashHKDUSDForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		try {
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getMinimalFSV())) {
				iCol.setMinimalFSV(null);
			}
			else {
				iCol.setMinimalFSVCcyCode(iCol.getCurrencyCode());
				iCol.setMinimalFSV(CurrencyManager.convertToAmount(locale, iCol.getMinimalFSVCcyCode(), aForm
						.getMinimalFSV()));
			}
		}
		catch (Exception e) {
			DefaultLogger.error("com.integrosys.cms.ui.collateral.cash.cashhkdusd.CashHKDUSDMapperHelper", "error is :"
					+ e.toString());
			throw new MapperException(e.getMessage());
		}

		return iCol;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		CashHKDUSDForm aForm = (CashHKDUSDForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IHKDUSD iObj = (IHKDUSD) obj;
		try {
			if ((iObj.getMinimalFSV() != null) && (iObj.getMinimalFSV().getCurrencyCode() != null)) {
				if (iObj.getMinimalFSV().getAmount() >= 0) {
					aForm.setMinimalFSV(CurrencyManager.convertToString(locale, iObj.getMinimalFSV()));
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.error("com.integrosys.cms.ui.collateral.cash.cashhkdusd.CashHKDUSDMapperHelper", "error is :"
					+ e.toString());
			throw new MapperException(e.getMessage());
		}

		return aForm;
	}

	public static Object getObject(HashMap inputs) {

		return ((IHKDUSD) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
