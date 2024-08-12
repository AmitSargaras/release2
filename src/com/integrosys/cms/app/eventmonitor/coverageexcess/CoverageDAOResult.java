/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/coverageexcess/CoverageDAOResult.java,v 1.4 2005/05/19 07:07:51 eliliana Exp $
 */

package com.integrosys.cms.app.eventmonitor.coverageexcess;

import java.util.List;

import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAOResult;

public class CoverageDAOResult extends AbstractMonitorDAOResult {

	private CoverageDAOResult() {
	}

	public CoverageDAOResult(List results) {
		super(results);
	}
}
