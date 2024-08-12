/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ICheckListTableConstants.java,v 1.7 2006/04/13 03:30:46 jzhai Exp $
 */
package com.integrosys.cms.app.checklist.bus;

/**
 * This interface defines the constant specific to the checklist and checklist
 * item table
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/04/13 03:30:46 $ Tag: $Name: $
 */
public interface ICheckListTableConstants {
	// For the checklist master table
	public static final String CHKLIST_TABLE = "CMS_CHECKLIST";

	public static final String STAGE_CHKLIST_TABLE = "STAGE_CHECKLIST";

	// Table constants for CHECKLIST
	public static final String CHKTBL_CHECKLISTID = "CHECKLIST_ID";
	
	public static final String CHKTBL_CAMDATE = "CAMDATE";
	
	public static final String CHKTBL_CAMEXPIRYDATE = "CAMEXPIRYDATE";
	
	public static final String CHKTBL_CAMNUMBER = "CAMNUMBER";
	
	public static final String CHKTBL_CAMTYPE = "CAMTYPE";
	
	public static final String CHKTBL_IS_LATEST = "IS_LATEST";

	public static final String CHKTBL_LIMIT_PROFILE_ID = "CMS_LSP_LMT_PROFILE_ID";
	
	public static final String CHKTBL_IS_DISPLAY = "IS_DISPLAY";


	public static final String CHKTBL_TEMPLATE_ID = "MASTERLIST_ID";

	public static final String CHKTBL_COLLATERAL_ID = "CMS_COLLATERAL_ID";

	public static final String CHKTBL_BORROWER_ID = "CMS_LMP_SUB_PROFILE_ID";

	public static final String CHKTBL_PLEDGER_ID = "CMS_PLEDGOR_DTL_ID";

	public static final String CHKTBL_CATEGORY = "CATEGORY";

	public static final String CHKTBL_SUB_CATEGORY = "SUB_CATEGORY";

	public static final String CHKTBL_STATUS = "STATUS";

	public static final String CHKTBL_DISABLE_TASK_IND = "disable_collaboration_ind";

	public static final String CHKTBL_DOC_LOC_CTRY = "DOC_ORIG_COUNTRY";

	public static final String CHKTBL_DOC_LOC_ORG = "DOC_ORIG_ORGANISATION";

	public static final String CHKTBL_ALLOW_DELETE_IND = "ALLOW_DELETE_IND";

    public static final String CHKTBL_APPLICATION_TYPE = "APPLICATION_TYPE";

    public static final String CHKTBL_CHECKLISTID_PREF = CHKLIST_TABLE + "." + CHKTBL_CHECKLISTID;

	public static final String CHKTBL_LIMIT_PROFILE_ID_PREF = CHKLIST_TABLE + "." + CHKTBL_LIMIT_PROFILE_ID;

	public static final String CHKTBL_TEMPLATE_ID_PREF = CHKLIST_TABLE + "." + CHKTBL_TEMPLATE_ID;

	public static final String CHKTBL_COLLATERAL_ID_PREF = CHKLIST_TABLE + "." + CHKTBL_COLLATERAL_ID;

	public static final String CHKTBL_BORROWER_ID_PREF = CHKLIST_TABLE + "." + CHKTBL_BORROWER_ID;

	public static final String CHKTBL_PLEDGER_ID_PREF = CHKLIST_TABLE + "." + CHKTBL_PLEDGER_ID;

	public static final String CHKTBL_CATEGORY_PREF = CHKLIST_TABLE + "." + CHKTBL_CATEGORY;
	
	public static final String CHKTBL_CAM_NO_PREF = CHKLIST_TABLE + "." + CHKTBL_CAMNUMBER;

	public static final String CHKTBL_SUB_CATEGORY_PREF = CHKLIST_TABLE + "." + CHKTBL_SUB_CATEGORY;

	public static final String CHKTBL_STATUS_PREF = CHKLIST_TABLE + "." + CHKTBL_STATUS;

	public static final String CHKTBL_DOC_LOC_CTRY_PREF = CHKLIST_TABLE + "." + CHKTBL_DOC_LOC_CTRY;

	public static final String CHKTBL_DOC_LOC_ORG_PREF = CHKLIST_TABLE + "." + CHKTBL_DOC_LOC_ORG;

	public static final String CHKTBL_ALLOW_DELETE_IND_PREF = CHKLIST_TABLE + "." + CHKTBL_ALLOW_DELETE_IND;

    public static final String CHKTBL_APPLICATION_TYPE_PREF = CHKLIST_TABLE + "." + CHKTBL_APPLICATION_TYPE;

	public static final String STAGE_CHKTBL_CHECKLISTID_PREF = STAGE_CHKLIST_TABLE + "." + CHKTBL_CHECKLISTID;

	public static final String STAGE_CHKTBL_LIMIT_PROFILE_ID_PREF = STAGE_CHKLIST_TABLE + "." + CHKTBL_LIMIT_PROFILE_ID;

	public static final String STAGE_CHKTBL_TEMPLATE_ID_PREF = STAGE_CHKLIST_TABLE + "." + CHKTBL_TEMPLATE_ID;

	public static final String STAGE_CHKTBL_COLLATERAL_ID_PREF = STAGE_CHKLIST_TABLE + "." + CHKTBL_COLLATERAL_ID;

	public static final String STAGE_CHKTBL_BORROWER_ID_PREF = STAGE_CHKLIST_TABLE + "." + CHKTBL_BORROWER_ID;

	public static final String STAGE_CHKTBL_PLEDGER_ID_PREF = STAGE_CHKLIST_TABLE + "." + CHKTBL_PLEDGER_ID;

	public static final String STAGE_CHKTBL_CATEGORY_PREF = STAGE_CHKLIST_TABLE + "." + CHKTBL_CATEGORY;

	public static final String STAGE_CHKTBL_SUB_CATEGORY_PREF = STAGE_CHKLIST_TABLE + "." + CHKTBL_SUB_CATEGORY;

	public static final String STAGE_CHKTBL_STATUS_PREF = STAGE_CHKLIST_TABLE + "." + CHKTBL_STATUS;

	public static final String STAGE_CHKTBL_DOC_LOC_CTRY_PREF = STAGE_CHKLIST_TABLE + "." + CHKTBL_DOC_LOC_CTRY;

	public static final String STAGE_CHKTBL_DOC_LOC_ORG_PREF = STAGE_CHKLIST_TABLE + "." + CHKTBL_DOC_LOC_ORG;

	public static final String STAGE_CHKTBL_ALLOW_DELETE_IND_PREF = STAGE_CHKLIST_TABLE + "." + CHKTBL_ALLOW_DELETE_IND;

    public static final String STAGE_CHKTBL_APPLICATION_TYPE_PREF = STAGE_CHKLIST_TABLE + "." + CHKTBL_APPLICATION_TYPE;

}
