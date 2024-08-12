package com.integrosys.cms.app.contractfinancing.bus;

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
public interface IFDR extends Serializable {

	public long getFdrID();

	public void setFdrID(long fdrID);

	public Date getFdrDate();

	public void setFdrDate(Date fdrDate);

	public String getAccountNo();

	public void setAccountNo(String accountNo);

	public Amount getFdrAmount();

	public void setFdrAmount(Amount fdrAmount);

	public String getReferenceNo();

	public void setReferenceNo(String referenceNo);

	public String getRemarks();

	public void setRemarks(String remarks);

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef();

	public void setCommonRef(long commonRef);

	public boolean getIsDeleted();

	public void setIsDeleted(boolean isDeleted);

}
