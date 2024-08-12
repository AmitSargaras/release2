/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/profile/MakerCloseDraftCreateProfileOperation.java,v 1.1 2005/01/13 08:29:30 whuang Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.profile;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to close rejected/draft
 * transactions.
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/01/13 08:29:30 $ Tag: $Name: $
 */
public class MakerCloseDraftCreateProfileOperation extends AbstractProfileTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerCloseDraftCreateProfileOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			IProfileTrxValue trxValue = super.getProfileTrxValue(value);
			trxValue = super.updateProfileTransaction(trxValue);

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
