/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/FacilityTypeList.java,v 1.2 2004/06/04 05:22:37 hltan Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:22:37 $ Tag: $Name: $
 */
public class FacilityTypeList extends BaseList { // ------
	private static ArrayList facilityTypeID;

	private static ArrayList facilityTypeValue;

	private static HashMap facilityTypeMap;

	private static Date createdDate;

	private static FacilityTypeList thisInstance;

	public synchronized static FacilityTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new FacilityTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new FacilityTypeList();
				// setLastDate(current);
			}
		}
		// -----

		return thisInstance;
	}

	private FacilityTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		facilityTypeID = new ArrayList();
		facilityTypeValue = new ArrayList();
		facilityTypeMap = new HashMap();
		facilityTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CommodityMainConstant.FACILITY_TYPE);
		Collection keyvalue = facilityTypeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			facilityTypeID.add(key);
			facilityTypeValue.add(facilityTypeMap.get(key));
		}
	}

	public Collection getFacilityTypeID() {
		return facilityTypeID;
	}

	public Collection getFacilityTypeValue() {
		return facilityTypeValue;
	}

	public String getFacilityTypeItem(String key) {
		if (!facilityTypeMap.isEmpty()) {
			return (String) facilityTypeMap.get(key);
		}
		return "";
	}
}
