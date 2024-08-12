package com.integrosys.cms.app.ws.dto;

/**
 @author $Author: Bharat waghela $
 */

public interface IFCUBSDataLogDao {

	static final String ACTUAL_INTERFACE_LOG_NAME = "FCUBSDataLog";

	
	IFCUBSDataLog createFCUBSDataLog(IFCUBSDataLog fcubsLog)
	        throws FCUBSDataLogException ;
    
   
    
  

}
