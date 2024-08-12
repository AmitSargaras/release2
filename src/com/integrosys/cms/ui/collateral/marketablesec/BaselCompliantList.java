/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/marketablesec/BaselCompliantList.java,v 1.1 2003/10/16 11:04:13 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.marketablesec;

import java.util.ArrayList;
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
 * @version $Revision: 1.1 $
 * @since $Date: 2003/10/16 11:04:13 $ Tag: $Name: $
 */
public class BaselCompliantList extends BaseList { // ------

	private static ArrayList baselCompliantID;

	private static ArrayList baselCompliantValue;

	private static HashMap baselCompliantMap;

	private static Date createdDate;

	private static BaselCompliantList thisInstance;

	public synchronized static BaselCompliantList getInstance() {
		if (thisInstance == null) {
			thisInstance = new BaselCompliantList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new BaselCompliantList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private BaselCompliantList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		baselCompliantID = new ArrayList();
		baselCompliantValue = new ArrayList();
		baselCompliantMap = new HashMap();
		baselCompliantMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.EXCHANGE_CONTROL);
		DefaultLogger.debug(this, "<<<<< ----- Basel Compliant map size: " + baselCompliantMap.size());
		Collection keyvalue = baselCompliantMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			baselCompliantID.add(key);
			baselCompliantValue.add(baselCompliantMap.get(key));
		}
	}

	public Collection getBaselCompliantID() {
		return baselCompliantID;
	}

	public Collection getBaselCompliantValue() {
		return baselCompliantValue;
	}

	public String getBaselCompliantItem(String key) {
		if (!baselCompliantMap.isEmpty()) {
			return (String) baselCompliantMap.get(key);
		}
		return "";
	}
}
