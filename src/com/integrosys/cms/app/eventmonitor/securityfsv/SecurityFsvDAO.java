/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/securityfsv/SecurityFsvDAO.java,v 1.16 2006/08/11 02:59:09 hmbao Exp $
 */
package com.integrosys.cms.app.eventmonitor.securityfsv;

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
import com.integrosys.cms.app.eventmonitor.common.OBFacilityInfo;

/**
 * @author $Author: hmbao $
 * @version $Revision: 1.16 $
 * @since $Date: 2006/08/11 02:59:09 $ Tag: $Name: $
 */
public class SecurityFsvDAO extends AbstractMonitorDAO implements IMonitorDAO {

	public IMonitorDAOResult getInitialSet(IRuleParam ruleParam) throws MonitorDaoResultRetrievalFailureException {

		String sql = getSQLStatement(ruleParam.hasCountryCode());

		List argList = new ArrayList();
		if (ruleParam.hasCountryCode()) {
			argList.add(ruleParam.getCountryCode());
			argList.add(ruleParam.getCountryCode());
		}

		List resultList = (List) getJdbcTemplate().query(sql, argList.toArray(), new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return processResultSet(rs);
			}
		});

		return new SecurityFsvDAOResult(resultList);

	}

	/**
	 * The following method is to be overriden by implementing classes
	 */
	private List processResultSet(ResultSet rs) throws SQLException {
		HashMap secMap = new HashMap();
		if (rs != null) {
			OBSecurityFsvInfo nn = null;
			while (rs.next()) {
				String secID = rs.getString("CMS_COLLATERAL_ID");
				nn = (OBSecurityFsvInfo) secMap.get(secID);
				if (nn == null) {
					nn = new OBSecurityFsvInfo();
					nn.setLeID(rs.getString("LSP_LE_ID"));
					nn.setLeName(rs.getString("LSP_SHORT_NAME"));
					nn.setSegment(rs.getString("SEGMENT"));
					nn.setOriginatingCountry(rs.getString("CMS_ORIG_COUNTRY"));
					nn.setOriginatingOrganisation(rs.getString("CMS_ORIG_ORGANISATION"));
					nn.setSecurityType(rs.getString("TYPE_NAME"));
					nn.setSecuritySubtype(rs.getString("SUBTYPE_NAME"));
					nn.setSecurityId(rs.getLong("SCI_SECURITY_DTL_ID"));
					nn.setArmName(rs.getString("ARMNAME"));
					String ccy = rs.getString("FSV_CURRENCY");
					String fsv = rs.getString("FSV");
					String minFsv = rs.getString("MINIMAL_FSV");
					if ((ccy != null) && !"".equals(ccy) && (fsv != null) && (minFsv != null)) {
						nn.setSecurityFsv(new Amount(Double.valueOf(fsv).doubleValue(), ccy));
						nn.setSecurityMinFsv(new Amount(Double.valueOf(minFsv).doubleValue(), ccy));
					}
					else {
						nn.setSecurityFsv(new Amount(0, ""));
						nn.setSecurityMinFsv(new Amount(0, ""));
					}
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
				String accID = rs.getString("LXM_ID");
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
		sqlBuffer.append(getSelectStmt());
		sqlBuffer.append(getFromStmt(false));
		sqlBuffer.append(getConditionStmt(isByCountry, false));
		sqlBuffer.append(" UNION ALL ");
		sqlBuffer.append(getSelectStmt());
		sqlBuffer.append(getFromStmt(true));
		sqlBuffer.append(getConditionStmt(isByCountry, true));
		return sqlBuffer.toString();
	}

	private String getSelectStmt() {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT CMS_SECURITY.CMS_COLLATERAL_ID,");
		sqlBuffer.append("CMS_SECURITY.SCI_SECURITY_DTL_ID,");
		sqlBuffer.append("CMS_SECURITY_SOURCE.SOURCE_SECURITY_ID AS SOURCE_ID,");
		sqlBuffer.append("CMS_SECURITY.FSV_CURRENCY,");
		sqlBuffer.append("CMS_SECURITY.FSV,");
		sqlBuffer.append("item.MINIMAL_FSV,");
		sqlBuffer.append("CMS_SECURITY.TYPE_NAME,");
		sqlBuffer.append("CMS_SECURITY.SUBTYPE_NAME,");
		sqlBuffer.append("CMS_SECURITY.SECURITY_SUB_TYPE_ID,");
		sqlBuffer.append("SCI_LSP_LMT_PROFILE.CMS_ORIG_COUNTRY,");
		sqlBuffer.append("SCI_LSP_LMT_PROFILE.CMS_ORIG_ORGANISATION,");
		sqlBuffer.append("SCI_LE_SUB_PROFILE.LSP_SHORT_NAME,");
		sqlBuffer.append("SCI_LE_SUB_PROFILE.LSP_LE_ID,");
		sqlBuffer.append("SCI_LE_MAIN_PROFILE.LMP_SGMNT_CODE_VALUE AS SEGMENT,");
		sqlBuffer.append("FAM.FAM_NAME ARMNAME,");
		sqlBuffer.append("SCI_LSP_APPR_LMTS.LMT_ID,");
		sqlBuffer.append("COMMON_CODE_CATEGORY_ENTRY.ENTRY_NAME,");
		sqlBuffer.append("SCI_LSP_LMTS_XREF_MAP.LXM_ID");
		return sqlBuffer.toString();
	}

	private String getFromStmt(boolean isCoBorrower) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" FROM CMS_SECURITY,");
		sqlBuffer.append("SCI_LSP_LMT_PROFILE,");
		sqlBuffer.append("SCI_LE_SUB_PROFILE,");
		sqlBuffer.append("SCI_LSP_APPR_LMTS,");
		if (isCoBorrower) {
			sqlBuffer.append("SCI_LSP_CO_BORROW_LMT,");
		}
		sqlBuffer.append("SCI_LE_MAIN_PROFILE,");
		sqlBuffer.append("CMS_LIMIT_SECURITY_MAP,");
		sqlBuffer.append("CMS_SECURITY_SOURCE, ");
		sqlBuffer.append("COMMON_CODE_CATEGORY_ENTRY,");
		sqlBuffer.append("SCI_LSP_LMTS_XREF_MAP,");
		sqlBuffer.append("(SELECT ms.minimal_fsv, ms.cms_collateral_id FROM CMS_MARKETABLE_SEC ms");
		sqlBuffer.append(" UNION ALL ");
		sqlBuffer.append("SELECT gt.minimal_fsv, gt.cms_collateral_id FROM CMS_GUARANTEE gt");
		sqlBuffer.append(" UNION ALL ");
		sqlBuffer.append("SELECT cs.minimal_fsv, cs.cms_collateral_id FROM CMS_CASH cs) ITEM,");
		sqlBuffer.append("CUST_LOC_FAM FAM");
		return sqlBuffer.toString();
	}

	private String getConditionStmt(boolean isByCountry, boolean isCoBorrower) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" WHERE CMS_SECURITY.CMS_COLLATERAL_ID = ITEM.CMS_COLLATERAL_ID");
		sqlBuffer.append(" AND CMS_SECURITY.CMS_COLLATERAL_ID = CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID");
		if (isCoBorrower) {
			sqlBuffer.append(getCBSecCustConnStmt());
		}
		else {
			sqlBuffer.append(getMBSecCustConnStmt());
		}
		sqlBuffer.append(" AND CMS_SECURITY.CMS_COLLATERAL_ID = CMS_SECURITY_SOURCE.CMS_COLLATERAL_ID");
		sqlBuffer.append(" AND SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = SCI_LSP_LMTS_XREF_MAP.CMS_LSP_APPR_LMTS_ID");
		sqlBuffer.append(" AND SCI_LSP_APPR_LMTS.LMT_PRD_TYPE_NUM = COMMON_CODE_CATEGORY_ENTRY.CATEGORY_CODE");
		sqlBuffer.append(" AND SCI_LSP_APPR_LMTS.LMT_PRD_TYPE_VALUE = COMMON_CODE_CATEGORY_ENTRY.ENTRY_CODE");
		sqlBuffer
				.append(" AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID ");
		sqlBuffer.append(" AND CMS_SECURITY.FSV < ITEM.MINIMAL_FSV ");
		sqlBuffer.append(" AND SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = FAM.CUSTOMER_ID");
		sqlBuffer.append(" AND SCI_LSP_LMT_PROFILE.CMS_ORIG_COUNTRY = FAM.BKG_LOC_CTRY");
		sqlBuffer.append(" AND SCI_LSP_LMT_PROFILE.CMS_ORIG_ORGANISATION = FAM.BKG_LOC_ORG");
		if (isByCountry) {
			sqlBuffer.append(" AND CMS_SECURITY.security_location = ? ");
		}
		return sqlBuffer.toString();
	}
}
