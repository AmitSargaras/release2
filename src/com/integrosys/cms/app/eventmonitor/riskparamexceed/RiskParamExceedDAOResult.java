/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/riskparamexceed/RiskParamExceedDAOResult.java,v 1.6 2005/05/18 09:39:20 wltan Exp $
 */

package com.integrosys.cms.app.eventmonitor.riskparamexceed;

import java.util.List;

import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAOResult;

public class RiskParamExceedDAOResult extends AbstractMonitorDAOResult {

	private RiskParamExceedDAOResult() {
	}

	public RiskParamExceedDAOResult(List results) {
		super(results);
	}
}
