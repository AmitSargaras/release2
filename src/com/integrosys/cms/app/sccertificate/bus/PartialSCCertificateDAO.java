/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/PartialSCCertificateDAO.java,v 1.11 2005/09/19 09:02:07 czhou Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//java
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * DAO for Partial SCC
 * @author $Author: czhou $
 * @version $Revision: 1.11 $
 * @since $Date: 2005/09/19 09:02:07 $ Tag: $Name: $
 */

public class PartialSCCertificateDAO implements IPartialSCCertificateDAO {
	private DBUtil dbUtil = null;

	private static String SELECT_PSCCERT_TRX = "select p.PSCC_ID, t.TRANSACTION_ID, t.STATUS from TRANSACTION t, CMS_PSCC_GENERATED p "
			+ "where t.TRANSACTION_TYPE = '"
			+ ICMSConstant.INSTANCE_PSCC
			+ "' and t.STATUS <> '"
			+ ICMSConstant.STATE_CLOSED + "' and t.REFERENCE_ID = p.PSCC_ID and p.CMS_LSP_LMT_PROFILE_ID = ?";

	private static String SELECT_ALL_PSCCERT_TRX = "select p.PSCC_ID, t.TRANSACTION_ID, t.STATUS from TRANSACTION t, CMS_PSCC_GENERATED p "
			+ "where t.TRANSACTION_TYPE = '"
			+ ICMSConstant.INSTANCE_PSCC
			+ "' and t.REFERENCE_ID = p.PSCC_ID and p.CMS_LSP_LMT_PROFILE_ID = ?";

	private static String SELECT_STAGE_PSCCERT_TRX = "SELECT STAGE_PSCC_GENERATED.PSCC_ID, TRANSACTION.TRANSACTION_ID FROM STAGE_PSCC_GENERATED, TRANSACTION "
			+ "WHERE TRANSACTION.TRANSACTION_TYPE = 'PSCC' AND STATUS <> 'CLOSED' AND "
			+ "TRANSACTION.STAGING_REFERENCE_ID = STAGE_PSCC_GENERATED.PSCC_ID";

	private static String SELECT_PSCCERT_TRX_COUNT = "SELECT COUNT(*)  FROM STAGE_PSCC_GENERATED, TRANSACTION WHERE TRANSACTION.TRANSACTION_TYPE = 'PSCC' "
			+ "AND STATUS <> 'CLOSED' AND TRANSACTION.STAGING_REFERENCE_ID = STAGE_PSCC_GENERATED.PSCC_ID";

	/**
	 * To get the SCCID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return long - the SCC ID
	 * @throws SearchDAOException
	 */
	public long getPSCCIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException {
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SELECT_PSCCERT_TRX);
			dbUtil.setLong(1, aLimitProfileID);
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				return rs.getLong(CERTBL_PSCC_ID);
			}
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getPSCCIDbyLimitProfile", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getPSCCIDbyLimitProfile", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getPSCCIDbyLimitProfile", ex);
			}
		}
	}

	/**
	 * Get a list of pssc info.
	 * 
	 * @param criteria partial sccertificate search criteria
	 * @return SearchResult contains a list of PSCCertificateSearchResult
	 * @throws SearchDAOException on error searching the pscc
	 */
	public SearchResult getPSCCbyLimitProfile(PartialSCCertificateSearchCriteria criteria) throws SearchDAOException {
		try {
			String sql = null;

			if (criteria.isIncludeClosedPSCC()) {
				sql = SELECT_ALL_PSCCERT_TRX;
			}
			else {
				sql = SELECT_PSCCERT_TRX;
			}
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, criteria.getLimitProfileID());
			ResultSet rs = dbUtil.executeQuery();
			ArrayList list = new ArrayList();
			while (rs.next()) {
				PartialSCCertificateSearchResult scc = new PartialSCCertificateSearchResult();
				scc.setPSCCID(rs.getLong(CERTBL_PSCC_ID));
				scc.setTrxID(rs.getString(TRX_ID));
				scc.setTrxStatus(rs.getString(TRX_STATUS));
				list.add(scc);
			}
			rs.close();
			return new SearchResult(0, 0, 0, list);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getPSCCbyLimitProfile", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (Exception ex) {
				throw new SearchDAOException("Exception in getPSCCbyLimitProfile", ex);
			}
		}
	}

	/**
	 * To get the PSCC Trx ID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return String - the SCC Trx ID
	 * @throws SearchDAOException
	 */
	public String getPSCCTrxIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException {
		StringBuffer buf = new StringBuffer();
		buf.append(" AND ");
		buf.append(STAGE_CERTBL_LIMIT_PROFILE_ID_PREF);
		buf.append(" = ?");
		String sql = SELECT_STAGE_PSCCERT_TRX + " " + buf.toString();
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, aLimitProfileID);
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				return rs.getString(TRX_ID);
			}
			return null;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getPSCCTrxIDbyLimitProfile", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getPSCCTrxIDbyLimitProfile", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getPSCCTrxIDbyLimitProfile", ex);
			}
		}
	}

	/**
	 * To get the number of partial sc certificate that satisfy the criteria
	 * @param aCriteria of SCCertificateSearchCriteria type
	 * @return int - the number of partial sc certificate that satisfy the
	 *         criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfPartialSCCertificate(SCCertificateSearchCriteria aCriteria) throws SearchDAOException {
		String sql = SELECT_PSCCERT_TRX_COUNT;

		try {
			ArrayList params = new ArrayList();
			String conditionPart = getStagingSearchString(aCriteria, params);
			if (conditionPart != null) {
				sql += conditionPart;
			}
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);
			ResultSet rs = dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			return count;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getNoOfPartialSCCertificate", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getNoOfPartialSCCertificate", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getNoOfPartialSCCertificate", ex);
			}
		}
	}

	/**
	 * Constructs the SQL string based on the search criteria Populates the
	 * parameter list for DBUtil to perform JDBC variables binding For PSCC
	 * Staging Table
	 * 
	 * @param aCriteria
	 * @param params
	 * @return String - constructed SQL
	 */
	private String getStagingSearchString(SCCertificateSearchCriteria aCriteria, ArrayList params) {
		StringBuffer buf = new StringBuffer();

		if (aCriteria == null) {
			return null;
		}

		if (aCriteria.getLimitProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			buf.append(" AND ");
			buf.append(STAGE_CERTBL_LIMIT_PROFILE_ID_PREF);
			buf.append(" = ?");
			params.add(new Long(aCriteria.getLimitProfileID()));
		}

		/**
		 * construct a dynamic IN SQL list for binding
		 */

		String[] trxStatus = aCriteria.getTrxStatusList();

		if (!CommonUtil.isEmptyArray(trxStatus)) {
			buf.append(" AND ");
			buf.append(TRX_STATUS_PREF);
			CommonUtil.buildSQLInList(trxStatus, buf, params);

		}

		/*
		 * Oracle 9i JDBC if ((aCriteria.getTrxStatusList() != null) &&
		 * (aCriteria.getTrxStatusList().length > 0)) { String[] trxStatus =
		 * aCriteria.getTrxStatusList(); String trxValues =
		 * CommonUtil.arrayToDelimStr(trxStatus); buf.append(" AND ");
		 * buf.append(TRX_STATUS_PREF); buf.append(CommonUtil.SQL_STRING_SET);
		 * params.add(trxValues); }
		 */

		return buf.toString();
	}

}
