/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/profile/MakerUpdateProfileOperation.java,v 1.6 2004/08/17 06:52:17 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.profile;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to update a checklist
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2004/08/17 06:52:17 $ Tag: $Name: $
 */
public class MakerUpdateProfileOperation extends AbstractProfileTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerUpdateProfileOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		// Update is 'cretae' in staging and update in the ORIGINAL.
		IProfileTrxValue trxValue = createStagingProfile(getProfileTrxValue(anITrxValue));
		trxValue = updateProfileTransaction(trxValue);

		return super.prepareResult(trxValue);
	}
}