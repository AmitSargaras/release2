package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents the insurance summary for the general charge asset.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/09/13 12:18:00 $ Tag: $Name: $
 */
public class OBInsuranceSummary implements IInsuranceSummary {
	String refID;

	String policyNumber;

	boolean isExpired = false;

	Amount insuredAmount; // entire policy

	Amount coverageAmount; // coverage for this stock/fao

	public String getRefID() {
		return refID;
	}

	public void setRefID(String refID) {
		this.refID = refID;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean expired) {
		isExpired = expired;
	}

	public Amount getInsuredAmount() {
		return insuredAmount;
	}

	public void setInsuredAmount(Amount insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	public Amount getCoverageAmount() {
		return coverageAmount;
	}

	public void setCoverageAmount(Amount coverageAmount) {
		this.coverageAmount = coverageAmount;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
