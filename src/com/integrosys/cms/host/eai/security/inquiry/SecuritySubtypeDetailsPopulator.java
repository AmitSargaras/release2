package com.integrosys.cms.host.eai.security.inquiry;

import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;

/**
 * Security Subtype Object populator to set the required security sub type
 * object into the message body.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface SecuritySubtypeDetailsPopulator {
	/**
	 * To populate security subject type object into message body based on the
	 * security info suppplied.
	 * 
	 * @param sec the basic information required such as internal key to
	 *        retrieve the sub type object
	 * @param securityMsgBody the message body to be populated
	 */
	public void prepareSecuritySubtypeDetails(ApprovedSecurity sec, SecurityMessageBody securityMsgBody);
}
