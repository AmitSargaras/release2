package com.integrosys.cms.batch.collateral.dao;

import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-5-31
 * @Tag com.integrosys.cms.batch.collateral.ICollateralValuationDAO.java
 */
public interface ICollateralValuationDAO {
	public ICollateralTrxValue[] getCollateralTrxValues(String countryCode) throws CollateralException;

	public void updateCollateralValuation(ICollateralTrxValue collTrxValue) throws CollateralException;
}
