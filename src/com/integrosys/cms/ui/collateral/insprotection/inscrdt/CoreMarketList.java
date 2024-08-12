/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/inscrdt/CoreMarketList.java,v 1.4 2003/09/29 12:04:11 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.insprotection.inscrdt;

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
public class CoreMarketList extends BaseList { // ------

	private static Collection coreMarketID;

	private static Collection coreMarketValue;

	private static HashMap coreMarketMap;

	private static Date createdDate;

	private static CoreMarketList thisInstance;

	public synchronized static CoreMarketList getInstance() {
		if (thisInstance == null) {
			thisInstance = new CoreMarketList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new CoreMarketList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private CoreMarketList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		coreMarketID = new ArrayList();
		coreMarketValue = new ArrayList();
		coreMarketMap = new HashMap();
		HashMap tempCoreMarketMap = new HashMap();
		coreMarketMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.CORE_MARKET);
		DefaultLogger.debug(this, "<<<<< ----- Core Market map size: " + coreMarketMap.size());
		Collection keyvalue = coreMarketMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			// coreMarketID.add(key);
			tempCoreMarketMap.put(coreMarketMap.get(key), key);
			coreMarketValue.add(coreMarketMap.get(key));
		}
		String[] tempCoreMarketValue = (String[]) coreMarketValue.toArray(new String[0]);
		Arrays.sort(tempCoreMarketValue);
		coreMarketValue = Arrays.asList(tempCoreMarketValue);
		for (int i = 0; i < tempCoreMarketValue.length; i++) {
			coreMarketID.add(tempCoreMarketMap.get(tempCoreMarketValue[i]));
		}

	}

	public Collection getCoreMarketID() {
		return coreMarketID;
	}

	public Collection getCoreMarketValue() {
		return coreMarketValue;
	}

	public String getCoreMarketItem(String key) {
		if (!coreMarketMap.isEmpty()) {
			return (String) coreMarketMap.get(key);
		}
		return "";
	}

}
