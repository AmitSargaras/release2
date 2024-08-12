/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/property/RankList.java,v 1.4 2005/08/24 08:43:56 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/08/24 08:43:56 $ Tag: $Name: $
 */
public class RankList extends BaseList { // ------
	private static HashMap rankListMap;

	private static Collection rankListID;

	private static Collection rankListValue;

	private static Date createdDate;

	private static RankList thisInstance;

	public synchronized static RankList getInstance() {
		if (thisInstance == null) {
			thisInstance = new RankList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new RankList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private RankList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		rankListID = new ArrayList();
		rankListValue = new ArrayList();
		rankListMap = new HashMap();
		HashMap tempRankListMap = new HashMap();
		rankListMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.RANK);

		Collection keyvalue = rankListMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempRankListMap.put(rankListMap.get(key), key);
			rankListID.add(key);
		}

		String[] tempRankID = (String[]) rankListID.toArray(new String[0]);
		Arrays.sort(tempRankID, new Comparator() {
			public int compare(Object o1, Object o2) {
				int int1 = Integer.parseInt(((String) o1));
				int int2 = Integer.parseInt(((String) o2));

				return int1 - int2;
			}
		});

		rankListID = Arrays.asList(tempRankID);
		for (int i = 0; i < tempRankID.length; i++) {
			rankListValue.add(rankListMap.get(tempRankID[i]));
		}

	}

	public Collection getRankListID() {
		return rankListID;
	}

	public Collection getRankListValue() {
		return rankListValue;
	}

	public String getRankListItem(String key) {
		return (String) rankListMap.get(key);
	}
}
