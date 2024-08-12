/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collaborationtask/CollaborationTaskDAOResult.java,v 1.6 2005/05/19 03:37:11 eliliana Exp $
 */

package com.integrosys.cms.app.eventmonitor.collaborationtask;

import java.util.List;

import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAOResult;

public class CollaborationTaskDAOResult extends AbstractMonitorDAOResult {

	private CollaborationTaskDAOResult() {
	}

	public CollaborationTaskDAOResult(List results) {
		super(results);
	}
}
