/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IRecurrentCheckListTableConstants.java,v 1.7 2005/09/08 10:01:20 jychong Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//java

/**
 * This interface defines the constant specific to the recurrent checklist table
 * and the methods required by the checklist
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/09/08 10:01:20 $ Tag: $Name: $
 */
public interface IRecurrentCheckListTableConstants {
	public static final String RECURRENT_CHKLIST_TABLE = "CMS_RECURRENT_DOC";

	public static final String STAGE_RECURRENT_CHKLIST_TABLE = "STAGE_RECURRENT_DOC";

	public static final String RECURRENT_CHKLIST_ITEM_TABLE = "CMS_RECURRENT_DOC_ITEM";

	public static final String STAGE_RECURRENT_CHKLIST_ITEM_TABLE = "STAGE_RECURRENT_DOC_ITEM";

	public static final String RECURRENT_CHKLIST_SUB_ITEM_TABLE = "CMS_RECURRENT_DOC_SUB_ITEM";

	public static final String STAGE_RECURRENT_CHKLIST_SUB_ITEM_TABLE = "STAGE_RECURRENT_DOC_SUB_ITEM";

	// cr 26
	public static final String COVENANT_TABLE = "CMS_CONVENANT";

	public static final String STAGE_COVENANT_TABLE = "STAGE_CONVENANT";

	public static final String COVENANT_SUB_ITEM_TABLE = "CMS_CONVENANT_SUB_ITEM";

	public static final String STAGE_COVENANT_SUB_ITEM_TABLE = "STAGE_CONVENANT_SUB_ITEM";

	public static final String RECTBL_RECURRENT_DOC_ID = "RECURRENT_DOC_ID";

	public static final String RECTBL_LIMIT_PROFILE_ID = "CMS_LSP_LMT_PROFILE_ID";

	public static final String RECTBL_BORROWER_ID = "CMS_LMP_SUB_PROFILE_ID";

	public static final String RECTBL_STATUS = "STATUS";

	public static final String ITMTBL_ITEM_ID = "RECURRENT_ITEM_ID";

	public static final String ITMTBL_ITEM_REF_ID = "RECURRENT_ITEM_REF_ID";

	public static final String ITMTBL_RECURRENT_DOC_ID = "RECURRENT_DOC_ID";

	public static final String ITMTBL_FREQUENCY = "FREQUENCY";

	public static final String ITMTBL_FREQUENCY_UNIT = "FREQUENCY_UNIT";

	public static final String ITMTBL_DUE_DATE = "DUE_DATE";

	public static final String ITMTBL_DOC_END_DATE = "DOC_END_DATE";

	public static final String ITMTBL_GRACE_PERIOD = "GRACE_PERIOD";

	public static final String ITMTBL_GRACE_PERIOD_UNIT = "GRACE_PERIOD_UNIT";

	public static final String ITMTBL_CHASE_REMIND_IND = "CHASE_REMIND_IND";

	public static final String ITMTBL_ONE_OFF = "ONE_OFF";

	public static final String ITMTBL_LAST_DOC_ENTRY_DATE = "LAST_DOC_ENTRY_DATE";

	public static final String ITMTBL_REMARKS = "REMARKS";

	// cr 26
	public static final String CVNTBL_ITEM_ID = "CONVENANT_ID";

	public static final String CVNTBL_ITEM_REF_ID = "CONVENANT_REF_ID";

	public static final String CVNTBL_RECURRENT_DOC_ID = "RECURRENT_DOC_ID";

	public static final String CVNTBL_FREQUENCY = "FREQUENCY";

	public static final String CVNTBL_FREQUENCY_UNIT = "FREQUENCY_UNIT";

	public static final String CVNTBL_DUE_DATE = "DUE_DATE";

	public static final String CVNTBL_DOC_END_DATE = "DOC_END_DATE";

	public static final String CVNTBL_GRACE_PERIOD = "GRACE_PERIOD";

