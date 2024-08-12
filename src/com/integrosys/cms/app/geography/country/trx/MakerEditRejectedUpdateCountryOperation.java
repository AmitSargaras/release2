package com.integrosys.cms.app.geography.country.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.trx.AbstractCountryTrxOperation;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;

public class MakerEditRejectedUpdateCountryOperation extends AbstractCountryTrxOperation{

	 /**
     * Defaulc Constructor
     */
     public MakerEditRejectedUpdateCountryOperation()
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
         return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_COUNTRY;
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
     	ICountryTrxValue idxTrxValue = getCountryTrxValue(anITrxValue);
         ICountry stage = idxTrxValue.getStagingCountry();
         /*IRelationshipMgr replicatedRelationshipMgr= new OBRelationshipMgr();
         replicatedRelationshipMgr.setId(stage.getId());
         replicatedRelationshipMgr.setRelationshipMgrCode(stage.getRelationshipMgrCode());
         replicatedRelationshipMgr.setEmployeeCode(stage.getEmployeeCode());
         replicatedRelationshipMgr.setRelationshipMgrName(stage.getRelationshipMgrName());
         replicatedRelationshipMgr.setRelationshipMgrMailId(stage.getRelationshipMgrMailId());
         replicatedRelationshipMgr.setReportingHeadName(stage.getReportingHeadName());
         replicatedRelationshipMgr.setReportingHeadMailId(stage.getReportingHeadMailId());
         replicatedRelationshipMgr.setRegion(stage.getRegion());
         
         replicatedRelationshipMgr.setCreatedBy(stage.getCreatedBy());
         replicatedRelationshipMgr.setCreationDate(stage.getCreationDate());
         replicatedRelationshipMgr.setDeprecated(stage.getDeprecated());
         replicatedRelationshipMgr.setLastUpdateBy(stage.getLastUpdateBy());
         replicatedRelationshipMgr.setLastUpdateDate(stage.getLastUpdateDate());
         replicatedRelationshipMgr.setStatus(stage.getStatus());
         replicatedRelationshipMgr.setVersionTime(stage.getVersionTime());*/
         
         idxTrxValue.setStagingCountry(stage);

         ICountryTrxValue trxValue = createStagingCountry(idxTrxValue);
         trxValue = updateCountryTrx(trxValue);
         return super.prepareResult(trxValue);
     }
}
