package com.integrosys.cms.app.common.bus;

import java.io.Serializable;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class Area implements IArea, Serializable {

	private double areaSize = ICMSConstant.DOUBLE_INVALID_VALUE;

	private String unitOfMeasurement = null;

	/**
	 * Default Constructor
	 */
	public Area() {
	}

	public Area(double areaSize, String unitOfMeasurement) {
		this.areaSize = areaSize;
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public double getAreaSize() {
		return areaSize;
	}

	public void setAreaSize(double areaSize) {
		this.areaSize = areaSize;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

}
