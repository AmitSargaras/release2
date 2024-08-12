/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntryRejectOperation.java
 *
 * Created on February 7, 2007, 3:53:42 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 7, 2007 Time: 3:53:42 PM To
 * change this template use File | Settings | File Templates.
 */
public class CommonCodeEntryRejectOperation extends AbstractCommonCodeEntriesTrxOperation {
	public String getOperationName() {
		return ICMSConstant.COMMON_CODE_ENTRY_REJECT;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ICommonCodeEntriesTrxValue trxValue = super.getCommonCodeEntriesTrxValue(value);
		trxValue = super.updateCommonCodeEntriesTrx(trxValue);
		return super.prepareResult(trxValue);
	}

}
