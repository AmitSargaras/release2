package com.integrosys.cms.app.customer.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Aug 12, 2003 Time: 2:29:09 PM
 * To change this template use Options | File Templates.
 */
public class GAMCustomerDAO extends CustomerDAO {

	protected String getDAPFilterSQL(String countryList, String orgList) {
		StringBuffer buf = new StringBuffer();
		buf.append(" AND (");
		buf.append(CUSTOMER_TABLE);
		buf.append(".");
		buf.append(CUSTOMER_ID);
		buf.append(" IN ( Select customerID from CUSTOMER_LOCATION_VIEW where ");
		buf.append("(CMS_ORIG_COUNTRY IN  ");
		buf.append(countryList);
		buf.append(" AND PROF_ORG IN ");
		buf.append(orgList);
		buf.append(") OR (LIMIT_COUNTRY in ");
		buf.append(countryList);
		buf.append(" AND PROF_ORG IN ");
		buf.append(orgList);
		buf.append(") OR SECURITY_LOCATION IN ");
		buf.append(countryList);
		buf.append(" ) OR LSP_DCL_AGREE_IND = '" + ICMSConstant.TRUE_VALUE + "') ");

		return buf.toString();
	}

	// todo implement this for GAM
}
