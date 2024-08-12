/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/sublimittype/SubLimitTypeDAO.java,v 1.1 2005/10/06 03:39:36 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.sublimittype;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-21
 * @Tag 
 *      com.integrosys.cms.app.commodity.main.bus.sublimittype.SubLimitTypeDAO.java
 */
public class SubLimitTypeDAO implements ISubLimitTypeDAOConstants {
	private static String SELECT_MAX_GROUP_ID = null;
	static {
		StringBuffer buf = new StringBuffer();
		buf = new StringBuffer();
		buf.append("select max(").append(SLT_GROUP_ID).append(") as ").append(MAX_SLT_GROUP_ID).append(" from ");

		SELECT_MAX_GROUP_ID = buf.toString();
	}

	public String constructGetGroupIDSQL(boolean isStaging) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_MAX_GROUP_ID);
		if (isStaging) {
			buf.append(SLT_STAGE_TABLE);
		}
		else {
			buf.append(SLT_TABLE);
		}
		return buf.toString();
	}
}
