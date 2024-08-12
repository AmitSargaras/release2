package com.integrosys.cms.app.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * This class contains commonly used constants in CMS.
 * 
 *  */
public class CMSConstantCla {
	// ********* constants for CMS: Sequence Names ****************
	
	

	private static final List MULTI_LEVEL_APPROVAL_STATES = Arrays
			.asList(new String[] { ICMSConstant.STATE_PENDING_CREATE_VERIFY,
					ICMSConstant.STATE_PENDING_UPDATE_VERIFY,
					ICMSConstant.STATE_PENDING_CLOSE_VERIFY,
					ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					ICMSConstant.STATE_PENDING_AUTH, });

	public static List getMultiLevelApprovalStates() {
		return MULTI_LEVEL_APPROVAL_STATES;
	}

}
