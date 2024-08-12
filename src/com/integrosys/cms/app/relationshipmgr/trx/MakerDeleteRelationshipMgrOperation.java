package com.integrosys.cms.app.relationshipmgr.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.otherbank.bus.OtherBankReplicationUtils;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrReplicationUtils;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * @author dattatray.thorat
 * Maker delete operation to  delete Relationship Manager 
 */
public class MakerDeleteRelationshipMgrOperation extends AbstractRelationshipMgrTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerDeleteRelationshipMgrOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_DELETE_RELATIONSHIP_MGR;
    }

    /**
     * Pre process.
     * Prepares the transaction object for persistance
     * Get the parent  transaction ID to be appended as trx parent ref
     *
     * @param anITrxValue is of type ITrxValue
     * @return ITrxValue
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          on error
     */
    public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
        anITrxValue = super.preProcess(anITrxValue);
        IRelationshipMgrTrxValue trxValue = getRelationshipMgrTrxValue(anITrxValue);
        IRelationshipMgr staging = trxValue.getStagingRelationshipMgr();
        try {
            if (staging != null) {
                

                if (staging.getId() != ICMSConstant.LONG_INVALID_VALUE) {
                	ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getId()), ICMSConstant.INSTANCE_RELATIONSHIP_MGR);
                    trxValue.setTrxReferenceID(parentTrx.getTransactionID());
                }
                
            }else{
            	throw new TrxOperationException("Staging Value is null");
            }
            return trxValue;
        }
        
        catch (Exception ex) {
            throw new TrxOperationException("Exception in preProcess: " + ex.getMessage());
        }
    }

    /**
     * Process the transaction
     * 1.	Create the staging data
     * 2.	Update the transaction record
     *
     * @param anITrxValue of ITrxValue type
     * @return ITrxResult - the transaction result
     * @throws TrxOperationException if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IRelationshipMgrTrxValue idxTrxValue = getRelationshipMgrTrxValue(anITrxValue);
        IRelationshipMgr stage = idxTrxValue.getStagingRelationshipMgr();
        IRelationshipMgr replicatedRelationshipMgr = RelationshipMgrReplicationUtils.replicateRelationshipMgrForCreateStagingCopy(stage);
   
        idxTrxValue.setStagingRelationshipMgr(replicatedRelationshipMgr);

        IRelationshipMgrTrxValue trxValue = createStagingRelationshipMgr(idxTrxValue);
        trxValue = updateRelationshipMgrTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
