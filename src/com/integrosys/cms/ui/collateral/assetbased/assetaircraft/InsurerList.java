/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetaircraft/InsurerList.java,v 1.3 2003/09/29 12:04:11 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetaircraft;

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
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/29 12:04:11 $ Tag: $Name: $
 */
public class InsurerList extends BaseList { // ------

	private static HashMap insurerMap;

	private static Collection insurerID;

	private static Collection insurerValue;

	private static Date createdDate;

	private static InsurerList thisInstance;

	public synchronized static InsurerList getInstance() {
		if (thisInstance == null) {
			thisInstance = new InsurerList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new InsurerList();
				// setLastDate(current);
			}
		}
		// ------

		return thisInstance;
	}

	private InsurerList() {

		// ------
		super(); // call super class constructor to register a time stamp
		createdDate = super.getLastDate();
		// ------

		insurerID = new ArrayList();
		insurerValue = new ArrayList();
		insurerMap = new HashMap();
		HashMap tempInsurerMap = new HashMap();
		insurerMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.INSURER_NAME);
		DefaultLogger.debug(this, "<<<<< ----- Insurer Name map size: " + insurerMap.size());
		Collection keyvalue = insurerMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempInsurerMap.put(insurerMap.get(key), key);
			// insurerID.add(key);
			insurerValue.add(insurerMap.get(key));
		}
		String[] tempInsurerValue = (String[]) insurerValue.toArray(new String[0]);
		Arrays.sort(tempInsurerValue);
		insurerValue = Arrays.asList(tempInsurerValue);
		for (int i = 0; i < tempInsurerValue.length; i++) {
			insurerID.add(tempInsurerMap.get(tempInsurerValue[i]));
		}

	}

	public Collection getInsurerID() {
		return insurerID;
	}

	public Collection getInsurerValue() {
		return insurerValue;
	}

	public String getInsurerItem(String key) {
		if (!insurerMap.isEmpty()) {
			return (String) insurerMap.get(key);
		}
		return "";
	}
}
