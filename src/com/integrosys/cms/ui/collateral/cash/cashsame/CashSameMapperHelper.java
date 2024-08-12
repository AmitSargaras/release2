/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/cash/cashsame/CashSameMapperHelper.java,v 1.8 2003/08/21 13:50:23 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.cash.cashsame;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.samecurrency.ISameCurrency;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2003/08/21 13:50:23 $ Tag: $Name: $
 */
public class CashSameMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {

		return obj;

	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		return cForm;
	}

	public static Object getObject(HashMap inputs) {
		DefaultLogger.debug("com.integrosys.cms.ui.collateral.cash.CashSameMapperHelper",
				"============= get object!!!!!");

		return ((ISameCurrency) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
