/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/documentexpiry/DocExpiryDAOResult.java,v 1.9 2005/05/18 09:39:20 wltan Exp $
 */

package com.integrosys.cms.app.eventmonitor.documentexpiry;

import java.util.List;

import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAOResult;

public class DocExpiryDAOResult extends AbstractMonitorDAOResult {

	DocExpiryDAOResult() {
	}

	public DocExpiryDAOResult(List results) {
		super(results);
	}

}
