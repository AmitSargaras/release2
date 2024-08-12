/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.isdacsa;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: Helper for ISDA CSA Deal Description: Help the
 * other to pull or get data from system
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class ISDACSADealHelper {

	public static final String TIME_FREQUENCY_CODE = ICMSUIConstant.TIME_FREQ;

	static final public HashMap buildTimeFrequencyMap() {
		return buildCommonCodeMap(ICMSUIConstant.TIME_FREQ);
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
