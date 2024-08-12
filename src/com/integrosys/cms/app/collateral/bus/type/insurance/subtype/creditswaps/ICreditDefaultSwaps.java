/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/insurance/subtype/creditswaps/ICreditDefaultSwaps.java,v 1.4 2004/01/16 08:23:45 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditswaps;

import java.util.Date;

import com.integrosys.cms.app.collateral.bus.type.insurance.IInsuranceCollateral;

/**
 * This interface represents Insurance of type Credit Default Swaps.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/01/16 08:23:45 $ Tag: $Name: $
 */
public interface ICreditDefaultSwaps extends IInsuranceCollateral {
	/**
	 * Get date of ISDA Master Agreement.
	 * 
	 * @return Date
	 */
	public Date getISDADate();

	/**
	 * Set date of ISDA Master Agreement.
	 * 
	 * @param iSDADate of type Date
	 */
	public void setISDADate(Date iSDADate);

	/**
	 * Get date of treasury documentation.
	 * 
	 * @return Date
	 */
	public Date getTreasuryDocDate();

	/**
	 * Set date of treasury documentation.
	 * 
	 * @param treasuryDocDate of type Date
	 */
	public void setTreasuryDocDate(Date treasuryDocDate);

	/**
	 * Get bank risk approval confirmation.
	 * 
	 * @return String
	 */
	public String getIsBankRiskConfirmation();

	/**
	 * Set bank risk approval confirmation.
	 * 
	 * @param isBankRiskConfirmation of type String
	 */
	public void setIsBankRiskConfirmation(String isBankRiskConfirmation);

	/**
	 * Get credit derivatives instrument description.
	 * 
	 * @return String
	 */
	public String getDescription();

	/**
	 * Set credit derivatives instrument description.
	 * 
	 * @param description of type String
	 */
	public void setDescription(String description);
	
	public String getArrInsurer();
	
	public void setArrInsurer(String arrInsurer);
}
