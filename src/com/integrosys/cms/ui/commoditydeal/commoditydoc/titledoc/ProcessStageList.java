/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/ProcessStageList.java,v 1.2 2004/06/04 05:05:58 hltan Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:05:58 $ Tag: $Name: $
 */
public class ProcessStageList extends BaseList { // ------
	private static ArrayList processStageID;

	private static ArrayList processStageValue;

	private static HashMap processStageMap;

	private static Date createdDate;

	private static ProcessStageList thisInstance;

	public synchronized static ProcessStageList getInstance() {
		if (thisInstance == null) {
			thisInstance = new ProcessStageList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new ProcessStageList();
				// setLastDate(current);
			}
		}
		// -----

		return thisInstance;
	}

	private ProcessStageList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		processStageID = new ArrayList();
		processStageValue = new ArrayList();
		processStageMap = new HashMap();
		processStageMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CommodityDealConstant.PROCESS_STAGE);
		Collection keyvalue = processStageMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			processStageID.add(key);
			processStageValue.add(processStageMap.get(key));
		}
	}

	public Collection getProcessStageID() {
		return processStageID;
	}

	public Collection getProcessStageValue() {
		return processStageValue;
	}

	public String getProcessStageItem(String key) {
		if (!processStageMap.isEmpty()) {
			return (String) processStageMap.get(key);
		}
		return "";
	}
}
