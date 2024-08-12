/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntryUpdateOperation.java
 *
 * Created on February 7, 2007, 9:18:57 AM
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
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntries;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 7, 2007 Time: 9:18:57 AM To
 * change this template use File | Settings | File Templates.
 */
public class CommonCodeEntryUpdateOperation extends AbstractCommonCodeEntriesTrxOperation {
	public String getOperationName() {
		return ICMSConstant.COMMON_CODE_ENTRY_UPDATE;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		DefaultLogger.debug(this, "In performProcess");
		ICommonCodeEntriesTrxValue trxValue = super.getCommonCodeEntriesTrxValue(anITrxValue);

		if (trxValue.getTransactionID().equals(String.valueOf(ICMSConstant.LONG_INVALID_VALUE))) {
			// means this must be new for the whole common code module,

			DefaultLogger.debug(this, "Possible new category found and new entries are being inserted");
			DefaultLogger.debug(this, "Creating new initial record in transaction table for new entry");
			DefaultLogger.debug(this, "trxValue.getTransactionID () : " + trxValue.getTransactionID());
			DefaultLogger.debug(this, "trxValue.getReferenceID () : " + trxValue.getReferenceID());

			ICommonCodeEntries stageData = trxValue.getStagingCommonCodeEntries();

			// trxValue.setFromState ( );
			trxValue = super.getCommonCodeEntriesTrxValue(super.createTransaction(trxValue));
			trxValue.setStagingCommonCodeEntries(stageData);

			DefaultLogger.debug(this, "Record in transaction table created");
			DefaultLogger.debug(this, "trxValue.getTransactionID () : " + trxValue.getTransactionID());
			DefaultLogger.debug(this, "trxValue.getReferenceID () : " + trxValue.getReferenceID());
		}

		trxValue = createStagingCommonCodeEntries(trxValue);
		trxValue = updateCommonCodeEntriesTrx(trxValue);
		return super.prepareResult(trxValue);
	}

}
