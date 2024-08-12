/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.math.BigDecimal;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Data model holds a GMRA Deal.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBGMRADeal extends OBDeal implements IGMRADeal {
	private String secDesc;

	private String ISINCode;

	private String CUSIPCode;

	private Double haircut;

	private String dealCountry;

	private String dealBranch;

	private Double dealRate;

	private BigDecimal repoStartAmt;

	private BigDecimal repoEndAmt;

	/**
	 * Default Constructor.
	 */
	public OBGMRADeal() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IGMRADeal
	 */
	public OBGMRADeal(IGMRADeal obj) {
		this();
		AccessorUtil.copyValue(obj, this);

	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#getSecDesc
	 */
	public String getSecDesc() {
		return this.secDesc;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#setSecDesc
	 */
	public void setSecDesc(String secDesc) {
		this.secDesc = secDesc;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#getISINCode
	 */
	public String getISINCode() {
		return this.ISINCode;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#setISINCode
	 */
	public void setISINCode(String iSINCode) {
		this.ISINCode = iSINCode;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#getCUSIPCode
	 */
	public String getCUSIPCode() {
		return this.CUSIPCode;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#setCUSIPCode
	 */
	public void setCUSIPCode(String cusipCode) {
		this.CUSIPCode = cusipCode;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#getHaircut
	 */
	public Double getHaircut() {
		return this.haircut;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#setHaircut
	 */
	public void setHaircut(Double haircut) {
		this.haircut = haircut;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#getDealCountry
	 */
	public String getDealCountry() {
		return this.dealCountry;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#setDealCountry
	 */
	public void setDealCountry(String dealCountry) {
		this.dealCountry = dealCountry;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#getDealBranch
	 */
	public String getDealBranch() {
		return this.dealBranch;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#setDealBranch
	 */
	public void setDealBranch(String dealBranch) {
		this.dealBranch = dealBranch;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#getDealRate
	 */
	public Double getDealRate() {
		return this.dealRate;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#setDealRate
	 */
	public void setDealRate(Double dealRate) {
		this.dealRate = dealRate;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#getRepoStartAmt
	 */
	public BigDecimal getRepoStartAmt() {
		return this.repoStartAmt;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#setRepoStartAmt
	 */
	public void setRepoStartAmt(BigDecimal repoStartAmt) {
		this.repoStartAmt = repoStartAmt;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#getRepoEndAmt
	 */
	public BigDecimal getRepoEndAmt() {
		return this.repoEndAmt;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#setRepoEndAmt
	 */
	public void setRepoEndAmt(BigDecimal repoEndAmt) {
		this.repoEndAmt = repoEndAmt;
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
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBGMRADeal)) {
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