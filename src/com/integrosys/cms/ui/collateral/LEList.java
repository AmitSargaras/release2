/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/LEList.java,v 1.2 2005/08/19 08:10:59 hshii Exp $
 */
package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/19 08:10:59 $ Tag: $Name: $
 */
public class LEList extends BaseList { // ------

	private static ArrayList lEID;

	private static ArrayList lEValue;

	private static HashMap lEMap;

	private static Date createdDate;

	private static LEList thisInstance;

	public synchronized static LEList getInstance() {
		if (thisInstance == null) {
			thisInstance = new LEList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new LEList();
				// setLastDate(current);
			}
		}
		// ------

		return thisInstance;
	}

	private LEList() {

		// ------
		super(); // call super class constructor to register a time stamp
		createdDate = super.getLastDate();
		// ------

		lEID = new ArrayList();
		lEValue = new ArrayList();
		lEMap = new HashMap();
		lEMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.LE_VALUE);
		Collection keyvalue = lEMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			lEID.add(key);
			lEValue.add(lEMap.get(key));
		}
	}

	public Collection getLEID() {
		return lEID;
	}

	public Collection getLEValue() {
		return lEValue;
	}

	public String getLEItem(String key) {
		if (!lEMap.isEmpty()) {
			return (String) lEMap.get(key);
		}
		return "";
	}
}
