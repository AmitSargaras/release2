/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckerApproveCreateRecurrentCheckListOperation.java,v 1.1 2003/07/28 02:17:38 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.annexure.trx;

//java

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;

/**
 * This operation allows a checker to approve a recurrent checklist create
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/28 02:17:38 $ Tag: $Name: $
 */
public class CheckerApproveCreateAnnexureOperation extends AbstractAnnexureTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateAnnexureOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_ANNEXURE_CHECKLIST;
	}

	/**
	 * Process the transaction 1. Create the actual data 2. Update the staging
	 * data with the actual data 3. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IRecurrentCheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		trxValue = createActualCheckList(trxValue);
		trxValue = updateCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create the actual document item
	 * @param anICheckListTrxValue - ITrxValue
	 * @return IRecurrentCheckListTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private IRecurrentCheckListTrxValue createActualCheckList(IRecurrentCheckListTrxValue anICheckListTrxValue)
			throws TrxOperationException {
		try {
			IRecurrentCheckList checkList = anICheckListTrxValue.getStagingCheckList();
			IRecurrentCheckList actualCheckList = getRecurrentBusManager().create(checkList);
			anICheckListTrxValue.setCheckList(actualCheckList);
			anICheckListTrxValue.setReferenceID(String.valueOf(actualCheckList.getCheckListID()));
			return anICheckListTrxValue;
		}
		catch (RecurrentException cex) {
			throw new TrxOperationException(cex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in createActualCheckList(): " + ex.toString());
		}
	}
}