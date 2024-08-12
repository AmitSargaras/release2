package com.integrosys.cms.app.eventmonitor.collateralmaturity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IMonitorDAOResult;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.MonitorDaoResultRetrievalFailureException;
import com.integrosys.cms.app.eventmonitor.VelocityNotificationUtil;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;
import com.integrosys.cms.app.eventmonitor.common.OBFacilityInfo;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * Describe this class.
 * 
 * @Purpose:
 * @Description:
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-7-20
 * @Tag com.integrosys.cms.app.eventmonitor.collateralmaturity.
 *      AbstractCollateralMaturityDAO.java
 */
public abstract class AbstractCollateralMaturityDAO extends AbstractMonitorDAO implements IMonitorDAO,
		ICMSTrxTableConstants {
	
	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException {

		OBDateRuleParam param = (OBDateRuleParam) ruleParam;
		String sql = getSQLStatement(ruleParam.hasCountryCode());

		List argList = new ArrayList();
		argList.add(new Date(param.getSysDate().getTime()));

		if (ruleParam.hasCountryCode()) {
			argList.add(param.getCountryCode());
		}

		argList.add(new Date(param.getSysDate().getTime()));
		argList.add(new Integer(param.getNumOfDays()));
//		argList.add(new Date(param.getSysDate().getTime()));
//
//		if (ruleParam.hasCountryCode()) {
//			argList.add(param.getCountryCode());
//		}
//
//		argList.add(new Date(param.getSysDate().getTime()));
//		argList.add(new Integer(param.getNumOfDays()));

		List resultList = (List) getJdbcTemplate().query(sql, argList.toArray(), new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return processResultSet(rs);
			}
		});
		resultList = populateCustomerLimitAccountInfo(resultList);

		return new CollateralMaturityDAOResult(resultList);
	}
	
	/**
	 * Process resultset to return a list of results.
	 * 
	 * @param ResultSet rs
	 * @return List - list of results
	 * @throws Exception
	 */
	protected List processResultSet(ResultSet rs) throws SQLException {
		//HashMap secMap = new HashMap();
		List resultList = new ArrayList();
		
		if (rs != null) {
			while (rs.next()) {
				OBCollateralMaturityInfo nn = new OBCollateralMaturityInfo();
				String secID = rs.getString("CMS_COLLATERAL_ID");	
				
				nn.setCollateralID(rs.getLong("CMS_COLLATERAL_ID"));
				
				String ccy = rs.getString("CCY");
				String amount = rs.getString("AMOUNT");
				if ((ccy != null) && !"".equals(ccy) && (amount != null)) {
					nn.setAmount(new Amount(Double.valueOf(amount).doubleValue(), ccy));
				}
				else {
					nn.setAmount(new Amount(0, ""));
				}
				ccy = rs.getString("F_CCY");
				amount = rs.getString("F_AMOUNT");
				if ((ccy != null) && !"".equals(ccy) && (amount != null)) {
					nn.setFsvAmount(new Amount(Double.valueOf(amount).doubleValue(), ccy));
				}

				nn.setValFreq(rs.getInt("VALUATION_FREQUENCY"));
				nn.setValFreqUnit(rs.getString("VALUATION_FREQUENCY_UNIT"));
				nn.setValuationDate(rs.getDate("VALUATION_DATE"));
				nn.setExpiryDate(rs.getDate("EXPIRYDATE"));

				// eon bank Security ID is using CMS internal ID
				//nn.setSciSecurityID(rs.getString("SCI_SECURITY_DTL_ID"));
				nn.setSciSecurityID(secID);
				
				nn.setDaysDue(rs.getInt("DAYSDUE"));
				nn.setMaturityDate(rs.getDate("SECURITY_MATURITY_DATE"));

				nn.setSubType(rs.getString("SECURITY_SUB_TYPE_ID"));
				nn.setType(rs.getString("TYPE_NAME"));

				resultList.add(nn);
				/*
				HashMap sourceIDMap = nn.getSourceIDMap();
				String sourceID = rs.getString("SOURCE_ID");
				if ((sourceID != null) && (sourceIDMap.get(sourceID) == null)) {
					sourceIDMap.put(sourceID, sourceID);
				}
				*/

			}
		}
		//return VelocityNotificationUtil.convertMap2List(secMap);
		return resultList;
	}

	protected List populateCustomerLimitAccountInfo (List monitorList) {
		List resultList = new ArrayList();
				
		if (monitorList != null && !monitorList.isEmpty()) {
			String sql = getCustomerInfoSQL();
			for (int i = 0; i < monitorList.size(); i++) {
				final OBCollateralMaturityInfo nn = (OBCollateralMaturityInfo)monitorList.get(i);
				List argList = new ArrayList();
				argList.add(new Long(nn.getCollateralID()));
				
				List customerInfoList = (List) getJdbcTemplate().query(sql, argList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						return processCustomerInfoResultSet(rs, nn);
					}
				});	
				resultList.addAll(customerInfoList);
			}
		}
		return resultList;
	}
	
	private List processCustomerInfoResultSet (ResultSet rs, OBCollateralMaturityInfo obj) throws SQLException {
		List resultList = new ArrayList();
		String leID = "";
		OBCollateralMaturityInfo nn = null;
		if (rs != null) {
			while (rs.next()) {
				String tempLeID = rs.getString("LSP_LE_ID");
			//	DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<tempLeID: "+tempLeID);
				if (!leID.equals(tempLeID)) {
					if (nn != null) {
						resultList.add(nn);
					}
					
					leID = tempLeID;
					
					nn = new OBCollateralMaturityInfo();
					nn.setOriginatingCountry(rs.getString("CMS_ORIG_COUNTRY"));
					nn.setOriginatingOrganisation(rs.getString("CMS_ORIG_ORGANISATION"));
					nn.setLeID(tempLeID);
					nn.setLeName(rs.getString("LSP_SHORT_NAME"));
					nn.setSegment(rs.getString("SEGMENT"));		
					
					// set collateral info
					nn.setAmount(obj.getAmount());
					nn.setCollateralID(obj.getCollateralID());
					nn.setDaysDue(obj.getDaysDue());
					nn.setExpiryDate(obj.getExpiryDate());
					nn.setFsvAmount(obj.getFsvAmount());
					nn.setMaturityDate(obj.getMaturityDate());
					nn.setSciSecurityID(obj.getSciSecurityID());
					nn.setSubType(obj.getSubType());
					nn.setType(obj.getType());
					nn.setValFreq(obj.getValFreq());
					nn.setValFreqUnit(obj.getValFreqUnit());
					nn.setValuationDate(obj.getValuationDate());
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
				String accID = rs.getString("LXM_ID");
				HashMap accIDMap = obFac.getAccIDMap();
				if (accIDMap.get(accID) == null) {
					accIDMap.put(accID, accID);
				}							
			}
			if (nn != null) resultList.add(nn);
		}
		return resultList;
	}
	
	protected String getCustomerInfoSQL() {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(getSelectCustomerInfoStmt());
		sqlBuffer.append(getFromCustomerInfoStmt());
		sqlBuffer.append(getConditionCustomerInfoStmt());
		sqlBuffer.append("ORDER BY LSP_LE_ID, LMT_ID");
		return sqlBuffer.toString();
	}
	
	private String getSelectCustomerInfoStmt() {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT SCI_LSP_LMT_PROFILE.CMS_ORIG_COUNTRY,");
		sqlBuffer.append("SCI_LSP_LMT_PROFILE.CMS_ORIG_ORGANISATION,");
		sqlBuffer.append("SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, ");
		sqlBuffer.append("SCI_LE_SUB_PROFILE.LSP_LE_ID, ");
		if (PropertiesConstantHelper.requireBizSegment()) {
			if (PropertiesConstantHelper.isFilterByApplicationType()) {
				sqlBuffer.append("SCI_LSP_LMT_PROFILE.APPLICATION_TYPE AS SEGMENT, ");
			} else {
				sqlBuffer.append("SCI_LE_MAIN_PROFILE.LMP_SGMNT_CODE_VALUE AS SEGMENT, ");
			}
		}

		sqlBuffer.append("(SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY "); 
		sqlBuffer.append("WHERE CATEGORY_CODE = 'PRODUCT_TYPE' ");
		sqlBuffer.append("AND ENTRY_CODE = (SCI_LSP_APPR_LMTS.LMT_PRD_TYPE_VALUE ");
		sqlBuffer.append("|| '|' || SCI_LSP_APPR_LMTS.LMT_CRRNCY_ISO_CODE)) AS ENTRY_NAME, ");
		sqlBuffer.append("SCI_LSP_APPR_LMTS.LMT_ID, ");
		sqlBuffer.append("SCI_LSP_LMTS_XREF_MAP.LXM_ID ");	
		
		return sqlBuffer.toString();
	}
	
	private String getFromCustomerInfoStmt() {
		StringBuffer sqlBuffer = new StringBuffer();
		
		sqlBuffer.append("FROM SCI_LSP_LMT_PROFILE,");
		sqlBuffer.append("SCI_LE_SUB_PROFILE,");
		
		if (PropertiesConstantHelper.requireBizSegment() && 
			!PropertiesConstantHelper.isFilterByApplicationType()) {		
			sqlBuffer.append("SCI_LE_MAIN_PROFILE,");
		}
				
		sqlBuffer.append("SCI_LSP_APPR_LMTS LEFT OUTER JOIN SCI_LSP_LMTS_XREF_MAP ");
		sqlBuffer.append(" ON SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = SCI_LSP_LMTS_XREF_MAP.CMS_LSP_APPR_LMTS_ID, ");
		sqlBuffer.append("CMS_LIMIT_SECURITY_MAP ");

		return sqlBuffer.toString();
	}
	
	private String getConditionCustomerInfoStmt() {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("WHERE SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID ");
		sqlBuffer.append("AND SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID ");
		
		if (PropertiesConstantHelper.requireBizSegment() && 
				!PropertiesConstantHelper.isFilterByApplicationType()) {	
			sqlBuffer.append(" AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID");
		}
		sqlBuffer.append("AND SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID ");
		sqlBuffer.append("AND (CMS_LIMIT_SECURITY_MAP.UPDATE_STATUS_IND IS NULL OR CMS_LIMIT_SECURITY_MAP.UPDATE_STATUS_IND <> 'D') ");
		sqlBuffer.append("AND CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID = ? ");
				
		return sqlBuffer.toString();
	}
	
	protected String getSQLStatement(boolean isByCountry) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(getSelectStmt());
		sqlBuffer.append(getFromStmt(false));
		sqlBuffer.append(getConditionStmt(isByCountry, false));
//		sqlBuffer.append(" UNION ALL ");
//		sqlBuffer.append(getSelectStmt());
//		sqlBuffer.append(getFromStmt(true));
//		sqlBuffer.append(getConditionStmt(isByCountry, true));
		sqlBuffer.append(" ORDER BY CMS_COLLATERAL_ID ");
		return sqlBuffer.toString();
	}

	abstract protected String getSelectStmt();

	abstract protected String getFromStmt(boolean isCoBorrower);

	abstract protected String getConditionStmt(boolean isByCountry, boolean isCoBorrower);

	protected String getCommonSelectStmt() {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT CMS_SECURITY.cms_collateral_id,");
		sqlBuffer.append("CMS_SECURITY.sci_security_dtl_id, ");
		//sqlBuffer.append("CMS_SECURITY_SOURCE.SOURCE_SECURITY_ID AS SOURCE_ID,");
		sqlBuffer.append("CMS_SECURITY.CMV_CURRENCY AS CCY,");
		sqlBuffer.append("CMS_SECURITY.CMV AS AMOUNT,");
		sqlBuffer.append("CMS_SECURITY.FSV_CURRENCY AS F_CCY,");
		sqlBuffer.append("CMS_SECURITY.FSV AS F_AMOUNT,");
		sqlBuffer.append("CMS_SECURITY.TYPE_NAME,");
		sqlBuffer.append("CMS_SECURITY.SUBTYPE_NAME,");
		sqlBuffer.append("CMS_SECURITY.SECURITY_SUB_TYPE_ID,");
		
		//sqlBuffer.append("CMS_LIMIT_SECURITY_MAP.CUSTOMER_CATEGORY, ");
		//sqlBuffer.append("CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID, ");

		sqlBuffer.append("PARAM.VALUATION_FREQUENCY,");
		sqlBuffer.append("PARAM.VALUATION_FREQUENCY_UNIT,"); 
		sqlBuffer.append("CMS_SECURITY.LAST_REMARGIN_DATE AS VALUATION_DATE,");
		//For Db2
//		sqlBuffer.append("NEXT_VAL_DATE ( cast(CMS_SECURITY.LAST_REMARGIN_DATE as date), cast(PARAM.VALUATION_FREQUENCY as bigint) ,cast (PARAM.VALUATION_FREQUENCY_UNIT as  BIGINT) ) AS EXPIRYDATE,");
		//For Oracle
		sqlBuffer.append("NEXT_VAL_DATE ( cast(CMS_SECURITY.LAST_REMARGIN_DATE as date), cast(PARAM.VALUATION_FREQUENCY as NUMBER) ,cast (PARAM.VALUATION_FREQUENCY_UNIT as  NUMBER) ) AS EXPIRYDATE,");

		return sqlBuffer.toString();
	}

	protected String getCommonFromStmt(boolean isCoBorrower) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("CMS_SECURITY,");
		//sqlBuffer.append("CMS_SECURITY_SOURCE, ");

		
		//sqlBuffer.append("CMS_LIMIT_SECURITY_MAP,");

		sqlBuffer.append("CMS_SECURITY_PARAMETER PARAM ");
		return sqlBuffer.toString();
	}

	protected String getCommonConditionStmt(boolean isByCountry, boolean isCoBorrower) {
		StringBuffer sqlBuffer = new StringBuffer();
		//sqlBuffer.append(" WHERE CMS_SECURITY.CMS_COLLATERAL_ID = CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID");
		//sqlBuffer.append(" AND CMS_SECURITY.CMS_COLLATERAL_ID = CMS_SECURITY_SOURCE.CMS_COLLATERAL_ID");
		sqlBuffer.append(" WHERE CMS_SECURITY.STATUS NOT IN ('DELETED', 'PENDING_DELETE')");
		//sqlBuffer.append(" AND (CMS_LIMIT_SECURITY_MAP.UPDATE_STATUS_IND IS NULL OR CMS_LIMIT_SECURITY_MAP.UPDATE_STATUS_IND <> 'D') ");
		sqlBuffer.append(" AND PARAM.COUNTRY_ISO_CODE = CMS_SECURITY.SECURITY_LOCATION");
		sqlBuffer.append(" AND PARAM.SECURITY_SUB_TYPE_ID = CMS_SECURITY.SECURITY_SUB_TYPE_ID");
		/*
		if (isCoBorrower) {
			sqlBuffer.append(" AND CMS_LIMIT_SECURITY_MAP.CUSTOMER_CATEGORY = 'CB' ");
		//	sqlBuffer.append(getCBSecCustConnStmt());
		}
		else {
			sqlBuffer.append(" AND CMS_LIMIT_SECURITY_MAP.CUSTOMER_CATEGORY = 'MB' ");
			//sqlBuffer.append(getMBSecCustConnStmt());
		}
		*/
		if (isByCountry) {
			sqlBuffer.append(" AND CMS_SECURITY.SECURITY_LOCATION = ?");
		}
		return sqlBuffer.toString();
	}
}