package com.integrosys.cms.ui.common;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Created by IntelliJ IDEA. User: pooja Date: Jul 9, 2003 Time: 5:29:26 PM
 * 
 */
public class ApprovingOfficerList {

	private static HashMap approvingOfficerList; // currency code,

	private static ApprovingOfficerList thisInstance;

	public synchronized static ApprovingOfficerList getInstance() {
		if (thisInstance == null) {
			thisInstance = new ApprovingOfficerList();
		}
		return thisInstance;
	}

	private ApprovingOfficerList() {

	}

	public HashMap getApprovingOfficerList() {
		DefaultLogger.debug(this, "Entering getapprovingofficerList");
		if (approvingOfficerList == null) {
			approvingOfficerList = new HashMap();
		}
		class A {
			String a = null;

			String b = null;

			A() {
			}

			A(String x, String y) {
				a = x;
				b = y;
			}

		}
		A obj[] = new A[15];
		for (int i = 0; i < 15; i++) {
			obj[i] = new A(i + "", i + "");

			approvingOfficerList.put(obj[i].a, obj[i].b);
		}

		DefaultLogger.debug(this, "Exiting getCountryList");
		return approvingOfficerList;
	}
}