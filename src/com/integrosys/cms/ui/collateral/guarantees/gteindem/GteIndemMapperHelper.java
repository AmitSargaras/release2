/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/guarantees/gteindem/GteIndemMapperHelper.java,v 1.4 2006/04/10 07:09:58 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.guarantees.gteindem;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.interbrindem.IInterBranchIndemnity;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/04/10 07:09:58 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class GteIndemMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		IInterBranchIndemnity iCol = (IInterBranchIndemnity) obj;
		GteIndemForm aForm = (GteIndemForm) cForm;
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
			DefaultLogger.error("com.integrosys.cms.ui.collateral.guarantees.gteindem.GteIndemMapperHelper",
					"error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return iCol;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		GteIndemForm aForm = (GteIndemForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IInterBranchIndemnity iObj = (IInterBranchIndemnity) obj;
		try {
			if ((iObj.getMinimalFSV() != null) && (iObj.getMinimalFSV().getCurrencyCode() != null)) {
				if (iObj.getMinimalFSV().getAmount() >= 0) {
					aForm.setMinimalFSV(CurrencyManager.convertToString(locale, iObj.getMinimalFSV()));
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.error("com.integrosys.cms.ui.collateral.guarantees.gteindem.GteIndemMapperHelper",
					"error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((IInterBranchIndemnity) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
