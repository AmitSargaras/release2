/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/InsuranceTypeList.java,v 1.1 2005/08/12 02:44:13 hshii Exp $
 */
package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/08/12 02:44:13 $ Tag: $Name: $
 */
public class InsuranceTypeList extends BaseList { // ------

	private static Collection insuranceTypeID;

	private static Collection insuranceTypeValue;

	private static HashMap insuranceTypeMap;

	private static Date createdDate;

	private static InsuranceTypeList thisInstance;

	public synchronized static InsuranceTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new InsuranceTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new InsuranceTypeList();
				// setLastDate(current);
			}
		}
		// ------

		return thisInstance;
	}

	private InsuranceTypeList() {

		// ------
		super(); // call super class constructor to register a time stamp
		createdDate = super.getLastDate();
		// ------

		insuranceTypeID = new ArrayList();
		insuranceTypeValue = new ArrayList();
		insuranceTypeMap = new HashMap();
		HashMap tempInsuranceTypeMap = new HashMap();

		insuranceTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.INSURANCE_TYPE);
		Collection keyvalue = insuranceTypeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempInsuranceTypeMap.put(insuranceTypeMap.get(key), key);
			insuranceTypeValue.add(insuranceTypeMap.get(key));
		}
		String[] tempInsuranceTypeValue = (String[]) insuranceTypeValue.toArray(new String[0]);
		Arrays.sort(tempInsuranceTypeValue);
		insuranceTypeValue = Arrays.asList(tempInsuranceTypeValue);
		for (int i = 0; i < tempInsuranceTypeValue.length; i++) {
			insuranceTypeID.add(tempInsuranceTypeMap.get(tempInsuranceTypeValue[i]));
		}

	}

	public Collection getInsuranceTypeID() {
		return insuranceTypeID;
	}

	public Collection getInsuranceTypeValue() {
		return insuranceTypeValue;
	}

	public String getInsuranceTypeItem(String key) {
		if (!insuranceTypeMap.isEmpty()) {
			return (String) insuranceTypeMap.get(key);
		}
		return "";
	}
}
