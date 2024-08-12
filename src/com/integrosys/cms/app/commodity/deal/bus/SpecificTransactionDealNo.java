/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/SpecificTransactionDealNo.java,v 1.3 2004/07/05 14:55:31 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/05 14:55:31 $ Tag: $Name: $
 */
public class SpecificTransactionDealNo extends AbstractDealNo {

	public SpecificTransactionDealNo(String ctryCode) {
		super(ctryCode);
	}

	public String getDealType() {
		return ICMSConstant.DEAL_TYPE_SPECIFIC_TRANSACTION;
	}

	public String getNewSequenceNo() throws Exception {
		String newSeqNo = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_DEAL_NO_SPECIFIC, false);
		return newSeqNo;
	}

	public static void main(String[] args) {
		try {
//			System.out.println("New Specific Deal No : " + (new SpecificTransactionDealNo("XX").getNewDealNo()));
//			System.out.println("New Pool Deal No : " + (new CollateralPoolDealNo("XX").getNewDealNo()));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
