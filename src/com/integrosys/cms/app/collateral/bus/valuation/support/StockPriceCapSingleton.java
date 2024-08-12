/*
 * Created on May 24, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus.valuation.support;

import com.integrosys.cms.app.collateral.bus.valuation.dao.IMarketableValuationDAO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @deprecated
 * 
 *             TODO To change the template for this generated type comment go to
 *             Window - Preferences - Java - Code Style - Code Templates
 */
public class StockPriceCapSingleton implements ValuationProfileSingleton, IMarketableProfileSingleton {
	private static StockPriceCapSingleton instance;

	private IMarketableValuationDAO marketableValuationDao;

	private Map priceCapSetting;

	private boolean initialized;

	public IMarketableValuationDAO getMarketableValuationDao() {
		return marketableValuationDao;
	}

	public void setMarketableValuationDao(IMarketableValuationDAO marketableValuationDao) {
		this.marketableValuationDao = marketableValuationDao;
	}

	private StockPriceCapSingleton() {
	}

	public void init() {
		if (!initialized) {
			priceCapSetting = new HashMap();
			try {
				marketableValuationDao.retrievePriceCapSetup(priceCapSetting);
				initialized = true;
			}
			catch (Exception ex) {
			}
		}
	}

	public static StockPriceCapSingleton getInstance() {
		if (instance == null) {
			synchronized (StockPriceCapSingleton.class) {
				if (instance == null) {
					instance = new StockPriceCapSingleton();
				}
			}
		}
		return instance;
	}

	public Double getPriceCapSetting(String boardType) {
		return (Double) (priceCapSetting.get(boardType));
	}

	public void reloadData() {
		clearData();
		marketableValuationDao.retrievePriceCapSetup(priceCapSetting);
	}

	public void clearData() {
		priceCapSetting.clear();
	}

	public Object getData(String key) {
		return getPriceCapSetting(key);
	}

	public Map getData() {
		return priceCapSetting;
	}

	public void reloadProfiles() {
		reloadData();
	}

}
