package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink;

import javax.ejb.EJBLocalObject;

public interface EBFeeDetailsLocal extends EJBLocalObject {
	/**
	 * @return IFeeDetails
	 */

	public IFeeDetails getValue();

	/**
	 * @param feeDetails
	 */

	public void setValue(IFeeDetails feeDetails);

	/**
	 * @param status
	 */
	public void setStatus(String status);
}