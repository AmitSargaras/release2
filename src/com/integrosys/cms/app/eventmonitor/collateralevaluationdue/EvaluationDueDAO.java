/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collateralevaluationdue/EvaluationDueDAO.java,v 1.32 2006/08/21 02:46:34 hmbao Exp $
 */

package com.integrosys.cms.app.eventmonitor.collateralevaluationdue;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAOResult;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.MonitorDaoResultRetrievalFailureException;
import com.integrosys.cms.app.eventmonitor.VelocityNotificationUtil;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;
import com.integrosys.cms.app.eventmonitor.common.OBFacilityInfo;

/**
 * Describe this class. *
 * 
 * @author BaoHongMan
 * @version R1.5
 * @Purpose:
 * @Description: if (security subtype next valuation date - system date = 90
 *               days) applies to only Asset based security except post-date
 *               cheques Number passed in should be -ve
 * @Tag 
 *      com.integrosys.cms.app.eventmonitor.collateralevaluationdue.EvaluationDueDAO
 *      .java
 * @since 2006-7-20
 */
public class EvaluationDueDAO extends AbstractMonitorDAO implements IMonitorDAO {

	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException {

		String sql = getSQLStatement(ruleParam.hasCountryCode());

		OBDateRuleParam param = (OBDateRuleParam) ruleParam;

		List argList = new ArrayList();
		argList.add(new Date(param.getSysDate().getTime()));
		argList.add(new Integer(param.getNumOfDays()));

		if (ruleParam.hasCountryCode()) {
			argList.add(ruleParam.getCountryCode());
		}

		List resultList = (List) getJdbcTemplate().query(sql, argList.toArray(), new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return processResultSet(rs);
			}
		});

		return new EvaluationDueDAOResult(resultList);

	}

	/**
	 * Process resultset to return a list of results.
	 * 
	 * @param ResultSet rs
	 * @return List - list of results
	 * @throws Exception
	 */
	private List processResultSet(ResultSet rs) throws SQLException {
		HashMap secMap = new HashMap();
		if (rs != null) {
			OBEvaluationDueInfo nn = null;
			while (rs.next()) {
				String secID = rs.getString("CMS_COLLATERAL_ID");
				nn = (OBEvaluationDueInfo) secMap.get(secID);
				if (nn == null) {
					nn = new OBEvaluationDueInfo();
					nn.setSciSecurityID(secID);
					String ccy = rs.getString("CCY");
					String amount = rs.getString("AMOUNT");
					if ((ccy != null) && !"".equals(ccy) && (amount != null)) {
						nn.setAmount(new Amount(Double.valueOf(amount).doubleValue(), ccy));
					}
					ccy = rs.getString("F_CCY");
					amount = rs.getString("F_AMOUNT");
					if ((ccy != null) && !"".equals(ccy) && (amount != null)) {
						nn.setFsvAmount(new Amount(Double.valueOf(amount).doubleValue(), ccy));
					}

					nn.setValFreq(rs.getInt("VALUATION_FREQUENCY"));
					nn.setValFreqUnit(rs.getString("VALUATION_FREQUENCY_UNIT"));
					nn.setValuationDate(rs.getDate("VALUATION_DATE"));

					nn.setDaysDue(rs.getInt("DAYSDUE"));
					nn.setExpiryDate(rs.getDate("EXPIRYDATE"));
					nn.setOriginatingCountry(rs.getString("CMS_ORIG_COUNTRY"));
					nn.setOriginatingOrganisation(rs.getString("CMS_ORIG_ORGANISATION"));
					nn.setSubType(rs.getString("SECURITY_SUB_TYPE_ID"));
					nn.setType(rs.getString("TYPE_NAME"));
					nn.setLeID(rs.getString("LSP_LE_ID"));
					nn.setLeName(rs.getString("LSP_SHORT_NAME"));
					nn.setSegment(rs.getString("SEGMENT"));
					nn.setMaturityDate(rs.getDate("SECURITY_MATURITY_DATE"));
					secMap.put(secID, nn);
				}

				HashMap sourceIDMap = nn.getSourceIDMap();
				String sourceID = rs.getString("SOURCE_ID");
				if ((sourceID != null) && (sourceIDMap.get(sourceID) == null)) {
					sourceIDMap.put(sourceID, sourceID);
				}

				String facID = rs.getString("LMT_ID");
				if (facID == null) {
					continue;
				}
				HashMap facMap = nn.getFacilityMap();
				OBFacilityInfo obFac = (OBFacilityInfo) facMap.get(facID);
				if (obFac == null) {
					obFac = new OBFacilityInfo();
					obFac.setFacilityID(facID);
					obFac.setFacilityDesc(rs.getString("ENTRY_NAME"));
					facMap.put(facID, obFac);
				}
				if (obFac == null) {
					continue;
				}
				String accID = rs.getString("LSX_EXT_SYS_ACCT_NUM");
				HashMap accIDMap = obFac.getAccIDMap();
				if (accIDMap.get(accID) == null) {
					accIDMap.put(accID, accID);
				}
			}
		}
		return VelocityNotificationUtil.convertMap2List(secMap);
	}

	private String getSQLStatement(boolean isByCountry) {
		StringBuffer sqlBuffer = new StringBuffer();
//		Note Replacing BIGINT with NUMBER used in cast function for Db2 to Oracle Migration. Anil Pandey
		String selectSQL = "SELECT CMS_SECURITY.CMS_COLLATERAL_ID, \n"
				+ "CMS_SECURITY.SCI_SECURITY_DTL_ID AS SECURITY_ID, \n"
				+ "CMS_SECURITY_SOURCE.SOURCE_SECURITY_ID AS SOURCE_ID, \n"
				+ "CMS_SECURITY.SECURITY_MATURITY_DATE AS SECURITY_MATURITY_DATE, \n"
				+ "PARAM.COUNTRY_ISO_CODE, \n"
				+ "PARAM.VALUATION_FREQUENCY, \n"
				+ "PARAM.VALUATION_FREQUENCY_UNIT, \n"
				+ "VAL1.VALUATION_DATE, \n"
				+ "NEXT_VAL_DATE(CAST(VAL1.VALUATION_DATE AS DATE),CAST(PARAM.VALUATION_FREQUENCY AS NUMBER), \n"
				+ "  CAST(PARAM.VALUATION_FREQUENCY_UNIT AS NUMBER)) AS EXPIRYDATE, \n"
				+ "CALENDAR_DAYS(CAST(current_timestamp AS DATE),NEXT_VAL_DATE(CAST(VAL1.VALUATION_DATE AS DATE),CAST(PARAM.VALUATION_FREQUENCY AS NUMBER), \n"
				+ "  CAST(PARAM.VALUATION_FREQUENCY_UNIT AS NUMBER))) daysdue, \n" + "CMS_SECURITY.CMV AS AMOUNT, \n"
				+ "CMS_SECURITY.CMV_CURRENCY AS CCY, \n" + "CMS_SECURITY.FSV AS F_AMOUNT, \n"
				+ "CMS_SECURITY.FSV_CURRENCY AS F_CCY, \n" + "CMS_SECURITY.SECURITY_SUB_TYPE_ID, \n"
				+ "CMS_SECURITY.TYPE_NAME, \n" + "SCI_LSP_LMT_PROFILE.CMS_ORIG_COUNTRY, \n"
				+ "SCI_LSP_LMT_PROFILE.CMS_ORIG_ORGANISATION, \n" + "SCI_LSP_APPR_LMTS.LMT_ID, \n"
				+ "(select COMMON_CODE_CATEGORY_ENTRY.ENTRY_NAME \n" + "from COMMON_CODE_CATEGORY_ENTRY \n"
				+ "where SCI_LSP_APPR_LMTS.LMT_PRD_TYPE_NUM = COMMON_CODE_CATEGORY_ENTRY.CATEGORY_CODE \n"
				+ "AND SCI_LSP_APPR_LMTS.LMT_PRD_TYPE_VALUE = COMMON_CODE_CATEGORY_ENTRY.ENTRY_CODE \n"
				+ "and sci_lsp_appr_lmts.SOURCE_ID = common_code_category_entry.ENTRY_SOURCE) as entry_name , \n"
				+ "SCI_LSP_SYS_XREF.LSX_EXT_SYS_ACCT_NUM, \n" + "SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID, \n"
				+ "SCI_LSP_LMT_PROFILE.LLP_SEGMENT_CODE_VALUE AS SEGMENT, \n" + "SCI_LE_SUB_PROFILE.LSP_LE_ID, \n"
				+ "SCI_LE_SUB_PROFILE.LSP_SHORT_NAME \n";

		String fromSQL = "FROM CMS_SECURITY, \n" + "CMS_SECURITY_SOURCE, \n" + "CMS_VALUATION VAL1, \n"
				+ "SCI_LSP_LMT_PROFILE, \n" + "SCI_LSP_APPR_LMTS, \n" + "CMS_LIMIT_SECURITY_MAP, \n"
				+ "CMS_SECURITY_PARAMETER PARAM, \n" + "SCI_LSP_LMTS_XREF_MAP,	\n" + "SCI_LSP_SYS_XREF, \n"
				+ "SCI_LE_SUB_PROFILE \n";
		// "SCI_LE_MAIN_PROFILE \n";
		// For Db2
		/*String whereSQL = "WHERE SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID \n"
				+
				// "AND SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID \n"
				// +
				"and CMS_SECURITY.CMS_COLLATERAL_ID = VAL1.CMS_COLLATERAL_ID \n"
				+ "AND CMS_SECURITY.CMS_COLLATERAL_ID = CMS_SECURITY_SOURCE.CMS_COLLATERAL_ID \n"
				+ "AND ((CMS_SECURITY.SECURITY_SUB_TYPE_ID LIKE 'AB%' \n"
				+ "  and CMS_SECURITY.SECURITY_SUB_TYPE_ID <> 'AB108') \n"
				+ "	OR CMS_SECURITY.SECURITY_SUB_TYPE_ID LIKE 'PT%') \n"
				+ "AND PARAM.COUNTRY_ISO_CODE = CMS_SECURITY.SECURITY_LOCATION \n"
				+ "AND PARAM.SECURITY_SUB_TYPE_ID = CMS_SECURITY.SECURITY_SUB_TYPE_ID \n"
				+ "AND CMS_SECURITY.CMS_COLLATERAL_ID = CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID \n"
				+ "AND CMS_LIMIT_SECURITY_MAP.CUSTOMER_CATEGORY = 'MB' \n"
				+ "AND CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID = SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID \n"
				+ "AND SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID \n"
				+ "AND SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = SCI_LSP_LMTS_XREF_MAP.CMS_LSP_APPR_LMTS_ID \n"
				+ "AND SCI_LSP_LMTS_XREF_MAP.CMS_LSP_SYS_XREF_ID = SCI_LSP_SYS_XREF.CMS_LSP_SYS_XREF_ID \n"
				+ "AND CALENDAR_DAYS(CAST(? AS DATE),NEXT_VAL_DATE(CAST(VAL1.VALUATION_DATE AS DATE), \n"
				+ "  CAST(PARAM.VALUATION_FREQUENCY AS NUMBER), CAST(PARAM.VALUATION_FREQUENCY_UNIT AS NUMBER))) = ? \n"
				+ "and val1.valuation_type = 'F' \n" + "and val1.VALUATION_ID = \n" + "	(SELECT v.valuation_id \n"
				+ "	from cms_valuation v \n" + "	where v.VALUATION_TYPE = 'F' \n"
				+ "	and v.valuation_date is not null \n" + "	and v.CMS_COLLATERAL_ID = val1.cms_collateral_id \n"
				+ "	order by v.valuation_date desc, v.valuation_id \n" + "	fetch first row only) \n";
*/	
			//For Oracle
		String whereSQL = "WHERE SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID \n"
				+
				// "AND SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID \n"
				// +
				"and CMS_SECURITY.CMS_COLLATERAL_ID = VAL1.CMS_COLLATERAL_ID \n"
				+ "AND CMS_SECURITY.CMS_COLLATERAL_ID = CMS_SECURITY_SOURCE.CMS_COLLATERAL_ID \n"
				+ "AND ((CMS_SECURITY.SECURITY_SUB_TYPE_ID LIKE 'AB%' \n"
				+ "  and CMS_SECURITY.SECURITY_SUB_TYPE_ID <> 'AB108') \n"
				+ "	OR CMS_SECURITY.SECURITY_SUB_TYPE_ID LIKE 'PT%') \n"
				+ "AND PARAM.COUNTRY_ISO_CODE = CMS_SECURITY.SECURITY_LOCATION \n"
				+ "AND PARAM.SECURITY_SUB_TYPE_ID = CMS_SECURITY.SECURITY_SUB_TYPE_ID \n"
				+ "AND CMS_SECURITY.CMS_COLLATERAL_ID = CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID \n"
				+ "AND CMS_LIMIT_SECURITY_MAP.CUSTOMER_CATEGORY = 'MB' \n"
				+ "AND CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID = SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID \n"
				+ "AND SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID \n"
				+ "AND SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = SCI_LSP_LMTS_XREF_MAP.CMS_LSP_APPR_LMTS_ID \n"
				+ "AND SCI_LSP_LMTS_XREF_MAP.CMS_LSP_SYS_XREF_ID = SCI_LSP_SYS_XREF.CMS_LSP_SYS_XREF_ID \n"
				+ "AND CALENDAR_DAYS(CAST(? AS DATE),NEXT_VAL_DATE(CAST(VAL1.VALUATION_DATE AS DATE), \n"
				+ "  CAST(PARAM.VALUATION_FREQUENCY AS NUMBER), CAST(PARAM.VALUATION_FREQUENCY_UNIT AS NUMBER))) = ? \n"
				+ "and val1.valuation_type = 'F' \n" + "and val1.VALUATION_ID = \n" + "(SELECT * FROM ( SELECT v.valuation_id \n"
				+ "	from cms_valuation v \n" + "	where v.VALUATION_TYPE = 'F' \n"
				+ "	and v.valuation_date is not null \n" + "	and v.CMS_COLLATERAL_ID = val1.cms_collateral_id \n"
				+ "	order by v.valuation_date desc, v.valuation_id \n" + ") TEMP WHERE ROWNUM<=1) \n";
		 
		String orderBySQL = "ORDER BY CMS_COLLATERAL_ID, LMT_ID";

		sqlBuffer.append(selectSQL);
		sqlBuffer.append(fromSQL);
		sqlBuffer.append(whereSQL);
		if (isByCountry) {
			sqlBuffer.append(" AND cms_security.security_location = ? ");
		}
		sqlBuffer.append(orderBySQL);

		return sqlBuffer.toString();
	}

}
