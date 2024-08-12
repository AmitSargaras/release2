/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/OfficerApproveUpdateCommodityDealOperation.java,v 1.3 2004/11/03 10:41:03 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;

/**
 * This operation class is invoked by an officer to approve the update commodity
 * deal request submitted by a maker.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/11/03 10:41:03 $ Tag: $Name: $
 */
public class OfficerApproveUpdateCommodityDealOperation extends OfficerApproveCommodityDealOperation {

	public String getOperationName() {
		return ICMSConstant.ACTION_OFFICER_APPROVE_UPDATE_DEAL;
	}

	protected CMSTrxOperation getOperation() {
		return new CheckerApproveUpdateCommodityDealOperation();
	}

	protected ICommodityDeal getDeal(ICommodityDealTrxValue value) {
		return value.getStagingCommodityDeal();
	}
}
