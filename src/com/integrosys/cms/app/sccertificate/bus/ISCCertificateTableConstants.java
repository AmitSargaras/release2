/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/ISCCertificateTableConstants.java,v 1.2 2003/11/14 09:07:58 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

/**
 * This interface defines the constant specific to the sc certificate table and
 * the methods required by the document
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/11/14 09:07:58 $ Tag: $Name: $
 */
public interface ISCCertificateTableConstants {
	public static final String SCCERTIFICATE_TABLE = "CMS_SCC_GENERATED";

	public static final String STAGE_SCCERTIFICATE_TABLE = "STAGE_SCC_GENERATED";

	public static final String CERTBL_SCC_ID = "SCC_ID";

	public static final String CERTBL_LIMIT_PROFILE_ID = "CMS_LSP_LMT_PROFILE_ID";

	public static final String CERTBL_GENERATION_DATE = "GENERATION_DATE";

	public static final String CERTBL_CREDIT_OFFICER_NAME = "CREDIT_OFFICER_NAME";

	public static final String CERTBL_CREDIT_OFFICER_SIGN_NO = "CREDIT_OFFICER_SIGN_NO";

	public static final String CERTBL_SENIOR_OFFICER_NAME = "SENIOR_OFFICER_NAME";

	public static final String CERTBL_SENIOR_OFFICER_SIGN_NO = "SENIOR_OFFICER_SIGN_NO";

	public static final String CERTBL_REMARKS = "REMARKS";

	public static final String CERTBL_SCC_ID_PREF = SCCERTIFICATE_TABLE + "." + CERTBL_SCC_ID;

	public static final String CERTBL_LIMIT_PROFILE_ID_PREF = SCCERTIFICATE_TABLE + "." + CERTBL_LIMIT_PROFILE_ID;

	public static final String CERTBL_GENERATION_DATE_PREF = SCCERTIFICATE_TABLE + "." + CERTBL_GENERATION_DATE;

	public static final String CERTBL_CREDIT_OFFICER_NAME_PREF = SCCERTIFICATE_TABLE + "." + CERTBL_CREDIT_OFFICER_NAME;

	public static final String CERTBL_CREDIT_OFFICER_SIGN_NO_PREF = SCCERTIFICATE_TABLE + "."
			+ CERTBL_CREDIT_OFFICER_SIGN_NO;

	public static final String CERTBL_SENIOR_OFFICER_NAME_PREF = SCCERTIFICATE_TABLE + "." + CERTBL_SENIOR_OFFICER_NAME;

	public static final String CERTBL_SENIOR_OFFICER_SIGN_NO_PREF = SCCERTIFICATE_TABLE + "."
			+ CERTBL_SENIOR_OFFICER_SIGN_NO;

	public static final String CERTBL_REMARKS_PREF = SCCERTIFICATE_TABLE + "." + CERTBL_REMARKS;

	public static final String STAGE_CERTBL_SCC_ID_PREF = STAGE_SCCERTIFICATE_TABLE + "." + CERTBL_SCC_ID;

	public static final String STAGE_CERTBL_LIMIT_PROFILE_ID_PREF = STAGE_SCCERTIFICATE_TABLE + "."
			+ CERTBL_LIMIT_PROFILE_ID;

	public static final String STAGE_CERTBL_GENERATION_DATE_PREF = STAGE_SCCERTIFICATE_TABLE + "."
			+ CERTBL_GENERATION_DATE;

	public static final String STAGE_CERTBL_CREDIT_OFFICER_NAME_PREF = STAGE_SCCERTIFICATE_TABLE + "."
			+ CERTBL_CREDIT_OFFICER_NAME;

	public static final String STAGE_CERTBL_CREDIT_OFFICER_SIGN_NO_PREF = STAGE_SCCERTIFICATE_TABLE + "."
			+ CERTBL_CREDIT_OFFICER_SIGN_NO;

	public static final String STAGE_CERTBL_SENIOR_OFFICER_NAME_PREF = STAGE_SCCERTIFICATE_TABLE + "."
			+ CERTBL_SENIOR_OFFICER_NAME;

	public static final String STAGE_CERTBL_SENIOR_OFFICER_SIGN_NO_PREF = STAGE_SCCERTIFICATE_TABLE + "."
			+ CERTBL_SENIOR_OFFICER_SIGN_NO;

	public static final String STAGE_CERTBL_REMARKS_PREF = STAGE_SCCERTIFICATE_TABLE + "." + CERTBL_REMARKS;

}
