/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/InsurerNameList.java,v 1.1 2005/08/12 02:44:13 hshii Exp $
 */
package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/08/12 02:44:13 $ Tag: $Name: $
 */
public class InsurerNameList extends BaseList { // ------

	private static Collection insurerNameID;

	private static Collection insurerNameValue;

	private static HashMap insurerNameMap;

	private static Date createdDate;

	private static InsurerNameList thisInstance;

	public synchronized static InsurerNameList getInstance() {
		if (thisInstance == null) {
			thisInstance = new InsurerNameList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new InsurerNameList();
				// setLastDate(current);
			}
		}
		// ------

		return thisInstance;
	}

	private InsurerNameList() {

		// ------
		super(); // call super class constructor to register a time stamp
		createdDate = super.getLastDate();
		// ------

		insurerNameID = new ArrayList();
		insurerNameValue = new ArrayList();
		insurerNameMap = new HashMap();
		HashMap tempInsurerNameMap = new HashMap();
		insurerNameMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.INSURER_NAME);
		Collection keyvalue = insurerNameMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempInsurerNameMap.put(insurerNameMap.get(key), key);
			insurerNameValue.add(insurerNameMap.get(key));
		}

		String[] tempInsurerNameValue = (String[]) insurerNameValue.toArray(new String[0]);
		Arrays.sort(tempInsurerNameValue);
		insurerNameValue = Arrays.asList(tempInsurerNameValue);
		for (int i = 0; i < tempInsurerNameValue.length; i++) {
			insurerNameID.add(tempInsurerNameMap.get(tempInsurerNameValue[i]));
		}

	}

	public Collection getInsurerNameID() {
		return insurerNameID;
	}

	public Collection getInsurerNameValue() {
		return insurerNameValue;
	}

	public String getInsurerNameItem(String key) {
		if (!insurerNameMap.isEmpty()) {
			return (String) insurerNameMap.get(key);
		}
		return "";
	}
}
