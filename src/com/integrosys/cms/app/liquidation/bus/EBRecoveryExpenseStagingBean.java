/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.liquidation.bus;

/**
 * Entity bean implementation for staging RecoveryExpense entity.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public abstract class EBRecoveryExpenseStagingBean extends EBRecoveryExpenseBean {
	public abstract void setStatus(String status);

	public abstract String getStatus();

	protected boolean isStaging() {
		return true;
	}
}