/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PropertyUsageList
 *
 * Created on 11:20:22 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.collateral.property;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 12, 2007 Time: 11:20:22 AM
 */
public class PropertyUsageList {
	private final HashMap propertyUsageMap;

	private final ArrayList propertyUsageLabel;

	private final ArrayList propertyUsageValue;

	private static final PropertyUsageList instance = new PropertyUsageList();

	private PropertyUsageList() {
		// init data

		propertyUsageLabel = new ArrayList();
		propertyUsageValue = new ArrayList();

		propertyUsageMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.PROPERTY_USAGE_LIST);
		propertyUsageValue.addAll(propertyUsageMap.keySet());
		propertyUsageLabel.addAll(propertyUsageMap.values());
	}

	public static PropertyUsageList getInstance() {
		return instance;
	}

	public final ArrayList getPropertyUsageLabels() {
		return (ArrayList) propertyUsageLabel.clone();
	}

	public final ArrayList getPropertyUsageValues() {
		return (ArrayList) propertyUsageValue.clone();
	}

	public final HashMap getPropertyUsageMap() {
		return (HashMap) propertyUsageMap.clone();
	}

	public final String getPropertyUsageLabel(String key) {
		if (propertyUsageMap != null) {
			return (String) propertyUsageMap.get(key);
		}

		return null;
	}

}
