package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;

public interface IBhavCopy extends Serializable {
	
	/**
	 * @return the scCode
	 */
	public long getScCode() ;

	/**
	 * @param scCode the scCode to set
	 */
	public void setScCode(long scCode);

	/**
	 * @return the scName
	 */
	public String getScName();

	/**
	 * @param scName the scName to set
	 */
	public void setScName(String scName);

	/**
	 * @return the scGroup
	 */
	public String getScGroup();

	/**
	 * @param scGroup the scGroup to set
	 */
	public void setScGroup(String scGroup) ;

	/**
	 * @return the openValue
	 */
	public Double getOpenValue() ;

	/**
	 * @param openValue the openValue to set
	 */
	public void setOpenValue(Double openValue);

	/**
	 * @return the closeValue
	 */
	public Double getCloseValue() ;

	/**
	 * @param closeValue the closeValue to set
	 */
	public void setCloseValue(Double closeValue);

	/**
	 * @return the lastValue
	 */
	public Double getLastValue();

	/**
	 * @param lastValue the lastValue to set
	 */
	public void setLastValue(Double lastValue) ;

}
