/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PropertyTypeList
 *
 * Created on 4:50:27 PM
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

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 9, 2007 Time: 4:50:27 PM
 */
public class PropertyTypeList {
	private final HashMap propertyTypeMap;

	private final ArrayList propertyTypeLabel;

	private final ArrayList propertyTypeValue;

	private static final PropertyTypeList instance = new PropertyTypeList();

	private PropertyTypeList() {
		// init data

		propertyTypeLabel = new ArrayList();
		propertyTypeValue = new ArrayList();

		propertyTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_PROP_TYPE);
		propertyTypeValue.addAll(propertyTypeMap.keySet());
		propertyTypeLabel.addAll(propertyTypeMap.values());
	}

	public static PropertyTypeList getInstance() {
		return instance;
	}

	public final ArrayList getPropertyTypeLabels() {
		return (ArrayList) propertyTypeLabel.clone();
	}

	public final ArrayList getPropertyTypeValues() {
		return (ArrayList) propertyTypeValue.clone();
	}

	public final HashMap getPropertyTypeMap() {
		return (HashMap) propertyTypeMap.clone();
	}

	public final String getPropertyTypeLabel(String key) {
		if (propertyTypeMap != null) {
			return (String) propertyTypeMap.get(key);
		}

		return null;
	}

}
