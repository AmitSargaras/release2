package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink;

import java.util.Date;

import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;

public interface IGteGovtLink extends IGuaranteeCollateral

{
	public Date getCancellationDateLG();

	public void setCancellationDateLG(Date cancellationDateLG);
	
	/**
	 * Get Fee Details information.
	 * 
	 * @return a list of feeDetails
	 */
	// public IFeeDetails[] getFeeDetails();
	/**
	 * set Fee Details information.
	 * 
	 * @param feeDetails a list of cash deposit
	 */
	// public void setFeeDetails(IFeeDetails[] feeDetails);
}