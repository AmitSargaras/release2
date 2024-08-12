package com.integrosys.cms.ui.collateral.assetbased.assetvessel;

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
 * @author $Author: Naveen $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 2007/03/02 12:04:11 $ Tag: $Name: $
 */
public class VesselTypeList extends BaseList { // ------

	private static HashMap vesselTypeMap;

	private static Collection vesselTypeID;

	private static Collection vesselTypeValue;

	private static Date createdDate;

	private static VesselTypeList thisInstance;

	public synchronized static VesselTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new VesselTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new VesselTypeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private VesselTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		vesselTypeID = new ArrayList();
		vesselTypeValue = new ArrayList();
		vesselTypeMap = new HashMap();
		HashMap tempVesselTypeMap = new HashMap();
		vesselTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.VESSEL_TYPE);
		DefaultLogger.debug(this, "<<<<< ----- Vessel type map size: " + vesselTypeMap.size());
		Collection keyvalue = vesselTypeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempVesselTypeMap.put(vesselTypeMap.get(key), key);
//			vesselTypeID.add(key);
			vesselTypeValue.add(vesselTypeMap.get(key));
		}
		String[] tempVesselTypeValue = (String[]) vesselTypeValue.toArray(new String[0]);
		Arrays.sort(tempVesselTypeValue);
		vesselTypeValue = Arrays.asList(tempVesselTypeValue);
		for (int i = 0; i < tempVesselTypeValue.length; i++) {
			vesselTypeID.add(tempVesselTypeMap.get(tempVesselTypeValue[i]));
		}

	}

	public Collection getVesselTypeID() {
		return vesselTypeID;
	}

	public Collection getVesselTypeValue() {
		return vesselTypeValue;
	}

	public String getVesselTypeItem(String key) {
		if (!vesselTypeMap.isEmpty()) {
			return (String) vesselTypeMap.get(key);
		}
		return "";
	}
}
