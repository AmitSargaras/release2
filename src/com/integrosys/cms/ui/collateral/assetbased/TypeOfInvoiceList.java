/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/TypeOfInvoiceList.java,v 1.3 2003/09/29 12:04:11 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased;

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
public class TypeOfInvoiceList extends BaseList { // ------

	private static HashMap typeOfInvoiceMap;

	private static Collection typeOfInvoiceListID; // type of invoice code, and
													// type of invoice name

	private static Collection typeOfInvoiceListValue;

	private static Date createdDate;

	private static TypeOfInvoiceList thisInstance;

	public synchronized static TypeOfInvoiceList getInstance() {
		if (thisInstance == null) {
			thisInstance = new TypeOfInvoiceList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new TypeOfInvoiceList();
				// setLastDate(current);
			}
		}
		// ------

		return thisInstance;
	}

	private TypeOfInvoiceList() {

		// ------
		super(); // call super class constructor to register a time stamp
		createdDate = super.getLastDate();
		// ------

		typeOfInvoiceListID = new ArrayList();
		typeOfInvoiceListValue = new ArrayList();
		typeOfInvoiceMap = new HashMap();
		HashMap tempTypeOfInvoiceMap = new HashMap();
		typeOfInvoiceMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.INVOICE_TYPE);
		DefaultLogger.debug(this, "<<<<< ----- Invoice Type map size: " + typeOfInvoiceMap.size());
		Collection keyvalue = typeOfInvoiceMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempTypeOfInvoiceMap.put(typeOfInvoiceMap.get(key), key);
			// typeOfInvoiceListID.add(key);
			typeOfInvoiceListValue.add(typeOfInvoiceMap.get(key));
		}
		String[] tempTypeOfInvoiceValue = (String[]) typeOfInvoiceListValue.toArray(new String[0]);
		Arrays.sort(tempTypeOfInvoiceValue);
		typeOfInvoiceListValue = Arrays.asList(tempTypeOfInvoiceValue);
		for (int i = 0; i < tempTypeOfInvoiceValue.length; i++) {
			typeOfInvoiceListID.add(tempTypeOfInvoiceMap.get(tempTypeOfInvoiceValue[i]));
		}

	}

	public Collection getTypeOfInvoiceListID() {
		return typeOfInvoiceListID;
	}

	public Collection getTypeOfInvoiceListValue() {
		return typeOfInvoiceListValue;
	}

	public String getTypeOfInvoiceItem(String key) {
		if (!typeOfInvoiceMap.isEmpty()) {
			return (String) typeOfInvoiceMap.get(key);
		}
		return "";
	}
}
