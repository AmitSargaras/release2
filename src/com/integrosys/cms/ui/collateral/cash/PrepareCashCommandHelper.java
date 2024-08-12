/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/cash/PrepareCashCommandHelper.java,v 1.8 2003/09/15 09:51:31 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.cash;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2003/09/15 09:51:31 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareCashCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "currencyCode", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "natureOfChargeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "natureOfChargeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE } });
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {
		/*Collection list1 = CurrencyList.getInstance().getCountryValues();
		result.put("currencyCode", list1);
		Collection list = NatureOfChargeList.getInstance().getNatureOfChargeListID();
		result.put("natureOfChargeID", list);
		list = NatureOfChargeList.getInstance().getNatureOfChargeListValue();
		result.put("natureOfChargeValue", list);
		*/
		return;
	}

}
