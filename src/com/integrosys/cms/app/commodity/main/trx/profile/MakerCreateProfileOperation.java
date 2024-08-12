/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/profile/MakerCreateProfileOperation.java,v 1.7 2004/11/03 09:08:16 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.profile;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation creates a pending document item
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.7 $
 * @since $Date: 2004/11/03 09:08:16 $ Tag: $Name: $
 */
public class MakerCreateProfileOperation extends AbstractProfileTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCreateProfileOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN;
	}

	/**
	 * Process the transaction 1. Create the staging data 2. Create the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {

		IProfileTrxValue trxValue = super.getProfileTrxValue(anITrxValue);
		trxValue = super.createStagingProfile(trxValue);

		if (anITrxValue.getTransactionID() == null) {
			trxValue = super.createTransaction(trxValue);
		}
		else {
			trxValue = super.updateProfileTransaction(trxValue);
		}

		return super.prepareResult(trxValue);
	}

}