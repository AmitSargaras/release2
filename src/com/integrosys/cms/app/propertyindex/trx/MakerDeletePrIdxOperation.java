package com.integrosys.cms.app.propertyindex.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxReplicationUtils;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

import java.rmi.RemoteException;

/**
 * Title: CLIMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 18, 2008
 */
public class MakerDeletePrIdxOperation extends AbstractPropertyIdxTrxOperation {

    private static final long serialVersionUID = 1L;

    /**
     * Defaulc Constructor
     */
    public MakerDeletePrIdxOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_DELETE_PRIDX;
    }

    /**
    * Pre process.
    * Prepares the transaction object for persistance
    * Get the parent  transaction ID to be appended as trx parent ref
    * @param anITrxValue is of type ITrxValue
    * @return ITrxValue
    * @throws com.integrosys.base.businfra.transaction.TrxOperationException on error
    */
    public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException
    {
        anITrxValue = super.preProcess(anITrxValue);
        IPropertyIdxTrxValue trxValue = getPropertyIdxTrxValue (anITrxValue);
        IPropertyIdx staging = trxValue.getStagingPrIdx();
        try
        {
            if (staging != null)
            {
                ICMSTrxValue parentTrx = null;
                if (staging.getPropertyIdxId() != ICMSConstant.LONG_INVALID_VALUE)
                {
                    parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getPropertyIdxId()), ICMSConstant.INSTANCE_PROPERTY_IDX);
                    trxValue.setTrxReferenceID(parentTrx.getTransactionID());
                }
                return trxValue;
            }
            return trxValue;
        }
        catch(TransactionException ex)
        {
            throw new TrxOperationException(ex);
        }
        catch(RemoteException ex)
        {
            throw new TrxOperationException("Exception in preProcess: " + ex.toString());
        }
    }

    /**
     * Process the transaction
     * 1.	Create the staging data
     * 2.	Update the transaction record
     *
     * @param anITrxValue of ITrxValue type
     * @return ITrxResult - the transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IPropertyIdxTrxValue idxTrxValue = getPropertyIdxTrxValue(anITrxValue);
        IPropertyIdx stage = idxTrxValue.getStagingPrIdx();
        IPropertyIdx replicatedPrIdx = PropertyIdxReplicationUtils.replicatePropertyIdxForCreateStagingCopy(stage);
        replicatedPrIdx.setStatus(ICMSConstant.STATE_DELETED);
        idxTrxValue.setStagingPrIdx(replicatedPrIdx);

        IPropertyIdxTrxValue trxValue = createStagingPropertyIdx(idxTrxValue);
        trxValue = updatePropertyIdxTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
