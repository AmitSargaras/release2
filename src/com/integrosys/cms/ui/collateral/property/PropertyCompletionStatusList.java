/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PropertyCompletionStatusList
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
public class PropertyCompletionStatusList {
	private final HashMap propertyCompletionStatusMap;

	private final ArrayList propertyCompletionStatusLabel;

	private final ArrayList propertyCompletionStatusValue;

	private static final PropertyCompletionStatusList instance = new PropertyCompletionStatusList();

	private PropertyCompletionStatusList() {
		// init data

		propertyCompletionStatusLabel = new ArrayList();
		propertyCompletionStatusValue = new ArrayList();

		propertyCompletionStatusMap = CommonDataSingleton
				.getCodeCategoryValueLabelMap(CategoryCodeConstant.PROPERTY_COMPLETION_STATUS_LIST);
		propertyCompletionStatusValue.addAll(propertyCompletionStatusMap.keySet());
		propertyCompletionStatusLabel.addAll(propertyCompletionStatusMap.values());
	}

	public static PropertyCompletionStatusList getInstance() {
		return instance;
	}

	public final ArrayList getPropertyCompletionStatusLabels() {
		return (ArrayList) propertyCompletionStatusLabel.clone();
	}

	public final ArrayList getPropertyCompletionStatusValues() {
		return (ArrayList) propertyCompletionStatusValue.clone();
	}

	public final HashMap getPropertyCompletionStatusMap() {
		return (HashMap) propertyCompletionStatusMap.clone();
	}

	public final String getPropertyCompletionStatusLabel(String key) {
		if (propertyCompletionStatusMap != null) {
			return (String) propertyCompletionStatusMap.get(key);
		}

		return null;
	}

}
