/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/warehouse/WarehouseDAO.java,v 1.3 2004/07/22 14:33:40 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.warehouse;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/22 14:33:40 $ Tag: $Name: $
 */
public class WarehouseDAO implements IWarehouseDAOConstants {
	private static String SELECT_MAX_GROUP_ID = null;
	static {
		StringBuffer buf = new StringBuffer();
		buf = new StringBuffer();
		buf.append("select max(").append(WAREHOUSE_GROUP_ID).append(") as ").append(MAX_WAREHOUSE_GROUP_ID).append(
				" from ");

		SELECT_MAX_GROUP_ID = buf.toString();
	}

	/**
	 * Default Constructor
	 */
	public WarehouseDAO() {
	}

	public String constructGetGroupIDSQL(String country, boolean isStaging) {
		if ((country == null) || (country.length() == 0)) {
			return null;
		}

		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_MAX_GROUP_ID);
		if (isStaging) {
			buf.append(WAREHOUSE_STAGE_TABLE);
		}
		else {
			buf.append(WAREHOUSE_TABLE);
		}
		buf.append(" where ").append(WAREHOUSE_CTRY_CODE).append(" = '");
		buf.append(country).append("'");
		return buf.toString();
	}

}
