package com.integrosys.cms.app.collateral.bus;


public class OBBhavCopy implements IBhavCopy{

	private static final long serialVersionUID = -2154544894001201940L;
	
	private long scCode;
	
	private String scName;
	
	private String scGroup;
	
	private Double openValue;
	
	private Double closeValue;
	
	private Double lastValue;

	/**
	 * @return the scCode
	 */
	public long getScCode() {
		return scCode;
	}

	/**
	 * @param scCode the scCode to set
	 */
	public void setScCode(long scCode) {
		this.scCode = scCode;
	}

	/**
	 * @return the scName
	 */
	public String getScName() {
		return scName;
	}

	/**
	 * @param scName the scName to set
	 */
	public void setScName(String scName) {
		this.scName = scName;
	}

	/**
	 * @return the scGroup
	 */
	public String getScGroup() {
		return scGroup;
	}

	/**
	 * @param scGroup the scGroup to set
	 */
	public void setScGroup(String scGroup) {
		this.scGroup = scGroup;
	}

	/**
	 * @return the openValue
	 */
	public Double getOpenValue() {
		return openValue;
	}

	/**
	 * @param openValue the openValue to set
	 */
	public void setOpenValue(Double openValue) {
		this.openValue = openValue;
	}

	/**
	 * @return the closeValue
	 */
	public Double getCloseValue() {
		return closeValue;
	}

	/**
	 * @param closeValue the closeValue to set
	 */
	public void setCloseValue(Double closeValue) {
		this.closeValue = closeValue;
	}

	/**
	 * @return the lastValue
	 */
	public Double getLastValue() {
		return lastValue;
	}

	/**
	 * @param lastValue the lastValue to set
	 */
	public void setLastValue(Double lastValue) {
		this.lastValue = lastValue;
	}

}
