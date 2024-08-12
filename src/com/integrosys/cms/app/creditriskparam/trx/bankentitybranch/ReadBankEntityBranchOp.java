package com.integrosys.cms.app.creditriskparam.trx.bankentitybranch;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParamBusManager;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Title: CLIMS
 * Description: Read Bank Entity Branch Trx Operation
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: June 1, 2008
 */

public class ReadBankEntityBranchOp extends AbstractBankEntityBranchTrxOp implements ITrxReadOperation {

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
    public ReadBankEntityBranchOp() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_BANK_ENTITY_BRANCH;
    }

    /**
     * This method is used to read a transaction object
     *
     * @param anITrxValue the ITrxValue object containing the parameters required for
     *                    retrieving a record, such as the transaction ID.
     * @return ITrxValue - containing the requested data.
     * @throws com.integrosys.base.businfra.transaction.TransactionException
     *          if any other errors occur.
     */
    public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
        try {
            String actualRefID = "1"; // ref ID for tis module always be 1
            String stagingRefID = null;

            ICMSTrxValue trxValue = getTrxManager().getTrxByRefIDAndTrxType(actualRefID, ICMSConstant.INSTANCE_BANK_ENTITY_BRANCH_PARAM);
            OBBankEntityBranchTrxValue branchTrxValue = new OBBankEntityBranchTrxValue(trxValue);
            stagingRefID = trxValue.getStagingReferenceID();

            if (stagingRefID != null) {
//                branchTrxValue.setStagingBankEntityBranchParam(getBusDelegate().getStgBankEntityBranchByGroupId(Long.parseLong(stagingRefID)));
                branchTrxValue.setStagingBankEntityBranchParam(getStagingBankEntityBranchParamBusManager().getBankEntityBranchParamByGroupID(Long.parseLong(stagingRefID)));
            }

//            branchTrxValue.setBankEntityBranchParam(getBusDelegate().getBankEntityBranch());
            branchTrxValue.setBankEntityBranchParam(getBankEntityBranchParamBusManager().getBankEntityBranchParam());
            return branchTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex.toString());
        }
    }
}
