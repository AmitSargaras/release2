package com.integrosys.cms.app.collateral.bus.valuation.support;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.integrosys.cms.app.collateral.bus.valuation.dao.IPropertyValuationDAO;

public class PropertyProfileSingleton implements ValuationProfileSingleton {
	private static PropertyProfileSingleton instance;

	private List profiles;

	private HashMap piProfilesMap;

	private IPropertyValuationDAO propertyValuationDao;

	private boolean initialized;

	public void setPropertyValuationDao(IPropertyValuationDAO propertyValuationDao) {
		this.propertyValuationDao = propertyValuationDao;
	}

	public IPropertyValuationDAO getPropertyValuationDao() {
		return propertyValuationDao;
	}

	private PropertyProfileSingleton() {
	}

	public void init() {
		if (!initialized) {
			profiles = new ArrayList();
			piProfilesMap = new HashMap();
			propertyValuationDao.retrieveValuationProfile(profiles);
			propertyValuationDao.retrieveValuationProfileByPropIndex(piProfilesMap);
			initialized = true;
		}
	}

	public static PropertyProfileSingleton getInstance() {
		if (instance == null) {
			synchronized (PropertyProfileSingleton.class) {
				if (instance == null) {
					instance = new PropertyProfileSingleton();
				}
			}
		}
		return instance;
	}

	public List getProfiles() {
		return profiles;
	}

	public void clearProfiles() {
		profiles.clear();
	}

	public void reloadProfiles() {
		profiles.clear();
		propertyValuationDao.retrieveValuationProfile(profiles);
		reloadPIProfiles();
	}

	public void reloadPIProfiles() {
		piProfilesMap.clear();
		propertyValuationDao.retrieveValuationProfileByPropIndex(piProfilesMap);
	}

	public HashMap getPiProfilesMap() {
		return piProfilesMap;
	}

	public void setPiProfilesMap(HashMap piProfilesMap) {
		this.piProfilesMap = piProfilesMap;
	}
}
