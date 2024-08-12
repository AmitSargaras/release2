/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/forex/SBCMSForexManagerBean.java,v 1.1 2003/08/05 11:34:53 hltan Exp $
 */
package com.integrosys.cms.app.forex;

//ofa
import com.integrosys.base.businfra.forex.ExchangeRate;
import com.integrosys.base.businfra.forex.SBForexManagerBean;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Session bean for CMS forex
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/05 11:34:53 $
 */

public class SBCMSForexManagerBean extends SBForexManagerBean {

	public ExchangeRate[] getExchangeRates() {
		try {
			ExchangeRate[] rates = null;
			rates = CMSForexDAOFactory.getCMSForexDAO().getExchangeRates();
			return rates;
		}
		catch (SearchDAOException ex) {
			DefaultLogger.error(this, "Exception in getExchangeRates: ", ex);
			return null;
		}
	}

	public ExchangeRate[] getExchangeRatesNoCache() {
		try {
			ExchangeRate[] rates = null;
			rates = CMSForexDAOFactory.getCMSForexDAO().getExchangeRatesNoCache();
			return rates;
		}
		catch (SearchDAOException ex) {
			DefaultLogger.error(this, "Exception in getExchangeRatesNoCache: ", ex);
			return null;
		}
	}

}
