/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/stockindex/MakerSubmitOperation.java,v 1.1 2003/08/18 10:13:16 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.stockindex;

//java

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to submit an stock index feed group It is the
 * same as the MakerUpdateOperation except that it transits to a different state
 * and routed to checker for approval, both of which is handled by transaction
 * manager As such, this class just returns a different operation name.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/18 10:13:16 $ Tag: $Name: $
 */
public class MakerSubmitOperation extends MakerUpdateOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerSubmitOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SUBMIT_STOCK_INDEX_FEED_GROUP;
	}

}