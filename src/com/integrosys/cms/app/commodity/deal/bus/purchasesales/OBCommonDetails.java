/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/purchasesales/OBCommonDetails.java,v 1.5 2004/08/06 06:30:25 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.purchasesales;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.common.Quantity;

/**
 * This class represents common information of purchase and sales.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.5 $
 * @since $Date: 2004/08/06 06:30:25 $ Tag: $Name: $
 */
public class OBCommonDetails implements ICommonDetails {
	private String referenceNo;

	private Date shipDate;

	private Date expiryDate;

	private String shipmentSource;

	private String shipmentDestination;

	private String transportationDocumentNo;

	private String paymentMode;

	private String bankName;

	private boolean isClaimAllowed;

	private int noDaysClaimed;

	private Quantity quantity;

	private Amount unitPrice;

	private String remarks;

	protected OBCommonDetails() {
	}

	protected OBCommonDetails(ICommonDetails iValue) {
		if (iValue != null) {
			AccessorUtil.copyValue(iValue, this);
		}
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getShipmentSource() {
		return shipmentSource;
	}

	public void setShipmentSource(String source) {
		this.shipmentSource = source;
	}

	public String getShipmentDestination() {
		return shipmentDestination;
	}

	public void setShipmentDestination(String destination) {
		this.shipmentDestination = destination;
	}

	public String getTransportationDocumentNo() {
		return transportationDocumentNo;
	}

	public void setTransportationDocumentNo(String docNo) {
		this.transportationDocumentNo = docNo;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public boolean getIsClaimAllowed() {
		return isClaimAllowed;
	}

	public void setIsClaimAllowed(boolean isAllowed) {
		this.isClaimAllowed = isAllowed;
	}

	public int getNoDaysClaimed() {
		return noDaysClaimed;
	}

	public void setNoDaysClaimed(int noDaysClaimed) {
		this.noDaysClaimed = noDaysClaimed;
	}

	public Quantity getQuantity() {
		return quantity;
	}

	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}

	public Amount getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Amount unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
