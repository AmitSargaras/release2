/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityuom/MarketUOMList.java,v 1.3 2004/10/20 08:55:31 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityuom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/10/20 08:55:31 $ Tag: $Name: $
 */
public class MarketUOMList extends BaseList { // ------
	private static ArrayList marketUOMID;

	private static ArrayList marketUOMValue;

	private static ArrayList metricUOMID;

	private static ArrayList metricUOMValue;

	private static HashMap marketUOMMap;

	private static Date createdDate;

	private static MarketUOMList thisInstance;

	public synchronized static MarketUOMList getInstance() {
		if (thisInstance == null) {
			thisInstance = new MarketUOMList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new MarketUOMList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private MarketUOMList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		marketUOMID = new ArrayList();
		marketUOMValue = new ArrayList();
		metricUOMID = new ArrayList();
		metricUOMValue = new ArrayList();
		marketUOMMap = new HashMap();
		TreeMap tmpMarketUOMMap = new TreeMap(new Comparator() {
			public int compare(Object obj1, Object obj2) {
				String str1 = (String) obj1;
				String str2 = (String) obj2;
				return str1.compareToIgnoreCase(str2);
			}
		});
		tmpMarketUOMMap.putAll(CommonDataSingleton
				.getCodeCategoryLabelValueMap(ICMSConstant.CATEGORY_COMMODITY_METRIC_MARKET_UOM));

		Collection labelValue = tmpMarketUOMMap.keySet();
		Iterator itr1 = labelValue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			metricUOMValue.add(key);
			metricUOMID.add(tmpMarketUOMMap.get(key));
		}

		TreeMap temp = tmpMarketUOMMap;
		temp.putAll(CommonDataSingleton.getCodeCategoryLabelValueMap(ICMSConstant.CATEGORY_COMMODITY_OTHER_MARKET_UOM));
		labelValue = temp.keySet();
		itr1 = labelValue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			marketUOMMap.put(temp.get(key), key);
			marketUOMValue.add(key);
			marketUOMID.add(temp.get(key));
		}
	}

	public Collection getMetricUOMID() {
		return metricUOMID;
	}

	public Collection getMetricUOMValue() {
		return metricUOMValue;
	}

	public Collection getMarketUOMID() {
		return marketUOMID;
	}

	public Collection getMarketUOMValue() {
		return marketUOMValue;
	}

	public String getMarketUOMItem(String key) {
		if (!marketUOMMap.isEmpty()) {
			return (String) marketUOMMap.get(key);
		}
		return "";
	}
}
