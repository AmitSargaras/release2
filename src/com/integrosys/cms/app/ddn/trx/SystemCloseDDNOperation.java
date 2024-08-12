/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/trx/SystemCloseDDNOperation.java,v 1.2 2005/06/08 06:33:42 htli Exp $
 */
package com.integrosys.cms.app.ddn.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows system to close DDN trx
 * 
 * @author $Author: htli $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/06/08 06:33:42 $ Tag: $Name: $
 */
public class SystemCloseDDNOperation extends AbstractDDNTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemCloseDDNOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_CLOSE_DDN;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IDDNTrxValue trxValue = getDDNTrxValue(anITrxValue);
		trxValue = updateDDNTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}