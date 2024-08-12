/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.gmra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: for screen to get the product type Description:
 * class that pull the product type from database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class ProductTypeList extends BaseList { // ------

	private static Collection productTypeListID;

	private static Collection productTypeListValue;

	private static HashMap productTypeListMap;

	private static Date createdDate;

	private static ProductTypeList thisInstance;

	public synchronized static ProductTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new ProductTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new ProductTypeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private ProductTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		productTypeListID = new ArrayList();
		productTypeListValue = new ArrayList();
		HashMap tempProductTypeListMap = new HashMap();

		productTypeListMap = CommonDataSingleton.getCodeCategoryValueLabelMap("FAC_TYPE_TRADE", "GMRA");
		Collection keyvalue = productTypeListMap.keySet();
		Iterator itr1 = keyvalue.iterator();

		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempProductTypeListMap.put(productTypeListMap.get(key), key);
			productTypeListValue.add(productTypeListMap.get(key));
		}

		String[] tempProductTypeListValue = (String[]) productTypeListValue.toArray(new String[0]);
		Arrays.sort(tempProductTypeListValue);
		productTypeListValue = Arrays.asList(tempProductTypeListValue);

		for (int i = 0; i < tempProductTypeListValue.length; i++) {
			productTypeListID.add(tempProductTypeListMap.get(tempProductTypeListValue[i]));
		}

	}

	public Collection getProductTypeListID() {
		return productTypeListID;
	}

	public Collection getProductTypeListValue() {
		return productTypeListValue;
	}

	public String getProductTypeItem(String key) {
		if (!productTypeListMap.isEmpty()) {
			return (String) productTypeListMap.get(key);
		}
		return "";
	}

}
