package com.integrosys.cms.app.collateral.bus.valuation.support;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

import com.integrosys.cms.app.collateral.bus.ICollateralSubType;

/**
 * A listener to be notified by <b>application</b> or <b>batch job</b> on reload
 * or initialize the valuation profile singleton instances.
 * @author Chong Jun Yong
 * @see ValuationProfileSingleton
 */
public class ValuationProfileSingletonListener {

	private Map collateralTypeCodeSingletonsMap;

	private Map collateralSubTypeCodeSingletonsMap;

	/**
	 * Constructor to provide 2 maps, one is collateral type to list of
	 * singleton map, another one is collateral subtype to list of singleton
	 * map.
	 * @param collateralTypeCodeSingletonsMap key is collateral type, value is
	 *        list of singletons
	 * @param collateralSubTypeCodeSingletonsMap key is collateral subtype,
	 *        value is list of singletons
	 */
	public ValuationProfileSingletonListener(Map collateralTypeCodeSingletonsMap, Map collateralSubTypeCodeSingletonsMap) {
		this.collateralTypeCodeSingletonsMap = collateralTypeCodeSingletonsMap;
		this.collateralSubTypeCodeSingletonsMap = collateralSubTypeCodeSingletonsMap;
	}

	/**
	 * To reload singleton that match the collateral subtype supplied
	 * @param collateralSubType collateral subtype having type and subtype code.
	 */
	public void reloadSingleton(ICollateralSubType collateralSubType) {
		Validate.notNull(collateralSubType, "Collateral Subtype must be provided.");

		execute(new SingletonsCallback() {
			public void doInSingleton(ValuationProfileSingleton singleton) {
				singleton.reloadProfiles();
			}
		}, collateralSubType);
	}

	/**
	 * To initialize singleton that match the collateral subtype supplied
	 * @param collateralSubType collateral subtype having type and subtype code.
	 */
	public void initSingleton(ICollateralSubType collateralSubType) {
		Validate.notNull(collateralSubType, "Collateral Subtype must be provided.");

		execute(new SingletonsCallback() {
			public void doInSingleton(ValuationProfileSingleton singleton) {
				singleton.init();
			}
		}, collateralSubType);
	}

	public void reloadAllSingletons() {
		if (collateralTypeCodeSingletonsMap != null) {
			Collection allSingletonsList = collateralTypeCodeSingletonsMap.values();
			if (allSingletonsList != null && !allSingletonsList.isEmpty()) {
				for (Iterator itr = allSingletonsList.iterator(); itr.hasNext();) {
					List singletons = (List) itr.next();
					for (Iterator itrSingleton = singletons.iterator(); itrSingleton.hasNext();) {
						ValuationProfileSingleton singleton = (ValuationProfileSingleton) itrSingleton.next();
						singleton.reloadProfiles();
					}
				}
			}
		}

		if (collateralSubTypeCodeSingletonsMap != null) {
			Collection allSingletonsList = collateralSubTypeCodeSingletonsMap.values();
			if (allSingletonsList != null && !allSingletonsList.isEmpty()) {
				for (Iterator itr = allSingletonsList.iterator(); itr.hasNext();) {
					List singletons = (List) itr.next();
					for (Iterator itrSingleton = singletons.iterator(); itrSingleton.hasNext();) {
						ValuationProfileSingleton singleton = (ValuationProfileSingleton) itrSingleton.next();
						singleton.reloadProfiles();
					}
				}
			}
		}
	}

	/**
	 * With the supplied collateral subtype, to retrieve all singletons that
	 * matched with it, then work on it. Either <b>reload</b> or
	 * <b>initialize</b> it.
	 * @param action a call back action on every singletons passed in
	 * @param collateralSubType collateral subtype, to be used to determine
	 *        which valuation profile singleton to be used.
	 */
	private void execute(SingletonsCallback action, ICollateralSubType collateralSubType) {
		String collateralTypeCode = collateralSubType.getTypeCode();
		String collateralSubTypeCode = collateralSubType.getSubTypeCode();

		if (collateralTypeCodeSingletonsMap != null) {
			List singletons = (List) this.collateralTypeCodeSingletonsMap.get(collateralTypeCode);
			if (singletons != null && !singletons.isEmpty()) {
				for (Iterator itr = singletons.iterator(); itr.hasNext();) {
					ValuationProfileSingleton singleton = (ValuationProfileSingleton) itr.next();
					action.doInSingleton(singleton);
				}
			}
		}

		if (collateralSubTypeCodeSingletonsMap != null) {
			List singletons = (List) this.collateralSubTypeCodeSingletonsMap.get(collateralSubTypeCode);
			if (singletons != null && !singletons.isEmpty()) {
				for (Iterator itr = singletons.iterator(); itr.hasNext();) {
					ValuationProfileSingleton singleton = (ValuationProfileSingleton) itr.next();
					action.doInSingleton(singleton);
				}
			}
		}
	}

	/**
	 * Callback interface to work on every singleton that matched the collateral
	 * subtype supplied.
	 */
	private interface SingletonsCallback {
		/**
		 * To work on the singleton passed in.
		 * @param singleton instance of valuation profile singleton
		 */
		public void doInSingleton(ValuationProfileSingleton singleton);
	}
}
