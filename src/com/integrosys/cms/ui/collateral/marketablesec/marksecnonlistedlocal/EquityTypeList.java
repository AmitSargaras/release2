/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/marketablesec/marksecnonlistedlocal/EquityTypeList.java,v 1.4 2003/09/29 12:04:11 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.marketablesec.marksecnonlistedlocal;

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
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/29 12:04:11 $ Tag: $Name: $
 */

public class EquityTypeList extends BaseList { // ------
	private static HashMap equityTypeMap;

	private static Collection equityTypeID;

	private static Collection equityTypeValue;

	private static Date createdDate;

	private static EquityTypeList thisInstance;

	public synchronized static EquityTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new EquityTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new EquityTypeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private EquityTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		equityTypeMap = new HashMap();
		equityTypeID = new ArrayList();
		equityTypeValue = new ArrayList();
		HashMap tempEquityTypeMap = new HashMap();
		equityTypeMap = CommonDataSingleton
				.getCodeCategoryValueLabelMap(CategoryCodeConstant.EQUITY_MARKSECNONLISTEDLOCAL);
		DefaultLogger.debug(this, "<<<<< ----- Equity Type map size: " + equityTypeMap.size());
		Collection keyvalue = equityTypeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempEquityTypeMap.put(equityTypeMap.get(key), key);
			// equityTypeID.add(key);
			equityTypeValue.add(equityTypeMap.get(key));
		}
		String[] tempEquityTypeValue = (String[]) equityTypeValue.toArray(new String[0]);
		Arrays.sort(tempEquityTypeValue);
		equityTypeValue = Arrays.asList(tempEquityTypeValue);
		for (int i = 0; i < tempEquityTypeValue.length; i++) {
			equityTypeID.add(tempEquityTypeMap.get(tempEquityTypeValue[i]));
		}
	}

	public Collection getEquityTypeID() {
		return equityTypeID;
	}

	public Collection getEquityTypeValue() {
		return equityTypeValue;
	}

	public String getEquityTypeItem(String key) {
		if (equityTypeMap != null) {
			return (String) equityTypeMap.get(key);
		}
		else {
			return "";
		}
	}

}
