package com.integrosys.cms.app.chktemplate.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.holiday.bus.HolidayReplicationUtils;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 * Maker Update operation to  update system 
 */
public class MakerUpdateDraftCreateDocOperation extends AbstractDocItemTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerUpdateDraftCreateDocOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_DOC_ITEM;
    }

    /**
	 * Process the transaction 1. Create the staging data 2. Create the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IDocumentItemTrxValue trxValue = getDocumentItemTrxValue(anITrxValue);
		//trxValue = prepareChildValue(trxValue);
		trxValue = createStagingDocItem(trxValue);
		trxValue = createDocItemTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create a document item transaction
	 * @param anITrxValue - ITrxValue
	 * @return OBDocumentItemTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private IDocumentItemTrxValue createDocItemTransaction(IDocumentItemTrxValue anIDocumentItemTrxValue)
			throws TrxOperationException {
		try {
			anIDocumentItemTrxValue = prepareTrxValue(anIDocumentItemTrxValue);
			ICMSTrxValue trxValue = updateTransaction(anIDocumentItemTrxValue);
			OBDocumentItemTrxValue docItemTrxValue = new OBDocumentItemTrxValue(trxValue);
			docItemTrxValue.setStagingDocumentItem(anIDocumentItemTrxValue.getStagingDocumentItem());
			docItemTrxValue.setDocumentItem(anIDocumentItemTrxValue.getDocumentItem());
			return docItemTrxValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}
}