package com.integrosys.cms.app.ws.dto;

/**
 @author $Author: Bharat waghela $
 */

public interface IFCUBSReportDataLogDao {

	static final String ACTUAL_INTERFACE_LOG_NAME = "FCUBSReportDataLog";

	
	IFCUBSReportDataLog createFCUBSReportDataLog(IFCUBSReportDataLog fcubsReportLog)
	        throws FCUBSReportDataLogException ;
    
   
    
  

}
