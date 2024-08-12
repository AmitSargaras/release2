package com.integrosys.cms.batch.collateral.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealDAO;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDealDAOConstants;
import com.integrosys.cms.app.commodity.deal.bus.OBCommodityDeal;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-5-31
 * @Tag com.integrosys.cms.batch.collateral.CommodityValuationDAO.java
 */
public class CommodityValuationDAO extends AbstractCollateralValuationDAO implements ICollateralValuationDAO,
		ICommodityDealDAOConstants {
	private String SELECT_CMDT_TRX = "SELECT VW_COLLATERAL_VALUATION.*"
			+ " FROM VW_COLLATERAL_VALUATION,CMS_COMMODITY,CMS_STAGE_COMMODITY"
			+ " WHERE VW_COLLATERAL_VALUATION.S_CMS_COLLATERAL_ID = CMS_STAGE_COMMODITY.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.A_CMS_COLLATERAL_ID = CMS_COMMODITY.CMS_COLLATERAL_ID"
			+ " AND VW_COLLATERAL_VALUATION.SECURITY_TYPE = 'CF'";

	protected String getSelectStatement() {
		return SELECT_CMDT_TRX;
	}

	protected boolean isValuationReqired(ICollateral collateral) {
		return true;
	}

	protected void synchronizeCollateralCMVFSV(ICollateralTrxValue collTrx) throws Exception {
		ICommodityDeal[] deals = getCommodityDeals(collTrx.getCollateral());

		CollateralValuator valuator = new CollateralValuator();
		valuator.setCollateralCMVFSV(collTrx.getCollateral(), deals);
		valuator.setCollateralCMVFSV(collTrx.getStagingCollateral(), deals);

	}

	private ICommodityDeal[] getCommodityDeals(ICollateral col) throws Exception {
		DBUtil dbUtil = null;
		try {
			dbUtil = util.getDBUtil();
			dbUtil.setSQL(CommodityDealDAO.SELECT_DEAL_BY_COLID);
			dbUtil.setLong(1, col.getCollateralID());
			ResultSet rs = dbUtil.executeQuery();
			ArrayList list = new ArrayList();
			while (rs.next()) {
				ICommodityDeal deal = new OBCommodityDeal();
				deal.setCollateralID(col.getCollateralID());
				deal.setDealNo(rs.getString(DEAL_NO));
				deal.setCommodityDealID(rs.getLong(DEAL_ID));
				deal.setCMV(util.getAmount(rs.getBigDecimal(DEAL_CMV), rs.getString(DEAL_CMV_CCY)));
				deal.setFSV(util.getAmount(rs.getBigDecimal(DEAL_FSV), rs.getString(DEAL_FSV_CCY)));
				list.add(deal);
			}
			rs.close();
			return (OBCommodityDeal[]) list.toArray(new OBCommodityDeal[0]);
		}
		finally {
			util.finalize(dbUtil);
		}
	}
}
