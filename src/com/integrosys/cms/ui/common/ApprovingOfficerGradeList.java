/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/ApprovingOfficerGradeList.java,v 1.2 2003/08/20 09:44:39 pooja Exp $
 */
package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author $Author: pooja $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/20 09:44:39 $ Tag: $Name: $
 */
public class ApprovingOfficerGradeList {

	private static HashMap approvingOfficerGradeMap;

	private static Collection approvingOfficerGradeListID; // type of invoice
															// code, and type of
															// invoice name

	private static Collection approvingOfficerGradeListValue;

	private static ApprovingOfficerGradeList thisInstance;

	public synchronized static ApprovingOfficerGradeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new ApprovingOfficerGradeList();
		}
		return thisInstance;
	}

	private ApprovingOfficerGradeList() {
		approvingOfficerGradeListID = new ArrayList();
		approvingOfficerGradeListID.add("11");
		approvingOfficerGradeListID.add("10");
		approvingOfficerGradeListID.add("9");
		approvingOfficerGradeListID.add("8");
		approvingOfficerGradeListID.add("7");
		approvingOfficerGradeListID.add("6");
		approvingOfficerGradeListID.add("5");
		approvingOfficerGradeListID.add("4");
		approvingOfficerGradeListID.add("3");
		approvingOfficerGradeListID.add("2");
		approvingOfficerGradeListID.add("1");

		approvingOfficerGradeListValue = new ArrayList();
		approvingOfficerGradeListValue.add("11");
		approvingOfficerGradeListValue.add("10");
		approvingOfficerGradeListValue.add("9");
		approvingOfficerGradeListValue.add("8");
		approvingOfficerGradeListValue.add("7");
		approvingOfficerGradeListValue.add("6");
		approvingOfficerGradeListValue.add("5");
		approvingOfficerGradeListValue.add("4");
		approvingOfficerGradeListValue.add("3");
		approvingOfficerGradeListValue.add("2");
		approvingOfficerGradeListValue.add("1");

		approvingOfficerGradeMap = new HashMap();
		approvingOfficerGradeMap.put("11", "11");
		approvingOfficerGradeMap.put("10", "10");
		approvingOfficerGradeMap.put("9", "9");
		approvingOfficerGradeMap.put("8", "8");
		approvingOfficerGradeMap.put("7", "7");
		approvingOfficerGradeMap.put("6", "6");
		approvingOfficerGradeMap.put("5", "5");
		approvingOfficerGradeMap.put("4", "4");
		approvingOfficerGradeMap.put("3", "3");
		approvingOfficerGradeMap.put("2", "2");
		approvingOfficerGradeMap.put("1", "1");

	}

	public Collection getApprovingOfficerGradeListID() {
		return approvingOfficerGradeListID;
	}

	public Collection getApprovingOfficerGradeListValue() {
		return approvingOfficerGradeListValue;
	}

	public String getApprovingOfficerGradeItem(String key) {
		if (!approvingOfficerGradeMap.isEmpty()) {
			return (String) approvingOfficerGradeMap.get(key);
		}
		return "";
	}
}
