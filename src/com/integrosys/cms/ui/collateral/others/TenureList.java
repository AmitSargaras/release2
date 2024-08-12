/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/others/TenureList.java,v 1.1 2004/06/22 09:00:37 visveswari Exp $
 */
package com.integrosys.cms.ui.collateral.others;

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
 * @author $Author: visveswari $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/06/22 09:00:37 $ Tag: $Name: $
 */
public class TenureList extends BaseList { // ------

	private static Collection tenureListID;

	private static Collection tenureListValue;

	private static HashMap tenureListMap;

	private static Date createdDate;

	private static TenureList thisInstance;

	public synchronized static TenureList getInstance() {
		if (thisInstance == null) {
			thisInstance = new TenureList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new TenureList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private TenureList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		tenureListID = new ArrayList();
		tenureListValue = new ArrayList();
		tenureListMap = new HashMap();
		HashMap tempTenureListMap = new HashMap();
		tenureListMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.TENURE_PROPERTY);
		DefaultLogger.debug(this, "<<<<< ----- Tenure List size: " + tenureListMap.size());
		Collection keyvalue = tenureListMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempTenureListMap.put(tenureListMap.get(key), key);
			// tenureListID.add(key);
			tenureListValue.add(tenureListMap.get(key));
		}
		String[] tempTenureValue = (String[]) tenureListValue.toArray(new String[0]);
		Arrays.sort(tempTenureValue);
		tenureListValue = Arrays.asList(tempTenureValue);
		for (int i = 0; i < tempTenureValue.length; i++) {
			tenureListID.add(tempTenureListMap.get(tempTenureValue[i]));
		}

	}

	public Collection getTenureListID() {
		return tenureListID;
	}

	public Collection getTenureListValue() {
		return tenureListValue;
	}

	public String getTenureListItem(String key) {
		if (!tenureListMap.isEmpty()) {
			return (String) tenureListMap.get(key);
		}
		return "";
	}

}
