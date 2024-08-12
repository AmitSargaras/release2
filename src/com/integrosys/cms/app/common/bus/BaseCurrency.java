/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/common/bus/BaseCurrency.java,v 1.1 2003/08/05 11:40:26 hltan Exp $
 */
package com.integrosys.cms.app.common.bus;

//java
import java.io.Serializable;

import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * This class represents the base currency
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/05 11:40:26 $ Tag: $Name: $
 */
public class BaseCurrency implements Serializable {

	public static CurrencyCode getCurrencyCode() {
		String baseCurrency = PropertyManager.getValue("baseCountryCode");
		if ((baseCurrency != null) && (baseCurrency.trim().length() > 0)) {
			return new CurrencyCode(baseCurrency);
		}
		return new CurrencyCode("INR");
	}
}