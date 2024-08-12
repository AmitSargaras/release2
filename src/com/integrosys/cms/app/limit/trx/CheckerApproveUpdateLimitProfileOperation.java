/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/CheckerApproveUpdateLimitProfileOperation.java,v 1.3 2003/07/23 07:49:22 kllee Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;

/**
 * This operation allows checker to approve update on limit profile
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/23 07:49:22 $ Tag: $Name: $
 */
public class CheckerApproveUpdateLimitProfileOperation extends AbstractLimitProfileTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerApproveUpdateLimitProfileOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_LIMIT_PROFILE;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. Update Actual Record From Staging 2. Update Transaction Record
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxResult
	 * @throws TrxOperationException on error
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ILimitProfileTrxValue trxValue = super.getLimitProfileTrxValue(value);

			ILimitProfile actual = trxValue.getLimitProfile();
			if (actual == null) {
				trxValue = super.createActualLimitProfile(trxValue);
			}
			else {
				trxValue = super.updateActualLimitProfileFromStaging(trxValue);
			}
			trxValue = super.updateTransaction(trxValue);
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> Staging ID in trx >> "+trxValue.getStagingReferenceID());
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> Reference ID in trx >> "+trxValue.getReferenceID());
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> Transaction ID in trx >> "+trxValue.getTransactionID());
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