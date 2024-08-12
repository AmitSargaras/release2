/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: to get rating type list from database
 * Description: class for Threshold Rating to get rating type list from database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class RatingTypeList extends BaseList { // ------

	private static Collection ratingTypeListID;

	private static Collection ratingTypeListValue;

	private static HashMap ratingTypeListMap;

	private static Date createdDate;

	private static RatingTypeList thisInstance;

	public synchronized static RatingTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new RatingTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new RatingTypeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private RatingTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		ratingTypeListID = new ArrayList();
		ratingTypeListValue = new ArrayList();
		HashMap tempRatingTypeListMap = new HashMap();
		ratingTypeListMap = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSUIConstant.RATING_TYPE_CODE);

		Collection keyvalue = ratingTypeListMap.keySet();
		Iterator itr1 = keyvalue.iterator();

		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempRatingTypeListMap.put(ratingTypeListMap.get(key), key);
			ratingTypeListValue.add(ratingTypeListMap.get(key));
		}

		String[] tempRatingTypeListValue = (String[]) ratingTypeListValue.toArray(new String[0]);
		Arrays.sort(tempRatingTypeListValue);
		ratingTypeListValue = Arrays.asList(tempRatingTypeListValue);

		for (int i = 0; i < tempRatingTypeListValue.length; i++) {
			ratingTypeListID.add(tempRatingTypeListMap.get(tempRatingTypeListValue[i]));
		}

	}

	public Collection getRatingTypeListID() {
		return ratingTypeListID;
	}

	public Collection getRatingTypeListValue() {
		return ratingTypeListValue;
	}

	public String getRatingTypeName(String key) {
		if (!ratingTypeListMap.isEmpty()) {
			return (String) ratingTypeListMap.get(key);
		}
		return "";
	}

}
