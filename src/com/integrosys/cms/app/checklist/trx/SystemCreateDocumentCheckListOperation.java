package com.integrosys.cms.app.checklist.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation allows a checklist database operation from RLOS to CMS  
 *
 * @author $Author: Iwan Satria $
 * @version $Revision: 1.4 $
 * @since $Date: 2008/11/27 12:52:44 $ Tag: $Name: $
 */
public class SystemCreateDocumentCheckListOperation extends AbstractCheckListTrxOperation {
	/**
	 * Default Constructor
	 */
	public SystemCreateDocumentCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 *
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_CREATE_DOCUMENT_CHECKLIST;
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
		ICheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		ICheckList staging = trxValue.getStagingCheckList();
		try {
			if (staging != null) {
				ICMSTrxValue parentTrx = null;
				if (staging.getCheckListOwner() instanceof ICCCheckListOwner) {
					ICCCheckListOwner owner = (ICCCheckListOwner) staging.getCheckListOwner();
					if (owner.getLimitProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
						parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(owner.getLimitProfileID()),
								ICMSConstant.INSTANCE_LIMIT_PROFILE);
						trxValue.setTrxReferenceID(parentTrx.getTransactionID());
					}
					else if (owner.getSubOwnerID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
						parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(owner.getSubOwnerID()),
								ICMSConstant.INSTANCE_CUSTOMER);
						trxValue.setTrxReferenceID(parentTrx.getTransactionID());
					}
					return trxValue;
				}

				if (staging.getCheckListOwner() instanceof ICollateralCheckListOwner) {
					ICollateralCheckListOwner owner = (ICollateralCheckListOwner) staging.getCheckListOwner();
					if (owner.getLimitProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
						parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(owner.getLimitProfileID()),
								ICMSConstant.INSTANCE_LIMIT_PROFILE);
						trxValue.setTrxReferenceID(parentTrx.getTransactionID());
					}
					return trxValue;
				}
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
	 * Create the actual document item doc
	 * @param anICheckListTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the trx object containing the created
	 *         actual document item
	 * @throws TrxOperationException if errors
	 */
	private ICheckListTrxValue createActualCheckList(ICheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		try {
			ICheckList checkList = getSBCheckListBusManager().create(anICheckListTrxValue.getCheckList());
			anICheckListTrxValue.setCheckList(checkList);
			anICheckListTrxValue.setReferenceID(String.valueOf(checkList.getCheckListID()));
			return anICheckListTrxValue;
		}
		catch (CheckListException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex.toString());
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
		ICheckListTrxValue trxValue = super.getCheckListTrxValue(anITrxValue);
		trxValue = createStagingCheckList(trxValue);
		trxValue = createActualCheckList(trxValue);
		trxValue = createCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}


	/**
	 * Create a document item transaction
	 * @param anICheckListTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the document item specific
	 *         transaction object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private ICheckListTrxValue createCheckListTransaction(ICheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		try {
			anICheckListTrxValue = prepareTrxValue(anICheckListTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICheckListTrxValue);
			OBCheckListTrxValue checkListTrxValue = new OBCheckListTrxValue(trxValue);
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
