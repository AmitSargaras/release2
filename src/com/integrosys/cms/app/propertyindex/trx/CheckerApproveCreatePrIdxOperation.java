package com.integrosys.cms.app.propertyindex.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxException;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxReplicationUtils;

import java.util.Set;

/**
 * Title: CLIMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 18, 2008
 */

public class CheckerApproveCreatePrIdxOperation extends AbstractPropertyIdxTrxOperation {
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_PRIDX;
    }

    /**
     * Process the transaction
     * 1.	Create the actual data
     * 2.	Update the transaction record
     *
     * @param anITrxValue of ITrxValue type
     * @return ITrxResult - the transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IPropertyIdxTrxValue trxValue = getPropertyIdxTrxValue(anITrxValue);
        trxValue = createActualPropertyIdx(trxValue);
        trxValue = updatePropertyIdxTrx(trxValue);
        return super.prepareResult(trxValue);
    }


    /**
     * Create the actual property index
     *
     * @param anITrxValue of ITrxValue type
     * @return ICCDocumentLocationTrxValue - the document item trx value
     * @throws TrxOperationException on errors
     */
    private IPropertyIdxTrxValue createActualPropertyIdx(IPropertyIdxTrxValue idxTrxValue) throws TrxOperationException {
        try {
            IPropertyIdx staging = idxTrxValue.getStagingPrIdx();
            IPropertyIdx replicatedPrIdx = PropertyIdxReplicationUtils.replicatePropertyIdxForCreateStagingCopy(staging);
            IPropertyIdx actual = getPropertyIdxBusManager().createPropertyIdx(replicatedPrIdx);
            idxTrxValue.setPrIdx(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getPropertyIdxId()));

            Set updatePropertyIdxEntryRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(actual
				.getPropertyIdxItemList(), "cmsRefId", Long.class, "propertyIdxItemId");
		    actual.getPropertyIdxItemList().clear();
		    if (updatePropertyIdxEntryRefSet != null) {
			    actual.getPropertyIdxItemList().addAll(updatePropertyIdxEntryRefSet);
		    }
		    getPropertyIdxBusManager().updatePropertyIdx(actual);
            return idxTrxValue;
        }
        catch (PropertyIdxException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
