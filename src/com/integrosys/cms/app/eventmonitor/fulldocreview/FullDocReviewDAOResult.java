/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/fulldocreview/FullDocReviewDAOResult.java,v 1.5 2005/05/18 09:39:20 wltan Exp $
 */

package com.integrosys.cms.app.eventmonitor.fulldocreview;

import java.util.List;

import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAOResult;

public class FullDocReviewDAOResult extends AbstractMonitorDAOResult {

	private FullDocReviewDAOResult() {
	}

	public FullDocReviewDAOResult(List results) {
		super(results);
	}
}
