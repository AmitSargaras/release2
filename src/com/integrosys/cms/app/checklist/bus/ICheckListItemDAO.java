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
 * @version $Revision: 1.5 $
 * @since $Date: 2004/06/17 02:13:04 $ Tag: $Name: $
 */
public interface ICheckListItemDAO extends ICheckListItemTableConstants {
	public static final String DOC_ITEM_NO = ITMTBL_DOC_ITEM_NO;

	public static final String DOC_DESCRIPTION = ITMTBL_DOC_DESCRIPTION;

	public static final String IN_VAULT = ITMTBL_IN_VAULT;

	public static final String IN_EXT_CUSTODY = ITMTBL_IN_EXT_CUSTODY;

	public static final String IS_MANDATORY = ITMTBL_IS_MANDATORY;

	public static final String ACTION_PARTY = ITMTBL_ACTION_PARTY;

	public static final String STATUS = ITMTBL_STATUS;

	public static final String DOC_DATE = ITMTBL_DOC_DATE;

	public static final String EXPIRY_DATE = ITMTBL_EXPIRY_DATE;

	public static final String IS_AUDIT = ITMTBL_IS_AUDIT;

	public static final String REMARKS = ITMTBL_REMARKS;

	public static final String IS_DELETED = ITMTBL_IS_DELETED;

	public static final String CHECKLIST_ID = ITMTBL_CHECKLIST_ID;

	public static final String DOCUMENT_ID = ITMTBL_DOCUMENT_ID;

	public static final String DOCUMENT_CODE = ITMTBL_DOCUMENT_CODE;

	public static final String DOC_ITEM_REF = ITMTBL_DOC_ITEM_REF;

	public static final String FORM_NO = ITMTBL_FORM_NO;

	public static final String DOC_REF = ITMTBL_DOC_REF;

	public static final String LAST_UPDATE_DATE = ITMTBL_LAST_UPDATE_DATE;

	public static final String CPC_CUST_STATUS = ITMTBL_CPC_CUST_STATUS;

	/**
	 * Get the list of document items based on the criteria specified
	 * @param days - DocumentSearchCriteria
	 * @return CheckListItemMonitorResultWrapper - this contains a collection of
	 *         DocumentSearchResultItem
	 * @throws CheckListException on errors
	 */
	public CheckListItemMonitorResultWrapper searchCheckListItemList(int days, String[] statusArray)
			throws CheckListException;
}
