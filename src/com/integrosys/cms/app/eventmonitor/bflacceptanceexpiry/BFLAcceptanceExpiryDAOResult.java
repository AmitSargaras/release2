/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/bflacceptanceexpiry/BFLAcceptanceExpiryDAOResult.java,v 1.6 2005/05/19 03:14:30 eliliana Exp $
 */

package com.integrosys.cms.app.eventmonitor.bflacceptanceexpiry;

import java.util.List;

import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAOResult;

public class BFLAcceptanceExpiryDAOResult extends AbstractMonitorDAOResult {

	private BFLAcceptanceExpiryDAOResult() {
	}

	public BFLAcceptanceExpiryDAOResult(List results) {
		super(results);
	}
}
