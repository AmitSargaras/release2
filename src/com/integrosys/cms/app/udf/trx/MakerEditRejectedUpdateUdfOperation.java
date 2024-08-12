package com.integrosys.cms.app.udf.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.OBUdf;

/**
 * @author Santosh.Sonmankar
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedUpdateUdfOperation extends AbstractUdfTrxOperation{

	 public MakerEditRejectedUpdateUdfOperation(){
		 super();
	 }
	  
	public String getOperationName() {
       return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_UDF;
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
       IUdfTrxValue idxTrxValue = super.getUdfTrxValue(anITrxValue);
       IUdf stage = idxTrxValue.getStagingUdf();
       IUdf replicatedUdf= new OBUdf();
       
       replicatedUdf.setModuleId(stage.getModuleId());
       replicatedUdf.setModuleName(stage.getModuleName());
       replicatedUdf.setFieldName(stage.getFieldName());
       replicatedUdf.setFieldTypeId(stage.getFieldTypeId());
       replicatedUdf.setFieldType(stage.getFieldType());
       replicatedUdf.setOptions(stage.getOptions());
       replicatedUdf.setSequence(stage.getSequence());
       replicatedUdf.setVersionTime(stage.getVersionTime());
       replicatedUdf.setId(stage.getId());
       replicatedUdf.setStatus(stage.getStatus());
       replicatedUdf.setMandatory(stage.getMandatory());
        replicatedUdf.setCreationDate(stage.getCreationDate());
       replicatedUdf.setCreateBy(stage.getCreateBy());
       replicatedUdf.setLastUpdateDate(stage.getLastUpdateDate());
       replicatedUdf.setLastUpdateBy(stage.getLastUpdateBy());
       replicatedUdf.setNumericLength(stage.getNumericLength());
       replicatedUdf.setOperationName(stage.getOperationName());
       replicatedUdf.setDeprecated(stage.getDeprecated());
       
       idxTrxValue.setStagingUdf(replicatedUdf);
       IUdfTrxValue trxValue = createStagingUdf(idxTrxValue);
       trxValue = super.updateUdfTrx(trxValue);
       return super.prepareResult(trxValue);
   }
}
