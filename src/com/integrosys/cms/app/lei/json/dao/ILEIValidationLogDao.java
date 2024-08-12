package com.integrosys.cms.app.lei.json.dao;


import java.util.Date;

import com.integrosys.cms.app.lei.json.dao.ILEIValidationLog;

public interface ILEIValidationLogDao {
	static final String ACTUAL_INTERFACE_LOG_NAME = "actualLEIValidationLog";
	

	public ILEIValidationLog createOrUpdateInterfaceLog(ILEIValidationLog log) throws Exception ;
	public ILEIValidationLog getInterfaceLog(long id) throws Exception ;
	public ILEIValidationLog insertInterfaceLog(ILEIValidationLog log) throws Exception;
	Boolean checkLEIAlreadyValidated (String entityName, String leiCode,String partyId );	
	public Date fetchLastValidatedDate (String entityName, String leiCode,String partyId);

}
