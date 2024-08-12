/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/common/constant/ICMSErrorCodes.java,v 1.22 2006/11/15 12:49:22 jychong Exp $
 */
package com.integrosys.cms.app.common.constant;

/**
 * This interface contains the list of error codes used in CMS
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.22 $
 * @since $Date: 2006/11/15 12:49:22 $ Tag: $Name: $
 */
public interface ICMSErrorCodes {
	/************ CCC Related Error Codes **************/
	public static final String CCC_BFL_NOT_ISSUED = "CCC_ERR0001";

	/************ SCC Related Error Codes **************/
	public static final String SCC_COLLATERAL_NOT_PERFECTED = "SCC_ERR0001";

	public static final String SCC_CHECKLIST_NOT_PERFECTED = "SCC_ERR0002";

	public static final String SCC_NOT_REQUIRED = "SCC_ERR0003";

	public static final String SCC_NO_CHECKLIST_FOUND = "SCC_ERR0004";

	public static final String SCC_BFL_NOT_ISSUED = "SCC_ERR0005"; // No longer
																	// in use

	public static final String SCC_CCC_NOT_PERFECTED = "SCC_ERR0006";

	public static final String PSCC_COLLATERAL_NOT_PERFECTED = "PSCC_ERR0001";

	public static final String PSCC_CHECKLIST_NOT_PERFECTED = "PSCC_ERR0002";

	public static final String PSCC_NOT_REQUIRED = "PSCC_ERR0003";

	public static final String PSCC_NOT_ALLOWED = "PSCC_ERR0004";

	public static final String PSCC_CCC_NOT_PERFECTED = "PSCC_ERR0005";

	public static final String PSCC_NOT_APPLICABLE_CLEAN_BCA = "PSCC_ERR0006";

	public static final String PSCC_NO_LIMITS = "PSCC_ERR0007";

	public static final String PSCC_CAN_GENERATE_SCC = "PSCC_ERR0008";

    public static final String PSCC_CONDITION_NOT_MEET = "PSCC_ERR0009";

	public static final String DDN_NOT_ALLOWED = "PSCC_ERR0004";

	/************ Common Error Codes *******************/
	public static final String CONCURRENT_UPDATE = "COMMON_ERR0001";

	/*********** Access Control (DDAP) Error Codes ***********/
	public static final String DDAP_SETUP_ERROR = "DDAP_ERR0001";

	public static final String DDAP_TAT_NOT_CREATED = "DDAP_ERR0002";

	public static final String DDAP_LIMIT_NOT_FOUND = "DDAP_ERR0003";

	public static final String DDAP_NO_ACCESS = "DDAP_ERR0004";

    public static final String DDAP_DIFF_COUNTRY = "DDAP_ERR0005";

    /*********** Access Control (FAP) Error Codes *************/
	public static final String FAP_NO_ACCESS = "FAP_ERR0001";

	/*********** Transaction Context Violation Error Codes ************/
	public static final String TRX_CONTEXT_VIOLATION_ERROR = "TRX_ERR0001";

	/*********** BFL Error Codes ***********/
	public static final String BFL_SPECIAL_ISSUE_NOT_ALLOWED = "BFL_ERR0001";

	public static final String BFL_SEND_DRAFT_NOT_ALLOWED = "BFL_ERR0002";

	public static final String BFL_ACK_REC_DRAFT_NOT_ALLOWED = "BFL_ERR0003";

	public static final String BFL_ISSUE_CLEAN_NOT_ALLOWED = "BFL_ERR0004";

	public static final String BFL_ISSUE_FINAL_NOT_ALLOWED = "BFL_ERR0005";

	public static final String BFL_CUSTOMER_ACCEPT_NOT_ALLOWED = "BFL_ERR0006";

	public static final String BFL_COMPLETE_BCA_NOT_ALLOWED = "BFL_ERR0007";

	public static final String BFL_ISSUE_DRAFT_NOT_ALLOWED = "BFL_ERR0008";

	public static final String BFL_UNKNOWN_TAT_CODE = "BFL_ERR1000";

	public static final String BFL_INVALID_FIRST_CODE = "BFL_ERR1001";

	public static final String BFL_ISSUE_NOT_REQUIRED = "BFL_ERR0010";

	/*********** CC Collaboration Task *********/
	public static final String CC_TASK_NOT_REQUIRED = "CCTASK_ERR001";

	public static final String COLLATERAL_TASK_NOT_REQUIRED = "COLTASK_ERR001";

	/*********** Waiver/Deferral Request Generation *************/
	public static final String WAIVER_PENDING_TRX_EXIST = "WAIVER_ERR001";

	public static final String WAIVER_NOT_REQUIRED = "WAIVER_ERR002";

	public static final String DEFERRAL_PENDING_TRX_EXIST = "DEFERRAL_ERR001";

	public static final String DEFERRAL_NOT_REQUIRED = "DEFERRAL_ERR002";

	/********** CheckList Templates ********************/
	public static final String GLOBAL_NOT_SETUP = "GLOBAL_ERR0001";

	/********** CheckList *****************************/
	public static final String NO_LEGAL_CONSTITUTION = "CHECKLIST_ERR0001";

	public static final String NO_INSTR_BKG_LOCATION = "CHECKLIST_ERR0002";

	/********* CC Document Location ******************/
	public static final String CC_DOC_LOC_NOT_REQUIRED = "DOCLOC_ERR0001";

	/********* DDN Related Errors ******************/
    public static final String DDN_NO_CHECKLIST_MAINTAIN = "DDN_ERR001";
    public static final String DDN_NO_DEFERRED_DOC = "DDN_ERR002";

}
