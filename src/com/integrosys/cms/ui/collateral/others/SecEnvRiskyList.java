/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/others/SecEnvRiskyList.java,v 1.1 2004/06/22 09:00:37 visveswari Exp $
 */
package com.integrosys.cms.ui.collateral.others;

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
 * @author $Author: visveswari $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/06/22 09:00:37 $ Tag: $Name: $
 */
public class SecEnvRiskyList extends BaseList { // ------

	private Collection secEnvRiskyID;

	private Collection secEnvRiskyValue;

	private HashMap secEnvRiskyMap;

	private static Date createdDate;

	private static SecEnvRiskyList thisInstance;

	public synchronized static SecEnvRiskyList getInstance() {
		if (thisInstance == null) {
			thisInstance = new SecEnvRiskyList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new SecEnvRiskyList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private SecEnvRiskyList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		secEnvRiskyID = new ArrayList();
		secEnvRiskyValue = new ArrayList();
		secEnvRiskyMap = new HashMap();
		// HashMap tempSecEnvRiskMap = new HashMap();
		secEnvRiskyMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.ENV_RISK_STATUS);
		DefaultLogger.debug(this, "<<<<< ----- Environmentally Risky List size: " + secEnvRiskyMap.size());
		Collection keyvalue = secEnvRiskyMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			// MBB-781 remove sorting to show NA first - Commented off the
			// sorting
			/*
			 * // secEnvRiskyID.add(key);
			 * tempSecEnvRiskMap.put(secEnvRiskyMap.get(key), key);
			 * secEnvRiskyValue.add(secEnvRiskyMap.get(key)); } String[]
			 * tempSecEnvRiskyValue = (String [])secEnvRiskyValue.toArray(new
			 * String[0]); Arrays.sort(tempSecEnvRiskyValue); secEnvRiskyValue =
			 * Arrays.asList(tempSecEnvRiskyValue); for (int i = 0; i <
			 * tempSecEnvRiskyValue.length; i++) {
			 * secEnvRiskyID.add(tempSecEnvRiskMap
			 * .get(tempSecEnvRiskyValue[i])); }
			 */

			secEnvRiskyID.add(key);
			secEnvRiskyValue.add(secEnvRiskyMap.get(key));
		}
	}

	public Collection getSecEnvRiskyID() {
		return secEnvRiskyID;
	}

	public Collection getSecEnvRiskyValue() {
		return secEnvRiskyValue;
	}

	public String getSecEnvRiskyItem(String key) {
		if (!secEnvRiskyMap.isEmpty()) {
			return (String) secEnvRiskyMap.get(key);
		}
		return "";
	}
}
