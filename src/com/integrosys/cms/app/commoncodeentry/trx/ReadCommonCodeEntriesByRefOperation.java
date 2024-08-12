package com.integrosys.cms.app.commoncodeentry.trx;

import java.util.Collection;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncodeentry.bus.CommonCodeEntryDAO;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntries;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntries;
import com.integrosys.cms.app.commoncodeentry.bus.SBCommonCodeEntryManager;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

public class ReadCommonCodeEntriesByRefOperation extends AbstractCommonCodeEntriesTrxOperation implements ITrxReadOperation {
	public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
		try {
			//ICMSTrxValue trxValue = getTrxManager().getTransaction(anITrxValue.getTransactionID());

			String referID = ((ICMSTrxValue)anITrxValue).getReferenceID();

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
				//by anup
				Collection  col = manager.getCommonCodeEntries(referID);
				CommonCodeEntryDAO cce=new CommonCodeEntryDAO();
				OBCommonCodeEntriesTrxValue obtrx=(OBCommonCodeEntriesTrxValue) anITrxValue;
					if("".equals(obtrx.getCodeDescription()))
							obtrx.setCodeDescription(null);
					if("".equalsIgnoreCase(obtrx.getCodeValue()))
							obtrx.setCodeValue(null);
					Long id = new Long(Long.parseLong(referID));
					if(null!=obtrx.getCodeDescription() || null!=obtrx.getCodeValue())
						col=cce.findByEntryValues(id, obtrx.getCodeDescription(), obtrx.getCodeValue());
				//end anup
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
		return ICMSConstant.COMMON_CODE_ENTRY_READ_BY_REF;
	}

}
