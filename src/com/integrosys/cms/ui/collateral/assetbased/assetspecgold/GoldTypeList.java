/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.collateral.assetbased.assetspecgold;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * GoldTypeList
 * 
 * Describe this class. Purpose: Parameter List for Gold Type Description: Class
 * for Parameter List of Gold Type
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class GoldTypeList extends BaseList { // ------

	private static HashMap goldTypeMap;

	private static Collection goldTypeValue;

	private static Collection goldTypeLabel;

	private static Date createdDate;

	private static GoldTypeList thisInstance;

	public synchronized static GoldTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new GoldTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new GoldTypeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private GoldTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		goldTypeValue = new ArrayList();
		goldTypeLabel = new ArrayList();
		goldTypeMap = new HashMap();
		HashMap tempGoldTypeMap = new HashMap();
		goldTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.CATEGORY_GOLD_TYPE);
		DefaultLogger.debug(this, "<<<<< ----- Gold Type map size: " + goldTypeMap.size());
		Collection keyvalue = goldTypeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempGoldTypeMap.put(goldTypeMap.get(key), key);
			// goldTypeValue.add(key);
			goldTypeLabel.add(goldTypeMap.get(key));
		}
		String[] tempGoldTypeLabel = (String[]) goldTypeLabel.toArray(new String[0]);
		Arrays.sort(tempGoldTypeLabel);
		goldTypeLabel = Arrays.asList(tempGoldTypeLabel);
		for (int i = 0; i < tempGoldTypeLabel.length; i++) {
			goldTypeValue.add(tempGoldTypeMap.get(tempGoldTypeLabel[i]));
		}

	}

	public Collection getGoldTypeValue() {
		return goldTypeValue;
	}

	public Collection getGoldTypeLabel() {
		return goldTypeLabel;
	}

	public String getGoldTypeItem(String key) {
		if (!goldTypeMap.isEmpty()) {
			return (String) goldTypeMap.get(key);
		}
		return "";
	}
}
