/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/ICCTaskTableConstants.java,v 1.1 2003/08/31 13:56:24 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

/**
 * This interface defines the constant specific to the CC task table
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/31 13:56:24 $ Tag: $Name: $
 */
public interface ICCTaskTableConstants {
	public static final String CC_TASK_TABLE = "CMS_CC_TASK";

	public static final String STAGE_CC_TASK_TABLE = "STAGE_CC_TASK";

	public static final String TSKTBL_TASK_ID = "TASK_ID";

	public static final String TSKTBL_LIMIT_PROFILE_ID = "CMS_LSP_LMT_PROFILE_ID";

	public static final String TSKTBL_SUB_PROFILE_ID = "CMS_LMP_SUB_PROFILE_ID";

	public static final String TSKTBL_PLEDGOR_ID = "CMS_PLEDGOR_DTL_ID";

	public static final String TSKTBL_CATEGORY = "CATEGORY";

	public static final String TSKTBL_DOMICILE_CTRY = "DMCL_CNTRY_ISO_CODE";

	public static final String TSKTBL_ORG_CODE = "ORG_CODE";

	public static final String TSKTBL_REMARKS = "REMARKS";

	public static final String TSKTBL_IS_DELETED = "IS_DELETED";

	public static final String TSKTBL_TASK_ID_PREF = CC_TASK_TABLE + "." + TSKTBL_TASK_ID;

	public static final String TSKTBL_LIMIT_PROFILE_ID_PREF = CC_TASK_TABLE + "." + TSKTBL_LIMIT_PROFILE_ID;

	public static final String TSKTBL_SUB_PROFILE_ID_PREF = CC_TASK_TABLE + "." + TSKTBL_SUB_PROFILE_ID;

	public static final String TSKTBL_PLEDGOR_ID_PREF = CC_TASK_TABLE + "." + TSKTBL_PLEDGOR_ID;

	public static final String TSKTBL_CATEGORY_PREF = CC_TASK_TABLE + "." + TSKTBL_CATEGORY;

	public static final String TSKTBL_DOMICILE_CTRY_PREF = CC_TASK_TABLE + "." + TSKTBL_DOMICILE_CTRY;

	public static final String TSKTBL_ORG_CODE_PREF = CC_TASK_TABLE + "." + TSKTBL_ORG_CODE;

	public static final String TSKTBL_REMARKS_PREF = CC_TASK_TABLE + "." + TSKTBL_REMARKS;

	public static final String TSKTBL_IS_DELETED_PREF = CC_TASK_TABLE + "." + TSKTBL_IS_DELETED;

	public static final String STAGE_TSKTBL_TASK_ID_PREF = STAGE_CC_TASK_TABLE + "." + TSKTBL_TASK_ID;

	public static final String STAGE_TSKTBL_LIMIT_PROFILE_ID_PREF = STAGE_CC_TASK_TABLE + "." + TSKTBL_LIMIT_PROFILE_ID;

	public static final String STAGE_TSKTBL_SUB_PROFILE_ID_PREF = STAGE_CC_TASK_TABLE + "." + TSKTBL_SUB_PROFILE_ID;

	public static final String STAGE_TSKTBL_PLEDGOR_ID_PREF = STAGE_CC_TASK_TABLE + "." + TSKTBL_PLEDGOR_ID;

	public static final String STAGE_TSKTBL_CATEGORY_PREF = STAGE_CC_TASK_TABLE + "." + TSKTBL_CATEGORY;

	public static final String STAGE_TSKTBL_DOMICILE_CTRY_PREF = STAGE_CC_TASK_TABLE + "." + TSKTBL_DOMICILE_CTRY;

	public static final String STAGE_TSKTBL_ORG_CODE_PREF = STAGE_CC_TASK_TABLE + "." + TSKTBL_ORG_CODE;

	public static final String STAGE_TSKTBL_REMARKS_PREF = STAGE_CC_TASK_TABLE + "." + TSKTBL_REMARKS;

	public static final String STAGE_TSKTBL_IS_DELETED_PREF = STAGE_CC_TASK_TABLE + "." + TSKTBL_IS_DELETED;
}
