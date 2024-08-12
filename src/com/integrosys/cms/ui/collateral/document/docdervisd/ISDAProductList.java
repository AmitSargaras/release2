/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/docdervisd/ISDAProductList.java,v 1.3 2003/09/02 11:24:45 hshii Exp $
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
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/02 11:24:45 $ Tag: $Name: $
 */
public class ISDAProductList extends BaseList { // ------

	private static ArrayList iSDAProductListID;

	private static ArrayList iSDAProductListValue;

	private static HashMap iSDAProductListMap;

	private static Date createdDate;

	private static ISDAProductList thisInstance;

	public synchronized static ISDAProductList getInstance() {
		if (thisInstance == null) {
			thisInstance = new ISDAProductList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new ISDAProductList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private ISDAProductList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		iSDAProductListID = new ArrayList();
		iSDAProductListValue = new ArrayList();
		iSDAProductListMap = new HashMap();
		iSDAProductListMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.ISDA_PRODUCT);
		DefaultLogger.debug(this, "<<<<< ----- Nature Of Charge map size: " + iSDAProductListMap.size());
		Collection keyvalue = iSDAProductListMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			iSDAProductListID.add(key);
			iSDAProductListValue.add(iSDAProductListMap.get(key));
		}

	}

	public Collection getISDAProductListID() {
		return iSDAProductListID;
	}

	public Collection getISDAProductListValue() {
		return iSDAProductListValue;
	}

	public String getISDAProductItem(String key) {
		if (!iSDAProductListMap.isEmpty()) {
			return (String) iSDAProductListMap.get(key);
		}
		return "";
	}

}
