package com.integrosys.cms.app.common.bus;

/**
 * This class represent an area - its size and unit of measurement
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface IArea {

	double getAreaSize();

	void setAreaSize(double areaSize);

	String getUnitOfMeasurement();

	void setUnitOfMeasurement(String unitOfMeasurement);
}
