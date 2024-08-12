package com.integrosys.cms.app.rbicategory.trx;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.rbicategory.bus.IRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.OBIndustryCodeCategory;
import com.integrosys.cms.app.rbicategory.bus.OBRbiCategory;

/**
  * @author Govind.Sahu
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedUpdateRbiCategoryOperation extends AbstractRbiCategoryTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedUpdateRbiCategoryOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_RBI_CATEGORY;
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
        	 IRbiCategoryTrxValue idxTrxValue = getRbiCategoryTrxValue(anITrxValue);
             IRbiCategory stage = idxTrxValue.getStagingRbiCategory();
             IRbiCategory replicatedRbiCategory= new OBRbiCategory();
             replicatedRbiCategory.setId(stage.getId());
             replicatedRbiCategory.setIndustryNameId(stage.getIndustryNameId());
             replicatedRbiCategory.setCreateBy(stage.getCreateBy());
             replicatedRbiCategory.setCreationDate(stage.getCreationDate());
             replicatedRbiCategory.setDeprecated(stage.getDeprecated());
             replicatedRbiCategory.setLastUpdateBy(stage.getLastUpdateBy());
             replicatedRbiCategory.setLastUpdateDate(stage.getLastUpdateDate());
             replicatedRbiCategory.setStatus(stage.getStatus());
             replicatedRbiCategory.setVersionTime(stage.getVersionTime());
             Set replicatedIndNameSet = new HashSet();
     		 Set stagingIndNameSet = stage.getStageIndustryNameSet();
     		 Iterator it = stagingIndNameSet.iterator();
     		 OBIndustryCodeCategory industryNameStageObj = new OBIndustryCodeCategory();
     		 while(it.hasNext())
     		 {
     			industryNameStageObj = (OBIndustryCodeCategory)it.next();
     			industryNameStageObj.setId(0);
     			industryNameStageObj.setRbiCategoryId(0);		
     			replicatedIndNameSet.add(industryNameStageObj);
     			
     		 }
     		 replicatedRbiCategory.setStageIndustryNameSet(replicatedIndNameSet);
            
            idxTrxValue.setStagingRbiCategory(replicatedRbiCategory);

            IRbiCategoryTrxValue trxValue = createStagingRbiCategory(idxTrxValue);
            trxValue = updateRbiCategoryTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
