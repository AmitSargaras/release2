/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collateralmaturity/CollateralMaturityDAOResult.java,v 1.7 2005/05/19 05:21:55 eliliana Exp $
 */

package com.integrosys.cms.app.eventmonitor.collateralmaturity;

import java.util.List;

import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAOResult;

public class CollateralMaturityDAOResult extends AbstractMonitorDAOResult {

	private CollateralMaturityDAOResult() {
	}

	public CollateralMaturityDAOResult(List results) {
		super(results);
	}
}
