/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/CCDocumentLocationDAO.java,v 1.9 2005/09/19 09:02:07 czhou Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//java
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * DAO for Template
 * @author $Author: czhou $
 * @version $Revision: 1.9 $
 * @since $Date: 2005/09/19 09:02:07 $ Tag: $Name: $
 */

public class CCDocumentLocationDAO implements ICCDocumentLocationDAO, ICMSTrxTableConstants {
	private DBUtil dbUtil = null;

	/*
	 * private static String SELECT_DOCUMENT_LOCATION =
	 * "SELECT CMS_CC_DOC_LOC.DOC_LOC_ID, CMS_CC_DOC_LOC.CMS_LSP_LMT_PROFILE_ID, "
	 * +
	 * "CMS_CC_DOC_LOC.CMS_LE_SUB_PROFILE_ID, CMS_CC_DOC_LOC.CMS_PLEDGOR_DTL_ID, "
	 * + "CMS_CC_DOC_LOC.SUB_CATEGORY, CMS_CC_DOC_LOC.DOC_ORIG_COUNTRY, " +
	 * "CMS_CC_DOC_LOC.DOC_ORIG_ORGANISATION, CMS_CC_DOC_LOC.REMARKS, TRANSACTION.TRANSACTION_ID, "
	 * + "TRANSACTION.STATUS FROM CMS_CC_DOC_LOC, TRANSACTION WHERE " +
	 * "TRANSACTION.TRANSACTION_TYPE = 'CC_DOC_LOC' AND TRANSACTION.STATUS <> 'CLOSED' AND "
	 * + "TRANSACTION.REFERENCE_ID = CMS_CC_DOC_LOC.DOC_LOC_ID";
	 * 
	 * private static String SELECT_DOCUMENT_LOCATION_COUNT =
	 * "SELECT COUNT(*)  FROM STAGE_CC_DOC_LOC, TRANSACTION WHERE " +
	 * "TRANSACTION.TRANSACTION_TYPE = 'CC_DOC_LOC' AND TRANSACTION.STATUS <> 'CLOSED' AND "
	 * + "TRANSACTION.STAGING_REFERENCE_ID = STAGE_CC_DOC_LOC.DOC_LOC_ID";
	 */

	private static String SELECT_DOCUMENT_LOCATION = null;

	private static String SELECT_DOCUMENT_LOCATION_COUNT = null;

	private static String SELECT_DOCUMENT_LOCATION_VIEWABLE = null;

