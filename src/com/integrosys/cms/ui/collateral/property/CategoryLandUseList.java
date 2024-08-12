/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CategoryLandUseList
 *
 * Created on 12:35:45 PM
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
 * Created by IntelliJ IDEA. User: Eric Date: Mar 9, 2007 Time: 12:35:45 PM
 */
public class CategoryLandUseList {
	private final HashMap catLandUseMap;

	private final ArrayList catLandUseLabel;

	private final ArrayList catLandUseValue;

	private static final CategoryLandUseList instance = new CategoryLandUseList();

	private CategoryLandUseList() {
		// init data

		catLandUseLabel = new ArrayList();
		catLandUseValue = new ArrayList();

		catLandUseMap = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_OF_LAND_USE);
		catLandUseValue.addAll(catLandUseMap.keySet());
		catLandUseLabel.addAll(catLandUseMap.values());
	}

	public static CategoryLandUseList getInstance() {
		return instance;
	}

	public final ArrayList getCategoryLandUseLabels() {
		return (ArrayList) catLandUseLabel.clone();
	}

	public final ArrayList getCategoryLandUseValues() {
		return (ArrayList) catLandUseValue.clone();
	}

	public final HashMap getCategoryLandUseMap() {
		return (HashMap) catLandUseMap.clone();
	}

	public final String getCategoryLandUseLabel(String key) {
		if (catLandUseMap != null) {
			return (String) catLandUseMap.get(key);
		}

		return null;
	}

}
