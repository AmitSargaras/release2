/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/MakerCloseRejectedUpdateRecurrentCheckListOperation.java,v 1.2 2003/09/22 11:56:23 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.annexure.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;

/**
 * This operation allows a maker to close a rejected checklist transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/22 11:56:23 $ Tag: $Name: $
 */
public class MakerCloseRejectedUpdateAnnexureOperation extends AbstractAnnexureTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCloseRejectedUpdateAnnexureOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UPDATE_ANNEXURE_CHECKLIST;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IRecurrentCheckListTrxValue trxValue = getCheckListTrxValue(anITrxValue);
		// trxValue.setStagingCheckList(trxValue.getCheckList());
		// trxValue = createStagingCheckList(trxValue);
		trxValue = updateCheckListTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}