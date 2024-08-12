/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/insuranceexpiry/OBDocumentExpiryRuleParam.java,v 1.3 2003/09/29 03:44:36 btchng Exp $
 */

package com.integrosys.cms.app.eventmonitor.insuranceexpiry;

import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;

public class OBDocumentExpiryRuleParam extends OBDateRuleParam {
	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	private String documentCode;

}
