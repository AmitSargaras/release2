package com.integrosys.cms.app.user.bus;

/**
 @author $Author: Abhijit R $
 */

public interface ISMSUserUploadLogDao {

static final String ACTUAL_SMS_USER_UPLOAD_LOG = "actualOBSMSUserUploadLog";

	


	ISMSUserUploadLog createSMSUserUploadLog( ISMSUserUploadLog SMSUserUploadLog)
			throws Exception;
}
