/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/CollateralTrxDAO.java,v 1.14 2006/07/19 11:10:42 wltan Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.StpTrxStatusReadyIndicator;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.component.bizstructure.app.constants.BizstructureConstant;

/**
 * DAO for collateral transaction.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/07/19 11:10:42 $ Tag: $Name: $
 */
public class CollateralTrxDAO extends JdbcTemplateAdapter implements ICollateralTrxDAO {

	private final Logger logger = LoggerFactory.getLogger(CollateralTrxDAO.class);

	private static final String SELECT_TRXID = "select TRANSACTION_ID from TRANSACTION";

	private static final String SELECT_REFID = "select REFERENCE_ID, STAGING_REFERENCE_ID from TRANSACTION";

	private static final String SELECT_TRX_VALUE = "select TRANSACTION_ID, REFERENCE_ID, STAGING_REFERENCE_ID from TRANSACTION";

	private static final String SELECT_TRX_SEC_SUBTYPE_ID = "SELECT col.security_sub_type_id, trx.transaction_subtype"
			+ "  FROM cms_security col, TRANSACTION trx" + " WHERE trx.reference_id = col.cms_collateral_id(+)"
			+ "   AND trx.transaction_id = ?";

	private static final String SELECT_TRX_SEC_SUBTYPE_ID_DB2 = "SELECT col.security_sub_type_id, trx.transaction_subtype"
			+ " FROM TRANSACTION trx, cms_security col where "
			+ "trx.reference_id = col.cms_collateral_id and trx.transaction_id = ?";

	/**
	 * Default Constructor
	 */
	public CollateralTrxDAO() {
	}

	/**
	 * Get security sub type ID for a collateral transaction.
	 * 
	 * @param aTrxID - primitive long denoting the collateral transaction
	 * @return String denoting the security sub type
	 * @throws SearchDAOException
	 */
	public String[] getSecuritySubTypeForTrxByTrxID(long aTrxID) throws SearchDAOException {

		if (aTrxID == ICMSConstant.LONG_INVALID_VALUE) {
			return null;
		}

		String dbUsed = PropertyManager.getValue(BizstructureConstant.DB_USED_PROPERTY_NAME);

		String sql = (BizstructureConstant.DB2_DATABASE.equals(dbUsed)) ? SELECT_TRX_SEC_SUBTYPE_ID_DB2
				: SELECT_TRX_SEC_SUBTYPE_ID;

		ResultSetExtractor resultSetExtractor = new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					String[] returnStr = new String[2];
					returnStr[0] = rs.getString("security_sub_type_id");
					returnStr[1] = rs.getString("transaction_subtype");
					return returnStr;
				}

