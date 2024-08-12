/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * RealEstateUsageList
 *
 * Created on 11:11:14 AM
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
 * Created by IntelliJ IDEA. User: Eric Date: Mar 12, 2007 Time: 11:11:14 AM
 */
public class RealEstateUsageList {
	private final HashMap realEstateUsageListMap;

	private final ArrayList realEstateUsageListLabel;

	private final ArrayList realEstateUsageListValue;

	private static final RealEstateUsageList instance = new RealEstateUsageList();

	private RealEstateUsageList() {
		// init data

		realEstateUsageListLabel = new ArrayList();
		realEstateUsageListValue = new ArrayList();

		realEstateUsageListMap = CommonDataSingleton
				.getCodeCategoryValueLabelMap(CategoryCodeConstant.REAL_ESTATE_USAGE);
		realEstateUsageListValue.addAll(realEstateUsageListMap.keySet());
		realEstateUsageListLabel.addAll(realEstateUsageListMap.values());
	}

	public static RealEstateUsageList getInstance() {
		return instance;
	}

	public final ArrayList getRealEstateUsageListLabels() {
		return (ArrayList) realEstateUsageListLabel.clone();
	}

	public final ArrayList getRealEstateUsageListValues() {
		return (ArrayList) realEstateUsageListValue.clone();
	}

	public final HashMap getRealEstateUsageListMap() {
		return (HashMap) realEstateUsageListMap.clone();
	}

	public final String getRealEstateUsageListLabel(String key) {
		if (realEstateUsageListMap != null) {
			return (String) realEstateUsageListMap.get(key);
		}

		return null;
	}

}
