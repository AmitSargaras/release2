/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/dealinfo/PriceTypeList.java,v 1.5 2006/03/24 08:37:19 hmbao Exp $
 */
package com.integrosys.cms.ui.commoditydeal.dealinfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.app.commodity.deal.bus.PriceType;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/03/24 08:37:19 $ Tag: $Name: $
 */
public class PriceTypeList {
	public static String LIST_ALL = "ALL";

	public static String LIST_RIC_ONLY = "RIC_ONLY";

	public static String LIST_NON_RIC_ONLY = "NON_RIC_ONLY";

	private static HashMap priceTypeMap;

	private String listType = LIST_ALL;

	private ArrayList priceTypeIDList;

	private ArrayList priceTypeValueList;

	static {
		priceTypeMap = CommonDataSingleton
				.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_COMMODITY_DEAL_PRICE_TYPE);
	}

	public PriceTypeList(String type) {
		listType = type;
		load();
	}

	public PriceTypeList() {
		listType = LIST_ALL;
		load();
	}

	private void load() {
		priceTypeIDList = new ArrayList();
		priceTypeValueList = new ArrayList();
		Collection keyvalue = priceTypeMap.keySet();
		Iterator itr = keyvalue.iterator();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			if (needAdd(key)) {
				priceTypeIDList.add(key);
				priceTypeValueList.add(priceTypeMap.get(key));
			}
		}
	}

	private boolean needAdd(String priceType) {
		if (LIST_ALL.equals(listType)) {
			return true;
		}
		if (LIST_RIC_ONLY.equals(listType) && !isNonRic(priceType)) {
			return true;
		}
		if (LIST_NON_RIC_ONLY.equals(listType) && isNonRic(priceType)) {
			return true;
		}
		return false;
	}

	private boolean isNonRic(String priceType) {
		return PriceType.NON_RIC_PRICE.getName().equals(priceType)
				|| PriceType.MANUAL_NON_RIC_PRICE.getName().equals(priceType);
	}

	public Collection getPriceTypeID() {
		return priceTypeIDList;
	}

	public Collection getPriceTypeValue() {
		return priceTypeValueList;
	}

	public static String getPriceTypeItem(String key) {
		if (!priceTypeMap.isEmpty()) {
			return (String) priceTypeMap.get(key);
		}
		return "";
	}
}
