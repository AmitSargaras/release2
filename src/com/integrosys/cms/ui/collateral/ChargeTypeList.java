/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/ChargeTypeList.java,v 1.1 2003/09/15 09:48:53 hshii Exp $
 */
package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/15 09:48:53 $ Tag: $Name: $
 */
public class ChargeTypeList extends BaseList { // ------

	private static ArrayList chargeTypeID;

	private static ArrayList chargeTypeValue;

	private static HashMap chargeTypeMap;

	private static Date createdDate;

	private static ChargeTypeList thisInstance;

	public synchronized static ChargeTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new ChargeTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new ChargeTypeList();
				// setLastDate(current);
			}
		}
		// ------

		return thisInstance;
	}

	private ChargeTypeList() {
		// ------
		super(); // call super class constructor to register a time stamp
		createdDate = super.getLastDate();
		// ------

		chargeTypeID = new ArrayList();
		chargeTypeValue = new ArrayList();
		chargeTypeMap = new HashMap();
		chargeTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.CHARGE_TYPE);
		DefaultLogger.debug(this, "<<<<< ----- Time Freq map size: " + chargeTypeMap.size());
		Collection keyvalue = chargeTypeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			chargeTypeID.add(key);
			chargeTypeValue.add(chargeTypeMap.get(key));
		}
	}

	public Collection getChargeTypeID() {
		return chargeTypeID;
	}

	public Collection getChargeTypeValue() {
		return chargeTypeValue;
	}

	public String getChargeTypeItem(String key) {
		if (!chargeTypeMap.isEmpty()) {
			return (String) chargeTypeMap.get(key);
		}
		return "";
	}
}
