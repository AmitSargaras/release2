package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This class represents the insurance summary for the general charge asset.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2006/09/13 12:16:59 $ Tag: $Name: $
 */
public interface IInsuranceSummary extends Serializable {
	public String getRefID();

	public void setRefID(String refID);

	public String getPolicyNumber();

	public void setPolicyNumber(String policyNumber);

	public boolean isExpired();

	public void setExpired(boolean expired);

	public Amount getInsuredAmount();

	public void setInsuredAmount(Amount insuredAmount);

	public Amount getCoverageAmount();

	public void setCoverageAmount(Amount coverageAmount);

	public String toString();
}
