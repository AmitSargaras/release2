package com.integrosys.cms.app.collateral.bus.valuation.support;

import com.integrosys.cms.app.collateral.bus.valuation.dao.IVehicleValuationDAO;

import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Sep 9, 2008 Time: 6:27:14 PM To
 * change this template use File | Settings | File Templates.
 */
public class CommonCodeRegionSingleton implements ValuationProfileSingleton {

	private static CommonCodeRegionSingleton instance;

	private Map profile;

	private IVehicleValuationDAO vehicleValuationDao;

	private boolean initialized;

	public void setVehicleValuationDao(IVehicleValuationDAO vehicleValuationDao) {
		this.vehicleValuationDao = vehicleValuationDao;
	}

	public IVehicleValuationDAO getVehicleValuationDao() {
		return vehicleValuationDao;
	}

	private CommonCodeRegionSingleton() {
	}

	public void init() {
		if (!initialized) {
			profile = vehicleValuationDao.retrieveRegionInfo();
			initialized = true;
		}
	}

	public static CommonCodeRegionSingleton getInstance() {
		if (instance == null) {
			synchronized (CommonCodeRegionSingleton.class) {
				instance = new CommonCodeRegionSingleton();
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
		profile = vehicleValuationDao.retrieveRegionInfo();
	}

}
