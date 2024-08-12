package com.integrosys.cms.app.geography.country.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.trx.AbstractCountryTrxOperation;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;

public class MakerEditRejectedDeleteCountryOperation extends AbstractCountryTrxOperation{

	 /**
     * Defaulc Constructor
     */
     public MakerEditRejectedDeleteCountryOperation()
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
         return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DELETE_COUNTRY;
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
         /*ICountry replicatedCountry= new OBCountry();
         replicatedCountry.setId(stage.getId());
         replicatedCountry.setCountryCode(stage.getCountryCode());
         replicatedCountry.setEmployeeCode(stage.getEmployeeCode());
         replicatedCountry.setCountryName(stage.getCountryName());
         replicatedCountry.setCountryMailId(stage.getCountryMailId());
         replicatedCountry.setReportingHeadName(stage.getReportingHeadName());
         replicatedCountry.setReportingHeadMailId(stage.getReportingHeadMailId());
         replicatedCountry.setRegion(stage.getRegion());
         
         replicatedCountry.setCreatedBy(stage.getCreatedBy());
         replicatedCountry.setCreationDate(stage.getCreationDate());
         replicatedCountry.setDeprecated(stage.getDeprecated());
         replicatedCountry.setLastUpdateBy(stage.getLastUpdateBy());
         replicatedCountry.setLastUpdateDate(stage.getLastUpdateDate());
         replicatedCountry.setStatus(stage.getStatus());
         replicatedCountry.setVersionTime(stage.getVersionTime());*/
         
         idxTrxValue.setStagingCountry(stage);

         ICountryTrxValue trxValue = createStagingCountry(idxTrxValue);
         trxValue = updateCountryTrx(trxValue);
         return super.prepareResult(trxValue);
     }
}
