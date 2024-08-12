/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/generalinfo/cashdeposit/CashTypeList.java,v 1.2 2004/06/04 05:07:38 hltan Exp $
 */
package com.integrosys.cms.ui.commoditydeal.generalinfo.cashdeposit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:07:38 $ Tag: $Name: $
 */
public class CashTypeList extends BaseList { // ------
	private static ArrayList cashTypeID;

	private static ArrayList cashTypeValue;

	private static HashMap cashTypeMap;

	private static Date createdDate;

	private static CashTypeList thisInstance;

	public synchronized static CashTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new CashTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new CashTypeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private CashTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		cashTypeID = new ArrayList();
		cashTypeValue = new ArrayList();
		cashTypeMap = new HashMap();
		cashTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CommodityDealConstant.CASH_TYPE);
		Collection keyvalue = cashTypeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			cashTypeID.add(key);
			cashTypeValue.add(cashTypeMap.get(key));
		}
	}

	public Collection getCashTypeID() {
		return cashTypeID;
	}

	public Collection getCashTypeValue() {
		return cashTypeValue;
	}

	public String getCashTypeItem(String key) {
		if (!cashTypeMap.isEmpty()) {
			return (String) cashTypeMap.get(key);
		}
		return "";
	}
}
