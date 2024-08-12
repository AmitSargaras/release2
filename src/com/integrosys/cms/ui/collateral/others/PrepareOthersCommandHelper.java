/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/others/PrepareOthersCommandHelper.java
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.others;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.FrequencyList;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/12 02:54:11 $
 * Tag: $Name:  $
 */
/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareOthersCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "currencyCode", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secRiskyID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secRiskyValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "freqID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "freqValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {
		FrequencyList freqList = FrequencyList.getInstance();
		result.put("freqValue", freqList.getFrequencyLabel());
		result.put("freqID", freqList.getFrequencyProperty());

		Collection list;
		Collection list1 = CurrencyList.getInstance().getCountryValues();
		result.put("currencyCode", list1);
		SecEnvRiskyList secRiskyList = SecEnvRiskyList.getInstance();
		result.put("secRiskyID", secRiskyList.getSecEnvRiskyID());
		result.put("secRiskyValue", secRiskyList.getSecEnvRiskyValue());

		return;
	}
}
