/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/InterestRateTypeList.java,v 1 2007/03/15 Jerlin Exp $
 */
package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: for screen to get the interest rate type
 * Description: class that pull the interest rate type from database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/03/15$ Tag: $Name$
 */

public class InterestRateTypeList extends BaseList { // ------

	private static Collection interestRateTypeListID;

	private static Collection interestRateTypeListValue;

	private static HashMap interestRateTypeListMap;

	private static Date createdDate;

	private static InterestRateTypeList thisInstance;

	public synchronized static InterestRateTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new InterestRateTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new InterestRateTypeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private InterestRateTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		interestRateTypeListID = new ArrayList();
		interestRateTypeListValue = new ArrayList();
		HashMap tempInterestRateTypeListMap = new HashMap();
		interestRateTypeListMap = CommonDataSingleton
				.getCodeCategoryValueLabelMap(ICMSUIConstant.INTEREST_RATE_TYPE_CODE);
		DefaultLogger.debug(this, "<<<<< ----- Type of Interest Rate List map size: " + interestRateTypeListMap.size());
		Collection keyvalue = interestRateTypeListMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempInterestRateTypeListMap.put(interestRateTypeListMap.get(key), key);
			interestRateTypeListValue.add(interestRateTypeListMap.get(key));
		}
		String[] tempInterestRateTypeListValue = (String[]) interestRateTypeListValue.toArray(new String[0]);
		Arrays.sort(tempInterestRateTypeListValue);
		interestRateTypeListValue = Arrays.asList(tempInterestRateTypeListValue);
		for (int i = 0; i < tempInterestRateTypeListValue.length; i++) {
			interestRateTypeListID.add(tempInterestRateTypeListMap.get(tempInterestRateTypeListValue[i]));
		}
	}

	public Collection getInterestRateTypeListID() {
		return interestRateTypeListID;
	}

	public Collection getInterestRateTypeListValue() {
		return interestRateTypeListValue;
	}

	public String getInterestRateTypeName(String key) {
		if (!interestRateTypeListMap.isEmpty()) {
			return (String) interestRateTypeListMap.get(key);
		}
		return "";
	}

}
