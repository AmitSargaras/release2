/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/NatureOfChargeList.java,v 1.4 2003/09/29 12:04:11 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.insprotection;

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
public class NatureOfChargeList extends BaseList { // ------

	private static HashMap natureOfChargeMap;

	private static Collection natureOfChargeListID; // nature of charge code,
													// and nature of charge name

	private static Collection natureOfChargeListValue;

	private static Date createdDate;

	private static NatureOfChargeList thisInstance;

	public synchronized static NatureOfChargeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new NatureOfChargeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new NatureOfChargeList();
				// setLastDate(current);
			}
		}
		// -----

		return thisInstance;
	}

	private NatureOfChargeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		natureOfChargeListID = new ArrayList();
		natureOfChargeListValue = new ArrayList();
		natureOfChargeMap = new HashMap();
		HashMap tempNatureOfChargeMap = new HashMap();
		natureOfChargeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.NAT_OF_CHARGE_INS);
		DefaultLogger.debug(this, "<<<<< ----- Nature Of Charge map size: " + natureOfChargeMap.size());
		Collection keyvalue = natureOfChargeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			// natureOfChargeListID.add(key);
			tempNatureOfChargeMap.put(natureOfChargeMap.get(key), key);
			natureOfChargeListValue.add(natureOfChargeMap.get(key));
		}
		String[] tempNatureOfChargeValue = (String[]) natureOfChargeListValue.toArray(new String[0]);
		Arrays.sort(tempNatureOfChargeValue);
		natureOfChargeListValue = Arrays.asList(tempNatureOfChargeValue);
		for (int i = 0; i < tempNatureOfChargeValue.length; i++) {
			natureOfChargeListID.add(tempNatureOfChargeMap.get(tempNatureOfChargeValue[i]));
		}

	}

	public Collection getNatureOfChargeListID() {
		return natureOfChargeListID;
	}

	public Collection getNatureOfChargeListValue() {
		return natureOfChargeListValue;
	}

	public String getNatureOfChargeItem(String key) {
		if (!natureOfChargeMap.isEmpty()) {
			return (String) natureOfChargeMap.get(key);
		}
		return "";
	}
}
