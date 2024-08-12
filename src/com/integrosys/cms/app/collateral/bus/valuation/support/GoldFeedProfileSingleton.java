package com.integrosys.cms.app.collateral.bus.valuation.support;

import java.util.Map;

import com.integrosys.cms.app.collateral.bus.valuation.dao.IGoldValuationDAO;

/**
 * @author Cynthia Zhou
 */
public class GoldFeedProfileSingleton implements ValuationProfileSingleton {

	private static GoldFeedProfileSingleton instance;

	private Map profile;

	private IGoldValuationDAO goldValuationDao;

	private boolean initialized;

	/**
	 * @return the goldValuationDao
	 */
	public IGoldValuationDAO getGoldValuationDao() {
		return goldValuationDao;
	}

	/**
	 * @param goldValuationDao the goldValuationDao to set
	 */
	public void setGoldValuationDao(IGoldValuationDAO goldValuationDao) {
		this.goldValuationDao = goldValuationDao;
	}

	private GoldFeedProfileSingleton() {
	}

	public void init() {
		if (!initialized) {
			profile = goldValuationDao.retrieveFeedInfo();
			initialized = true;
		}
	}

	public static GoldFeedProfileSingleton getInstance() {
		if (instance == null) {
			synchronized (GoldFeedProfileSingleton.class) {
				instance = new GoldFeedProfileSingleton();
			}
		}
		return instance;
	}

	public Map getProfile() {
		init();
		return profile;
	}

	public void clearProfile() {
		profile.clear();
	}

	public void reloadProfiles() {
		clearProfile();

		profile = goldValuationDao.retrieveFeedInfo();

	}

}
