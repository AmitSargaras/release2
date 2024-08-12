/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collateralevaluationdue/EvaluationDueDAOResult.java,v 1.6 2005/05/19 03:44:54 eliliana Exp $
 */

package com.integrosys.cms.app.eventmonitor.collateralevaluationdue;

import java.util.List;

import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAOResult;

public class EvaluationDueDAOResult extends AbstractMonitorDAOResult {

	private EvaluationDueDAOResult() {
	}

	public EvaluationDueDAOResult(List results) {
		super(results);
	}
}
