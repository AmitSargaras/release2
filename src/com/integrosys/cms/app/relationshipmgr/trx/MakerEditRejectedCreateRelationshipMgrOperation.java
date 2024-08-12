package com.integrosys.cms.app.relationshipmgr.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * @author dattatray.thorat
 *Maker Edit Rejected operation to   create rejected record by checker
 */
public class MakerEditRejectedCreateRelationshipMgrOperation extends AbstractRelationshipMgrTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedCreateRelationshipMgrOperation()
        {
            super();
        }

        /**
        * Get the operation name of the current operation
        *
        * @return String - the operation name of the current operation
        */
        public String getOperationName()
        {
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_RELATIONSHIP_MGR;
        }

        /**
        * Process the transaction
        * 1.    Create Staging record
        * 2.    Update the transaction record
        * @param anITrxValue - ITrxValue
        * @return ITrxResult - the transaction result
        * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
        */
        public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
        {
            IRelationshipMgrTrxValue idxTrxValue = getRelationshipMgrTrxValue(anITrxValue);
            IRelationshipMgr stage = idxTrxValue.getStagingRelationshipMgr();
            idxTrxValue.setStagingRelationshipMgr(stage);

            IRelationshipMgrTrxValue trxValue = createStagingRelationshipMgr(idxTrxValue);
            trxValue = updateRelationshipMgrTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
