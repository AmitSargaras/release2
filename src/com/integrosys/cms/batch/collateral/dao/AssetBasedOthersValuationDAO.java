package com.integrosys.cms.batch.collateral.dao;

import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-5-31
 * @Tag com.integrosys.cms.batch.collateral.AssetBasedOthersValuationDAO.java
 */
public class AssetBasedOthersValuationDAO extends AbstractCollateralValuationDAO {
	private String SELECT_ASSET_BASED_OTHERS_TRX = "SELECT VW_COLLATERAL_VALUATION.* "
			+ " FROM VW_COLLATERAL_VALUATION,CMS_ASSET,CMS_STAGE_ASSET"
			+ " WHERE VW_COLLATERAL_VALUATION.S_CMS_COLLATERAL_ID = CMS_STAGE_ASSET.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.A_CMS_COLLATERAL_ID = CMS_ASSET.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.A_SECURITY_SUB_TYPE_ID <>" + "'"
			+ ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE + "'"
			+ " AND VW_COLLATERAL_VALUATION.A_SECURITY_SUB_TYPE_ID <>" + "'" + ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE
			+ "'" + " AND VW_COLLATERAL_VALUATION.A_SECURITY_SUB_TYPE_ID NOT IN ("
			+ " SELECT ENTRY_CODE from COMMON_CODE_CATEGORY, COMMON_CODE_CATEGORY_ENTRY	 "
			+ " WHERE COMMON_CODE_CATEGORY.CATEGORY_CODE =	 COMMON_CODE_CATEGORY_ENTRY.CATEGORY_CODE "
			+ " AND ((COMMON_CODE_CATEGORY_ENTRY.ENTRY_NAME = " + "'" + ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE
			+ "') " + " OR(COMMON_CODE_CATEGORY_ENTRY.ENTRY_NAME = " + "'" + ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE
			+ "')) " + ")" + " AND VW_COLLATERAL_VALUATION.SECURITY_TYPE = 'AB'";

	protected String getSelectStatement() {
		return SELECT_ASSET_BASED_OTHERS_TRX;
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
