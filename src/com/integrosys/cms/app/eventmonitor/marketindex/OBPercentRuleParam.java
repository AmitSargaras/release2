/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/marketindex/OBPercentRuleParam.java,v 1.3 2003/09/29 03:44:36 btchng Exp $
 */

package com.integrosys.cms.app.eventmonitor.marketindex;

import com.integrosys.cms.app.eventmonitor.OBRuleParam;

/**
 * Rule parameter contain percentage as a parameter
 */
public class OBPercentRuleParam extends OBRuleParam {
	private double percent;

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}
}
