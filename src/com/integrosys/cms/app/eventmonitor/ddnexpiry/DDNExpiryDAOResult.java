/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/ddnexpiry/DDNExpiryDAOResult.java,v 1.8 2005/05/19 07:14:45 eliliana Exp $
 */

package com.integrosys.cms.app.eventmonitor.ddnexpiry;

import java.util.List;

import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAOResult;

public class DDNExpiryDAOResult extends AbstractMonitorDAOResult {

	private DDNExpiryDAOResult() {
	}

	public DDNExpiryDAOResult(List results) {
		super(results);
	}

}
