/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/sublimittype/SubLimitTypeSearchCriteria.java,v 1.1 2005/10/06 03:39:36 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.sublimittype;

import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-15
 * @Tag com.integrosys.cms.app.commodity.main.bus.sublimittype.
 *      SubLimitTypeSearchCriteria.java
 */
public class SubLimitTypeSearchCriteria extends CommodityMainInfoSearchCriteria {
	public SubLimitTypeSearchCriteria() {
		super(ICommodityMainInfo.INFO_TYPE_SUBLIMITTYPE);
	}
}