	static {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT COUNT(*) ");
		strBuf.append(" FROM ");
		strBuf.append(STAGE_CC_DOC_LOCATION_TABLE);
		strBuf.append(", ");
		strBuf.append(TRX_TBL_NAME);
		strBuf.append(" WHERE ");
		strBuf.append(TRX_TBL_NAME + "." + TRXTBL_TRANSACTION_TYPE);
		strBuf.append(" = '");
		strBuf.append(ICMSConstant.INSTANCE_CC_DOC_LOC);
		strBuf.append("'");
		strBuf.append(" AND ");
		strBuf.append(TRX_TBL_NAME + "." + TRXTBL_STATUS);
		strBuf.append(" <> '");
		strBuf.append(ICMSConstant.STATE_CLOSED);
		strBuf.append("'");
		strBuf.append(" AND ");
		strBuf.append(TRX_TBL_NAME + "." + TRXTBL_STAGING_REFERENCE_ID);
		strBuf.append(" = ");
		strBuf.append(STAGE_DOCTBL_DOC_LOCATION_ID_PREF);
		SELECT_DOCUMENT_LOCATION_COUNT = strBuf.toString();

		/*
		 * SELECT CMS_CC_DOC_LOC.DOC_LOC_ID,
		 * CMS_CC_DOC_LOC.CMS_LSP_LMT_PROFILE_ID,
		 * CMS_CC_DOC_LOC.CMS_LE_SUB_PROFILE_ID,
		 * CMS_CC_DOC_LOC.CMS_PLEDGOR_DTL_ID, CMS_CC_DOC_LOC.SUB_CATEGORY,
		 * CMS_CC_DOC_LOC.DOC_ORIG_COUNTRY,
		 * CMS_CC_DOC_LOC.DOC_ORIG_ORGANISATION, CMS_CC_DOC_LOC.REMARKS,
		 * TRANSACTION.TRANSACTION_ID, TRANSACTION.STATUS FROM CMS_CC_DOC_LOC,
		 * TRANSACTION WHERE TRANSACTION.TRANSACTION_TYPE = 'CC_DOC_LOC' AND
		 * TRANSACTION.STATUS <> 'CLOSED' AND TRANSACTION.REFERENCE_ID =
		 * CMS_CC_DOC_LOC.DOC_LOC_ID AND CMS_CC_DOC_LOC.SUB_CATEGORY = 'PLEDGOR'
		 * AND CMS_CC_DOC_LOC.CMS_LSP_LMT_PROFILE_ID = 20031001303 AND
		 * CMS_CC_DOC_LOC.CMS_PLEDGOR_DTL_ID = 20031001103
		 */

		strBuf = new StringBuffer();
		strBuf.append("SELECT ");
		strBuf.append(DOCTBL_DOC_LOCATION_ID_PREF);
		strBuf.append(", ");
		strBuf.append(DOCTBL_LIMIT_PROFILE_ID_PREF);
		strBuf.append(", ");
		strBuf.append(DOCTBL_SUB_PROFILE_ID_PREF);
		strBuf.append(", ");
		strBuf.append(DOCTBL_PLEDGOR_ID_PREF);
		strBuf.append(", ");
		strBuf.append(DOCTBL_SUB_CATEGORY_PREF);
		strBuf.append(", ");
		strBuf.append(DOCTBL_ORIG_CTRY_PREF);
		strBuf.append(", ");
		strBuf.append(DOCTBL_ORIG_ORG_CODE_PREF);
		strBuf.append(", ");
		strBuf.append(DOCTBL_REMARKS_PREF);
		strBuf.append(", ");
		strBuf.append(TRX_TBL_NAME + "." + TRXTBL_TRANSACTION_ID);
		strBuf.append(", ");
		strBuf.append(TRX_TBL_NAME + "." + TRXTBL_STATUS);
		strBuf.append(" FROM ");
		strBuf.append(CC_DOC_LOCATION_TABLE);
		strBuf.append(", ");
		strBuf.append(TRX_TBL_NAME);
		strBuf.append(" ");
		strBuf.append("WHERE ");
		strBuf.append(TRX_TBL_NAME + "." + TRXTBL_TRANSACTION_TYPE);
		strBuf.append(" = '");
		strBuf.append(ICMSConstant.INSTANCE_CC_DOC_LOC);
		strBuf.append("' AND ");
		strBuf.append(TRX_TBL_NAME + "." + TRXTBL_STATUS);
		strBuf.append(" <> '");
		strBuf.append(ICMSConstant.STATE_CLOSED);
		strBuf.append("' AND ");
		strBuf.append(TRX_TBL_NAME + "." + TRXTBL_REFERENCE_ID);
		strBuf.append(" = ");
		strBuf.append(DOCTBL_DOC_LOCATION_ID_PREF);
		SELECT_DOCUMENT_LOCATION = strBuf.toString();

		strBuf = new StringBuffer();
		strBuf.append("SELECT ");
		strBuf.append(DOCTBL_PLEDGOR_ID_PREF);
		strBuf.append(", ");
		strBuf.append(DOCTBL_SUB_PROFILE_ID_PREF);
		strBuf.append(", ");
		strBuf.append(DOCTBL_SUB_CATEGORY_PREF);
		strBuf.append(" FROM ");
		strBuf.append(CC_DOC_LOCATION_TABLE);
		strBuf.append(", ");
		strBuf.append(TRX_TBL_NAME);
		strBuf.append(" ");
		strBuf.append("WHERE ");
		strBuf.append(TRX_TBL_NAME + "." + TRXTBL_TRANSACTION_TYPE);
		strBuf.append(" = '");
		strBuf.append(ICMSConstant.INSTANCE_CC_DOC_LOC);
		strBuf.append("' AND ");
		strBuf.append(TRX_TBL_NAME + "." + TRXTBL_STATUS);
		strBuf.append(" <> '");
		strBuf.append(ICMSConstant.STATE_CLOSED);
		strBuf.append("' AND ");
		strBuf.append(TRX_TBL_NAME + "." + TRXTBL_REFERENCE_ID);
		strBuf.append(" = ");
		strBuf.append(DOCTBL_DOC_LOCATION_ID_PREF);
		SELECT_DOCUMENT_LOCATION_VIEWABLE = strBuf.toString();
	}

