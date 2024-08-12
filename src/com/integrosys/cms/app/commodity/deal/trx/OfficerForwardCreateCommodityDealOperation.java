/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/OfficerForwardCreateCommodityDealOperation.java,v 1.1 2004/08/20 08:29:44 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxForwardOperation;

/**
 * This operation class is invoked by an officer to forward the create commodity
 * deal request submitted by a maker.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/08/20 08:29:44 $ Tag: $Name: $
 */
public class OfficerForwardCreateCommodityDealOperation extends CMSTrxForwardOperation {

	public String getOperationName() {
		return ICMSConstant.ACTION_OFFICER_FORWARD_CREATE_DEAL;
	}
}
