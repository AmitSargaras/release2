package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Mar 1, 2007 Time: 2:54:44 PM
 * To change this template use File | Settings | File Templates.
 */
// EBFeeDetailsBean
public interface IFeeDetails extends Serializable {

	public long getFeeDetailsID();

	public void setFeeDetailsID(long feeDetailsID);

	public Date getEffectiveDate();

	public void setEffectiveDate(Date effectiveDate);

	public Date getExpirationDate();

	public void setExpirationDate(Date expirationDate);

	public Amount getAmountCGC();

	public void setAmountCGC(Amount amountCGC);

	public Amount getAmountFee();

	public void setAmountFee(Amount amountFee);

	public int getTenor();

	public void setTenor(int tenor);

	public long getRefID();

	public void setRefID(long refID);

	public String getStatus();

	public void setStatus(String status);

	public String getTenorFreq();

	public void setTenorFreq(String tenorFreq);
	
	public Date getFeePaymentDateCGC();

	public void setFeePaymentDateCGC(Date feePaymentDateCGC);

	// public String getGuaranteeCcyCode();
	// public void setGuaranteeCcyCode(String guaranteeCcyCode);

}
