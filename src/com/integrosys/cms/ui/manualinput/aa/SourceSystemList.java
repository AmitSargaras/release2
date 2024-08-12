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
 * Describe this class. Purpose: to get source system list from database
 * Description: class for AA detail to get source system list from database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class SourceSystemList extends BaseList { // ------

	private static Collection sourceSystemListID;

	private static Collection sourceSystemListValue;

	private static HashMap sourceSystemListMap;

	private static Date createdDate;

	private static SourceSystemList thisInstance;

	public synchronized static SourceSystemList getInstance(String countryCode) {
		if (thisInstance == null) {
			thisInstance = new SourceSystemList(countryCode);
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new SourceSystemList(countryCode);
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private SourceSystemList(String countryCode) {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		sourceSystemListID = new ArrayList();
		sourceSystemListValue = new ArrayList();
		HashMap tempSourceSystemListMap = new HashMap();
		sourceSystemListMap = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSUIConstant.AA_SOURCE_CODE, null,
				countryCode);

		Collection keyvalue = sourceSystemListMap.keySet();
		Iterator itr1 = keyvalue.iterator();

		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempSourceSystemListMap.put(sourceSystemListMap.get(key), key);
			sourceSystemListValue.add(sourceSystemListMap.get(key));
		}

		String[] tempSourceSystemListValue = (String[]) sourceSystemListValue.toArray(new String[0]);
		Arrays.sort(tempSourceSystemListValue);
		sourceSystemListValue = Arrays.asList(tempSourceSystemListValue);

		for (int i = 0; i < tempSourceSystemListValue.length; i++) {
			sourceSystemListID.add(tempSourceSystemListMap.get(tempSourceSystemListValue[i]));
		}

	}

	public Collection getSourceSystemListID() {
		return sourceSystemListID;
	}

	public Collection getSourceSystemListValue() {
		return sourceSystemListValue;
	}

	public String getSourceSystemItem(String key) {
		if (!sourceSystemListMap.isEmpty()) {
			return (String) sourceSystemListMap.get(key);
		}
		return "";
	}

}