				return null;
			}
		};

		String[] returnStr = (String[]) getJdbcTemplate().query(sql, new Object[] { String.valueOf(aTrxID) },
				resultSetExtractor);

		if (returnStr == null) {
			logger.warn("failed to retrieve transaction value with actual collateral table, trying staging table.");
			String query = "SELECT col.security_sub_type_id, trx.transaction_subtype"
					+ " FROM TRANSACTION trx, cms_stage_security col where "
					+ "trx.staging_reference_id = col.cms_collateral_id and trx.transaction_id = ?";

			returnStr = (String[]) getJdbcTemplate().query(query, new Object[] { String.valueOf(aTrxID) },
					resultSetExtractor);

		}
		return returnStr;
	}

	/**
	 * Get transaction value objects by a list of reference ids.
	 * 
	 * @param cols of type collaterals that contain reference id
	 * @return a list of transaction values
	 * @throws SearchDAOException on error searching the transaction
	 */
	public ICollateralTrxValue[] getTrxValueByRefIDs(ICollateral[] cols) throws SearchDAOException {
		String sql = constructTrxValueByRefIDsSQL(cols);

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBCollateralTrxValue trxValue = new OBCollateralTrxValue();

				trxValue.setTransactionID(rs.getString(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID));
				trxValue.setReferenceID(rs.getString(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID));
				trxValue.setStagingReferenceID(rs.getString(ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID));

				return trxValue;
			}

		});

		return (ICollateralTrxValue[]) resultList.toArray(new ICollateralTrxValue[0]);
	}

	/**
	 * Get transaction id given the reference id and transaction type. The refID
	 * can be actual reference id or staging reference id.
	 * 
	 * @param refID actual or staging reference id
	 * @param trxType transaction type
	 * @return transaction id
	 * @throws SearchDAOException on error getting the transaction id
	 */
	public String getTransactionID(String refID, String trxType) throws SearchDAOException {
		String sql = SELECT_TRXID + constructByRefIDSQL(refID, trxType);

		return (String) getJdbcTemplate().query(sql, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID);
				}

				return null;
			}

		});

	}

	/**
	 * Get transaction id given its transaction reference id.
	 * 
	 * @param trxRefID parent transaction id
	 * @param trxType transaction type
	 * @return transaction id
	 * @throws SearchDAOException on error getting the transaction id
	 */
	public String getTrxIDByTrxRefID(String trxRefID, String trxType) throws SearchDAOException {
		String sql = SELECT_TRXID + constructByTrxRefIDSQL(trxRefID, trxType);

		return (String) getJdbcTemplate().query(sql, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID);
				}

				return null;
			}

		});

	}

	/**
	 * Get collateral transaction ids which is related to transaction reference
	 * id.
	 * 
	 * @param trxRefID transaction reference id
	 * @return a list of collateral transaction ids belong to the trx reference
	 *         id
	 * @throws SearchDAOException on error getting the transaction ids
	 */
	public String[] getColTrxIDsByTrxRefID(String trxRefID) throws SearchDAOException {
		String sql = SELECT_TRXID + constructByTrxRefIDSQL(trxRefID, ICMSConstant.INSTANCE_COLLATERAL, null);

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID);
			}
		});

		return (String[]) resultList.toArray(new String[0]);
	}

	/**
	 * Get reference id given its transaction id.
	 * 
	 * @param trxID transaction id
	 * @return reference id
	 * @throws SearchDAOException on error getting the reference id
	 */
	public String getRefIDByTrxID(String trxID) throws SearchDAOException {
		String sql = SELECT_REFID + constructByTrxIDSQL(trxID);

		return (String) getJdbcTemplate().query(sql, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					rs.getString(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID);
				}

				return null;
			}

		});
	}

	/**
	 * Get staging reference id given its transaction id.
	 * 
	 * @param trxID transaction id
	 * @return staging reference id
	 * @throws SearchDAOException on error getting the staging reference id
	 */
	public String getStagingRefIDByTrxID(String trxID) throws SearchDAOException {
		String sql = SELECT_REFID + constructByTrxIDSQL(trxID);

		return (String) getJdbcTemplate().query(sql, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					rs.getString(ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID);
				}

				return null;
			}

		});
	}

	/**
	 * Helper method to construct where clause sql given actual/staging
	 * reference id and its transaction type.
	 * 
	 * @param refID actual or staging reference id
	 * @param trxType transaction type
	 * @return String representation of transaction id
	 */
	private String constructByRefIDSQL(String refID, String trxType) {
		StringBuffer buf = new StringBuffer();
		buf.append(" where (");
		buf.append(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID);
		buf.append(" = ");
		buf.append(refID);
		buf.append(" or ");
		buf.append(ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID);
		buf.append(" = ");
		buf.append(refID);
		buf.append(") and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_TYPE);
		buf.append(" = '");
		buf.append(trxType);
		buf.append("'");

		return buf.toString();
	}

	/**
	 * Helper method to construct where clause sql based on transaction
	 * reference id and transaction type.
	 * 
	 * @param trxRefID parent transaction id
	 * @param trxType transaction type
	 * @return String representation of transction id
	 */
	private String constructByTrxRefIDSQL(String trxRefID, String trxType) {
		StringBuffer buf = new StringBuffer();
		buf.append(" where ");
		buf.append(ICMSTrxTableConstants.TRXTBL_TRX_REFERENCE_ID);
		buf.append(" = ");
		buf.append(trxRefID);
		buf.append(" and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_TYPE);
		buf.append(" = '");
		buf.append(trxType);
		buf.append("'");
		buf.append(" and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS);
		buf.append(" <> '");
		buf.append(ICMSConstant.STATE_CANCELLED);
		buf.append("' and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS);
		buf.append(" <> '");
		buf.append(ICMSConstant.STATE_CLOSED);
		buf.append("'");
		return buf.toString();
	}

	/**
	 * Helper method to construct where clause sql based on transaction
	 * reference id.
	 * 
	 * @param trxRefID parent transaction id
	 * @return String representation of transction id
	 */
	private String constructByTrxRefIDSQL(String trxRefID, String trxType, String[] excludeStatus) {
		StringBuffer buf = new StringBuffer();
		buf.append(" where ");
		buf.append(ICMSTrxTableConstants.TRXTBL_TRX_REFERENCE_ID);
		buf.append(" = '");
		buf.append(trxRefID);
		buf.append("' and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_TYPE);
		buf.append(" = '");
		buf.append(trxType);
		buf.append("'");

		if (excludeStatus != null) {
			for (int i = 0; i < excludeStatus.length; i++) {
				buf.append(" and ");
				buf.append(ICMSTrxTableConstants.TRXTBL_STATUS);
				buf.append(" <> '");
				buf.append(excludeStatus[i]);
				buf.append("'");
			}

		}
		return buf.toString();
	}

	/**
	 * Helper method to construct where clause of sql given transaction id.
	 * 
	 * @param trxID transaction id
	 * @return String representation of transaction id
	 */
	private String constructByTrxIDSQL(String trxID) {
		StringBuffer buf = new StringBuffer();
		buf.append(" where ");
		buf.append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID);
		buf.append(" = '");
		buf.append(trxID);
		buf.append("'");

		return buf.toString();
	}

	/**
	 * Helper method to construct sql for getting transaction value given a list
	 * of reference IDs.
	 * 
	 * @param cols a list of collaterals
	 * @return sql
	 */
	private String constructTrxValueByRefIDsSQL(ICollateral[] cols) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_TRX_VALUE);
		buf.append(" where ");
		buf.append(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID);
		buf.append(" in ");
		buf.append(getCollateralIDSQLList(cols));
		return buf.toString();
	}

	/**
	 * Helper method to convert collateral IDs into a sql friendly string ( in
	 * Clause).
	 * 
	 * @param cols a list of collaterals
	 * @return String in clause sql
	 */
	private String getCollateralIDSQLList(ICollateral[] cols) {
		if ((cols == null) || (cols.length == 0)) {
			return null;
		}

		StringBuffer buf = new StringBuffer();
		buf.append("(");

		for (int i = 0; i < cols.length; i++) {
			buf.append(cols[i].getCollateralID());
			if (i != cols.length - 1) {
				buf.append(",");
			}
			else {
				buf.append(")");
			}
		}
		return buf.toString();
	}

	public Map retrieveTrxStatusByRefIds(Long[] cmsCollateralIds) {
		if (cmsCollateralIds == null || cmsCollateralIds.length == 0) {
			return Collections.EMPTY_MAP;
		}

		List argList = new ArrayList();

		StringBuffer buf = new StringBuffer();
		buf.append("SELECT trx.from_state, sec.collateral_status col_status, sec.to_be_discharged_ind, ");
		buf.append("trx.status, trx.reference_id, m.is_stp_ready, stp_trx.status stp_status ");
		buf.append("FROM cms_security sec, transaction trx LEFT OUTER JOIN cms_stp_ready_status_map m ");
		buf.append("ON trx.transaction_id = m.transaction_id ");
		buf.append("LEFT OUTER JOIN stp_master_trans stp_trx ");
		buf.append("ON trx.transaction_id = stp_trx.transaction_id ");
		buf.append("WHERE trx.reference_id = sec.cms_collateral_id ");
		buf.append("AND sec.cms_collateral_id ");

		CommonUtil.buildSQLInList(cmsCollateralIds, buf, argList);

		buf.append(" AND trx.transaction_type = ?");
		argList.add(ICMSConstant.INSTANCE_COLLATERAL);

		return (Map) getJdbcTemplate().query(buf.toString(), argList.toArray(), new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map collateralIdStatusMap = new HashMap();
				while (rs.next()) {
					StpTrxStatusReadyIndicator stpTrxStatusReadyInd = new StpTrxStatusReadyIndicator();

					String status = rs.getString("status");
					String collateralStatus = rs.getString("col_status");
					String stpStatus = rs.getString("stp_status");
					String collateralToBeDischargedInd = rs.getString("to_be_discharged_ind");

					if (ICMSConstant.TRUE_VALUE.equals(collateralToBeDischargedInd)
							&& ICMSConstant.STATE_ACTIVE.equals(status)) {
						stpTrxStatusReadyInd.setTrxStatus("TO_BE_DISCHARGED");
					}
					else if (!IStpConstants.MASTER_TRX_COMPLETE.equals(stpStatus)
							&& ICMSConstant.STATE_ACTIVE.equals(status)) {
						stpTrxStatusReadyInd.setTrxStatus(ICMSConstant.STATE_NEW);
					}
					else {
						if (COLLATERAL_STATUS_RELEASED.equals(collateralStatus)
								&& ICMSConstant.STATE_ACTIVE.equals(status)) {
							stpTrxStatusReadyInd.setTrxStatus("RELEASED");
						}
						else {
							stpTrxStatusReadyInd.setTrxStatus(status);
						}
					}

					stpTrxStatusReadyInd.setOriginalTrxStatus(rs.getString("status"));
					stpTrxStatusReadyInd.setStpReadyIndicator((ICMSConstant.TRUE_VALUE.equals(rs
							.getString("is_stp_ready"))));

					collateralIdStatusMap.put(new Long(rs.getLong("reference_id")), stpTrxStatusReadyInd);
				}

				return collateralIdStatusMap;
			}
		});
	}
}
