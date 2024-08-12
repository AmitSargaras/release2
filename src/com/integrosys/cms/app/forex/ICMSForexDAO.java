/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/forex/ICMSForexDAO.java,v 1.1 2003/08/05 11:34:53 hltan Exp $
 */
package com.integrosys.cms.app.forex;

//ofa
import com.integrosys.base.businfra.forex.ExchangeRate;
import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * This interface defines the constant specific to the cms forex table and the
 * methods required by the cms forex
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/05 11:34:53 $ Tag: $Name: $
 */
public interface ICMSForexDAO extends ICMSForexTableConstants {

	/**
	 * Get the list of exchange rates without caching
	 * @return ExchangeRate[] - the list of exchange rate info
	 * @throws SearchDAOException on errors
	 */
	public ExchangeRate[] getExchangeRatesNoCache() throws SearchDAOException;

	/**
	 * Get the list of exchange rates
	 * @return ExchangeRates[] - the list of exchange rate info
	 * @throws SearchDAOException on errors
	 */
	public ExchangeRate[] getExchangeRates() throws SearchDAOException;
}