/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/RuleParamUtil.java,v 1.1 2003/09/02 02:00:11 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * Utility class to get attributes for constructing rule parameters
 */
public abstract class RuleParamUtil {

	private static final String RULE_PARAM_KEY_PREFIX = "cms.eventmonitor.ruleparam";

	public static int getInt(String ruleID, String key) {
		return PropertyManager.getInt(RULE_PARAM_KEY_PREFIX + "." + ruleID + "." + key);
	}

	public static String getString(String ruleID, String key) {
		return PropertyManager.getValue(RULE_PARAM_KEY_PREFIX + "." + ruleID + "." + key);
	}

	public static double getDouble(String ruleID, String key) {
		return PropertyManager.getDouble(RULE_PARAM_KEY_PREFIX + "." + ruleID + "." + key);
	}

}