	public static final String CVNTBL_GRACE_PERIOD_UNIT = "GRACE_PERIOD_UNIT";

	public static final String CVNTBL_CHASE_REMIND_IND = "CHASE_REMIND_IND";

	public static final String CVNTBL_ONE_OFF = "ONE_OFF";

	public static final String CVNTBL_LAST_DOC_ENTRY_DATE = "LAST_DOC_ENTRY_DATE";

	public static final String CVNTBL_IS_VERIFIED = "IS_VERIFIED";

	public static final String CVNTBL_CHECKED_DATE = "DATE_CHECKED";

	public static final String CVNTBL_REMARKS = "REMARKS";

	public static final String SUBITM_SUB_ITEM_ID = "SUB_ITEM_ID";

	public static final String SUBITM_SUB_ITEM_REF_ID = "SUB_ITEM_REF_ID";

	public static final String SUBITM_DOC_END_DATE = "DOC_END_DATE";

	public static final String SUBITM_DUE_DATE = "DUE_DATE";

	public static final String SUBITM_REC_DATE = "REC_DATE";

	public static final String SUBITM_DEFERRED_DATE = "DEFERRED_DATE";

	public static final String SUBITM_PRINT_REMINDER_IND = "PRINT_REMINDER_IND";

	public static final String SUBITM_IS_DELETED_IND = "IS_DELETED_IND";

	public static final String SUBITM_RECURRENT_ITEM_ID = "RECURRENT_ITEM_ID";

	public static final String SUBITM_WAIVED_DATE = "WAIVED_DATE";

	public static final String SUBITM_DEFERRED_CNT = "DEFERRED_CNT";

	public static final String SUBITM_REMARKS = "SUB_REMARKS";

	// cr 26
	public static final String CVNSUBITM_SUB_ITEM_ID = "SUB_ITEM_ID";

	public static final String CVNSUBITM_SUB_ITEM_REF_ID = "SUB_ITEM_REF_ID";

	public static final String CVNSUBITM_DOC_END_DATE = "DOC_END_DATE";

	public static final String CVNSUBITM_DUE_DATE = "DUE_DATE";

	public static final String CVNSUBITM_CHECKED_DATE = "CHECKED_DATE";

	public static final String CVNSUBITM_DEFERRED_DATE = "DEFERRED_DATE";

	public static final String CVNSUBITM_PRINT_REMINDER_IND = "PRINT_REMINDER_IND";

	public static final String CVNSUBITM_IS_DELETED_IND = "IS_DELETED_IND";

	public static final String CVNSUBITM_COVENANT_ID = "CONVENANT_ID";

	public static final String CVNSUBITM_WAIVED_DATE = "WAIVED_DATE";

	public static final String CVNSUBITM_DEFERRED_CNT = "DEFERRED_CNT";

	public static final String CVNSUBITM_REMARKS = "SUB_REMARKS";

	public static final String CVNSUBITM_IS_VERIFIED_IND = "IS_VERIFIED_IND";

	// public static final String ITMTBL_DATE_RECEIVED = "DATE_RECEIVED";

	public static final String SRETBL_RECURRENT_DOC_ID_PREF = STAGE_RECURRENT_CHKLIST_TABLE + "."
			+ RECTBL_RECURRENT_DOC_ID;

	public static final String SRETBL_LIMIT_PROFILE_ID_PREF = STAGE_RECURRENT_CHKLIST_TABLE + "."
			+ RECTBL_LIMIT_PROFILE_ID;

	public static final String SRETBL_BORROWER_ID_PREF = STAGE_RECURRENT_CHKLIST_TABLE + "." + RECTBL_BORROWER_ID;

	public static final String SRETBL_STATUS_PREF = STAGE_RECURRENT_CHKLIST_TABLE + "." + RECTBL_STATUS;

