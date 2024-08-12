/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/TrxTypeList.java,v 1.1 2004/06/21 11:43:42 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/06/21 11:43:42 $ Tag: $Name: $
 */
public class TrxTypeList extends BaseList { // ------
	private static ArrayList trxTypeID;

	private static ArrayList trxTypeValue;

	private static HashMap trxTypeMap;

	private static Date createdDate;

	private static TrxTypeList thisInstance;

	public synchronized static TrxTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new TrxTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new TrxTypeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private TrxTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		trxTypeID = new ArrayList();
		trxTypeValue = new ArrayList();
		trxTypeMap = new HashMap();
		trxTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CommodityMainConstant.TRX_TYPE);
		Collection keyvalue = trxTypeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			trxTypeID.add(key);
			trxTypeValue.add(trxTypeMap.get(key));
		}
	}

	public Collection getTrxTypeID() {
		return trxTypeID;
	}

	public Collection getTrxTypeValue() {
		return trxTypeValue;
	}

	public String getTrxTypeItem(String key) {
		if (!trxTypeMap.isEmpty()) {
			return (String) trxTypeMap.get(key);
		}
		return "";
	}
}
