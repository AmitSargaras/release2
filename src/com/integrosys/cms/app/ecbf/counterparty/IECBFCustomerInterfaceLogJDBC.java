package com.integrosys.cms.app.ecbf.counterparty;

import java.util.Date;
import java.util.Map;

public interface IECBFCustomerInterfaceLogJDBC {
	
	Map<String, Long> findAllFailedRequest(Date date) throws Exception;

}