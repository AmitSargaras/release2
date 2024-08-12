/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/CleanSecurity.java,v 1.1 2004/06/04 03:11:20 lyng Exp $
 */
package com.integrosys.cms.host.eai.security.bus.clean;

import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;

/**
 * This class represents approved security of type Commodity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/06/04 03:11:20 $ Tag: $Name: $
 */
public class CleanSecurity extends ApprovedSecurity {
	/**
	 * Default constructor.
	 */
	public CleanSecurity() {
		super();
	}

	String comments;

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
