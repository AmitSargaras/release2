/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/property/propindus/PropIndusMapperHelper.java,v 1.17 2005/08/15 09:05:27 lyng Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.property.propindusspec;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.industrialspecial.IIndustrialSpecial;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/08/15 09:05:27 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PropIndusSpecMapperHelper {
	private static final String DEBUG_CLASS = PropIndusSpecMapperHelper.class.getName();

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		IIndustrialSpecial iObj = (IIndustrialSpecial) obj;
		PropIndusSpecForm aForm = (PropIndusSpecForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug(DEBUG_CLASS, "Locale is: " + locale);

		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		IIndustrialSpecial iObj = (IIndustrialSpecial) obj;
		PropIndusSpecForm aForm = (PropIndusSpecForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug(DEBUG_CLASS, "Locale is: " + locale);

		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((IIndustrialSpecial) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
