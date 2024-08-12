/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/ChequeTypeList.java,v 1.3 2003/09/29 12:04:11 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs;

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
public class ChequeTypeList extends BaseList { // ------

	private static Collection chequeTypeID;

	private static Collection chequeTypeValue;

	private static HashMap chequeTypeMap;

	private static Date createdDate;

	private static ChequeTypeList thisInstance;

	public synchronized static ChequeTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new ChequeTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new ChequeTypeList();
				// setLastDate(current);
			}
		}
		// -----

		return thisInstance;
	}

	private ChequeTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		chequeTypeID = new ArrayList();
		chequeTypeValue = new ArrayList();
		chequeTypeMap = new HashMap();
		HashMap tempChequeTypeMap = new HashMap();
		chequeTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.CHEQUE_TYPE);
		DefaultLogger.debug(this, "<<<<< ----- Cheque Type map size: " + chequeTypeMap.size());
		Collection keyvalue = chequeTypeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempChequeTypeMap.put(chequeTypeMap.get(key), key);
			// chequeTypeID.add(key);
			chequeTypeValue.add(chequeTypeMap.get(key));
		}
		String[] tempChequeTypeValue = (String[]) chequeTypeValue.toArray(new String[0]);
		Arrays.sort(tempChequeTypeValue);
		chequeTypeValue = Arrays.asList(tempChequeTypeValue);
		for (int i = 0; i < tempChequeTypeValue.length; i++) {
			chequeTypeID.add(tempChequeTypeMap.get(tempChequeTypeValue[i]));
		}

	}

	public Collection getChequeTypeID() {
		return chequeTypeID;
	}

	public Collection getChequeTypeValue() {
		return chequeTypeValue;
	}

	public String getChequeTypeItem(String key) {
		if (!chequeTypeMap.isEmpty()) {
			return (String) chequeTypeMap.get(key);
		}
		return "";
	}
}
