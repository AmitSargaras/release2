package com.integrosys.cms.batch.valuation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.cms.app.collateral.bus.valuation.model.GenericValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationFrequency;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationUtil;

/**
 * Valuation Jdbc to do do the various Jdbc task on valuation related, such as
 * to retrieve collaterals to be valuated, clear/update valuation flag
 * (valuated_date).
 * @author Zhan Jia
 * @author Chong Jun Yong
 * 
 */
public class ValuationMainDAO extends JdbcDaoSupport implements IValuationMainDAO {

	private static String query = " SELECT S.CMS_COLLATERAL_ID, "
			+ " S.SCI_SECURITY_DTL_ID, "
			+ " S.SCI_SECURITY_TYPE_VALUE, "
			+ " S.SCI_SECURITY_SUBTYPE_VALUE, "
			+ " S.SECURITY_LOCATION, "
			+ " S.SECURITY_ORGANISATION, "
			+ " S.SCI_SECURITY_CURRENCY, "
			+ " S.LAST_REMARGIN_DATE, "
			+ " S.NEXT_REMARGIN_DATE, "
			+ " P.THRESHOLD_PERCENT, "
			+ " P.VALUATION_FREQUENCY, "
			+ " P.VALUATION_FREQUENCY_UNIT, "
//			For Db2
//			+ " ROWNUMBER() OVER (ORDER BY CMS_COLLATERAL_ID ) AS ROW_NEXT  "
//			For Oracle
			+ " ROW_NUMBER() OVER (ORDER BY CMS_COLLATERAL_ID ) AS ROW_NEXT  "
			+ " FROM CMS_SECURITY S, CMS_SECURITY_PARAMETER P "
			+ " WHERE S.SECURITY_LOCATION = P.COUNTRY_ISO_CODE  "
			+ " AND S.SECURITY_SUB_TYPE_ID = P.SECURITY_SUB_TYPE_ID "
			+ " AND P.STATUS = 'ACTIVE' "
			+ " AND P.THRESHOLD_PERCENT  IS NOT NULL "
			+ " AND P.VALUATION_FREQUENCY IS NOT NULL  "
			+ " AND P.VALUATION_FREQUENCY > 0  "
			+ " AND P.VALUATION_FREQUENCY_UNIT IS NOT NULL "
			+ " AND P.VALUATION_FREQUENCY_UNIT <> '' "
			+ " AND (S.SECURITY_SUB_TYPE_ID NOT IN ('AB100','AB103','AB106','AB107','AB108','CL001', 'GT409') "
			+ "  AND S.SECURITY_SUB_TYPE_ID NOT LIKE 'DC%' )"
			// + " AND NEXT_REMARGIN_DATE IS NOT NULL " +
			// " AND DAYS(S.NEXT_REMARGIN_DATE) <= DAYS(CURRENT DATE) "
			+ " AND ((NEXT_REMARGIN_DATE IS NULL) OR (NEXT_REMARGIN_DATE IS NOT NULL AND DAYS(S.NEXT_REMARGIN_DATE) <= DAYS(CURRENT DATE))) "
			+ " AND ((VALUATED_DATE IS NULL) OR (VALUATED_DATE IS NOT NULL AND DAYS(VALUATED_DATE) < DAYS(CURRENT DATE))) "
			+ " AND S.SECURITY_LOCATION = ? AND S.SCI_SECURITY_TYPE_VALUE = ? ";

