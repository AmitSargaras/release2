/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/YesNoList.java
 */
package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: kienleong $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 2007/03/01$ Tag: $Name: $
 */
public class YesNoList extends BaseList { // ------

	private static ArrayList yesNoID;

	private static ArrayList yesNoValue;

	private static HashMap yesNoMap;

	private static Date createdDate;

	private static YesNoList thisInstance;

	public synchronized static YesNoList getInstance() {
		if (thisInstance == null) {
			thisInstance = new YesNoList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new YesNoList();
				// setLastDate(current);
			}
		}
		// ------

		return thisInstance;
	}

	private YesNoList() {

		// ------
		super(); // call super class constructor to register a time stamp
		createdDate = super.getLastDate();
		// ------

		yesNoID = new ArrayList();
		yesNoValue = new ArrayList();
		yesNoMap = new HashMap();
		yesNoMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.YES_NO_NA);
		DefaultLogger.debug(this, "<<<<< ----- Yes No map size: " + yesNoMap.size());
		Collection keyvalue = yesNoMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			yesNoID.add(key);
			yesNoValue.add(yesNoMap.get(key));
		}
	}

	public Collection getYesNoID() {
		return yesNoID;
	}

	public Collection getYesNoValue() {
		return yesNoValue;
	}

	public String getYesNoItem(String key) {
		if (!yesNoMap.isEmpty()) {
			return (String) yesNoMap.get(key);
		}

		return "";
	}

	public String getYesNoItem(char key) {
		if (!yesNoMap.isEmpty()) {
			return (String) yesNoMap.get(String.valueOf(key).toUpperCase());
		}

		return "";
	}
}
