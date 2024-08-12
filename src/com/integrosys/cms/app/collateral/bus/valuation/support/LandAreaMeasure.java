/*
 * Created on May 4, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus.valuation.support;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LandAreaMeasure implements Serializable {
	public static final String UOM_ACRES = "ACRES";

	public static final String UOM_HQT = "HQT";

	public static final String UOM_SQFT = "SQFT";

	public static final String UOM_SQM = "SQM";

	public static final String UOM_SQY = "SQY";

	private double from;

	private String fromUnit;

	private double to;

	private String toUnit;

	/**
	 * @return Returns the from.
	 */
	public double getFrom() {
		return from;
	}

	/**
	 * @param from The from to set.
	 */
	public void setFrom(double from) {
		this.from = from;
	}

	/**
	 * @return Returns the fromUnit.
	 */
	public String getFromUnit() {
		return fromUnit;
	}

	/**
	 * @param fromUnit The fromUnit to set.
	 */
	public void setFromUnit(String fromUnit) {
		this.fromUnit = fromUnit;
	}

	/**
	 * @return Returns the to.
	 */
	public double getTo() {
		return to;
	}

	/**
	 * @param to The to to set.
	 */
	public void setTo(double to) {
		this.to = to;
	}

	/**
	 * @return Returns the toUnit.
	 */
	public String getToUnit() {
		return toUnit;
	}

	/**
	 * @param toUnit The toUnit to set.
	 */
	public void setToUnit(String toUnit) {
		this.toUnit = toUnit;
	}
}
