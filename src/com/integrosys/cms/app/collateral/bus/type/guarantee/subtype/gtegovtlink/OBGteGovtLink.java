package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.guarantee.OBGuaranteeCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;

public class OBGteGovtLink extends OBGuaranteeCollateral implements IGteGovtLink {

	/*
	 * private IFeeDetails[] FeeDetails;
	 * 
	 * public IFeeDetails[] getFeeDetails() { return FeeDetails; }
	 * 
	 * public void setFeeDetails(IFeeDetails[] IFeeDetails) { this.FeeDetails =
	 * IFeeDetails; }
	 */

	private Date cancellationDateLG;
	
	
	public OBGteGovtLink() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_GUARANTEE_GOVT_LINK));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IInterBranchIndemnity
	 */
	public OBGteGovtLink(IGteGovtLink obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	public Date getCancellationDateLG() {
		return cancellationDateLG;
	}

	public void setCancellationDateLG(Date cancellationDateLG) {
		this.cancellationDateLG = cancellationDateLG;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBGteGovtLink)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}