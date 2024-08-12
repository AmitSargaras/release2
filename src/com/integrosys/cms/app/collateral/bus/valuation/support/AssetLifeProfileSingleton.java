package com.integrosys.cms.app.collateral.bus.valuation.support;

import com.integrosys.cms.app.collateral.bus.valuation.dao.IStrtLineValuationDAO;

import java.util.Map;

/**
 * @author Cynthia Zhou
 */
public class AssetLifeProfileSingleton implements ValuationProfileSingleton {

	private static AssetLifeProfileSingleton instance;

	private Map profile;

	private IStrtLineValuationDAO strtLineValuationDao;

	private boolean initialized;

	/**
	 * @return the strtLineValuationDao
	 */
	public IStrtLineValuationDAO getStrtLineValuationDao() {
		return strtLineValuationDao;
	}

	/**
	 * @param strtLineValuationDao the strtLineValuationDao to set
	 */
	public void setStrtLineValuationDao(IStrtLineValuationDAO strtLineValuationDao) {
		this.strtLineValuationDao = strtLineValuationDao;
	}

	private AssetLifeProfileSingleton() {
	}

	public void init() {
		if (!initialized) {
			profile = strtLineValuationDao.retrieveAssetLife();
			initialized = true;
		}
	}

	public static AssetLifeProfileSingleton getInstance() {
		if (instance == null) {
			synchronized (AssetLifeProfileSingleton.class) {
				instance = new AssetLifeProfileSingleton();
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
		profile = strtLineValuationDao.retrieveAssetLife();
	}

}
