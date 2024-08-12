package com.integrosys.cms.app.propertyparameters.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.OBCommonMFTemplate;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Jan 31, 2007 Time: 5:21:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyParametersDAO {
	private DBUtil dbUtil = null;

	private static final String SELECT_PROPAR_TRX = "SELECT * from CMS_PROPERTY_PARAMETERS param, TRANSACTION trx WHERE param.PARAMETER_ID = trx.REFERENCE_ID AND trx.STATUS NOT IN ('CLOSED','DELETED') AND trx.TRANSACTION_TYPE = 'AUTO_VAL_PARAM'";

	private static final String SELECT_COUNT_DISALLOWED_TRX = "SELECT * from TRANSACTION trx WHERE trx.REFERENCE_ID = ? AND trx.STATUS IN ('REJECTED','PENDING_CREATE','PENDING_UPDATE','PENDING_DELETE')";

	private static MessageFormat SELECT_MF_TEMPLATE_BY_SEC_SUBTYPE = new MessageFormat(
			"SELECT T.MF_TEMPLATE_ID, T.MF_TEMPLATE_NAME " + "FROM CMS_MF_TEMPLATE T, CMS_MF_TEMPLATE_SEC_SUBTYPE S "
					+ "WHERE " + "T.MF_TEMPLATE_STATUS = \''" + ICMSConstant.MF_STATUS_ACTIVE + "\'' AND "
					+ "T.STATUS <> \''" + ICMSConstant.STATE_DELETED + "\'' AND " + "S.STATUS <> \''"
					+ ICMSConstant.STATE_DELETED + "\'' AND " + "T.MF_TEMPLATE_ID = S.MF_TEMPLATE_ID AND "
					+ "T.SECURITY_TYPE_ID = \''{0}\'' AND " + "S.SECURITY_SUB_TYPE_ID = \''{1}\'' ");

	/**
	 * Check if DDN the latest certificate generated for the limit profile.
	 * 
	 * @param limitProfileID limit profile id
	 * @return true if it is the latest cert generated, otherwise false
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on error
	 *         checking the ddn
	 */
	public List getAllProParameters() throws SearchDAOException {

		List prpPraList = new ArrayList();
		try {
			dbUtil = new DBUtil();

			dbUtil.setSQL(SELECT_PROPAR_TRX);

			ResultSet rs = dbUtil.executeQuery();

			while (rs.next()) {
				IPropertyParameters proParOB = new OBPropertyParameters();
				proParOB.setParameterId(rs.getLong("PARAMETER_ID"));
				if (!isEmpty(rs.getString("COLLATERAL_SUBTYPE"))) {
					proParOB.setCollateralSubType(rs.getString("COLLATERAL_SUBTYPE"));
				}
				if (!isEmpty(rs.getString("VALUATION_DESCRIPTION"))) {
					proParOB.setValuationDescription(rs.getString("VALUATION_DESCRIPTION"));
				}
				prpPraList.add(proParOB);
			}

			rs.close();

			return prpPraList;
		}
		catch (Exception e) {
			throw new SearchDAOException("Exception at getAllProParameters", e);
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				throw new SearchDAOException("SQLException in closing dbUtil at isDDNLatestCert", e);
			}
		}
	}

	public boolean allowAutoValParamTrx(String referenceId) throws SearchDAOException {
		boolean allow = true;
		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, "SELECT_COUNT_DISALLOWED_TRX sql : " + SELECT_COUNT_DISALLOWED_TRX);

			dbUtil.setSQL(SELECT_COUNT_DISALLOWED_TRX);
			dbUtil.setString(1, referenceId);

			ResultSet rs = dbUtil.executeQuery();

			if (rs.next()) {
				allow = false;
				DefaultLogger.debug(this, "count of records : " + rs.getRow());
				DefaultLogger.debug(this, "trx status : " + rs.getString("STATUS"));
			}
			rs.close();
			DefaultLogger.debug(this, "allow transaction ? : " + allow);

			return allow;
		}
		catch (Exception e) {
			throw new SearchDAOException("Exception at getAllProParameters", e);
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				throw new SearchDAOException("SQLException in closing dbUtil at isDDNLatestCert", e);
			}
		}
	}

	/**
	 * Gets the list of MF Template by security type code and security subtype
	 * code.
	 * 
	 * @param secTypeCode Security Type Code
	 * @param secSubTypeCode Security SubType Code
	 * @return array of ICommonMFTemplate
	 * @throws SearchDAOException on errors encountered
	 */
	public ICommonMFTemplate[] getMFTemplateBySecSubType(String secTypeCode, String secSubTypeCode)
			throws SearchDAOException {
		String sql = constructMFTemplateBySecSubTypeSQL(secTypeCode, secSubTypeCode);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processMFTemplateBySecSubTypeResultSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getMFTemplateBySecSubType: ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	protected String constructMFTemplateBySecSubTypeSQL(String secTypeCode, String secSubTypeCode) {
		String[] params = new String[] { secTypeCode, secSubTypeCode };
		String qryStr = SELECT_MF_TEMPLATE_BY_SEC_SUBTYPE.format(params);
		DefaultLogger.debug(this, "constructMFTemplateBySecSubTypeSQL=" + qryStr);
		return qryStr;
	}

	private ICommonMFTemplate[] processMFTemplateBySecSubTypeResultSet(ResultSet rs) throws SQLException {
		ArrayList arrList = new ArrayList();

		while (rs.next()) {
			ICommonMFTemplate com = new OBCommonMFTemplate();
			com.setMFTemplateID(rs.getLong("MF_TEMPLATE_ID"));
			com.setMFTemplateName(rs.getString("MF_TEMPLATE_NAME"));

			arrList.add(com);
		}
		return (ICommonMFTemplate[]) arrList.toArray(new ICommonMFTemplate[0]);
	}

	private boolean isEmpty(String aValue) {
		if ((aValue != null) && (aValue.trim().length() > 0)) {
			return false;
		}
		return true;
	}

	/**
	 * Helper method to clean up database resources.
	 * 
	 * @param dbUtil database utility object
	 * @throws SearchDAOException error in cleaning up DB resources
	 */
	private void finalize(DBUtil dbUtil) throws SearchDAOException {
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in cleaning up DB resources.");
		}
	}
}