	public static final String SITTBL_ITEM_REF_ID_PREF = STAGE_RECURRENT_CHKLIST_ITEM_TABLE + "." + ITMTBL_ITEM_REF_ID;

	public static final String SITTBL_RECURRENT_DOC_ID = STAGE_RECURRENT_CHKLIST_ITEM_TABLE + "."
			+ ITMTBL_RECURRENT_DOC_ID;

	public static final String SITTBL_FREQUENCY_PREF = STAGE_RECURRENT_CHKLIST_ITEM_TABLE + "." + ITMTBL_FREQUENCY;

	public static final String SITTBL_FREQUENCY_UNIT_PREF = STAGE_RECURRENT_CHKLIST_ITEM_TABLE + "."
			+ ITMTBL_FREQUENCY_UNIT;

	public static final String SITTBL_DOC_END_DATE_PREF = STAGE_RECURRENT_CHKLIST_ITEM_TABLE + "."
			+ ITMTBL_DOC_END_DATE;

	public static final String SITTBL_DUE_DATE_PREF = STAGE_RECURRENT_CHKLIST_ITEM_TABLE + "." + ITMTBL_DUE_DATE;

	// public static final String SITTBL_DATE_RECEIVED_PREF =
	// STAGE_RECURRENT_CHKLIST_ITEM_TABLE + "." + ITMTBL_DATE_RECEIVED;

	// cr 26
	public static final String SCVNTBL_ITEM_REF_ID_PREF = STAGE_COVENANT_TABLE + "." + CVNTBL_ITEM_REF_ID;

	public static final String SCVNTBL_RECURRENT_DOC_ID = STAGE_COVENANT_TABLE + "." + CVNTBL_RECURRENT_DOC_ID;

	public static final String SCVNTBL_FREQUENCY_PREF = STAGE_COVENANT_TABLE + "." + CVNTBL_FREQUENCY;

	public static final String SCVNTBL_FREQUENCY_UNIT_PREF = STAGE_COVENANT_TABLE + "." + CVNTBL_FREQUENCY_UNIT;

	public static final String SCVNTBL_DOC_END_DATE_PREF = STAGE_COVENANT_TABLE + "." + CVNTBL_DOC_END_DATE;

	public static final String SCVNTBL_DUE_DATE_PREF = STAGE_COVENANT_TABLE + "." + CVNTBL_DUE_DATE;

	public static final String RIMTBL_ITEM_ID_PREF = RECURRENT_CHKLIST_ITEM_TABLE + "." + ITMTBL_ITEM_ID;

	public static final String RIMTBL_ITEM_REF_ID_PREF = RECURRENT_CHKLIST_ITEM_TABLE + "." + ITMTBL_ITEM_REF_ID;

	public static final String RIMTBL_RECURRENT_DOC_ID_PREF = RECURRENT_CHKLIST_ITEM_TABLE + "."
			+ ITMTBL_RECURRENT_DOC_ID;

	public static final String RIMTBL_FREQUENCY_PREF = RECURRENT_CHKLIST_ITEM_TABLE + "." + ITMTBL_FREQUENCY;

	public static final String RIMTBL_FREQUENCY_UNIT_PREF = RECURRENT_CHKLIST_ITEM_TABLE + "." + ITMTBL_FREQUENCY_UNIT;

	public static final String RIMTBL_DUE_DATE_PREF = RECURRENT_CHKLIST_ITEM_TABLE + "." + ITMTBL_DUE_DATE;

	public static final String RIMTBL_DOC_END_DATE_PREF = RECURRENT_CHKLIST_ITEM_TABLE + "." + ITMTBL_DOC_END_DATE;

	public static final String RIMTBL_GRACE_PERIOD_PREF = RECURRENT_CHKLIST_ITEM_TABLE + "." + ITMTBL_GRACE_PERIOD;

