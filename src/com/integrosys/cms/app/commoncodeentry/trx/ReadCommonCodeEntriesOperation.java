/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * ReadCommonCodeEntriesOperation.java
 *
 * Created on February 7, 2007, 4:27:26 PM
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

import java.util.Collection;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntries;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntries;
import com.integrosys.cms.app.commoncodeentry.bus.SBCommonCodeEntryManager;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 6, 2007 Time: 4:27:26 PM To
 * change this template use File | Settings | File Templates.
 */
public class ReadCommonCodeEntriesOperation extends AbstractCommonCodeEntriesTrxOperation implements ITrxReadOperation {

	public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
		try {
			ICMSTrxValue trxValue = getTrxManager().getTransaction(anITrxValue.getTransactionID());

			String referID = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Reference ID : " + referID);

			ICMSTrxValue entryValues = getTrxManager().getTrxByRefIDAndTrxType(referID,
					ICMSConstant.COMMON_CODE_ENTRY_INSTANCE_NAME);

			OBCommonCodeEntriesTrxValue entries = new OBCommonCodeEntriesTrxValue(entryValues);

			String stagingID = entryValues.getStagingReferenceID();

			DefaultLogger.debug(this, "Staging data : " + entryValues.getReferenceID());

			DefaultLogger.debug(this, "Actual Reference : " + referID + " , Staging Reference : " + stagingID);

			if (stagingID != null) {
				SBCommonCodeEntryManager manager = getSBCommonCodeEntryManager();
				ICommonCodeEntries stagingData = manager.getStagingData(stagingID);

				DefaultLogger.debug(this, "Staging data found");

				entries.setStagingCommonCodeEntries(stagingData);
			}
			else {
				DefaultLogger.debug(this, "Staging id is null");
			}

			if (referID != null) {
				SBCommonCodeEntryManager manager = getSBCommonCodeEntryManager();
				Collection col = manager.getCommonCodeEntries(referID);
				OBCommonCodeEntries temp = new OBCommonCodeEntries();

				temp.setEntries(col);
				temp.setReferenceID(referID);
				temp.setTransactionID(anITrxValue.getTransactionID());
				temp.setCategoryCodeId(Long.parseLong(referID));

				DefaultLogger.debug(this, "Reference data found");

				entries.setCommonCodeEntries(temp);

			}
			else {
				DefaultLogger.debug(this, "Reference id is null");
			}

			return entries;
		}
		catch (Exception e) {
			throw new TransactionException(e);
		}
	}

	public String getOperationName() {
		return ICMSConstant.COMMON_CODE_ENTRY_READ;
	}
}
