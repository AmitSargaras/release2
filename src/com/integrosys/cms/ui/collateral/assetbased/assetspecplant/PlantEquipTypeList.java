/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetspecplant/PlantEquipTypeList.java,v 1.3 2003/09/29 12:04:11 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetspecplant;

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
public class PlantEquipTypeList extends BaseList { // ------

	private static HashMap plantEquipTypeMap;

	private static Collection plantEquipTypeID;

	private static Collection plantEquipTypeValue;

	private static Date createdDate;

	private static PlantEquipTypeList thisInstance;

	public synchronized static PlantEquipTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new PlantEquipTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new PlantEquipTypeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private PlantEquipTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		plantEquipTypeID = new ArrayList();
		plantEquipTypeValue = new ArrayList();
		plantEquipTypeMap = new HashMap();
		HashMap tempPlantEquipTypeMap = new HashMap();
		plantEquipTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.PLANT_EQUIP_TYPE);
		DefaultLogger.debug(this, "<<<<< ----- Plant Equip Type map size: " + plantEquipTypeMap.size());
		Collection keyvalue = plantEquipTypeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempPlantEquipTypeMap.put(plantEquipTypeMap.get(key), key);
			// plantEquipTypeID.add(key);
			plantEquipTypeValue.add(plantEquipTypeMap.get(key));
		}
		String[] tempPlantEquipTypeValue = (String[]) plantEquipTypeValue.toArray(new String[0]);
		Arrays.sort(tempPlantEquipTypeValue);
		plantEquipTypeValue = Arrays.asList(tempPlantEquipTypeValue);
		for (int i = 0; i < tempPlantEquipTypeValue.length; i++) {
			plantEquipTypeID.add(tempPlantEquipTypeMap.get(tempPlantEquipTypeValue[i]));
		}

	}

	public Collection getPlantEquipTypeID() {
		return plantEquipTypeID;
	}

	public Collection getPlantEquipTypeValue() {
		return plantEquipTypeValue;
	}

	public String getPlantEquipTypeItem(String key) {
		if (!plantEquipTypeMap.isEmpty()) {
			return (String) plantEquipTypeMap.get(key);
		}
		return "";
	}
}
