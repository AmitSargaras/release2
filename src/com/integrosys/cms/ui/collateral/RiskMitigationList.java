/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/RiskMitigationList.java,v 1.1 2007/02/22 Jerlin Exp $
 */
package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: Jerlin $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/02/22 $ Tag: $Name: $
 */
public class RiskMitigationList extends BaseList { // ------

	private static ArrayList riskMitigationID;

	private static ArrayList riskMitigationValue;

	private static HashMap riskMitigationMap;

	private static Date createdDate;

	private static RiskMitigationList thisInstance;

	public synchronized static RiskMitigationList getInstance() {

		if (thisInstance == null) {
			thisInstance = new RiskMitigationList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new RiskMitigationList();
				// setLastDate(current);
			}
		}
		// ------

		return thisInstance;
	}

	private RiskMitigationList() {

		// ------
		super(); // call super class constructor to register a time stamp
		createdDate = super.getLastDate();
		// ------

		riskMitigationID = new ArrayList();
		riskMitigationValue = new ArrayList();
		riskMitigationMap = new HashMap();
		riskMitigationMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.RISK_MITIGATION);
		DefaultLogger.debug(this, "<<<<< ----- Risk Mitigation map size: " + riskMitigationMap.size());

		Collection keyvalue = riskMitigationMap.keySet();

		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {

			String key = (String) itr1.next();
			riskMitigationID.add(key);
			riskMitigationValue.add(riskMitigationMap.get(key));
		}
	}

	public Collection getRiskMitigationID() {
		return riskMitigationID;
	}

	public Collection getRiskMitigationValue() {
		return riskMitigationValue;
	}

	public String getRiskMitigationItem(String key) {

		if (!riskMitigationMap.isEmpty()) {
			return (String) riskMitigationMap.get(key);
		}

		return "";
	}

}
