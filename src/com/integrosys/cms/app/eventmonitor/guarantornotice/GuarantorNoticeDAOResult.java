/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/guarantornotice/GuarantorNoticeDAOResult.java,v 1.9 2005/05/18 09:39:20 wltan Exp $
 */

package com.integrosys.cms.app.eventmonitor.guarantornotice;

import java.util.List;

import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAOResult;

public class GuarantorNoticeDAOResult extends AbstractMonitorDAOResult {

	private GuarantorNoticeDAOResult() {
	}

	public GuarantorNoticeDAOResult(List results) {
		super(results);
	}
}
