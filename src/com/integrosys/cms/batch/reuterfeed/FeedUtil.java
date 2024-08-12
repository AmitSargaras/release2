/*
 * Created on Jun 1, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.reuterfeed;

import java.util.Date;
import java.util.List;

import com.integrosys.base.techinfra.dbsupport.DBUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class FeedUtil {
	public static void setParams(List paramList, DBUtil dbUtil) throws Exception {
		for (int i = 0; i < paramList.size(); i++) {
			Object nextParamVal = paramList.get(i);
			if (nextParamVal instanceof String) {
				dbUtil.setString(i + 1, (String) nextParamVal);
			}
			else if (nextParamVal instanceof Integer) {
				dbUtil.setInt(i + 1, ((Integer) nextParamVal).intValue());
			}
			else if (nextParamVal instanceof Long) {
				dbUtil.setLong(i + 1, ((Long) nextParamVal).longValue());
			}
			else if (nextParamVal instanceof Float) {
				dbUtil.setFloat(i + 1, ((Float) nextParamVal).floatValue());
			}
			else if (nextParamVal instanceof Double) {
				dbUtil.setDouble(i + 1, ((Double) nextParamVal).doubleValue());
			}
			else if (nextParamVal instanceof Date) {
				Date d = (Date) nextParamVal;
				dbUtil.setDate(i + 1, new java.sql.Date(d.getTime()));
			}
			else {
				dbUtil.setObject(i + 1, nextParamVal);
			}
		}
	}

	public static boolean checkDataIsNull(Object data) {
		if (data == null) {
			return true;
		}
		String val = data.toString().trim();
		if ("".equals(val) || "NULL".equalsIgnoreCase(val)) {
			return true;
		}
		return false;
	}
}
