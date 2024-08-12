package com.integrosys.cms.app.collateral.bus.valuation.dao;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.StrtLineValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StrtLineValuationDAO extends GenericValuationDAO implements IStrtLineValuationDAO {

	public Map retrieveAssetLife() throws ValuationException {
		//String query = "SELECT LIFESPAN FROM CMS_SECURITY_ASSET_LIFE WHERE SECURITY_SUB_TYPE_ID = ? AND STATUS = 'ACTIVE'";
        String query = "SELECT SECURITY_SUB_TYPE_ID, LIFESPAN FROM CMS_SECURITY_ASSET_LIFE WHERE STATUS = 'ACTIVE'";

        return (Map) getJdbcTemplate().query(query, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                Map resultMap = new HashMap();

				while(rs.next()) {
                    String securitySubType = rs.getString("SECURITY_SUB_TYPE_ID");
					String assetLife = rs.getString("LIFESPAN");
                    resultMap.put(securitySubType, (assetLife==null)? new Integer(0) : new Integer(assetLife));
                    //DefaultLogger.debug(this, "SECURITY_SUB_TYPE_ID [" + securitySubType + "] - LIFESPAN[" + assetLife +"]");
				}

				return resultMap;
			}
		});
	}

	private void retrieveAssetValInfo(IValuationModel valModel) throws ValuationException {
		final StrtLineValuationModel stlValModel = (StrtLineValuationModel) valModel;
		String query = "SELECT " + "COL.SCI_SECURITY_CURRENCY, A.PURCHASE_DATE, A.PURCHASE_PRICE, "
				+ "A.SCRAP_VALUE, A.GOOD_STATUS, A.MANUFACTURE_YEAR, "
				+ "A.INIT_RESIDUAL_ASSET_LIFE, A.INIT_RESIDUAL_ASSET_LIFE_UOM, A.INIT_RESIDUAL_ASSET_LIFE_DATE, "
				+ "A.DEPRECIABLE_ASSET_VALUE, A.DEPRECIABLE_ASSET_VALUE_DATE, A.DEPRECIATE_RATE "
				+ "FROM CMS_ASSET A, CMS_SECURITY COL " + "WHERE A.CMS_COLLATERAL_ID = ? "
				+ "AND A.CMS_COLLATERAL_ID = COL.CMS_COLLATERAL_ID";

		getJdbcTemplate().query(query, new Object[] { new Long(valModel.getCollateralId()) }, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					String goodStatus = rs.getString("GOOD_STATUS");
					String manufactureYear = rs.getString("MANUFACTURE_YEAR");
					String currency = rs.getString("SCI_SECURITY_CURRENCY");
					String purchasePrice = rs.getString("PURCHASE_PRICE");
					String scrapValueStr = rs.getString("SCRAP_VALUE");
                    double scrapValue = rs.getDouble("SCRAP_VALUE");

					Date purchaseDate = rs.getDate("PURCHASE_DATE");
					String initResidualAstLife = rs.getString("INIT_RESIDUAL_ASSET_LIFE");
					String initResidualAstLifeUOM = rs.getString("INIT_RESIDUAL_ASSET_LIFE_UOM");
					Date initResidualAstLifeDate = rs.getDate("INIT_RESIDUAL_ASSET_LIFE_DATE");
					String depreciableAssetValue = rs.getString("DEPRECIABLE_ASSET_VALUE");
					Date depreciableAssetValueDate = rs.getDate("DEPRECIABLE_ASSET_VALUE_DATE");
					String depreciateRate = rs.getString("DEPRECIATE_RATE");

					stlValModel.setCurrencyCode(currency);

					if (purchasePrice != null) {
						stlValModel.setPurchasePrice(new Double(purchasePrice));
					}
					if (scrapValueStr != null) {
                        //logger.debug(">>>>>>>>>>>>> scrapValue = " + scrapValue);
                        scrapValue = (scrapValue <=0) ? 0 : scrapValue;
                        stlValModel.setScrapValue(new Double(scrapValue));
					} else {
                        stlValModel.setScrapValue(new Double(0));
                    }

					if ((manufactureYear != null) && !manufactureYear.equals("0")) {
						stlValModel.setManufactureYear(new Integer(manufactureYear));
					}
					stlValModel.setPurchaseDate(purchaseDate);

					if ((goodStatus != null) && goodStatus.equals(ICMSConstant.GOOD_STATUS_NEW)) {
						stlValModel.setIsNewGoodStatus(true);
					}
					if (initResidualAstLifeDate == null) {
						stlValModel.setIsFirstTime(true);
					}
					else {
                        if(initResidualAstLife != null) {
                            double initResAstLifeDbl = ValuationUtil.convertTimeToYear(initResidualAstLife,
                                    initResidualAstLifeUOM);
                            stlValModel.setInitResidualAssetLife(new Double(initResAstLifeDbl));
                        }
                        stlValModel.setInitResidualAssetLifeDate(initResidualAstLifeDate);
					}
					if (depreciableAssetValueDate != null) {
                        if(depreciableAssetValue != null) {
                            stlValModel.setDepreciableAssetValue(new Double(depreciableAssetValue));
                        }                        
                        stlValModel.setDepreciableAssetValueDate(depreciableAssetValueDate);
					}
					if (depreciateRate != null) {
						stlValModel.setDepreciateRate(new Double(depreciateRate));
					}
				}

				return null;
			}
		});
	}

	private void retrieveInitOMV(IValuationModel valModel) throws ValuationException {
		final StrtLineValuationModel stlValModel = (StrtLineValuationModel) valModel;

//		String query = "SELECT * FROM (SELECT SOURCE_TYPE, VALUATION_DATE, VALUATION_CURRENCY, CMV FROM CMS_VALUATION "
//				+ "WHERE CMS_COLLATERAL_ID = ? AND SOURCE_TYPE = '"
//				+ ICMSConstant.VALUATION_SOURCE_TYPE_M
//				+ "' "
//				+ "ORDER BY VALUATION_DATE FETCH FIRST 1 ROWS ONLY) AS TEMP1 "
//				+ "UNION ALL "
//				+ "SELECT * FROM (SELECT SOURCE_TYPE, VALUATION_DATE, VALUATION_CURRENCY, CMV FROM CMS_VALUATION "
//				+ "WHERE CMS_COLLATERAL_ID = ? AND VALUATION_DATE IS NOT NULL "
//				+ "AND SOURCE_TYPE = '"
//				+ ICMSConstant.VALUATION_SOURCE_TYPE_S
//				+ "' "
//				+ "AND VALUATION_TYPE = '"
//				+ ICMSConstant.VALUATION_TYPE_FORMAL
//				+ "' "
//				+ "ORDER BY VALUATION_DATE FETCH FIRST 1 ROWS ONLY) AS TEMP2 "
//				+ "UNION ALL "
//				+ "SELECT * FROM (SELECT SOURCE_TYPE, VALUATION_DATE, VALUATION_CURRENCY, CMV FROM CMS_VALUATION "
//				+ "WHERE CMS_COLLATERAL_ID = ? AND VALUATION_DATE IS NOT NULL "
//				+ "AND SOURCE_TYPE = '"
//				+ ICMSConstant.VALUATION_SOURCE_TYPE_S
//				+ "' "
//				+ "AND VALUATION_TYPE = '"
//				+ ICMSConstant.VALUATION_TYPE_BRANCH
//				+ "' "
//				+ "ORDER BY VALUATION_DATE FETCH FIRST 1 ROWS ONLY) AS TEMP3 "
//				+ "UNION ALL "
//				+ "SELECT * FROM (SELECT SOURCE_TYPE, VALUATION_DATE, VALUATION_CURRENCY, CMV FROM CMS_VALUATION "
//				+ "WHERE CMS_COLLATERAL_ID = ? AND VALUATION_DATE IS NOT NULL "
//				+ "AND SOURCE_TYPE = '"
//				+ ICMSConstant.VALUATION_SOURCE_TYPE_S
//				+ "' "
//				+ "AND VALUATION_TYPE = '"
//				+ ICMSConstant.VALUATION_TYPE_APPRAISED
//				+ "' "
//				+ "ORDER BY VALUATION_DATE FETCH FIRST 1 ROWS ONLY) AS TEMP4 "
//				+ "UNION ALL "
//				+ "SELECT * FROM (SELECT SOURCE_TYPE, VALUATION_DATE, VALUATION_CURRENCY, CMV FROM CMS_VALUATION "
//				+ "WHERE CMS_COLLATERAL_ID = ? AND VALUATION_DATE IS NOT NULL "
//				+ "AND SOURCE_TYPE = '"
//				+ ICMSConstant.VALUATION_SOURCE_TYPE_S
//				+ "' "
//				+ "AND VALUATION_TYPE = '"
//				+ ICMSConstant.VALUATION_TYPE_VERBAL
//				+ "' "
//				+ "ORDER BY VALUATION_DATE FETCH FIRST 1 ROWS ONLY) AS TEMP5 ";

				//For Db2
/*        String query = "select * from ( " +
                "    SELECT row_number()over(partition by SOURCE_TYPE, VALUATION_TYPE order by VALUATION_DATE desc) rownum, " +
                "    SOURCE_TYPE, VALUATION_TYPE, VALUATION_DATE, VALUATION_CURRENCY, CMV, FSV, VALUER, " +
                "    case SOURCE_TYPE " +
                "        WHEN '" + ICMSConstant.VALUATION_SOURCE_TYPE_M + "' THEN 1 " +
                "        WHEN '" + ICMSConstant.VALUATION_SOURCE_TYPE_S + "' THEN 2 " +
                "    END AS SOURCE_TYPE_ORDER, " +
                "    case VALUATION_TYPE " +
                "        WHEN '" + ICMSConstant.VALUATION_TYPE_FORMAL    + "' THEN 1 " +
                "        WHEN '" + ICMSConstant.VALUATION_TYPE_BRANCH    + "' THEN 2 " +
                "        WHEN '" + ICMSConstant.VALUATION_TYPE_APPRAISED + "' THEN 3 " +
                "        WHEN '" + ICMSConstant.VALUATION_TYPE_VERBAL    + "' THEN 4 " +
                "        ELSE 1 " +
                "    END AS VAL_TYPE_ORDER    " +
                "    FROM CMS_VALUATION " +
                "    WHERE CMS_COLLATERAL_ID = ? " +
                ") valgrp " +
                "where valgrp.rownum = 1 " +
                "ORDER BY SOURCE_TYPE_ORDER, VAL_TYPE_ORDER " +
                "FETCH FIRST 1 row only";
*/
		//For Oracle
		        String query = "SELECT * FROM ( select * from ( " +
                "    SELECT row_number()over(partition by SOURCE_TYPE, VALUATION_TYPE order by VALUATION_DATE desc) rownum, " +
                "    SOURCE_TYPE, VALUATION_TYPE, VALUATION_DATE, VALUATION_CURRENCY, CMV, FSV, VALUER, " +
                "    case SOURCE_TYPE " +
                "        WHEN '" + ICMSConstant.VALUATION_SOURCE_TYPE_M + "' THEN 1 " +
                "        WHEN '" + ICMSConstant.VALUATION_SOURCE_TYPE_S + "' THEN 2 " +
                "    END AS SOURCE_TYPE_ORDER, " +
                "    case VALUATION_TYPE " +
                "        WHEN '" + ICMSConstant.VALUATION_TYPE_FORMAL    + "' THEN 1 " +
                "        WHEN '" + ICMSConstant.VALUATION_TYPE_BRANCH    + "' THEN 2 " +
                "        WHEN '" + ICMSConstant.VALUATION_TYPE_APPRAISED + "' THEN 3 " +
                "        WHEN '" + ICMSConstant.VALUATION_TYPE_VERBAL    + "' THEN 4 " +
                "        ELSE 1 " +
                "    END AS VAL_TYPE_ORDER    " +
                "    FROM CMS_VALUATION " +
                "    WHERE CMS_COLLATERAL_ID = ? " +
                ") valgrp " +
                "where valgrp.rownum = 1 " +
                "ORDER BY SOURCE_TYPE_ORDER, VAL_TYPE_ORDER " +
                " )TEMP WHERE ROWNUM<=1";
		 
        Long collateralId = new Long(stlValModel.getCollateralId());
		getJdbcTemplate().query(query, new Object[] { collateralId }, new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							String valCurrency = rs.getString("VALUATION_CURRENCY");
                            String cmvStr = rs.getString("CMV");
                            if(valCurrency != null && cmvStr != null) {
                                double cmv = rs.getDouble("CMV");
                                stlValModel.setInitValOMV(new Amount(cmv, valCurrency));
                            }
                            stlValModel.setInitValOMVDate(rs.getDate("VALUATION_DATE"));
                        }

						return null;
					}

				});
	}

	public void retrieveValuationInfoForCal(IValuationModel valModel) throws ValuationException {
		//retrieveAssetLife(valModel); //shifted to AssetLifeProfileSingleton class
		// retrieveStartValDate(valModel);
		retrieveAssetValInfo(valModel);

		if (!((StrtLineValuationModel) valModel).getIsNewGoodStatus()) {
			retrieveInitOMV(valModel);
		}
	}

	public void persistOtherInfo(IValuationModel valModel) throws ValuationException {
		StrtLineValuationModel strtLineValModel = (StrtLineValuationModel) valModel;

		String query = "UPDATE CMS_ASSET SET RESIDUAL_ASSET_LIFE = ?, " + "RESIDUAL_ASSET_LIFE_UOM = ? ";
		List argList = new ArrayList();
		argList.add(new Double(strtLineValModel.getResidualAssetLife()));
		argList.add(ICMSConstant.FREQ_UNIT_YEARS);

		if (strtLineValModel.getIsFirstTime()) {
			query = query + ", DEPRECIATE_RATE = ? " + ", INIT_RESIDUAL_ASSET_LIFE = ? "
					+ ", INIT_RESIDUAL_ASSET_LIFE_UOM = ? " + ", INIT_RESIDUAL_ASSET_LIFE_DATE = ? "
					+ ", DEPRECIABLE_ASSET_VALUE = ? " + ", DEPRECIABLE_ASSET_VALUE_DATE = ? ";
			argList.add(strtLineValModel.getDepreciateRate());
			argList.add(strtLineValModel.getInitResidualAssetLife());
            argList.add(ICMSConstant.FREQ_UNIT_YEARS);
            argList.add(strtLineValModel.getInitResidualAssetLifeDate());
			argList.add(strtLineValModel.getDepreciableAssetValue());
			argList.add((strtLineValModel.getDepreciableAssetValueDate() == null) ?
                    new Date() : strtLineValModel.getDepreciableAssetValueDate() );
		}

		query = query + " WHERE CMS_COLLATERAL_ID=?";
		argList.add(new Long(valModel.getCollateralId()));

		getJdbcTemplate().update(query, argList.toArray());
	}

}
