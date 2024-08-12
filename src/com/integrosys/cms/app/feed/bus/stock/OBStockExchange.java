/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/OBStockExchange.java,v 1.1 2003/09/17 03:53:27 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stock;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/17 03:53:27 $ Tag: $Name: $
 */
public class OBStockExchange implements IStockExchange {

	public String getStockExchangeCode() {
		return stockExchangeCode;
	}

	public void setStockExchangeCode(String stockExchangeCode) {
		this.stockExchangeCode = stockExchangeCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getStockExchangeName() {
		return stockExchangeName;
	}

	public void setStockExchangeName(String stockExchangeName) {
		this.stockExchangeName = stockExchangeName;
	}

	private String stockExchangeCode;

	private String stockExchangeName;

	private String countryCode;

}
