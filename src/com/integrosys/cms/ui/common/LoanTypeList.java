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
 * Describe this class. Purpose: to get loan type list from database
 * Description: class for Agreement details to get loan type list from database
 * 
 * @author $Author: Pctan$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name$
 */

public class LoanTypeList extends BaseList { // ------

	private static Collection loanTypeListID;

	private static Collection loanTypeListValue;

	private static HashMap loanTypeListMap;

	private static Date createdDate;

	private static LoanTypeList thisInstance;

	public synchronized static LoanTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new LoanTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new LoanTypeList();
				// setLastDate(current);
			}
		}
		// -----

		return thisInstance;
	}

	private LoanTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		loanTypeListID = new ArrayList();
		loanTypeListValue = new ArrayList();
		HashMap tempListMap = new HashMap();
		loanTypeListMap = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSUIConstant.LOAN_TYPE_CODE);

		Collection keyvalue = loanTypeListMap.keySet();
		Iterator itr1 = keyvalue.iterator();

		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempListMap.put(loanTypeListMap.get(key), key);
			loanTypeListValue.add(loanTypeListMap.get(key));
		}

		String[] tempListValue = (String[]) loanTypeListValue.toArray(new String[0]);
		Arrays.sort(tempListValue);
		loanTypeListValue = Arrays.asList(tempListValue);

		for (int i = 0; i < tempListValue.length; i++) {
			loanTypeListID.add(tempListMap.get(tempListValue[i]));
		}

	}

	public Collection getLoanTypeListID() {
		return loanTypeListID;
	}

	public Collection getLoanTypeListValue() {
		return loanTypeListValue;
	}

	public String getLoanTypeName(String key) {
		if (!loanTypeListMap.isEmpty()) {
			return (String) loanTypeListMap.get(key);
		}
		return "";
	}

}
