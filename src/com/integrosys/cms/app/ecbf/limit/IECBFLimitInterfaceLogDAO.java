package com.integrosys.cms.app.ecbf.limit;

public interface IECBFLimitInterfaceLogDAO {

	static final String INTERFACE_LOG_NAME = "ecbfLimitInterfaceLog";

	IECBFLimitInterfaceLog createOrUpdateInterfaceLog(IECBFLimitInterfaceLog log) throws Exception;
	
}