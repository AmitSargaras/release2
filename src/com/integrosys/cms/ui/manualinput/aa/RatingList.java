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
 * Describe this class. Purpose: to get rating list from database Description:
 * class for Threshold Rating to get rating list from database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class RatingList extends BaseList { // ------

	private static Collection ratingListID;

	private static Collection ratingListValue;

	private static HashMap ratingListMap;

	private static Date createdDate;

	private static RatingList thisInstance;

	public synchronized static RatingList getInstance(String ratingType) {
		if (thisInstance == null) {
			thisInstance = new RatingList(ratingType);
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new RatingList(ratingType);
				// setLastDate(current);
			}
			else {
				thisInstance = null;
				thisInstance = new RatingList(ratingType);
			}
		}
		// -----

		return thisInstance;
	}

	private RatingList(String ratingType) {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		ratingListID = new ArrayList();
		ratingListValue = new ArrayList();
		HashMap tempRatingListMap = new HashMap();
		ratingListMap = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSUIConstant.RATING_CODE, ratingType);

		Collection keyvalue = ratingListMap.keySet();
		Iterator itr1 = keyvalue.iterator();

		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempRatingListMap.put(ratingListMap.get(key), key);
			ratingListValue.add(ratingListMap.get(key));
		}

		String[] tempRatingListValue = (String[]) ratingListValue.toArray(new String[0]);
		Arrays.sort(tempRatingListValue);
		ratingListValue = Arrays.asList(tempRatingListValue);

		for (int i = 0; i < tempRatingListValue.length; i++) {
			ratingListID.add(tempRatingListMap.get(tempRatingListValue[i]));
		}

	}

	public Collection getRatingListID() {
		return ratingListID;
	}

	public Collection getRatingListValue() {
		return ratingListValue;
	}

	public String getRatingName(String key) {
		if (!ratingListMap.isEmpty()) {
			return (String) ratingListMap.get(key);
		}
		return "";
	}

}