	/**
	 * To get the number of cc document location that satisfy the criteria
	 * @param aCriteria of CCDocumentLocationSearchCriteria type
	 * @return int - the number of CC document location that satisfy the
	 *         criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria) throws SearchDAOException {
		StringBuffer sb = new StringBuffer(SELECT_DOCUMENT_LOCATION_COUNT);

		try {
			ArrayList params = new ArrayList();
			String condition = buildCCDocLocationSearchCriteria(aCriteria, params);
			String sql = null;

			if (condition != null) {
				sb.append(condition);
				sql = sb.toString();
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
			throw new SearchDAOException("SQLException in getNoOfCCDocumentLocation", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getNoOfCCDocumentLocation", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getNoOfCCTask", ex);
			}
		}
	}

	/**
	 * Get the list of cc document location based on the criteria
	 * @param anOwnerType of String type
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCDocumentLocation[] - the list of cc document location
	 * @throws SearchDAOException on error
	 */
	public ICCDocumentLocation[] getCCDocumentLocation(String anOwnerType, long aLimitProfileID, long anOwnerID)
			throws SearchDAOException {
		try {
			ArrayList params = new ArrayList();
			String condition = buildCCDocLocationSearchCriteria(anOwnerType, aLimitProfileID, anOwnerID, params);

			String sql = SELECT_DOCUMENT_LOCATION;

			if (condition != null) {
				sql += condition;
			}

			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList locationList = new ArrayList();
			ICCDocumentLocation ccLocation = null;
			IBookingLocation location = null;
			while (rs.next()) {
				ccLocation = new OBCCDocumentLocation();
				ccLocation.setDocLocationID(rs.getLong(DOCTBL_DOC_LOCATION_ID));
				ccLocation.setDocLocationCategory(rs.getString(DOCTBL_SUB_CATEGORY));
				ccLocation.setLimitProfileID(rs.getLong(DOCTBL_LIMIT_PROFILE_ID));
				if (ICMSConstant.CHECKLIST_PLEDGER.equals(ccLocation.getDocLocationCategory())) {
					ccLocation.setCustomerID(rs.getLong(DOCTBL_PLEDGOR_ID));
				}
				else {
					ccLocation.setCustomerID(rs.getLong(DOCTBL_SUB_PROFILE_ID));
				}
				location = new OBBookingLocation();
				location.setCountryCode(rs.getString(DOCTBL_ORIG_CTRY));
				location.setOrganisationCode(rs.getString(DOCTBL_ORIG_ORG_CODE));
				ccLocation.setOriginatingLocation(location);
				locationList.add(ccLocation);
			}
			return (ICCDocumentLocation[]) locationList.toArray(new ICCDocumentLocation[0]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCCDocumentLocation", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCCDocumentLocation", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCCDocumentLocation", ex);
			}
		}
	}

