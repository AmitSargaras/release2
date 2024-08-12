/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/FAMRejectCreateCommodityDealOperation.java,v 1.1 2004/08/20 08:29:44 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by a FAM officer to reject the create
 * commodity deal request submitted by a maker.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/08/20 08:29:44 $ Tag: $Name: $
 */
public class FAMRejectCreateCommodityDealOperation extends FAMConfirmRejectCreateCommodityDealOperation {

	public String getOperationName() {
		return ICMSConstant.ACTION_FAM_REJECT_CREATE_DEAL;
	}
}
