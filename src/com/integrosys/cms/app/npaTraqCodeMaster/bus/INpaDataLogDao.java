package com.integrosys.cms.app.npaTraqCodeMaster.bus;

/**
 @author $Author: Bharat waghela $
 */

public interface INpaDataLogDao {

	static final String ACTUAL_INTERFACE_LOG_NAME = "npaDataLog";

	
	INpaDataLog createNpaDataLog(INpaDataLog npaLog)
	        throws NpaDataLogException ;
    
}
