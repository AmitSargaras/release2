package com.integrosys.cms.app.component.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.bus.OBHoliday;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;

public class MakerEditRejectedUpdateComponentOperation extends
		AbstractComponentTrxOperation {
	
	 public MakerEditRejectedUpdateComponentOperation()
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
         return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_COMPONENT;
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
         IComponentTrxValue idxTrxValue = getComponentTrxValue(anITrxValue);
         IComponent stage = idxTrxValue.getStagingComponent();
         IComponent replicatedComponent= new OBComponent();
         replicatedComponent.setId(stage.getId());
         replicatedComponent.setComponentType(stage.getComponentType());
         replicatedComponent.setComponentCode(stage.getComponentCode());
         replicatedComponent.setComponentName(stage.getComponentName());
         replicatedComponent.setHasInsurance(stage.getHasInsurance());
         replicatedComponent.setVersionTime(stage.getVersionTime());
        
       //Start:-------->Abhishek Naik
         replicatedComponent.setAge(stage.getAge());
         replicatedComponent.setDebtors(stage.getDebtors());
         
      // End:-------->Abhishek Naik 
         replicatedComponent.setStatus(stage.getStatus());
         replicatedComponent.setDeprecated(stage.getDeprecated());
         //Start Santosh
         replicatedComponent.setComponentCategory(stage.getComponentCategory());
         replicatedComponent.setApplicableForDp(stage.getApplicableForDp());
         //End Santosh
         idxTrxValue.setStagingComponent(replicatedComponent);

         IComponentTrxValue trxValue = createStagingComponent(idxTrxValue);
         trxValue = updateComponentTrx(trxValue);
         return super.prepareResult(trxValue);
     }


}
