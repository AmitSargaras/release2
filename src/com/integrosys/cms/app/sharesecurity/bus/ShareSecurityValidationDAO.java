package com.integrosys.cms.app.sharesecurity.bus;

import java.sql.ResultSet;
import java.util.Vector;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class ShareSecurityValidationDAO {

	public Vector getSharedSecurityValidationResult(OBShareSecurityValidation obShareSecurityValidation)
			throws ShareSecurityValidationException {
		Vector result = new Vector();
		boolean searchWithPledgorInfo = false;

		searchWithPledgorInfo = checkPledgorSearchCriteria(obShareSecurityValidation);
		// Mandatory fields all in
		String query = "SELECT SEC.CMS_COLLATERAL_ID, SEC.SCI_SECURITY_TYPE_VALUE, SEC.SCI_SECURITY_SUBTYPE_VALUE,SEC.SECURITY_LOCATION, "
				+ "PROP.STATE, PROP.DISTRICT, PROP.MUKIM, PROP.TITLE_NUMBER, PROP.TITLE_TYPE";

		// if (searchWithPledgorInfo){
		query += ", PLDG.PLG_LEGAL_NAME,PLDG.PLG_INC_NUM_TEXT,PLDG.PLG_INC_CNTRY_ISO_CODE ";
		// }

		query += " FROM CMS_SECURITY SEC INNER JOIN CMS_PROPERTY PROP ON " + // Security
																				// Table
				"SEC.CMS_COLLATERAL_ID = PROP.CMS_COLLATERAL_ID ";

		// if (searchWithPledgorInfo){
		query += "INNER JOIN SCI_SEC_PLDGR_MAP PMAP ON " + // Pledgor Map Table
				"SEC.CMS_COLLATERAL_ID = PMAP.CMS_COLLATERAL_ID " + "INNER JOIN SCI_PLEDGOR_DTL PLDG ON " + // Pledgor
																											// table
				"PMAP.CMS_PLEDGOR_DTL_ID = PLDG.CMS_PLEDGOR_DTL_ID ";
		// }

		query += "WHERE SEC.SCI_SECURITY_TYPE_VALUE = ? " + // Security Type
				"AND SEC.SCI_SECURITY_SUBTYPE_VALUE = ? " + // Security Sub Type
				"AND SEC.SECURITY_LOCATION  = ? " + // Country
				"AND PROP.STATE  = ? " + // State
				"AND PROP.DISTRICT  = ? " + // District
				"AND PROP.MUKIM  = ? " + // Mukim
				"AND PROP.TITLE_TYPE  = ? " + // Title Type
				"AND PROP.TITLE_NUMBER LIKE '" + obShareSecurityValidation.getTitleNumber() + "%' "; // Title
																										// Number

		if (searchWithPledgorInfo) {

			// Optional fields
			query += "AND PROP.PLG_LEGAL_NAME LIKE '%" + obShareSecurityValidation.getProviderName() + "%' " + // Provider
																												// Name
					"AND PROP.PLG_INC_NUM_TEXT = " + obShareSecurityValidation.getIdNumber(); // Id
																								// Number

			if ((stringNotEmpty(obShareSecurityValidation.getIdCountry()))
					|| stringNotEmpty(obShareSecurityValidation.getIdNumber())) {
				query += "AND PROP.PLG_INC_CNTRY_ISO_CODE LIKE '" + obShareSecurityValidation.getIdCountry() + "% "; // Id
																														// Country
			}
		}

		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, "Query String SSV " + query);
			dbUtil.setSQL(query);
			dbUtil.setString(1, obShareSecurityValidation.getSecurityType());
			dbUtil.setString(2, obShareSecurityValidation.getSecuritySubType());
			dbUtil.setString(3, obShareSecurityValidation.getCountry());
			dbUtil.setString(4, obShareSecurityValidation.getState());
			dbUtil.setString(5, obShareSecurityValidation.getDistrict());
			dbUtil.setString(6, obShareSecurityValidation.getMukim());
			// dbUtil.setString(7, obShareSecurityValidation.getTitleNumber());
			dbUtil.setString(7, obShareSecurityValidation.getTitleType());
			rs = dbUtil.executeQuery();

			while (rs.next()) {
				OBShareSecurityValidation obResult = new OBShareSecurityValidation();
				obResult.setSecurityId(rs.getLong("CMS_COLLATERAL_ID"));
				obResult.setSecurityType(rs.getString("SCI_SECURITY_TYPE_VALUE"));
				obResult.setSecuritySubType(rs.getString("SCI_SECURITY_SUBTYPE_VALUE"));
				obResult.setCountry(rs.getString("SECURITY_LOCATION"));
				obResult.setState(rs.getString("STATE"));
				obResult.setDistrict(rs.getString("DISTRICT"));
				obResult.setMukim(rs.getString("MUKIM"));
				obResult.setTitleNumber(rs.getString("TITLE_NUMBER"));
				obResult.setTitleType(rs.getString("TITLE_TYPE"));
				obResult.setProviderName(rs.getString("PLG_LEGAL_NAME"));
				obResult.setIdNumber(rs.getString("PLG_INC_NUM_TEXT"));
				obResult.setIdCountry(rs.getString("PLG_INC_CNTRY_ISO_CODE"));
				// Pledgor information
				result.add(obResult);
			}

			return result;
		}
		catch (Exception ex) {
			DefaultLogger.debug(this, "Exception in getSharedSecurityValidationResult");
			ex.printStackTrace();
			throw new ShareSecurityValidationException(ex);
		}
		finally {
			finalize(dbUtil, rs);
		}
	}

	private void finalize(DBUtil util, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (Exception ex) {
			}
		}
		if (util != null) {
			try {
				util.close();
			}
			catch (Exception ex) {
			}
		}
	}

	private boolean checkPledgorSearchCriteria(OBShareSecurityValidation obShareSecurityValidation) {
		if (stringNotEmpty(obShareSecurityValidation.getProviderName())
				|| stringNotEmpty(obShareSecurityValidation.getIdNumber())
				|| stringNotEmpty(obShareSecurityValidation.getIdCountry())) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean stringNotEmpty(String value) {
		if ((value != null) && !("").equals(value.trim())) {
			return true;
		}
		return false;
	}
}
