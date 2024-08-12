/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/property/PropertyValidationHelper.java,v 1.15 2006/03/17 07:25:43 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.property;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.collateral.CollateralValidator;
import com.integrosys.cms.ui.collateral.ManualValuationValidator;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/03/17 07:25:43 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class PropertyValidationHelper extends CollateralValidator {

	private static String LOGOBJ = PropertyValidationHelper.class.getName();

	public static ActionErrors validateInput(PropertyForm aForm, Locale locale, ActionErrors errors) {
        DefaultLogger.debug(LOGOBJ, ">>>>>>>>>>>>> in PropertyValidationHelper");
        return ManualValuationValidator.validateInput(aForm, locale, errors);
	}
}
