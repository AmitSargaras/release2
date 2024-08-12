/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/ExchangeControlList.java,v 1.2 2003/09/10 05:44:33 hshii Exp $
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
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/10 05:44:33 $ Tag: $Name: $
 */
public class ExchangeControlList extends BaseList { // ------

	private static ArrayList exchangeControlID;

	private static ArrayList exchangeControlValue;

	private static HashMap exchangeControlMap;

	private static Date createdDate;

	private static ExchangeControlList thisInstance;

	public synchronized static ExchangeControlList getInstance() {
		if (thisInstance == null) {
			thisInstance = new ExchangeControlList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new ExchangeControlList();
				// setLastDate(current);
			}
		}
		// ------

		return thisInstance;
	}

	private ExchangeControlList() {

		// ------
		super(); // call super class constructor to register a time stamp
		createdDate = super.getLastDate();
		// ------

		exchangeControlID = new ArrayList();
		exchangeControlValue = new ArrayList();
		exchangeControlMap = new HashMap();
		exchangeControlMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.EXCHANGE_CONTROL);
		DefaultLogger.debug(this, "<<<<< ----- Exchange Control map size: " + exchangeControlMap.size());
		Collection keyvalue = exchangeControlMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			exchangeControlID.add(key);
			exchangeControlValue.add(exchangeControlMap.get(key));
		}
	}

	public Collection getExchangeControlID() {
		return exchangeControlID;
	}

	public Collection getExchangecontrolValue() {
		return exchangeControlValue;
	}

	public String getExchangeControlItem(String key) {
		if (!exchangeControlMap.isEmpty()) {
			return (String) exchangeControlMap.get(key);
		}
		return "";
	}
}
