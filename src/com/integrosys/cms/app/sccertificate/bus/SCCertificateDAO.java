/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/SCCertificateDAO.java,v 1.14 2005/09/19 09:02:07 czhou Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//java
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * DAO for SCC
 * @author $Author: czhou $
 * @version $Revision: 1.14 $
 * @since $Date: 2005/09/19 09:02:07 $ Tag: $Name: $
 */

public class SCCertificateDAO implements ISCCertificateDAO {
	private DBUtil dbUtil = null;

	private static String SELECT_STAGE_SCCERT_TRX = "SELECT STAGE_SCC_GENERATED.SCC_ID, TRANSACTION.TRANSACTION_ID FROM STAGE_SCC_GENERATED, TRANSACTION "
			+ "WHERE TRANSACTION.TRANSACTION_TYPE = 'SCC' AND STATUS <> 'CLOSED' AND "
			+ "TRANSACTION.STAGING_REFERENCE_ID = STAGE_SCC_GENERATED.SCC_ID";

	private static String SELECT_SCCERT_TRX_COUNT = "SELECT COUNT(*)  FROM STAGE_SCC_GENERATED, TRANSACTION "
			+ "WHERE TRANSACTION.TRANSACTION_TYPE = 'SCC' AND STATUS <> 'CLOSED' AND "
			+ "TRANSACTION.STAGING_REFERENCE_ID = STAGE_SCC_GENERATED.SCC_ID";

	private static String SELECT_SCCERT_TRX = "select s.SCC_ID, t.TRANSACTION_ID, t.STATUS from TRANSACTION t, CMS_SCC_GENERATED s "
			+ "where t.TRANSACTION_TYPE = '"
			+ ICMSConstant.INSTANCE_SCC
			+ "' and t.STATUS <> '"
			+ ICMSConstant.STATE_CLOSED + "' and t.REFERENCE_ID = s.SCC_ID and s.CMS_LSP_LMT_PROFILE_ID = ?";

	private static String SELECT_ALL_SCCERT_TRX = "select s.SCC_ID, t.TRANSACTION_ID, t.STATUS from TRANSACTION t, CMS_SCC_GENERATED s "
			+ "where t.TRANSACTION_TYPE = '"
			+ ICMSConstant.INSTANCE_SCC
			+ "' and t.REFERENCE_ID = s.SCC_ID and s.CMS_LSP_LMT_PROFILE_ID = ?";

	/**
	 * To get the SCCID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return long - the SCC ID
	 * @throws SearchDAOException
	 */
	public long getSCCIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException {
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SELECT_SCCERT_TRX);
			dbUtil.setLong(1, aLimitProfileID);
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				return rs.getLong(CERTBL_SCC_ID);
			}
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getSCCIDbyLimitProfile", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getSCCIDbyLimitProfile", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getSCCIDbyLimitProfile", ex);
			}
		}
	}

	/**
	 * Get a list of ssc info.
	 * 
	 * @param criteria sccertificate search criteria
	 * @return SearchResult contains a list of SCCertificateSearchResult
	 * @throws SearchDAOException on error searching the scc
	 */
	public SearchResult getSCCbyLimitProfile(SCCertificateSearchCriteria criteria) throws SearchDAOException {
		try {
			String sql = null;

			if (criteria.isIncludeClosedSCC()) {
				sql = SELECT_ALL_SCCERT_TRX;
			}
			else {
				sql = SELECT_SCCERT_TRX;
			}
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, criteria.getLimitProfileID());
			ResultSet rs = dbUtil.executeQuery();
			ArrayList list = new ArrayList();
			while (rs.next()) {
				SCCertificateSearchResult scc = new SCCertificateSearchResult();
				scc.setSCCID(rs.getLong(CERTBL_SCC_ID));
				scc.setTrxID(rs.getString(TRX_ID));
				scc.setTrxStatus(rs.getString(TRX_STATUS));
				list.add(scc);
			}
			rs.close();
			return new SearchResult(0, 0, 0, list);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getSCCbyLimitProfile", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (Exception ex) {
				throw new SearchDAOException("Exception in getSCCbyLimitProfile", ex);
			}
		}
	}

	/**
	 * To get the SCC Trx ID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return String - the SCC Trx ID
	 * @throws SearchDAOException
	 */
	public String getSCCTrxIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException {
		StringBuffer buf = new StringBuffer();
		buf.append(" AND ");
		buf.append(STAGE_CERTBL_LIMIT_PROFILE_ID_PREF);
		buf.append(" = ?");
		String sql = SELECT_STAGE_SCCERT_TRX + " " + buf.toString();
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
			throw new SearchDAOException("SQLException in getSCCTrxIDbyLimitProfile", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getSCCTrxIDbyLimitProfile", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getSCCTrxIDbyLimitProfile", ex);
			}
		}
	}

	/**
	 * To get the number of sc certificate that satisfy the criteria
	 * @param aCriteria of SCCertificateSearchCriteria type
	 * @return int - the number of cc certificate that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfSCCertificate(SCCertificateSearchCriteria aCriteria) throws SearchDAOException {

		String sql = SELECT_SCCERT_TRX_COUNT;

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
			throw new SearchDAOException("SQLException in getNoOfSCCertificate", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getNoOfSCCertificate", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getNoOfSCCertificate", ex);
			}
		}
	}

	/**
	 * Constructs the SQL string based on the search criteria Populates the
	 * parameter list for DBUtil to perform JDBC variables binding For SCC
	 * Staging Table
	 * 
	 * @param aCriteria
	 * @param params
	 * @return String - constructed SQL
	 */
	private String getStagingSearchString(SCCertificateSearchCriteria aCriteria, ArrayList params) {
		DefaultLogger.info(this, "IN method getStagingSearchString");

		if (aCriteria == null) {
			return null;
		}

		StringBuffer buf = new StringBuffer();

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
		 * Oracle 9i JDB if ((aCriteria.getTrxStatusList() != null) &&
		 * (aCriteria.getTrxStatusList().length > 0)) { String[] trxStatus =
		 * aCriteria.getTrxStatusList(); String trxValues =
		 * CommonUtil.arrayToDelimStr(trxStatus); buf.append(" AND ");
		 * buf.append(TRX_STATUS_PREF); buf.append(CommonUtil.SQL_STRING_SET);
		 * params.add(trxValues);
		 * 
		 * }
		 */

		return buf.toString();
	}

}
