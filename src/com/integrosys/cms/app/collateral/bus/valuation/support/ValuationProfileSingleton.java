package com.integrosys.cms.app.collateral.bus.valuation.support;

/**
 * <p>
 * Profile Singleton to be used by the
 * {@link com.integrosys.cms.app.collateral.bus.valuation.IValuator}, storing
 * information such feed info, index info, profile to be used throughout the
 * whole process of valuation on a collateral type.
 * @author Chong Jun Yong
 * 
 */
public interface ValuationProfileSingleton {
	/**
	 * To initialize the singleton, ie, to load the feed info, index info,
	 * profile from persistent storage.
	 */
	public void init();

	/**
	 * To reload all the feed info, index info, profiles which previously loaded
	 * by {@link #init()}.
	 */
	public void reloadProfiles();
}
