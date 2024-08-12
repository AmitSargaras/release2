package com.integrosys.cms.app.user.bus;

/**
 @author $Author: Abhijit R $
 */

public interface ISMSUserLogDao {

	static final String ACTUAL_SMS_USER_LOG = "actualOBSMSUserLog";

	


	ISMSUserLog createSMSUserLog( ISMSUserLog SMSUserLog)
			throws Exception;
}
