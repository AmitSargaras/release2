package com.integrosys.cms.app.bridgingloan.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since May 28, 2005 Tag: $Name$
 */
public interface IDisbursementDetail extends Serializable {

	public long getDisburseDetailID();

	public void setDisburseDetailID(long disburseDetailID);

	public long getDisbursementID();

	public void setDisbursementID(long disbursementID);

	public Date getDisbursementDate();

	public void setDisbursementDate(Date disbursementDate);

	public String getSubpurpose();

	public void setSubpurpose(String subpurpose);

	public String getInvoiceNumber();

	public void setInvoiceNumber(String invoiceNumber);

	public Amount getInvoiceAmount();

	public void setInvoiceAmount(Amount invoiceAmount);

	public Amount getDisburseAmount();

	public void setDisburseAmount(Amount disburseAmount);

	public String getDisbursementMode();

	public void setDisbursementMode(String disbursementMode);

	public String getPayee();

	public void setPayee(String payee);

	public String getReferenceNumber();

	public void setReferenceNumber(String referenceNumber);

	public String getGlReferenceNumber();

	public void setGlReferenceNumber(String glReferenceNumber);

	public String getRemarks();

	public void setRemarks(String remarks);

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef();

	public void setCommonRef(long commonRef);

	public boolean getIsDeletedInd();

	public void setIsDeletedInd(boolean isDeletedInd);
}
