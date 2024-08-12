/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/marketablesec/SettleOrgList.java,v 1.3 2003/09/29 12:04:11 hshii Exp $
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

public class SettleOrgList extends BaseList { // ------
	private static Collection settleOrgID;

	private static Collection settleOrgValue;

	private static HashMap settleOrgMap;

	private static Date createdDate;

	private static SettleOrgList thisInstance;

	public synchronized static SettleOrgList getInstance() {
		if (thisInstance == null) {
			thisInstance = new SettleOrgList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new SettleOrgList();
				// setLastDate(current);
			}
		}
		// -----

		return thisInstance;
	}

	private SettleOrgList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		settleOrgID = new ArrayList();
		settleOrgValue = new ArrayList();
		settleOrgMap = new HashMap();
		HashMap tempSettleOrgMap = new HashMap();
		settleOrgMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.SETTLEMENT_ORG);
		DefaultLogger.debug(this, "<<<<< ----- Settlement Organisation map size: " + settleOrgMap.size());
		Collection keyvalue = settleOrgMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempSettleOrgMap.put(settleOrgMap.get(key), key);
			// settleOrgID.add(key);
			settleOrgValue.add(settleOrgMap.get(key));
		}
		String[] tempSettleOrgValue = (String[]) settleOrgValue.toArray(new String[0]);
		Arrays.sort(tempSettleOrgValue);
		settleOrgValue = Arrays.asList(tempSettleOrgValue);
		for (int i = 0; i < tempSettleOrgValue.length; i++) {
			settleOrgID.add(tempSettleOrgMap.get(tempSettleOrgValue[i]));
		}

	}

	public Collection getSettleOrgID() {
		return settleOrgID;
	}

	public Collection getSettleOrgValue() {
		return settleOrgValue;
	}

	public String getSettlementOrgItem(String key) {
		if (settleOrgMap != null) {
			return (String) settleOrgMap.get(key);
		}
		else {
			return "";
		}
	}
}
