/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.math.BigDecimal;

/**
 * This interface represents a GMRA Deal.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IGMRADeal extends IDeal {

	/**
	 * Get Security Desc
	 * 
	 * @return String
	 */
	public String getSecDesc();

	/**
	 * Set Security Desc.
	 * 
	 * @param secDesc of type String
	 */
	public void setSecDesc(String secDesc);

	/**
	 * Get ISIN Code
	 * 
	 * @return String
	 */
	public String getISINCode();

	/**
	 * Set ISIN Code
	 * 
	 * @param iSINCode of type String
	 */
	public void setISINCode(String iSINCode);

	/**
	 * Get CUSIP Code
	 * 
	 * @return String
	 */
	public String getCUSIPCode();

	/**
	 * Set CUSIP Code
	 * 
	 * @param cusipCode of type String
	 */
	public void setCUSIPCode(String cusipCode);

	/**
	 * Get haircut
	 * 
	 * @return Double
	 */
	public Double getHaircut();

	/**
	 * Set haircut
	 * 
	 * @param haircut of type Double
	 */
	public void setHaircut(Double haircut);

	/**
	 * Get deal country
	 * 
	 * @return String
	 */
	public String getDealCountry();

	/**
	 * Set deal country
	 * 
	 * @param dealCountry of type string
	 */
	public void setDealCountry(String dealCountry);

	/**
	 * Get deal branch
	 * 
	 * @return String
	 */
	public String getDealBranch();

	/**
	 * Set deal branch
	 * 
	 * @param dealBranch of type string
	 */
	public void setDealBranch(String dealBranch);

	/**
	 * Get deal rate
	 * 
	 * @return Double
	 */
	public Double getDealRate();

	/**
	 * Set deal rate
	 * 
	 * @param dealRate of type Double
	 */
	public void setDealRate(Double dealRate);

	/**
	 * Get repo amount start cash
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getRepoStartAmt();

	/**
	 * Set repo amount start cash
	 * 
	 * @param repoStartAmt of type BigDecimal
	 */
	public void setRepoStartAmt(BigDecimal repoStartAmt);

	/**
	 * Get repo amount end cash
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getRepoEndAmt();

	/**
	 * Set repo amount end cash
	 * 
	 * @param repoEndAmt of type BigDecimal
	 */
	public void setRepoEndAmt(BigDecimal repoEndAmt);

}
