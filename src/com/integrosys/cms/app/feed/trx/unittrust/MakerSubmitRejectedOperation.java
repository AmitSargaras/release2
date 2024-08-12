/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/unittrust/MakerSubmitRejectedOperation.java,v 1.1 2003/09/12 08:30:33 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.unittrust;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/12 08:30:33 $ Tag: $Name: $
 */
public class MakerSubmitRejectedOperation extends MakerUpdateRejectedOperation {

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_UNIT_TRUST_FEED_GROUP;
	}
}
