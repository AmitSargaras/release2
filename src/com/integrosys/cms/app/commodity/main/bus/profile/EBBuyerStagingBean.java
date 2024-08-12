/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/EBBuyerStagingBean.java,v 1.3 2004/08/12 03:14:49 wltan Exp $
 */

package com.integrosys.cms.app.commodity.main.bus.profile;

import com.integrosys.cms.app.commodity.CommodityConstant;
import com.integrosys.cms.app.commodity.main.CommodityException;

/**
 * @author dayanand $
 * @version $
 * @since $Date: 2004/08/12 03:14:49 $ Tag: $Name: $
 */

public abstract class EBBuyerStagingBean extends EBBuyerBean {
	protected long generateBuyerPK() throws CommodityException {
		return generatePK(CommodityConstant.SEQUENCE_PROFILE_BUYER_STAGING_SEQ);
	}

}