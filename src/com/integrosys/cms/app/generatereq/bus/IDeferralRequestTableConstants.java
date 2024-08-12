/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/IDeferralRequestTableConstants.java,v 1.1 2003/09/12 02:29:26 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

/**
 * This interface defines the constant specific to the deferral request table
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/12 02:29:26 $ Tag: $Name: $
 */
public interface IDeferralRequestTableConstants {
	public static final String DEFERRAL_REQ_TABLE = "CMS_DEFERRAL_GENERATED";

	public static final String STAGE_DEFERRAL_REQ_TABLE = "STAGE_DEFERRAL_GENERATED";

	public static final String DEFTBL_DEFERRAL_ID = "DEFERRAL_ID";

	public static final String DEFTBL_LIMIT_PROFILE_ID = "CMS_LSP_LMT_PROFILE_ID";

	public static final String DEFTBL_DEFERRAL_ID_PREF = DEFERRAL_REQ_TABLE + "." + DEFTBL_DEFERRAL_ID;

	public static final String DEFTBL_LIMIT_PROFILE_ID_PREF = DEFERRAL_REQ_TABLE + "." + DEFTBL_LIMIT_PROFILE_ID;

	public static final String STAGE_DEFTBL_DEFERRAL_ID_PREF = STAGE_DEFERRAL_REQ_TABLE + "." + DEFTBL_DEFERRAL_ID;

	public static final String STAGE_DEFTBL_LIMIT_PROFILE_ID_PREF = STAGE_DEFERRAL_REQ_TABLE + "."
			+ DEFTBL_LIMIT_PROFILE_ID;

}
