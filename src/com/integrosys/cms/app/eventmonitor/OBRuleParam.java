/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/OBRuleParam.java,v 1.6 2004/04/07 07:29:56 sathish Exp $
 */

package com.integrosys.cms.app.eventmonitor;

/**
 * Base RuleParam class
 */
public class OBRuleParam implements IRuleParam {

	private int ruleNum;

	private String ruleID;

	private String countryCode;

	public int getRuleNum() {
		return ruleNum;
	}

	public void setRuleNum(int ruleNum) {
		this.ruleNum = ruleNum;
	}

	public String getRuleID() {
		return ruleID;
	}

	public void setRuleID(String ruleID) {
		this.ruleID = ruleID;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public boolean hasCountryCode() {
		/* Provide a detailed Implementation */
		if (countryCode == null) {
			return false;
		}
		if (countryCode.equals("")) {
			return false;
		}
		return true;
		/*
		 * if (!(countryCode == null && "".equals(countryCode))) { return true;
		 * }
		 * 
		 * return false;
		 */
	}
}
