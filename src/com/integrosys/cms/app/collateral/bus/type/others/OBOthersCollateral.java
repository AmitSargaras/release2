/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/
 */
package com.integrosys.cms.app.collateral.bus.type.others;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateral;

/**
 * This class represents a Collateral of type Others entity.
 * 
 * @author $Author: visveswari $<br>
 * @since $Date: 2004/06/22 08:53:26 $ Tag: $Name: $
 */
public class OBOthersCollateral extends OBCollateral implements IOthersCollateral {
	private String envRiskyStatus;

	private Date envRiskyDate;

	private String envRiskyRemarks;

	private String description;

	private boolean isPhysicalInspection;

	private int physicalInspectionFreq;

	private String physicalInspectionFreqUnit;

	private Date lastPhysicalInspectDate;

	private Date nextPhysicalInspectDate;

	private Amount minimalValue;
	
	public double unitsNumber;
	
	private String goodStatus;
	
	
	
	// private String securityOthers;

	public String getGoodStatus() {
		return goodStatus;
	}

	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}

	public double getUnitsNumber() {
		return unitsNumber;
	}

	public void setUnitsNumber(double unitsNumber) {
		this.unitsNumber = unitsNumber;
	}

	/**
	 * Default Constructor.
	 */
	public OBOthersCollateral() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IOthersCollateral
	 */
	public OBOthersCollateral(IOthersCollateral obj) {
		this();
		AccessorUtil.copyValue(obj, this);
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
	 * Get security environmentally risky. Possible values: High, Medium, Low.
	 * 
	 * @return String
	 */
	public String getEnvRiskyStatus() {
		return envRiskyStatus;
	}

	/**
	 * Set security environmentally risky. Possible values: High, Medium, Low.
	 * 
	 * @param envRiskyStatus is of type String
	 */
	public void setEnvRiskyStatus(String envRiskyStatus) {
		this.envRiskyStatus = envRiskyStatus;
	}

	/**
	 * Get date the collateral confirmed as environmentally risky.
	 * 
	 * @return Date
	 */
	public Date getEnvRiskyDate() {
		return envRiskyDate;
	}

	/**
	 * Set date the collateral confirmed as environmentally risky.
	 * 
	 * @param envRiskyDate is of type Date
	 */
	public void setEnvRiskyDate(Date envRiskyDate) {
		this.envRiskyDate = envRiskyDate;
	}

	/**
	 * Get remarks for Security Environmentally Risky.
	 * 
	 * @return String
	 */
	public String getEnvRiskyRemarks() {
		return envRiskyRemarks;
	}

	/**
	 * Set remarks for Security Environmentally Risky.
	 * 
	 * @param envRiskyRemarks is of type String
	 */
	public void setEnvRiskyRemarks(String envRiskyRemarks) {
		this.envRiskyRemarks = envRiskyRemarks;
	}

	/**
	 * Get description of collateral.
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set description of collateral.
	 * 
	 * @param description of type String
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get if it is physical inspection.
	 * 
	 * @return boolean
	 */
	public boolean getIsPhysicalInspection() {
		return isPhysicalInspection;
	}

	/**
	 * Set if it is physical inspection.
	 * 
	 * @param isPhysicalInspection of type boolean
	 */
	public void setIsPhysicalInspection(boolean isPhysicalInspection) {
		this.isPhysicalInspection = isPhysicalInspection;
	}

	/**
	 * Get physical inspection frequency.
	 * 
	 * @return int
	 */
	public int getPhysicalInspectionFreq() {
		return physicalInspectionFreq;
	}

	/**
	 * Set physical inspection frequency.
	 * 
	 * @param physicalInspectionFreq is of type int
	 */
	public void setPhysicalInspectionFreq(int physicalInspectionFreq) {
		this.physicalInspectionFreq = physicalInspectionFreq;
	}

	/**
	 * Get physical inspection frequency unit.
	 * 
	 * @return String
	 */
	public String getPhysicalInspectionFreqUnit() {
		return physicalInspectionFreqUnit;
	}

	/**
	 * Set physical inspection frequency unit.
	 * 
	 * @param physicalInspectionFreqUnit of type String
	 */
	public void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit) {
		this.physicalInspectionFreqUnit = physicalInspectionFreqUnit;
	}

	/**
	 * Get last physical inspection date.
	 * 
	 * @return Date
	 */
	public Date getLastPhysicalInspectDate() {
		return lastPhysicalInspectDate;
	}

	/**
	 * Set last physical inspection date.
	 * 
	 * @param lastPhysicalInspectDate of type Date
	 */
	public void setLastPhysicalInspectDate(Date lastPhysicalInspectDate) {
		this.lastPhysicalInspectDate = lastPhysicalInspectDate;
	}

	/**
	 * Get next physical inspection date.
	 * 
	 * @return Date
	 */
	public Date getNextPhysicalInspectDate() {
		return nextPhysicalInspectDate;
	}

	/**
	 * Set next physical inspection date.
	 * 
	 * @param nextPhysicalInspectDate of type Date
	 */
	public void setNextPhysicalInspectDate(Date nextPhysicalInspectDate) {
		this.nextPhysicalInspectDate = nextPhysicalInspectDate;
	}

	/**
	 * Get Minimal Value Required.
	 * 
	 * @return Amount
	 */
	public Amount getMinimalValue() {
		return minimalValue;
	}
	
	/**
	 * Set minimalvalue.
	 * 
	 * @param minimalValue of type Amount
	 */
	
	public void setMinimalValue(Amount minimalValue) {
		this.minimalValue = minimalValue;
	}

	/**
	 * Get Security Type - Others .
	 * 
	 * @return String
	 */
	// public String getSecurityOthers() {
	// return securityOthers;
	// }
	/**
	 * Set Security Type - Others .
	 * 
	 * @param remarks of type String
	 */
	// public void setSecurityOthers(String securityOthers) {
	// this.securityOthers = securityOthers;
	// }

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
		else if (!(obj instanceof OBOthersCollateral)) {
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