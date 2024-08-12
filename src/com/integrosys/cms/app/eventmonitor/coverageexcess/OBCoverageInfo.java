/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/coverageexcess/OBCoverageInfo.java,v 1.1 2003/09/14 09:19:57 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.coverageexcess;

import com.integrosys.cms.app.eventmonitor.OBEventInfo;
import com.integrosys.cms.app.limit.bus.ILimitProfile;

public class OBCoverageInfo extends OBEventInfo {
	private long limitProfileID;

	private ILimitProfile limitProfile;

	public long getLimitProfileID() {
		return limitProfileID;
	}

	public void setLimitProfileID(long limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	public ILimitProfile getLimitProfile() {
		return limitProfile;
	}

	public void setLimitProfile(ILimitProfile limitProfile) {
		this.limitProfile = limitProfile;
	}
}
