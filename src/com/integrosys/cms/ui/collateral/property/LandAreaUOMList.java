/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/property/LandAreaUOMList.java,v 1.3 2003/09/29 12:04:11 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.property;

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
public class LandAreaUOMList extends BaseList { // ------

	private static Collection landAreaUOMID;

	private static Collection landAreaUOMValue;

	private static HashMap landAreaUOMMap;

	private static Date createdDate;

	private static LandAreaUOMList thisInstance;

	public synchronized static LandAreaUOMList getInstance() {
		if (thisInstance == null) {
			thisInstance = new LandAreaUOMList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new LandAreaUOMList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private LandAreaUOMList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		landAreaUOMID = new ArrayList();
		landAreaUOMValue = new ArrayList();
		landAreaUOMMap = new HashMap();
		HashMap tempLandAreaUOMMap = new HashMap();
		landAreaUOMMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.AREA_UOM);
		DefaultLogger.debug(this, "<<<<< ----- Unit Of Measure size: " + landAreaUOMMap.size());
		Collection keyvalue = landAreaUOMMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempLandAreaUOMMap.put(landAreaUOMMap.get(key), key);
			// landAreaUOMID.add(key);
			landAreaUOMValue.add(landAreaUOMMap.get(key));
		}
		String[] tempLandAreaUOMValue = (String[]) landAreaUOMValue.toArray(new String[0]);
		Arrays.sort(tempLandAreaUOMValue);
		landAreaUOMValue = Arrays.asList(tempLandAreaUOMValue);
		for (int i = 0; i < tempLandAreaUOMValue.length; i++) {
			DefaultLogger.debug(this, "insurance name: " + tempLandAreaUOMValue[i]);
			landAreaUOMID.add(tempLandAreaUOMMap.get(tempLandAreaUOMValue[i]));
		}

	}

	public Collection getLandAreaUOMID() {
		return landAreaUOMID;
	}

	public Collection getLandAreaUOMValue() {
		return landAreaUOMValue;
	}

	public String getLandAreaUOMItem(String key) {
		if (!landAreaUOMMap.isEmpty()) {
			return (String) landAreaUOMMap.get(key);
		}
		return "";
	}
}
