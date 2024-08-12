/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/financingdoc/SalesDocTypeList.java,v 1.5 2005/09/15 06:00:48 czhou Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.financingdoc;

import java.util.ArrayList;
import java.util.Arrays;
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
 * @author $Author: czhou $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/09/15 06:00:48 $ Tag: $Name: $
 */
public class SalesDocTypeList extends BaseList { // ------
	private static Collection salesDocTypeID;

	private static Collection salesDocTypeValue;

	private static HashMap salesDocTypeMap;

	private static Date createdDate;

	private static SalesDocTypeList thisInstance;

	public synchronized static SalesDocTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new SalesDocTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new SalesDocTypeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private SalesDocTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		salesDocTypeID = new ArrayList();
		salesDocTypeValue = new ArrayList();
		salesDocTypeMap = new HashMap();
		HashMap tempSalesDocTypeMap = new HashMap();
		salesDocTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CommodityDealConstant.SALES_DOC_DESCRIPTION);
		Collection keyvalue = salesDocTypeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempSalesDocTypeMap.put(salesDocTypeMap.get(key), key);
			salesDocTypeValue.add(salesDocTypeMap.get(key));
		}
		String[] tempSalesDocTypeValue = (String[]) salesDocTypeValue.toArray(new String[0]);
		Arrays.sort(tempSalesDocTypeValue);
		salesDocTypeValue = Arrays.asList(tempSalesDocTypeValue);
		for (int i = 0; i < tempSalesDocTypeValue.length; i++) {
			salesDocTypeID.add(tempSalesDocTypeMap.get(tempSalesDocTypeValue[i]));
		}
	}

	public Collection getSalesDocTypeID() {
		return salesDocTypeID;
	}

	public Collection getSalesDocTypeValue() {
		return salesDocTypeValue;
	}

	public String getSalesDocTypeItem(String key) {
		if ((key != null) && (!salesDocTypeMap.isEmpty())) {
			return (String) salesDocTypeMap.get(key.trim());
		}
		return "";
	}

}
