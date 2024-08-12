/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/marketablesec/PrepareMarketableSecCommandHelper.java,v 1.3 2003/09/15 09:51:31 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.marketablesec;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/15 09:51:31 $
 * Tag: $Name:  $
 */
/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareMarketableSecCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "natureOfChargeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "natureOfChargeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "currencyCode", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE } 
				});
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {
/*		NatureOfChargeList nature = NatureOfChargeList.getInstance();
		Collection list = nature.getNatureOfChargeListID();
		result.put("natureOfChargeID", list);
		list = nature.getNatureOfChargeListValue();
		result.put("natureOfChargeValue", list);
		Collection list1 = CurrencyList.getInstance().getCountryValues();
		result.put("currencyCode", list1);
*/
		return;
	}

}
