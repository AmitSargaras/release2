package com.integrosys.cms.app.tatduration.bus;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 4:08:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITatParamConstant 
{
	public static final String INSTANCE_TAT_DURATION = "TAT_DURATION";
	public static final String TAT_DURATION = "TAT_DURATION";
	
	public static final String DURATION_TYPE_ENTRY_CODE = "DURATION_TYPE";
	
	public static final String ACTION_READ_TAT_PARAM = "READ_TAT_PARAM";
	public static final String ACTION_READ_TAT_PARAM_ID = "READ_TAT_PARAM_ID";
	
	public static final String ACTION_MAKER_SUBMIT_TAT_DURATION = "MAKER_SUBMIT";
	public static final String ACTION_CHECKER_APPROVE_TAT_DURATION = "CHECKER_APPROVE";
	public static final String ACTION_CHECKER_REJECT_TAT_DURATION = "CHECKER_REJECT";
	public static final String ACTION_MAKER_CLOSE_TAT_DURATION = "MAKER_CLOSE";
}