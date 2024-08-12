package com.integrosys.cms.app.bridgingloan.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface ISettlement extends Serializable {

	public long getSettlementID();

	public void setSettlementID(long settlementID);

	public long getProjectID();

	public void setProjectID(long projectID);

	public Date getSettlementDate();

	public void setSettlementDate(Date settlementDate);

	public Amount getSettledAmount();

	public void setSettledAmount(Amount settledAmount);

	public Amount getOutstandingAmount();

	public void setOutstandingAmount(Amount outstandingAmount);

	public String getRemarks();

	public void setRemarks(String remarks);

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef();

	public void setCommonRef(long commonRef);

	public boolean getIsDeletedInd();

	public void setIsDeletedInd(boolean isDeleted);
}