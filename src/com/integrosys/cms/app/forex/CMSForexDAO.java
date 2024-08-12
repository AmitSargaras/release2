/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/forex/CMSForexDAO.java,v 1.4 2005/09/19 09:02:07 czhou Exp $
 */
package com.integrosys.cms.app.forex;

//java
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.forex.ExchangeRate;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This is the default DAO object which contains the SQL statement(s) required
 * for getting forex rates for CMS. More statements may be added subsequently
 * @author Vikram Ramakrishnan
 * @version $Revision: 1.4 $
 * @since $Date: 2005/09/19 09:02:07 $
 */
public class CMSForexDAO implements ICMSForexDAO {
	private static final double DEFAULT_BUY_RATE = 1.0000;

	private static final double DEFAULT_SELL_RATE = 1.0000;

	private static final int DEFAULT_BUY_UNITS = 1;

	private static final int DEFAULT_SELL_UNITS = 1;

	private static String EXCHANGERATE_QUERY_STRING = "SELECT CMS_FOREX.FEED_ID, CMS_FOREX.BUY_CURRENCY, CMS_FOREX.SELL_CURRENCY, "
			+ "CMS_FOREX.BUY_RATE, CMS_FOREX.SELL_RATE, CMS_FOREX.BUY_UNIT, CMS_FOREX.SELL_UNIT, "
			+ "CMS_FOREX.EFFECTIVE_DATE FROM CMS_FEED_GROUP, CMS_FOREX WHERE "
			+ "CMS_FOREX.FEED_GROUP_ID = CMS_FEED_GROUP.FEED_GROUP_ID AND CMS_FEED_GROUP.GROUP_TYPE = 'FOREX'";

	private static ArrayList cachedList = null;

	private static boolean cached = false;

	private static long lastReloadTime = 0;

	private static String PROPERTYFILE_KEY = "integrosys.internal.reloader.reloadTimeInterval";

	/**
	 * Default Constructor
	 */
	public CMSForexDAO() {
	}

	/**
	 * Get the list of exchange rates without caching
	 * @return ExchangeRate[] - the list of exchange rate info
	 * @throws SearchDAOException on errors
	 */
	public ExchangeRate[] getExchangeRatesNoCache() throws SearchDAOException {
		cached = false;
		return (getExchangeRates());
	}

	/**
	 * Get the list of exchange rates with caching
	 * @return ExchangeRate[] - the list of exchange rate info
	 * @throws SearchDAOException on errors
	 */
	public ExchangeRate[] getExchangeRates() throws SearchDAOException {
		DBUtil dbUtil = null;
		ResultSet rs = null;

		if (reloadExchangeRate()) {
			try {

				String sql = EXCHANGERATE_QUERY_STRING;
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				rs = dbUtil.executeQuery();
				ArrayList exchangeRateList = new ArrayList();
				while (rs.next()) {

					long forexRateID = rs.getLong(FOREXTBL_FEED_ID);

					// String rateInstanceName =
					// rs.getString(COL_RATE_INSTANCE_NAME);

					// if ( rs.wasNull() )
					// rateInstanceName = TT_RATE_INSTANCE_NAME;

					String buyCurrency = rs.getString(FOREXTBL_BUY_CURRENCY);

					String sellCurrency = rs.getString(FOREXTBL_SELL_CURRENCY);

					double buyRate = rs.getDouble(FOREXTBL_BUY_RATE);
					if (rs.wasNull()) {
						buyRate = DEFAULT_BUY_RATE;
					}

					double sellRate = rs.getDouble(FOREXTBL_SELL_RATE);
					if (rs.wasNull()) {
						sellRate = DEFAULT_SELL_RATE;
					}

					int buyUnits = rs.getInt(FOREXTBL_BUY_UNIT);
					if (rs.wasNull()) {
						buyUnits = DEFAULT_BUY_UNITS;
					}

					int sellUnits = rs.getInt(FOREXTBL_SELL_UNIT);
					if (rs.wasNull()) {
						sellUnits = DEFAULT_SELL_UNITS;
					}

					// Purposely did the swapping of the buy and sell currency
					// pairs due to the way
					// it is being persisted
					ExchangeRate er = new ExchangeRate(ICMSConstant.FOREX_FEED_GROUP_TYPE, new CurrencyCode(
							sellCurrency), sellUnits, new CurrencyCode(buyCurrency), buyUnits, sellRate, buyRate);

					exchangeRateList.add(er);
				}

				cachedList = exchangeRateList;
				cached = true;
				return ((ExchangeRate[]) exchangeRateList.toArray(new ExchangeRate[exchangeRateList.size()]));
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQL EXCEPTION at getExchangeRates", ex);
			}
			catch (Exception ex) {
				throw new SearchDAOException("Exception at getExchangeRates" + ex.toString());
			}

			finally {
				try {
					dbUtil.close();
				}
				catch (SQLException ex) {
					throw new SearchDAOException("SQLException in getExchangeRates", ex);
				}
			}

		}
		// DefaultLogger.debug(this,
		// "============= RETRIEVING FROM CACHE ==============");
		return ((ExchangeRate[]) cachedList.toArray(new ExchangeRate[cachedList.size()]));
	}

	private boolean reloadExchangeRate() {
		/*
		 * String maxTimeSinceLastReloadString =
		 * PropertyManager.getValue(PROPERTYFILE_KEY); Long
		 * maxTimeSinceLastReloadLong = new Long(maxTimeSinceLastReloadString);
		 * long maxTimeSinceLastReload = maxTimeSinceLastReloadLong.longValue();
		 * DefaultLogger.debug(this,
		 * "============= maxTimeSinceLastReload ==============" +
		 * maxTimeSinceLastReload);
		 * 
		 * java.util.Date current = DateUtil.getDate();
		 * 
		 * if ( (cachedList == null) || (cached == false) || (current.getTime()
		 * - lastReloadTime > maxTimeSinceLastReload)) { lastReloadTime =
		 * current.getTime(); return true; } return false;
		 */
		return true;
	}

}