	public static final String RIMTBL_GRACE_PERIOD_UNIT_PREF = RECURRENT_CHKLIST_ITEM_TABLE + "."
			+ ITMTBL_GRACE_PERIOD_UNIT;

	public static final String RIMTBL_CHASE_REMIND_IND_PREF = RECURRENT_CHKLIST_ITEM_TABLE + "."
			+ ITMTBL_CHASE_REMIND_IND;

	public static final String RIMTBL_ONE_OFF_PREF = RECURRENT_CHKLIST_ITEM_TABLE + "." + ITMTBL_ONE_OFF;

	public static final String RIMTBL_LAST_DOC_ENTRY_DATE_PREF = RECURRENT_CHKLIST_ITEM_TABLE + "."
			+ ITMTBL_LAST_DOC_ENTRY_DATE;

	// cr 26
	public static final String CVNTBL_ITEM_ID_PREF = COVENANT_TABLE + "." + CVNTBL_ITEM_ID;

	public static final String CVNTBL_ITEM_REF_ID_PREF = COVENANT_TABLE + "." + CVNTBL_ITEM_REF_ID;

	public static final String CVNTBL_RECURRENT_DOC_ID_PREF = COVENANT_TABLE + "." + CVNTBL_RECURRENT_DOC_ID;

	public static final String CVNTBL_FREQUENCY_PREF = COVENANT_TABLE + "." + CVNTBL_FREQUENCY;

	public static final String CVNTBL_FREQUENCY_UNIT_PREF = COVENANT_TABLE + "." + CVNTBL_FREQUENCY_UNIT;

	public static final String CVNTBL_DUE_DATE_PREF = COVENANT_TABLE + "." + CVNTBL_DUE_DATE;

	public static final String CVNTBL_DOC_END_DATE_PREF = COVENANT_TABLE + "." + CVNTBL_DOC_END_DATE;

	public static final String CVNTBL_GRACE_PERIOD_PREF = COVENANT_TABLE + "." + CVNTBL_GRACE_PERIOD;

	public static final String CVNTBL_GRACE_PERIOD_UNIT_PREF = COVENANT_TABLE + "." + CVNTBL_GRACE_PERIOD_UNIT;

	public static final String CVNTBL_CHASE_REMIND_IND_PREF = COVENANT_TABLE + "." + CVNTBL_CHASE_REMIND_IND;

	public static final String CVNTBL_ONE_OFF_PREF = COVENANT_TABLE + "." + CVNTBL_ONE_OFF;

	public static final String CVNTBL_LAST_DOC_ENTRY_DATE_PREF = COVENANT_TABLE + "." + CVNTBL_LAST_DOC_ENTRY_DATE;

	public static final String CVNTBL_IS_VERIFIED_PREF = COVENANT_TABLE + "." + CVNTBL_IS_VERIFIED;

	public static final String CVNTBL_CHECKED_DATE_PREF = COVENANT_TABLE + "." + CVNTBL_CHECKED_DATE;

	public static final String SIMTBL_SUB_ITEM_ID_PREF = RECURRENT_CHKLIST_SUB_ITEM_TABLE + "." + SUBITM_SUB_ITEM_ID;

	public static final String SIMTBL_SUB_ITEM_REF_ID_PREF = RECURRENT_CHKLIST_SUB_ITEM_TABLE + "."
			+ SUBITM_SUB_ITEM_REF_ID;

	public static final String SIMTBL_DOC_END_DATE_PREF = RECURRENT_CHKLIST_SUB_ITEM_TABLE + "." + SUBITM_DOC_END_DATE;

	public static final String SIMTBL_DUE_DATE_PREF = RECURRENT_CHKLIST_SUB_ITEM_TABLE + "." + SUBITM_DUE_DATE;

	public static final String SIMTBL_REC_DATE_PREF = RECURRENT_CHKLIST_SUB_ITEM_TABLE + "." + SUBITM_REC_DATE;