	private static String vehicle_feed_val_query = " SELECT S.CMS_COLLATERAL_ID, "
			+ " S.SCI_SECURITY_DTL_ID, "
			+ " S.SCI_SECURITY_TYPE_VALUE, "
			+ " S.SCI_SECURITY_SUBTYPE_VALUE, "
			+ " S.SECURITY_LOCATION, "
			+ " S.SECURITY_ORGANISATION, "
			+ " S.SCI_SECURITY_CURRENCY, "
			+ " S.LAST_REMARGIN_DATE, "
			+ " S.NEXT_REMARGIN_DATE, "
			+ " P.THRESHOLD_PERCENT, "
			+ " P.VALUATION_FREQUENCY, "
			+ " P.VALUATION_FREQUENCY_UNIT, "
//			For Db2
//			+ " ROWNUMBER() OVER (ORDER BY CMS_COLLATERAL_ID ) AS ROW_NEXT  "
//			For Oracle
			+ " ROW_NUMBER() OVER (ORDER BY CMS_COLLATERAL_ID ) AS ROW_NEXT  "
			+ " FROM CMS_SECURITY S, CMS_SECURITY_PARAMETER P, CMS_VEHICLE_PRICE_FEED F, "
			+ "COMMON_CODE_CATEGORY_ENTRY BRANCH, COMMON_CODE_CATEGORY_ENTRY CENTRE "
			+ " WHERE S.SECURITY_LOCATION = P.COUNTRY_ISO_CODE  "
			+ " AND S.SECURITY_SUB_TYPE_ID = P.SECURITY_SUB_TYPE_ID "
			+ " AND P.STATUS = 'ACTIVE' "
			+ " AND P.THRESHOLD_PERCENT IS NOT NULL "
			+ " AND P.VALUATION_FREQUENCY IS NOT NULL  "
			+ " AND P.VALUATION_FREQUENCY > 0  "
			+ " AND P.VALUATION_FREQUENCY_UNIT IS NOT NULL "
			+ " AND P.VALUATION_FREQUENCY_UNIT <> '' "
			+ " AND S.SECURITY_SUB_TYPE_ID = 'AB102' "
			+
			// " AND NEXT_REMARGIN_DATE IS NOT NULL "+
			// " AND DAYS(S.NEXT_REMARGIN_DATE) <= DAYS(CURRENT DATE) "+
			// " AND S.SECURITY_LOCATION = ? "+
			// " AND S.SCI_SECURITY_TYPE_VALUE = ? "+
			" AND S.SECURITY_ORGANISATION = BRANCH.ENTRY_CODE AND BRANCH.CATEGORY_CODE = '40' AND BRANCH.ACTIVE_STATUS = '1' "
			+ " AND BRANCH.REF_ENTRY_CODE = CENTRE.ENTRY_CODE AND CENTRE.CATEGORY_CODE = 'CENTRE' AND CENTRE.ACTIVE_STATUS = '1' "
			+ " AND CENTRE.REF_ENTRY_CODE = F.REGION "
			+ " AND F.REGION = ? "
			+ " AND DATE(F.LAST_UPDATE_DATE) = CURRENT DATE "
			+ " AND S.SECURITY_LOCATION = ? "
			+ " AND ((VALUATED_DATE IS NULL) OR (VALUATED_DATE IS NOT NULL AND DAYS(VALUATED_DATE) < DAYS(CURRENT DATE))) ";

	private static String filterBySource = " AND S.SOURCE_ID = ? ";

	private static String filterByCollateralID = " AND S.CMS_COLLATERAL_ID = ? ";

	private static String filterBySecurityID = " AND S.SCI_SECURITY_DTL_ID = ? ";

	private static String cgcQuery = " SELECT S.CMS_COLLATERAL_ID, "
			+ " S.SCI_SECURITY_DTL_ID, "
			+ " S.SCI_SECURITY_TYPE_VALUE, "
			+ " S.SCI_SECURITY_SUBTYPE_VALUE, "
			+ " S.SECURITY_LOCATION, "
			+ " S.SECURITY_ORGANISATION, "
			+ " S.SCI_SECURITY_CURRENCY, "
			+ " S.LAST_REMARGIN_DATE, "
			+ " S.NEXT_REMARGIN_DATE, "
			+ " P.THRESHOLD_PERCENT, "
			+ " P.VALUATION_FREQUENCY, "
			+ " P.VALUATION_FREQUENCY_UNIT, "
//			For Db2
//			+ " ROWNUMBER() OVER (ORDER BY CMS_COLLATERAL_ID ) AS ROW_NEXT  "
//			For Oracle
			+ " ROW_NUMBER() OVER (ORDER BY CMS_COLLATERAL_ID ) AS ROW_NEXT  "
			+ " FROM CMS_SECURITY S, CMS_SECURITY_PARAMETER P "
			+ " WHERE S.SECURITY_LOCATION = P.COUNTRY_ISO_CODE  "
			+ " AND S.SECURITY_SUB_TYPE_ID = P.SECURITY_SUB_TYPE_ID "
			+ " AND P.STATUS = 'ACTIVE' "
			+ " AND P.THRESHOLD_PERCENT  IS NOT NULL "
			+ " AND P.VALUATION_FREQUENCY IS NOT NULL  "
			+ " AND P.VALUATION_FREQUENCY > 0  "
			+ " AND P.VALUATION_FREQUENCY_UNIT IS NOT NULL "
			+ " AND P.VALUATION_FREQUENCY_UNIT <> '' "
			+ " AND S.SECURITY_SUB_TYPE_ID = 'GT409' "
			// + " AND NEXT_REMARGIN_DATE IS NOT NULL " +
			// " AND DAYS(S.NEXT_REMARGIN_DATE) <= DAYS(CURRENT DATE) "
			+ " AND ((NEXT_REMARGIN_DATE IS NULL) OR (NEXT_REMARGIN_DATE IS NOT NULL AND DAYS(S.NEXT_REMARGIN_DATE) <= DAYS(CURRENT DATE))) "
			+ " AND ((VALUATED_DATE IS NULL) OR (VALUATED_DATE IS NOT NULL AND DAYS(VALUATED_DATE) < DAYS(CURRENT DATE))) "
			+ " AND S.SECURITY_LOCATION = ? ";

	// private static String orderBy = " ORDER BY S.CMS_COLLATERAL_ID";

