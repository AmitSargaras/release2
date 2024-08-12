package com.integrosys.cms.app.collateral.bus.valuation.support;

import java.util.Map;

import com.integrosys.cms.app.collateral.bus.valuation.dao.IVehicleValuationDAO;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Sep 9, 2008 Time: 6:27:14 PM To
 * change this template use File | Settings | File Templates.
 */
public class VehicleFeedProfileSingleton implements ValuationProfileSingleton {

	private static VehicleFeedProfileSingleton instance;

	private Map profile;

	private IVehicleValuationDAO vehicleValuationDao;

	private boolean initialized;

	public void setVehicleValuationDao(IVehicleValuationDAO vehicleValuationDao) {
		this.vehicleValuationDao = vehicleValuationDao;
	}

	public IVehicleValuationDAO getVehicleValuationDao() {
		return vehicleValuationDao;
	}

	private VehicleFeedProfileSingleton() {
	}

	public void init() {
		if (!initialized) {
			profile = vehicleValuationDao.retrieveFeedInfo();
			initialized = true;
		}
	}

	public static VehicleFeedProfileSingleton getInstance() {
		if (instance == null) {
			synchronized (VehicleFeedProfileSingleton.class) {
				instance = new VehicleFeedProfileSingleton();
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
		profile = vehicleValuationDao.retrieveFeedInfo();
	}

}
