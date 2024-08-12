package com.integrosys.cms.app.user.bus;

/**
 @author $Author: Abhijit R $
 */
import java.io.Serializable;
import java.util.List;

public interface ISMSUserErrorLogDao {

static final String ACTUAL_SMS_USER_ERROR_LOG = "actualOBSMSUserErrorLog";

	


	ISMSUserErrorLog createSMSUserErrorLog( ISMSUserErrorLog SMSUserErrorLog)
			throws Exception;
}
