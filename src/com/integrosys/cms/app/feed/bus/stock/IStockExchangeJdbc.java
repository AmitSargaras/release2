/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/IStockExchangeDAO.java,v 1.1 2003/09/17 03:53:27 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stock;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/17 03:53:27 $ Tag: $Name: $
 */
public interface IStockExchangeJdbc {

	/**
	 * Gets all the stock exchanges' info.
	 * @return An array of element where each element represent a stock
	 *         exchange's information. This array can have zero elements.
	 */
	IStockExchange[] getAllStockExchanges();
}
