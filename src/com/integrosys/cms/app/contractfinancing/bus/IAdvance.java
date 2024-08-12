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
public interface IAdvance extends Serializable {

	public long getAdvanceID();

	public void setAdvanceID(long advanceID);

	public long getFacilityTypeID();

	public void setFacilityTypeID(long facilityTypeID);

	public String getFacilityType();

	public void setFacilityType(String facilityType);

	public String getReferenceNo();

	public void setReferenceNo(String referenceNo);

	public Date getDrawdownDate();

	public void setDrawdownDate(Date drawdownDate);

	public String getTenorUOM();

	public void setTenorUOM(String tenorUOM);

	public int getTenor();

	public void setTenor(int tenor);

	public float getFacilityTypeMOA();

	public void setFacilityTypeMOA(float facilityTypeMOA);

	public Amount getAmount();

	public void setAmount(Amount amount);

	public Amount getActualAdvanceAmount();

	public void setActualAdvanceAmount(Amount actualAdvanceAmount);

	public Date getExpiryDate();

	public void setExpiryDate(Date expiryDate);

	public IPayment[] getPaymentList();

	public void setPaymentList(IPayment[] paymentList);

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef();

	public void setCommonRef(long commonRef);

	public boolean getIsDeleted();

	public void setIsDeleted(boolean isDeleted);

	/*
	 * ==================================== // Derived Fields
	 * =====================================
	 */
	/*
	 * public Amount getAdvanceAmount();
	 * 
	 * //Only applicable to MBS public Amount getActualAdvanceAmount();
	 * 
	 * public Amount getBalanceAmount();
	 * 
	 * public Amount getTransferAmount();
	 * 
	 * public Amount getOutstandingAmount();
	 * 
	 * public Amount getOperativeLimit();
	 */
}
