package com.integrosys.cms.app.baselmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.baselmaster.bus.IBaselMaster;
import com.integrosys.cms.app.baselmaster.bus.OBBaselMaster;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;

public class MakerEditRejectedUpdateBaselOperation extends AbstractBaselTrxOperation{
	
	 public MakerEditRejectedUpdateBaselOperation()
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
        return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_BASEL;
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
    	IBaselMasterTrxValue idxTrxValue = getBaselTrxValue(anITrxValue);
    	IBaselMaster stage = idxTrxValue.getStagingBaselMaster();
    	IBaselMaster replicatedBasel= new OBBaselMaster();
    	replicatedBasel.setId(stage.getId());
    	replicatedBasel.setSystem(stage.getSystem());
    	replicatedBasel.setSystemValue(stage.getSystemValue());
    	replicatedBasel.setExposureSource(stage.getExposureSource());
    	replicatedBasel.setReportHandOff(stage.getReportHandOff());
        replicatedBasel.setVersionTime(stage.getVersionTime());
        replicatedBasel.setBaselValidation(stage.getBaselValidation())        ;
        
        replicatedBasel.setStatus(stage.getStatus());
        replicatedBasel.setDeprecated(stage.getDeprecated());
        
        idxTrxValue.setStagingBaselMaster(replicatedBasel);

        IBaselMasterTrxValue trxValue = createStagingBasel(idxTrxValue);
        trxValue = updateBaselTrx(trxValue);
        return super.prepareResult(trxValue);
    }


}
