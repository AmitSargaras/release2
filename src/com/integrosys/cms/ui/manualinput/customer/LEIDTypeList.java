/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.common.constant.ICategoryEntryConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class LEIDTypeList extends BaseList { // ------

	// The options for LE ID Type on the screen
	static final String[] CUST_TYPE_LIST = { ICategoryEntryConstant.GCIF_ENTRY_CODE,
			ICategoryEntryConstant.BWCIF_ENTRY_CODE, ICategoryEntryConstant.PROBANK_ENTRY_CODE,
			ICategoryEntryConstant.MYSEC_ENTRY_CODE };

	private static ArrayList LEIDTypeID;

	private static ArrayList LEIDTypeValue;

	private static HashMap LEIDTypeMap;

	private static Date createdDate;

	private static LEIDTypeList thisInstance;

	public synchronized static LEIDTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new LEIDTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new LEIDTypeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private LEIDTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		LEIDTypeID = new ArrayList();
		LEIDTypeValue = new ArrayList();
		LEIDTypeMap = new HashMap();
		HashMap tempLEIDTypeListMap = new HashMap();
		LEIDTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSUIConstant.COMMON_CODE_REF_LE_ID_TYPE);
		Collection keyvalue = LEIDTypeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			if (contains(key)) {
				tempLEIDTypeListMap.put(LEIDTypeMap.get(key), key);
				LEIDTypeValue.add(LEIDTypeMap.get(key));
			}
		}
		String[] tempLEIDTypeListValue = (String[]) LEIDTypeValue.toArray(new String[0]);
		Arrays.sort(tempLEIDTypeListValue);
		LEIDTypeValue = new ArrayList(Arrays.asList(tempLEIDTypeListValue));

		for (int i = 0; i < tempLEIDTypeListValue.length; i++) {
			LEIDTypeID.add(tempLEIDTypeListMap.get(tempLEIDTypeListValue[i]));
		}
		// LEIDTypeID.add(key);
	}

	private boolean contains(String key) {
		for (int k = 0; k < CUST_TYPE_LIST.length; k++) {

			if (key.equals(CUST_TYPE_LIST[k])) {
				return true;
			}
		}
		return false;
	}

	public Collection getLEIDTypeID() {
		return LEIDTypeID;
	}

	public Collection getLEIDTypeValue() {
		return LEIDTypeValue;
	}

	public String getLEIDTypeItem(String key) {
		if (!LEIDTypeMap.isEmpty()) {
			return (String) LEIDTypeMap.get(key);
		}
		return "";
	}
}