	private String formulateQuery(Map parameters, List argList) {

		String specialHandleSec = (String) parameters.get(ValuationMain.SPECIAL_HANDLING);
		String source = (String) parameters.get(ValuationMain.SOURCE);
		String id = (String) parameters.get(ValuationMain.ID);
		String secId = (String) parameters.get(ValuationMain.SECID);

		StringBuffer sqlBuf = new StringBuffer();
		if (specialHandleSec != null) {
			if (specialHandleSec.equals("AB102")) {
				sqlBuf.append(vehicle_feed_val_query);
				argList.add(parameters.get(ValuationMain.REGION));
				argList.add(parameters.get(ValuationMain.COUNTRY));
			}
			else if (specialHandleSec.equals("GT409")) {
				sqlBuf.append(cgcQuery);
				argList.add(parameters.get(ValuationMain.COUNTRY));
			}
		}
		else {
			sqlBuf.append(query);
			argList.add(parameters.get(ValuationMain.COUNTRY));
			argList.add(parameters.get(ValuationMain.SEC_TYPE));
		}

		if (source != null) {
			sqlBuf.append(filterBySource);
			argList.add(parameters.get(ValuationMain.SOURCE));
		}

		if (id != null) {
			sqlBuf.append(filterByCollateralID);
			argList.add(parameters.get(ValuationMain.ID));
		}

		if (secId != null) {
			sqlBuf.append(filterBySecurityID);
			argList.add(parameters.get(ValuationMain.SECID));
		}

		return sqlBuf.toString();
	}

	private String formulateCountQuery(Map parameters, List argList) {

		String sql = formulateQuery(parameters, argList);
		return "select count(1) from (" + sql + ") as pagingTemp1";
	}

	private String formulatePaginationQuery(Map parameters, List argList, long startIndex, int batchSize) {

		String sql = formulateQuery(parameters, argList);
		argList.add(new Long(startIndex + 1));
		argList.add(new Long(startIndex + batchSize));
		return " SELECT PAGINGTEMP2.* FROM ( " + sql + " ) AS PAGINGTEMP2 WHERE PAGINGTEMP2.ROW_NEXT BETWEEN ? AND ? ";

	}

	public long getNoOfSecurities(Map paramMap) {
		List argList = new ArrayList();

		String query = formulateCountQuery(paramMap, argList);

		Long number = (Long) getJdbcTemplate().query(query, argList.toArray(), new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return new Long(rs.getLong(1));
				}
				return null;
			}
		});

		return number.longValue();
	}

	public List getNextBatchSecurities(Map paramMap, long startIndex, int batchSize) {
		List argList = new ArrayList();

		String query = formulatePaginationQuery(paramMap, argList, startIndex, batchSize);

		return (List) getJdbcTemplate().query(query, argList.toArray(), new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List resultList = new ArrayList();
				while (rs.next()) {
					long collateralId = rs.getLong("CMS_COLLATERAL_ID");
					String secId = rs.getString("SCI_SECURITY_DTL_ID");
					String secType = rs.getString("SCI_SECURITY_TYPE_VALUE");
					String secSubtype = rs.getString("SCI_SECURITY_SUBTYPE_VALUE");
					String secLocation = rs.getString("SECURITY_LOCATION");
					String secOrganisation = rs.getString("SECURITY_ORGANISATION");
					String secCurrency = rs.getString("SCI_SECURITY_CURRENCY");
					double threshold = rs.getDouble("THRESHOLD_PERCENT");
					String valFreq = rs.getString("VALUATION_FREQUENCY");
					String valFreqUnit = rs.getString("VALUATION_FREQUENCY_UNIT");
					Date lastValDate = rs.getDate("LAST_REMARGIN_DATE");
					Date nextValDate = rs.getDate("NEXT_REMARGIN_DATE");

					// if (thresholdPercentStr != null && valFreq != null &&
					// valFreqUnit != null) {
					if (valFreq != null && valFreqUnit != null) {
						GenericValuationModel valuationModel = new GenericValuationModel();
						valuationModel.setCollateralId(collateralId);
						valuationModel.setSecId(secId);
						valuationModel.setSecType(secType);
						valuationModel.setSecSubtype(secSubtype);
						valuationModel.setSecurityCountry(secLocation);
						valuationModel.setSecurityOrganisation(secOrganisation);
						valuationModel.setSecCurrency(secCurrency);
						ValuationUtil.determineMargin(valuationModel, threshold);
						ValuationFrequency freq = new ValuationFrequency();
						freq.setFreq(Integer.parseInt(valFreq));
						freq.setFreqUnit(valFreqUnit);
						valuationModel.setValuationFrequency(freq);
						valuationModel.setPrevValuationDate(lastValDate);
						valuationModel.setValuationDate((nextValDate == null) ? new Date() : nextValDate);
						resultList.add(valuationModel);
					}
				}
				return resultList;
			}
		});
	}

	/**
	 * For this implementation, update the valuated_date to DB's current
	 * timestamp for the collateral after valuation run (whether success or
	 * fail)
	 * @param collateralId cms collateral id
	 */
	public void updateValuatedFlagForCollateral(long collateralId) {
		getJdbcTemplate().update(
				"UPDATE cms_security SET valuated_date = current_timestamp WHERE cms_collateral_id = ?",
				new Object[] { new Long(collateralId) });
	}

}
