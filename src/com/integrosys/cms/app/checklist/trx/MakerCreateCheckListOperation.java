/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/MakerCreateCheckListOperation.java,v 1.6 2005/05/10 09:39:37 bxu Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.checklist.bus.ICCCheckListOwner;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation creates a pending document item
 * 
 * @author $Author: bxu $
 * @version $Revision: 1.6 $
 * @since $Date: 2005/05/10 09:39:37 $ Tag: $Name: $
 */
public class MakerCreateCheckListOperation extends AbstractCheckListTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCreateCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_CHECKLIST;
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
						if(parentTrx!=null){
							
						if(parentTrx.getTransactionID()!=null){
						trxValue.setTrxReferenceID(parentTrx.getTransactionID());
						}
						}
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
						// by abhijit r 7july 
						if(owner.getLimitProfileID()!=0){
						parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(owner.getLimitProfileID()),
								ICMSConstant.INSTANCE_LIMIT_PROFILE);
						if(parentTrx!=null){
							
							if(parentTrx.getTransactionID()!=null){
						trxValue.setTrxReferenceID(parentTrx.getTransactionID());
						}
						}
						}
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
		trxValue = createCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Post Processing after the trx operation 1. To update the customer CCC
	 * status for non borrower
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult postProcess(ITrxResult result) throws TrxOperationException {
		result = super.postProcess(result);

		ICheckListTrxValue trxValue = (ICheckListTrxValue) result.getTrxValue();
		ICheckList staging = trxValue.getStagingCheckList();

		if (staging.getCheckListOwner() instanceof ICCCheckListOwner) {
			ICCCheckListOwner owner = (ICCCheckListOwner) staging.getCheckListOwner();
			if ((owner.getSubOwnerID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)
					&& (ICMSConstant.CHECKLIST_NON_BORROWER.equals(owner.getSubOwnerType()))) {
				super.updateCustomerStatus(owner.getSubOwnerID(), ICMSConstant.CCC_STARTED);
			}
		}
		return result;
	}

	/**
	 * Create a document item transaction
	 * @param anICheckListTrxValue - ICheckListTrxValue
	 * @return ICheckListTrxValue - the document item specific transaction
	 *         object created
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