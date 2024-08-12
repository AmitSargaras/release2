/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/IDDNTableConstants.java,v 1.2 2003/08/13 12:09:28 hltan Exp $
 */
package com.integrosys.cms.app.ddn.bus;

/**
 * This interface defines the constant specific to the ddn table and the methods
 * required by the document
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/13 12:09:28 $ Tag: $Name: $
 */
public interface IDDNTableConstants {
	public static final String DDN_TABLE = "CMS_DDN_GENERATED";

	public static final String STAGE_DDN_TABLE = "STAGE_DDN_GENERATED";

	public static final String DDNTBL_DDN_ID = "DDN_ID";

	public static final String DDNTBL_LIMIT_PROFILE_ID = "CMS_LSP_LMT_PROFILE_ID";

	public static final String DDNTBL_GENERATION_DATE = "GENERATION_DATE";

	public static final String DDNTBL_DEFERRED_TO = "DEFERRED_TO";

	public static final String DDNTBL_VALID_FOR = "VALID_FOR";

	public static final String DDNTBL_CREDIT_OFFICER_NAME = "CREDIT_OFFICER_NAME";

	public static final String DDNTBL_CREDIT_OFFICER_SIGN_NO = "CREDIT_OFFICER_SIGN_NO";

	public static final String DDNTBL_SENIOR_OFFICER_NAME = "SENIOR_OFFICER_NAME";

	public static final String DDNTBL_SENIOR_OFFICER_SIGN_NO = "SENIOR_OFFICER_SIGN_NO";

	public static final String DDNTBL_REMARKS = "REMARKS";

	public static final String DDNTBL_DDN_ID_PREF = DDN_TABLE + "." + DDNTBL_DDN_ID;

	public static final String DDNTBL_LIMIT_PROFILE_ID_PREF = DDN_TABLE + "." + DDNTBL_LIMIT_PROFILE_ID;

	public static final String DDNTBL_GENERATION_DATE_PREF = DDN_TABLE + "." + DDNTBL_GENERATION_DATE;

	public static final String DDNTBL_DEFERRED_TO_PREF = DDN_TABLE + "." + DDNTBL_DEFERRED_TO;

	public static final String DDNTBL_VALID_FOR_PREF = DDN_TABLE + "." + DDNTBL_VALID_FOR;

	public static final String DDNTBL_CREDIT_OFFICER_NAME_PREF = DDN_TABLE + "." + DDNTBL_CREDIT_OFFICER_NAME;

	public static final String DDNTBL_CREDIT_OFFICER_SIGN_NO_PREF = DDN_TABLE + "." + DDNTBL_CREDIT_OFFICER_SIGN_NO;

	public static final String DDNTBL_SENIOR_OFFICER_NAME_PREF = DDN_TABLE + "." + DDNTBL_SENIOR_OFFICER_NAME;

	public static final String DDNTBL_SENIOR_OFFICER_SIGN_NO_PREF = DDN_TABLE + "." + DDNTBL_SENIOR_OFFICER_SIGN_NO;

	public static final String DDNTBL_REMARKS_PREF = DDN_TABLE + "." + DDNTBL_REMARKS;

	public static final String STAGE_DDNTBL_DDN_ID_PREF = STAGE_DDN_TABLE + "." + DDNTBL_DDN_ID;

	public static final String STAGE_DDNTBL_LIMIT_PROFILE_ID_PREF = STAGE_DDN_TABLE + "." + DDNTBL_LIMIT_PROFILE_ID;

	public static final String STAGE_DDNTBL_GENERATION_DATE_PREF = STAGE_DDN_TABLE + "." + DDNTBL_GENERATION_DATE;

	public static final String STAGE_DDNTBL_DEFERRED_TO_PREF = STAGE_DDN_TABLE + "." + DDNTBL_DEFERRED_TO;

	public static final String STAGE_DDNTBL_VALID_FOR_PREF = STAGE_DDN_TABLE + "." + DDNTBL_VALID_FOR;

	public static final String STAGE_DDNTBL_CREDIT_OFFICER_NAME_PREF = STAGE_DDN_TABLE + "."
			+ DDNTBL_CREDIT_OFFICER_NAME;

	public static final String STAGE_DDNTBL_CREDIT_OFFICER_SIGN_NO_PREF = STAGE_DDN_TABLE + "."
			+ DDNTBL_CREDIT_OFFICER_SIGN_NO;

	public static final String STAGE_DDNTBL_SENIOR_OFFICER_NAME_PREF = STAGE_DDN_TABLE + "."
			+ DDNTBL_SENIOR_OFFICER_NAME;

	public static final String STAGE_DDNTBL_SENIOR_OFFICER_SIGN_NO_PREF = STAGE_DDN_TABLE + "."
			+ DDNTBL_SENIOR_OFFICER_SIGN_NO;

	public static final String STAGE_DDNTBL_REMARKS_PREF = STAGE_DDN_TABLE + "." + DDNTBL_REMARKS;

}
