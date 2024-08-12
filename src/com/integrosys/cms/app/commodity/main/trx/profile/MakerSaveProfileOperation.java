/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/profile/MakerSaveProfileOperation.java,v 1.4 2004/08/17 06:52:17 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.profile;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to save commodity prices.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/17 06:52:17 $ Tag: $Name: $
 */
public class MakerSaveProfileOperation extends AbstractProfileTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerSaveProfileOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging commodity price records 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {

			IProfileTrxValue trxValue = super.getProfileTrxValue(value);

			trxValue = super.createStagingProfile(trxValue);

			if (trxValue.getTransactionID() == null) {
				trxValue = getProfileTrxValue(super.createTransaction(trxValue));
			}
			else {
				trxValue = getProfileTrxValue(super.updateProfileTransaction(trxValue));
			}

			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}
}
