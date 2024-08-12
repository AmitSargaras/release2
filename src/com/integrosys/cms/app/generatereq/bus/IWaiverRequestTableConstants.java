/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/IWaiverRequestTableConstants.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

/**
 * This interface defines the constant specific to the waiver request table
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public interface IWaiverRequestTableConstants {
	public static final String WAIVER_REQ_TABLE = "CMS_WAIVER_GENERATED";

	public static final String STAGE_WAIVER_REQ_TABLE = "STAGE_WAIVER_GENERATED";

	public static final String WAVTBL_WAIVER_ID = "WAIVER_ID";

	public static final String WAVTBL_LIMIT_PROFILE_ID = "CMS_LSP_LMT_PROFILE_ID";

	public static final String WAVTBL_WAIVER_ID_PREF = WAIVER_REQ_TABLE + "." + WAVTBL_WAIVER_ID;

	public static final String WAVTBL_LIMIT_PROFILE_ID_PREF = WAIVER_REQ_TABLE + "." + WAVTBL_LIMIT_PROFILE_ID;

	public static final String STAGE_WAVTBL_WAIVER_ID_PREF = STAGE_WAIVER_REQ_TABLE + "." + WAVTBL_WAIVER_ID;

	public static final String STAGE_WAVTBL_LIMIT_PROFILE_ID_PREF = STAGE_WAIVER_REQ_TABLE + "."
			+ WAVTBL_LIMIT_PROFILE_ID;

}
