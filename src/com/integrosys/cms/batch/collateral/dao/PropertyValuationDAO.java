package com.integrosys.cms.batch.collateral.dao;

import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-5-31
 * @Tag com.integrosys.cms.batch.collateral.PropertyValuationDAO.java
 */
public class PropertyValuationDAO extends AbstractCollateralValuationDAO {
	private String SELECT_PROPERTY_TRX = "SELECT VW_COLLATERAL_VALUATION.*"
			+ " FROM VW_COLLATERAL_VALUATION,CMS_PROPERTY,CMS_STAGE_PROPERTY"
			+ " WHERE VW_COLLATERAL_VALUATION.S_CMS_COLLATERAL_ID = CMS_STAGE_PROPERTY.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.A_CMS_COLLATERAL_ID = CMS_PROPERTY.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.SECURITY_TYPE = 'PT'";

	protected String getSelectStatement() {
		return SELECT_PROPERTY_TRX;
	}

	protected boolean isValuationReqired(ICollateral collateral) {
		return true;
	}

	protected void synchronizeCollateralCMVFSV(ICollateralTrxValue collTrx) throws Exception {
		CollateralValuator valuator = new CollateralValuator();
		valuator.setCollateralCMVFSV(collTrx.getCollateral());
		valuator.setCollateralCMVFSV(collTrx.getStagingCollateral());
	}
}
