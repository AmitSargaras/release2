/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/
 */
package com.integrosys.cms.app.collateral.bus.type.others;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailBean;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;

/**
 * Entity bean implementation for others collateral type.
 * 
 * @author $Author: lyng $<br>
 * @version
 * @since $Date: 2005/08/15 09:20:07 $ Tag: $Name: $
 */
public abstract class EBOthersCollateralBean extends EBCollateralDetailBean implements IOthersCollateral {
	/**
	 * Get collateral ID.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return getEBCollateralID().longValue();
	}

	/**
	 * set collateral ID.
	 * 
	 * @param collateralID is of type long
	 */
	public void setCollateralID(long collateralID) {
		setEBCollateralID(new Long(collateralID));
	}

	/**
	 * Get if it is physical inspection.
	 * 
	 * @return boolean
	 */
	public boolean getIsPhysicalInspection() {
		String isInspect = getEBIsPhysicalInspection();
		if ((isInspect != null) && isInspect.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if it is physical inspection.
	 * 
	 * @param isPhysicalInspection of type boolean
	 */
	public void setIsPhysicalInspection(boolean isPhysicalInspection) {
		if (isPhysicalInspection) {
			setEBIsPhysicalInspection(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsPhysicalInspection(ICMSConstant.FALSE_VALUE);
		}
	}

	public double getUnitsNumber() {
		if (getEBUnitsNumber()!=null) return (getEBUnitsNumber().doubleValue());
		return ICMSConstant.DOUBLE_INVALID_VALUE;
	}

	public void setUnitsNumber(double unitsNumber) {
		if (unitsNumber!=ICMSConstant.DOUBLE_INVALID_VALUE) setEBUnitsNumber(new Double(unitsNumber));
		else setEBUnitsNumber(null);
	}
	/**
	 * Get nominal value.
	 * 
	 * @return Amount
	 */
	public Amount getMinimalValue() {
		if (getEBMinimalValue() != null) {
			return new Amount(getEBMinimalValue().doubleValue(), currencyCode);
		}
		else {
			return null;
		}
	}

	/**
	 * Set minimalvalue.
	 * 
	 * @param minimalValue of type Amount
	 */
	public void setMinimalValue(Amount minimalValue) {
		if (minimalValue != null) {
			setEBMinimalValue(new Double(minimalValue.getAmountAsDouble()));
		}
		else {
			setEBMinimalValue(null);
		}
	}

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract String getEBIsPhysicalInspection();

	public abstract void setEBIsPhysicalInspection(String eBIsPhysicalInspection);

	public abstract Double getEBMinimalValue();

	public abstract void setEBMinimalValue(Double minimalValue);

    public abstract String getEnvRiskyStatus();

    public abstract void setEnvRiskyStatus(String envRiskyStatus);

    public abstract Date getEnvRiskyDate();

    public abstract void setEnvRiskyDate(Date envRiskyDate);

    public abstract String getEnvRiskyRemarks();

    public abstract void setEnvRiskyRemarks(String envRiskyRemarks);

    public abstract String getDescription();

    public abstract void setDescription(String description);

    public abstract int getPhysicalInspectionFreq();

    public abstract void setPhysicalInspectionFreq(int physicalInspectionFreq);

    public abstract String getPhysicalInspectionFreqUnit();

    public abstract void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit);

    public abstract Date getLastPhysicalInspectDate();

    public abstract void setLastPhysicalInspectDate(Date lastPhysicalInspectDate);

    public abstract Date getNextPhysicalInspectDate();

    public abstract void setNextPhysicalInspectDate(Date nextPhysicalInspectDate);
    
    public abstract Double getEBUnitsNumber();
    
    public abstract void setEBUnitsNumber(Double eBUnitsNumber);
    
    
    public abstract String getGoodStatus();

	public abstract void setGoodStatus(String goodStatus) ;
   

}
