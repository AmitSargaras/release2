/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/marketablesec/marksecbondlocal/BondTypeList.java,v 1.3 2003/09/29 12:04:11 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.marketablesec.marksecbondlocal;

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

public class BondTypeList extends BaseList { // ------
	private static Collection bondTypeID;

	private static Collection bondTypeValue;

	private static HashMap bondTypeMap;

	private static Date createdDate;

	private static BondTypeList thisInstance;

	public synchronized static BondTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new BondTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new BondTypeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private BondTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		bondTypeID = new ArrayList();
		bondTypeValue = new ArrayList();
		bondTypeMap = new HashMap();
		HashMap tempBondTypeMap = new HashMap();
		bondTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.BOND_MARKSECBONDLOCAL);
		DefaultLogger.debug(this, "<<<<< ----- Bond Type map size: " + bondTypeMap.size());
		Collection keyvalue = bondTypeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempBondTypeMap.put(bondTypeMap.get(key), key);
			// bondTypeID.add(key);
			bondTypeValue.add(bondTypeMap.get(key));
		}
		String[] tempBondTypeValue = (String[]) bondTypeValue.toArray(new String[0]);
		Arrays.sort(tempBondTypeValue);
		bondTypeValue = Arrays.asList(tempBondTypeValue);
		for (int i = 0; i < tempBondTypeValue.length; i++) {
			bondTypeID.add(tempBondTypeMap.get(tempBondTypeValue[i]));
		}

	}

	public Collection getBondTypeID() {
		return bondTypeID;
	}

	public Collection getBondTypeValue() {
		return bondTypeValue;
	}

	public String getBondTypeItem(String key) {
		if (!bondTypeMap.isEmpty()) {
			return (String) bondTypeMap.get(key);
		}
		return "";
	}
}
