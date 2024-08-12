/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/IStockExchange.java,v 1.2 2003/09/18 07:25:31 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stock;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/18 07:25:31 $ Tag: $Name: $
 */
public interface IStockExchange extends java.io.Serializable {

	String getStockExchangeCode();

	String getStockExchangeName();

	String getCountryCode();

	void setStockExchangeCode(String stockExchangeCode);

	void setStockExchangeName(String stockExchangeName);

	void setCountryCode(String countryCode);

}
