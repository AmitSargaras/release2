/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/IRuleParam.java,v 1.4 2004/03/03 03:33:13 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor;

import java.io.Serializable;

/**
 * @author $Author: phtan $
 * @version $Revision: 1.4 $ Empty super interface for rule param To be
 *          overriden by subclasses for actual parameters
 */
public interface IRuleParam extends Serializable {

	String getRuleID();

	String getCountryCode();

	boolean hasCountryCode();

	void setRuleID(String ruleId);

	void setRuleNum(int i);

	void setCountryCode(String countryCode);

	int getRuleNum();

}
