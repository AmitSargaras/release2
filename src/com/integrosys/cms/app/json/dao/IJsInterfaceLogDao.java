package com.integrosys.cms.app.json.dao;


import com.integrosys.cms.app.json.dto.IJsInterfaceLog;

public interface IJsInterfaceLogDao {
	static final String ACTUAL_INTERFACE_LOG_NAME = "actualJsInterfaceLog";
	

	public IJsInterfaceLog createOrUpdateInterfaceLog(IJsInterfaceLog log) throws Exception ;
	public IJsInterfaceLog getInterfaceLog(long id) throws Exception ;
}
