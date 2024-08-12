/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/common/CollateralUtil.java,v 1.1 2003/09/08 05:02:11 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.common;

import java.sql.ResultSet;
import java.util.HashMap;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

public class CollateralUtil {

	public static final String SUB_TYPE_NAME = "subTypeName";

	public static final String TYPE_NAME = "typeName";

	public static HashMap getSubtypeAndTypeName(String collateralSubtypeID) {
		HashMap hm;
		ResultSet rs;

		try {
			DBUtil dbUtil = new DBUtil();
			StringBuffer str = new StringBuffer();
			str.append("SELECT subtype_name, security_type_name FROM CMS_SECURITY_SUB_TYPE ").append(
					"WHERE security_sub_type_id = ? ");

			dbUtil.setSQL(str.toString());
			dbUtil.setString(1, collateralSubtypeID);

			rs = dbUtil.executeQuery();
			hm = new HashMap();
			hm.put(SUB_TYPE_NAME, "");
			hm.put(TYPE_NAME, "");
			while (rs.next()) {
				hm = new HashMap();
				hm.put(SUB_TYPE_NAME, rs.getString("SUBTYPE_NAME"));
				hm.put(TYPE_NAME, rs.getString("SECURITY_TYPE_NAME"));
			}

			dbUtil.close();

			return hm;
		}
		catch (Exception e) {
			DefaultLogger.error("CollateralUtil", "Unable to get security subtype and type name", e);
		}

		return new HashMap();
	}
}
