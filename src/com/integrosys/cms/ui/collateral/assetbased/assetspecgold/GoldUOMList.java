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
 * GoldUOMList
 * 
 * Describe this class. Purpose: Parameter List for Gold UOM Description: Class
 * for Parameter List of Gold UOM
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class GoldUOMList extends BaseList { // ------

	private static HashMap goldUOMMap;

	private static Collection goldUOMValue;

	private static Collection goldUOMLabel;

	private static Date createdDate;

	private static GoldUOMList thisInstance;

	public synchronized static GoldUOMList getInstance() {
		if (thisInstance == null) {
			thisInstance = new GoldUOMList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new GoldUOMList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private GoldUOMList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		goldUOMValue = new ArrayList();
		goldUOMLabel = new ArrayList();
		goldUOMMap = new HashMap();
		HashMap tempGoldUOMMap = new HashMap();
		goldUOMMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.CATEGORY_GOLD_UOM);
		DefaultLogger.debug(this, "<<<<< ----- Gold UOM map size: " + goldUOMMap.size());
		Collection keyvalue = goldUOMMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempGoldUOMMap.put(goldUOMMap.get(key), key);
			// goldUOMValue.add(key);
			goldUOMLabel.add(goldUOMMap.get(key));
		}
		String[] tempGoldUOMLabel = (String[]) goldUOMLabel.toArray(new String[0]);
		Arrays.sort(tempGoldUOMLabel);
		goldUOMLabel = Arrays.asList(tempGoldUOMLabel);
		for (int i = 0; i < tempGoldUOMLabel.length; i++) {
			goldUOMValue.add(tempGoldUOMMap.get(tempGoldUOMLabel[i]));
		}

	}

	public Collection getGoldUOMValue() {
		return goldUOMValue;
	}

	public Collection getGoldUOMLabel() {
		return goldUOMLabel;
	}

	public String getGoldUOMItem(String key) {
		if (!goldUOMMap.isEmpty()) {
			return (String) goldUOMMap.get(key);
		}
		return "";
	}
}
