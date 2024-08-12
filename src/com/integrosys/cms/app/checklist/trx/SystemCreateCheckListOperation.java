package com.integrosys.cms.app.checklist.trx;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.trx.AbstractRecurrentCheckListTrxOperation;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.app.recurrent.trx.OBRecurrentCheckListTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import java.rmi.RemoteException;

/**
 * This operation allows a checker to approve the checklist updating
 *
 * @author $Author: hltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/04/08 12:52:44 $ Tag: $Name: $
 */
public class SystemCreateCheckListOperation extends AbstractRecurrentCheckListTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemCreateCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 *
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_CREATE_CHECKLIST;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * parent checklist transaction ID to be appended as trx parent ref
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		IRecurrentCheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		IRecurrentCheckList staging = trxValue.getStagingCheckList();
		try {
			if (staging != null) {
				ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(
						String.valueOf(staging.getLimitProfileID()), ICMSConstant.INSTANCE_LIMIT_PROFILE);
				trxValue.setTrxReferenceID(parentTrx.getTransactionID());
			}
			return trxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in preProcess: " + ex.toString());
		}
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
		IRecurrentCheckListTrxValue trxValue = super.getCheckListTrxValue(anITrxValue);
		trxValue = createStagingCheckList(trxValue);
		trxValue = createCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create a document item transaction
	 * @param anICheckListTrxValue - IRecurrentCheckListTrxValue
	 * @return IRecurrentCheckListTrxValue - the document item specific
	 *         transaction object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private IRecurrentCheckListTrxValue createCheckListTransaction(IRecurrentCheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		try {
			anICheckListTrxValue = prepareTrxValue(anICheckListTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICheckListTrxValue);
			OBRecurrentCheckListTrxValue checkListTrxValue = new OBRecurrentCheckListTrxValue(trxValue);
			checkListTrxValue.setStagingCheckList(anICheckListTrxValue.getStagingCheckList());
			checkListTrxValue.setCheckList(anICheckListTrxValue.getCheckList());
			return checkListTrxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}


}