	public static final String SIMTBL_DEFERRED_DATE_PREF = RECURRENT_CHKLIST_SUB_ITEM_TABLE + "."
			+ SUBITM_DEFERRED_DATE;

	public static final String SIMTBL_PRINT_REMINDER_IND_PREF = RECURRENT_CHKLIST_SUB_ITEM_TABLE + "."
			+ SUBITM_PRINT_REMINDER_IND;

	public static final String SIMTBL_IS_DELETED_IND_PREF = RECURRENT_CHKLIST_SUB_ITEM_TABLE + "."
			+ SUBITM_IS_DELETED_IND;

	public static final String SIMTBL_RECURRENT_ITEM_ID_PREF = RECURRENT_CHKLIST_SUB_ITEM_TABLE + "."
			+ SUBITM_RECURRENT_ITEM_ID;

	public static final String SIMTBL_WAIVED_DATE_PREF = RECURRENT_CHKLIST_SUB_ITEM_TABLE + "." + SUBITM_WAIVED_DATE;

	public static final String SIMTBL_DEFERRED_CNT_PREF = RECURRENT_CHKLIST_SUB_ITEM_TABLE + "." + SUBITM_DEFERRED_CNT;

	public static final String SIMTBL_REMARKS_PREF = RECURRENT_CHKLIST_SUB_ITEM_TABLE + "." + SUBITM_REMARKS;

	// cr 26
	public static final String CVNSUBITMTBL_SUB_ITEM_ID_PREF = COVENANT_SUB_ITEM_TABLE + "." + CVNSUBITM_SUB_ITEM_ID;

	public static final String CVNSUBITMTBL_SUB_ITEM_REF_ID_PREF = COVENANT_SUB_ITEM_TABLE + "."
			+ CVNSUBITM_SUB_ITEM_REF_ID;

	public static final String CVNSUBITMTBL_DOC_END_DATE_PREF = COVENANT_SUB_ITEM_TABLE + "." + CVNSUBITM_DOC_END_DATE;

	public static final String CVNSUBITMTBL_DUE_DATE_PREF = COVENANT_SUB_ITEM_TABLE + "." + CVNSUBITM_DUE_DATE;

	public static final String CVNSUBITMTBL_CHECKED_DATE_PREF = COVENANT_SUB_ITEM_TABLE + "." + CVNSUBITM_CHECKED_DATE;

	public static final String CVNSUBITMTBL_DEFERRED_DATE_PREF = COVENANT_SUB_ITEM_TABLE + "."
			+ CVNSUBITM_DEFERRED_DATE;

	public static final String CVNSUBITMTBL_PRINT_REMINDER_IND_PREF = COVENANT_SUB_ITEM_TABLE + "."
			+ CVNSUBITM_PRINT_REMINDER_IND;

	public static final String CVNSUBITMTBL_IS_DELETED_IND_PREF = COVENANT_SUB_ITEM_TABLE + "."
			+ CVNSUBITM_IS_DELETED_IND;

	public static final String CVNSUBITMTBL_COVENANT_ID_PREF = COVENANT_SUB_ITEM_TABLE + "." + CVNSUBITM_COVENANT_ID;

	public static final String CVNSUBITMTBL_WAIVED_DATE_PREF = COVENANT_SUB_ITEM_TABLE + "." + CVNSUBITM_WAIVED_DATE;

	public static final String CVNSUBITMTBL_DEFERRED_CNT_PREF = COVENANT_SUB_ITEM_TABLE + "." + CVNSUBITM_DEFERRED_CNT;

	public static final String CVNSUBITMTBL_REMARKS_PREF = COVENANT_SUB_ITEM_TABLE + "." + CVNSUBITM_REMARKS;

	public static final String CVNSUBITMTBL_IS_VERIFIED_IND_PREF = COVENANT_SUB_ITEM_TABLE + "."
			+ CVNSUBITM_IS_VERIFIED_IND;

}