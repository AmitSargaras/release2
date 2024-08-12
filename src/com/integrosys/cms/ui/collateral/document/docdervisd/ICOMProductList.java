/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/docdervisd/ICOMProductList.java,v 1.1 2003/09/10 03:03:43 hshii Exp $
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
public class ICOMProductList extends BaseList { // ------

	private static ArrayList iCOMProductListID;

	private static ArrayList iCOMProductListValue;

	private static HashMap iCOMProductListMap;

	private static Date createdDate;

	private static ICOMProductList thisInstance;

	public synchronized static ICOMProductList getInstance() {
		if (thisInstance == null) {
			thisInstance = new ICOMProductList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new ICOMProductList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private ICOMProductList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		iCOMProductListID = new ArrayList();
		iCOMProductListValue = new ArrayList();
		iCOMProductListMap = new HashMap();
		iCOMProductListMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.ICOM_PRODUCT);
		DefaultLogger.debug(this, "<<<<< ----- ICOM map size: " + iCOMProductListMap.size());
		Collection keyvalue = iCOMProductListMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			iCOMProductListID.add(key);
			iCOMProductListValue.add(iCOMProductListMap.get(key));
		}

	}

	public Collection getICOMProductListID() {
		return iCOMProductListID;
	}

	public Collection getICOMProductListValue() {
		return iCOMProductListValue;
	}

	public String getICOMProductItem(String key) {
		if (!iCOMProductListMap.isEmpty()) {
			return (String) iCOMProductListMap.get(key);
		}
		return "";
	}

}
