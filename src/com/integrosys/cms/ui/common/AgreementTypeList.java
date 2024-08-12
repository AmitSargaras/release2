/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: to get agreement type list from database
 * Description: class for Threshold Rating to get agreement type list from
 * database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class AgreementTypeList extends BaseList { // ------

	private static Collection agreementTypeListID;

	private static Collection agreementTypeListValue;

	private static HashMap agreementTypeListMap;

	private static Date createdDate;

	private static AgreementTypeList thisInstance;

	public synchronized static AgreementTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new AgreementTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new AgreementTypeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private AgreementTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		agreementTypeListID = new ArrayList();
		agreementTypeListValue = new ArrayList();
		HashMap tempAgreementTypeListMap = new HashMap();
		agreementTypeListMap = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSUIConstant.AGREEMENT_TYPE_CODE);

		Collection keyvalue = agreementTypeListMap.keySet();
		Iterator itr1 = keyvalue.iterator();

		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempAgreementTypeListMap.put(agreementTypeListMap.get(key), key);
			agreementTypeListValue.add(agreementTypeListMap.get(key));
		}

		String[] tempAgreementTypeListValue = (String[]) agreementTypeListValue.toArray(new String[0]);
		Arrays.sort(tempAgreementTypeListValue);
		agreementTypeListValue = Arrays.asList(tempAgreementTypeListValue);

		for (int i = 0; i < tempAgreementTypeListValue.length; i++) {
			agreementTypeListID.add(tempAgreementTypeListMap.get(tempAgreementTypeListValue[i]));
		}

	}

	public Collection getAgreementTypeListID() {
		return agreementTypeListID;
	}

	public Collection getAgreementTypeListValue() {
		return agreementTypeListValue;
	}

	public String getAgreementTypeName(String key) {
		if (!agreementTypeListMap.isEmpty()) {
			return (String) agreementTypeListMap.get(key);
		}
		return "";
	}

}
