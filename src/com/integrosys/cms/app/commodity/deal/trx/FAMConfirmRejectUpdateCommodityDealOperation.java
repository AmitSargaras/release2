/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/FAMConfirmRejectUpdateCommodityDealOperation.java,v 1.1 2004/09/02 07:35:26 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;

/**
 * This operation class is invoked by a FAM officer to confirm rejection of the
 * update commodity deal request submitted by a maker.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/09/02 07:35:26 $ Tag: $Name: $
 */
public class FAMConfirmRejectUpdateCommodityDealOperation extends FAMConfirmRejectCreateCommodityDealOperation {
	public String getOperationName() {
		return ICMSConstant.ACTION_FAM_CONFIRM_REJECT_UPDATE_DEAL;
	}

	protected CMSTrxOperation getOperation() {
		return new CheckerRejectUpdateCommodityDealOperation();
	}

	protected ICommodityDeal getDeal(ICommodityDealTrxValue value) {
		return value.getStagingCommodityDeal();
	}
}