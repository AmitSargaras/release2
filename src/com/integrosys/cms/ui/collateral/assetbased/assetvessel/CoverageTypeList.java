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
public class CoverageTypeList extends BaseList { // ------

	private static HashMap coverageTypeMap;

	private static Collection coverageTypeID;

	private static Collection coverageTypeValue;

	private static Date createdDate;

	private static CoverageTypeList thisInstance;

	public synchronized static CoverageTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new CoverageTypeList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new CoverageTypeList();
				// setLastDate(current);
			}
		}
		// -----
		return thisInstance;
	}

	private CoverageTypeList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		coverageTypeID = new ArrayList();
		coverageTypeValue = new ArrayList();
		coverageTypeMap = new HashMap();
		HashMap tempCoverageTypeMap = new HashMap();
		coverageTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.COVERAGE_TYPE);
		DefaultLogger.debug(this, "<<<<< ----- Plant Equip Type map size: " + coverageTypeMap.size());
		Collection keyvalue = coverageTypeMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempCoverageTypeMap.put(coverageTypeMap.get(key), key);
			// coverageTypeID.add(key);
			coverageTypeValue.add(coverageTypeMap.get(key));
		}
		String[] tempCoverageTypeValue = (String[]) coverageTypeValue.toArray(new String[0]);
		Arrays.sort(tempCoverageTypeValue);
		coverageTypeValue = Arrays.asList(tempCoverageTypeValue);
		for (int i = 0; i < tempCoverageTypeValue.length; i++) {
			coverageTypeID.add(tempCoverageTypeMap.get(tempCoverageTypeValue[i]));
		}

	}

	public Collection getCoverageTypeID() {
		return coverageTypeID;
	}

	public Collection getCoverageTypeValue() {
		return coverageTypeValue;
	}

	public String getCoverageTypeItem(String key) {
		if (!coverageTypeMap.isEmpty()) {
			return (String) coverageTypeMap.get(key);
		}
		return "";
	}
}
