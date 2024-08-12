/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.liquidation.bus;

/**
 * Entity bean implementation for staging RecoveryIncome entity.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public abstract class EBRecoveryIncomeStagingBean extends EBRecoveryIncomeBean {
	public abstract void setStatus(String status);

	protected boolean isStaging() {
		return true;
	}
}