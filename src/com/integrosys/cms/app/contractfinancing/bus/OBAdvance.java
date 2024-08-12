package com.integrosys.cms.app.contractfinancing.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.exception.ChainedException;
import com.integrosys.cms.app.commodity.common.AmountConversionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class OBAdvance implements IAdvance {

	private long advanceID = ICMSConstant.LONG_INVALID_VALUE;

	private long facilityTypeID = ICMSConstant.LONG_INVALID_VALUE;

	private String facilityType;

	private String referenceNo;

	private Date drawdownDate;

	private String tenorUOM;

	private int tenor;

	private Amount amount;

	private float facilityTypeMOA = ICMSConstant.FLOAT_INVALID_VALUE;

	private Amount actualAdvanceAmount;

	private Date expiryDate;

	private IPayment[] paymentList = null;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isDeleted;

	/**
	 * Default Constructor
	 */
	public OBAdvance() {
	}

	public long getAdvanceID() {
		return advanceID;
	}

	public void setAdvanceID(long advanceID) {
		this.advanceID = advanceID;
	}

	public long getFacilityTypeID() {
		return facilityTypeID;
	}

	public void setFacilityTypeID(long facilityTypeID) {
		this.facilityTypeID = facilityTypeID;
	}

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Date getDrawdownDate() {
		return drawdownDate;
	}

	public void setDrawdownDate(Date drawdownDate) {
		this.drawdownDate = drawdownDate;
	}

	public String getTenorUOM() {
		return tenorUOM;
	}

	public void setTenorUOM(String tenorUOM) {
		this.tenorUOM = tenorUOM;
	}

	public int getTenor() {
		return tenor;
	}

	public void setTenor(int tenor) {
		this.tenor = tenor;
	}

	public float getFacilityTypeMOA() {
		return facilityTypeMOA;
	}

	public void setFacilityTypeMOA(float facilityTypeMOA) {
		this.facilityTypeMOA = facilityTypeMOA;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public Amount getActualAdvanceAmount() {
		return actualAdvanceAmount;
	}

	public void setActualAdvanceAmount(Amount actualAdvanceAmount) {
		this.actualAdvanceAmount = actualAdvanceAmount;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean deleted) {
		isDeleted = deleted;
	}

	public IPayment[] getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(IPayment[] paymentList) {
		this.paymentList = paymentList;
	}

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef() {
		return commonRef;
	}

	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/*
	 * ==================================== // Derived Fields
	 * =====================================
	 */
	public Amount getAdvanceAmount() {
		if (amount == null) {
			return null;
		}

		return new Amount(amount.getAmount() * facilityTypeMOA / 100, amount.getCurrencyCode());
	}

	public Amount getBalanceAmount() throws AmountConversionException {
		if (amount == null) {
			return null;
		}

		Amount totalReceived = getTotalPaymentReceived();
		Amount totalDistributed = getTotalPaymentDistributed();
		Amount advanceAmount = getAdvanceAmount();

		try {
			if (CommonUtil.compareAmount(totalReceived, advanceAmount) == CommonUtil.GREATER) {
				Amount outstandingAmt = totalReceived.subtract(totalDistributed);
				if (outstandingAmt.getAmount() != 0) {
					return outstandingAmt;
				}
				return amount.subtract(totalDistributed);
			}
			else {
				return advanceAmount.subtract(totalDistributed);
			}

		}
		catch (ChainedException e) {
			throw new AmountConversionException(e.getMessage());
		}
	}

	/**
	 * Gets the outstanding amount. This is calculated by subtracting the total
	 * payment received from the amount. Total payment received would be
	 * calculated in "base" currency (amount's currency) before subtracting.
	 * 
	 * @return outstanding amount
	 * @throws AmountConversionException - thrown when there is no exchange rate
	 *         is available to does the currency conversion
	 */
	public Amount getOutstandingAmount() throws AmountConversionException {
		if (amount == null) {
			return null;
		}
		return CommonUtil.subtractAmount(amount, getTotalPaymentReceived());
	}

	/**
	 * Gets the operative limit. This method will only return operative limit =
	 * 0 when total payment is equal or greater than the advance amount,
	 * otherwise, the advance amount would be returned.
	 * 
	 * @return 0 if total payment equals or exceeds advance amount; advance
	 *         amount otherwise
	 * @throws AmountConversionException - thrown when there is no exchange rate
	 *         is available to does the currency conversion
	 */
	public Amount getOperativeLimitZeroWhenFullPayment() throws AmountConversionException {
		if (amount == null) {
			return null;
		}
		Amount totalPayment = getTotalPaymentReceived();
		Amount advanceAmt = getAdvanceAmount();
		int result = CommonUtil.compareAmount(totalPayment, getAdvanceAmount());

		if (result == CommonUtil.LESS) {
			return advanceAmt;
		}
		else {
			return new Amount(0, advanceAmt.getCurrencyCode());
		}
	}

	/**
	 * Gets the operative limit. This method will return operative limit = 0
	 * when at least $1 had been paid (at least 1 payment). otherwise, the
	 * actual advance amount would be returned.
	 * 
	 * @return 0 if total payment equals or exceeds $1; actual advance amount
	 *         otherwise
	 * @throws AmountConversionException - thrown when there is no exchange rate
	 *         is available to does the currency conversion
	 */
	public Amount getOperativeLimitZeroWhenPartialPayment() throws AmountConversionException {
		if (amount == null) {
			return null;
		}
		Amount totalPayment = getTotalPaymentReceived();

		if (totalPayment != null) {
			if (totalPayment.getAmount() > 0) {
				return new Amount(0, amount.getCurrencyCode());
			}
		}

		return getActualAdvanceAmount();
	}

	/**
	 * This will return the total payment received in the currency of the
	 * (advanced) amount. Currency of (advanced) amount is treated as the "base"
	 * currency.
	 * 
	 * @return total payment received
	 * @throws AmountConversionException - thrown when there is no exchange rate
	 *         is available to does the currency conversion
	 */
	public Amount getTotalPaymentReceived() throws AmountConversionException {
		if (amount == null) {
			return null;
		}

		Amount total = new Amount(0, amount.getCurrencyCode());
		if (paymentList != null) {
			for (int i = 0; i < paymentList.length; i++) {
				total = CommonUtil.addAmount(total, paymentList[i].getReceiveAmount());
			}
		}
		return total;
	}

	/**
	 * This will return the total payment distributed in the currency of the
	 * (advanced) amount. Currency of (advanced) amount is treated as the "base"
	 * currency.
	 * 
	 * @return total payment distributed
	 * @throws AmountConversionException - thrown when there is no exchange rate
	 *         is available to does the currency conversion
	 */
	public Amount getTotalPaymentDistributed() throws AmountConversionException {
		if (amount == null) {
			return null;
		}

		Amount total = new Amount(0, amount.getCurrencyCode());
		if (paymentList != null) {
			for (int i = 0; i < paymentList.length; i++) {
				total = CommonUtil.addAmount(total, paymentList[i].getDistributeAmount());
			}
		}
		return total;
	}
}