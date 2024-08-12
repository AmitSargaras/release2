/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/CollateralPoolDealNo.java,v 1.3 2004/07/05 14:55:31 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/05 14:55:31 $ Tag: $Name: $
 */
public class CollateralPoolDealNo extends AbstractDealNo {

	public CollateralPoolDealNo(String ctryCode) {
		super(ctryCode);
	}

	public String getDealType() {
		return ICMSConstant.DEAL_TYPE_COLLATERAL_POOL;
	}

	public String getNewSequenceNo() throws Exception {
		String newSeqNo = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_DEAL_NO_POOL, false);
		return newSeqNo;
	}
}
