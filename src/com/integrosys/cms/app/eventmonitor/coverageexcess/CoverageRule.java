/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/coverageexcess/CoverageRule.java,v 1.5 2003/09/19 09:14:59 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.coverageexcess;

import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorConstant;
import com.integrosys.cms.app.eventmonitor.IRuleParam;

/**
 * @author $Author: phtan $
 * @version $Revision: 1.5 $ Always return true
 */
public class CoverageRule implements IMonRule, java.io.Serializable {
	public String evaluateRule(IRuleParam ruleParam, Object anObject) {
		// no further filtering required.
		/*
		 * OBCoverageInfo info = (OBCoverageInfo) anObject; ILimit limits[] =
		 * info.getLimitProfile().getLimits(); boolean hasCollateral = false;
		 * 
		 * // check if clean line, if clean line then don't fire event. if
		 * (limits != null) { for (int ii=0; ii<limits.length; ii++) { if
		 * (limits[ii].getCollateralAllocations() != null &&
		 * limits[ii].getCollateralAllocations().length != 0) { hasCollateral =
		 * true; break; } } }
		 * 
		 * 
		 * if (hasCollateral) { return IMonitorConstant.EVENT_TRUE; }
		 */
		return IMonitorConstant.EVENT_TRUE;
	}
}
