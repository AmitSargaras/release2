/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor;

/**
 * This interface defines the actions that should be part of monitor rule..
 * 
 * @author $Author: phtan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/18 06:27:32 $ Tag: $Name: $
 */
public interface IMonRule {
	public String evaluateRule(IRuleParam ruleParam, Object anObject);
}
