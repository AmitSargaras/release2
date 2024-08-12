/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/
 */
package com.integrosys.cms.app.collateral.bus.type.others;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;

/**
 * This interface represents a Collateral of type Others
 * 
 * @author $Author: visveswari $<br>
 * @since $Date: 2004/06/22 08:53:12 $ Tag: $Name: $
 */

public interface IOthersCollateral extends ICollateral {
	/**
	 * Get Security Environmental Risk . Possible values: High, Medium, Low.
	 * 
	 * @return String
	 */
	public String getEnvRiskyStatus();

	/**
	 * Set Security Environmental Risk . Possible values: High, Medium, Low.
	 * 
	 * @param envRiskyStatus is of type String
	 */
	public void setEnvRiskyStatus(String envRiskyStatus);

	/**
	 * Get Date Security confirmed as Environmentally Risky==== date the
	 * collateral confirmed as environmentally risky.
	 * 
	 * @return Date
	 */
	public Date getEnvRiskyDate();

	/**
	 * Set Date Security confirmed as Environmentally Risky==== date the
	 * collateral confirmed as environmentally risky.
	 * 
	 * @param envRiskyDate is of type Date
	 */
	public void setEnvRiskyDate(Date envRiskyDate);

	/**
	 * Get Remarks for Security Environmental Risk.
	 * 
	 * @return String
	 */
	public String getEnvRiskyRemarks();

	/**
	 * Set Remarks for Security Environmental Risk
	 * 
	 * @param envRiskyRemarks is of type String
	 */
	public void setEnvRiskyRemarks(String envRiskyRemarks);

	/**
	 * Get Description of Collateral
	 * 
	 * @return String
	 */
	public String getDescription();

	/**
	 * Set Description of Collateral
	 * 
	 * @param description of type String
	 */
	public void setDescription(String description);

	/**
	 * Get if it is physical inspection.
	 * 
	 * @param isPhysicalInspection of type boolean
	 */
	public boolean getIsPhysicalInspection();

	/**
	 * Set if it is physical inspection.
	 * 
	 * @param isPhysicalInspection of type boolean
	 */
	public void setIsPhysicalInspection(boolean isPhysicalInspection);

	/**
	 * Get Physical Inspection Frequency.
	 * 
	 * @return int
	 */
	public int getPhysicalInspectionFreq();

	/**
	 * Set Physical Inspection Frequency.
	 * 
	 * @param physicalInspectionFreq is of type int
	 */
	public void setPhysicalInspectionFreq(int physicalInspectionFreq);

	/**
	 * Get physical inspection frequency unit.
	 * 
	 * @return String
	 */
	public String getPhysicalInspectionFreqUnit();

	/**
	 * Set physical inspection frequency unit.
	 * 
	 * @param physicalInspectionFreqUnit of type String
	 */
	public void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit);

	/**
	 * Get Last Physical Inspection date.
	 * 
	 * @return Date
	 */

	public Date getLastPhysicalInspectDate();

	/**
	 * Set Last Physical Inspection date.
	 * 
	 * @param lastPhysicalInspectDate of type Date
	 */
	public void setLastPhysicalInspectDate(Date lastPhysicalInspectDate);

	/**
	 * Get Next Physical Inspection date.
	 * 
	 * @return Date
	 */
	public Date getNextPhysicalInspectDate();

	/**
	 * Set Next Physical Inspection date.
	 * 
	 * @param nextPhysicalInspectDate of type Date
	 */
	public void setNextPhysicalInspectDate(Date nextPhysicalInspectDate);

	public double getUnitsNumber();
	
	public void setUnitsNumber(double unitsNumber);
	
	/**
	 * Get Minimal Value Required.
	 * 
	 * @return Amount
	 */
	public Amount getMinimalValue();

	/**
	 * Set Minimal Value Required
	 * 
	 * @param minimalValue of type Amount
	 */
	public void setMinimalValue(Amount minimalValue);

	/**
	 * Get Security Type - Others .
	 * 
	 * @return String
	 */
	// public String getSecurityOthers();
	/**
	 * Set Security Type - Others .
	 * 
	 * @param remarks of type String
	 */
	// public void setSecurityOthers(String securityOthers);

	public String getGoodStatus();

	public void setGoodStatus(String goodStatus) ;
}
