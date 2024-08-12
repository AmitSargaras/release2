/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.checklist.bus;

/**
 * This interface defines the constant specific to the checklist item table and
 * the methods required by the document
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/06/17 02:13:04 $ Tag: $Name: $
 */
public interface ICheckListItemTableConstants {
	public static final String CHECKLIST_ITEM_TABLE = "CMS_CHECKLIST_ITEM";

	public static final String STAGE_CHECKLIST_ITEM_TABLE = "STAGE_CHECKLIST_ITEM";

	public static final String ITMTBL_DOC_ITEM_NO = "DOC_ITEM_ID";

	public static final String ITMTBL_DOC_DESCRIPTION = "DOC_DESCRIPTION";

	public static final String ITMTBL_IN_VAULT = "IN_VAULT";

	public static final String ITMTBL_IN_EXT_CUSTODY = "IN_EXT_CUSTODY";

	public static final String ITMTBL_IS_MANDATORY = "IS_MANDATORY";

	public static final String ITMTBL_ACTION_PARTY = "ACTION_PARTY";

	public static final String ITMTBL_STATUS = "STATUS";

	public static final String ITMTBL_DOC_DATE = "DOC_DATE";

	public static final String ITMTBL_EXPIRY_DATE = "EXPIRY_DATE";

	public static final String ITMTBL_IS_AUDIT = "IS_AUDIT";

	public static final String ITMTBL_REMARKS = "REMARKS";

	public static final String ITMTBL_IS_DELETED = "IS_DELETED";

	public static final String ITMTBL_CHECKLIST_ID = "CHECKLIST_ID";

	public static final String ITMTBL_DOCUMENT_ID = "DOCUMENT_ID";

	public static final String ITMTBL_DOCUMENT_CODE = "DOCUMENT_CODE";

	public static final String ITMTBL_DOC_ITEM_REF = "DOC_ITEM_REF";

	public static final String ITMTBL_FORM_NO = "FORM_NO";

	public static final String ITMTBL_DOC_REF = "DOC_REFERENCE";

	public static final String ITMTBL_DEFER_EXPIRY_DATE = "DEFER_EXPIRY_DATE";

	public static final String ITMTBL_MONITOR_TYPE = "MONITOR_TYPE";

	public static final String ITMTBL_LAST_UPDATE_DATE = "LAST_UPDATE_DATE";

	public static final String ITMTBL_CPC_CUST_STATUS = "CPC_CUST_STATUS";

	public static final String ITMTBL_DOC_ITEM_NO_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_DOC_ITEM_NO;

	public static final String ITMTBL_DOC_DESCRIPTION_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_DOC_DESCRIPTION;

	public static final String ITMTBL_IN_VAULT_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_IN_VAULT;

	public static final String ITMTBL_IN_EXT_CUSTODY_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_IN_EXT_CUSTODY;

	public static final String ITMTBL_IS_MANDATORY_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_IS_MANDATORY;

	public static final String ITMTBL_ACTION_PARTY_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_ACTION_PARTY;

	public static final String ITMTBL_STATUS_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_STATUS;

	public static final String ITMTBL_DOC_DATE_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_DOC_DATE;

	public static final String ITMTBL_EXPIRY_DATE_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_EXPIRY_DATE;

	public static final String ITMTBL_IS_AUDIT_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_IS_AUDIT;

	public static final String ITMTBL_REMARKS_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_REMARKS;

	public static final String ITMTBL_IS_DELETED_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_IS_DELETED;

	public static final String ITMTBL_CHECKLIST_ID_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_CHECKLIST_ID;

	public static final String ITMTBL_DOCUMENT_ID_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_DOCUMENT_ID;

	public static final String ITMTBL_DOCUMENT_CODE_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_DOCUMENT_CODE;

	public static final String ITMTBL_DOC_ITEM_REF_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_DOC_ITEM_REF;

	public static final String ITMTBL_DOC_REF_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_DOC_REF;

	public static final String ITMTBL_DEFER_EXPIRY_DATE_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_DEFER_EXPIRY_DATE;

	public static final String ITMTBL_MONITOR_TYPE_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_MONITOR_TYPE;

	public static final String ITMTBL_LAST_UPDATE_DATE_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_LAST_UPDATE_DATE;

	public static final String ITMTBL_CPC_CUST_STATUS_PREF = CHECKLIST_ITEM_TABLE + "." + ITMTBL_CPC_CUST_STATUS;
}
