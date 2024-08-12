/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/docdervisd/IFEMAProductList.java,v 1.1 2003/09/10 03:03:43 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.document.docdervisd;

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
 * @since $Date: 2003/09/10 03:03:43 $ Tag: $Name: $
 */
public class IFEMAProductList extends BaseList { // ------

	private static ArrayList iFEMAProductListID;

	private static ArrayList iFEMAProductListValue;

	private static HashMap iFEMAProductListMap;

	private static Date createdDate;

	private static IFEMAProductList thisInstance;

	public synchronized static IFEMAProductList getInstance() {
		if (thisInstance == null) {
			thisInstance = new IFEMAProductList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new IFEMAProductList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private IFEMAProductList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		iFEMAProductListID = new ArrayList();
		iFEMAProductListValue = new ArrayList();
		iFEMAProductListMap = new HashMap();
		iFEMAProductListMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.IFEMA_PRODUCT);
		DefaultLogger.debug(this, "<<<<< ----- IFEMA map size: " + iFEMAProductListMap.size());
		Collection keyvalue = iFEMAProductListMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			iFEMAProductListID.add(key);
			iFEMAProductListValue.add(iFEMAProductListMap.get(key));
		}

	}

	public Collection getIFEMAProductListID() {
		return iFEMAProductListID;
	}

	public Collection getIFEMAProductListValue() {
		return iFEMAProductListValue;
	}

	public String getIFEMAProductItem(String key) {
		if (!iFEMAProductListMap.isEmpty()) {
			return (String) iFEMAProductListMap.get(key);
		}
		return "";
	}

}
