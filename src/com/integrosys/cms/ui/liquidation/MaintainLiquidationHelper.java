/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/liquidation/MaintainLiquidationHelper.java,v 1 2007/02/09 Lini Exp $
 */
package com.integrosys.cms.ui.liquidation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: Helper for Liquidation Description: Help the
 * other to pull or get data from system
 * 
 * @author $Author: lini$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/01/09$ Tag: $Name$
 */

public class MaintainLiquidationHelper {

	public static final String TIME_FREQUENCY_CODE = ICMSUIConstant.TIME_FREQ;

	static final public HashMap buildTimeFrequencyMap() {
		return buildCommonCodeMap(ICMSUIConstant.TIME_FREQ);
	}

	static final public HashMap buildCurrencyMap() {
		CurrencyList currList = CurrencyList.getInstance();
		List currencyValuesList = new ArrayList(currList.getCountryValues());
		List currencyLabelsList = new ArrayList(currList.getCurrencyLabels());

		String[] currencyLabels = new String[currencyLabelsList.size()];
		currencyLabels = (String[]) currencyLabelsList.toArray(currencyLabels);
		String[] currencyValues = new String[currencyValuesList.size()];
		currencyValues = (String[]) currencyValuesList.toArray(currencyValues);

		HashMap map = new HashMap();
		map.put("Labels", currencyLabels);
		for (int i = 0; i < currencyValues.length; i++) {
			map.put(currencyValues[i], currencyLabels[i]);
		}
		map.put("Labels", currList.getCurrencyLabels());
		map.put("Values", currList.getCountryValues());
		return map;
	}

	static final private HashMap buildCommonCodeMap(String categoryCode) {
		Collection codeCategoryLabelsCol = CommonDataSingleton.getCodeCategoryLabels(categoryCode);
		Collection codeCategoryValeusCol = CommonDataSingleton.getCodeCategoryValues(categoryCode);
		String[] codeCategoryLabels = new String[codeCategoryLabelsCol.size()];
		codeCategoryLabels = (String[]) codeCategoryLabelsCol.toArray(codeCategoryLabels);
		String[] codeCategoryValues = new String[codeCategoryValeusCol.size()];
		codeCategoryValues = (String[]) codeCategoryValeusCol.toArray(codeCategoryValues);

		HashMap map = new HashMap();
		for (int i = 0; i < codeCategoryValues.length; i++) {
			map.put(codeCategoryValues[i], codeCategoryLabels[i]);
		}
		return map;
	}
}
