/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/MakerUpdateCoBorrowerLimitOperation.java,v 1.2 2005/09/22 02:03:08 whuang Exp $
 */
package com.integrosys.cms.app.limit.trx;

//java

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows maker to update limit
 * 
 * @author $Author: whuang $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/09/22 02:03:08 $ Tag: $Name: $
 */
public class MakerUpdateCoBorrowerLimitOperation extends AbstractCoBorrowerLimitTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerUpdateCoBorrowerLimitOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_CO_LIMIT;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. Create New Staging Record 2. Update Transaction Record
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxResult
	 * @throws TrxOperationException on error
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ICoBorrowerLimitTrxValue trxValue = super.getCoBorrowerLimitTrxValue(value);
			trxValue = super.createStagingLimit(trxValue);
			trxValue = super.updateTransaction(trxValue);
			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			DefaultLogger.debug(this, e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new TrxOperationException("Caught Exception!", e);
		}
	}
}