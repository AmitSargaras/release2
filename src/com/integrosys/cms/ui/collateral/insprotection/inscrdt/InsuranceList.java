/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/inscrdt/InsuranceList.java,v 1.4 2003/09/29 12:04:11 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.insprotection.inscrdt;

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
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/29 12:04:11 $ Tag: $Name: $
 */
public class InsuranceList extends BaseList { // ------

	private static Collection insuranceListID;

	private static Collection insuranceListValue;

	private static HashMap insuranceListMap;

	private static Date createdDate;

	private static InsuranceList thisInstance;

	public synchronized static InsuranceList getInstance() {
		if (thisInstance == null) {
			thisInstance = new InsuranceList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new InsuranceList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private InsuranceList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		insuranceListID = new ArrayList();
		insuranceListValue = new ArrayList();
		insuranceListMap = new HashMap();
		HashMap tempInsuranceListMap = new HashMap();
		insuranceListMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.INSURANCE);
		DefaultLogger.debug(this, "<<<<< ----- Insurance List map size: " + insuranceListMap.size());
		Collection keyvalue = insuranceListMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempInsuranceListMap.put(insuranceListMap.get(key), key);
			// insuranceListID.add(key);
			insuranceListValue.add(insuranceListMap.get(key));
		}
		String[] tempInsuranceListValue = (String[]) insuranceListValue.toArray(new String[0]);
		Arrays.sort(tempInsuranceListValue);
		insuranceListValue = Arrays.asList(tempInsuranceListValue);
		for (int i = 0; i < tempInsuranceListValue.length; i++) {
			insuranceListID.add(tempInsuranceListMap.get(tempInsuranceListValue[i]));
		}
	}

	public Collection getInsuranceListID() {
		return insuranceListID;
	}

	public Collection getInsuranceListValue() {
		return insuranceListValue;
	}

	public String getInsuranceItem(String key) {
		if (!insuranceListMap.isEmpty()) {
			return (String) insuranceListMap.get(key);
		}
		return "";
	}

}
