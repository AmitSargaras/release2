package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargegold;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Factory class for the SpecificChargeGold DAO
 */

public class SpecificChargeGoldDaoFactory {

	/**
	 * Get the SpecificChargeGold DAO
	 * 
	 * @return ISpecificChargeGoldDao - the interface class for accessing the DAO
	 * @throws SearchDAOException
	 */
	public static ISpecificChargeGoldDao getSpecificChargeGoldDao()throws SearchDAOException {
		return new SpecificChargeGoldDao();
	}

}
