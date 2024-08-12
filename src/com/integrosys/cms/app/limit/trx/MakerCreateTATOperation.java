/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/MakerCreateTATOperation.java,v 1.3 2003/07/28 05:56:56 kllee Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;

/**
 * This operation allows a create of TAT
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/28 05:56:56 $ Tag: $Name: $
 */
public class MakerCreateTATOperation extends AbstractLimitProfileTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCreateTATOperation() {
		super();
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Update TAT
	 * Create Date
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		value = super.preProcess(value);

		// set TAT Create Date
		ILimitProfileTrxValue trxValue = getLimitProfileTrxValue(value);

		ILimitProfile stage = trxValue.getStagingLimitProfile();
		// stage.setTATCreateDate(DateUtil.getDate());
		trxValue.setStagingLimitProfile(stage);
		return trxValue;
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_TAT;
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
			ILimitProfileTrxValue trxValue = super.getLimitProfileTrxValue(value);

			trxValue = super.createStagingLimitProfile(trxValue);
			trxValue = super.updateTransaction(trxValue);

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