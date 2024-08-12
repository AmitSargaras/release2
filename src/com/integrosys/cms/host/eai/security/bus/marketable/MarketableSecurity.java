/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/MarketableSecurity.java,v 1.3 2003/12/08 07:03:29 lyng Exp $
 */
package com.integrosys.cms.host.eai.security.bus.marketable;

import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;

/**
 * This class represents approved security of type Marketable.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/12/08 07:03:29 $ Tag: $Name: $
 */
public class MarketableSecurity extends ApprovedSecurity {
	/**
	 * Default constructor.
	 */
	public MarketableSecurity() {
		super();
	}

	private Double cappedPrice;

	private String stockCounterCode;

	private Double interestRate;

	public Double getCappedPrice() {
		return cappedPrice;
	}

	public void setCappedPrice(Double cappedPrice) {
		this.cappedPrice = cappedPrice;
	}

	public String getStockCounterCode() {
		return stockCounterCode;
	}

	public void setStockCounterCode(String stockCounterCode) {
		this.stockCounterCode = stockCounterCode;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

}
