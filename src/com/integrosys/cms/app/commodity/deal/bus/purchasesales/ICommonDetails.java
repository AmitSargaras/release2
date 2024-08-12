package com.integrosys.cms.app.commodity.deal.bus.purchasesales;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.commodity.common.Quantity;

/**
 * This interface represents common details of purchase and sales.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/06 06:30:25 $ Tag: $Name: $
 */
public interface ICommonDetails extends Serializable {
	public String getReferenceNo();

	public void setReferenceNo(String referenceNo);

	public Date getShipDate();

	public void setShipDate(Date shipDate);

	public Date getExpiryDate();

	public void setExpiryDate(Date expiryDate);

	public String getShipmentSource();

	public void setShipmentSource(String source);

	public String getShipmentDestination();

	public void setShipmentDestination(String destination);

	public String getTransportationDocumentNo();

	public void setTransportationDocumentNo(String docNo);

	public String getPaymentMode();

	public void setPaymentMode(String paymentMode);

	public String getBankName();

	public void setBankName(String bankName);

	public boolean getIsClaimAllowed();

	public void setIsClaimAllowed(boolean isAllowed);

	public int getNoDaysClaimed();

	public void setNoDaysClaimed(int noDaysClaimed);

	public Quantity getQuantity();

	public void setQuantity(Quantity quantity);

	public Amount getUnitPrice();

	public void setUnitPrice(Amount unitPrice);

	public String getRemarks();

	public void setRemarks(String remarks);
}
