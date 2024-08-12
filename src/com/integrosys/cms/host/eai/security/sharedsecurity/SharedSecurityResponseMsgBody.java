/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/SecurityMessageBody.java,v 1.3 2003/10/23 02:49:03 lyng Exp $
 */
package com.integrosys.cms.host.eai.security.sharedsecurity;

import java.util.Vector;

import com.integrosys.cms.host.eai.EAIBody;

/**
 * Message for Security.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/10/23 02:49:03 $ Tag: $Name: $
 */
public class SharedSecurityResponseMsgBody extends EAIBody implements java.io.Serializable {

	private static final long serialVersionUID = -2547384204722460583L;

	private Vector sharedSecurityResultItem;

	public Vector getSharedSecurityResultItem() {
		return sharedSecurityResultItem;
	}

	public void setSharedSecurityResultItem(Vector sharedSecurityResultItem) {
		this.sharedSecurityResultItem = sharedSecurityResultItem;
	}

}
