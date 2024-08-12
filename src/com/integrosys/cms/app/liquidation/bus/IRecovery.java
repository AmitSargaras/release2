/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.liquidation.bus;

import java.io.Serializable;
import java.util.Collection;

/**
 * This interface represents Recovery Income
 * 
 * @author $Author: lini $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IRecovery extends Serializable {
	// public long getLiquidationID();
	// public void setLiquidationID(long liquidationID);
	public Long getRecoveryID();

	public void setRecoveryID(Long recoveryID);

	public String getRecoveryType();

	public void setRecoveryType(String recoveryType);

	public String getRemarks();

	public void setRemarks(String remarks);

	public String getStatus();

	public void setStatus(String status);

	public long getVersionTime();

	public void setVersionTime(long versionTime);

	public Collection getRecoveryIncome();

	public void setRecoveryIncome(Collection incomeColl);

	public void setRefID(long refID);

	public long getRefID();

}