	/**
	 * Get the list of cc document location based on the list of pledgor ID and
	 * sub profile ID
	 * @param limitProfileID of long type
	 * @param pledgorIDList of List
	 * @param subProfileIDList of List
	 * @return List - the List of id (either pledgor id or sub profile id concat
	 *         with doc type)
	 * @throws SearchDAOException on error
	 */
	public List getIsViewableStatus(long limitProfileID, List pledgorIDList, List subProfileIDList)
			throws SearchDAOException {
		try {
			String sql = SELECT_DOCUMENT_LOCATION_VIEWABLE;
			ArrayList params = new ArrayList();

			StringBuffer strBuf = new StringBuffer();
			if (limitProfileID != ICMSConstant.LONG_MIN_VALUE) {
				strBuf.append(" AND ");
				strBuf.append(DOCTBL_LIMIT_PROFILE_ID_PREF);
				strBuf.append(" = ?");
				params.add(new Long(limitProfileID));
				boolean hasPledgor = false;
				boolean hasCoBorrower = false;
				if ((pledgorIDList != null) && (pledgorIDList.size() > 0)) {
					hasPledgor = true;
					strBuf.append(" AND ");
					strBuf.append(" (( ");
					strBuf.append(DOCTBL_SUB_CATEGORY_PREF);
					strBuf.append(" = ");
					strBuf.append(" '");
					strBuf.append(ICMSConstant.CHECKLIST_PLEDGER);
					strBuf.append("' ");
					strBuf.append(" AND ");
					strBuf.append(DOCTBL_PLEDGOR_ID_PREF);
					CommonUtil.buildSQLInList(pledgorIDList, strBuf, params);
					strBuf.append(" ) ");
				}
				if ((subProfileIDList != null) && (subProfileIDList.size() > 0)) {
					hasCoBorrower = true;
					if (hasPledgor) {
						strBuf.append(" OR ");
					}
					else {
						strBuf.append(" AND ");
						strBuf.append(" ( ");
					}
					strBuf.append(" ( ");
					strBuf.append(DOCTBL_SUB_CATEGORY_PREF);
					strBuf.append(" = ");
					strBuf.append("' ");
					strBuf.append(ICMSConstant.CHECKLIST_CO_BORROWER);
					strBuf.append("' ");
					strBuf.append(" AND ");
					strBuf.append(DOCTBL_SUB_PROFILE_ID_PREF);
					CommonUtil.buildSQLInList(subProfileIDList, strBuf, params);
					strBuf.append(" ) ");
				}
				if (hasPledgor || hasCoBorrower) {
					strBuf.append(" ) ");
				}
			}
			else {
				strBuf.append(" AND ");
				strBuf.append(DOCTBL_SUB_PROFILE_ID_PREF);
				CommonUtil.buildSQLInList(subProfileIDList, strBuf, params);
			}

			sql += strBuf.toString();

			dbUtil = new DBUtil();
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);
			ResultSet rs = dbUtil.executeQuery();
			List viewableList = new ArrayList();
			while (rs.next()) {
				String docType = rs.getString(DOCTBL_SUB_CATEGORY);
				DefaultLogger.debug(this, "<<<<<<< docType: " + docType + "\tpledgor id: "
						+ rs.getLong(DOCTBL_PLEDGOR_ID) + "\t sub profile id: " + rs.getLong(DOCTBL_SUB_PROFILE_ID));
				if (ICMSConstant.CHECKLIST_PLEDGER.equals(docType)) {
					viewableList.add(rs.getString(DOCTBL_PLEDGOR_ID) + docType);
				}
				else {
					viewableList.add(rs.getString(DOCTBL_SUB_PROFILE_ID) + docType);
				}
			}
			return viewableList;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getIsViewableStatus", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getIsViewableStatus", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getIsViewableStatus", ex);
			}
		}
	}

	/**
	 * Constructs the SQL string based on the search criteria Populates the
	 * parameter list for DBUtil to perform JDBC variables binding For CC
	 * Document Location Table
	 * @param anOwnerType
	 * @param aLimitProfileID
	 * @param anOwnerID
	 * @return String - constructed SQL filter
	 */
	private String buildCCDocLocationSearchCriteria(String anOwnerType, long aLimitProfileID, long anOwnerID,
			ArrayList params) {

		StringBuffer strBuf = new StringBuffer();
		strBuf.append(" AND ");
		strBuf.append(DOCTBL_SUB_CATEGORY_PREF);
		strBuf.append(" = ?");
		params.add(anOwnerType);

		if (aLimitProfileID != ICMSConstant.LONG_MIN_VALUE) {
			strBuf.append(" AND ");
			strBuf.append(DOCTBL_LIMIT_PROFILE_ID_PREF);
			strBuf.append(" = ?");
			params.add(new Long(aLimitProfileID));
		}

		if (ICMSConstant.CHECKLIST_PLEDGER.equals(anOwnerType)) {
			strBuf.append(" AND ");
			strBuf.append(DOCTBL_PLEDGOR_ID_PREF);
			strBuf.append(" = ?");
			params.add(new Long(anOwnerID));
		}
		else {
			strBuf.append(" AND ");
			strBuf.append(DOCTBL_SUB_PROFILE_ID_PREF);
			strBuf.append(" = ?");
			params.add(new Long(anOwnerID));

		}
		return strBuf.toString();
	}

	private String buildCCDocLocationSearchCriteria(CCDocumentLocationSearchCriteria aCriteria, ArrayList params) {
		StringBuffer buf = new StringBuffer();

		if (aCriteria == null) {
			return null;
		}

		if (aCriteria.getLimitProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			buf.append(" AND ");
			buf.append(STAGE_DOCTBL_LIMIT_PROFILE_ID_PREF);
			buf.append(" = ?");
			params.add(new Long(aCriteria.getLimitProfileID()));
		}

		if (!isEmpty(aCriteria.getDocLocationCategory())) {
			buf.append(" AND ");
			buf.append(STAGE_DOCTBL_SUB_CATEGORY_PREF);
			buf.append(" = ?");
			params.add(aCriteria.getDocLocationCategory());

			if (aCriteria.getCustomerID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				buf.append(" AND ");
				if (ICMSConstant.CHECKLIST_PLEDGER.equals(aCriteria.getDocLocationCategory())) {
					buf.append(STAGE_DOCTBL_PLEDGOR_ID_PREF);
				}
				else {
					buf.append(STAGE_DOCTBL_SUB_PROFILE_ID_PREF);
				}
				buf.append(" = ?");
				params.add(new Long(aCriteria.getCustomerID()));

			}
		}

		if (!isEmpty(aCriteria.getDocLocationCountry())) {
			buf.append(" AND ");
			buf.append(STAGE_DOCTBL_ORIG_CTRY_PREF);
			buf.append(" = ?");
			params.add(aCriteria.getDocLocationCountry());
		}

		if (!isEmpty(aCriteria.getDocLocationOrgCode())) {
			buf.append(" AND ");
			buf.append(STAGE_DOCTBL_ORIG_ORG_CODE_PREF);
			buf.append(" = ?");
			params.add(aCriteria.getDocLocationOrgCode());
		}

		/**
		 * construct a dynamic IN SQL list for binding
		 */
		String[] trxStatus = aCriteria.getTrxStatusList();
		if (!CommonUtil.isEmptyArray(trxStatus)) {
			buf.append(" AND ");
			buf.append(TRX_TBL_NAME + "." + TRXTBL_STATUS);
			CommonUtil.buildSQLInList(trxStatus, buf, params);
		}
		return buf.toString();
	}

	/**
	 * Utilty method to check if a string value is null or empty
	 * @param aValue - String
	 * @return boolean - true if empty and false otherwise
	 */
	private boolean isEmpty(String aValue) {
		if ((aValue != null) && (aValue.trim().length() > 0)) {
			return false;
		}
		return true;
	}

}
