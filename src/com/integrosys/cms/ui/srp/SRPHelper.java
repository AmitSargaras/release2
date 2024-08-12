/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/srp/SRPHelper.java,v 1.3 2005/09/12 02:53:44 hshii Exp $
 */

package com.integrosys.cms.ui.srp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * SRPHelper
 * @author $Author: hshii $
 * @version $
 * @since Aug 16, 2003 11:29:17 AM$
 */
public class SRPHelper {

	public static final String TIME_FREQUENCY_CODE = ICMSUIConstant.TIME_FREQ;

	static final public HashMap buildTimeFrequencyMap() {
		return buildCommonCodeMap(ICMSUIConstant.TIME_FREQ);
	}

	static final public HashMap buildCountryeMap() {
		// set Coutnry List
		CountryList countryList = CountryList.getInstance();
		List countryValuesList = new ArrayList(countryList.getCountryValues());
		List countryLabelsList = new ArrayList(countryList.getCountryLabels());

		String[] countryLabels = new String[countryLabelsList.size()];
		countryLabels = (String[]) countryLabelsList.toArray(countryLabels);
		String[] countryValues = new String[countryValuesList.size()];
		countryValues = (String[]) countryValuesList.toArray(countryValues);

		HashMap map = new HashMap();
		for (int i = 0; i < countryValues.length; i++) {
			map.put(countryValues[i], countryLabels[i]);
		}
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
