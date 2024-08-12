package com.integrosys.cms.host.eai.security.handler;

import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;

/**
 * Persistent Helper for Limit Charge
 * 
 * @author Chong Jun Yong
 * 
 */
public interface ILimitChargeHelper {
	/**
	 * persist all relevant limit charge of the security passed in
	 * 
	 * @param secMsgBody security message body
	 * @param approvedSecurity approved security
	 */
	public void persistAll(SecurityMessageBody secMsgBody, ApprovedSecurity approvedSecurity);
}
