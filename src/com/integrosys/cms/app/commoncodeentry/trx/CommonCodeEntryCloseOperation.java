/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntryCloseOperation.java
 *
 * Created on February 7, 2007, 4:50:12 PM
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
 * Created by IntelliJ IDEA. User: Eric Date: Feb 7, 2007 Time: 4:50:12 PM To
 * change this template use File | Settings | File Templates.
 */
public class CommonCodeEntryCloseOperation extends AbstractCommonCodeEntriesTrxOperation {
	private static final long serialVersionUID = 1L;

	public String getOperationName() {
		return ICMSConstant.COMMON_CODE_ENTRY_CLOSE;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ICommonCodeEntriesTrxValue trxValue = super.getCommonCodeEntriesTrxValue(value);
		trxValue = restoreStageCommonCodeEntry(trxValue);
		trxValue = super.updateCommonCodeEntriesTrx(trxValue);
		return super.prepareResult(trxValue);
	}

}
