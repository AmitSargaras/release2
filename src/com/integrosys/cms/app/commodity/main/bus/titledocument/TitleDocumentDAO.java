/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/titledocument/TitleDocumentDAO.java,v 1.1 2004/07/22 19:56:44 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.titledocument;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/07/22 19:56:44 $ Tag: $Name: $
 */
public class TitleDocumentDAO implements ITitleDocumentDAOConstant {
	private static String SELECT_MAX_GROUP_ID = null;

	static {
		StringBuffer buf = new StringBuffer();
		buf = new StringBuffer();
		buf.append("select max(").append(TITLEDOCTYPE_GROUP_ID).append(") as ").append(MAX_TITLEDOCTYPE_GROUP_ID)
				.append(" from ");

		SELECT_MAX_GROUP_ID = buf.toString();
	}

	/**
	 * Default Constructor
	 */
	public TitleDocumentDAO() {
	}

	public String constructGetMaxGroupIDSQL(boolean isStaging) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_MAX_GROUP_ID);
		if (isStaging) {
			buf.append(TITLEDOCTYPE_STAGE_TABLE);
		}
		else {
			buf.append(TITLEDOCTYPE_TABLE);
		}
		return buf.toString();
	}

}
