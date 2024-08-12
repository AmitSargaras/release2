/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/IGeneralChargeSubTypeSummary.java,v 1.1 2006/09/13 12:16:59 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents valuation details of the Asset of type General
 * Charge.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2006/09/13 12:16:59 $ Tag: $Name: $
 */
public interface IGeneralChargeSubTypeSummary extends Serializable {

	public String getID();

	public void setID(String id);

	public String getAddress();

	public void setAddress(String address);

	public String getValuerName();

	public void setValuerName(String valuerName);

	public Date getValuationDate();

	public void setValuationDate(Date valuationDate);

	public Amount getGrossValue();

	public void setGrossValue(Amount grossValue);

	public Amount getNetValue();

	public void setNetValue(Amount netValue);

	public Amount getTotalInsrCoverageAmt();

	public void setTotalInsrCoverageAmt(Amount totalInsrCoverageAmt);

	public Amount getRecoverableAmount();

	public void setRecoverableAmount(Amount recoverableAmount);

	public OBInsuranceSummary[] getInsuranceSummary();

	public void setInsuranceSummary(OBInsuranceSummary[] insuranceSummary);

	public String getCompareResultKey();

	public void setCompareResultKey(String compareResultKey);

	public String toString();
}
