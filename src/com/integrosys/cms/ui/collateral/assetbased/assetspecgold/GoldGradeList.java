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
 * GoldGradeList
 * 
 * Describe this class. Purpose: Parameter List for Gold Grade Description:
 * Class for Parameter List of Gold Grade
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class GoldGradeList extends BaseList { // ------

	private static HashMap goldGradeMap;

	private static Collection goldGradeValue;

	private static Collection goldGradeLabel;

	private static Date createdDate;

	private static GoldGradeList thisInstance;

	public synchronized static GoldGradeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new GoldGradeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new GoldGradeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private GoldGradeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		goldGradeValue = new ArrayList();
		goldGradeLabel = new ArrayList();
		goldGradeMap = new HashMap();
		HashMap tempGoldGradeMap = new HashMap();
		goldGradeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.CATEGORY_GOLD_GRADE);
		DefaultLogger.debug(this, "<<<<< ----- Gold Grade map size: " + goldGradeMap.size());
		Collection keyvalue = goldGradeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempGoldGradeMap.put(goldGradeMap.get(key), key);
			// goldGradeValue.add(key);
			goldGradeLabel.add(goldGradeMap.get(key));
		}
		String[] tempGoldGradeLabel = (String[]) goldGradeLabel.toArray(new String[0]);
		Arrays.sort(tempGoldGradeLabel);
		goldGradeLabel = Arrays.asList(tempGoldGradeLabel);
		for (int i = 0; i < tempGoldGradeLabel.length; i++) {
			goldGradeValue.add(tempGoldGradeMap.get(tempGoldGradeLabel[i]));
		}

	}

	public Collection getGoldGradeValue() {
		return goldGradeValue;
	}

	public Collection getGoldGradeLabel() {
		return goldGradeLabel;
	}

	public String getGoldGradeItem(String key) {
		if (!goldGradeMap.isEmpty()) {
			return (String) goldGradeMap.get(key);
		}
		return "";
	}
}
