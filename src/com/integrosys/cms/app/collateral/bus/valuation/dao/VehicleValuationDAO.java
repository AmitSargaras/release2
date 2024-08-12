package com.integrosys.cms.app.collateral.bus.valuation.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.VehicleValuationModel;

/**
 * @author Cynthia Zhou
 * 
 */
public class VehicleValuationDAO extends StrtLineValuationDAO implements IVehicleValuationDAO {

    public Map retrieveRegionInfo() throws ValuationException {
        String sql = "select branch.entry_code BRANCH, centre.ENTRY_CODE CENTRE, centre.REF_ENTRY_CODE REGION " +
                "from common_code_category_entry branch, common_code_category_entry centre " +
                "where branch.REF_ENTRY_CODE = centre.ENTRY_CODE " +
                "and branch.category_code = '40' " +
                "and branch.active_status = '1' " +
                "and centre.category_code = 'CENTRE' " +
                "and centre.active_status = '1' ";

        final Map map = new HashMap();

        getJdbcTemplate().query(sql, new ResultSetExtractor() {

            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    String branch = rs.getString("BRANCH");
                    String region = rs.getString("REGION");
                    map.put(branch, region);
                }
                return null;
            }
        });

        return map;
    }


    public void retrieveValuationInfoForCal(IValuationModel valModel) throws ValuationException {
		super.retrieveValuationInfoForCal(valModel);
		final VehicleValuationModel secModel = (VehicleValuationModel) valModel;
//        String sql = "select ast.CMS_COLLATERAL_ID, ast.BRAND, ast.MODEL_NO, centre.REF_ENTRY_CODE as REGION " +
//                "from cms_asset ast, cms_security sec, common_code_category_entry branch, " +
//                "common_code_category_entry centre " +
//                "where ast.cms_collateral_id = sec.cms_collateral_id " +
//                "and sec.SECURITY_ORGANISATION = branch.ENTRY_CODE " +
//                "and branch.category_code = '40' " +
//                "and branch.REF_ENTRY_CODE = centre.ENTRY_CODE " +
//                "and branch.active_status = '1' " +
//                "and centre.category_code = 'CENTRE' " +
//                "and centre.active_status = '1' " +
//                "and ast.cms_collateral_id = ? " +
//                "fetch first 1 row only ";

//        String sql = "select ast.CMS_COLLATERAL_ID, ast.BRAND, ast.MODEL_NO, " +
//                "(select centre.REF_ENTRY_CODE " +
//                "from common_code_category_entry branch, common_code_category_entry centre " +
//                "where sec.SECURITY_ORGANISATION = branch.ENTRY_CODE " +
//                "and branch.category_code = '40' " +
//                "and branch.REF_ENTRY_CODE = centre.ENTRY_CODE " +
//                "and branch.active_status = '1' " +
//                "and centre.category_code = 'CENTRE' " +
//                "and centre.active_status = '1' )  as REGION " +
//                "from cms_asset ast, cms_security sec " +
//                "where ast.cms_collateral_id = sec.cms_collateral_id " +
//                "and ast.cms_collateral_id = ? " +
//                "fetch first 1 row only";

        String sql = "select ast.CMS_COLLATERAL_ID, ast.BRAND, ast.MODEL_NO " +
                "from cms_asset ast, cms_security sec " +
                "where ast.cms_collateral_id = sec.cms_collateral_id " +
                "and ast.cms_collateral_id = ? " +
                // For DB2
//                "fetch first 1 row only";
                // For Oracle
                "and rownum<=1";

        getJdbcTemplate().query(sql, new Object[] { new Long(valModel.getCollateralId()) }, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					secModel.setMake(rs.getString("BRAND"));
					secModel.setModel(rs.getString("MODEL_NO"));
                    //secModel.setRegion(rs.getString("REGION"));       //moved to CommonCodeRegionSingleton
                }

				return secModel;
			}
		});
	}

	public Map retrieveFeedInfo() throws ValuationException {
		String sql = "select CAR_MAKE, CAR_MODEL, CAR_YEAR, CAR_FSV, REGION, LAST_UPDATE_DATE from CMS_VEHICLE_PRICE_FEED";

		final Map map = new HashMap();

		getJdbcTemplate().query(sql, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					String make = rs.getString("CAR_MAKE");
					String model = rs.getString("CAR_MODEL");
					int year = rs.getInt("CAR_YEAR");
					String region = rs.getString("REGION");
					String currency = "MYR";
					double fsvAmt = rs.getDouble("CAR_FSV");
					Amount fsv = new Amount(fsvAmt, currency);
					processFeedInfo(map, make, model, year, region, fsv);
				}
				return null;
			}
		});

		return map;
	}

	private void processFeedInfo(Map map, String make, String model, int year, String region, Amount fsv) {
		Integer yr = new Integer(year);

		if (map.isEmpty() || map.get(make) == null) {
			map.put(make, createModelMap(model, yr, region, fsv));
		}
		else {
			Map modelMap = (Map) map.get(make);
			if (modelMap.get(model) == null) {
				modelMap.put(model, createYearMap(yr, region, fsv));
			}
			else {
				Map yearMap = (Map) modelMap.get(model);
				if (yearMap.get(yr) == null) {
					yearMap.put(yr, createRegionMap(region, fsv));
				}
				else {
					Map regionMap = (Map) yearMap.get(yr);
					// no action required if already exists
					if (regionMap.get(region) == null) {
						regionMap.put(region, fsv);
					}
					yearMap.put(yr, regionMap);
				}
				modelMap.put(model, yearMap);
			}
			map.put(make, modelMap);
		}
	}

	protected Map createRegionMap(String region, Amount fsv) {
		Map regionMap = new HashMap();
		regionMap.put(region, fsv);
		return regionMap;
	}

	protected Map createYearMap(Integer year, String region, Amount fsv) {
		Map yearMap = new HashMap();

		Map regionMap = createRegionMap(region, fsv);
		yearMap.put(year, regionMap);
		return yearMap;
	}

	protected Map createModelMap(String model, Integer year, String region, Amount fsv) {
		Map modelMap = new HashMap();

		Map yearMap = createYearMap(year, region, fsv);
		modelMap.put(model, yearMap);
		return modelMap;
	}
}
