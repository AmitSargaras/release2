/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/profile/ProfileTrxDAO.java,v 1.3 2006/09/26 02:52:00 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.profile;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.bus.profile.ProfileDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * @author $Author: hmbao $
 * @version $Revision: 1.3 $
 * @since $Date: 2006/09/26 02:52:00 $ Tag: $Name: $
 */
public class ProfileTrxDAO {
	private DBUtil dbUtil;

	private static String SELECT_PROFILE_TRANSACTION = null;

	static {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT ").append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID).append(",");
		buf.append(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID);
		buf.append(" from ").append(ICMSTrxTableConstants.TRX_TBL_NAME);
		buf.append(" where ").append(ICMSTrxTableConstants.TRXTBL_TRANSACTION_TYPE);
		buf.append(" = '").append(ICMSConstant.INSTANCE_COMMODITY_MAIN_PROFILE).append("' and (");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS).append(" != '").append(ICMSConstant.STATE_CLOSED).append(
				"' and ");
		buf.append(ICMSTrxTableConstants.TRXTBL_STATUS).append(" != '").append(ICMSConstant.STATE_DELETED).append("')");

		SELECT_PROFILE_TRANSACTION = buf.toString();
	}

	// public IProfileTrxValue getProfileTrxValue(IProfile[] profiles, boolean
	// isStaging)
	// throws SearchDAOException
	// {
	// if (profiles == null || profiles.length == 0) return new
	// OBProfileTrxValue();
	// return getProfileTrxValue(isStaging);
	// }

	public IProfileTrxValue getProfileTrxValue(boolean isStaging, String category) throws SearchDAOException {

		String sql = constructSQL(isStaging, category);
		try {
			dbUtil = new DBUtil();
			DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			return processResulSet(rs);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting commodity profile trx value ", e);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (Exception e) {
				throw new SearchDAOException("Error in cleaning up DB resources.", e);
			}
		}
	}

	private String constructSQL(boolean isStaging, String category) {

		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_PROFILE_TRANSACTION);
		buf.append(" and ");
		if (isStaging) {
			buf.append(ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID);
		}
		else {
			buf.append(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID);
		}
		buf.append(" = (");
		buf.append(new ProfileDAO().constructGetGroupIDSQL(isStaging, category));
		buf.append(")");

		return buf.toString();
	}

	private IProfileTrxValue processResulSet(ResultSet rs) throws SQLException {

		OBProfileTrxValue trxVal = new OBProfileTrxValue();
		if (rs.next()) {
			trxVal.setTransactionID(String.valueOf(rs.getLong(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID)));
			trxVal.setStatus(rs.getString(ICMSTrxTableConstants.TRXTBL_STATUS));

			BigDecimal refID = rs.getBigDecimal(ICMSTrxTableConstants.TRXTBL_REFERENCE_ID);
			if (refID != null) {
				trxVal.setReferenceID(String.valueOf(refID.longValue()));
			}

			BigDecimal stageRefID = rs.getBigDecimal(ICMSTrxTableConstants.TRXTBL_STAGING_REFERENCE_ID);
			if (stageRefID != null) {
				trxVal.setStagingReferenceID(String.valueOf(stageRefID.longValue()));
			}
		}
		return trxVal;
	}
}
