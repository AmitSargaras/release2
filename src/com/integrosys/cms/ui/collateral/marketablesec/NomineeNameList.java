/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/marketablesec/NomineeNameList.java,v 1.3 2003/09/29 12:04:11 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.marketablesec;

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

public class NomineeNameList extends BaseList { // ------
	private static HashMap nomineeNameMap;

	private static Collection nomineeNameID;

	private static Collection nomineeNameValue;

	private static Date createdDate;

	private static NomineeNameList thisInstance;

	public synchronized static NomineeNameList getInstance() {
		if (thisInstance == null) {
			thisInstance = new NomineeNameList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new NomineeNameList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private NomineeNameList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		nomineeNameMap = new HashMap();
		nomineeNameID = new ArrayList();
		nomineeNameValue = new ArrayList();
		HashMap tempNomineeNameMap = new HashMap();

		nomineeNameMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.NOMINEE_NAME);
		DefaultLogger.debug(this, "<<<<< ----- Nature Of Charge map size: " + nomineeNameMap.size());
		Collection keyvalue = nomineeNameMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			// nomineeNameID.add(key);
			tempNomineeNameMap.put(nomineeNameMap.get(key), key);
			nomineeNameValue.add(nomineeNameMap.get(key));
		}
		String[] tempNomineeNameValue = (String[]) nomineeNameValue.toArray(new String[0]);
		Arrays.sort(tempNomineeNameValue);
		nomineeNameValue = Arrays.asList(tempNomineeNameValue);
		for (int i = 0; i < tempNomineeNameValue.length; i++) {
			nomineeNameID.add(tempNomineeNameMap.get(tempNomineeNameValue[i]));
		}

	}

	public Collection getNomineeNameID() {
		return nomineeNameID;
	}

	public Collection getNomineeNameValue() {
		return nomineeNameValue;
	}

	public String getNomineeNameItem(String key) {
		if (nomineeNameMap != null) {
			return (String) nomineeNameMap.get(key);
		}
		else {
			return "";
		}
	}

}
