/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/CCCustodianInfo.java,v 1.1 2004/02/05 01:35:03 hltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

import java.io.Serializable;

/**
 * Helper class that encapsulate the cc custodian specify info
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/05 01:35:03 $ Tag: $Name: $
 */
public class CCCustodianInfo implements Serializable {
	private String legalReference = null;

	private String legalName = null;

	private String ownerReference = null;

	public CCCustodianInfo() {
	}

	public String getLegalReference() {
		return this.legalReference;
	}

	public String getLegalName() {
		return this.legalName;
	}

	public String getOwnerReference() {
		return this.ownerReference;
	}

	public void setLegalReference(String aLegalReference) {
		this.legalReference = aLegalReference;
	}

	public void setLegalName(String aLegalName) {
		this.legalName = aLegalName;
	}

	public void setOwnerReference(String anOwnerReference) {
		this.ownerReference = anOwnerReference;
	}
}
