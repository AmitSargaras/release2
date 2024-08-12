/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus.group;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.custrelationship.bus.CustRelationshipException;

/**
 * Group Exposure DAO
 * @author skchai
 * 
 */
public class GroupExposureDAO implements IGroupExposureDAO {

	private static final String SELECT_CUSTOMERID_RELATIONSHIP_BY_PARENT_ID_SQL = 
		    " SELECT CMS_LE_SUB_PROFILE_ID \n" +
			" FROM SCI_LE_REL \n" +
			" WHERE PARENT_SUB_PROFILE_ID = ? \n" + 
			" AND STATUS = '" + ICMSConstant.ACTIVE + "' \n" +
			" AND ( ( REL_VALUE = '" + ICMSConstant.RELATIONSHIP_SHAREHOLDER_ENTRY_CODE + "' AND PERCENT_OWN >= 20 ) \n" +
			" OR REL_VALUE = '" + ICMSConstant.RELATIONSHIP_DIRECTOR_ENTRY_CODE + "' \n" +
			" OR REL_VALUE = '" + ICMSConstant.RELATIONSHIP_KEY_MANAGEMENT_ENTRY_CODE + "' ) \n";

	/**
	 * Get customer ids that belongs to the parent sub profile ids
	 * in terms of share holder, directors or key management
	 * @param parentSubProfileId
	 * @return list of customer id
	 */
	public List getCustomerRelationshipIds(long parentSubProfileId) {

		ResultSet rs = null;
		DBUtil dbUtil = null;
		List customerIdList = null;

		StringBuffer querySQL = new StringBuffer();
		SQLParameter params = SQLParameter.getInstance();
		querySQL.append(SELECT_CUSTOMERID_RELATIONSHIP_BY_PARENT_ID_SQL);

		log(querySQL.toString());
		params.addLong(new Long(parentSubProfileId));

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(querySQL.toString());
			CommonUtil.setSQLParams(params, dbUtil);
			rs = dbUtil.executeQuery();
			customerIdList = this.processCustomerRelationshipIds(rs);
		} catch (Exception e) {
			DefaultLogger.error(this, "", e);
		} finally {
			finalize(dbUtil, rs);
		}

		return customerIdList;
	}

	/**
	 * Capture the customer ids that relate to the parent sub profile id
	 * @param rs resultset
	 * @return list of captured customer id
	 * @see com.integrosys.cms.app.custexposure.bus.group.GroupExposureDAO#getCustomerRelationshipIds
	 */
	private List processCustomerRelationshipIds(ResultSet rs) {

		List list = new ArrayList();

		try {
			while (rs.next()) {
				list.add(new Long(rs.getLong("cms_le_sub_profile_id")));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		log("processCustomerIds list size :  " + list.size());
		return list;
	}

	/**
	 * Helper method to clean up database resources.
	 * 
	 * @param dbUtil
	 *            database utility object
	 */
	private static void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (Exception e1) {
		}

		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		} catch (Exception e2) {
		}
	}


	private void log(String str) {
		DefaultLogger.debug(this, str);
	}
}
