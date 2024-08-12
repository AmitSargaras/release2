package com.integrosys.cms.host.eai.document;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Various constants used by the checklist module
 * 
 * @author Chong Jun Yong
 * 
 */
public interface DocumentConstants {
	/**
	 * Checklist category for borrowers, ie, co borrower, main borrower and
	 * joint borrower
	 */
/*	public static final String[] CHECKLIST_CATEGORY_BORROWER = new String[] { ICMSConstant.CHECKLIST_CO_BORROWER,
			ICMSConstant.CHECKLIST_MAIN_BORROWER, ICMSConstant.CHECKLIST_JOINT_BORROWER };

	*//** Checklist category for pledgors/guarantor/hirer, ie, pledgor *//*
	public static final String[] CHECKLIST_CATEGORY_PLEDGOR = new String[] { ICMSConstant.CHECKLIST_PLEDGER };

	*//**
	 * Checklist category for all borrower and pledgor
	 *//*
	public static final String[] CHECKLIST_CATEGORIES = new String[] { ICMSConstant.CHECKLIST_CO_BORROWER,
			ICMSConstant.CHECKLIST_MAIN_BORROWER, ICMSConstant.CHECKLIST_JOINT_BORROWER, ICMSConstant.CHECKLIST_PLEDGER };*/

	/** Checklist type for borrower and pledgor, ie, CC */
	public static final String CHECKLIST_TYPE_BORROWER_PLEDGOR = "CC";

	/** Checklist type for collateral, ie, SC */
	public static final String CHECKLIST_TYPE_COLLATERAL = "SC";

/*	*//** Checklist types available in the system, ie, CC and SC *//*
	public static final String[] CHECKLIST_TYPES = new String[] { CHECKLIST_TYPE_BORROWER_PLEDGOR,
			CHECKLIST_TYPE_COLLATERAL };*/
}
