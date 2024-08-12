package com.integrosys.cms.app.ecbf.counterparty;

public interface IECBFCustomerInterfaceLogDAO {

	static final String INTERFACE_LOG_NAME = "ecbfCustomerInterfaceLog";

	IECBFCustomerInterfaceLog createOrUpdateInterfaceLog(IECBFCustomerInterfaceLog log) throws Exception;
	
	IECBFCustomerInterfaceLog getInterfaceLog(long id) throws Exception;

}