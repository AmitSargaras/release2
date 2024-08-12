package com.integrosys.cms.app.discrepency.bus;

public interface IDiscrepencyConstant {

	String DEFAULT_REMARKS = "ROC charge is pending to be filed";
	String DEFAULT_DISCREPENCY_CODE = "ID";
	String DEFAULT_DISCREPENCY_TYPE = "General";
	String DEFAULT_DISCREPENCY_CRITICAL = "No";
	int DEFAULT_TARGET_DAYS = 30;
	
	String[] EXCLUDE_FOR_MAKER = {"getId", "getTransactionStatus", "getVersionTime"};
	String[] EXCLUDE_FOR_CHECKER_ACTUAL = {"getTransactionStatus", "getVersionTime"};
	String[] EXCLUDE_FOR_CHECKER_STAGING = {"getId", "getTransactionStatus", "getVersionTime", "getStatus"};
	
}
