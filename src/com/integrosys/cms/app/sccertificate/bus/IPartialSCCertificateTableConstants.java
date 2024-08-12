/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/IPartialSCCertificateTableConstants.java,v 1.1 2003/08/11 12:49:32 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

/**
 * This interface defines the constant specific to the partial sc certificate
 * table and the methods required by the document
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/11 12:49:32 $ Tag: $Name: $
 */
public interface IPartialSCCertificateTableConstants {
	public static final String PSCCERTIFICATE_TABLE = "CMS_PSCC_GENERATED";

	public static final String STAGE_PSCCERTIFICATE_TABLE = "STAGE_PSCC_GENERATED";

	public static final String CERTBL_PSCC_ID = "PSCC_ID";

	public static final String CERTBL_LIMIT_PROFILE_ID = "CMS_LSP_LMT_PROFILE_ID";

	public static final String CERTBL_CREDIT_OFFICER_NAME = "CREDIT_OFFICER_NAME";

	public static final String CERTBL_CREDIT_OFFICER_SIGN_NO = "CREDIT_OFFICER_SIGN_NO";

	public static final String CERTBL_SENIOR_OFFICER_NAME = "SENIOR_OFFICER_NAME";

	public static final String CERTBL_SENIOR_OFFICER_SIGN_NO = "SENIOR_OFFICER_SIGN_NO";

	public static final String CERTBL_REMARKS = "REMARKS";

	public static final String CERTBL_PSCC_ID_PREF = PSCCERTIFICATE_TABLE + "." + CERTBL_PSCC_ID;

	public static final String CERTBL_LIMIT_PROFILE_ID_PREF = PSCCERTIFICATE_TABLE + "." + CERTBL_LIMIT_PROFILE_ID;

	public static final String CERTBL_CREDIT_OFFICER_NAME_PREF = PSCCERTIFICATE_TABLE + "."
			+ CERTBL_CREDIT_OFFICER_NAME;

	public static final String CERTBL_CREDIT_OFFICER_SIGN_NO_PREF = PSCCERTIFICATE_TABLE + "."
			+ CERTBL_CREDIT_OFFICER_SIGN_NO;

	public static final String CERTBL_SENIOR_OFFICER_NAME_PREF = PSCCERTIFICATE_TABLE + "."
			+ CERTBL_SENIOR_OFFICER_NAME;

	public static final String CERTBL_SENIOR_OFFICER_SIGN_NO_PREF = PSCCERTIFICATE_TABLE + "."
			+ CERTBL_SENIOR_OFFICER_SIGN_NO;

	public static final String CERTBL_REMARKS_PREF = PSCCERTIFICATE_TABLE + "." + CERTBL_REMARKS;

	public static final String STAGE_CERTBL_PSCC_ID_PREF = STAGE_PSCCERTIFICATE_TABLE + "." + CERTBL_PSCC_ID;

	public static final String STAGE_CERTBL_LIMIT_PROFILE_ID_PREF = STAGE_PSCCERTIFICATE_TABLE + "."
			+ CERTBL_LIMIT_PROFILE_ID;

	public static final String STAGE_CERTBL_CREDIT_OFFICER_NAME_PREF = STAGE_PSCCERTIFICATE_TABLE + "."
			+ CERTBL_CREDIT_OFFICER_NAME;

	public static final String STAGE_CERTBL_CREDIT_OFFICER_SIGN_NO_PREF = STAGE_PSCCERTIFICATE_TABLE + "."
			+ CERTBL_CREDIT_OFFICER_SIGN_NO;

	public static final String STAGE_CERTBL_SENIOR_OFFICER_NAME_PREF = STAGE_PSCCERTIFICATE_TABLE + "."
			+ CERTBL_SENIOR_OFFICER_NAME;

	public static final String STAGE_CERTBL_SENIOR_OFFICER_SIGN_NO_PREF = STAGE_PSCCERTIFICATE_TABLE + "."
			+ CERTBL_SENIOR_OFFICER_SIGN_NO;

	public static final String STAGE_CERTBL_REMARKS_PREF = STAGE_PSCCERTIFICATE_TABLE + "." + CERTBL_REMARKS;

}
