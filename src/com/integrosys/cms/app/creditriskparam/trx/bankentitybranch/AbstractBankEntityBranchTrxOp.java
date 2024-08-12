package com.integrosys.cms.app.creditriskparam.trx.bankentitybranch;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.BankEntityBranchParamException;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParam;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParamBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Title: CLIMS 
 * Description: Abstract class for all transaction operation
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: June 1, 2008
 */

public abstract class AbstractBankEntityBranchTrxOp extends CMSTrxOperation implements ITrxRouteOperation {

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

    protected IBankEntityBranchTrxValue prepareTrxValue(IBankEntityBranchTrxValue entityBranchTrxValue)
    {
        if(entityBranchTrxValue != null)
        {
            ArrayList actual = new ArrayList();
            ArrayList staging = new ArrayList();

            if(entityBranchTrxValue.getBankEntityBranchParam()!=null) {
                actual = new ArrayList(entityBranchTrxValue.getBankEntityBranchParam());
            }

            if(entityBranchTrxValue.getStagingBankEntityBranchParam()!=null) {
                staging = new ArrayList(entityBranchTrxValue.getStagingBankEntityBranchParam());
            }

            //default reference ID to 1, this module should hav only 1 transaction entry all the time
            entityBranchTrxValue.setReferenceID("1");
            entityBranchTrxValue.setStagingReferenceID(staging.size()==0?null:((IBankEntityBranchParam)staging.get(0)).getGroupId().toString());
            return entityBranchTrxValue;
        }
        return null;
    }

    /**
     * Create actual bank entity branch param
     */
    protected IBankEntityBranchTrxValue createBankEntityBranch (IBankEntityBranchTrxValue value)
        throws TrxOperationException
    {
        try {
//            Collection collection = getBusDelegate().createBankEntityBranch(value.getStagingBankEntityBranchParam());
            Collection collection = getBankEntityBranchParamBusManager().createBankEntityBranchParam(value.getStagingBankEntityBranchParam());
            value.setBankEntityBranchParam(collection);
            return value;
        }
        catch (BankEntityBranchParamException e) {
            throw new TrxOperationException ("BankEntityBranchParamException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
     * Create staging bank entity branch param
     */
    protected IBankEntityBranchTrxValue createStagingBankEntityBranch (IBankEntityBranchTrxValue value)
        throws TrxOperationException
    {
        try {
            if(value.getStagingBankEntityBranchParam().size() > 0)
            {
//                Collection collection = getBusDelegate().createStgBankEntityBranch(value.getStagingBankEntityBranchParam());
                Collection collection = getStagingBankEntityBranchParamBusManager().createBankEntityBranchParam(value.getStagingBankEntityBranchParam());
                value.setStagingBankEntityBranchParam(collection);
            }
            return value;
        }
        catch (BankEntityBranchParamException e) {
            throw new TrxOperationException ("BankEntityBranchParamException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
     * Updates a list of actual bank entity branch param using a list of
     * staging bank entity branch param.
     */
    protected IBankEntityBranchTrxValue updateBankEntityBranch (IBankEntityBranchTrxValue value)
            throws TrxOperationException
    {
        try {
            Collection stageParams =value.getStagingBankEntityBranchParam();
            ArrayList arrayList = stageParams!=null?new ArrayList(stageParams):new ArrayList();
//            value.setStagingBankEntityBranchParam(getBusDelegate().updateBankEntityBranch(arrayList));
            value.setStagingBankEntityBranchParam(getBankEntityBranchParamBusManager().updateBankEntityBranchParam(arrayList));

            return value;
        }
        catch (BankEntityBranchParamException e) {
            throw new TrxOperationException ("BankEntityBranchParamException caught!", e);
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
     * Method to create a transaction record
     */
    protected IBankEntityBranchTrxValue createTransaction (IBankEntityBranchTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue (value);
            ICMSTrxValue tempValue = super.createTransaction (value);
            OBBankEntityBranchTrxValue newValue = new OBBankEntityBranchTrxValue(tempValue);
            newValue.setBankEntityBranchParam (value.getBankEntityBranchParam());
            newValue.setStagingBankEntityBranchParam (value.getStagingBankEntityBranchParam());
            return newValue;
        }
        catch(TrxOperationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    /**
     * Method to update a transaction record.
     */
    protected IBankEntityBranchTrxValue updateTransaction (IBankEntityBranchTrxValue value)
        throws TrxOperationException
    {
        try {
            value = prepareTrxValue(value);

            ICMSTrxValue tempValue = super.updateTransaction (value);
            OBBankEntityBranchTrxValue newValue = new OBBankEntityBranchTrxValue(tempValue);
            newValue.setBankEntityBranchParam (value.getBankEntityBranchParam());
            newValue.setStagingBankEntityBranchParam (value.getStagingBankEntityBranchParam());
            return newValue;
        }
        catch (TrxOperationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TrxOperationException ("Exception caught!", e);
        }
    }

    protected IBankEntityBranchTrxValue getBankEntityBranchTrxValue(ITrxValue anITrxValue) throws TrxOperationException
    {
        try
        {
            return (IBankEntityBranchTrxValue)anITrxValue;
        }
        catch(ClassCastException ex)
        {
            throw new TrxOperationException("The ITrxValue is not of type OBBankEntityBranchTrxValue: " + ex.toString());
        }
    }

//    protected IPropertyIdx mergePropertyIdx(IPropertyIdx anOriginal, IPropertyIdx aCopy) throws TrxOperationException
//    {
//        aCopy.setPropertyIdxId(anOriginal.getPropertyIdxId());
//        aCopy.setVersionTime(anOriginal.getVersionTime());
//        return aCopy;
//    }

    protected ITrxResult prepareResult(IBankEntityBranchTrxValue value)
    {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }

//    protected BankEntityBranchBusDelegate getBusDelegate()
//    {
//        return new BankEntityBranchBusDelegate();
//    }
}
