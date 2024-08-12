/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/NatureOfAssignmentList.java,v 1.3 2003/09/29 12:04:11 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased;

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
public class NatureOfAssignmentList extends BaseList { // ------

	private static HashMap natureOfAssignmentMap;

	private static Collection natureOfAssignmentListID; // nature of Assignment
														// code, and nature of
														// Assignment name

	private static Collection natureOfAssignmentListValue;

	private static Date createdDate;

	private static NatureOfAssignmentList thisInstance;

	public synchronized static NatureOfAssignmentList getInstance() {
		if (thisInstance == null) {
			thisInstance = new NatureOfAssignmentList();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				thisInstance = null;
				thisInstance = new NatureOfAssignmentList();
				// setLastDate(current);
			}
		}
		// ------

		return thisInstance;
	}

	private NatureOfAssignmentList() {

		// ------
		super(); // call super class constructor to register a time stamp
		createdDate = super.getLastDate();
		// ------

		natureOfAssignmentListID = new ArrayList();
		natureOfAssignmentListValue = new ArrayList();
		natureOfAssignmentMap = new HashMap();
		HashMap tempNatureOfAssignmentMap = new HashMap();
		natureOfAssignmentMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.NAT_OF_ASS_ASSET);
		DefaultLogger.debug(this, "<<<<< ----- Nature of assignment map size: " + natureOfAssignmentMap.size());
		Collection keyvalue = natureOfAssignmentMap.keySet();
		Iterator itr1 = keyvalue.iterator();
		while (itr1.hasNext()) {
			String key = (String) itr1.next();
			tempNatureOfAssignmentMap.put(natureOfAssignmentMap.get(key), key);
			// natureOfAssignmentListID.add(key);
			natureOfAssignmentListValue.add(natureOfAssignmentMap.get(key));
		}
		String[] tempNatureOfAssignmentValue = (String[]) natureOfAssignmentListValue.toArray(new String[0]);
		Arrays.sort(tempNatureOfAssignmentValue);
		natureOfAssignmentListValue = Arrays.asList(tempNatureOfAssignmentValue);
		for (int i = 0; i < tempNatureOfAssignmentValue.length; i++) {
			natureOfAssignmentListID.add(tempNatureOfAssignmentMap.get(tempNatureOfAssignmentValue[i]));
		}

	}

	public Collection getNatureOfAssignmentListID() {
		return natureOfAssignmentListID;
	}

	public Collection getNatureOfAssignmentListValue() {
		return natureOfAssignmentListValue;
	}

	public String getNatureOfAssignmentItem(String key) {
		if (!natureOfAssignmentMap.isEmpty()) {
			return (String) natureOfAssignmentMap.get(key);
		}
		return "";
	}
}
