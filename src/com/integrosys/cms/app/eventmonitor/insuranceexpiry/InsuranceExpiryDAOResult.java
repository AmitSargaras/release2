/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/insuranceexpiry/InsuranceExpiryDAOResult.java,v 1.7 2005/05/18 09:39:20 wltan Exp $
 */

package com.integrosys.cms.app.eventmonitor.insuranceexpiry;

import java.util.List;

import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAOResult;

public class InsuranceExpiryDAOResult extends AbstractMonitorDAOResult {

	private InsuranceExpiryDAOResult() {
	}

	public InsuranceExpiryDAOResult(List results) {
		super(results);
	}
}
