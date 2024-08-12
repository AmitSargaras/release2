/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetspecvehicles/VehicleTypeList.java,v 1.3 2003/09/29 12:04:11 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetspecvehicles;

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
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/29 12:04:11 $ Tag: $Name: $
 */
public class VehicleTypeList extends BaseList { // ------

	private static HashMap vehicleTypeMap;

	private static Collection vehicleTypeID;

	private static Collection vehicleTypeValue;

	private static Date createdDate;

	private static VehicleTypeList thisInstance;

	public synchronized static VehicleTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new VehicleTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new VehicleTypeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private VehicleTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		vehicleTypeID = new ArrayList();
		vehicleTypeValue = new ArrayList();
		vehicleTypeMap = new HashMap();
		HashMap tempVehicleTypeMap = new HashMap();
		vehicleTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.VEHICLE_TYPE);
		DefaultLogger.debug(this, "<<<<< ----- Vehicle type map size: " + vehicleTypeMap.size());
		Collection keyvalue = vehicleTypeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempVehicleTypeMap.put(vehicleTypeMap.get(key), key);
			// vehicleTypeID.add(key);
			vehicleTypeValue.add(vehicleTypeMap.get(key));
		}
		String[] tempVehicleTypeValue = (String[]) vehicleTypeValue.toArray(new String[0]);
		Arrays.sort(tempVehicleTypeValue);
		vehicleTypeValue = Arrays.asList(tempVehicleTypeValue);
		for (int i = 0; i < tempVehicleTypeValue.length; i++) {
			vehicleTypeID.add(tempVehicleTypeMap.get(tempVehicleTypeValue[i]));
		}

	}

	public Collection getVehicleTypeID() {
		return vehicleTypeID;
	}

	public Collection getVehicleTypeValue() {
		return vehicleTypeValue;
	}

	public String getVehicleTypeItem(String key) {
		if (!vehicleTypeMap.isEmpty()) {
			return (String) vehicleTypeMap.get(key);
		}
		return "";
	}
}
