/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetaircraft/ExportCrdtAgencyList.java,v 1.3 2003/09/29 12:04:11 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetaircraft;

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
public class ExportCrdtAgencyList extends BaseList { // ------

	private static HashMap exportCrdtAgencyMap;

	private static Collection exportCrdtAgencyID;

	private static Collection exportCrdtAgencyValue;

	private static Date createdDate;

	private static ExportCrdtAgencyList thisInstance;

	public synchronized static ExportCrdtAgencyList getInstance() {
		if (thisInstance == null) {
			thisInstance = new ExportCrdtAgencyList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new ExportCrdtAgencyList();
				// setLastDate(current);
			}
		}
		// ------

		return thisInstance;
	}

	private ExportCrdtAgencyList() {

		// ------
		super(); // call super class constructor to register a time stamp
		createdDate = super.getLastDate();
		// ------

		exportCrdtAgencyID = new ArrayList();
		exportCrdtAgencyValue = new ArrayList();
		exportCrdtAgencyMap = new HashMap();
		HashMap tempExportCrdtAgencyMap = new HashMap();
		exportCrdtAgencyMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.ECA);
		DefaultLogger.debug(this, "<<<<< ----- Export Credit Agency map size: " + exportCrdtAgencyMap.size());
		Collection keyvalue = exportCrdtAgencyMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempExportCrdtAgencyMap.put(exportCrdtAgencyMap.get(key), key);
			// exportCrdtAgencyID.add(key);
			exportCrdtAgencyValue.add(exportCrdtAgencyMap.get(key));
		}
		String[] tempExportCrdtAgencyValue = (String[]) exportCrdtAgencyValue.toArray(new String[0]);
		Arrays.sort(tempExportCrdtAgencyValue);
		exportCrdtAgencyValue = Arrays.asList(tempExportCrdtAgencyValue);
		for (int i = 0; i < tempExportCrdtAgencyValue.length; i++) {
			exportCrdtAgencyID.add(tempExportCrdtAgencyMap.get(tempExportCrdtAgencyValue[i]));
		}

	}

	public Collection getExportCrdtAgencyID() {
		return exportCrdtAgencyID;
	}

	public Collection getExportCrdtAgencyValue() {
		return exportCrdtAgencyValue;
	}

	public String getExportCrdtAgencyItem(String key) {
		if (!exportCrdtAgencyMap.isEmpty()) {
			return (String) exportCrdtAgencyMap.get(key);
		}
		return "";
	}
}
