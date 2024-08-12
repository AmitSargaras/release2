/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/ICollateralTaskTableConstants.java,v 1.1 2003/08/22 03:31:05 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

/**
 * This interface defines the constant specific to the collateral task table and
 * the methods required by the document
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/22 03:31:05 $ Tag: $Name: $
 */
public interface ICollateralTaskTableConstants {
	public static final String COLLATERAL_TASK_TABLE = "CMS_COLLATERAL_TASK";

	public static final String STAGE_COLLATERAL_TASK_TABLE = "STAGE_COLLATERAL_TASK";

	public static final String TSKTBL_TASK_ID = "TASK_ID";

	public static final String TSKTBL_LIMIT_PROFILE_ID = "CMS_LSP_LMT_PROFILE_ID";

	public static final String TSKTBL_COLLATERAL_ID = "CMS_COLLATERAL_ID";

	public static final String TSKTBL_COLLATERAL_LOCATION = "SECURITY_LOCATION";

	public static final String TSKTBL_REMARKS = "REMARKS";

	public static final String TSKTBL_IS_DELETED = "IS_DELETED";

	public static final String TSKTBL_TASK_ID_PREF = COLLATERAL_TASK_TABLE + "." + TSKTBL_TASK_ID;

	public static final String TSKTBL_LIMIT_PROFILE_ID_PREF = COLLATERAL_TASK_TABLE + "." + TSKTBL_LIMIT_PROFILE_ID;

	public static final String TSKTBL_COLLATERAL_ID_PREF = COLLATERAL_TASK_TABLE + "." + TSKTBL_COLLATERAL_ID;

	public static final String TSKTBL_COLLATERAL_LOCATION_PREF = COLLATERAL_TASK_TABLE + "."
			+ TSKTBL_COLLATERAL_LOCATION;

	public static final String TSKTBL_REMARKS_PREF = COLLATERAL_TASK_TABLE + "." + TSKTBL_REMARKS;

	public static final String TSKTBL_IS_DELETED_PREF = COLLATERAL_TASK_TABLE + "." + TSKTBL_IS_DELETED;

	public static final String STAGE_TSKTBL_TASK_ID_PREF = STAGE_COLLATERAL_TASK_TABLE + "." + TSKTBL_TASK_ID;

	public static final String STAGE_TSKTBL_LIMIT_PROFILE_ID_PREF = STAGE_COLLATERAL_TASK_TABLE + "."
			+ TSKTBL_LIMIT_PROFILE_ID;

	public static final String STAGE_TSKTBL_COLLATERAL_ID_PREF = STAGE_COLLATERAL_TASK_TABLE + "."
			+ TSKTBL_COLLATERAL_ID;

	public static final String STAGE_TSKTBL_COLLATERAL_LOCATION_PREF = STAGE_COLLATERAL_TASK_TABLE + "."
			+ TSKTBL_COLLATERAL_LOCATION;

	public static final String STAGE_TSKTBL_REMARKS_PREF = STAGE_COLLATERAL_TASK_TABLE + "." + TSKTBL_REMARKS;

	public static final String STAGE_TSKTBL_IS_DELETED_PREF = STAGE_COLLATERAL_TASK_TABLE + "." + TSKTBL_IS_DELETED;
}
