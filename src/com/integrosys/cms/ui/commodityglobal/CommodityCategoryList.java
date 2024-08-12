/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/CommodityCategoryList.java,v 1.5 2004/08/16 05:54:59 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.collateral.bus.OBCollateralType;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/08/16 05:54:59 $ Tag: $Name: $
 */
public class CommodityCategoryList extends BaseList { // ------
	private static Collection commCategoryID;

	private static Collection commCategoryValue;

	private static HashMap commCategoryMap;

	private static HashMap commProductCategoryMap;

	private static HashMap commProductMap;

	private static Date createdDate;

	private static CommodityCategoryList thisInstance;

	public synchronized static CommodityCategoryList getInstance() {
		if (thisInstance == null) {
			thisInstance = new CommodityCategoryList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new CommodityCategoryList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private CommodityCategoryList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		commCategoryID = new ArrayList();
		commCategoryValue = new ArrayList();
		commCategoryMap = new HashMap();
		commProductCategoryMap = new HashMap();
		commProductMap = new HashMap();

		try {
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			ICollateralType colType = new OBCollateralType();
			colType.setTypeCode(ICMSConstant.SECURITY_TYPE_COMMODITY);
			ICollateralSubType[] st = proxy.getCollateralSubTypeByType(colType);
			if (st != null) {
				for (int i = 0; i < st.length; i++) {
					commCategoryMap.put(st[i].getSubTypeCode(), st[i].getSubTypeName());
				}
			}
		}
		catch (Exception e) {
			// do nothing, just assume no subtypes available for commodity
		}

		// commCategoryMap =
		// CommonDataSingleton.getCodeCategoryValueLabelMap(CommodityConstant
		// .COMMODITY_CATEGORY_LIST_NAME);
		Collection keyvalue = commCategoryMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		HashMap tempCommCategoryMap = new HashMap();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempCommCategoryMap.put(commCategoryMap.get(key), key);
			/*
			 * commCategoryID.add(key);
			 */
			commCategoryValue.add(commCategoryMap.get(key));

			HashMap temp = new HashMap();
			HashMap tempIdValueMap = new HashMap();
			temp = CommonDataSingleton.getCodeCategoryValueLabelMap(key);
			Collection tempKeyVal = temp.keySet();
			Iterator tempItr = tempKeyVal.iterator();
			Collection tempID = new ArrayList();
			Collection tempValue = new ArrayList();
			HashMap tempSubTypeMap = new HashMap();
			while (tempItr.hasNext()) {
				String tempKey = (String) tempItr.next();
				tempSubTypeMap.put(temp.get(tempKey), tempKey);
				/*
				 * tempID.add(tempKey);
				 */
				tempValue.add(temp.get(tempKey));

				commProductMap.put(key + " " + tempKey, temp.get(tempKey));
			}
			String[] tempSubTypeValue = (String[]) tempValue.toArray(new String[0]);
			Arrays.sort(tempSubTypeValue);
			tempValue = Arrays.asList(tempSubTypeValue);
			for (int i = 0; i < tempSubTypeValue.length; i++) {
				tempID.add(tempSubTypeMap.get(tempSubTypeValue[i]));
			}
			tempIdValueMap.put("ID", tempID);
			tempIdValueMap.put("Value", tempValue);
			commProductCategoryMap.put(key, tempIdValueMap);
		}
		String[] tempProductValue = (String[]) commCategoryValue.toArray(new String[0]);
		Arrays.sort(tempProductValue);
		commCategoryValue = Arrays.asList(tempProductValue);
		for (int i = 0; i < tempProductValue.length; i++) {
			commCategoryID.add(tempCommCategoryMap.get(tempProductValue[i]));
		}

	}

	public Collection getCommCategoryID() {
		return commCategoryID;
	}

	public Collection getCommCategoryValue() {
		return commCategoryValue;
	}

	public String getCommCategoryItem(String key) {
		if (!commCategoryMap.isEmpty()) {
			return (String) commCategoryMap.get(key);
		}
		return "";
	}

	public Collection getCommProductID(String category) {
		if (!commProductCategoryMap.isEmpty()) {
			HashMap temp = (HashMap) commProductCategoryMap.get(category);
			if ((temp != null) && !temp.isEmpty()) {
				return (Collection) temp.get("ID");
			}
			return new ArrayList();
		}
		return new ArrayList();
	}

	public Collection getCommProductValue(String category) {
		if (!commProductCategoryMap.isEmpty()) {
			HashMap temp = (HashMap) commProductCategoryMap.get(category);
			if ((temp != null) && !temp.isEmpty()) {
				return (Collection) temp.get("Value");
			}
			return new ArrayList();
		}
		return new ArrayList();
	}

	public String getCommProductItem(String category, String productType) {
		if (!commProductMap.isEmpty()) {
			return (String) commProductMap.get(category + " " + productType);
		}
		return "";
	}
}
