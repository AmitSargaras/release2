package com.integrosys.cms.ui.collateral.document.docdoa;

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

public class TypeOfAssignmentList extends BaseList { // ------

	private static Collection typeOfAssignmentListID;

	private static Collection typeOfAssignmentListValue;

	private static HashMap typeOfAssignmentListMap;

	private static Date createdDate;

	private static TypeOfAssignmentList thisInstance;

	public synchronized static TypeOfAssignmentList getInstance() {
		if (thisInstance == null) {
			thisInstance = new TypeOfAssignmentList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new TypeOfAssignmentList();
				// setLastDate(current);
			}
		}
		// -----

		return thisInstance;
	}

	private TypeOfAssignmentList() {

		// ------
		super();
		createdDate = super.getLastDate();
		// -----

		typeOfAssignmentListID = new ArrayList();
		typeOfAssignmentListValue = new ArrayList();
		HashMap tempTypeOfAssignmentListMap = new HashMap();
		typeOfAssignmentListMap = CommonDataSingleton
				.getCodeCategoryValueLabelMap(CategoryCodeConstant.Type_Of_Assignment);
		DefaultLogger.debug(this, "<<<<< ----- Type of Assignment List map size: " + typeOfAssignmentListMap.size());
		Collection keyvalue = typeOfAssignmentListMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempTypeOfAssignmentListMap.put(typeOfAssignmentListMap.get(key), key);
			typeOfAssignmentListValue.add(typeOfAssignmentListMap.get(key));
		}
		String[] tempTypeOfAssignmentListValue = (String[]) typeOfAssignmentListValue.toArray(new String[0]);
		Arrays.sort(tempTypeOfAssignmentListValue);
		typeOfAssignmentListValue = Arrays.asList(tempTypeOfAssignmentListValue);
		for (int i = 0; i < tempTypeOfAssignmentListValue.length; i++) {
			typeOfAssignmentListID.add(tempTypeOfAssignmentListMap.get(tempTypeOfAssignmentListValue[i]));
		}
	}

	public Collection getTypeOfAssignmentListID() {
		return typeOfAssignmentListID;
	}

	public Collection getTypeOfAssignmentListValue() {
		return typeOfAssignmentListValue;
	}

	public String getTypeOfAssignmentItem(String key) {
		if (!typeOfAssignmentListMap.isEmpty()) {
			return (String) typeOfAssignmentListMap.get(key);
		}
		return "";
	}

}
