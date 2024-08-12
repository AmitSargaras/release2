/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/TimeFreqList.java,v 1.3 2005/04/01 09:44:11 hshii Exp $
 */
package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/04/01 09:44:11 $ Tag: $Name: $
 */
public class TimeFreqList extends BaseList { // ------

	private static ArrayList timeFreqID;

	private static ArrayList timeFreqValue;

	private static HashMap timeFreqMap;

	private static Date createdDate;

	private static TimeFreqList thisInstance;

	public synchronized static TimeFreqList getInstance() {
		if (thisInstance == null) {
			thisInstance = new TimeFreqList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new TimeFreqList();
				// setLastDate(current);
			}
		}
		// ------

		return thisInstance;
	}

	private TimeFreqList() {

		// ------
		super(); // call super class constructor to register a time stamp
		createdDate = super.getLastDate();
		// ------

		timeFreqID = new ArrayList();
		timeFreqValue = new ArrayList();
		timeFreqMap = new HashMap();
		timeFreqMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.TIME_FREQ);
		DefaultLogger.debug(this, "<<<<< ----- Time Freq map size: " + timeFreqMap.size());
		Collection keyvalue = timeFreqMap.keySet();
		String[] keyValueList = (String[]) keyvalue.toArray(new String[0]);
		Arrays.sort(keyValueList);

		for (int i = 0; i < keyValueList.length; i++) {
			String key = keyValueList[i];
			timeFreqID.add(key);
			timeFreqValue.add(timeFreqMap.get(key));
		}
	}

	public Collection getTimeFreqID() {
		return timeFreqID;
	}

	public Collection getTimeFreqValue() {
		return timeFreqValue;
	}

	public String getTimeFreqItem(String key) {
		if (!timeFreqMap.isEmpty()) {
			return (String) timeFreqMap.get(key);
		}
		return "";
	}
}
