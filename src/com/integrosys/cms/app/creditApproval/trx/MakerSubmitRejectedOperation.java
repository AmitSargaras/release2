
package com.integrosys.cms.app.creditApproval.trx;

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
		return ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_CREDIT_APPROVAL;
	}
}
