package com.integrosys.cms.app.ws.dto;

public interface IPSRDataLogDao {
	
	static final String ACTUAL_INTERFACE_LOG_NAME = "PSRDataLog";
	
	IPSRDataLog createPSRDataLog(IPSRDataLog psrLog) throws PSRDataLogException ;
}
