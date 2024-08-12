package com.integrosys.cms.app.collateral.bus.valuation.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.GoldValuationModel;
import com.integrosys.cms.app.feed.bus.gold.OBGoldFeedEntry;

/**
 * @author Cynthia Zhou
 */
public class GoldValuationDAO extends GenericValuationDAO implements IGoldValuationDAO {

	public void retrieveValuationInfoForCal(IValuationModel valModel) throws ValuationException {
		final GoldValuationModel gValModel = (GoldValuationModel) valModel;
		String sql = "SELECT GOLD_GRADE, GOLD_WEIGHT, GOLD_UOM FROM CMS_ASSET_GOLD WHERE CMS_COLLATERAL_ID = ?";

		getJdbcTemplate().query(sql, new Object[] { new Long(valModel.getCollateralId()) }, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					gValModel.setGoldGrade(rs.getString("GOLD_GRADE"));
					gValModel.setGoldWeight(rs.getDouble("GOLD_WEIGHT"));
					gValModel.setGoldUOM(rs.getString("GOLD_UOM"));
				}

				return null;
			}
		});

	}

	public Map retrieveFeedInfo() throws ValuationException {
		String sql = "select GOLD_GRADE_CODE_NUM, GOLD_UOM_CODE_NUM, CURRENCY, PRICE from CMS_GOLD_FEED";

		return (Map) getJdbcTemplate().query(sql, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map resultMap = new HashMap();
				while (rs.next()) {
					OBGoldFeedEntry entry = new OBGoldFeedEntry();
					String goldGrade = rs.getString("GOLD_GRADE_CODE_NUM");

					entry.setGoldGradeNum(goldGrade);
					entry.setUnitMeasurementNum(rs.getString("GOLD_UOM_CODE_NUM"));
					entry.setUnitPrice(new BigDecimal(rs.getDouble("PRICE")));
					entry.setCurrencyCode(rs.getString("CURRENCY"));

					resultMap.put(goldGrade, entry);
				}

				return resultMap;
			}

		});
	}


    public void persistOtherInfo(IValuationModel valModel) throws ValuationException {

        String query = "update CMS_ASSET_GOLD SET GOLD_UNIT_PRICE=?, GOLD_UNIT_PRICE_CURRENCY=? WHERE CMS_COLLATERAL_ID=? ";
        GoldValuationModel gValModel = (GoldValuationModel) valModel;

        if(gValModel.getGoldUnitPrice() != null) {
            ArrayList argList = new ArrayList();
            argList.add(new Double(gValModel.getGoldUnitPrice().getAmount()));
            argList.add(gValModel.getGoldUnitPrice().getCurrencyCode());
            argList.add(new Long(gValModel.getCollateralId()));
            getJdbcTemplate().update(query, argList.toArray());
        } else {
            logger.info("Unit Price not updated for gold with collateral id = " + gValModel.getCollateralId());
        }
    }
}
