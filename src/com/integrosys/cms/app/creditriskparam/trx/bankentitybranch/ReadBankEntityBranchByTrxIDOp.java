/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.bankentitybranch;

import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParamBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Title: CLIMS
 * Description: Read Bank Entity Branch Trx Operation by Trx Id
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: June 1, 2008
 */
public class ReadBankEntityBranchByTrxIDOp extends AbstractBankEntityBranchTrxOp implements ITrxReadOperation
{

    private IBankEntityBranchParamBusManager bankEntityBranchParamBusManager;

    private IBankEntityBranchParamBusManager stagingBankEntityBranchParamBusManager;

    public IBankEntityBranchParamBusManager getBankEntityBranchParamBusManager() {
        return bankEntityBranchParamBusManager;
    }

    public void setBankEntityBranchParamBusManager(IBankEntityBranchParamBusManager bankEntityBranchParamBusManager) {
        this.bankEntityBranchParamBusManager = bankEntityBranchParamBusManager;
    }

    public IBankEntityBranchParamBusManager getStagingBankEntityBranchParamBusManager() {
        return stagingBankEntityBranchParamBusManager;
    }

    public void setStagingBankEntityBranchParamBusManager(IBankEntityBranchParamBusManager stagingBankEntityBranchParamBusManager) {
        this.stagingBankEntityBranchParamBusManager = stagingBankEntityBranchParamBusManager;
    }

    /**
  	 * Default Constructor
 	 */
	public ReadBankEntityBranchByTrxIDOp() {
		super();
	}

	/**
 	 * Get the operation name of the current operation.
     *
 	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_BANK_ENTITY_BRANCH_BY_TRXID;
	}

    /**
     * This method is used to read a transaction object.
     *
     * @param val transaction value required for retrieving transaction record
     * @return transaction value
     * @throws com.integrosys.base.businfra.transaction.TransactionException on errors retrieving the transaction value
     */
    public ITrxValue getTransaction (ITrxValue val) throws TransactionException
    {
        try
        {

			ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());

			OBBankEntityBranchTrxValue branchTrxValue = new OBBankEntityBranchTrxValue(cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();

			if (stagingRef != null) {
//                branchTrxValue.setStagingBankEntityBranchParam(getBusDelegate().getStgBankEntityBranchByGroupId(Long.parseLong(stagingRef)));
                branchTrxValue.setStagingBankEntityBranchParam(getStagingBankEntityBranchParamBusManager().getBankEntityBranchParamByGroupID(Long.parseLong(stagingRef)));
            }

			if (actualRef != null) {
//                branchTrxValue.setBankEntityBranchParam(getBusDelegate().getBankEntityBranch());
                branchTrxValue.setBankEntityBranchParam(getBankEntityBranchParamBusManager().getBankEntityBranchParam());
            }
            return branchTrxValue;
        }
        catch (Exception e) {
            throw new TrxOperationException (e);
        }
    }
}