/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/debtors/DebtorPeriodList.java,v 1.2 2005/03/29 12:02:30 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.debtors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/03/29 12:02:30 $ Tag: $Name: $
 */
public class DebtorPeriodList extends BaseList { // ------

	private static Collection debtorPeriodID;

	private static Collection debtorPeriodValue;

	private static HashMap debtorPeriodMap;

	private static Date createdDate;

	private static DebtorPeriodList thisInstance;

	public synchronized static DebtorPeriodList getInstance() {
		if (thisInstance == null) {
			thisInstance = new DebtorPeriodList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new DebtorPeriodList();
				// setLastDate(current);
			}
		}
		// -----

		return thisInstance;
	}

	private DebtorPeriodList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		debtorPeriodID = new ArrayList();
		debtorPeriodValue = new ArrayList();
		debtorPeriodMap = new HashMap();

		debtorPeriodMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.AGC_DEBTOR_PERIOD);
		Collection keyvalue = debtorPeriodMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			debtorPeriodID.add(key);
		}
		String[] tempDebtorPeriodID = (String[]) debtorPeriodID.toArray(new String[0]);
		Arrays.sort(tempDebtorPeriodID, new Comparator() {
			public int compare(Object o1, Object o2) {
				int id1 = Integer.parseInt((String) o1);
				int id2 = Integer.parseInt((String) o2);
				return id1 - id2;
			}
		});
		debtorPeriodID = Arrays.asList(tempDebtorPeriodID);
		for (int i = 0; i < tempDebtorPeriodID.length; i++) {
			debtorPeriodValue.add(debtorPeriodMap.get(tempDebtorPeriodID[i]));
		}
	}

	public Collection getDebtorPeriodID() {
		return debtorPeriodID;
	}

	public Collection getDebtorPeriodValue() {
		return debtorPeriodValue;
	}

	public String getDebtorPeriodItem(String key) {
		if (!debtorPeriodMap.isEmpty()) {
			return (String) debtorPeriodMap.get(key);
		}
		return "";
	}
}
