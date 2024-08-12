/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/trx/MakerCloseRejectedCreateDDNOperation.java,v 1.2 2005/06/08 06:33:54 htli Exp $
 */
package com.integrosys.cms.app.ddn.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to close a rejected ddn creation transaction
 * 
 * @author $Author: htli $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/06/08 06:33:54 $ Tag: $Name: $
 */
public class MakerCloseRejectedCreateDDNOperation extends AbstractDDNTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCloseRejectedCreateDDNOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CREATE_GENERATE_DDN;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {

		try {
			IDDNTrxValue newValue = new DDNTrxDAO().getCloseApprovedHistoryTrxValue(anITrxValue.getTransactionID(),
					ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_DDN);

			IDDNTrxValue trxValue = super.getDDNTrxValue(anITrxValue);

			if (newValue != null) {
				trxValue.setRemarks(newValue.getRemarks());
			}

			trxValue = updateDDNTransaction(trxValue);
			return super.prepareResult(trxValue);
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex.toString());
		}

	}
}